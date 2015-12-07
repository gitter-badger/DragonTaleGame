package tools;

import java.awt.Graphics2D;

import entities.BackgroundElement;
import entities.WorldElement;

public class LevelPainter{

	ResourceManager resources;

	public LevelPainter(ResourceManager resources) {
		this.resources = resources;
	}

	public void drawLevel(Graphics2D g) {
		for (BackgroundElement element : resources.getBackgroundElements()) {
			element.scroll();
			element.draw(g);
		}
		
		for (WorldElement element : resources.getWorldElements()) {
			element.scroll();
			element.draw(g);
		}
	}
}
