package graphics;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.ServiceLoader;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import Utility.Loader;
import data.Data;
import game.App;
import game.Module;
import ui.Overlay;

public class SetupPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3624770630284325178L;
	
	private Map<Module, Boolean> modules;
	
	private Renderer renderer;

	public SetupPanel() {
		
		this.setLayout(new GridLayout());
		
		findRenderers();
		
		addRendererSelector();
		addModuleBoxes();
		addFinishButton();
	}
	
	private void findModules() {
		ServiceLoader<Module> loader = Loader.getLoader(Module.class);
		for (Module m : loader) {
			modules.put(m, false);
		}
	}
	
	private void findRenderers() {
		
	}
	
	private void addRendererSelector() {
	
	}
	
	private void addModuleBoxes() {
		for (Module m : modules.keySet()) {
			this.add(createModuleBox(m));
		}
	}
	
	private JCheckBox createModuleBox(Module module) {
		JCheckBox box = new JCheckBox(module.toString());
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modules.put(module, box.isSelected());
			}
		});
		return box;
	}
	
	private void addFinishButton() {
		SetupPanel panel = this;
		
		JButton finish = new JButton("Launch game");
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.finishSetup(modules.keySet().toArray(new Module[0]), renderer, panel); //TODO clean this up
			}
		});
		this.add(finish);
	}

}
