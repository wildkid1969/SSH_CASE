package com.test.base;

public class TestStringIntern {
    public static void main(String[] args) {
        /**
         * String.intern()分析
         *  判断这个常量是否存在于常量池。
         *   如果存在
         *    判断存在内容是引用还是常量，
         *     如果是引用，
         *      返回引用地址指向堆空间对象，
         *     如果是常量，
         *      直接返回常量池常量
         *   如果不存在，
         *    将当前对象引用复制到常量池,并且返回的是当前对象的引用
         *
         * 总结
         * 1.只在常量池上创建常量
         *    String a1 = "AA";
         * 2.只在堆上创建对象
         *   String a2 = new String("A") + new String("A");
         * 3.在堆上创建对象，在常量池上创建常量
         *   String a4 = new String("A") + new String("A");//只在堆上创建对象AA
         *     a4.intern();//将该对象AA的引用保存到常量池上
         * 5.在堆上创建对象，在常量池上创建引用, 在常量池上创建常量（不可能）
         *  String a5 = new String("A") + new String("A");//只在堆上创建对象
         *     a5.intern();//在常量池上创建引用
         *     String a6 = "AA";//此时不会再在常量池上创建常量AA，而是将a5的引用返回给a6
         *     System.out.println(a5 == a6); //true
         */
        String a1 = "AA";
        System.out.println(a1 == a1.intern()); //true
        String a2 = new String("B") + new String("B");
        a2.intern();
        String a3 = new String("B") + new String("B");
        System.out.println(a2 == a3.intern());//true
        System.out.println(a3 == a3.intern());//false
        String a4 = new String("C") + new String("C");
        System.out.println(a4 == a4.intern()); //true

    }
}
