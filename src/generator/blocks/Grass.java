package generator.blocks;

import java.awt.Color;

import generator.Block;

public class Grass extends Block {
	
	public Grass() {
		super();
		name = "Grass";
		mainColor = Color.GREEN;
		accentColor = new Color(25, 100, 25);
	}
}