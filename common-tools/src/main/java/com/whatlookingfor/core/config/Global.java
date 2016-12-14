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
import com.whatlookingfor.common.utils.PropertiesLoader;
import com.whatlookingfor.common.utils.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 全局对象配置类
 * 单例模式
 *
 * @author Jonathan
 * @version 2016/6/2 10:26
 * @since JDK 7.0+
 */
public class Global {

	/**
	 * 利用静态变量来记录Global类的唯一实例
	 */
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("application.properties");

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	public static final int YES_INT = 1;

	public static final int NO_INT = 0;

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	/**
	 * 根据key值获取properties中的value值
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}


	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("spring.mvc.view.prefix");
	}

	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}


	/**
	 * 页面获取常量
	 *
	 * @see {fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * TODO 重写
	 * 获取上传文件的根目录
	 * @return
	 */
//	public static String getUserfilesBaseDir() {
//		String dir = getConfig("userfiles.basedir");
//		if (StringUtils.isBlank(dir)){
//			try {
//				dir = ServletContextFactory.getServletContext().getRealPath("/");
//			} catch (Exception e) {
//				return "";
//			}
//		}
//		if(!dir.endsWith("/")) {
//			dir += "/";
//		}
//		return dir;
//	}

	/**
	 * 获取工程路径
	 *
	 * @return
	 */
	public static String getProjectPath() {
		// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)) {
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null) {
				while (true) {
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()) {
						break;
					}
					if (file.getParentFile() != null) {
						file = file.getParentFile();
					} else {
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
	}

}
