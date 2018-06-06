package core.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;

public class Input {
	
	public static Point mousePosition;
	
	public static final Key forward = new Key(KeyEvent.VK_W);
	public static final Key backward = new Key(KeyEvent.VK_S);
	public static final Key left = new Key(KeyEvent.VK_A);
	public static final Key right = new Key(KeyEvent.VK_D);
	public static final Key rotRight = new Key(KeyEvent.VK_E);
	public static final Key rotLeft = new Key(KeyEvent.VK_Q);
	public static final Key jump = new Key(KeyEvent.VK_SPACE);
	
	public static final Key[] KEY_ARRAY = new Key[] { forward, backward, left, right, rotRight, rotLeft, jump };
	
}
