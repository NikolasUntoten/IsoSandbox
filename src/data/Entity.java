package data;

import java.awt.image.BufferedImage;

import Utility.Point3;

public class Entity {
	public Point3 position;
	public Point3 velocity;
	public Point3 force;
	public Point3 size;
	public double mass;
	
	public BufferedImage currentImage;
	
	public Entity() {
		position = new Point3(1, 5, 1);
		velocity = new Point3(0, 0, 0);
		force = new Point3(0, 0, 0);
		size = new Point3(1, 1, 1);
		mass = 100.0;
	}
	
	public BufferedImage makeImage(int scale) {
		if (currentImage != null) return currentImage;
		
		BufferedImage i = new BufferedImage((int) (size.x*scale), (int) ((size.y + size.z)*scale), BufferedImage.TYPE_INT_ARGB);
		i.getGraphics().fillRect(0, 0, (int) i.getWidth(), (int) i.getHeight());
		for (int x = 0; x < size.z; x++) {
			
		}
		return i;
	}
}
