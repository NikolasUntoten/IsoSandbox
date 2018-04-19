package graphics;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

import generator.Entity;
import generator.World;
import ui.Control;
import ui.Input;
import ui.KeyUpdater;

/*
 * Initiates game, updates via loops. Should do little to nothing more than this.
 */
public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607984926706552984L;
	
	
	private static final long FPS = 60; // Screen refreshes per second.
	private static final long UPS = 120; // World updates per second.

	protected boolean isRunning;
	protected World world;
	protected Control control;
	protected Entity player;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		super("RPG");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		KeyListener k = new KeyUpdater();
		this.addKeyListener(k);
		
		player = new Entity();
		
		world= new World(player);
		Camera c = new Camera(world);
		c.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.add(c);
		
		control = new Control(world, player, c);

		isRunning = true;
		startRefreshLoop();
		startWorldLoop();
	}

	public void Quit() {
		isRunning = false;
		System.exit(0);
	}

	private void startRefreshLoop() {
		new Thread() {
			@Override
			public void run() {
				refreshLoop();
			}
		}.start();
	}

	private void refreshLoop() {
		while (isRunning) {
			refresh();
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void refresh() {
		revalidate();
		repaint();
	}

	private void startWorldLoop() {
		new Thread() {
			@Override
			public void run() {
				worldLoop();
			}
		}.start();
	}

	private void worldLoop() {
		while (isRunning) {
			worldUpdate();
			try {
				Thread.sleep(1000 / UPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void worldUpdate() {
		control.update();
		world.update(1.0 / UPS);
	}
}
