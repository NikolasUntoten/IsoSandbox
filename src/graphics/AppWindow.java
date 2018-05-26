package graphics;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.KeyUpdater;
import ui.Overlay;

/*
 * Initiates game, updates via loops. Should do little to nothing more than this.
 */
public class AppWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607984926706552984L;
	
	private BufferedImage frame;

	public AppWindow() {
		super("RPG");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		this.add(new Panel());
		
		frame = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
		
		KeyListener k = new KeyUpdater();
		this.addKeyListener(k);
	}
	
	public void refresh(BufferedImage render) {
		frame = render;
		
		this.revalidate();
		this.repaint();
	}
	
	class Panel extends JPanel {
		
		public Panel() {
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			//TODO make it so that image scaling centers on center of image
			g.drawImage(frame, 0, 0, null);
		}
	}
}
