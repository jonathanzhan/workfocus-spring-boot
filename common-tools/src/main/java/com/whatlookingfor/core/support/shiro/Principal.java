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

package com.whatlookingfor.core.support.shiro;


import java.io.Serializable;

/**
 * Shiro的身份实体类
 *
 * @author Jonathan
 * @version 2016/12/7 14:52
 * @since JDK 7.0+
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 编号,唯一约束
	 */
	private String id;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * token
	 */
	private String token;

	public Principal(String id, String loginName, String name) {
		this.id = id;
		this.loginName = loginName;
		this.name = name;
	}

	public Principal(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "Principal{" +
				"id='" + id + '\'' +
				", loginName='" + loginName + '\'' +
				", name='" + name + '\'' +
				", token='" + token + '\'' +
				'}';
	}
}
