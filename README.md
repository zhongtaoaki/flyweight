# 【デザインパターン】Flyweight

## 初めに　
社内勉強会で、「一つのデザインパターンを選んで、皆に紹介してください。」と言われた時に、迷わずFlyweightに選んだ。そもそも「Flyweight」とは、ボクシングなどの体重階級の中でもっとも軽い部類に分類できる階級だと分かっている。  

が、デザインパターンとして、一体なにか軽いかな？

## なにか
一言で、多くのオブジェクトを生成する時に、すでにnewしたインスタンスに関しては繰り返し使用して、メモリの消費は軽くなれるようにのデザインパターンだ。

## なぜか
オブジェクトを生成するにはnewを行い、メモリを確保する(インスタンス化)必要がありますが、多くのオブジェクトを生成するとメモリを多く消費してしまい、処理の速度が遅くなってしまう。

## いつか
1. 多くのオブジェクトを生成する
1. オブジェクト達かなりメモリを消費する
1. オブジェクトの状態は外部化できる
1. システムはこのオブジェクト達のIDに依存しない
1. オブジェクト達は見分けられない

## ポイント
- ファクトリーデザインパターンを利用する
    - オブジェクトの生成はfactoryに任せる
- オブジェクトのプール
    - 共有可能なオブジェクトが要求された場合は、プールからそのオブジェクトを返す

## どうやって

会社で従業員を仕事させるというサンプルアプリケーションを作成する。
初めてある会社員を仕事させる時は、雇ってやる。この以降は、雇わずに仕事させる。

ここで、従業員はオブジェクトとして、「雇う」はつまりオブジェクトの生成とみなす。

### Step1

会社員のインターフェースを作成

~~~
public interface Employee {
	void work();
}
~~~

### Step2

プログラマーのクラスを作成

~~~
public class Programmer implements Employee {
	
    private String name;
	public Programmer(String name) {
		this.name = name;
	}

	@Override
	public void work() {
		System.out.println(name + "さんが仕事中");
	}
}
~~~
### Step3

プログラマーのファクトリーを作成

~~~
public class EmployeeFactory {

	private static final HashMap<String, Employee> progammerMap = new HashMap<>();

	public static Employee getEmployee(String name) {
		Programmer programmer = (Programmer) progammerMap.get(name);

		if (programmer == null) {
            //初めて生成する場合
			programmer = new Programmer(name);
			progammerMap.put(name, programmer);
			System.out.println(name + "さんがソフトシンクに加入しました。");
		}
		return programmer;
	}
}
~~~
### Step4

テストクラス

~~~
public class TestDemo {

	private static final String names[] = { "王", "鍾", "向", "添峰", "云" };

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
~~~

### Step5

実行すると

~~~
王さんがソフトシンクに加入しました。
王さんが仕事中
向さんがソフトシンクに加入しました。
向さんが仕事中
向さんが仕事中
鍾さんがソフトシンクに加入しました。
鍾さんが仕事中
向さんが仕事中
鍾さんが仕事中
向さんが仕事中
向さんが仕事中
向さんが仕事中
向さんが仕事中
云さんがソフトシンクに加入しました。
云さんが仕事中
云さんが仕事中
王さんが仕事中
王さんが仕事中
鍾さんが仕事中
向さんが仕事中
王さんが仕事中
云さんが仕事中
鍾さんが仕事中
向さんが仕事中
~~~
## 既に実装したJDKの例

Integer#valueOfでFlyweightパターンが使われてるとのことで早速ソースを見てみた。
~~~
public static Integer valueOf(int i) {
    if(i >= -128 && i <= IntegerCache.high)
        return IntegerCache.cache[i + 128];
    else
        return new Integer(i);
}
~~~

cacheHighは基本的には127なので、つまり-128から127までのIntegerオブジェクトはあらかじめ作ってある。
以下のようなテストでちょっと確認してみる。
~~~
public class testInteger {
	public static void main(String[] args) {
		Integer a = 127;
		Integer b = Integer.valueOf(127);
		Integer c = Integer.valueOf(127);

		System.out.println(a == b ? "a == b" : "a != b");
		System.out.println(a == c ? "a == c" : "a != c");
		System.out.println(b == c ? "b == c" : "b != c");

		System.out.println("----------");

		Integer aa = 128;
		Integer bb = Integer.valueOf(128);
		Integer cc = Integer.valueOf(128);

		System.out.println(aa == bb ? "aa == bb" : "aa != bb");
		System.out.println(aa == cc ? "aa == cc" : "aa != cc");
		System.out.println(bb == cc ? "bb == cc" : "bb != cc");
	}
}
~~~

実行すると
~~~
a == b
a == c
b == c
----------
aa != bb
aa != cc
bb != cc
~~~

## 後書き
便利なデザインパターンだけど、欠点として少なくともシステムの複雑さをあげてしまう。

更に、外部状態と内部状態を分離しないと、システムを混乱させてしまう。


利用したい場合あらかじめ真剣に考えよう。
