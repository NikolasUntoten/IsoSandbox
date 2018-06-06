package renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.JPanel;

import Utility.Point3;
import core.Data;
import core.templates.Block;
import core.templates.Entity;
import core.templates.Renderer;

/*
 * Renders the blocks in a given world.
 * NOTE: starts at the top of the array, and goes down.
 * The y position on screen is inverted, but since the array 
 * is traversed starting from the top, this should not be a problem.
 */

public class IsoRenderer implements Renderer {
	
	private Point3 position;
	private int scale;
	private Direction direction;
	
	public enum Direction {NORTH, EAST, SOUTH, WEST};
	
	public IsoRenderer() {
		scale = 30;
		position = new Point3(0, 0, 0);
		direction = Direction.NORTH;
	}
	
	public void move(Point3 dist) {
		position.x += dist.x;
		position.y += dist.y;
		position.z += dist.z;
	}
	
	public void move(double x, double y, double z) {
		position.x += x;
		position.y += y;
		position.z += z;
	}
	
	public void setDirection(Direction d) {
		direction = d;
	}
	
	public void rotateRight() {
		switch (direction) {
		case NORTH: direction = Direction.EAST;
			break;
		case EAST: direction = Direction.SOUTH;
			break;
		case SOUTH: direction = Direction.WEST;
			break;
		case WEST: direction = Direction.NORTH;
			break;
		}
	}
	
	public void rotateLeft() {
		switch (direction) {
		case NORTH: direction = Direction.WEST;
			break;
		case EAST: direction = Direction.NORTH;
			break;
		case SOUTH: direction = Direction.EAST;
			break;
		case WEST: direction = Direction.SOUTH;
			break;
		}
	}
	
	public void setPosition(int x, int y, int z) {
		position = new Point3(x, y, z);
	}
	
	@Override
	public BufferedImage render(Data data, int width, int height) {
		long startTime = System.currentTimeMillis();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		drawWorld(g);
		
		g.setColor(Color.BLACK);
		g.drawString("Render Time: " + (System.currentTimeMillis() - startTime), 15, 15);
		return null;
	}
	
	private void drawWorld(Graphics g) {
		
		Map<Point, Set<Entity>> entities = getEntities();
		
		for (int y = 0; y < blocks[0].length; y++) {
			for (int z = 0; z < blocks[0][0].length; z++) {
				Set<Entity> temp = entities.get(new Point(y, z));
				if (temp != null) {
					for (Entity e : temp) drawEntity(g, e, position);
				}
				for (int x = 0; x < blocks.length; x++) {
					if (world.getBlock(x, y, z) != null && !blocked(x, y, z))
						drawBlock(g, x, y, z, position);
				}
				
			}
		}
	}
	
	private Map<Point, Set<Entity>> getEntities() {
		Map<Point, Set<Entity>> map = new HashMap<Point, Set<Entity>>();
		Stack<Entity> temp = new Stack<Entity>();
		for (Entity e : world.entities) temp.push(e);
		
		while (temp.size() > 0) {
			Entity e = temp.pop();
			Point key = new Point((int) (e.position.y - e.size.y/2), (int) (e.position.z - e.size.z/2)); //determines key
			if (!map.containsKey(key)) map.put(key, new HashSet<Entity>()); //if no set at key, create set
			map.get(key).add(e); //adds entity to set at key
		}
		return map;
	}
	
	private boolean blocked(int x, int y, int z) {
		y++;
		z++;
		while (y < world.blocks[0].length && z < world.blocks[0][0].length) {
			if (world.getBlock(x, y, z, direction) != null) return true;
			y++;
			z++;
		}
		return false;
	}
	
	private boolean onscreen(int x, int y, int width, int height) {
		if (x + width < 0 || x > getWidth() || y + height < 0 || y > getHeight()) {
			return false;
		}
		return true;
	}
	
	private void drawEntity(Graphics g, Entity e, Point3 pos) {
		double relativeX = e.position.x - e.size.x/2 - pos.x;
		int cameraX = (int) (relativeX*scale);
		
		double relativeY = e.position.y + e.size.y/2 - pos.y;
		double relativeZ = e.position.z + e.size.z/2 - pos.z;
		int cameraY = (int) ((relativeZ-relativeY)*scale);
		
		int width = (int) (scale * (e.size.x));
		int height = (int) (scale * (e.size.y + e.size.z));
		if (onscreen(cameraX, cameraY, width, height)) {
			g.drawImage(e.makeImage(scale), cameraX, cameraY, width, height, null);
		}
	}
	
	private void drawBlock(Graphics g, int x, int y, int z, Point3 pos) {
		
		//pos = new Point3((int) pos.x, (int) pos.y, (int) pos.z);
		
		double relativeX = x - pos.x;
		int cameraX = (int) (relativeX*scale);
		
		double relativeY = y - pos.y;
		double relativeZ = z - pos.z;
		int cameraY = (int) ((relativeZ-relativeY)*scale);
		
		int width = (int) (scale+1);
		int height = (int) (scale*2+1);
		
		if (onscreen(cameraX, cameraY, width, height)) {
			BufferedImage image = world.getBlock(x, y, z, direction).getImage(
					connectedX(x, y, z, false), 
					connectedX(x, y, z, true),
					connectedY(x, y, z, true), 
					connectedY(x, y, z, false),
					connectedZ(x, y, z, true), 
					connectedZ(x, y, z, false),
					nook(x, y, z));
			g.drawImage(image, cameraX, cameraY, width, height, null);
		}
	}
	
	
	//I am sorry from here down. I am too lazy to write this cleanly
	private boolean connectedX(int x, int y, int z, boolean direction) {
		int off = direction ? 1 : -1;
		if (x+off < 0 || x+off >= world.blocks.length) return false;
		if (world.blocks[x+off][y][z] != null) return true;
		
		return false;
	}
	
	private boolean connectedY(int x, int y, int z, boolean direction) {
		int off = direction ? 1 : -1;
		if (y+off < 0 || y+off >= world.blocks[0].length) return false;
		if (world.blocks[x][y+off][z] != null) return true;
		
		return false;
	}
	
	private boolean connectedZ(int x, int y, int z, boolean direction) {
		int off = direction ? 1 : -1;
		if (z+off < 0 || z+off >= world.blocks[0][0].length) return false;
		if (world.blocks[x][y][z+off] != null) return true;
		
		return false;
	}
	
	private boolean nook(int x, int y, int z) {
		if (y-1 < 0 || z+1 >= world.blocks[0][0].length) return false;
		if (world.blocks[x][y-1][z+1] != null) return true;
		
		return false;
	}
}
