package generator;

import java.util.HashSet;
import java.util.Set;

import Utility.Physics;
import Utility.Point3;
import Utility.Simplex;
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

		for (int x = 0; x < WIDTH; x++) {
			// for (int y = 0; y < HEIGHT; y++) {
			for (int z = 0; z < LENGTH; z++) {
				blocks[x][0][z] = new Grass();
			}
			// }
		}
		
		//blocks[1][1][1] = new Block();
		//blocks[2][1][1] = new Block();
		//blocks[1][1][2] = new Block();
		
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
		if (x < 0 || y < 0 || z < 0 ||
			x >= WIDTH || y >= HEIGHT || z >= LENGTH) {
				return null;
		}
		switch (dir) {
		case NORTH: return blocks[x][y][z];
		case EAST: return blocks[z][y][x];
		case SOUTH: return blocks[x][y][LENGTH-1-z];
		case WEST: return blocks[z][y][x];
		default: return blocks[WIDTH-1-x][y][z];
		}
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
		handleSectionLoad();
	}
	
	private void handleSectionLoad() {
		int minX = WIDTH/2 - Section.WIDTH/2;
		int maxX = WIDTH/2 + Section.WIDTH/2;
		int minY = HEIGHT/2 - Section.HEIGHT/2;
		int maxY = HEIGHT/2 + Section.HEIGHT/2;
		int minZ = LENGTH/2 - Section.LENGTH/2;
		int maxZ = LENGTH/2 + Section.LENGTH/2;
		
		if (lockedTarget.position.x - loadedPosition.x < minX) {
			loadedPosition.x -= Section.WIDTH;
			loadSectionLeft();
			System.out.println("loading left");
		}
		if (lockedTarget.position.x - loadedPosition.x > maxX) {
			loadedPosition.x += Section.WIDTH;
			loadSectionRight();
		}
		if (lockedTarget.position.y - loadedPosition.y < minY) {
			loadedPosition.y -= Section.HEIGHT;
			loadSectionDown();
		}	
		if (lockedTarget.position.y - loadedPosition.y > maxY) {
			loadedPosition.y += Section.HEIGHT;
			loadSectionUp();
		}
		if (lockedTarget.position.z - loadedPosition.z < minZ) {
			loadedPosition.z -= Section.LENGTH;
			loadSectionBack();
		}
		if (lockedTarget.position.z - loadedPosition.z > maxZ) {
			loadedPosition.z += Section.LENGTH;
			loadSectionFront();
		}
	}
	

	private void loadSectionLeft() {
		for (int i = 0; i < HEIGHT / Section.HEIGHT; i++) {
			for (int j = 0; j < LENGTH / Section.LENGTH; j++) {
				//Section.save(subsection(WIDTH-Section.WIDTH, Section.HEIGHT*i, Section.LENGTH*j), (int) loadedPosition.x, (int) loadedPosition.y, (int) loadedPosition.z);
			}
		}
		
		for (int i = 0; i < WIDTH - Section.WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				for (int k = 0; k < LENGTH; k++) {
					//blocks[i+Section.WIDTH][j][k] = blocks[i][j][k];
				}
			}
		}
		
		for (int i = 0; i < HEIGHT / Section.HEIGHT; i++) {
			for (int j = 0; j < LENGTH / Section.LENGTH; j++) {
				Block[][][] section = Section.load(0, i * Section.HEIGHT, j * Section.LENGTH);
				//paste(blocks, section, 0, i * Section.HEIGHT, j * Section.LENGTH);
			}
		}
	}
	
	private Block[][][] subsection(int x, int y, int z) {
		Block[][][] arr = new Block[Section.WIDTH][Section.HEIGHT][Section.LENGTH];
		for (int i = 0; i < Section.WIDTH; i++) {
			for (int j = 0; j < Section.HEIGHT; j++) {
				for (int k = 0; k < Section.LENGTH; k++) {
					arr[i][j][k] = blocks[x+i][y+j][z+k];
				}
			}
		}
		return arr;
	}
	
	private void paste(Block[][][] world, Block[][][] section, int x, int y, int z) {
		for (int i = 0; i < Section.WIDTH; i++) {
			for (int j = 0; j < Section.HEIGHT; j++) {
				for (int k = 0; k < Section.LENGTH; k++) {
					world[x + i][y + j][z + k] = section[i][j][k];
				}
			}
		}
	}
	
	private void loadSectionRight() {
		
	}
	
	private void loadSectionDown() {
		
	}
	
	private void loadSectionUp() {
		
	}
	
	private void loadSectionBack() {
		
	}
	
	private void loadSectionFront() {
		
	}
}
