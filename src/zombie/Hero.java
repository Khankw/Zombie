package zombie;

public class Hero extends Unit {
	private final int MAX_POTION = 5;
	private int potion;

	public Hero() {
		super("영웅", "H", 100, 10, 0);
		potion = 3;
	}

	public void recovery() {
		hp += 100;
		if (hp > maxHp)
			hp = maxHp;
	}

	public void reset() {
		pos = 0;
	}

	public void addPotion() {
		if (potion < MAX_POTION)
			potion++;
	}

	public int getPotion() {
		return potion;
	}

	@Override
	public void attack(Unit unit) {
		
	}

	@Override
	public void move(int start, int end) {
		pos++;
		if (pos > end)
			pos = end;

		System.out.println(pos);
	}

	@Override
	protected boolean isDie() {
		if (hp == 0) {
			System.out.println(name + "이 사망했습니다...†\n클리어 실패.");
			return true;
		}
		return false;
	}
}
