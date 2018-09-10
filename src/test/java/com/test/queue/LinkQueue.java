package com.test.queue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LinkQueue<T> {
	private QNode<T> front;// 队头指针
	private QNode<T> rear;// 队尾指针
	private int maxSize;// 为了便于操作，使用这个变量表示链队的数据容量

	// 初始化
	public LinkQueue() {
		this.front = new QNode<T>();
		this.rear = new QNode<T>();
		this.maxSize = 0;
	}

	// 初始化队列
	public void initQueue() {
		front.next = null;
		rear.next = null;
		maxSize = 0;
	}

	// 队空判断
	public boolean isNull() {
		if (front.next == null || rear.next == null)
			return true;
		else
			return false;
	}

	// 进队
	public void push(QNode<T> node) {
		if (isNull()) {
			// 第一次
			front.next = node;
			rear.next = node;
			maxSize++;
		} else {
			node.next = front.next;
			front.next = node;
			maxSize++;
		}
	}

	// 出队
	public QNode<T> pop() {
		if (isNull())
			return null;// 队为空时，无法出队
		else if (maxSize == 1) {
			// 队只有一个元素时直接初始化即可
			QNode<T> node = front.next;
			initQueue();
			return node;
		} else {
			// 准备工作
			QNode<T> p = front;// 使用p指针来遍历队列
			for (int i = 1; i < maxSize - 1; i++)
				p = p.next;
			// pop
			QNode<T> node = rear.next;
			rear.next = p.next;
			maxSize--;
			return node;
		}
	}
	
	/**
	 * 递归实现头尾互换
	 * @param node
	 * @return
	 */
	public static QNode<Integer> reverseList(QNode<Integer> node) {
	    if (node.next == null) {
	        return node;
	    } else {
	    	QNode<Integer> tmp1 =   reverseList(node.next);

	    	QNode<Integer> tmp2 = node.next;
	        tmp2.next = node;
	        node.next = null;

	        return tmp1;
	    }
	}
	
	/**
	 * 非递归实现头尾互换
	 * @param node
	 * @return
	 */
	public static QNode<Integer> reverseList2(QNode<Integer> head) {
		QNode<Integer> prev = null;
        while(head!=null){
        	QNode<Integer> tmp = head.next;
            head.next = prev;
            prev = head;
            head = tmp;
        }
        return prev;
    }
	
	private static Object getFieldValueByName(String fieldName, Object o) {  
        try {    
            String firstLetter = fieldName.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + fieldName.substring(1);    
            Method method = o.getClass().getMethod(getter, new Class[] {});    
            Object value = method.invoke(o, new Object[] {});    
            return value;    
        } catch (Exception e) {    
          
            return null;    
        }    
    } 
	
	
	public static void main(String[] args) {
		LinkQueue<Integer> lq = new LinkQueue<Integer>();
        
        System.out.println("队列是否为空："+lq.isNull());
        
        //依次插入1、2、3、4
        lq.push(new QNode<Integer>(1));
        lq.push(new QNode<Integer>(2));
        lq.push(new QNode<Integer>(3));
        lq.push(new QNode<Integer>(4));
        
        //依次出队
        System.out.println("依次出队：");
        while(!lq.isNull()){
            System.out.println(lq.pop().getData());
        }
        
        //头尾交换
        QNode<Integer> node1 = new QNode<Integer>(1);
        QNode<Integer> node2 = new QNode<Integer>(2);
        QNode<Integer> node3 = new QNode<Integer>(3);
        node1.setNext(node2);
        node2.setNext(node3);
        
//        QNode<Integer> rnode = reverseList(node1);
        QNode<Integer> rnode = reverseList2(node1);
        System.out.println(rnode.getData());
        
        Double a = 0.00;
        Double b = 0D;
        System.out.println(a==b);
        
	}

}

// 链队结点
class QNode<T> {
	private T data;// 数据域
	public QNode<T> next;// 指针域

	// 初始化1
	public QNode() {
		this.data = null;
		this.next = null;
	}

	// 初始化2
	public QNode(T data) {
		this.data = data;
		this.next = null;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	public QNode<T> getNext() {
		return next;
	}

	public void setNext(QNode<T> next) {
		this.next = next;
	}

}
