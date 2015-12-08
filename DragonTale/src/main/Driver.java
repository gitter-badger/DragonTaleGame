package main;

import javax.swing.JFrame;

public class Driver {

    public static void main(String[] args) {
	initializeGame();
    }

    public static void initializeGame() {
	JFrame window = new JFrame("Dragon Tale");
	window.setContentPane(new GamePanel());
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable(false);
	window.pack();
	window.setVisible(true);
    }
}
