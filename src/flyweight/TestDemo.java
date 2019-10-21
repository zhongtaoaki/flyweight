package flyweight;

public class TestDemo {

	private static final String names[] = { "Íõ", "æR", "Ïò", "Ìí·å", "ÔÆ" };

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
