/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whatlookingfor.core.base.service;

import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.core.base.entity.BaseEntity;
import com.whatlookingfor.modules.sys.entity.Role;
import com.whatlookingfor.modules.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param orgAlias 机构表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @param isBusiness 是否系统管理员有权限查看数据  true 是可以
	 * @return 标准连接条件对象
	 */
	public static String dataScopeFilter(User user, String orgAlias, String userAlias, boolean isBusiness) {
		StringBuilder sqlString = new StringBuilder();
		if(user.isAdmin()){//系统管理员是否可以查询所有数据
			if(isBusiness){
				return "";
			}else{
				return " AND 1=2";
			}
		}

		//查询该用户的最大数据权限,以及是否有自定义权限
		boolean isDataScopeCustom = false;
		int dataScope = Role.DATA_SCOPE_SELF;
		for (Role r : user.getRoleList()){
			int ds = r.getDataScope();
			if (ds == Role.DATA_SCOPE_CUSTOM){
				isDataScopeCustom = true;
			}else if (ds < dataScope){//ds越小,权限范围越大
				dataScope = ds;
			}
		}

		if(dataScope == Role.DATA_SCOPE_ALL){//能查看所有数据
			return "";
		}else{
			for (String oa : StringUtils.split(orgAlias, ",")){
				if(StringUtils.isNoneBlank(oa)){//可能存在orgAlias 误写等情况,在此做个过滤
					//数据范围是本部门以及下属部门,并且当前登录人拥有部门信息
					if(Role.DATA_SCOPE_ORG_AND_CHILD == dataScope && user.hasOrg()){
						sqlString.append(" OR " + oa + ".id = '" + user.getOrg().getId() + "'");
						sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOrg().getParentIds() + user.getOrg().getId() + ",%'");
					}
					//数据范围是本部门,并且当前登录人拥有部门信息
					else if(Role.DATA_SCOPE_ORG == dataScope && user.hasOrg()){
						sqlString.append(" OR " + oa + ".id = '" + user.getOrg().getId() + "'");
					}

					//拥有自定义权限(表后缀为160130是我儿子生日,O(∩_∩)O哈哈~)
					if(isDataScopeCustom){
						sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_user ru160130, sys_role r160130 ,sys_role_org ro160130");
						sqlString.append(" WHERE ru160130.user_id = '" + user.getId() + "' ");
						sqlString.append(" AND ru160130.role_id = r160130.id ");
						sqlString.append(" AND ro160130.role_id = r160130.id ");
						sqlString.append(" AND ro160130.org_id = " + oa +".id)");
					}
				}
			}
		}

		//可能有点难理解
		//如果存在userAlias，则查询结果增加userAlias.id = userId
		//如果不存在，但是存在机构关联表，则查询结果增加 结构id=null的数据(如果仅仅是本人数据,那么无userAlias，其实就不应该查询到数据)
		if (StringUtils.isNotBlank(userAlias)){
			for (String ua : StringUtils.split(userAlias, ",")){
				sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
			}
		} else {
			for (String oa : StringUtils.split(orgAlias, ",")){
				sqlString.append(" OR " + oa + ".id IS NULL");
			}
		}

		// 生成个人权限SQL语句
		if (StringUtils.isNotBlank(userAlias)){
			if (Role.DATA_SCOPE_SELF == dataScope){
				for (String ua : StringUtils.split(userAlias, ",")){
					sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
				}
			}
		}

		if (StringUtils.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}

	/**
	 * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
	 *
	 *  <pre>dataScopeFilter(user, "dsf", "id=a.office_id", "id=a.create_by");</pre>
	 * 	<pre>dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。</pre>
	 *
	 * @param entity 当前过滤的实体类
	 * @param sqlMapKey sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
	 * @param orgWheres org表条件，组成：部门表字段=业务表的部门字段
	 * @param userWheres user表条件，组成：用户表字段=业务表的用户字段
	 * @param isBusiness 管理员是否可以查询业务数据(是的话,表示能查看,否表示查询不到任何数据)
	 */
	public static void dataScopeFilter(BaseEntity<?> entity, String sqlMapKey, String orgWheres, String userWheres, boolean isBusiness) {

		User user = entity.getCurrentUser();
		// 数据范围（1：所有数据；2：所在部门及以下数据；3：所在部门数据；4：仅本人数据；5：按明细设置）
		StringBuilder sqlString = new StringBuilder();

		if(user.isAdmin()){//系统管理员是否可以查询所有数据
			if(isBusiness){
				return;
			}else{
				entity.getSqlMap().put(sqlMapKey, " AND 1=2");
				return;
			}
		}

		//查询该用户的最大数据权限,以及是否有自定义权限
		boolean isDataScopeCustom = false;
		int dataScope = Role.DATA_SCOPE_SELF;
		for (Role r : user.getRoleList()){
			int ds = r.getDataScope();
			if (ds == Role.DATA_SCOPE_CUSTOM){
				isDataScopeCustom = true;
			}else if (ds < dataScope){//ds越小,权限范围越大
				dataScope = ds;
			}
		}

		if(dataScope == Role.DATA_SCOPE_ALL){//能查看所有数据
			return;
		}else{
			// 生成部门权限SQL语句
			for (String where : StringUtils.split(orgWheres, ",")){
				if (Role.DATA_SCOPE_ORG_AND_CHILD == dataScope && user.hasOrg()){
					sqlString.append(" OR EXISTS (SELECT 1 FROM SYS_ORG");
					sqlString.append(" WHERE (id = '" + user.getOrg().getId() + "'");
					sqlString.append(" OR parent_ids LIKE '" + user.getOrg().getParentIds() + user.getOrg().getId() + ",%')");
					sqlString.append(" AND " + where +")");
				}
				else if (Role.DATA_SCOPE_ORG == dataScope && user.hasOrg()){
					sqlString.append(" OR EXISTS (SELECT 1 FROM SYS_ORG");
					sqlString.append(" WHERE id = '" + user.getOrg().getId() + "'");
					sqlString.append(" AND " + where +")");
				}

				//拥有自定义权限(表后缀为160130是我儿子生日,O(∩_∩)O哈哈~)
				if(isDataScopeCustom){
					sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_user ru160130, sys_role r160130 ,sys_role_org ro160130,sys_org o160130");
					sqlString.append(" WHERE ru160130.user_id = '" + user.getId() + "' ");
					sqlString.append(" AND ru160130.role_id = r160130.id ");
					sqlString.append(" AND ro160130.role_id = r160130.id ");
					sqlString.append(" AND ro160130.org_id = o160130.id ");
					sqlString.append(" AND o160130." + where +")");
				}
			}
		}


		// 生成个人权限SQL语句
		for (String where : StringUtils.split(userWheres, ",")){
			if (Role.DATA_SCOPE_SELF == dataScope){
				sqlString.append(" OR EXISTS (SELECT 1 FROM sys_user");
				sqlString.append(" WHERE id='" + user.getId() + "'");
				sqlString.append(" AND " + where + ")");
			}
		}

		String result = "";
		if (StringUtils.isNotBlank(sqlString.toString())){
			result = " AND (" + sqlString.substring(4) + ")";
		}
		// 设置到自定义SQL对象
		entity.getSqlMap().put(sqlMapKey, result);

	}


}
