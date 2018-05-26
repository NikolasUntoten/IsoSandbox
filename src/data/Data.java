package data;

import java.util.ArrayList;
import java.util.List;

import Utility.ShiftingArray;

public class Data {
	
	private ShiftingArray<Block> blocks;
	
	private List<Entity> entities;
	
	public Data() {
		blocks = new ShiftingArray<Block>();
		entities = new ArrayList<Entity>();
	}
	
	public Block getBlock(int x, int y, int z) {
		return blocks.get(x, y, z);
	}
	
	public void setBlock(Block b, int x, int y, int z) {
		blocks.set(b, x, y, z);
	}
	
	public void destroyBlock(int x, int y, int z) {
		blocks.set(null, x, y, z);
	}
	
	public Entity[] getEntities() {
		return (Entity[]) entities.toArray();
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
}
