package com.test.jdk8;

import com.google.common.collect.Lists;
import com.rollingStones.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TestStream {
    public static void main(String[] args) throws ClassNotFoundException {
        //交易员 1 人名 2 城市
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        //交易记录  1 人名2年份 3 钱
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2013, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2015, 700),
                new Transaction(alan, 2017, 950)
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
        		.map(transaction -> transaction.getTrader().getName())//中间操作-Stream转换 所有的中间操作都会返回另一个Stream, 这让多个操作可以链接起来组成中间操作链, 从而形成一条流水线, 因此它的特点就是前面提到的延迟执行
        		.distinct()   //.collect(Collectors.toSet());
        		.sorted(Comparator.comparing(String::toString))  //Comparator.naturalOrder()
        		.collect(Collectors.toList());
        System.out.println("4:"+userList);
       //(5) 有没有交易员是在米兰工作的?
        boolean hasWork = transactions.stream()
        		.anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println("5:"+hasWork);
       //(6) 打印生活在剑桥的交易员的所有交易额。
        int total = transactions.stream()
        		.filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
        		.mapToInt(Transaction::getValue) //通用流转成了数值流
        		.sum();
        System.out.println("6:"+total);		
       //(7) 所有交易中，最高的交易额是多少?
        OptionalInt max = transactions.stream()
        		.mapToInt(Transaction::getValue)
        		.max();
        System.out.println("7:"+max.orElse(0));//默认值。如果Optional类的实例为非空值的话，isPresent()返回true，否从返回false。
       //(8) 找到交易额最小的交易。（Optional，Google的Guava里也有这个类）
        Optional<Transaction> min = transactions.stream()
        		.min(Comparator.comparing(Transaction::getValue));
        System.out.println("8:"+min.orElseThrow(IllegalArgumentException::new));
       //(9) 按年份给所有交易分组
        Map<Integer, List<Transaction>> map = transactions.stream()
        		.collect(Collectors.groupingBy(Transaction::getYear));
        System.out.println("9:"+map);

        //(10) 按照年份去重
        ArrayList<Transaction> yearList = transactions.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(o -> o.getYear()))
                ), ArrayList::new));
        System.out.println("10:"+yearList);

        System.out.println((Integer.MAX_VALUE+1) == Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE-1>Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE+1+" | "+(Integer.MIN_VALUE-1));

        List<CourseUserId> msgList = Lists.newArrayList();
        CourseUserId cui = new CourseUserId();
        cui.setId(1);
        cui.setCreateTime(new Date());
        msgList.add(cui);

        CourseUserId cui2 = new CourseUserId();
        cui2.setId(2);
        cui2.setCreateTime(DateUtils.addDay(new Date(),2));
        msgList.add(cui2);

        msgList = msgList.stream()
                .sorted(Comparator.comparing(CourseUserId::getCreateTime).reversed())
                .collect(Collectors.toList());

        System.out.println(msgList);
        
    }
}
