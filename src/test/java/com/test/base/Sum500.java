package com.test.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Sum500 {

	static int[] v = {91,146,28,23,42,206,72,113};
	static int minDiff = 500;
	static final int standart = 500;
	
	static Map<Integer,List<Integer>> pool = new HashMap<Integer,List<Integer>>();
	static List<String> keys = new ArrayList<String>();
	
	public static void main(String[] args) {
		//列出所有组合
		Combine c = new Combine();
		List <ArrayList <Integer>> res = c.useCombine(v);
		
		//以与500的差值为key存入map
		for(ArrayList <Integer> useInt:res) {
			System.out.println(useInt);
			System.out.println(calSum(useInt));
			pool.put(calSum(useInt), useInt);
		}
		
		for(int i=0;i<=500;i++) {
			//从0开始循环，pool第一个包含的就是差值最小的，也就是最接近的组合
			if(pool.containsKey(i)) {
				System.out.println("推荐组合:"+pool.get(i));
				System.out.println("最小差值:"+i);
				break;
			}
		}
	}
	
	//计算每个组合的总和，并求出与500的差值绝对值
	public static int calSum(List<Integer> useInt) {
		int sum = 0;
		for(int i=0;i<useInt.size();i++) {
			sum +=useInt.get(i);
		}
		return Math.abs(sum-standart);
	}
	  
}
