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

package com.whatlookingfor.core.base.entity;

import java.io.Serializable;

/**
 * 系统用户的基类
 *
 * @author Jonathan
 * @version 2016/6/28 17:12
 * @since JDK 7.0+
 */
public class BaseUser<T> extends DataEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String loginName;//登录名

	protected String password;//密码

	protected Integer userType;//用户类型

	protected String name;//用户昵称


	public BaseUser() {
		super();
	}

	public BaseUser(String id) {
		super(id);
	}

	public BaseUser(String id, String loginName) {
		super(id);
		this.loginName = loginName;
	}

	public BaseUser(String id, String loginName, String name) {
		super(id);
		this.loginName = loginName;
		this.name = name;
	}


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BaseUser{" +
				"loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", userType=" + userType +
				", name='" + name + '\'' +
				"} ";
	}
}
