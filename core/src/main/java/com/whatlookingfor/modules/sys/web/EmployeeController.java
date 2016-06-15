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
package com.whatlookingfor.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whatlookingfor.common.config.Global;
import com.whatlookingfor.core.persistence.Page;
import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.core.base.web.BaseController;
import com.whatlookingfor.modules.sys.entity.Employee;
import com.whatlookingfor.modules.sys.entity.Org;
import com.whatlookingfor.modules.sys.service.EmployeeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * 员工的controller
 * @author Jonathan
 * @version 2016/4/11 17:26
 * @since JDK 7.0+
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/employee")
public class EmployeeController extends BaseController {

	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	public Employee get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return employeeService.get(id);
		}else{
			return new Employee();
		}
	}


	@RequiresPermissions("sys:employee:view")
	@RequestMapping(value = {"","index"})
	public String index(){
		return "modules/sys/employeeIndex";
	}

	/**
	 * 员工管理分页
	 * @param employee
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:employee:view")
	@RequestMapping(value = "list")
	public String list(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee);
        model.addAttribute("page", page);
		return "modules/sys/employeeList";
	}

	/**
	 * 员工管理from
	 * @param employee
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:employee:view")
	@RequestMapping(value = "form")
	public String form(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/sys/employeeForm";
	}

	/**
	 * 员工信息保存
	 * @param employee
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(Employee employee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(redirectAttributes, employee)){
			return "redirect:" + adminPath + "/sys/employee/list?repage";
		}
		employeeService.save(employee);
		addMessage(redirectAttributes, "保存员工'" + employee.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/employee/list?repage";
	}

	/**
	 * 员工信息删除
	 * @param employee
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(Employee employee, RedirectAttributes redirectAttributes) {
		employeeService.delete(employee);
		addMessage(redirectAttributes, "删除员工成功");
		return "redirect:" + adminPath + "/sys/employee/list?repage";
	}

	/**
	 * 员工信息批量删除
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:employee:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			Employee employee = employeeService.get(id);
			if(Global.isDemoMode()){
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/sys/employee/list?repage";
			}

			employeeService.delete(employee);
			addMessage(redirectAttributes, "删除员工成功");
		}
		return "redirect:" + adminPath + "/sys/employee/list?repage";
	}


	/**
	 * 根据机构选择员工，用于弹出树，异步树加载
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=true) String orgId) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Org org = new Org(orgId);
		List<Employee> list = employeeService.findList(new Employee(org));
		for (int i=0; i<list.size(); i++){
			Employee e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "e_"+e.getId());
			map.put("pId", orgId);
			map.put("name", StringUtils.replace(e.getName(), " ", "")+"("+StringUtils.replace(e.getCode(), " ", "")+")");
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 员工编号是否重复检查
	 * @param oldEmployeeCd
	 * @param employeeCd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkEmployeeCd")
	public String checkEmployeeCd(String oldEmployeeCd, String employeeCd) {
		if (employeeCd!=null && employeeCd.equals(oldEmployeeCd)) {
			return "true";
		} else if (employeeCd!=null && employeeService.getEmployeeByCd(employeeCd) == null) {
			return "true";
		}
		return "false";
	}

}
