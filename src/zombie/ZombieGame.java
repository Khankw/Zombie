package zombie;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ZombieGame {
	private final int HP = 1;
	private final int POW = 2;
	private final int ACC = 3;
	private final int MOVE = 1;
	private final int EXIT = 0;
	private final int ATTACK = 1;
	private final int SPECIAL = 2;
	private final int AVOID = 3;
	private final int POTION = 4;

	private final int MAP_SIZE = 40;

	private Scanner scan = new Scanner(System.in);
	private RandomManager ran = RandomManager.getInstance();

	private ArrayList<Unit> units = new ArrayList<>();

	private Hero hero;
	private int stage;

	private boolean isRun = true;

	private ZombieGame() {
	}

	private static ZombieGame instance = new ZombieGame();

	public static ZombieGame getInstance() {
		return instance;
	}

	public void run() {
		hero = new Hero();
		units.add(hero);
		while (isRun) {
			if (units.size() == 1)
				setStage();
			printMap();
			int sel = inputNum("[1]이동하기 [0]종료");

			if (sel == MOVE)
				play();
			else if (sel == EXIT) {
				System.out.println("게임 종료.");
				break;
			}
		}
	}

	private void setStage() {
		stage++;
		hero.reset();
		int pos = 0;
		for (int i = 0; i < stage; i++) {
			pos += ran.get(5, 10);
			Zombie zombie = new Zombie(50 + stage * 5, 10 + stage, pos);
			units.add(zombie);
		}
		pos += ran.get(10, 15);
		Boss boss = new Boss(100 + stage * 10, 15 + stage * 2, pos);
		units.add(boss);
		upgrade();
	}

	private void upgrade() {
		int point = stage;
		while (point > 0) {
			hero.showUpgrade();
			System.out.println("남은 포인트 : " + point);
			int sel = inputNum("업그레이드 선택");

			int hp = 0;
			int pow = 0;
			int acc = 0;
			if (sel == HP) {
				hp = 10;
				point--;
			} else if (sel == POW) {
				pow = 2;
				point--;
			} else if (sel == ACC && hero.getAccuracy() < 90) {
				acc = 1;
				point--;
			}
			hero.upgrade(hp, pow, acc);
		}
		hero.showUpgrade();
	}

	private void printMap() {
		String map = "\nSTAGE - " + stage;
		map += "\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n";
		int heroPos = hero.getPos();
		int size = units.size();
		ArrayList<String> unitsStr = new ArrayList<>();

		for (int i = 0; i < MAP_SIZE; i++)
			unitsStr.add(" ");
		for (int i = 0; i < size; i++) {
			Unit unit = units.get(i);
			int pos = unit.getPos();
			pos -= heroPos;
			pos += 2;
			if (pos >= 0 && pos < MAP_SIZE) {
				unitsStr.set(pos, unit.getSymbol());
			}
		}
		for (int i = 0; i < MAP_SIZE; i++)
			map += unitsStr.get(i);

		map += "\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n";
		map += hero + "\n" + hero.getGuageToString();
		System.out.println(map);
	}

	private void play() {
		move();
		action();
	}

	private void move() {
		int start = 0;
		int end = 0;
		int size = units.size();
		for (int i = 0; i < units.size(); i++) {
			Unit unit = units.get(i);
			if (i > 0)
				start = units.get(i - 1).getPos() + 1;
			if (i < size - 1)
				end = units.get(i + 1).getPos() - 1;
			unit.move(start, end);
		}
	}

	private void action() {
		int heroPos = hero.getPos();
		Unit enemy = units.get(1);
		boolean isFallDown = false;

		if (enemy.getPos() != heroPos + 1)
			return;

		printMap();
		while (true) {
			printBattle(enemy);
			int sel = inputNum("[1]공격하기 [2]필살기  [3]회피하기 [4]물약먹기");

			if (sel == ATTACK || sel == SPECIAL) {
				if (sel == SPECIAL && hero.getSpecialGuage() != 10) {
					System.out.println("스페셜게이지가 모자릅니다.");
					continue;
				} else if (sel == SPECIAL)
					hero.activeSpecial();

				int ranOrder = ran.get(2);
				if (ranOrder == 0) {// 공격순서 랜덤
					if (attack(hero, enemy) || (!isFallDown && attack(enemy, hero)))
						return;
				} else {
					if ((!isFallDown && attack(enemy, hero)) || attack(hero, enemy))
						return;
				}
			} else if (sel == AVOID) {
				int ranAvoid = ran.get(3);
				if (ranAvoid == 0) {
					System.out.println("회피를 실패했습니다");
					if (!isFallDown && attack(enemy, hero))
						return;
				} else {
					System.out.println("회피를 성공했습니다");
					System.out.println(enemy.getName() + "가 쓰러져 다음턴에 공격을 못 합니다.");
					isFallDown = true;
					continue;
				}
			} else if (sel == POTION) {
				if (hero.getPotion() == 0) {
					System.out.println("물약이 없습니다.");
					continue;
				}
				hero.recovery();
				if (!isFallDown && attack(enemy, hero))
					return;
			}
			isFallDown = false;
		}
	}

	private void printBattle(Unit enemy) {
		String msg = String.format("\nBATTLE - %s VS %s\n", hero.getName(), enemy.getName());
		msg += "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n";
		msg += String.format("%-18s%15s\n", hero, enemy);
		msg += hero.getGuageToString();
		msg += "\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒";
		System.out.println(msg);
	}

	private boolean attack(Unit attacker, Unit target) {
		boolean isDead = attacker.attack(target);
		if (isDead) {
			if (target instanceof Hero)
				isRun = false;
			else {
				units.remove(target);
				int item = ran.get(2);
				if (item == 0)
					hero.addPotion();
			}
		}
		return isDead;
	}

	private int inputNum(String msg) {
		System.out.print(msg + " : ");
		String input = scan.nextLine();

		try {
			int num = Integer.parseInt(input);
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
