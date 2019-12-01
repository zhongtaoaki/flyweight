package flyweight;

public abstract class Employee {

	public String name;

	public Employee(String name) {
		this.name = name;
	}

	public abstract void work();

}
