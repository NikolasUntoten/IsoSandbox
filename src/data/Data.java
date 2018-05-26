package data;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private Block[][][] blocks;
	private List<Entity> entities;
	
	public Data() {
		blocks = new Block[0][0][0];
		entities = new ArrayList<Entity>();
	}
	
	public Block getBlock(int x, int y, int z) {
		return null;
	}
	
	public void setBlock(Block b, int x, int y, int z) {
		
	}
	
	public Entity[] getEntities() {
		return (Entity[]) entities.toArray();
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
}
