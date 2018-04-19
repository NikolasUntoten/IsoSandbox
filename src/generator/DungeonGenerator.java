package generator;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import generator.blocks.Stone;

public class DungeonGenerator {
	
	private static final int ROOM_SIZE = 10;
	private static final int HALLWAY_LENGTH = 30;
	
	private static int MaxDensity;
	
	public static void generate2D(World w, int width, int height, int length, int density) {
		boolean[][][] blocks = new boolean[width][height][length];
		MaxDensity = density;
		new Room(width/2, 0, length/2, rand(ROOM_SIZE,1) + 2, height, rand(ROOM_SIZE,1) + 2, 4, blocks, 0);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					if (!blocks[x][y][z])
						w.blocks[x][y][z] = new Stone();
				}
			}
		}
	}
	
	static int rand(int value, int power) {
		double r = Math.random();
		double temp = 1;
		for (int i = 0; i < power; i++) {
			temp *= r;
		}
		return (int) (temp*value) + 1;
	}
	
	static class Room {
		int x, y, z, width, height, length, doors;
		
		public Room(int initx, int inity, int initz, int w, int h, int l, int d, boolean[][][] blocks, int count) {
			x = initx;
			y = inity;
			z = initz;
			width = w;
			height = h;
			length = l;
			doors = d;
			if (count < MaxDensity) {
				makeDoors(blocks, count);
			}
			System.out.println(this);
			clear(blocks);
		}
		
		void makeDoors(boolean[][][] blocks, int count) {
			for (int i = 0; i < doors - 1; i++) {
				int r = rand(4,1);
				Point p;
				switch (r) {
				case 0: p = makeHall(new Point(x-width/2, z+rand(length,1)), new Point(x - rand(HALLWAY_LENGTH,1)-width/2, z), blocks);
					break;
				case 1: p = makeHall(new Point(x+width/2, z+rand(length,1)), new Point(x + rand(HALLWAY_LENGTH,1)+width/2, z), blocks);
					break;
				case 2: p = makeHall(new Point(x+rand(width/2,1), z-length/2), new Point(x, z - rand(HALLWAY_LENGTH,1)-length/2), blocks);
					break;
				case 3: p = makeHall(new Point(x+rand(width/2,1), z+length/2), new Point(x, z + rand(HALLWAY_LENGTH,1)+length/2), blocks);
					break;
				default: p = makeHall(new Point(x, z), new Point(x, z), blocks);
						
				}
				count++;
				makeRoom(p.x, p.y, blocks, count);
			}
		}
		
		Point makeHall(Point p1, Point p2, boolean[][][] blocks) {
			int width = blocks.length;
			int length = blocks[0][0].length;
			int height = blocks[0].length;
			
			if (p2.x < 0) return p1;
			if (p2.x >= width-1) return p1;
			if (p2.y < 0) return p1;
			if (p2.y >= length-1) return p1;
			
			while (p1.x != p2.x) {
				for (int i = 0; i < height; i++) {
					blocks[p1.x][i][p1.y] = true;
				}
				if (p1.x != p2.x) {
					p1.x += Math.abs(p2.x - p1.x) / (p2.x - p1.x);
				}
			}
			while (p1.y != p2.y) {
				for (int i = 0; i < height; i++) {
					blocks[p1.x][i][p1.y] = true;
				}
				if (p1.y != p2.y) {
					p1.y += Math.abs(p2.y - p1.y) / (p2.y - p1.y);
				}
			}
			return p2;
		}
		
		void makeRoom(int x, int z, boolean[][][] blocks, int count) {
			int width = blocks.length;
			int length = blocks[0][0].length;
			int height = blocks[0].length;
			
			int roomW = 10;//rand(ROOM_SIZE,1) + 2;
			int roomL = 10;//rand(ROOM_SIZE,1) + 2;
			if (roomW/2 + x >= width) {
				roomW = width - x - 1;
			}
			if (x - roomW/2 < 0) {
				roomW = x/2;
			}
			if (roomL/2 + z >= length) {
				roomL = length - z - 1;
			}
			if (z - roomL/2 < 0) {
				roomL = z/2;
			}
			Room r = new Room(x-roomW/2, 0, z-roomL/2, roomW, height, roomL, rand(6,2), blocks, count);
			System.out.println(r);
		}
		
		void clear(boolean[][][] blocks) {
			for (int i = x; i < width + x; i++) {
				for (int j = y; j < height + y; j++) {
					for (int k = z; k < length + z; k++) {
						blocks[i][j][k] = true;
					}
				}
			}
		}
		
		public String toString() {
			return "Room is " + width + "x" + height + "x" + length + " at (" + x + ", " + y + ", " + z + "), width " + doors + " doors.";
		}
	}
}
