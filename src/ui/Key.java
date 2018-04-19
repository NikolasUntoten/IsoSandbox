package ui;

import java.awt.event.KeyEvent;

public class Key {
	public boolean isPressed;
	public int binding;
	public Mode mode;
	
	public Key() {
		binding = KeyEvent.VK_0;
		isPressed = false;
		mode = Mode.HOLD;
	}
	
	public Key(int bind) {
		binding = bind;
		isPressed = false;
		mode = Mode.HOLD;
	}
	public Key(int bind, Mode m) {
		binding = bind;
		isPressed = false;
		mode = m;
	}
	
	public void keyPressed() {
		if (mode == Mode.TAP) {
			tap();
		} else {
			isPressed = true;
		}
	}
	
	public void toggle() {
		isPressed = !isPressed;
	}
	
	public enum Mode {
		TAP, HOLD
	}
	
	//Override this method with action to be taken on tap.
	public void tap() {
		
	}
}
