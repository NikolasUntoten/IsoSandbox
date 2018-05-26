package game;

import javax.swing.JPanel;

import data.Data;

public interface Module {
	
	public void update(Data data);
	
	public void initialize(Data data);
	
	public JPanel getOverlay(Data data);
	
	public void updateOverlay(Data data);
}
