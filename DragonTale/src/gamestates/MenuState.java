package gamestates;

import java.awt.Graphics2D;

import tools.AudioManager;
import tools.AudioManager.AudioType;
import tools.LevelPainter;
import tools.ResourceManager;
import tools.ResourceManager.ResourceType;

public class MenuState extends GameState {

	private final String LEVEL_PATH;
	private ResourceManager resources;
	private LevelPainter painter;
	
	public MenuState(String path) {
		
		this.LEVEL_PATH = path;
		this.resources = new ResourceManager(LEVEL_PATH);
		this.painter = new LevelPainter(resources);
		resources.loadAllData();
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
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		painter.drawLevel(g);
	}

	@Override
	public void keyPressed(int k) {

	}

	@Override
	public void keyReleased(int k) {

	}

}
