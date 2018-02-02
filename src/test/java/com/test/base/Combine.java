package com.test.base;

import java.util.ArrayList;
import java.util.List;

public class Combine {  
	public Combine(){}
    private ArrayList <Integer>tmpArr = new ArrayList<Integer>();  
    private List <ArrayList <Integer>> res = new ArrayList <ArrayList <Integer>>();  
    public List <ArrayList <Integer>> useCombine(int[] args) {  
        int [] com = args;  
        
        for(int k = 1; k<=com.length;k++) {
	        if(k > com.length || com.length <= 0){  
	            return null;  
	        }  
	        combine(0 ,k ,com);  
        }
        return res;
    }  
    @SuppressWarnings("unchecked")
	public void combine(int index,int k,int []arr) {  
        if(k == 1){  
            for (int i = index; i < arr.length; i++) {  
                tmpArr.add(arr[i]);  
                res.add((ArrayList<Integer>) tmpArr.clone());
                tmpArr.remove((Object)arr[i]);  
            }  
        }else if(k > 1){  
            for (int i = index; i <= arr.length - k; i++) {  
                tmpArr.add(arr[i]);  
                combine(i + 1,k - 1, arr);  
                tmpArr.remove((Object)arr[i]);  
            }  
        }else{  
            return ;   
        }  
    }  
}