package tools;

import java.awt.image.BufferedImage;

public abstract class Animation {
    // Array that contains the frames that will be used for animation.
    private BufferedImage[] frames;
    // This variable stores the frame # that is being displayed.
    private int currentFrame;
    // This variable determines how many of the frames in the frames array will
    // actually be used for animation.
    private int numOfFrames;
    // Counter variable to count the number of frames that have passed.
    private int count;
    // This variable determines the delay between frames.
    private int delay;
    // This variable stores how many times the animation been played.
    private int timesPlayed;

    /**
     * This class has a constructor, since every animated object will be
     * independent of each other. That is, an object being animated should not
     * put on hold the animation of another.
     */
    public Animation() {
	// A new instance of this class has been created, so the animation
	// haven't played yet.
	timesPlayed = 0;
    }

    /**
     * This method sets the frames that will be animated.
     * 
     * @param frames
     *            Array of BufferedImage objects, each of which should represent
     *            a single frame.
     */
    public void setFrames(BufferedImage[] frames) {
	this.frames = frames;
	currentFrame = 0;
	count = 0;
	timesPlayed = 0;
	// Default delay is 2 frames.
	delay = 2;
	numOfFrames = frames.length;
    }

    /**
     * Sets the delay that is to occur before switching to a new frame.
     * 
     * @param delay
     *            Number of frames to delay for. A delay value of -1 stops the
     *            animation.
     */
    public void setDelay(int delay) {
	this.delay = delay;
    }

    /**
     * Sets the current frame. This method allows changing the currently
     * animated frame on demand. As an example, this method can be used to
     * transition from the first frame of a jumping animation to the last.
     * 
     * @param frame
     *            Frame to switch to.
     */
    public void setFrame(int frame) {
	currentFrame = frame;
    }

    /**
     * Sets the number of frames in the animation. Useful if for example an
     * animation contains 7 frames, but it is only desired for the first 5 to
     * play.
     * 
     * @param numFrames
     *            Number of frames in the animation.
     */
    public void setNumOfFrames(int numOfFrames) {
	this.numOfFrames = numOfFrames;
    }

    /**
     * Update method. It should be called every frame the animation is to be
     * played. This method will only work for frame-based animation, for custom
     * animations, please override customTick() instead.
     */
    public void tick() {
	// If the delay is -1, that means it is stopped, so there's nothing to
	// do.
	if (delay == -1)
	    return;
	// Every time this method is called it means that we're on a new frame.
	// Increase the frame count.
	count++;

	// If the counter is equal to the delay, we can move on to the next
	// frame of the animation.
	if (count == delay) {
	    // Move to the next frame.
	    currentFrame++;
	    // Reset counter.
	    count = 0;
	}
	// If the current frame is the last frame, we want to return to the
	// first frame.
	if (currentFrame == numOfFrames) {
	    // Reset back to the first frame of the animation.
	    currentFrame = 0;
	    // Increase the number of times the animation has been played.
	    timesPlayed++;
	}

    }

    /**
     * Gets the current frame of the animation.
     * 
     * @return Frame number of the frame that is currently on display.
     */
    public int getFrame() {
	return currentFrame;
    }

    /**
     * Gets the frame count as measured by this class.
     * 
     * @return Number of frames that have passed since the last complete
     *         animation loop.
     */
    public int getCount() {
	return count;
    }

    /**
     * Gets the image currently on display.
     * 
     * @return The image (frame) to be displayed.
     */
    public BufferedImage getImage() {
	return frames[currentFrame];
    }

    /**
     * Returns whether the animation has played once.
     * 
     * @return Whether the animation has been played only once.
     */
    public boolean hasPlayedOnce() {
	return timesPlayed > 0;
    }

    /**
     * Returns whether or not the animation has played the specified number of
     * times.
     * 
     * @param i
     *            The number to compare the number of times that the animation
     *            has played against.
     * @return Whether the animation has been played that exact number of times.
     */
    public boolean hasPlayed(int i) {
	return timesPlayed == i;
    }

    /**
     * When creating custom animations, this method must be implemented instead
     * of using the normal tick() method.
     */
    public abstract void customTick();

    /**
     * Template method for an entirely custom animation. Could be anything from
     * color changing to a screen shake effect to simply moving something across
     * the screen.
     */
    public abstract void customAnimation();
}
