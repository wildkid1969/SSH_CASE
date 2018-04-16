package com.test.base;

public class TestSynchronized extends Thread {
	public static int count;
	public void run() {
		/**
		 * 一个线程访问一个对象中的synchronized(this)同步代码块时，其他试图访问该对象的线程将被阻塞。
		 * synchronized锁定的是对象
		 * 如果new了两个TestThread()，这时会有两把锁分别锁定syncThread1对象和syncThread2对象，
		 * 而这两把锁是互不干扰的，不形成互斥，所以两个线程可以同时执行。
		 * 
		 * 当一个线程访问对象的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该对象中的非synchronized(this)同步代码块。
		 * 
		 * 括号里如果是静态成员变量或者是这个类，则会同步这个类的所有对象
		 */
		synchronized(lock){//修饰代码块（如果改成修饰一个方法，效果一样 public synchronized void method(){}）
			for(int i=0;i<10;i++){
				System.out.println(Thread.currentThread().getName()+"--"+(count++));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 1.当有一个明确的对象作为锁时，就可以用类似下面这样的方式写程序。
	 */
	public void method3(Object obj){
		//obj 锁定的对象
		synchronized (this){
			
		}
	}
	
	/**
	 * 2.当没有明确的对象作为锁，只是想让一段代码同步时，可以创建一个特殊的对象来充当锁：
	 * 加static，将会同步这个类的所有对象
	 */
	private static byte[] lock = new byte[0];  // 特殊的instance变量
	public void method(){
	    synchronized(lock) {
	         // todo 同步代码块
	     }
	}
	 
	public void run1() {
	 
	}
	   
	
	
	
	/**
	 * 在用synchronized修饰方法时要注意以下几点：
	 *	1. synchronized关键字不能继承。
	 *  2.在定义接口方法时不能使用synchronized关键字。
	 *  3.构造方法不能使用synchronized关键字，但可以使用synchronized代码块来进行同步。
	 *  
	 *  总结：
	 *  A. 无论synchronized关键字加在方法上还是对象上，如果它作用的对象是非静态的，则它取得的锁是对象；
	 *     如果synchronized作用的对象是一个静态方法或一个类，则它取得的锁是对类，该类所有的对象同一把锁。
	 *  B. 每个对象只有一个锁（lock）与之相关联，谁拿到这个锁谁就可以运行它所控制的那段代码。
	 *  C. 实现同步是要很大的系统开销作为代价的，甚至可能造成死锁，所以尽量避免无谓的同步控制。
	 */
	public static void main(String[] args) {
		//1.
		new TestSynchronized().start();
		new TestSynchronized().start();
		new TestSynchronized().start();
		
		//2.
//		TestSynchronized ts = new TestSynchronized();
//		Thread thread1 = new Thread(ts, "thread-1"); 
//		Thread thread2 = new Thread(ts, "thread-2"); 
//		
//		thread1.start();
//		thread2.start();
	}

}
