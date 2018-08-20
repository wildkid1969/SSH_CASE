package com.test.base;

/**
 * 类的继承与多态：
 * 父类的属性和方法，除了private外还有一样是子类继承不了的---构造器。
 * 继承的好处 
		a：提高了代码的复用性
		b：提高了代码的维护性
		c：让类与类之间产生了关系，是多态的前提
       继承的弊端
		
		a：类的耦合性增强了。
		b：开发的原则：高内聚，低耦合。
		
		耦合：类与类的关系
		内聚：就是自己完成某件事情的能力
		
 * 
 * 类的多态：	
 * 所谓多态就是指程序中定义的引用变量所指向的具体类型和通过该引用变量发出的方法调用在编程时并不确定，
 * 而是在程序运行期间才确定，即一个引用变量倒底会指向哪个类的实例对象，该引用变量发出的方法调用到底
 * 是哪个类中实现的方法，必须在由程序运行期间才能决定。因为在程序运行时才确定具体的类，这样，不用修
 * 改源程序代码，就可以让引用变量绑定到各种不同的类实现上，从而导致该引用调用的具体方法随之改变，
 * 即不修改程序代码就可以改变程序运行时所绑定的具体代码，让程序可以选择多个运行状态，这就是多态性。
 * 
 * 
 * 当超类对象引用变量引用子类对象时，被引用对象的类型而不是引用变量的类型决定了调用谁的成员方法，
 * 但是这个被调用的方法必须是在超类中定义过的，也就是说被子类覆盖的方法，
 * 但是它仍然要根据继承链中方法调用的优先级来确认方法，
 * 该优先级为：this.show(O)、super.show(O)、this.show((super)O)、super.show((super)O)。
 * 
 */
public class A {
    public String show(D obj) {
        return ("A and D");
    }

    public String show(A obj) {
        return ("A and A");
    } 
    
    
    
    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new B();
        B b = new B();
        C c = new C();
        D d = new D();
        
        System.out.println("1--" + a1.show(b));//A and A
        System.out.println("2--" + a1.show(c));//A and A
        System.out.println("3--" + a1.show(d));//A and D
        System.out.println("4--" + a2.show(b));//B and A  虽然a2的实例是B(),但是他的引用确是A。实例决定了调用谁，但这个方法必须在父类中被定义过
        System.out.println("5--" + a2.show(c));//同上(如果父类有这个方法，而没被覆盖就调用父类方法，覆盖了就调用子类方法)
        System.out.println("6--" + a2.show(d));//A and D
        System.out.println("7--" + b.show(b));//B and B
        System.out.println("8--" + b.show(c));//B and B
        System.out.println("9--" + b.show(d));//A and D 调用了父类方法有优先级    (如果父类有这个方法，而没被覆盖就调用父类方法，覆盖了就调用子类方法)
        
        System.out.println("10--" + a1.show(a1));//A and A
        System.out.println("11--" + a2.show(a1));//B and A
        
        /**
         * 运行结果：
         *  1--A and A
			2--A and A
			3--A and D
			4--B and A
			5--B and A
			6--A and D
			7--B and B
			8--B and B
			9--A and D
         */
    }

}

class B extends A{
    public String show(B obj){
        return ("B and B");
    }
    
    public String show(A obj){
        return ("B and A");
    } 
}

class C extends B{

}

class D extends B{

}
