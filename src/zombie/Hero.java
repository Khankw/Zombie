package zombie;

public class Hero extends Unit {
	private final int MAX_POTION = 5;
	private int accuracy;
	private int potion;
	private int specialGuage;
	private boolean activeSpecial;

	public Hero() {
		super("영웅", "H", 100, 20, 0);
		accuracy = 10;
		potion = 3;
	}

	public void recovery() {
		int heal = (maxHp * 50 / 100);
		if (hp + heal > maxHp)
			heal = maxHp - hp;
		hp += heal;
		String msg = String.format("%s이 포션을 사용해 %d만큼 회복해 체력이 %d가 되었습니다.", name, heal, hp);
		System.out.println(msg);
		potion--;
	}

	public void reset() {
		pos = 0;
	}

	public void addPotion() {
		System.out.println("포션을 얻었습니다!");
		if (potion < MAX_POTION)
			potion++;
		else
			System.out.println("가방이 가득차 더 이상 가질 수 없습니다.");
	}

	public int getPotion() {
		return potion;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void showUpgrade() {
		System.out.println("UPGRADE >>>>");
		System.out.println("[1]최대 체력     : " + maxHp);
		System.out.println("[2]공격력       : " + power);
		System.out.println("[3]정확도(최대90) : " + accuracy);
	}

	public void upgrade(int maxHp, int power, int accuracy) {
		this.maxHp += maxHp;
		this.hp += maxHp;
		this.power += power;
		this.accuracy += accuracy;
	}

	public String guageToString() {
		String msg = "SPECIAL[";
		if (specialGuage == 10)
			msg += "■■■■■■■■■■";
		else {
			for (int i = 0; i < 10; i++) {
				if (i < specialGuage)
					msg += "□";
				else
					msg += " ";
			}
		}
		msg += "]";
		return msg;
	}

	public int getSpecialGuage() {
		return specialGuage;
	}

	private void specialGuage() {
		specialGuage++;
		if (specialGuage > 10)
			specialGuage = 10;
	}

	public void activeSpecial() {
		activeSpecial = true;
		specialGuage = 0;
	}

	@Override
	public boolean attack(Unit unit) {
		specialGuage();
		int min = power * accuracy / 100;
		int max = power;
		String msg = "";
		if (min == 0)
			min = 1;
		if (activeSpecial) {
			msg += String.format("%s이 강력한 필살기를 사용합니다!!!\n", name);
			min = (min + power) / 2;
			max += power;
			activeSpecial = false;
		} else {
			msg += String.format("%s이 일반 공격을 합니다.\n", name);
		}

		int attack = ran.get(min, max);
		unit.takeDamage(attack);
		msg += String.format("%s이 %s에게 %d의 피해를 입혔습니다.", name, unit.getName(), attack);
		System.out.println(msg);
		return unit.isDead();
	}

	@Override
	public void move(int start, int end) {
		specialGuage();
		pos++;
		if (pos > end)
			pos = end;
	}

	@Override
	protected boolean isDead() {
		if (hp == 0) {
			System.out.println("††††††††††††††††††††††††††††††††††††††††");
			System.out.println(name + "이 사망했습니다...†\n클리어 실패.");
			System.out.println("††††††††††††††††††††††††††††††††††††††††");
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s 포션[%d/%d]", super.toString(), potion, MAX_POTION);
	}
}
