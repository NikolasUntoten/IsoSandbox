package Utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	private static HashMap<String, Image> map = new HashMap<String, Image>();
	private static String currentDir = System.getProperty("user.dir");
	
	public static BufferedImage loadImage(String name) {
		Image i;
		if (map.containsKey(name)) {
			return (BufferedImage) map.get(name);
		}
		try {
			String path = "/resources/blocks/" + name;
			URL url = ImageUtil.class.getResource(path);
			if (url == null) return null;
			i = ImageIO.read(url);
			map.put(name, i);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return (BufferedImage) i;
	}
}
