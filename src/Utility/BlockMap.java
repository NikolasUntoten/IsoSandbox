package Utility;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import game.Block;

/*
 * Map which stores blocks in 3 dimensions, and is able to "shift" easily
 * shifting is defined as moving currently stored blocks in a direction, 
 * unloading those which are outside the bounds of the map. New content is loaded to fill
 * the space left after the shift.
 * 
 * Uses  as the primary data structure.
 */

public class BlockMap {
	
	private Map<Point, Block> data;
	private static int width;
	private static int length;
	private static int height;
	private static Point center;
	
	public BlockMap() {
		data = new HashMap<Point, Block>();
		width = 0;
		length = 0;
		height = 0;
		center = new Point(0, 0, 0);
	}
	
	public BlockMap(int w, int l, int h) {
		data = new HashMap<Point, Block>();
		width = w;
		length = l;
		height = h;
		center = new Point(0, 0, 0);
	}
	
	public Block[][][] getBlocks() {
		Block[][][] arr = new Block[width][length][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					arr[x][y][z] = getBlock(x, y, z);
				}
			}
		}
		return null;
	}
	
	public Block getBlock(int x, int y, int z) {
		return null;
	}
	
	public void setBlock(int x, int y, int z) {
		
	}
	
	public void moveCenter(int deltaX, int deltaY, int deltaZ) {
		center.x += deltaX;
		center.y += deltaY;
		center.z += deltaZ;
		
		updateData();
	}
	
	private void updateData() {
		for (Block b : data.values()) {
			save(b);
			if (outOfRange(b)) remove(b);
		}
	}
	
	private void save(Block b) {
		
	}
	
	private void remove(Block b) {
		
	}
	
	private boolean outOfRange(Block b) {
		return false;
	}
	
	private void load() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					int realX = x + center.x;
					int realY = y + center.y;
					int realZ = z + center.z;
					if (!data.containsKey(new Point3(realX, realY, realZ))) {
						loadBlock(realX, realY, realZ);
					}
				}
			}
		}
	}
	
	private void loadBlock(int x, int y, int z) {
		
	}
	
	private class Point {
		public int x;
		public int y;
		public int z;
		
		public Point() {
			x = 0;
			y = 0;
			z = 0;
		}
		
		public Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
