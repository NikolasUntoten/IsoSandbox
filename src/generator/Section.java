package generator;

import game.Block;

public class Section {
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int LENGTH = 10;
	
	public static Block[][][] load(int x, int y, int z) {
		return World.generate(x, y, z, WIDTH, HEIGHT, LENGTH);
	}
	
	public static void save(Block[][][] section, int x, int y, int z) {
		
	}
}
