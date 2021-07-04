package com.test.base;

import java.util.Scanner;

/**
 * @Author: mengya
 * @Date: 2021/4/14 16:17
 */
public class TestNumber {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入size：");

        while (true) {
            int total = 199;
            int size = 10;
            int preferenceCount = s.nextInt();

            double lastTotal = 0;
            double every = BigDecimalUtil.divide(total, size, 2);

            for(int i=0;i<size;i++){
                if(i==0){
                    double firstBig = BigDecimalUtil.subtract(total, BigDecimalUtil.multiply((size - 1), every));

                    lastTotal = BigDecimalUtil.add(lastTotal, firstBig);
                    System.out.println(firstBig);
                }else{
                    lastTotal = BigDecimalUtil.add(lastTotal, every);
                    System.out.println(every);
                }
            }

            System.out.println(lastTotal);

        }

    }
}
