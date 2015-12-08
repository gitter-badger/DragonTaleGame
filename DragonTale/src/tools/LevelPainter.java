package tools;

import java.awt.Graphics2D;

import entities.BackgroundElement;
import entities.WorldElement;

public class LevelPainter{

	public void drawLevel(Graphics2D g) {
		for (BackgroundElement element : ResourceManager.getBackgroundElements()) {
			element.scroll();
			element.draw(g);
		}
		
		for (WorldElement element : ResourceManager.getWorldElements()) {
			element.scroll();
			element.draw(g);
		}
	}
}
