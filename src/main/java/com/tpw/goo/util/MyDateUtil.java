package com.tpw.goo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/** 
 * 
 * @ClassName: MyDateUtil 
 * @Description: 日期工具类
 * @author tianpengw 
 * @date 2017年12月7日 下午4:39:50 
 *
 */
public class MyDateUtil {
	
	public static String DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static String DATETIME1 = "yyyy/MM/dd HH:mm:ss";
	public static String DATETIME2 = "yyyyMMddHHmmss";
	public static String DATE = "yyyy-MM-dd";
	public static String DATE1 = "yyyyMMdd";
	public static long nd = 1000 * 24 * 60 * 60;
	public static long nh = 1000 * 60 * 60;
	public static long nm = 1000 * 60;

	/**
	 * 
	 * @Description: 日期转换指定格式的字符串
	 * @author tianpengw 
	 * @return String
	 */
	public static String formatDate(Date date, String pattern){
		try {
			if(null != date){
				pattern = (null == pattern || pattern.isEmpty() ? DATETIME : pattern);
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				return sdf.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 指定格式的字符串转换日期
	 * @author tianpengw 
	 * @return String
	 */
	public static Date parseDate(String dateStr, String pattern){
		try {
			if(!MyStringUtils.isEmpty(dateStr)){
				pattern = (null == pattern || pattern.isEmpty() ? DATETIME : pattern);
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				return sdf.parse(dateStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 获得一年以后的今天
	 * @author tianpengw 
	 * @return Date
	 */
	public static Date getTomorrowYearofToday(){
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description: 获得30分钟的时间字符串
	 * @author tianpengw 
	 * @param d
	 * @return
	 */
	public static String get30Minute(Date d){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE, 30);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return format.format(calendar.getTime());
	}
	
	/**
	 * 
	 * @Description: 根据生日获得年龄
	 * @author tianpengw 
	 * @return int
	 */
	public static int getAge(Date birthDay) throws Exception { 
        Calendar cal = Calendar.getInstance(); 
  
        if (cal.before(birthDay)) { 
            throw new IllegalArgumentException("出生日期大于当前日期，不可用！"); 
        } 
        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH); 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
        cal.setTime(birthDay); 
  
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
  
        int age = yearNow - yearBirth; 
  
        if (monthNow <= monthBirth) { 
            if (monthNow == monthBirth) { 
                if (dayOfMonthNow < dayOfMonthBirth) age--; 
            }else{ 
                age--; 
            } 
        } 
        return age; 
    }
	
	public static long getDateDiff(Date date){
		
		long diff = System.currentTimeMillis() - date.getTime();
		long min = diff / nm;
		return min;
		
	}
}
