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

package com.whatlookingfor.core.config.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * mybatis热部署的配置参数
 *
 * @author Jonathan
 * @version 2016/5/19 18:32
 * @since JDK 7.0+
 */
@ConfigurationProperties(prefix = "mybatisRefresh")
public class MybatisRefreshProperties implements Serializable{


	private static final long serialVersionUID = -4664996476409093729L;

	private boolean enabled;

	private int delaySeconds;

	private int sleepSeconds;

	private String mappingPath;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getDelaySeconds() {
		return delaySeconds;
	}

	public void setDelaySeconds(int delaySeconds) {
		this.delaySeconds = delaySeconds;
	}

	public int getSleepSeconds() {
		return sleepSeconds;
	}

	public void setSleepSeconds(int sleepSeconds) {
		this.sleepSeconds = sleepSeconds;
	}

	public String getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	@Override
	public String toString() {
		return "MybatisRefresh{" +
				"enabled=" + enabled +
				", delaySeconds=" + delaySeconds +
				", sleepSeconds=" + sleepSeconds +
				", mappingPath='" + mappingPath + '\'' +
				'}';
	}
}
