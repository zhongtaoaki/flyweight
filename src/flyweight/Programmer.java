package flyweight;

public class Programmer extends Employee {

	public Programmer(String name) {
		super(name);
	}

	@Override
	public void work() {
		System.out.println(name + "���������ФǤ���");
	}
}
