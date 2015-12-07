package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Entity {

	/**
	 * The current horizontal position of this instance.
	 */
	private double xPosition;

	/**
	 * The current vertical position of this instance.
	 */
	private double yPosition;

	/**
	 * The width of this instance.
	 */
	private int WIDTH;

	/**
	 * The height of this instance.
	 */
	private int HEIGHT;

	/**
	 * The horizontal velocity of this instance relative to the player. This field
	 * is defaulted to a value of 0 and is not initialized in the constructor.
	 */
	private double xScrollSpeed = 0;

	/**
	 * The vertical velocity of this instance relative to the player. This field is
	 * defaulted to a value of 0 and is not initialized in the constructor.
	 */
	private double yScrollSpeed = 0;

	/**
	 * The default image of this instance.
	 */
	private BufferedImage DEFAULT_IMAGE;

	/**
	 * The constant at which to scale this instance. This field is final. This
	 * field is static.
	 */
	public static final int SCALE = GamePanel.SCALE;

	/**
	 * The parallax factor of this instance. The xScrollSpeed and yScrollSpeed
	 * fields will be scaled by this factor. This field is final.
	 */
	private final double PARALLAX;

	public Entity(String imagePath, double xPosition, double yPosition, double distance) {

		try {
			this.DEFAULT_IMAGE = ImageIO.read(getClass().getResourceAsStream(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setXPosition(xPosition);
		setYPosition(yPosition);

		this.WIDTH = DEFAULT_IMAGE.getWidth();
		this.HEIGHT = DEFAULT_IMAGE.getHeight();

		this.PARALLAX = distance;
	}

	/**
	 * Sets the horizontal scroll speed to the specified magnitude.
	 * 
	 * @param magnitude
	 *            The magnitude to set the scroll speed to.
	 */
	public void setHorizontalScroll(double magnitude) {
		xScrollSpeed = (magnitude * PARALLAX);
	}

	/**
	 * Sets the vertical scroll speed to the specified magnitude.
	 * 
	 * @param magnitude
	 *            The magnitude to set the scroll speed to.
	 */

	public void setVerticalScroll(double magnitude) {
		yScrollSpeed = (magnitude * PARALLAX);
	}

	/**
	 * Draws this instance to the screen.
	 * 
	 * @param g
	 *            The graphics object.
	 */
	public void draw(Graphics2D g) {
		g.drawImage(DEFAULT_IMAGE, (int)getScaled(xPosition), (int)getScaled(yPosition), getScaled(WIDTH),
				getScaled(HEIGHT), null);
	}

	/**
	 * Scrolls this instance according to the scroll speeds.
	 */
	public void scroll() {
		xPosition += xScrollSpeed;
		yPosition += yScrollSpeed;
		
		if (this.xPosition > GamePanel.WIDTH) {
			this.xPosition -= (GamePanel.WIDTH + this.WIDTH);
		}
	}

	/**
	 * Gets the current horizontal position of this instance.
	 * 
	 * @return The current horizontal position.
	 */
	public double getXPosition() {
		return xPosition;
	}
	
	public double getXScrollSpeed() {
		return xScrollSpeed;
	}
	
	/**
	 * Gets the current vertical position of this instance.
	 * 
	 * @return The current vertical position.
	 */
	public double getYPosition() {
		return yPosition;
	}
	
	public double getYScrollSpeed() {
		return yScrollSpeed;
	}
	

	/**
	 * Sets the horizontal position of this instance.
	 * 
	 * @param xPosition
	 *            The coordinate to set the position to.
	 */
	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Sets the vertical position of this instance.
	 * 
	 * @param yPosition
	 *            The coordinate to set the position to.
	 */
	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getScaled(int magnitude) {
		return magnitude * SCALE;
	}

	public double getScaled(double magnitude) {
		return magnitude * SCALE;
	}
}
