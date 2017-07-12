package com.test.base;

public class TestString {
	public static void main(String[] args) {
		String str=null;  
	    str=String.format("Hi,%s", "王力");  
	    System.out.println(str);  
	    str=String.format("Hi,%s:%s.%s", "王南","王力","王张");       
	    System.out.println(str);
	    
	   /*
	    * %06d :  
		* %是格式化输入接受参数的标记  
		* 0格式化命令：结果将用零来填充  
		* 6：填充位数  
		* d：代表十进制 数据  
	    */
	    String s = String.format("%06d",154651321);
	    System.out.println(s);
	}
}
