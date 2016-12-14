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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 * 项目启动的监听处理类
 *
 * @author Jonathan
 * @version 2016/5/19 16:18
 * @since JDK 7.0+
 */
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

	protected Logger logger = LoggerFactory.getLogger(StartUpListener.class);
	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		if (evt.getApplicationContext().getParent() == null) {
			init();
		}
	}

	private void init() {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用   - Powered By whatlookingfor@gmail.com\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		//判断是否清除缓存
		logger.info("启动时先清除缓存");

	}





}
