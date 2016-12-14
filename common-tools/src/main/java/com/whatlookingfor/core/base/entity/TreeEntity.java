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
import com.whatlookingfor.common.utils.Reflections;
import com.whatlookingfor.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 树形接口的基类
 *
 * @author Jonathan
 * @version 2016/7/13 15:00
 * @since JDK 7.0+
 */
public abstract class TreeEntity<T> extends DataEntity<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * 上级节点
	 */
	protected T parent;

	/**
	 * 所有父级编号
	 */
	protected String parentIds;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 排序
	 */
	protected Integer sort;

	/**
	 * 树的级别,从1开始
	 */
	protected Integer level;

	/**
	 * 原有级别
	 */
	protected Integer oldLevel;

	public TreeEntity() {
		super();
		this.sort = 30;
	}

	public TreeEntity(String id) {
		super(id);
		this.sort = 30;
	}

	@NotNull
	public abstract T getParent();

	/**
	 * 父对象，只能通过子类实现，父类实现mybatis无法读取
	 *
	 * @return
	 */
	public abstract void setParent(T parent);

	@Length(min = 1, max = 2000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Length(min = 1, max = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@Range(min = 0, max = 1000, message = "序号只能为0-1000的整数")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@NotNull
	@Range(min = 0, max = 10, message = "级别只能为0-10的整数")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@JsonIgnore
	public Integer getOldLevel() {
		return oldLevel;
	}

	public void setOldLevel(Integer oldLevel) {
		this.oldLevel = oldLevel;
	}

	/**
	 * 获取parentId
	 *
	 * @return 如果parent不为空, 则返回parentId, 反之, 返回0
	 */
	@JsonIgnore
	public String getParentId() {
		String id = null;
		if (parent != null) {
			id = (String) Reflections.getFieldValue(parent, "id");
		}
		return StringUtils.isNotBlank(id) ? id : "0";
	}

}
