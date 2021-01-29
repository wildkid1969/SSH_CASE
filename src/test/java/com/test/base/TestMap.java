package com.test.base;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestMap {
    public static void main(String[] args) {
        Map<String,String> map = Maps.newTreeMap();
        map.put("2020-07-09", "1");
        map.put("2020-07-06", "1");
        map.put("2020-07-03", "1");
        map.put("2020-07-11", "1");

        Map<String, String> sortMap = new TreeMap<>(String::compareTo);
        sortMap.putAll(map);

        System.out.println(sortMap);

        int x = 5;
        System.out.println(1 / (1 + Math.exp(-x)));

        List<Integer> list = Lists.newArrayList();
        for(int i=1;i<=200;i++){
            list.add(i);
        }

        Integer businessId=5775;
        Integer courseId=5775;
        System.out.println(businessId==courseId.intValue());

    }
}
