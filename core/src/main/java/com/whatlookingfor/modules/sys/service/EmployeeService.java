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
package com.whatlookingfor.modules.sys.service;

import com.whatlookingfor.core.persistence.Page;
import com.whatlookingfor.core.base.service.CrudService;
import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.modules.sys.dao.EmployeeDao;
import com.whatlookingfor.modules.sys.entity.Employee;
import com.whatlookingfor.modules.sys.entity.Role;
import com.whatlookingfor.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class EmployeeService extends CrudService<EmployeeDao, Employee> {
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private SystemService systemService;

    public Page<Employee> findPage(Page<Employee> page, Employee employee) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        dataScopeFilter(employee,"dsf","id=a.org_id","employee_id = a.id",true);
        // 设置分页参数
        employee.setPage(page);
        // 执行分页查询
        page.setList(employeeDao.findList(employee));
        return page;
    }

    /**
     * 员工信息保存
     *
     * @param employee
     */
    @Transactional(readOnly = false)
    public void save(Employee employee) {
        if (StringUtils.isBlank(employee.getId())) {//根据ID是否为空判断新增还是删除
            employee.preInsert();
            employeeDao.insert(employee);//新增员工信息
			if(employee.getIsOpen()==1){//如果等于开通，那么给给user表添加对应数据
				//初始化用户信息
				User user = new User();
				user.setEmployee(employee);
				user.setLoginName(employee.getCode());
				user.setName(employee.getName());
				user.setPassword(SystemService.entryptPassword("123456"));
				user.setUserType(2);
				user.setImg(employee.getPhoto());
				user.getRoleList().add(new Role("1"));

				//调用添加用户方法
				systemService.saveUser(user);
			}
        } else {
            employee.preUpdate();
            employeeDao.update(employee);//修改员工信息
        }


    }

    /**
     * 员工信息删除
     *
     * @param employee
     */
    @Transactional(readOnly = false)
    public void delete(Employee employee) {
        super.delete(employee);
    }

    /**
     * 根据员工编号查询员工信息
     *
     * @param code
     * @return
     */
    public Employee getEmployeeByCd(String code) {
        Employee employee = new Employee();
        employee.setCode(code);
        return employeeDao.getEmployeeByCd(employee);
    }
}
