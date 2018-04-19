package com.test.jdk8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

public class Copy_2_of_test {
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
        List<Transaction> list = transactions.stream()
        		.filter(transaction -> transaction.getYear()==2011)
        		.sorted(Comparator.comparing(Transaction :: getValue))
        		.collect(Collectors.toList());
        System.out.println("1:"+list);
       //(2) 交易员都在哪些不同的城市工作过?
        Set<String> cities = transactions.stream()
        		.map(transaction -> transaction.getTrader().getCity())
        		.collect(Collectors.toSet());
        System.out.println("2:"+cities);
       //(3) 查找所有来自于剑桥的交易员，并按姓名排序。
        List<Trader> userSet = transactions.stream()
        		.filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
        		.map(Transaction::getTrader)
        		.sorted(Comparator.comparing(Trader::getName))
        		.distinct()
        		.collect(Collectors.toList());//有排序并且有去重的，最好不要用toSet()，否则会无序输出
        System.out.println("3:"+userSet);
       //(4) 返回所有交易员的姓名字符串，按字母顺序排序。
        List<String> userList = transactions.stream()
        		.map(transaction -> transaction.getTrader().getName())
        		.distinct()   //.collect(Collectors.toSet());
        		.sorted(Comparator.comparing(String::toString))  //Comparator.naturalOrder()
        		.collect(Collectors.toList());
        System.out.println("4:"+userList);
       //(5) 有没有交易员是在米兰工作的?
        boolean hasWork = transactions.stream()
        		.anyMatch((Transaction transaction) -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println("5:"+hasWork);
       //(6) 打印生活在剑桥的交易员的所有交易额。
        int total = transactions.stream()
        		.filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
        		.mapToInt(Transaction::getValue)
        		.sum();
        System.out.println("6:"+total);		
       //(7) 所有交易中，最高的交易额是多少?
        OptionalInt max = transactions.stream()
        		.mapToInt(Transaction::getValue)
        		.max();
        System.out.println("7:"+max.orElse(0));		
       //(8) 找到交易额最小的交易。
        Optional<Transaction> min = transactions.stream()
        		.min(Comparator.comparing(Transaction::getValue));
        System.out.println("8:"+min.orElseThrow(IllegalArgumentException::new));
        
    }
}
