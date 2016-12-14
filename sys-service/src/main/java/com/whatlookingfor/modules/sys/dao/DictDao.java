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

import com.whatlookingfor.modules.sys.entity.Dict;

import java.util.List;

/**
 * 数据字典DAO接口
 *
 * @author Jonathan
 * @version 2016-03-21
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

    @Override
    Dict get(String id);

    @Override
    List<Dict> findList(Dict dict);

    @Override
    List<Dict> findAllList(Dict dict);

    @Override
    int insert(Dict dict);

    @Override
    int update(Dict dict);

    @Override
    int delete(Dict dict);


    List<String> findTypeList(Dict dict);


}
