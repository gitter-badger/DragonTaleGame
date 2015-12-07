package entities;

public class WorldElement extends Entity {

	/**
	 * The absolute horizontal velocity of this instance. This field is not
	 * related to xScrollSpeed.
	 */
	private double xVelocity;

	/**
	 * The absolute vertical velocity of this instance. This field is not
	 * related to yScrollSpeed.
	 */
	private double yVelocity;

	public WorldElement(String imagePath, double xPosition, double yPosition, double distance, double xVelocity, double yVelocity) {
		super(imagePath, xPosition, yPosition, distance);
		setXVelocity(xVelocity);
		setYVelocity(yVelocity);

	}
	
	public void setXVelocity(double magnitude) {
		xVelocity = magnitude;
		setHorizontalScroll(getXScrollSpeed()+xVelocity);
	}
	
	public void setYVelocity(double magnitude) {
		yVelocity = magnitude;
		setVerticalScroll(getYScrollSpeed()+yVelocity);
	}

}
