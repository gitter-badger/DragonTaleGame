package gamestates;

import java.awt.Graphics2D;

public abstract class GameState {

    /**
     * Initializes the GameState by loading any required resources
     */
    public abstract void initalize();

    /**
     * Update the GameState
     */
    public abstract void tick();

    /**
     * Draws this state onto the screen.
     * @param g
     *            Graphics2D object that will be used for drawing to the screen
     */
    public abstract void draw(Graphics2D g);
    /**
     * Each GameState could handle input differently
     */
    public abstract void handleInput();
}
