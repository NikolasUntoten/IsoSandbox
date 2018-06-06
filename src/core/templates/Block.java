package core.templates;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import Utility.ImageUtil;

public class Block {
	
	public static final int WIDTH = 8;
	public static final int HEIGHT = 16;
	
	protected String name;
	
	public double MU_S;
	public double MU_K;
	
	protected Color mainColor;
	protected Color accentColor;
	
	private BufferedImage currentImage;
	
	private GraphicsConfiguration config;
	
	public Block() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		config = device.getDefaultConfiguration();
		
		name = "Generic";
		mainColor = Color.WHITE;
		accentColor = Color.BLACK;
		
		MU_S = 0.02;
		MU_K = 0.2;
	}
	
	public void refreshImage() {
		currentImage = null;
	}
	
	public BufferedImage getImage(boolean left, boolean right, boolean up, boolean down, boolean front, boolean back, boolean nook) {
		
		if (currentImage != null) return currentImage;
		
		BufferedImage image = config.createCompatibleImage(WIDTH, HEIGHT, Transparency.OPAQUE);
	    Graphics g = image.getGraphics();
	    
	    BufferedImage mainTexture = ImageUtil.loadImage(name + ".png");
		
	    if (mainTexture != null) {
	    	g.drawImage(mainTexture, 0, 0, null);
	    	g.drawImage(mainTexture, 0, HEIGHT/2, null);
	    } else if (mainColor != null){
	    	g.setColor(mainColor);
			g.fillRect(0, 0, WIDTH, HEIGHT);
	    } else {
	    	g.setColor(Color.WHITE);
			g.fillRect(0, 0, WIDTH, HEIGHT);
	    }
		
		g.setColor(accentColor);
		if (!left) g.fillRect(0, 0, 1, HEIGHT);
		if (!right) g.fillRect(WIDTH-1, 0, 1, HEIGHT);
		if (!up) g.fillRect(0, HEIGHT/2, WIDTH, 1);
		if (!down || nook) g.fillRect(0, HEIGHT-1, WIDTH, 1);
		if (!front) g.fillRect(0, HEIGHT/2-1, WIDTH, 1);
		if (!back) g.fillRect(0, 0, WIDTH, 1);
		
		currentImage = image;
		return image;
	}
	
	public String toString() {
		return "Block[" + name + "]";
	}
}
