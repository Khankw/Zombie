package zombie;

public class Zombie extends Unit {

	protected Zombie(int hp, int power, int pos) {
		super("좀비", "Z", hp, power, pos);
	}

	@Override
	protected void attack(Unit unit) {

	}

	@Override
	protected void move(int start, int end) {
		int newPos = pos + ran.get(-1, 1);
		if (newPos < start || newPos > end)
			return;

		pos = newPos;
	}

	@Override
	protected boolean isDie() {
		if (hp == 0) {
			System.out.println(name + "를 쓰러트렸습니다.");
			return true;
		}
		return false;
	}
}
