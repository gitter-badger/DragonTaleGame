package tools;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

//FIXME Document this class better.

public class InputManager {

    private InputMap im;
    private ActionMap am;
    private static final int NUM_KEYS = 10;
    // Array that holds the ASCII values of the keys to check for input.
    private static int keyMappings[] = new int[NUM_KEYS];
    public static boolean keyState[] = new boolean[NUM_KEYS];
    public static boolean prevKeyState[] = new boolean[NUM_KEYS];

    /**
     * The enum determines the order in which the keys are laid out.
     */
    enum Keys {
	UP, RIGHT, DOWN, LEFT, BUTTON1, BUTTON2, BUTTON3, BUTTON4, BUTTON5, BUTTON6
    };

    // TODO Think of a way of getting away with this without a constructor.
    /**
     * Sample constructor call (assuming the caller extends some sort of JComponent): 
     * InputManager input = new InputManager(getInputMap(WHEN_FOCUSED), getActionMap());
     * @param im InputMap 
     * @param am
     */
    public InputManager(InputMap im, ActionMap am) {
	this.im = im;
	this.am = am;
    }

    /**
     * InputManager's equivalent of an init() method.
     */
    public void setKeyMappings() {
	// TODO Load keymappings from a file
	// --------Start of Hard-coded, temporary values-------------
	keyMappings[0] = KeyEvent.VK_UP;
	keyMappings[1] = KeyEvent.VK_RIGHT;
	keyMappings[2] = KeyEvent.VK_DOWN;
	keyMappings[3] = KeyEvent.VK_LEFT;
	keyMappings[4] = KeyEvent.VK_NUMPAD1;
	keyMappings[5] = KeyEvent.VK_NUMPAD2;
	keyMappings[6] = KeyEvent.VK_NUMPAD3;
	keyMappings[7] = KeyEvent.VK_NUMPAD4;
	keyMappings[8] = KeyEvent.VK_NUMPAD5;
	keyMappings[9] = KeyEvent.VK_NUMPAD6;
	// --------End of Hard-coded, temporary values-----------------
	for (Keys key : Keys.values()) {
	    im.put(KeyStroke.getKeyStroke(keyMappings[key.ordinal()], 0), key.name());
	    am.put(key.name(), new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
		    keySet(key.ordinal(), true);
		    // Test code
		    if (!isPressed(key.ordinal()))
			System.out.println(key.name());
		    // End of test code
		}
	    });
	}
    }

    /**
     * Sets the state of a key
     * 
     * @param i
     *            Key ASCCI value
     * @param b
     */
    private static void keySet(int i, boolean state) {
	if (i == keyMappings[0])
	    keyState[Keys.UP.ordinal()] = state;
	if (i == keyMappings[1])
	    keyState[Keys.LEFT.ordinal()] = state;
	if (i == keyMappings[2])
	    keyState[Keys.DOWN.ordinal()] = state;
	if (i == keyMappings[3])
	    keyState[Keys.RIGHT.ordinal()] = state;
	if (i == keyMappings[4])
	    keyState[Keys.BUTTON1.ordinal()] = state;
	if (i == keyMappings[5])
	    keyState[Keys.BUTTON2.ordinal()] = state;
	if (i == keyMappings[6])
	    keyState[Keys.BUTTON3.ordinal()] = state;
	if (i == keyMappings[7])
	    keyState[Keys.BUTTON4.ordinal()] = state;
	if (i == keyMappings[8])
	    keyState[Keys.BUTTON5.ordinal()] = state;
	if (i == keyMappings[9])
	    keyState[Keys.BUTTON6.ordinal()] = state;
    }

    /**
     * Determine if the specified key has been pressed
     * 
     * @param i
     *            Key to check for its current state
     * @return Whether or not the key is pressed
     */
    public static boolean isPressed(int i) {
	return keyState[i] && !prevKeyState[i];
    }

    /**
     * Checks whether there was any key pressed at all. Only checks for the
     * first key that has a value of "true".
     * 
     * @return First key that was pressed (according to the enum's ordering)
     */
    public static boolean anyKeyPressed() {
	for (int i = 0; i < NUM_KEYS; i++) {
	    if (keyState[i])
		return true;
	}
	return false;
    }
}
