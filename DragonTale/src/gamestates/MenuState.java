package gamestates;

import java.awt.Graphics2D;

import tools.AudioManager;
import tools.AudioManager.AudioType;
import tools.LevelPainter;
import tools.ResourceManager;
import tools.ResourceManager.ResourceType;

public class MenuState extends GameState {

	private final String LEVEL_PATH;
	private LevelPainter painter;
	
	public MenuState(String path) {		
		this.LEVEL_PATH = path;
		ResourceManager.loadFile(ResourceType.LEVELDATA, LEVEL_PATH, null);
		this.painter = new LevelPainter();
		ResourceManager.loadAllData();
		// Test code
		ResourceManager.loadFile(ResourceType.MUSIC, "/Music/TITLE_01.mp3", "menu_theme");
		AudioManager.init(AudioType.MUSIC);
		AudioManager.loop("menu_theme");
		// Test code over
	}

	@Override
	public void initalize() {

	}

	@Override
	public void tick() {

	}

	@Override
	public void draw(Graphics2D g) {
		painter.drawLevel(g);
	}

	@Override
	public void handleInput() {
	   	    
	}
}
