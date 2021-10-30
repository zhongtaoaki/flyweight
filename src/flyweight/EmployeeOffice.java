package flyweight;

import java.util.concurrent.ConcurrentHashMap;

public class EmployeeOffice {

	//核心实现方式
	private static final ConcurrentHashMap<String, Employee> progammerMap = new ConcurrentHashMap<>();

	public static Employee getEmployee(String name) {
		Programmer programmer = (Programmer) progammerMap.get(name);

		if (programmer == null) {
			//当这位还没有在办公室的时候我们首先要招聘一下
			programmer = new Programmer(name);
			progammerMap.put(name, programmer);
			System.out.println(name + "已被招聘，在办公室就位了");
		}
		return programmer;
	}
}
