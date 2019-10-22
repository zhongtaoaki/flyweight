package flyweight;

import java.util.HashMap;

public class EmployeeFactory {

	private static final HashMap<String, Employee> progammerMap = new HashMap<>();

	public static Employee getEmployee(String name) {
		Programmer programmer = (Programmer) progammerMap.get(name);

		if (programmer == null) {
			programmer = new Programmer(name);
			progammerMap.put(name, programmer);
			System.out.println(name + "���󤬜ʂ䤷�ޤ��������C�ФǤ���");
		}
		return programmer;
	}

}
