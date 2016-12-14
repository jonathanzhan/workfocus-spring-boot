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
import com.whatlookingfor.modules.sys.entity.Params;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统参数的dao层
 * 
 * @author Jonathan
 * @version 2016/5/9 17:28
 * @since JDK 7.0+
 */
@MyBatisDao
public interface ParamsDao extends CrudDao<Params> {

    public void save(Params params);

    public Params getParamById(@Param(value = "paramName") String paramName);

    public List<String> findParamNameList(Params params);

    void updateValueByName(Params params);
}
