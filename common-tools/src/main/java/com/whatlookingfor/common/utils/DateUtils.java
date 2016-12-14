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

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作辅助类
 *
 * @author Jonathan
 * @version 2016/6/27 15:04
 * @since JDK 7.0+
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	private static String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd)
	 *
	 * @param date    日期
	 * @param pattern 格式
	 * @return 自定义格式的日期字符串
	 */
	public static String format(Date date, Object... pattern) {
		if (date == null) {
			return null;
		}
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 获取日期时间字符串,转换格式（yyyy-MM-dd HH:mm:ss）
	 *
	 * @param date 日期
	 * @return 时间字符串, 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取当前时间的日期时间字符串,转换格式（yyyy-MM-dd HH:mm:ss）
	 *
	 * @return 时间字符串, 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
		return getDateTime(new Date());
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 *
	 * @param pattern 格式
	 * @return 自定义格式的日期字符串
	 */
	public static String getDate(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 *
	 * @return yyyy-MM-dd的日期字符串
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}


	/**
	 * 获取当前时间的字符串(HH:mm:ss)
	 *
	 * @return HH:mm:ss格式的时间字符串
	 */
	public static String getTime() {
		return format(new Date(), "HH:mm:ss");
	}


	/**
	 * 获取当前时间的年份
	 *
	 * @return yyyy格式的年份
	 */
	public static String getYear() {
		return format(new Date(), "yyyy");
	}

	/**
	 * 获取当前时间的月份
	 *
	 * @return MM格式的月份
	 */
	public static String getMonth() {
		return format(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 *
	 * @return dd格式的天
	 */
	public static String getDay() {
		return format(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 *
	 * @return 当前星期字符串
	 */
	public static String getWeek() {
		return format(new Date(), "E");
	}


	/**
	 * 日期型字符串转化为日期 格式{@value parsePatterns}
	 * <p>
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 *
	 * @param str 日期型字符串
	 * @return 日期
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取两个时间之间间隔的天数
	 *
	 * @return 间隔的天数
	 */
	public static long pastDays(Date before, Date after) {
		if(after == null){
			after = new Date();
		}
		long milliseconds = after.getTime() - before.getTime();
		return milliseconds / (1000 * 60 * 60 * 24);
	}

	public static long pastHour(Date before, Date after) {
		if(after == null){
			after = new Date();
		}
		long milliseconds = after.getTime() - before.getTime();
		return milliseconds / (1000 * 60 * 60);
	}

	public static long pastMinutes(Date before, Date after) {
		if(after == null){
			after = new Date();
		}
		long milliseconds = after.getTime() - before.getTime();
		return milliseconds / (1000 * 60);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 *
	 * @param date 日期
	 * @return 天, 时:分:秒.毫秒）的字符串
	 */
	public static String formatDateTime(Date date) {
		long timeMillis = date.getTime();
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}


	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
	}

	/**
	 * 日期的计算
	 *
	 * @param date   日期
	 * @param field  计算项,参考Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_YEAR 等
	 * @param amount 计算的值
	 * @return 计算后的日期
	 */
	public static Date addDate(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

}
