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


import com.whatlookingfor.core.Constants;
import com.whatlookingfor.core.support.HttpCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 恶意请求拦截器
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:57
 */
public class MaliciousRequestInterceptor extends BaseInterceptor {
	private Boolean allRequest = false; // 拦截所有请求,否则拦截相同请求(false=拦截相同请求)
	private Long minRequestIntervalTime; // 允许的最小请求间隔
	private Integer maxMaliciousTimes; // 允许的最大恶意请求次数

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String preRequest = (String) session.getAttribute(Constants.PREREQUEST);
		Long preRequestTime = (Long) session.getAttribute(Constants.PREREQUEST_TIME);
		String url = request.getServletPath();
		if (preRequestTime != null && preRequest != null) { // 过滤频繁操作
			if ((url.equals(preRequest) || allRequest)
					&& System.currentTimeMillis() - preRequestTime < minRequestIntervalTime) {
				Integer maliciousRequestTimes = (Integer) session.getAttribute(Constants.MALICIOUS_REQUEST_TIMES);
				if (maliciousRequestTimes == null) {
					maliciousRequestTimes = 1;
				} else {
					maliciousRequestTimes++;
				}
				session.setAttribute(Constants.MALICIOUS_REQUEST_TIMES, maliciousRequestTimes);
				if (maliciousRequestTimes > maxMaliciousTimes) {
					response.setStatus(HttpCode.MULTI_STATUS.value());
					logger.warn("To intercept a malicious request : {}", url);
					return false;
				}
			} else {
				session.setAttribute(Constants.MALICIOUS_REQUEST_TIMES, 0);
			}
		}
		session.setAttribute(Constants.PREREQUEST, url);
		session.setAttribute(Constants.PREREQUEST_TIME, System.currentTimeMillis());
		return super.preHandle(request, response, handler);
	}

	public void setAllRequest(Boolean allRequest) {
		this.allRequest = allRequest;
	}

	public void setMinRequestIntervalTime(Long minRequestIntervalTime) {
		this.minRequestIntervalTime = minRequestIntervalTime;
	}

	public void setMaxMaliciousTimes(Integer maxMaliciousTimes) {
		this.maxMaliciousTimes = maxMaliciousTimes;
	}
}
