package game;

import data.Data;
import graphics.AppWindow;
import graphics.Renderer;
import ui.Overlay;

public class App {
	
	public static final long WORLD_TICK = 20;
	
	private Renderer renderer;
	
	private Overlay overlay;
	
	private AppWindow window;
	
	private Module[] modules;
	
	private Data data;
	
	private static boolean isRunning;
	
	public App(Module[] initModules, Renderer initRenderer) {
		modules = initModules;
		renderer = initRenderer;
		data = new Data();
		overlay = new Overlay();
		window = new AppWindow();
		
		isRunning = true;
		
		initialize();
		loop();
	}
	
	public static void Stop() {
		isRunning = false;
	}
	
	//runs initialization of all modules
	private void initialize() {
		for (Module m : modules) {
			m.initialize(data);
		}
	}
	
	//initializes game loop in a thread
	private void loop() {
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
		window.refresh(renderer.render(data), overlay.render(data, modules));
	}
}
