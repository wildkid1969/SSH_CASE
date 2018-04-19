package com.test.jdk8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CopyOftest {
    public static void main(String[] args) {
        //交易员 1 人名 2 城市
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        //交易记录  1 人名2年份 3 钱
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        
      //(1) 找出2011年发生的所有交易，并按交易额排序(从低到高)。
        System.out.println("1-找出2011年发生的所有交易，并按交易额排序(从低到高)");
        transactions.stream()
                .filter(a -> a.getYear()==2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList())
                .forEach(a -> System.out.println(a.toString()));
       //(2) 交易员都在哪些不同的城市工作过?
        System.out.println("2-交易员都在哪些不同的城市工作过?\n");
         transactions.stream()
                .map(a -> a.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList())
                .forEach(a-> System.out.println(a+","));

       //(3) 查找所有来自于剑桥的交易员，并按姓名排序。
        System.out.println("3-查找所有来自于剑桥的交易员，并按姓名排序。");
        transactions.stream()
                .filter(a -> a.getTrader().getCity().equals("Cambridge"))
                .map(a->a.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList())
                .forEach(a-> System.out.println(a));


       //(4) 返回所有交易员的姓名字符串，按字母顺序排序。
        System.out.println("4-返回所有交易员的姓名字符串，按字母顺序排序。");
        transactions.stream()
                .map(a->a.getTrader().getName())
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList())
                .forEach(a -> System.out.println(a+","));
       //(5) 有没有交易员是在米兰工作的?
        System.out.println("5-有没有交易员是在米兰工作的?");

        Boolean bool = transactions.stream()
                        .anyMatch(a -> a.getTrader().getCity().equals("Milan"));
        System.out.println(bool);
       //(6) 打印生活在剑桥的交易员的所有交易额。
        System.out.println("6-打印生活在剑桥的交易员的所有交易额");
        int num = transactions.stream()
                .filter(a->a.getTrader().getCity().equals("Cambridge"))
                .map(a->a.getValue())
                .reduce(0, (a, b) -> a + b);
        System.out.println("所有交易额的总额是"+num);
       //(7) 所有交易中，最高的交易额 是多少?

        Optional<Integer> max = transactions.stream()
                .map(a -> a.getValue())
                .reduce(Integer::max);
        System.out.println("7-最大"+max);
       //(8) 找到交易额最小的交易。

        Optional<Integer> min = transactions.stream()
                .map(a -> a.getValue())
                .reduce(Integer::min);
        System.out.println("8-最小"+min);
    }
}
