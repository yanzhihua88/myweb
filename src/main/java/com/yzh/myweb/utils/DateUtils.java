package com.yzh.myweb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 */
public class DateUtils {
	private static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
	private static final String YEAR_FORMAT = "yyyy";
	private static final String MONTH_FORMAT = "yyyyMM";
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final String TIME_FORMAT = "HHmmss";

	/**
	 * 转换日期时间格式
	 * @param sDatetime
	 * @return
	 */
	public static Date convertStringToDate(String sDatetime){
		Date date = null;
		try {
			date = new SimpleDateFormat(DATETIME_FORMAT).parse(sDatetime);
		} catch (ParseException e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		return date;
	}

	/**
	 * 获取当前年
	 * @return
	 */
	public static String getCurrentYear(){
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_FORMAT);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前月份
	 * @return
	 */
	public static String getCurrentMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat(MONTH_FORMAT);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前具体时间
	 * @return
	 */
	public static String getCurrentDatetime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(new Date());
	}

	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取特定范围日期
	 * @param count
	 * @return
	 */
	public static String getTargetDate(int count){
		String sDate;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, count+1);
			Date specDate = cal.getTime();
			sDate = new SimpleDateFormat(DATE_FORMAT).format(specDate);
		} catch (Exception e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		return sDate;
	}
	
	/**
	 * 获取特定范围日期
	 * @param date
	 * @param count
	 * @return
	 */
	public static String getSpecDate(String date, int count){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date specDate;
		String targetDate;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(date));
			cal.add(Calendar.DATE, count);
			specDate = cal.getTime();
			targetDate = sdf.format(specDate);
		} catch (Exception e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		return targetDate;
	}
	
	/**
	 * 计算两个日期之间相差的天数
	 * @param maxDate
	 * @param minDate
	 * @return
	 */
	public static int getDaysBetween(String maxDate, String minDate) {
		int count = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(maxDate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(minDate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			count = Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		return count;
	}
	
	public static String parseBeginDate(String beginDate){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = formatter.parse(beginDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String newStr = formatter.format(date);
		return newStr.replaceAll("-", "");
	}
	
	public static String getEndDate(String beginDate,int count){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date specDate;
		String targetDate;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(beginDate));
			cal.add(Calendar.DATE, count);
			specDate = cal.getTime();
			targetDate = sdf.format(specDate);
		} catch (Exception e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		return parseBeginDate(targetDate);
	}
}
