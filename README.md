# 【デザインパターン】Flyweight

## 初めに　
社内勉強会で、「一つのデザインパターンを選んで、皆に紹介してください。」と言われた時に、迷わずFlyweightに選んだ。

そもそも「Flyweight」とは、ボクシングなどの体重階級の中でもっとも軽い部類に分類できる階級だと分かっているが、デザインパターンにおいて、軽いのは一体なにを意味しているかなと興味が湧いた。

## なにか
一言でいうと、オブジェクトを生成する時、すでに作成済みのインスタンス再作成せず、それを再利用するコードで、メモリの消費を抑えるようにのデザインパターンである。

## なぜか
オブジェクトを生成するには、メモリを確保する(インスタンス化)必要がある。多くのオブジェクトを生成するとメモリが多く消費されてしまう。

## どんな場合か
1. 多くのオブジェクトを生成する
1. 一つのオブジェクト生成にかなりメモリを消費する
1. オブジェクトの状態は外部化できる
1. システムはこのオブジェクト達のIDに依存しない
1. オブジェクト達は見分けられないTODO

## ポイント
- ファクトリーデザインパターンを利用する
    - オブジェクトの生成はfactoryに任せる
- オブジェクトのプール
    - 共有可能なオブジェクトが要求された場合、プールからそのオブジェクトを返す

## 実装例

会社で従業員を仕事させるというサンプルアプリケーションを作成する。
待機従業員のワーキングプールを事前に用意している。
プールの中の従業員は既に待機中ので、直接仕事させられる。
準備していない場合は準備させてワーキングプールに追加する。

ここで、従業員はオブジェクトとして、待機させるのはつまりオブジェクトの生成とみなす。

###クラス図

### Step1

会社員のインターフェースを作成

```java
public interface Employee {
	void work();
}
```

### Step2

プログラマーのクラスを作成

```java
public class Programmer implements Employee {
	
    private String name;
	public Programmer(String name) {
		this.name = name;
	}

	@Override
	public void work() {
		System.out.println(name + "さんが仕事中です。");
	}
}
```
### Step3

プログラマーのファクトリーを作成

```java
public class EmployeeFactory {

	private static final HashMap<String, Employee> progammerMap = new HashMap<>();

	public static Employee getEmployee(String name) {
		Programmer programmer = (Programmer) progammerMap.get(name);

		if (programmer == null) {
            	//初めて生成する場合
			programmer = new Programmer(name);
			progammerMap.put(name, programmer);
			System.out.println(name + "さんが準備しました。待機中です。");
		}
		return programmer;
	}
}
```
### Step4

テストクラス

```java
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
```

### Step5

実行すると

```
鍾さんが準備しました。待機中です。
鍾さんが仕事中です。
王さんが準備しました。待機中です。
王さんが仕事中です。
王さんが仕事中です。
添峰さんが準備しました。待機中です。
添峰さんが仕事中です。
云さんが準備しました。待機中です。
云さんが仕事中です。
向さんが準備しました。待機中です。
向さんが仕事中です。
向さんが仕事中です。
向さんが仕事中です。
添峰さんが仕事中です。
王さんが仕事中です。
鍾さんが仕事中です。
云さんが仕事中です。
王さんが仕事中です。
向さんが仕事中です。
添峰さんが仕事中です。
云さんが仕事中です。
云さんが仕事中です。
鍾さんが仕事中です。
向さんが仕事中です。
向さんが仕事中です。
```
## 既に実装したJDKの例

Integer#valueOfでFlyweightパターンが使われてるとのことで早速ソースを見てみた。
```java
public static Integer valueOf(int i) {
    if(i >= IntegerCache.low  && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    else
        return new Integer(i);
}
```

cacheHighは基本的には127で、IntegerCache.lowは-128なので、つまり-128から127までのIntegerオブジェクトはあらかじめ作ってある。
以下のようなテストでちょっと確認してみる。
```java
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
```

実行すると
```
a == b
a == c
b == c
----------
aa != bb
aa != cc
bb != cc
```

## 後書き
便利なデザインパターンだけど、欠点として少なくともシステムの複雑さをあげてしまう。

更に、外部状態と内部状態TODO（説明）を分離しないと、システムを混乱させてしまう。

実際利用したい場合はこのデザインパターンのメリットデメリットを深く考えてから、本当に必要かどうかを判断して提供しよう。