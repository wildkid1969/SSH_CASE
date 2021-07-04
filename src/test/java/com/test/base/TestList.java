package com.test.base;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * @Author: mengya
 * @Date: 2021/3/15 15:48
 */
public class TestList {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList();

        for(int i=0;i<3;i++){
            list.add(i);
        }

        forEachList(list);

        List<Integer> list1 = Lists.newArrayList(1,2,3,37899);
        Set<Integer> set = Sets.newHashSet(2,3,4,5);
        Set<Integer> finalSet = Sets.newHashSet(6,7,8);
        set.removeAll(list1);
        System.out.println(set);

        finalSet.addAll(set);
        System.out.println(finalSet);

        Integer userId = 37899;
        System.out.println(list1.contains(userId));
    }

    public static void forEachList(List list) {
        int listSize = list.size();
        int pageSize = 10;
        for (int i = 0; i < list.size(); i += pageSize) {
            //作用为toIndex最后没有900条数据则剩余几条newList中就装几条
            if (i + pageSize > listSize) {
                pageSize = listSize - i;
            }

            List newList = list.subList(i, i + pageSize);
            System.out.println(newList);
        }
    }
}
