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

package com.whatlookingfor.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器基类
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:31
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	protected static Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);
	private BaseInterceptor[] nextInterceptor;

	public void setNextInterceptor(BaseInterceptor... nextInterceptor) {
		this.nextInterceptor = nextInterceptor;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (ObjectUtils.isEmpty(nextInterceptor)) {
			return true;
		}
		for (int i = 0; i < nextInterceptor.length; i++) {
			if (!nextInterceptor[i].preHandle(request, response, handler)) {
				return false;
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		if (!ObjectUtils.isEmpty(nextInterceptor)) {
			for (int i = nextInterceptor.length - 1; i >= 0; i--) {
				nextInterceptor[i].postHandle(request, response, handler, modelAndView);
			}
		}
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (!ObjectUtils.isEmpty(nextInterceptor)) {
			for (int i = nextInterceptor.length - 1; i >= 0; i--) {
				nextInterceptor[i].afterCompletion(request, response, handler, ex);
			}
		}
	}

	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!ObjectUtils.isEmpty(nextInterceptor)) {
			for (int i = nextInterceptor.length - 1; i >= 0; i--) {
				nextInterceptor[i].afterConcurrentHandlingStarted(request, response, handler);
			}
		}
	}
}
