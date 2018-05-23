package my.test;

public class ClassLoaderTest {
	static int d = 3;
	static {
		System.out.println(d+"我是ClassLoaderProduce类");
	}

	public static void main(String[] args) {
		SimpleClass simpleClass = new SimpleClass();
		simpleClass.run();
	}
}

class SimpleClass {
	static int a = 3;
	static {
		a = 100;
		System.out.println(a);
	}

	public SimpleClass() {
		System.out.println("对类进行加载！");
	}

	public void run() {
		System.out.println("我要跑跑跑！");
	}
}