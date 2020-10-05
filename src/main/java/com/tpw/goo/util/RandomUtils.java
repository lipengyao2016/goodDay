package com.tpw.goo.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;
/**
 * 
 * @ClassName: RandomUtils 
 * @Description: 随机数生成工具，生成数据库的唯一主键
 * @author tianpengw 
 * @date 2017年5月4日 下午5:58:27 
 *
 */
public class RandomUtils {

	/** The FieldPosition. */
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

	/** This Format for format the data to special format. */
	private final static Format dateFormat = new SimpleDateFormat("YYMMddHHmmss");

	/** This Format for format the number to special format. */
	private final static NumberFormat numberFormat = new DecimalFormat("0000");

	/** This int is the sequence number ,the default value is 0. */
	private static int seq = 0;

	private static final int MAX = 9999;
	/**
	 * 去掉4的1-9数字数组，验证码生成使用
	 */
	private static String[] DIGIT_ARG = {"0","1","2","3","5","6","7","8","9"};

	/**
	 * 
	  * @Description: TODO 生成固定长度为16位的包含时间的序列  YYMMDDHHmmss+四位随机数
	  * @param @return  
	  * @return String
	  * @throws
	 */
    public static synchronized String g() {
    	Calendar rightNow = Calendar.getInstance();
        StringBuffer sbf = new StringBuffer();
        dateFormat.format(rightNow.getTime(), sbf, HELPER_POSITION);
        numberFormat.format(seq, sbf, HELPER_POSITION);
        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }
        return sbf.toString();
    }
    
    /**
     *   
     * @Description: 获得一个UUID
     * @author tianpengw 
     * @return String
     */
    public static String getUUID(){  
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);  
    }  
    
    /**
     * 
     * @Description: 获取指定位数的数字验证码（去掉4）
     * @author tianpengw 
     * @return String
     */
    public static String getValidCode(int digit){
    	StringBuffer  sbf = new StringBuffer();
    	Random r = new Random();
    	for(int i=0;i<digit;i++){
    		int index = r.nextInt(9);
    		sbf.append(DIGIT_ARG[index]);
    	}
    	return sbf.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(getUUID());
	}
}
