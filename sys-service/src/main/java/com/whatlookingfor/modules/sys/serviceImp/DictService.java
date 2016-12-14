/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whatlookingfor.modules.sys.serviceImp;

import com.whatlookingfor.core.base.service.CrudService;
import com.whatlookingfor.common.utils.CacheUtils;
import com.whatlookingfor.modules.sys.dao.DictDao;
import com.whatlookingfor.modules.sys.entity.Dict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典Service
 *
 * @author thinkgem
 * @version 2013-03-21
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

    @Override
    public Dict get(String id) {
        return dao.get(id);
    }


    /**
     * 查询字段类型列表
     *
     * @return
     */
    public List<String> findTypeList() {
        return dao.findTypeList(new Dict());
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Dict dict) {
        super.save(dict);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Dict dict) {
        super.delete(dict);
    }

}
