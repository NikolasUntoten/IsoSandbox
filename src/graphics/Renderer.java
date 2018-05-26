package graphics;

import java.awt.image.BufferedImage;

import data.Data;

/*
 * Returns an image of the given data set
 */

public interface Renderer {
	public BufferedImage render(Data data);
}
