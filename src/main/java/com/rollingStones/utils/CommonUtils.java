package com.rollingStones.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;

public class CommonUtils {

	public static String SMSURL = "http://openapi.m.hc360.com/openapi/v1/senMsg/";

	public static final int SECONDS_IN_DAY = 60 * 60 * 24;
	public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;
	private static Gson gson = null;

	public static String getNumFromString(String s){
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(s);   
		return m.replaceAll("").trim();
	}
	
	//递归计算阶乘
	public static int factorial(int n){
         if(n<=1){
             return 1;
         }else{
             return n*factorial(n-1);
         }
    }
	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) { 
	    String ipAddress = null; 
	    ipAddress = request.getHeader("x-forwarded-for"); 
	    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
	    	ipAddress = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
	    	ipAddress = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
	       ipAddress = request.getRemoteAddr(); 
	       if (ipAddress.equals("127.0.0.1")) { 
	         // 根据网卡取本机配置的IP 
	         InetAddress inet = null; 
	         try { 
	           inet = InetAddress.getLocalHost(); 
	         } catch (UnknownHostException e) { 
	           e.printStackTrace(); 
	         } 
	         ipAddress = inet.getHostAddress(); 
	       } 

	    } 
	    
	    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 
	    if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15 
	      if (ipAddress.indexOf(",") > 0) { 
	        ipAddress = ipAddress.substring(0, ipAddress.indexOf(",")); 
	      } 
	    } 
	    
	    return ipAddress; 
	  } 

	// 求经纬度之间的距离
	public static double getDistance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378.137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	/*
	 * 不是数字返回true
	 */
	public static boolean noDigital(String n){
		Pattern pattern = Pattern.compile("[0-9]*");
		return StringUtils.isEmpty(n) || !pattern.matcher(n).matches();
	}

	/*
	 * 把对象转成JSON字符串
	 */
	public static String toJSON(Object o){
		if(o == null){
			return null;
		}
		return new Gson().toJson(o);
	}

	/*
	 * 把JSON字符串转成对象
	 */
	public static <T> T fromJSON(String json, Class<T> classOfT){
		if(StringUtils.isEmpty(json)){
			return null;
		}
		return new Gson().fromJson(json, classOfT);
	}

	public static int GetDateToInt() {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyyMMdd");
		String date = format1.format(new Date());
		return Integer.valueOf(date);
	}

	public static String getDateNow() {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format1.format(new Date());
		return date;
	}

	public static String getReturnCallback(Object o, String callback) {
		if (o != null) {
			if (callback != null) {
				return callback + "(" + getGson().toJson(o) + ")";
			} else {
				return getGson().toJson(o);
			}
		} else {
			return "";
		}
	}

	public static String GetDateToString(String source) {
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyyMMdd");
		Date date;
		try {
			date = format1.parse(source);
			return format2.format(date);
		} catch (ParseException e) {
			return null;
		}

	}


	/*
	 * @intro 生成短信验证码
	 */
	public static String getSmsCheckCode(int len) {
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 9; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++)
			result = result * 10 + array[i];
		return String.valueOf(result);
	}

	public static Gson getGson() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}


	/**
	 * @ 判断是否是手机号
	 */
	public static boolean getUserIdByPhoneNum(String phoneNum) {
		return phoneNum.matches("^(13|14|15|17|18)\\d{9}$");
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	/**
	 * 将map中的k-v 按照 k1=v1k2=v2输出字符串
	 */
	public static String mapToString(Map<String, String> map) {
		StringBuffer bf = new StringBuffer();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				bf.append(entry.getKey() + "=" + entry.getValue());
			}
		}
		return bf.toString();
	}

	/**
	 * 获取n以内的随机数
	 * */
	public static int getRandom(int n) {
		return (int) (Math.random() * n)+1;
	}
}

class MapKeyComparator implements Comparator<String> {
	public int compare(String str1, String str2) {
		return str1.compareTo(str2);
	}
}
