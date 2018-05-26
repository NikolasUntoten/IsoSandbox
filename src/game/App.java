package game;

import data.Data;
import graphics.AppWindow;
import graphics.Renderer;
import ui.Overlay;

public class App {
	
	public static final long WORLD_TICK = 20;
	
	private Renderer renderer;
	
	private AppWindow window;
	
	private Module[] modules;
	
	private Data data;
	
	private static boolean isRunning;
	
	public App(Module[] initModules, Renderer initRenderer) {
		modules = initModules;
		renderer = initRenderer;
		data = new Data();
		window = new AppWindow();
		
		isRunning = true;
		
		initializeModules();
		startGameLoop();
	}
	
	//Shuts the game down
	public static void Stop() {
		isRunning = false;
	}
	
	//runs initialization of all modules, as well as initializing overlays
	//most have 2 loops, to ensure full initialization of data before overlays are initialized
	private void initializeModules() {
		for (Module m : modules) {
			m.initialize(data);
		}
		for (Module m : modules) {
			Overlay ov = m.getOverlay(data);
			if (ov != null) {
				window.add(ov);
			}
		}
	}
	
	//initializes game loop in a thread
	private void startGameLoop() {
		new Thread() {
			@Override
			public void run() {
				refreshLoop();
			}
		}.start();
	}

	//game loop that is run from thread
	private void refreshLoop() {
		while (isRunning) {
			refresh();
			try {
				Thread.sleep(WORLD_TICK);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//set of methods run every refresh
	private void refresh() {
		for (Module m : modules) {
			m.update(data);
		}
		window.refresh(renderer.render(data, window.getWidth(), window.getHeight()));
	}
}
