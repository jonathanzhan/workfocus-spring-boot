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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.core.config.Global;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * 实体类的基类
 *
 * @author Jonathan
 * @version 2016/6/27 17:04
 * @since JDK 7.0+
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final int DEL_FLAG_NORMAL = 0;
	public static final int DEL_FLAG_DELETE = 1;

	/**
	 * 实体类编号(唯一标识)
	 */
	protected String id;


	/**
	 * 当前实体的分页对象
	 */
	protected Page<T> page;

	/**
	 * 自定义sql(sql标识,sql内容)
	 */
	protected Map<String, String> sqlMap;

	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	protected boolean isNewRecord = false;

	public BaseEntity() {
	}

	public BaseEntity(String id) {
		this.id = id;
	}

	/**
	 * 插入之前执行方法，子类实现
	 */
	public abstract void preInsert();

	/**
	 * 更新之前执行方法，子类实现
	 */
	public abstract void preUpdate();


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@JsonIgnore
	public Page<T> getPage() {
		if (page == null) {
			page = new Page<T>();
		}
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	@JsonIgnore
	public Map<String, String> getSqlMap() {
		if (sqlMap == null) {
			sqlMap = Maps.newHashMap();
		}
		return sqlMap;
	}


	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 *
	 * @return boolean
	 */
	@JsonIgnore
	public boolean isNewRecord() {
		return isNewRecord || StringUtils.isBlank(getId());
	}

	public void setNewRecord(boolean newRecord) {
		isNewRecord = newRecord;
	}

	/**
	 * 全局变量对象
	 */
	@JsonIgnore
	public Global getGlobal() {
		return Global.getInstance();
	}

	/**
	 * 获取数据库名称
	 */
	@JsonIgnore
	public String getDbName() {
		return Global.getConfig("jdbc.type");
	}


	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		BaseEntity<?> that = (BaseEntity<?>) obj;
		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}


}
