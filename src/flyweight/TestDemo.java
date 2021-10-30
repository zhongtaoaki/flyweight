package flyweight;

public class TestDemo {

	private static final String names[] = { "小王", "小李", "小刘"};

	public static void main(String[] args) {

		//我们试着分配10个工作给三位程序员
		for (int i = 0; i < 10; i++) {
			Programmer programmer = (Programmer) EmployeeOffice.getEmployee(getRandomName());
			programmer.work();
		}
	}

	//用随机的方式来分配工作
	private static String getRandomName() {
		return names[(int) (Math.random() * names.length)];
	}
}
