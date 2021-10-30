package flyweight;

//继承·
public class Programmer extends Employee {

	public Programmer(String name) {
		super(name);
	}

	//重写父类方法
	@Override
	public void work() {
		System.out.println(name + "开始搬砖了");
	}
}
