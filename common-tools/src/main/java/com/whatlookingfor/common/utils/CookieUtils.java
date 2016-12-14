/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whatlookingfor.common.utils;

import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * CookieUtils cookie工具类
 *
 * @author Jonathan
 * @version 2016/6/27 15:00
 * @since JDK 7.0+
 */
public class CookieUtils {

	/**
	 * 设置 Cookie（生成时间为1天）
	 *
	 * @param response HttpServletResponse
	 * @param name     名称
	 * @param value    值
	 */
	public static void setCookieValue(HttpServletResponse response, String name, String value) {
		setCookieValue(response, name, value, 60 * 60 * 24);
	}

	/**
	 * 设置 Cookie
	 *
	 * @param response HttpServletResponse
	 * @param name     名称
	 * @param value    值
	 * @param path     路径
	 */
	public static void setCookieValue(HttpServletResponse response, String name, String value, String path) {
		setCookieValue(response, name, value, path, 60 * 60 * 24);
	}

	/**
	 * 设置 Cookie
	 *
	 * @param response HttpServletResponse
	 * @param name     名称
	 * @param value    值
	 * @param maxAge   生存时间（单位秒）
	 */
	public static void setCookieValue(HttpServletResponse response, String name, String value, int maxAge) {
		setCookieValue(response, name, value, "/", maxAge);
	}

	/**
	 * 设置 Cookie
	 *
	 * @param response HttpServletResponse
	 * @param name     名称
	 * @param value    值
	 * @param path     路径
	 * @param maxAge   生存时间(单位秒)
	 */
	public static void setCookieValue(HttpServletResponse response, String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		try {
			cookie.setValue(URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addCookie(cookie);
	}

	/**
	 * 获得指定Cookie的值
	 *
	 * @param request HttpServletRequest
	 * @param name    名称
	 * @return 值
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		return getCookieValue(request, null, name, false);
	}

	/**
	 * 获得指定Cookie的值，并删除。
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name     名称
	 * @return 值
	 */
	public static String getCookieValue(HttpServletRequest request, HttpServletResponse response, String name) {
		return getCookieValue(request, response, name, true);
	}

	/**
	 * 获取cookie对象
	 * @param request 请求对象
	 * @param name cookie的名称
	 * @return cookie对象
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}


	/**
	 * 获取指定cookie的值
	 * @param request 请求对象
	 * @param cookieName cookie的名称
	 * @param defaultValue 缺省值
	 * @return
	 */
	public static final String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue){
		Cookie cookie = org.springframework.web.util.WebUtils.getCookie(request, cookieName);
		if (cookie == null) {
			return defaultValue;
		}
		return cookie.getValue();
	}

	/**
	 * 获得指定Cookie的值
	 *
	 * @param request  请求对象
	 * @param response 响应对象
	 * @param name     名字
	 * @param isRemove 是否移除
	 * @return 值
	 */
	public static String getCookieValue(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
		Assert.notNull(request, "Request must not be null");
		Assert.notNull(name, "name must not be null");
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (isRemove) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		return value;
	}
}
