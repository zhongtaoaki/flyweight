package flyweight;

public class Programmer implements Employee {

	private String name;

	public Programmer(String name) {
		this.name = name;
	}

	@Override
	public void work() {
		System.out.println(name + "§µ§Û§¨ À ¬÷–");
	}
}
