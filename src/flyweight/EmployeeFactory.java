package flyweight;

import java.util.concurrent.ConcurrentHashMap;

public class EmployeeFactory {

	private static final ConcurrentHashMap<String, Employee> progammerMap = new ConcurrentHashMap<>();

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
