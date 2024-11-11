package zombie;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ZombieGame {
	private final int MOVE = 1;
	private final int EXIT = 0;

	private Scanner scan = new Scanner(System.in);
	private RandomManager ran = RandomManager.getInstance();

	private ArrayList<Unit> units = new ArrayList<>();

	private Hero hero;
	private int stage;

	private String loads = "";

	private ZombieGame() {
	}

	private static ZombieGame instance = new ZombieGame();

	public static ZombieGame getInstance() {
		return instance;
	}

	public void run() {
		hero=new Hero();
		units.add(hero);
		while (true) {
			if (units.size() == 1)
				setStage();
			int sel = inputNum("[1]이동하기 [0]종료");

			if (sel == MOVE)
				move();
			else if (sel == EXIT) {
				System.out.println("게임 종료.");
				break;
			}
		}

//		setGame();
//		
//		String props= "@#%&$";
//		
//		int pos = 0;
//		for(int i=0;i<100;i++) {
//			loads+=props.charAt(ran.nextInt(props.length())) ;
//		}
//		String def="▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒";
//		while(pos+20 < loads.length()) {
//			int defMax=def.length();
//			int defPos=pos % defMax;
//			String map="";
//			if(defPos+20>defMax) {
//				map=def.substring(defPos, defMax);
//				map+=def.substring(0, defPos+20-defMax);
//			}else
//				map=def.substring(defPos, defPos+20);
//			System.out.println("====================");
//			System.out.println(map);
//			System.out.println("    H        Z    B");
//			
//			System.out.println(map);
//			System.out.println(loads.substring(pos, pos+20));
//			System.out.println("--------------------");
//			String input= scan.nextLine();
//			System.out.println(pos);
//			pos++;
//		}
	}

	private void setStage() {
		stage++;
		int pos = 0;
		for (int i = 0; i < stage; i++) {
			pos += ran.get(5, 10);
			Zombie zombie = new Zombie(100 + stage * 10, 10 + stage, pos);
			units.add(zombie);
		}
		pos += ran.get(5, 10);
		Boss boss = new Boss(200 + stage * 50, 20 + stage, pos);
		units.add(boss);
	}

	private void move() {
		
		for(int i = 0; i < units.size(); i++) {
			Unit unit=units.get(i);
			unit.move();

		}
	}

	private int inputNum(String msg) {
		System.out.print(msg + " : ");
		String input = scan.nextLine();

		try {
			int num = Integer.parseInt(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
