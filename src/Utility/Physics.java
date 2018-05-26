package Utility;

import data.Block;
import data.Data;
import data.Entity;

public class Physics {
	
	public static Data data;
	public static final double GRAVITY = 1;
	
	public static void updateEntitySimple(Data d, Entity e, double time) {
		
		data = d;
		
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
					if (data.getBlock(tempx, tempy, tempz) != null) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
