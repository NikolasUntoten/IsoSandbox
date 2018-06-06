package core;

import javax.swing.JFrame;

import core.templates.Module;
import core.templates.Renderer;
import core.ui.GamePanel;
import core.ui.Overlay;
import core.ui.SetupPanel;

public class App {
	
	public static final long WORLD_TICK = 20;
	
	private static Renderer renderer;
	
	private static JFrame frame;
	
	private static GamePanel window;
	
	private static Module[] modules;
	
	private static Data data;
	
	private static boolean isRunning;
	
	public static void main(String[] args) {
		frame = new JFrame("RPG");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 300, 300);
		
		SetupPanel setup = new SetupPanel();
		frame.add(setup);
		
		frame.validate();
		frame.repaint();
	}
	
	public static void finishSetup(Module[] initModules, Renderer initRenderer, SetupPanel setup) {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.remove(setup);
		
		
		modules = initModules;
		renderer = initRenderer;
		data = new Data();
		window = new GamePanel();
		
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
	private static void initializeModules() {
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
	private static void startGameLoop() {
		new Thread() {
			@Override
			public void run() {
				refreshLoop();
			}
		}.start();
	}

	//game loop that is run from thread
	private static void refreshLoop() {
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
	private static void refresh() {
		for (Module m : modules) {
			m.update(data);
		}
		window.refresh(renderer.render(data, window.getWidth(), window.getHeight()));
	}
}
