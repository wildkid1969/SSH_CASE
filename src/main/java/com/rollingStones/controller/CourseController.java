package com.rollingStones.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;

@Controller
public class CourseController {

	public static void main(String[] args) {
		//存储100个数字
		List<Integer> list= new ArrayList<Integer>();
		//Set无序，不可重复，存储最终的集合
		Set<Integer> set = new HashSet<Integer>();
		
		Random random = new Random();
		for(int i=0;i<100;i++){
			list.add(random.nextInt(100));
		}
		
		//添加集合，Set自动去重，不能用set.add方法
		set.addAll(list);
		
		List<Integer> finalList= new ArrayList<Integer>();
		finalList.addAll(set);
		
		//排序，从小到大
		Collections.sort(finalList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);//从大到小就是：o2.compareTo(o1)
			}
		});
		
		
		
		System.out.println(finalList);
		System.out.println(finalList.size());
	}
	
}
