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

import com.whatlookingfor.core.base.entity.DataEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 员工实体类
 *
 * @author Jonathan
 * @version 2016/5/9 16:45
 * @since JDK 7.0+
 */
public class Employee extends DataEntity<Employee> {

	private Org org;//员工所属机构

	private String code;//员工编码(生成user的loginName)

	private String name;//员工中文姓名

	private String eName;//员工英文姓名

	private Integer sex;//性别

	private Date birthday;//出生年月

	private String address;//联系地址

	private String tel;// 联系电话

	private String idCode;//身份证

	private Integer education;//学历

	private String qq; //qq

	private String email;// 电子邮箱

	private Integer isOpen;// 是否开通系统帐号

	private String photo;//照片


	public Employee(Org org) {
		super();
		this.org = org;
	}


	public Employee() {
		super();
		this.isOpen = 0;
		this.sex = 1;
	}


	@Length(min = 1, max = 12, message = "员工编号长度必须介于 1 和 12 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 1, max = 15, message = "中文名称长度必须介于 1 和 15 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 1, max = 15, message = "英文文名称长度必须介于 1 和 15 之间")
	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}


	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday != null ? new Date(birthday.getTime()) : null;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday != null ? new Date(birthday.getTime()) : null;
	}


	@Length(min = 0, max = 200, message = "联系地址长度最大长度为200")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min = 0, max = 20, message = "联系电话长度最大长度为20")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Length(min = 18, max = 18, message = "身份证长度为18位")
	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}


	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Email(message = "email格式不正确")
	@Length(min = 0, max = 20, message = "email的最大长度为20")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}


}
