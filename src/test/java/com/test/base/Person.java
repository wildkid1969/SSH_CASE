package com.test.base;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {
    private Integer id;
    protected String name;
    protected int age;
    protected String sex;
    
    Person(String name){
    	this.name = name;
        System.out.println("Person Constrctor-----" + name);
    }

    Person(Integer id, String name){
        this.id = id;
        this.name = name;
        System.out.println("Person Constrctor2-----" + name);
    }
}
