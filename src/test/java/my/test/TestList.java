package my.test;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

/**
 * @Author: mengya
 * @Date: 2020-09-24 19:09
 */
public class TestList {
    public static void main(String[] args) {
        List<Integer> list1 = Lists.newArrayList(1,4,6,7);
        List<Integer> list2 = Lists.newArrayList(11,4,61,17);
        List<Integer> list3 = Lists.newArrayList();

        System.out.println(list3.isEmpty());
        Integer minValue = list1.stream().min(Comparator.comparing(Integer::longValue)).get();
        System.out.println(minValue);

        list1.retainAll(list2);
        System.out.println(list1.isEmpty());


    }
}
