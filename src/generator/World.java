package generator;

import java.util.HashSet;
import java.util.Set;

import Utility.Physics;
import Utility.Point3;
import Utility.Simplex;
import game.Block;
import game.Entity;
import generator.blocks.Brick;
import generator.blocks.Grass;
import graphics.Camera;

public class World {

	public static final int WIDTH = 200;
	public static final int HEIGHT = 20;
	public static final int LENGTH = 200;

	public Block[][][] blocks;
	public Set<Entity> entities;
	public Point3 loadedPosition;
	public Entity lockedTarget;

	public World(Entity target) {
		lockedTarget = target;
		loadedPosition = new Point3(0, 0, 0);

		blocks = new Block[WIDTH][HEIGHT][LENGTH];

		entities = new HashSet<Entity>();
		entities.add(lockedTarget);
		
		generateTerrain();
		//DungeonGenerator.generate2D(this, WIDTH, 2, LENGTH, 5);
		// dome(15, 4, 15, 5);
	}

	private void dome(int x, int y, int z, int r) {
		for (int i = x - r; i < x + r; i++) {
			for (int j = y; j < y + r; j++) {
				for (int k = z - r; k < z + r; k++) {
					if (Math.sqrt((i - x) * (i - x) + (j - y) * (j - y) + (k - z) * (k - z)) < r)
						blocks[i][j][k] = new Grass();
				}
			}
		}
	}
	
	public static Block[][][] generate(int x, int y, int z, int width, int height, int length) {
		Block[][][] arr = new Block[width][height][length];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				arr[i][0][j] = new Grass();
			}
		}
		return arr;
	}
	
	public Block getBlock(int x, int y, int z, Camera.Direction dir) {
		switch (dir) {
		case NORTH :
			break;
		case EAST :
			break;
		case SOUTH :
			break;
		case WEST :
			break;
		}
		
		if (x < 0 || y < 0 || z < 0 ||
			x >= WIDTH || y >= HEIGHT || z >= LENGTH) {
				return null;
		}
		return blocks[x][y][z];
	}
	
	public Block getBlock(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 ||
			x >= WIDTH || y >= HEIGHT || z >= LENGTH) {
				return null;
		}
		return blocks[x][y][z];
	}

	private void generateTerrain() {
		for (int x = 0; x < WIDTH; x++) {
			for (int z = 0; z < LENGTH; z++) {
				double h = Simplex.noise(x*0.05, z*0.05) * 2 + 2;
				for (int y = 0; y < h; y++) {
					blocks[x][y][z] = new Grass();
				}
			}
		}
	}

	public void update(double time) {
		for (Entity e : entities) {
			// Physics.updateEntity(this, e, time);
			Physics.updateEntitySimple(this, e, time);
		}
	}
}