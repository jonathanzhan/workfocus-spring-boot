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

package com.whatlookingfor.core.config;

import com.google.common.collect.Maps;
import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.core.security.shiro.session.CacheSessionDAO;
import com.whatlookingfor.core.security.shiro.session.SessionManager;
import com.whatlookingfor.modules.sys.security.FormAuthenticationFilter;
import com.whatlookingfor.modules.sys.security.SystemAuthorizingRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro的权限配置
 *
 * @author Jonathan
 * @version 2016/6/1 14:38
 * @since JDK 7.0+
 */
@Configuration
public class ShiroConfiguration implements EnvironmentAware {

	private String adminPath;

	private long sessionTimeOut;

	private long sessionTimeOutClean;

	@Override
	public void setEnvironment(Environment environment) {
		this.adminPath = environment.getProperty("adminPath");
		this.sessionTimeOut = StringUtils.toLong(environment.getProperty("shiro.session.sessionTimeOut"));
		this.sessionTimeOutClean = StringUtils.toLong(environment.getProperty("shiro.session.sessionTimeoutClean"));
	}


	/**
	 * 配置shiro的安全管理配置
	 * @return DefaultWebSecurityManager
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(systemAuthorizingRealm());
		securityManager.setCacheManager(shiroCacheManager());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}


	/**
	 * 系统安全认证的实现类
	 * @return SystemAuthorizingRealm
	 */
	@Bean(name = "systemAuthorizingRealm")
	@DependsOn("lifecycleBeanPostProcessor")
	public SystemAuthorizingRealm systemAuthorizingRealm(){
		return new SystemAuthorizingRealm();
	}


	/**
	 * ehcache的缓存管理的工厂类
	 * @return EhCacheManagerFactoryBean
	 */
	@Bean(name = "cacheManager")
	public EhCacheManagerFactoryBean cacheManager(){
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ClassPathResource resource = new ClassPathResource("cache/ehcache-local.xml");
		ehCacheManagerFactoryBean.setConfigLocation(resource);
		return ehCacheManagerFactoryBean;
	}

	/**
	 * 定义授权缓存管理器
	 * @return EhCacheManager
	 */
	@Bean(name = "shiroCacheManager")
	public EhCacheManager shiroCacheManager(){
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManager(cacheManager().getObject());
		return ehCacheManager;
	}

	/**
	 * 自定义会话管理配置
	 * @return SessionManager
	 */
	@Bean(name = "sessionManager")
	public SessionManager sessionManager(){
		SessionManager sessionManager = new SessionManager();
		//session的存储容器
		sessionManager.setSessionDAO(sessionDAO());
		//会话超时时间,单位:毫秒
		sessionManager.setGlobalSessionTimeout(sessionTimeOut);
		//会话清理间隔时间
		sessionManager.setSessionValidationInterval(sessionTimeOutClean);
		//扫描session线程,负责清理超时会话
		sessionManager.setSessionValidationSchedulerEnabled(true);
		//设置session的cookie编号键
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;

	}

	/**
	 * 指定本系统SESSIONID, 默认为: JSESSIONID 问题: 与SERVLET容器名冲突, 如JETTY, TOMCAT 等默认JSESSIONID,
	 * 当跳出SHIRO SERVLET时如ERROR-PAGE容器会为JSESSIONID重新分配值导致登录会话丢失!
	 * @return
	 */
	@Bean(name = "sessionIdCookie")
	public SimpleCookie sessionIdCookie(){
		SimpleCookie simpleCookie = new SimpleCookie("whatlookingfor.session.id");
		return simpleCookie;
	}

	/**
	 * 配置session的存储容器
	 * @return CacheSessionDAO
	 */
	@Bean(name = "sessionDAO")
	public CacheSessionDAO sessionDAO(){
		CacheSessionDAO sessionDAO = new CacheSessionDAO();
//		下面两行设置 用默认即可
//		sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
//		sessionDAO.setActiveSessionsCacheName("activeSessionsCache");
		sessionDAO.setCacheManager(shiroCacheManager());
		return sessionDAO;
	}

	/**
	 * 保证实现了Shiro内部lifecycle函数的bean执行
	 * @return LifecycleBeanPostProcessor
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * AOP式方法级权限检查
	 * @return  DefaultAdvisorAutoProxyCreator
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager());
		return aasa;
	}

	/**
	 * CAS认证过滤器
	 * @return CasFilter
	 */
	@Bean(name = "casFilter")
	public CasFilter casFilter(){
		CasFilter casFilter = new CasFilter();
		casFilter.setFailureUrl(adminPath+"/login");
		return casFilter;
	}

	/**
	 * web的过滤器
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		//  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");// 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
		return filterRegistration;
	}

	/**
	 * shiroFilter shiro过滤器
	 * @return ShiroFilterFactoryBean
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilter.setSecurityManager(securityManager());
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilter.setLoginUrl(adminPath+"/login");
		// 登录成功后要跳转的连接
		shiroFilter.setSuccessUrl(adminPath+"?login");
		shiroFilter.setUnauthorizedUrl("/error/403");
		// 添加casFilter到shiroFilter中
		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("authc",new FormAuthenticationFilter());
		filters.put("cas",casFilter());
		shiroFilter.setFilters(filters);

		Map<String,String> shiroFilterChainDefinitions = Maps.newHashMap();
		shiroFilterChainDefinitions.put("/static/**","anon");
		shiroFilterChainDefinitions.put(adminPath+"/cas","cas");
		shiroFilterChainDefinitions.put(adminPath+"/login","authc");
		shiroFilterChainDefinitions.put(adminPath+"/logout","logout");
		shiroFilterChainDefinitions.put(adminPath+"/**","user");
		shiroFilter.setFilterChainDefinitionMap(shiroFilterChainDefinitions);
		return shiroFilter;
	}




}
