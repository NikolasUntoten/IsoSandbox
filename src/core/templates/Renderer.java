package core.templates;

import java.awt.image.BufferedImage;

import core.Data;

/*
 * Returns an image of the given data set
 */

public interface Renderer {
	public BufferedImage render(Data data, int width, int height);
}
