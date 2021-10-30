package flyweight;

//员工抽象类
public abstract class Employee {

	//员工姓名
	public String name;

	//构造器
	public Employee(String name) {
		this.name = name;
	}

	//工作方法
	public abstract void work();

}
