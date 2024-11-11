package zombie;

public class Zombie extends Unit {

	protected Zombie(int hp, int power, int pos) {
		super("좀비", "Z", hp, power, pos);
	}

	@Override
	protected boolean attack(Unit unit) {
		int min = power / 10;
		if (min == 0)
			min = 1;
		int attack = ran.get(min, power);
		unit.takeDamage(attack);
		int heal = attack / 3;
		if (heal == 0)
			heal = 1;
		hp += heal;
		String msg = String.format("%s가 %s에게 %d의 피해를 입혔습니다.\n", name, unit.getName(), attack);
		msg += String.format("%s는 %s의 피를 먹고 %d의 체력을 회복했습니다.", name, unit.getName(), heal);
		System.out.println(msg);
		return unit.isDead();
	}

	@Override
	protected void move(int start, int end) {
		int newPos = pos + ran.get(-1, 1);
		if (newPos < start || newPos > end)
			return;

		pos = newPos;
	}

	@Override
	protected boolean isDead() {
		if (hp == 0) {
			System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			System.out.println(name + "를 쓰러트렸습니다.");
			System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			return true;
		}
		return false;
	}
}
