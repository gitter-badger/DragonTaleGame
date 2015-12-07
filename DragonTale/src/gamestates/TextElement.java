package gamestates;

import java.awt.Color;
import java.awt.Font;

import entities.Entity;

public class TextElement extends Entity {

	public enum ScrollSetting {
		FIXED, SCROLLABLE;
	}

	
	@SuppressWarnings("unused")
	private String text;
	
	@SuppressWarnings("unused")
	private Font font;
	
	@SuppressWarnings("unused")
	private Color color;

	@SuppressWarnings("unused")
	private ScrollSetting scrollSetting;

	public TextElement(String stringPath, int xPosition, int yPosition, double distance, String text, String fontName,
			int fontSize, Color color, ScrollSetting scrollSetting) {
		
		super(stringPath, xPosition, yPosition, distance);
		this.text = text;
		this.font = new Font(fontName, Font.PLAIN, fontSize);
		this.color = color;

		this.scrollSetting = scrollSetting;
	}
}
