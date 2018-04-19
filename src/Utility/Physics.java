package Utility;

import java.util.HashSet;
import java.util.Set;

import generator.Block;
import generator.Entity;
import generator.World;

public class Physics {
	
	public static World world;
	public static final double GRAVITY = 1;
	
	public static void updateEntitySimple(World w, Entity e, double time) {
		
		world = w;
		
		Point3 newPos = new Point3(e.position.x, e.position.y, e.position.z);
		e.velocity.y -= GRAVITY;
		
		newPos.x += e.velocity.x * time;
		if (blocked(e, newPos)) {
			newPos.x -= e.velocity.x * time;
			e.velocity.x = 0;
		}
		
		newPos.y += e.velocity.y * time;
		if (blocked(e, newPos)) {
			newPos.y -= e.velocity.y * time;
			e.velocity.y = 0;
		}
		
		newPos.z += e.velocity.z * time;
		if (blocked(e, newPos)) {
			newPos.z -= e.velocity.z * time;
			e.velocity.z = 0;
		}
		
		e.position = newPos;
		
		//System.out.println(e.position);
	}
	
	private static boolean blocked(Entity e, Point3 pos) {
		
		double xSize = (e.size.x);
		double ySize = (e.size.y);
		double zSize = (e.size.z);
		
		for (int x = 0; x <= xSize; x++) {
			for (int y = 0; y <= ySize; y++) {
				for (int z = 0; z <= zSize; z++) {
					int tempx = (int) (x + pos.x - e.size.x/2);
					int tempy = (int) (y + pos.y - e.size.y/2);
					int tempz = (int) (z + pos.z - e.size.z/2);
					if (withinBounds(tempx, tempy, tempz)) {
						if (world.blocks[tempx][tempy][tempz] != null) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private static boolean withinBounds(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < world.WIDTH && y < world.HEIGHT && z < world.LENGTH;
	}
}
