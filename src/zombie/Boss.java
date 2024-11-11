package zombie;

public class Boss extends Unit {

	protected Boss(int hp, int power, int pos) {
		super("보스", "B", hp, power, pos);
	}

	@Override
	protected void attack(Unit unit) {

	}

	@Override
	protected void move(int start, int end) {
		int newPos = pos + ran.get(-1, 1);
		if (newPos < start)
			return;

		pos = newPos;
	}

	@Override
	protected boolean isDie() {
		if (hp == 0) {
			System.out.println(name + "을 쓰러트렸습니다!\n다음스테이지로 이동합니다.");
			return true;
		}
		return false;
	}

}
