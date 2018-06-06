package core.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Utility.Loader;
import core.App;
import core.templates.Module;
import core.templates.Renderer;

public class SetupPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3624770630284325178L;
	
	private Map<Module, Boolean> modules;
	
	private Renderer renderer;
	
	private GridBagConstraints c;

	public SetupPanel() {
		
		c = new GridBagConstraints();
		c.ipadx = 20;
		c.ipady = 10;
		
		this.setLayout(new GridBagLayout());
		
		modules = new HashMap<Module, Boolean>();
		
		findModules();
		
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
	
	private void addModuleBoxes() {
		int count = 0;
		for (Module m : modules.keySet()) {
			c.gridx = 2;
			c.gridy = count;
			c.gridheight = 1;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.EAST;
			this.add(createModuleBox(m), c);
			count++;
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
	
	private void addRendererSelector() {
		ServiceLoader<Renderer> loader = Loader.getLoader(Renderer.class);
		ButtonGroup group = new ButtonGroup();
		
		int count = 0;
		for (Renderer r : loader) {
			JRadioButton b = createRendererButton(r);
			group.add(b);
			
			c.gridx = 0;
			c.gridy = count;
			c.gridheight = 1;
			c.gridwidth = 2;
			c.anchor = GridBagConstraints.WEST;
			this.add(b, c);
			
			count++;
		}
	}
	
	private JRadioButton createRendererButton(Renderer rend) {
		JRadioButton button = new JRadioButton(rend.toString());
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				renderer = rend;
			}
		});
		return button;
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
		
		c.gridx = 0;
		c.gridy = 10;
		c.gridheight = 1;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.NORTH;
		this.add(finish, c);
	}

}
