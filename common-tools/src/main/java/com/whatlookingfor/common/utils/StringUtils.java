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

package com.whatlookingfor.common.utils;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承类{@link org.apache.commons.lang3.StringUtils }
 *
 * @author Jonathan
 * @version 2016/6/23 17:22
 * @since JDK 7.0+
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	public static final String CHARSET = "UTF-8";


	/**
	 * 将字节数组的信息显示出来
	 *
	 * @param data 字节数组
	 * @return 字节信息的toString
	 */
	public static String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}


	/**
	 * 字符串转换为字节编码
	 *
	 * @param string  字符串
	 * @param charset 编码格式
	 * @return 字节编码
	 */
	public static byte[] getBytesDefault(String string, Charset charset) {
		return string == null ? null : string.getBytes(charset);
	}


	/**
	 * 字符串转换为字节数组,默认编码为 utf-8
	 *
	 * @param string
	 * @return 字符串转换的字节编码(utf8)
	 */
	public static byte[] getBytes(String string) {
		return getBytesDefault(string, Charset.forName(CHARSET));
	}


	/**
	 * 字节数组转换为字符串
	 *
	 * @param bytes       字节数组
	 * @param charsetName 编码格式
	 * @return 字符串
	 */
	public static String newString(byte[] bytes, String charsetName) {
		if (bytes == null) {
			return null;
		} else {
			try {
				return new String(bytes, charsetName);
			} catch (UnsupportedEncodingException var3) {
				return EMPTY;
			}
		}
	}


	/**
	 * 字节数组转换为字符串,默认编码 utf8
	 *
	 * @param bytes 待转换的字节数组
	 * @return 转换后的字符串
	 */
	public static String toString(byte[] bytes) {
		return newString(bytes, CHARSET);
	}

	/**
	 * 是否包含字符串
	 *
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}

	/**
	 * 获取客户端IP
	 *
	 * @param request 请求对象
	 * @return ip地址
	 */
	public static final String getHost(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("127.0.0.1".equals(ip)) {
			InetAddress inet = null;
			try { // 根据网卡取本机配置的IP
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ip = inet.getHostAddress();
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 * toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 * toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase("hello_world") == "helloWorld"
	 * toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}


	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 *
	 * @param objectString 对象串
	 *                     例如：row.user.id
	 *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}


	/**
	 * 提供精确的加法运算。
	 *
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 *
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 小数点以后10位，以后的数字四舍五入。
	 *
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2) {
		return div(v1, v2, 3);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 *
	 * @param v1    被除数
	 * @param v2    除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 *
	 * @param v     需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 替换掉HTML标签方法
	 * @param html html源码
	 * @return 替换后的html
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html){
		if (html == null){
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt){
		if (txt == null){
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
