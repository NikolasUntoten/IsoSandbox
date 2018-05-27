package graphics;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.KeyUpdater;

/*
 * Initiates game, updates via loops. Should do little to nothing more than this.
 */
public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607984926706552984L;
	
	private BufferedImage frame;

	public GamePanel() {
		
		frame = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		
		KeyListener k = new KeyUpdater();
		this.addKeyListener(k);
	}
	
	public void refresh(BufferedImage render) {
		frame = render;
		
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//TODO make it so that image scaling centers on center of image
		g.drawImage(frame, 0, 0, null);
	}
}
