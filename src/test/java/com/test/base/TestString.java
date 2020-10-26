package com.test.base;

/**
 * 字符串及java堆栈
 * 
 * 1.寄存器：最快的存储区, 由编译器根据需求进行分配,我们在程序中无法控制.
 * 2. 栈：存放基本类型的变量数据和对象的引用，但对象本身不存放在栈中，而是存放在堆（new 出来的对象）或者常量池中（字符串常量对象存放在常量池中。）
 * 3. 堆：存放所有new出来的对象。
 * 4. 静态域：存放静态成员（static定义的）
 * 5. 方法区：存放着类的版本，字段，方法，接口和常量池。
 * 6. 常量池：存放字面量(文本字符串、final常量值、基本数据类型的值和其他)和符号引用(类的全限定名、字段名和描述符、方法名和描述符)。可分为：字符串常量池、class常量池和运行时常量池
 * 7. 非RAM存储：硬盘等永久存储空间
 * 对于 栈和常量池中的对象可以共享，对于堆中的对象不可以共享。
 * 栈中的数据大小和生命周期是可以确定的，当没有引用指向数据时(或者说退出其作用域后)，这个数据就会消失。
 * 堆中的对象的由垃圾回收器负责回收，因此大小和生命周期不需要确定 ，具有很大的灵活性。 
 * 
 * 对于字符串：其对象的引用都是存储在栈中的，
 * 		如果是编译期已经创建好(直接用双引号定义的)的就存储在常量池中，
 * 		如果是运行期（new出来的）才能确定的就存储在堆中 。
 * 对于equals相等的字符串，在常量池中永远只有一份，在堆中有多份。
 */
import com.google.common.collect.Sets;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestString {
	/**
	 * 通过new产生一个字符串（假设为”Hello”）时，会先去常量池中查找是否已经有了”Hello”对象，
	 * 如果没有则在常量池中创建一个此字符串对象，然后堆中再创建一个常量池中此”Hello”对象的拷贝对象。
	 * 所以 new  String( "Hello")产生的对象是：一个或者两个。
	 */
	public static void main(String[] args) throws Exception {
		String s = "Hello"; 
		s = "Java "; 
		String s0 = "Hello"; 
		String s00 = new  String("Hello"); 
		final char[] charArray="Hello".toCharArray();//new了一个新的数组对象
		
		
		String s1=new String("test");//创建2个对象，一个Class和一个堆里面 
		String s2="test";//创建1个对象，s2指向pool里面的"test"对象 
		String s3="test";//创建0个对象,指向s2指想pool里面的那个对象 
		String s4=s2;//创建0个对象,指向s2,s3指想pool里面的那个对象 
		String s5=new String("test");//创建1个对象在堆里面，注意，与s1没关系 

		System.out.println(s2=="test");//true s2=="test"很明显true 
		System.out.println(s2==s3);//true,因为指向的都是pool里面的那个"test" 
		System.out.println(s2==s4);//true,同上,那么s3和s4...:) 
		System.out.println(s1==s5);//false,很明显,false 
		System.out.println(s1==s2);//false,指向的对象不一样,下面再说 
		System.out.println(s1=="test");//false,难道s1!="test"？下面再说 

		System.out.println("---------------"); 

		s1=s2; 
		System.out.println(s1=="test");//true,下面说 
		
		
		String str=null;  
	    str=String.format("Hi,%s", "王力");  
	    System.out.println(str);  
	    str=String.format("Hi,%s:%s.%s", "王南","王力","王张");       
	    System.out.println(str);

	   /**
	    * %06d :  
		* %是格式化输入接受参数的标记  
		* 0格式化命令：结果将用零来填充  
		* 6：填充位数  
		* d：代表十进制 数据  
	    */
	    String fStr = String.format("%06d",154651321);
	    System.out.println(fStr);
	    
	    System.out.println("换行\n了吗???");
	    System.out.println("换行\r了吗");
	    System.out.println("换行\n\r了吗");

	    double a = 0.6;
		System.out.println(a+0.1);

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd 00:00:00");
		System.out.println(sdf.format(new Date()));

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		System.out.println(cal.getTime());

		Date viewStartTime = new Date(1594288800000L);
		Date limitDate = DateUtils.addDays(viewStartTime, 8);
		//当前时间设为0点
		cal.setTime(limitDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		limitDate = cal.getTime();
		System.out.println(limitDate);

		Date limitDateMax = DateUtils.addDays(limitDate, 1);
		long diff = limitDateMax.getTime() - System.currentTimeMillis();
		Long days = diff / (1000 * 60 * 60 * 24);
		System.out.println(days);

		String subject = "小学数学/高中数学/初中数学小学数学/高中数学/初中数学小学数学/高中数学/初中数学";
		if(subject.length() > 5){
			subject = subject.substring(0,5);
		}
		System.out.println(subject);

		String userName = "小张,小王,小李,小刘,小陈,小赵,小李,小李";

		String[] arr = userName.split(",");
		HashSet<String> strings = Sets.newHashSet(arr);
		System.out.println(strings.stream().limit(5).collect(Collectors.joining(",")));

		System.out.println("s".split(",")[0]);

		Pattern p = Pattern.compile(".*\\d+.*");
		System.out.println(p.matcher("2020-01").matches());

		Pattern p1 = Pattern.compile("[^0-9]");
		String id = "https://movie.douban.com/subject/35193180/";
		System.out.println(p1.matcher(id).replaceAll(""));

		System.out.println(System.getProperty("user.dir"));

		//4 0:1 41:1 2:1 3:1 4:1 8:1 5:1 32:1 36:1
		Date date = new Date();
		double aaa = 12.0;
		System.out.println(String.format("%s 0:%s 1:%f","sdf",date,aaa));

		String pub = "邵军 主编 / 安徽美术出版社 / 2017-11 / 49.80元";
		String[] pubArr = pub.split("/");
		System.out.println(pubArr.length);

	}

	public static String removeRepeat(String str) {
		StringBuffer sb = new StringBuffer(str);
		String rs = sb.reverse().toString().replaceAll("(.)(?=.*\\1)", "");
		StringBuffer out = new StringBuffer(rs);
		return out.reverse().toString();
	}

}
