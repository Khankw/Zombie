package zombie;

public class Boss extends Unit {

	protected Boss(int hp, int power, int pos) {
		super("보스", "B", hp, power, pos);
	}

	@Override
	protected boolean attack(Unit unit) {
		int attack = 0;
		int atkType = ran.get(3);
		String msg = "";
		if (atkType < 2) {
			attack = ran.get(power / 3, power);
			msg += String.format("%s가 일반 공격을 합니다.\n", name);
		} else {
			attack = ran.get(power / 2, power * 2);
			msg += String.format("%s가 강력한 공격을 합니다.\n", name);
		}
		unit.takeDamage(attack);
		msg += String.format("%s가 %s에게 %d의 피해를 입혔습니다.", name, unit.getName(), attack);
		System.out.println(msg);
		return unit.isDead();
	}

	@Override
	protected void move(int start, int end) {
		int newPos = pos + ran.get(-1, 1);
		if (newPos < start)
			return;

		pos = newPos;
	}

	@Override
	protected boolean isDead() {
		if (hp == 0) {
			System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			System.out.println(name + "을 쓰러트렸습니다!\n다음스테이지로 이동합니다.");
			System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			return true;
		}
		return false;
	}

}
