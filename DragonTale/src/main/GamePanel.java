package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import gamestates.MenuState;
import tools.InputManager;
import tools.InputManager.Keys;

public class GamePanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;

    // Auto 4:3 aspect ratio, just set the width
    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 4 * 3;

    public static final int SCALE = 3;

    private Thread thread;
    private boolean isRunning;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    // Let's not do it this way
    MenuState mainMenu = new MenuState("Resources/LevelData/menu.txt");

    // Temporary Variables
    DecimalFormat df = new DecimalFormat("0.00");
    private double currentFPS = 0.00;

    public GamePanel() {
	super();
	setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

	setFocusable(true);
	requestFocus();

	thread = new Thread(this);
	thread.start();
    }

    private void initializeComponents() {
	isRunning = true;
	// Initialize the InputManager
	InputManager.setKeyMappings(getInputMap(WHEN_FOCUSED), getActionMap());
    }

    public void run() {
	initializeComponents();

	int frames = 0;
	long previousUpdate = 0;
	// the point in time (in nanoseconds) in which an iteration of the
	// loop is started.
	long startTime;

	// the point in time (in nanoseconds) in which the same iteration of
	// the loop is finished.
	long endTime;

	// the elapsed time (in milliseconds) between the start and end the
	// iteration.
	int elapsedTime;

	// the time (in milliseconds) to wait, until the next iteration of
	// the loop.
	int waitTime;
	while (isRunning) {
	    startTime = System.nanoTime();
	    // Reset the InputManager every frame.
	    InputManager.resetKeyStates();
	    if (frames > 59) {
		currentFPS = 60 / ((System.nanoTime() - previousUpdate) / 1000000000.0);
		frames = 0;
		previousUpdate = System.nanoTime();
	    } else {
		frames++;
	    }

	    repaint();

	    endTime = System.nanoTime();

	    elapsedTime = (int) ((endTime - startTime) / 1000000);
	    waitTime = (int) (targetTime - elapsedTime);
	    // If we are not running behind, pause for the remaining time.
	    if (waitTime > 0) {
		try {
		    Thread.sleep(waitTime);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	    // Input test
	    if (InputManager.isPressed(Keys.UP)) {
		System.out.println(Keys.UP);
	    }
	    if (InputManager.isPressed(Keys.DOWN)) {
		System.out.println(Keys.DOWN);
	    }
	    if (InputManager.isPressed(Keys.LEFT)) {
		System.out.println(Keys.LEFT);
	    }
	    if (InputManager.isPressed(Keys.RIGHT)) {
		System.out.println(Keys.RIGHT);
	    }
	    if (InputManager.isPressed(Keys.BUTTON1)) {
		System.out.println(Keys.BUTTON1);
	    }
	    // End of input test
	}
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	mainMenu.draw((Graphics2D) g);

	// Display FPS for debugging
	g.setColor(Color.BLACK);
	g.setFont(new Font("Century Gothic", Font.PLAIN, 25));
	g.drawString("FPS: " + df.format(currentFPS) + "", 3, 24);
    }
}
