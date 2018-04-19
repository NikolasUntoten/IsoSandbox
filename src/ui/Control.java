package ui;

import generator.Entity;
import generator.World;
import graphics.Camera;

/*
 * Class which contains user controls. Will rapidly change over time.
 * Attempt to keep this class clean.
 */

public class Control {
	
	protected final double MOVE_SPEED = 12;
	
	World world;
	Entity player;
	Camera camera;
	
	
	public Control(World w, Entity c, Camera cam) {
		world = w;
		player = c;
		camera = cam;
	}
	
	public void update() {
		
		player.velocity.x = player.velocity.x / 2;
		player.velocity.z = player.velocity.z / 2;
		
		if (Input.forward.isPressed) {
			player.velocity.z = -MOVE_SPEED;
		}
		if (Input.backward.isPressed) {
			player.velocity.z = MOVE_SPEED;
		}
		if (Input.left.isPressed) {
			player.velocity.x = -MOVE_SPEED;
		}
		if (Input.right.isPressed) {
			player.velocity.x = MOVE_SPEED;
		}
		if (Input.rotRight.isPressed) {
			camera.rotateRight();
			Input.rotRight.isPressed = false;
		}
		if (Input.rotLeft.isPressed) {
			camera.rotateLeft();
			Input.rotLeft.isPressed = false;
		}
		if (Input.jump.isPressed) {
			player.velocity.y = 30;
			Input.jump.isPressed = false;
		}
		camera.setPosition(player.position);
	}
}
