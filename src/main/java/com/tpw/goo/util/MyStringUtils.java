package com.tpw.goo.util;

import java.io.PrintWriter;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 
 * @ClassName: MyStringUtils 
 * @Description: 字符串工具类
 * @author tianpengw 
 * @date 2017年12月7日 下午4:42:37 
 *
 */
public class MyStringUtils extends org.springframework.util.StringUtils{
	
	private static String[] imgs = {"jpg","bmp","png","jpeg"};

	/**
	  * 
	  * @Description: 字符串为null时转换为"",不为null时不作处理
	  * @author tianpengw 
	  * @return String
	  */
	public static String filterStr(String obj){
		if(null == obj || obj.toLowerCase().equals("null")){
			return "";
		}
		return obj;
	}
	
	/**
	  * 
	  * @Description: 判断字符串或者对象是否为null，或者字符串是否为""
	  * @author tianpengw 
	  * @return String
	  */
	public static boolean isEmpty(Object obj){
		if(null == obj || obj.toString().isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	  * 
	  * @Description: 或则指定长度的字符串，如果为空不处理,如果小于定长也不处理
	  * @author tianpengw 
	  * @return String
	  */
	public static String getSpecifyLengthStr(String str,int length){
		if(null == str || isEmpty(str) || str.length() <= length){
			return str;
		}else{
			return str.substring(0, length)+"...";
		}
	}
	
	/**
	 * 
	 * @Description: 处理异常栈信息为字符串
	 * @author tianpengw 
	 * @return String
	 */
	public static String getStackTrace(Throwable t){
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	try{
    		t.printStackTrace(pw);
    		return sw.toString();
    	}finally{
    		pw.close();
    	}
    }
	
	/**
	 * 
	 * @Description: 去掉所有html标签
	 * @author tianpengw 
	 * @return String
	 */
	public static String removeHtml(String content) {
		if(null == content || "".equals(content)){
			return content;
		}
		String txtContent = content.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
		txtContent = txtContent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");// 去除字符串中的空格,回车,换行符,制表符
		txtContent = txtContent.replaceAll("&nbsp;", "");
		txtContent = txtContent.replaceAll("\\s*", "");
		txtContent = txtContent.replaceAll(" ",""); 
		txtContent = txtContent.replace("　", "");
		txtContent = txtContent.trim();
		return txtContent;
	}
	
	public static String replaceHtml(String content) {
		String txtcontent = "";
		if(!MyStringUtils.isEmpty(content)){
			txtcontent = content.replaceAll("</?[^>]+>", " "); // 剔出<html>的标签
//			txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", " ");// 去除字符串中的空格,回车,换行符,制表符

			txtcontent = txtcontent.trim();
		}
		return txtcontent;
	}
	/**
	 * 
	 * @Description: 根据传送的扩展名判断文件类型，上传文件归档目录使用
	 * @author tianpengw 
	 * @return String
	 */
	public static String getFileType(String type){
		String fileType = "file";
		if(!isEmpty(type)){
			for(int i=0;i<imgs.length;i++){
				if(type.endsWith(imgs[i])){
					return "image";
				}
			}
		}
		return fileType;
	}
	

	
	/**
	 * 获取指定字符串出现的次数
	 * 
	 * @param srcText 源字符串
	 * @param findText 要查找的字符串
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {
		
		if(MyStringUtils.isEmpty(srcText) || MyStringUtils.isEmpty(findText)){
			return 0;
		}else{
			int count = 0;
			Pattern p = Pattern.compile(findText);
			Matcher m = p.matcher(srcText);
			while (m.find()) {
				count++;
			}
			return count;
		}
	}
	
	public static String hiddenPhone(String phone){
		if(!isEmpty(phone) && phone.length() == 11){
			return phone.substring(0, 3)+"******"+phone.substring(9);
		}
		return phone;
	}
	
	public static String coventToUTF8(String str) throws UnsupportedEncodingException{
		return new String(str.trim().getBytes("ISO-8859-1"), "utf-8");
	}
	
}
