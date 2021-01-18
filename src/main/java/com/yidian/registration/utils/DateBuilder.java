package com.yidian.registration.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用日期处理类
 * update 2017-08-22代码规范整理 Created by jintian
 */
public class DateBuilder {

	private static DateBuilder instance;
	static Logger logger = LoggerFactory.getLogger(DateBuilder.class);

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	/*********************************** new begin ***************************************/

	/**
	 * 格式化字符串-日期天开始时刻
	 */
	public static final String FORMAT_DAY_BEGIN = "yyyy-MM-dd 00:00:00";
	/**
	 * 格式化字符串-日期月开始时刻
	 */
	public static final String FORMAT_MONTH_BEGIN = "yyyy-MM-01 00:00:00";
	/**
	 * 格式化字符串-日期天结束时刻
	 */
	public static final String FORMAT_DAY_END = "yyyy-MM-dd 23:59:59";
	/**
	 * 格式化字符串-日期时刻到秒
	 */
	public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 格式化字符串-日期时刻到分钟
	 */
	public static final String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
	/**
	 * 格式化字符串-汉化上下午 小时分钟(K am/pm 中的小时数（0-11） )
	 */
	public static final String FORMAT_CN_MINUTE = "aKK:mm";
	/**
	 * 格式化字符串-汉化上下午 小时分钟(h am/pm 中的小时数（1-12） )
	 */
	public static final String FORMAT_CN_MINUTE2 = "ahh:mm";
	/**
	 * 格式化字符串-汉化月日
	 */
	public static final String FORMAT_CN_MD = "MM月dd日";

	/**
	 * 格式化字符串-小时分钟
	 */
	public static final String FORMAT_HM = "HH:mm";

	/**
	 * 格式化 年月
	 */
	public static final String FORMAT_YM="yyyyMM";
    /**
     * 格式化字符串-小时分钟
     */
    public static final String FORMAT_MDHM= "MM月dd日 HH:mm";

	private DateBuilder() {
	}


	public static DateBuilder getInstance() {
		if (instance == null) {
			instance = new DateBuilder();
		}
		return instance;
	}

	public static Date getinitDate(){
		return getDate(FORMAT_DAY_BEGIN,"1970-01-01 00:00:00");
	}

	public static Date getDate(String sFormat, String date) {
		if (date == null || "".equals(date)) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}



	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_FULL);
		return format.format(date);

	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDate(String fm) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(fm);
		return format.format(date);

	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateByLongTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_FULL);
		return format.format(date);
	}


	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static int getMinute() {
		int min = 0;
		try {
			Calendar calendar = Calendar.getInstance();
			min = calendar.get(Calendar.MINUTE);
		}catch (Exception e){
			logger.error("得到现在分钟数异常error{}",e);
		}
		return min;
	}


	/**
	 * 取得系统当前时间后n天
	 * 
	 * @param day
	 *            int
	 * @return String yyyy-mm-dd
	 */
	public static String getDayAfterCurrentDate(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, day);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE)));
	}

	public static Date getDateTimeAfterCurrentDate(int day) throws ParseException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String newDate = getDayAfterCurrentDate(day);
		String time = format.format(date);
		SimpleDateFormat formatTime = new SimpleDateFormat(FORMAT_FULL);
		return formatTime.parse(newDate + " " + time);
	}



	/**
	 * 将一个日期字符串转化成日期
	 * 
	 * @param strDate
	 *            String
	 * @return Date yyyy-mm-dd
	 */
	public static Date convertStringToDate(String strDate) {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			date = df.parse(strDate);
		} catch (Exception e) {
			logger.debug("日期转换失败:", e);
		}
		return date;
	}

	public static Date convertStringToDate(String strDate, String fm) {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(fm);
			date = df.parse(strDate);

		} catch (Exception e) {
			logger.debug("日期转换失败:", e);
		}
		return date;
	}

	public static Date convertStringToDate2(String strDate) {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = df.parse(strDate);

		} catch (Exception e) {
			logger.debug("日期转换失败:", e);
		}
		return date;
	}



	/**
	 * 将一个日期字符串转化成Calendar
	 * 
	 * @param strDate
	 *            String
	 * @return Calendar
	 */
	public static Calendar convertDateStringToCalendar(String strDate) {
		Date date = convertStringToDate(strDate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}



	/**
	 * 将日期转化为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
		return dateFormat.format(date);
	}

	public static String convertDateToString(Date date, String fm) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(fm);
		return dateFormat.format(date);
	}



	/**
	 * 将日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式字符串
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return "";
		if (StringUtils.isBlank(pattern)) {
			pattern = YYYY_MM_DD;
		}
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(pattern);
		} catch (Exception e) {
			logger.error("时间格式转化异常error{}",e);
		}
		return dateFormat.format(date);
	}


	public static String addHour(String strDate, int i) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULL);
		Date date = sdf.parse(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + i);
		return sdf.format(cal.getTime());
	}



	/**
	 * 得到日期天开始时刻字符串
	 * 
	 * @param date
	 *            日期实例
	 * @return 格式化字符串 ("yyyy-MM-dd 00:00:00")
	 */
	public static String toDayBeginStr(Date date) {
		return new SimpleDateFormat(FORMAT_DAY_BEGIN).format(date);
	}

	/**
	 * 得到日期天结束时刻字符串
	 * 
	 * @param date
	 *            日期实例
	 * @return 格式化字符串 ("yyyy-MM-dd 23:59:59")
	 */
	public static String toDayEndStr(Date date) {
		return new SimpleDateFormat(FORMAT_DAY_END).format(date);
	}

	/**
	 * 得到日期天结束时刻字符串
	 * 
	 * @param date
	 *            日期实例
	 * @return 格式化字符串 ("yyyy-MM-01 23:59:59")
	 */
	public static String toMonthBeginStr(Date date) {
		return new SimpleDateFormat(FORMAT_MONTH_BEGIN).format(date);
	}



	/**
	 * 日期天数增加
	 * 
	 * @param date
	 *            日期实例
	 * @param days
	 *            增加的天数
	 * @return 增加后的日期实例
	 */
	public static Date addDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	 
	/**
	 * 转化字符串到日期实例
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            格式字符串
	 * @return 日期实例
	 * @throws ParseException
	 *             转化异常
	 */
	public static Date toDate(String dateStr, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateStr);
	}

	/**
	 * 转化字符串到日期实例
	 * 
	 * @param dateStr
	 *            字符串格式(yyyy-MM-dd HH:mm:ss)
	 * @return 日期实例
	 * @throws ParseException
	 *             转化异常
	 */
	public static Date toDate(String dateStr) throws ParseException {
		return toDate(dateStr, FORMAT_FULL);
	}
	/**
	 * 
	 * @Title: toDateForMatYM 
	 * @Description: 格式化日期 年月 如 201506 
	 * @param 
	 * @throws ParseException   
	 * @return yyyyMM    
	 */
    public static String toDateStrForMatYM(Date date) throws ParseException {
		return new SimpleDateFormat(FORMAT_YM).format(date);
	}
    
	/**
	 * 得到日期标准字符串
	 * 
	 * @param date
	 *            日期实例
	 * @return 格式化字符串 (yyyy-MM-dd HH:mm:ss)
	 */
	public static String toStr(Date date) {
		return new SimpleDateFormat(FORMAT_FULL).format(date);
	}

	/**
	 * 得到特定格式的日期字符串
	 * 
	 * @param date
	 *            日期实例
	 * @param format
	 *            特定格式
	 * @return 指定字符串
	 */
	public static String toStr(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 调整日期
	 * 
	 * @param date
	 *            日期实例
	 * @param field
	 *            单位
	 * @param amount
	 *            数量
	 * @return 日期实例
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return c.getTime();
	}


	/**
	 * 加/减小时
	 * 
	 * @param date
	 *            日期实例
	 * @param amount
	 *            数量
	 * @return 日期实例
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	/**
	 * 加/减分钟
	 * 
	 * @param date
	 *            日期实例
	 * @param amount
	 *            数量
	 * @return 日期实例
	 */
	public static Date addMin(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 日期月数增加
	 * 
	 * @param date
	 *            日期实例
	 * @param amount
	 *            增加的月数
	 * @return 增加后的日期实例
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 系统定制显示（今天|明天|MM月 上午|下午 hh:mm）
	 * 
	 * @param date
	 *            日期实例
	 * @return 特定字符串
	 */
	public static String getCustomSerTime(Date date) {
		String re = "";
		try {
			Date now = new Date();
			Date todayBegin = DateBuilder.toDate(DateBuilder.toDayBeginStr(now));
			Date todayEnd = DateBuilder.toDate(DateBuilder.toDayEndStr(now));
			Date tomorrowEnd = DateBuilder.toDate(DateBuilder.toDayEndStr(DateBuilder.addDay(now, 1)));
			if (date.after(todayBegin) && date.before(todayEnd)) {
				re = "今天";
			} else if (date.after(todayEnd) && date.before(tomorrowEnd)) {
				re = "明天";
			} else {
				re = DateBuilder.toStr(date, DateBuilder.FORMAT_CN_MD) + " ";
			}
			Calendar calDate = Calendar.getInstance();
			calDate.setTime(date);

			if (calDate.get(Calendar.HOUR_OF_DAY) == 12) {
				re += DateBuilder.toStr(date, DateBuilder.FORMAT_CN_MINUTE2);
			} else {
				re += DateBuilder.toStr(date, DateBuilder.FORMAT_CN_MINUTE);
			}

		} catch (Exception e) {
			logger.error("得到定制服务时间出错", e);
		}
		return re;
	}
	/**
	 * 得到日期标准字符串
	 *
	 * @param date
	 *            日期实例
	 * @return 格式化字符串 (HH:mm)
	 */
	public static String toStrHourMin(Date date) {
		return new SimpleDateFormat(FORMAT_MDHM).format(date);
	}
}
