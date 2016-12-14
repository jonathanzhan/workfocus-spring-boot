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
package com.whatlookingfor.modules.sys.dao;

import com.whatlookingfor.core.base.dao.CrudDao;
import com.whatlookingfor.core.support.mybatis.MyBatisDao;
import com.whatlookingfor.modules.sys.entity.Role;

import java.util.List;

/**
 * 角色DAO接口
 * @author Jonathan
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	@Override
	Role get(String id);

	@Override
	List<Role> findList(Role role);

	@Override
	List<Role> findAllList(Role role);

	@Override
	int insert(Role role);

	@Override
	int update(Role role);

	@Override
	int delete(Role role);


	Role getByName(Role role);
	
	Role getByEname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	int deleteRoleMenu(Role role);

	int insertRoleMenu(Role role);


	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOrg(Role role);

	public int insertRoleOrg(Role role);

}
