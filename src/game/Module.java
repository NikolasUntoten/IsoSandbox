package game;

import data.Data;
import ui.Overlay;

public interface Module {
	
	public void update(Data data);
	
	public void initialize(Data data);
	
	public Overlay getOverlay(Data data);
	
	public void updateOverlay(Data data);
}
