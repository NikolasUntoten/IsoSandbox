package game;

import javax.swing.JFrame;

import graphics.AppWindow;

public class Setup {

	public static void main(String[] args) {
		JFrame frame = new SetupWindow();
	}
	
	private static class SetupWindow extends JFrame {
		public SetupWindow() {
			super("RPG");
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setVisible(true);
			this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		}
	}
}
