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

package com.whatlookingfor.modules.sys.serviceImp;


import com.whatlookingfor.core.base.entity.Page;
import com.whatlookingfor.core.base.service.CrudService;
import com.whatlookingfor.modules.sys.dao.ParamsDao;
import com.whatlookingfor.modules.sys.entity.Params;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统参数的service
 *
 * @author Jonathan
 * @version 2016/5/9 17:33
 * @since JDK 7.0+
 */
@Service
@Transactional(readOnly = true)
public class ParamsService extends CrudService<ParamsDao, Params> {

    /**
     * 根据参数名查询系统参数信息
     * @param paramName
     * @return
     */
    public Params getParamById(String paramName) {
        return dao.getParamById(paramName);
    }

    public List<Params> findList(Params params) {
        return super.findList(params);
    }

    public Page<Params> findPage(Page<Params> page, Params params) {
        return super.findPage(page, params);
    }

    public List<String> findParamNameList(){
        return dao.findParamNameList(new Params());
    }


    @Transactional(readOnly = false)
    public void save(Params params) {
        params.preInsert();
        super.dao.save(params);
    }


    @Transactional(readOnly = false)
    public void update(Params params) {
        params.preUpdate();
        super.dao.update(params);
    }

    @Transactional(readOnly = false)
    public void delete(Params params) {
        super.delete(params);
    }

}
