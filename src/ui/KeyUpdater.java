package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyUpdater implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		for (Key k : Input.KEY_ARRAY) {
			if (k.binding == e.getKeyCode()) {
				k.keyPressed();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (Key k : Input.KEY_ARRAY) {
			if (k.binding == e.getKeyCode()) {
				k.isPressed = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
