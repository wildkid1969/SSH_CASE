package my.test;


public class TestClass {
    static int value=9;
    private static TestClass tester = new TestClass();//step1
    private static int count1;                        //step2
    private static int count2 = 2;                    //step3
    
    public TestClass(){                               //step4
        count1++;  
        count2++;  
        System.out.println("构造器：" + count1 + count2);  
    }
    
    
    //构造代码块
    //构造代码块在创建对象时被调用，每次创建对象都会被调用，并且构造代码块的执行次序优先于类构造函数。
    {
        System.out.println("构造代码块");    
    }
    
    public static TestClass getTester(){              //step5
        return tester;  
    }  
    
    public static void main(String[] args) {
        TestClass.getTester();                    //step6
        //先找到程序的入口main()、要执行main（）方法，还得先将TestClass类加载到内存中。
        //那么TestClass类被加载，那么就会先执行TestClass类的静态属性和静态语句块（static），执行先后顺序看谁在前面就先执行谁
        //顺序为14235
        
        /**
         * 对象的初始化顺序：
         * （1）类加载之后，按从上到下（从父类到子类）执行被static修饰的语句；
         * （2）当static语句执行完之后,再执行main方法；
         * （3）如果有语句new了自身的对象，将从上到下执行构造代码块、构造器（两者可以说绑定在一起）。
         */
        
        int value = new TestClass().getValue();
        System.out.println("value:"+value);
        
        System.out.println(args==null);//false
        System.out.println(args.length);//0
        
    }
    
    
    private int getValue(){
        System.out.println("getValue");
        int value=69;
        return this.value;
    }
    
}
