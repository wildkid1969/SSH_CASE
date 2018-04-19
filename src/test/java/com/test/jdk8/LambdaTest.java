package com.test.jdk8;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import my.test.MyFunctionalInterface;

public class LambdaTest {

    public static void test1() {//λ表达式本质上是一个匿名方法
        Runnable runnable = () -> System.out.println("test1 runnable.run");
        runnable.run();
        //Comparator<Integer> comparator = (Integer a,Integer b) -> {
        Comparator<Integer> comparator = (a,b) -> {
            if(a == b){
                return 0;
            }else if (a > b){
                return 1;
            }else{
                return -1;
            }
        };
        System.out.println("comparator 9 19：" + comparator.compare(9,19));
        //Function<String,Integer> function = (s) -> {return new Integer(s);};
        Function<String,Integer> function = (s) -> new Integer(s);
        System.out.println("function:" + (function.apply("1234") == 1234));
        MyFunctionalInterface<String,Integer> myFunction = (s) -> new Integer(s);
        System.out.println("myFunction:" + (myFunction.apply("1234") == 1234));
        // int five = ( (x, y) -> x + y ) (2, 3); // ERROR! try to call a lambda in-place 不能声明立即调用
    }

    public static void test2() {//λ与Object的关系
        //Object obj1 = () -> System.out.println("test2 runnable.run");
        //Object obj2 = (Runnable) () -> System.out.println("test2 runnable.run");
        Runnable runnable = () -> System.out.println("test2 runnable.run");
        //Object obj3 = runnable;
        runnable.run();
//        System.out.println( () -> {} ); //编译不通过，目标类型不明
        System.out.println( (Runnable)() -> {} );
    }

    public static void useLambdaTest1(){
        Thread th1 = new Thread(() -> System.out.println("A thread running."));
        th1.start();
        System.out.println("useLambdaTest1 end");
    }

    private static List<String> targetList = Lists.newArrayList("123","13","123","13","55","513","7293","733");

    public static void useLambdaTest2(){
        targetList.forEach(o -> System.out.println(o));
//        targetList.forEach(System.out::println);
    }

    public static void useLambdaTest3(){
        System.out.println("targetList:"+targetList);
//        targetList.stream() //顺序计算
        targetList.parallelStream() //并行计算 或 targetList.stream().parallel()
                .filter(s -> (new Integer(s)) % 3 == 0)//filter 是 lazy性质的方法
                .forEach(o -> System.out.println(o));//forEach 是 eager性质的方法，遇到eager性质的方法
    }

    public static void useLambdaTest4(){
        System.out.println("targetList:"+targetList);
        List<Integer> intList1 = targetList.parallelStream() //并行计算 targetList.stream().parallel()
                .map(s -> new Integer(s))
                // .map(Integer::new)
                .filter(s -> s % 3 == 0)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("intList1:"+intList1);
    }

    public static void useLambdaTest5(){
        System.out.println("targetList:"+targetList);
        List<Integer> intList2 = targetList.parallelStream() //并行计算 targetList.stream().parallel()
//        List<Integer> intList2 = targetList.stream() //为了看到流式计算效果，采用顺序计算，切换到上面可以看到并行计算的现象
                .map(s -> {
                    System.out.println("map>"+s);
                    return new Integer(s);
                })
                .filter(s -> {
                    System.out.println("filter>"+s);
                    return s % 3 == 0;
                })
                .distinct()
                .collect(Collectors.toList());
        System.out.println("intList2:"+intList2);
    }

    public static void useLambdaTest6(){// 聚合计算 类似SQL的 group by
        System.out.println("targetList:"+targetList);
        Map<Integer, Integer> map1 = targetList.parallelStream() //统计大于50的数出现的次数
                .map(s -> new Integer(s))
                .filter(s -> s > 50)
                .collect(Collectors.groupingBy(p -> p,Collectors.summingInt(p -> 1)));
        System.out.println("map1:"+map1);
    }

    public static void useLambdaTest7(){ // Filter-Map-Reduce
        System.out.println("targetList:"+targetList);
        //reduce方法用来产生单一的一个最终结果。
        //流有很多预定义的reduce操作，如sum()，max()，min()等
        int sum = targetList.parallelStream() //统计大于50的数出现的次数
                .map(s -> new Integer(s))
                .filter(s -> s > 50)
                .distinct()
                .reduce(0,(x,y) -> x + y);//提供了初始值，否则返回值不是int，而是 Optional<Integer>
        System.out.println("sum:"+sum);
    }

    public static void useLambdaTest8() throws Exception { // 现在就可以挑战下面的了
        Callable<Runnable> callable1 = () -> () -> System.out.println("A Runnable running"); //嵌套
        callable1.call().run();

        Callable<Integer> callable2 = () -> 42;
        Callable<Integer> callable2_ = System.currentTimeMillis() % 3 == 0 ? (() -> 42) : (() -> 24);
        System.out.println("callable2" + callable2.call());

        LambdaTest lambdaTest = new LambdaTest();
        System.out.println("factorial:" + lambdaTest.factorial.apply(5));
    }

    // 定义一个递归函数，注意须用this限定
    public UnaryOperator<Integer> factorial =
            i -> i == 0 ? 1 : i * this.factorial.apply( i - 1 );

    public static void useLambdaTest9(){ // 既然是匿名内部方法，那使用外部变量要注意哪些
        // final int num = 100;
        int num = 100; //不用显示声明final，但一旦被匿名内部方法使用，立即变成final，否则编译不通过
        int sum2 = targetList.parallelStream() //统计大于num的数出现的次数
                .map(s -> new Integer(s))
                .filter(s -> s > num)
                .distinct()
                .reduce(0,(x,y) -> x + y);//提供了初始值，否则返回值不是int，而是 Optional<Integer>
        //num++; 一旦后面改变了其final属性，则中间过程中的Lambda表达式会编译报错
        System.out.println("sum2:"+sum2);
    }

    public Long someMethod(){
        System.out.println("some method running");
        return System.currentTimeMillis();
    }

    public static void otherAbout() throws Exception {
        Runnable test1 = LambdaTest::test1; //静态方法引用
        test1.run();
        System.out.println("------分割线------");

        Callable<Long> callable= (new LambdaTest())::someMethod; //实例方法引用
        System.out.println("otherAbout.callable:" + callable.call());
        System.out.println("------分割线------");

        Runnable aNew = LambdaTest::new;//构造函数都是可以引用的，具体查看useLambdaTest4注释掉的代码

        //有时流不一定是已经生成好的，可以是生成器函数来生成
        Stream.generate(Math::random).limit(5).forEach(System.out::println);

    }

}
