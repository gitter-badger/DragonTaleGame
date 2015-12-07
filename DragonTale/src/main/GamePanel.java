package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import gamestates.MenuState;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;

	public static final int SCALE = 3;

	private Thread thread;
	private boolean isRunning;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	MenuState mainMenu = new MenuState("Resources/LevelData/menu.txt");

	
	//Temporary Variables//
	DecimalFormat df = new DecimalFormat("0.00");
	private double currentFPS = 0.00;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		setFocusable(true);
		requestFocus();

		addKeyListener(this);

		thread = new Thread(this);
		thread.start();
	}

	private void initializeComponents() {
		isRunning = true;
	}

	public void run() {
		initializeComponents();

		int frames = 0;
		long previousUpdate = 0;
		while (isRunning) {
			// the point in time (in nanoseconds) in which an iteration of the
			// loop
			// is started.
			long startTime;

			// the point in time (in nanoseconds) in which the same iteration of
			// the
			// loop is finished.
			long endTime;

			// the elapsed time (in milliseconds) between the start and end the
			// iteration.
			int elapsedTime;

			// the time (in milliseconds) to wait, until the next iteration of
			// the
			// loop.
			int waitTime;

			if (frames > 59) {
				currentFPS = 60 / ((System.nanoTime() - previousUpdate) / 1000000000.0);
				frames = 0;
				previousUpdate = System.nanoTime();
			} else {
				frames++;
			}
			startTime = System.nanoTime();

			repaint();

			endTime = System.nanoTime();

			elapsedTime = (int) ((endTime - startTime) / 1000000);
			waitTime = (int) (targetTime - elapsedTime);

			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		mainMenu.draw((Graphics2D) g);
		
		//Display FPS for debugging
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 25));
		g.drawString("FPS: " + df.format(currentFPS) + "", 3, 24);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
