package zombie;

public abstract class Unit {
	protected String name;
	private String symbol;
	protected int maxHp;
	protected int hp;
	protected int power;
	protected int pos;

	protected RandomManager ran = RandomManager.getInstance();

	protected Unit(String name, String symbol, int hp, int power, int pos) {
		this.name = name;
		this.symbol = symbol;
		this.maxHp = hp;
		this.hp = hp;
		this.power = power;
		this.pos = pos;
		System.out.println(name + " 생성!");
	}

	protected abstract void attack(Unit unit);

	protected abstract void move(int start, int end);

	protected abstract boolean isDie();

	protected int getHp() {
		return hp;
	}

	protected int getPos() {
		return pos;
	}
	
	public String getSymbol() {
		return symbol;
	}
}
