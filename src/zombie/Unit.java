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
	}

	protected abstract boolean attack(Unit unit);

	protected abstract void move(int start, int end);

	protected abstract boolean isDead();

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getHp() {
		return hp;
	}

	public int getPos() {
		return pos;
	}

	public void takeDamage(int damage) {
		hp -= damage;
		if (hp < 0)
			hp = 0;
	}

	@Override
	public String toString() {
		return String.format("%s HP[%d/%d]", name, hp, maxHp);
	}
}
