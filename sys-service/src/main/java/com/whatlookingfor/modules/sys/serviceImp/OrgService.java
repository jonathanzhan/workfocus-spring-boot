/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whatlookingfor.modules.sys.serviceImp;

import com.whatlookingfor.core.base.service.TreeService;
import com.whatlookingfor.modules.sys.dao.OrgDao;
import com.whatlookingfor.modules.sys.entity.Org;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构Service
 * @author thinkgem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OrgService extends TreeService<OrgDao, Org> {

	public List<Org> findAll(){
		return dao.findAllList(new Org());
	}

	public List<Org> findList(Boolean isAll){
		if (isAll != null && isAll){
			return dao.findAllList(new Org());
		}else{
			return dao.findList(new Org());
		}
	}
	
	@Transactional(readOnly = true)
	public List<Org> findList(Org org){
		if(org != null){
			org.setParentIds(org.getParentIds()+"%");
			return dao.findByParentIdsLike(org);
		}
		return  new ArrayList<Org>();
	}
	
	@Transactional(readOnly = false)
	public void save(Org org) {
		super.save(org);
	}
	
	@Transactional(readOnly = false)
	public void delete(Org org) {
		super.delete(org);
	}
	
}
