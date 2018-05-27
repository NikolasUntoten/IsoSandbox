package graphics;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import game.App;
import game.Module;

public class SetupPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3624770630284325178L;
	
	private Module[] modules;
	
	private Renderer renderer;

	public SetupPanel() {
		
		this.setLayout(new GridLayout());
		
		findModules();
		findRenderers();
		
		addRendererSelector();
		addModuleBoxes();
		addFinishButton();
	}
	
	private void findModules() {
		
	}
	
	private void findRenderers() {
		
	}
	
	private void addRendererSelector() {
	
	}
	
	private void addModuleBoxes() {
		JCheckBox box1 = new JCheckBox("Test Box");
		box1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				box1.isSelected();
			}
		});
		this.add(box1);
	}
	
	private void addFinishButton() {
		SetupPanel panel = this;
		
		JButton finish = new JButton("Launch game");
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.finishSetup(modules, renderer, panel);
			}
		});
		this.add(finish);
	}

}
