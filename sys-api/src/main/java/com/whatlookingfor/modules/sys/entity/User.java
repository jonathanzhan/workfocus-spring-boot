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

package com.whatlookingfor.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.whatlookingfor.core.base.entity.DataEntity;
import com.whatlookingfor.common.utils.Collections3;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 用户Entity
 * @author Jonathan
 * @version 2013-12-05
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;
	private String loginName;// 登录名
	private String password;// 密码
	private Integer userType;// 用户类型
	private String name;//用户名称

	private String img;	// 头像
	private String oldLoginName;// 原登录名
	private String newPassword;	// 新密码

	private String thisLoginIp;	// 最后登陆IP
	private Date thisLoginAt;	// 最后登陆日期

	private String lastLoginIp;	// 上次登陆IP
	private Date lastLoginAt;	// 上次登陆日期
	
	private Role role;	// 根据角色查询用户条件

	private Employee employee;// 员工信息
	
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

	public User() {
		super();
	}


	public User(String loginName, String password, String id) {
		this.loginName = loginName;
		this.password = password;
		this.id = id;
	}

	public User(String id){
		super(id);
	}

	public User(String id, String loginName){
		super(id);
		this.loginName = loginName;
	}

	public User(Role role){
		super();
		this.role = role;
	}

	public User(Employee employee){
		super();
		this.employee = employee;
	}


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}



	@Length(min=1, max=50, message="登录名长度必须介于 1 和 50 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	@JsonIgnore
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Length(min=1, max=20, message="用户名长度必须介于 1 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getThisLoginIp() {
		return thisLoginIp;
	}

	public void setThisLoginIp(String thisLoginIp) {
		this.thisLoginIp = thisLoginIp;
	}

	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getThisLoginAt() {
		return thisLoginAt;
	}

	public void setThisLoginAt(Date thisLoginAt) {
		this.thisLoginAt = thisLoginAt;
	}

	@JsonIgnore
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Date lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	@JsonIgnore
	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
	
	public boolean isAdmin(){
		return isAdmin(this.id);
	}
	
	public static boolean isAdmin(String id){
		return id != null && "1".equals(id);
	}

	/**
	 * 判断用户是否有机构
	 * @return
	 */
	@JsonIgnore
	public boolean hasOrg(){
		return getEmployee()!=null && getEmployee().getOrg()!=null;
	}

	/**
	 * 获取用户对应的机构
	 * @return
	 */
	public Org getOrg(){
		if(hasOrg()){
			return getEmployee().getOrg();
		}else{
			return null;
		}
	}


}