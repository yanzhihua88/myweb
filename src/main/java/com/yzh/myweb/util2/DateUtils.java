package com.yzh.myweb.util2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author pero.yan
 *
 */
public class DateUtils {
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String MONTH_FORMAT = "yyyy-MM";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "HH:mm:ss";
	
	/**
	 * 转换日期时间格式
	 * @param sDatetime
	 * @return
	 */
	public static Date convertStringToDateTime(String sDatetime,String format){
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(sDatetime);
		} catch (ParseException e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		return date;
	}
	
	/**
     * 根据给出的Date值和格式串采用操作系统的默认所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param date
     *            日期对象
     * @param formatStr
     *            日期格式
     * @return 如果为null，返回字符串""
     */
    public static String convertDateTimetoString(Date date, String formatStr) {
        String reStr = "";
        if (date == null || formatStr == null || formatStr.trim().length() < 1) {
            return reStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(formatStr);
        reStr = sdf.format(date);
        return reStr == null ? "" : reStr;
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
	 * 
	 * @param count 近count个月
	 * @return
	 */
	public static List<String> getCurrentMonthAndBefore(int count,String month){
		if(StringUtils.isEmpty(month)){
			month = getCurrentMonth();
		}
		Date date = convertStringToDateTime(month,"yyyy-MM");
		List<String> list = new ArrayList<>();
		list.add(month);
		try {
			String smonth;
			for(int i=1; i<=count; i++){
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -i);
				Date specDate = cal.getTime();
				smonth = new SimpleDateFormat(MONTH_FORMAT).format(specDate);
				list.add(smonth);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param count 近count天
	 * @return
	 */
	public static List<String> getCurrentDateAndBefore(int count,String sdate){
		if(StringUtils.isEmpty(sdate)){
			sdate = getCurrentDate();
		}
		Date date1 = convertStringToDateTime(sdate,"yyyy-MM-dd");
		List<String> list = new ArrayList<>();
		list.add(sdate);
		try {
			String sDate;
			for(int i=1; i<=count; i++){
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
				cal.add(Calendar.DATE, -i);
				Date specDate = cal.getTime();
				sDate = new SimpleDateFormat(DATE_FORMAT).format(specDate);
				list.add(sDate);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("日期转换异常");
		}
		
		return list;
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
	
	public static int getDaysBetween(Date maxDate, Date minDate) {
		int count = 0;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(maxDate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(minDate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time1 - time2) / (1000 * 3600 * 24);
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
