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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.whatlookingfor.core.base.entity.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单Entity
 * 顶级节点的ID为1，必须写死
 * @author Jonathan
 * @version 2016-03-15
 */
public class Menu extends DataEntity<Menu> {

	private static final long serialVersionUID = 1L;
	private Menu parent;	// 父级菜单
	private String parentIds; // 所有父级编号
	private String name; 	// 名称
	private String href; 	// 链接
	private String target; 	// 目标（ mainFrame、_blank、_self、_parent、_top）
	private String icon; 	// 图标
	private Integer seq; 	// 排序
	private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
	private String permission; // 权限标识

	private String remarks;
	
	private String userId;

	private Integer level;//菜单级别

	private Integer startLevel;//开始级别

	private Integer endLevel;//截止级别

	private List<Menu> childList;
	
	public Menu(){
		super();
		this.seq = 30;
	}
	
	public Menu(String id){
		super(id);
	}


	public Menu(Integer startLevel,Integer endLevel){
		super();
		this.startLevel = startLevel;
		this.endLevel = endLevel;
	}

	public List<Menu> getChildList() {
		return childList;
	}

	public void setChildList(List<Menu> childList) {
		this.childList = childList;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@JsonIgnore
	public Integer getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(Integer startLevel) {
		this.startLevel = startLevel;
	}

	@JsonIgnore
	public Integer getEndLevel() {
		return endLevel;
	}

	public void setEndLevel(Integer endLevel) {
		this.endLevel = endLevel;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonBackReference
	@NotNull
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@Length(min=1, max=2000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=100)
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min=0, max=20)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Length(min=0, max=100)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@NotNull
	@Range(min = 0,max = 1000,message = "序号只能为0-1000的整数")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}



	
	@Length(min=1, max=1)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Length(min=0, max=1000)
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}


	/**
	 * 菜单数据的遍历，获取按照遍历顺序组合而成的数组
	 * @param list
	 * @param sourceList
	 * @param parentId
	 * @param cascade
	 */
	@JsonIgnore
	public static void sortList(List<Menu> list, List<Menu> sourceList, String parentId, boolean cascade){
		for (int i=0; i<sourceList.size(); i++){
			Menu e = sourceList.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourceList.size(); j++){
						Menu child = sourceList.get(j);
						if (child.getParent()!=null && child.getParent().getId()!=null
								&& child.getParent().getId().equals(e.getId())){
							sortList(list, sourceList, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}


	/**
	 * 获取从某个级别开始的树形结构的数据
	 * @param sourceList
	 * @param preMenu
	 * @return
	 */
	@JsonIgnore
	public static List<Menu> getMenuTree(List<Menu> sourceList,List<Menu> preMenu){
		List<Menu> list = Lists.newArrayList();//定义返回值
		for (int i=0; i<preMenu.size(); i++) {
			Menu e = preMenu.get(i);
			list.addAll(getMenuTree(sourceList,e.getId()));
		}
		return list;
	}


	/**
	 * 获取某parentId下的树形结构的数据
	 * @param sourceList
	 * @param parentId
	 * @return
	 */
	@JsonIgnore
	public static List<Menu> getMenuTree(List<Menu> sourceList,String parentId){
		List<Menu> list = Lists.newArrayList();//定义返回值
		for (int i=0; i<sourceList.size(); i++) {
			Menu e = sourceList.get(i);
			// 判断是否还有子节点, 有则继续获取子节点
			if (e.getParentId().equals(parentId)){
				List<Menu> childList = getMenuTree(sourceList, e.getId());
				e.setChildList(childList);
				list.add(e);
			}
		}
		return list;
	}




	@JsonIgnore
	public static String getRootId(){
		return "1";
	}


	@JsonIgnore
	public static boolean isRoot(String id){
		return id != null && getRootId().equals(id);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return name;
	}
}