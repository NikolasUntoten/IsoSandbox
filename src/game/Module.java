package game;

import java.awt.image.BufferedImage;

public interface Module {
	public void update(Data data);
	public void initialize(Data data);
	public BufferedImage getOverlay(Data data);
}
