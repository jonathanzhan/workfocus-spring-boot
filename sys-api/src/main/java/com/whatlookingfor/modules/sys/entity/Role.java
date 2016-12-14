/**
 * Copyright  2014-2016 whatlookingfor@gmail.com(Jonathan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whatlookingfor.modules.sys.entity;

import com.google.common.collect.Lists;
import com.whatlookingfor.core.base.entity.DataEntity;
import com.whatlookingfor.common.utils.Collections3;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * 角色的实体类
 * @author Jonathan
 * @version 2016/4/15 15:26
 * @since JDK 7.0+
 *
 */
public class Role extends DataEntity<Role> {
	
	private static final long serialVersionUID = 1L;
	private String name; 	// 角色名称
	private String ename;	// 英文名称
	private int isSys; //是否系统设置

	private String roleType;//角色类型

	private int dataScope;//数据范围


	private String oldName; 	// 原角色名称（判断重复使用）
	private String oldEname;	// 原英文名称（判断重复使用）

	private User user;		// 根据用户ID查询角色列表

	private List<Menu> menuList = Lists.newArrayList(); // 拥有菜单列表
	private List<Org> orgList = Lists.newArrayList(); // 按明细设置数据范围

	// 数据范围（1：所有数据；2：所在部门及以下数据；3：所在部门数据；4：仅本人数据； 5自定义部门
	public static final int DATA_SCOPE_ALL = 1;
	public static final int DATA_SCOPE_ORG_AND_CHILD = 2;
	public static final int DATA_SCOPE_ORG = 3;
	public static final int DATA_SCOPE_SELF = 4;
	public static final int DATA_SCOPE_CUSTOM = 5;
	
	public Role() {
		super();
		this.dataScope = DATA_SCOPE_SELF;
	}
	
	public Role(String id){
		super(id);
	}

	public Role(User user) {
		this();
		this.user = user;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	@Length(min=1, max=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=20)
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}


	@NotNull
	public int getIsSys() {
		return isSys;
	}

	public void setIsSys(int isSys) {
		this.isSys = isSys;
	}


	@NotNull
	public int getDataScope() {
		return dataScope;
	}

	public void setDataScope(int dataScope) {
		this.dataScope = dataScope;
	}

	public String getOldEname() {
		return oldEname;
	}

	public void setOldEname(String oldEname) {
		this.oldEname = oldEname;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}



	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<String> getMenuIdList() {
		List<String> menuIdList = Lists.newArrayList();
		for (Menu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		menuList = Lists.newArrayList();
		for (String menuId : menuIdList) {
			Menu menu = new Menu();
			menu.setId(menuId);
			menuList.add(menu);
		}
	}


	public String getMenuIds() {
		return StringUtils.join(getMenuIdList(), ",");
	}


	public void setMenuIds(String menuIds) {
		menuList = Lists.newArrayList();
		if (menuIds != null){
			String[] ids = StringUtils.split(menuIds, ",");
			setMenuIdList(Lists.newArrayList(ids));
		}
	}

	


	public List<Org> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}

	public List<String> getOrgIdList() {
		List<String> orgIdList = Lists.newArrayList();
		for (Org org : orgList) {
			orgIdList.add(org.getId());
		}
		return orgIdList;
	}

	public void setOrgIdList(List<String> orgIdList) {
		orgList = Lists.newArrayList();
		for (String orgId : orgIdList) {
			Org org = new Org();
			org.setId(orgId);
			orgList.add(org);
		}
	}

	public String getOrgNames() {
		return Collections3.extractToString(getOrgList(), "name", ",");
	}

	public String getOrgIds() {
		return StringUtils.join(getOrgIdList(), ",");
	}

	public void setOrgIds(String orgIds) {
		orgList = Lists.newArrayList();
		if (orgIds != null){
			String[] ids = StringUtils.split(orgIds, ",");
			setOrgIdList(Lists.newArrayList(ids));
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
