package com.tpw.goo.util;

import org.apache.shiro.codec.Base64;

import org.apache.shiro.crypto.hash.Md5Hash;
/**
 * 
 * @ClassName: CryptographyUtil 
 * @Description: 加密工具类
 * @author tianpengw 
 * @date 2017年12月7日 下午4:38:03 
 *
 */
public class CryptographyUtil {
	public static final String SALT = "antFuture";
	/** 
     * base64加密 
     * @param str 
     * @return 
     */  
    public static String encBase64(String str){  
        return Base64.encodeToString(str.getBytes());  
    }  
      
    /** 
     * base64解密 
     * @param str 
     * @return 
     */  
    public static String decBase64(String str){  
        return Base64.decodeToString(str);  
    }  
   
    /**
     * 
     * @Description: Shiro中自带MD5加密 没有salt
     * @author tianpengw 
     * @return String
     */
    public static String md5(String str){  
        return new Md5Hash(str).toString();  
    }  
    /** 
     * @Description: Shiro中自带MD5加密 
     * @param str   要加密的值 
     * @param salt可以看做是拌料 
     * @return 
     */  
    public static String md5(String str,String salt){  
        return new Md5Hash(str,salt).toString();  
    }  
    
    public static void main(String[] args) {
		System.out.println(md5("root2017"));
	}
}
