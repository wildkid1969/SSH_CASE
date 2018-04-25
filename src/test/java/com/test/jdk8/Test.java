package com.test.jdk8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public class Test {
    @SuppressWarnings("unused")
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
        List<Transaction> transactions1 =  transactions.stream()
                .filter(a -> a.getYear()==2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(transactions1);
       //(2) 交易员都在哪些不同的城市工作过?
        Set<String> distinctCities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(Collectors.toSet());   // or .distinct().collect(Collectors.toList())
        System.out.println(distinctCities);
       //(3) 查找所有来自于剑桥的交易员，并按姓名排序。
        Trader[] traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .toArray(Trader[]::new);
        System.out.println(Arrays.toString(traders));
       //(4) 返回所有交易员的姓名字符串，按字母顺序排序。
        String names = transactions.stream()
        		.map(transaction -> transaction.getTrader().getName())
        		.distinct()
        		.sorted(Comparator.naturalOrder())
        		.reduce("", (str1, str2) -> str1 + " " + str2);
        System.out.println(names);
       //(5) 有没有交易员是在米兰工作的?
        boolean workMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(workMilan);
       //(6) 打印生活在剑桥的交易员的所有交易额。
        long sum = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .mapToLong(Transaction::getValue)
                .sum();
        System.out.println(sum);
       //(7) 所有交易中，最高的交易额是多少?
        OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        // or transactions.stream().map(Transaction::getValue).max(Comparator.naturalOrder());
        System.out.println(max.orElse(0));
       //(8) 找到交易额最小的交易。
        Optional<Transaction> min = transactions.stream()
                .min(Comparator.comparingInt(Transaction::getValue));
        System.out.println(min.orElseThrow(IllegalArgumentException::new));
        
        
        List<Integer> nums = Lists.newArrayList(1,null,3,4,null,6);
        
        int count1 = 0;
        for(Integer num : nums){
            if(num != null){
                count1++;
            }
        }
        System.out.println(count1);//4
        
        long count2 = nums.stream()
            .filter(num -> num != null)
            .count();
        System.out.println(count2);//4
        
        List<String> words = Lists.newArrayList("Apple","Bug","Abc","Dog");
        Stream<String> stream = words.stream();
        
        
        OptionalInt maxLength= words.stream() 
                .filter(s -> s.startsWith("A")) 
                .mapToInt(String::length) 
                .max();
        
        System.out.println(maxLength.orElse(0));//return isPresent ? value : other;

        // 1.使用Stream静态方法来创建Stream
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 5);
        Stream<String> stringStream = Stream.of("taobao");
        
        // 2.通过Collection子类获取Stream
        
    }
}
