package my.test;

public class StaticTest {
	{
		System.out.println("1");
	}

	StaticTest() {
		System.out.println("2");
		System.out.println("a=" + a + ",b=" + b);
	}


	static StaticTest st = new StaticTest();//有他会输出1234，否则只有34

	static {
		System.out.println("3");
	}

	public static void staticFunction() {
		System.out.println("4");
	}

	
	public static void main(String[] args) {
		staticFunction();
	}
	
	//当这两个成员变量放在最上面，则输出1=110，b=112； 放在最下面输出a=110,b=0
	//如果用final修饰b，那么在准备阶段b的值就是112 (类的生命周期是：加载->验证->准备->解析->初始化->使用->卸载)
	int a = 110;
	static int b = 112;
}
