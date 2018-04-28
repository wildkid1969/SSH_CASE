package com.test.jdk8;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
/**
 * 测试 java8里的方法引用
 * Created by mengya on 2018年4月24日
 *
 */
public class LambdaTest2 {
	public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>> DEST 
				transferElements(SOURCE sourceColletions, Supplier<DEST> colltionFactory) {
        DEST result = colltionFactory.get();
        sourceColletions.forEach(o -> result.add(o));
        return result;
    }

	
	/**
	 * 方法引用是lambda表达式的一种特殊形式，实际上方法引用是lambda表达式的一种语法糖。
	 * 
	 * 语法糖（Syntactic Sugar），也称糖衣语法，指在计算机语言中添加的某种语法，
	 * 这种语法对语言本身功能来说没有什么影响，只是为了方便程序员的开发，提高开发效率。
	 * 说白了，语法糖就是对现有语法的一个封装。
	 * 
	 * Java作为一种与平台无关的高级语言，当然也含有语法糖，这些语法糖并不被虚拟机所支持，在编译成字节码阶段就自动转换成简单常用语法。
	 * 一般来说Java中的语法糖主要有以下几种： 
	 * 1. 泛型与类型擦除 
	 * 2. 自动装箱与拆箱，变长参数、
	 * 3. 增强for循环 
	 * 4. 内部类与枚举类 
	 * 
	 * 方法引用共分为四类：
	 * 1.类名::静态方法名
	 * 2.对象::实例方法名
	 * 3.类名::实例方法名 
	 * 4.类名::new
	 */
	public static void main(String[] args) {
		//1.构造器引用 语法是Class::new。请注意构造器没有参数。比如HashSet::new.
		Function<String,Integer> function = (s) -> new Integer(s);
        System.out.println((function.apply("1234") == 1234));
        
        Function<String,Integer> newFunc = Integer::new;
        System.out.println((newFunc.apply("1234") == 1234));
        
        
        Function<Integer,Integer> intFunc = s -> ++s;
        System.out.println(intFunc.apply(5));
        
        
        //2.特定类的任意对象的方法引用 语法是Class::method。这个方法没有参数
        Function<String,String> strFunc = (s) -> String.valueOf(s);
        System.out.println(strFunc.apply("1688"));
        
        Function<String,String> str = String::valueOf;
        System.out.println(str.apply("1688"));
        
        //3.
        List<String> names = new ArrayList<String>();
        names.add("Mahesh");
        names.add("Suresh");
        names.add("Ramesh");
        names.add("Naresh");
        names.add("Kalpesh");

        names.forEach(System.out::println);
        
        //4.
        String[] array = {"gjyg", "ads", "jk"};
//        Arrays.sort(array, String::compareToIgnoreCase);
        Arrays.sort(array, (a,b) -> a.compareToIgnoreCase(b));

        for (String s : array) {
            System.out.println(s);
        }
        
        //5.
        List<Point> pointList = Lists.newArrayList();
        pointList.add(new Point(1,2));
        pointList.add(new Point(9,4));
        pointList.add(new Point(5,6));
        pointList.add(new Point(7,2));
        
        Collections.sort(pointList, (a,b) -> Double.valueOf(a.getX()).intValue()-Double.valueOf(b.getX()).intValue());
        System.out.println(pointList);
        
        //6.
        final List<Point> personList = Arrays.asList(new Point(1,2),new Point(1,2));
        Set<Point> personSet = transferElements(personList,() -> new HashSet<>());
        System.out.println("personSet:"+personSet);
	}
}
