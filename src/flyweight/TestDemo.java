package flyweight;

public class TestDemo {

	private static final String names[] = { "王", "鍾", "添峰", "向", "云" };

	public static void main(String[] args) {

		for (int i = 0; i < 20; i++) {
			Programmer programmer = (Programmer) EmployeeFactory.getEmployee(getRandomName());
			programmer.work();
		}
	}

	private static String getRandomName() {
		return names[(int) (Math.random() * names.length)];
	}
}
