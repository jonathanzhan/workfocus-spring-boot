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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whatlookingfor.common.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Range;
import com.whatlookingfor.core.support.shiro.Principal;

import java.util.Date;

/**
 * 增删改查功能的实体类的基类
 *
 * @author Jonathan
 * @version 2016/6/29 17:15
 * @since JDK 7.0+
 */
public abstract class DataEntity<T> extends BaseEntity<T>{

	private static final long serialVersionUID = 1L;

	/**
	 * 当前用户
	 */
	protected BaseUser currentUser;

	protected BaseUser createBy;	// 创建者
	protected Date createAt;	// 创建日期
	protected BaseUser updateBy;	// 更新者
	protected Date updateAt;	// 更新日期
	protected int delFlag; 	// 删除标记（0：正常；1：删除；2：审核）

	public DataEntity() {
		this.delFlag = DEL_FLAG_NORMAL;
	}

	public DataEntity(String id) {
		super(id);
		this.delFlag = DEL_FLAG_NORMAL;
	}

	@JsonIgnore
	public BaseUser getCurrentUser() {
		if(this.currentUser!=null){
			return currentUser;
		}
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return new BaseUser(principal.getId(), principal.getLoginName(), principal.getName());
			}
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return new BaseUser();
	}

	public void setCurrentUser(BaseUser currentUser) {
		this.currentUser = currentUser;
	}
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		BaseUser user = getCurrentUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.createBy = user;
			this.updateBy = user;
		}
		this.updateAt = new Date();
		this.createAt = this.updateAt;
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		BaseUser user = getCurrentUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}
		this.updateAt = new Date();
	}

	@JsonIgnore
	public BaseUser getCreateBy() {
		return createBy;
	}

	public void setCreateBy(BaseUser createBy) {
		this.createBy = createBy;
	}

	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@JsonIgnore
	public BaseUser getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(BaseUser updateBy) {
		this.updateBy = updateBy;
	}

	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@JsonIgnore
	@Range(max = 1,min = 0,message = "删除标识只能为0和1")
	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
}
