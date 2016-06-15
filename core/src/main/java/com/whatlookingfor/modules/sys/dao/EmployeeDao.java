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
import com.whatlookingfor.core.persistence.annotation.MyBatisDao;
import com.whatlookingfor.modules.sys.entity.Employee;

import java.util.List;

/**
 * 员工DAO接口
 * @author Jonathan
 * @version 2014-05-16
 */
@MyBatisDao
public interface EmployeeDao extends CrudDao<Employee> {

    @Override
    Employee get(String id);

    @Override
    List<Employee> findAllList(Employee entity);

    @Override
    List<Employee> findList(Employee entity);

    @Override
    int insert(Employee entity);

    @Override
    int update(Employee entity);

    @Override
    int delete(Employee entity);

    /**
     * 根据员工编号查询员工信息
     * @param employee
     * @return
     */
    public Employee getEmployeeByCd(Employee employee);

    /**
     * 更新员工信息(个人信息保存时调用)
     * @param employee
     */
    public void updateEmployeeInfo(Employee employee);
}
