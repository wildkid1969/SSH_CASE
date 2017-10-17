package com.test.base;

public class Ticket {
	public static void main(String[] args) {
		int people = 100;
		TicketSystem t1 = new TicketSystem();
		Thread[] thread = new Thread[people];
		for (int i = 1; i < people; i++) {
			thread[i] = new Thread(t1);
			thread[i].start();
		}
	}

}

class TicketSystem extends Thread {
	//为了保持票数的一致，票数要静态
	static int ticket = 20;
	//创建一个静态钥匙
	String str = "key";//值是任意的
	int time = 50;

	@Override
	public void run() {
		if (ticket > 0) {
			synchronized (str) {//这个很重要，必须使用一个锁，
				if (ticket > 0) {
					System.out.println(Thread.currentThread().getName()
							+ ": taker  get " + ticket + " ticket");
					ticket--;
				}
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
