package com.test.base;

public class Person {
    protected String name;
    protected int age;
    protected String sex;
    
    Person(String name){
    	this.name = name;
        System.out.println("Person Constrctor-----" + name);
    }
}
