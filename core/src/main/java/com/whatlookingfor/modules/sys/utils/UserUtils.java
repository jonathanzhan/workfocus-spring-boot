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
package com.whatlookingfor.modules.sys.utils;

import com.google.common.collect.Lists;
import com.whatlookingfor.common.utils.CacheUtils;
import com.whatlookingfor.common.utils.SpringContextHolder;
import com.whatlookingfor.modules.sys.dao.*;
import com.whatlookingfor.modules.sys.entity.*;
import com.whatlookingfor.modules.sys.security.SystemAuthorizingRealm.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * 用户工具类
 * @author Jonathan
 * @version 2015-12-05
 */
public class UserUtils {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(UserUtils.class);

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static OrgDao orgDao = SpringContextHolder.getBean(OrgDao.class);


	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_JOB_LIST = "jobList";
	public static final String CACHE_MENU_TREE = "menuTree";

	public static final String CACHE_ORG_LIST = "orgList";
	public static final String CACHE_ORG_ALL_LIST = "orgAllList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		@SuppressWarnings("unchecked")
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.get(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
//			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		@SuppressWarnings("unchecked")
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
//			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_ORG_LIST);
		removeCache(CACHE_ORG_ALL_LIST);
		removeCache(CACHE_JOB_LIST);
		removeCacheStartWith(CACHE_MENU_TREE, CACHE_MENU_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		@SuppressWarnings("unchecked")
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}


	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			User user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.findAllList(new Role());
			}else{
				Role role = new Role();
//				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleDao.findList(role);
			}
//			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}


	/**
	 * 根据开始级别以及截至级别，获取菜单的树形结构的数据
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Menu> getMenuTree(int start,int end){
		@SuppressWarnings("unchecked")
		List<Menu> result = (List<Menu>)getCache(CACHE_MENU_TREE + "-" + start + "-" + end);
		if(result == null){
			List<Menu> menuList = getMenuListByLevel(start, end);
			result = Menu.getMenuTree(menuList, start);
			logger.debug("putCache:{}",CACHE_MENU_TREE + "-" + start + "-" + end);
			putCache(CACHE_MENU_TREE+"-"+start+"-"+end,result);
		}
		return result;
	}


	/**
	 * 根据开始界别和截至界别，查询菜单的数组
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Menu> getMenuListByLevel(int start,int end){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST+"-"+start+"-"+end);
		if(menuList == null){
			User user = getUser();
			Menu menu = new Menu(start,end);
			if(user.isAdmin()){
				menuList = menuDao.findAllList(menu);
			}else{
				menu.setUserId(user.getId());
				menuList = menuDao.findByUserId(menu);
			}
			putCache(CACHE_MENU_LIST+"-"+start+"-"+end,menuList);
			logger.debug("putCache:{}",CACHE_MENU_LIST+"-"+start+"-"+end);
		}

		return menuList;
	}

	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			User user = getUser();
			if (user.isAdmin()){
				menuList = menuDao.findAllList(new Menu());
			}else{
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			putCache(CACHE_MENU_LIST, menuList);
			logger.debug("putCache:{}",CACHE_MENU_LIST);
		}
		return menuList;
	}


	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Org> getOrgList(){
		@SuppressWarnings("unchecked")
		List<Org> orgList = (List<Org>)getCache(CACHE_ORG_LIST);
		if (orgList == null){
			User user = getUser();
			if (user.isAdmin()){
				orgList = orgDao.findAllList(new Org());
			}else{
				Org org = new Org();
//				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				orgList = orgDao.findList(org);
			}
//			putCache(CACHE_ORG_LIST, orgList);
		}
		return orgList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Org> getOrgAllList(){
		@SuppressWarnings("unchecked")
		List<Org> orgList = (List<Org>)getCache(CACHE_ORG_ALL_LIST);
		if (orgList == null){
			orgList = orgDao.findAllList(new Org());
		}
		return orgList;
	}



	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {

		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		getSession().removeAttribute(key);
	}


	/**
	 * 清除自定义后缀的缓存
	 */
	public static void removeCacheStartWith(String... caches){

		Iterator<Object> iterator = getSession().getAttributeKeys().iterator();
		List<String> attributes = Lists.newArrayList();
		while(iterator.hasNext()){
			Object o = iterator.next();
			for (String cache:caches){
				if(o.toString().startsWith(cache)){
					attributes.add(o.toString());
				}
			}
		}
		for (String key : attributes) {
			logger.debug("remove cache:{}", key);
			removeCache(key);
		}
	}

	
}
