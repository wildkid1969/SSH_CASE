package com.test.base;

/**
 * 继承是使用已存在的类的定义作为基础建立新类的技术，新类的定义可以增加新的数据或新的功能，也可以用父类的功能，但不能选择性地继承父类。
 * 通过使用继承我们能够非常方便地复用以前的代码，能够大大的提高开发的效率。
 * 
 * 慎用继承！！
 * 
 * 《Think in java》中提供了解决办法：
 * 问一问自己是否需要从子类向父类进行向上转型。如果必须向上转型，则继承是必要的，
 * 但是如果不需要，则应当好好考虑自己是否需要继承。
 * 
 * Created on 2018年8月20日
 *
 */
public class Husband extends Person{

	/**
	 * 对于继承而言，子类会默认调用父类的构造器，
	 * 但是如果没有默认的父类构造器，子类必须要显示的指定父类的构造器，而且必须是在子类构造器中做的第一件事(第一行代码)
	 */
    Husband(){
        super("chenssy");
        System.out.println("Husband Constructor...");
    }
    
    public static void main(String[] args) {
        Husband husband  = new Husband();
        System.out.println(husband.name);
    }
}

