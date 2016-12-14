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
import com.whatlookingfor.modules.sys.entity.Menu;

import java.util.List;

/**
 * 菜单DAO接口
 * @author Jonathan
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	@Override
	Menu get(String id);

	@Override
	List<Menu> findAllList(Menu menu);

	@Override
	int insert(Menu entity);

	@Override
	int update(Menu entity);

	@Override
	int delete(Menu entity);

	List<Menu> findByParentIdsLike(Menu menu);

	List<Menu> findByUserId(Menu menu);
	
	int updateParentIds(Menu menu);
	
	int updateSort(Menu menu);
	
}
