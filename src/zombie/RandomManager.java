package zombie;

import java.util.Random;

public class RandomManager {
	private Random ran = new Random();

	private RandomManager() {
	}

	private static RandomManager instance = new RandomManager();

	public static RandomManager getInstance() {
		return instance;
	}

	public int get(int length) {
		return ran.nextInt(length);
	}

	public int get(int start, int end) {
		return ran.nextInt(end - start + 1) + start;
	}

}
