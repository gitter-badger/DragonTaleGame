package tools;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

//FIXME Document this class better.

public class InputManager {

    private static final int NUM_KEYS = 10;
    // Array that holds the ASCII values of the keys to check for input.
    private static int keyMappings[] = new int[NUM_KEYS];
    // Array that works along the above array. Contains the key states.
    public static boolean keyState[] = new boolean[NUM_KEYS];

    /**
     * The enum determines the order in which the keys are laid out.
     */
    public enum Keys {
	UP, RIGHT, DOWN, LEFT, BUTTON1, BUTTON2, BUTTON3, BUTTON4, BUTTON5, BUTTON6
    };

    /**
     * InputManager's equivalent of an init() method.
     * 
     * Sample call (assuming the caller extends some sort of JComponent):
     * InputManager input = new InputManager(getInputMap(WHEN_FOCUSED),
     * getActionMap());
     * 
     * @param im
     *            InputMap
     * @param am
     *            ActionMap
     */
    public static void setKeyMappings(InputMap im, ActionMap am) {
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
		    // When a key is pressed, set the pressed state of that key
		    // to "true".
		    keySet(keyMappings[key.ordinal()], true);
		}
	    });
	}
    }

    /**
     * This method resets all the key states back to false. This method must be
     * called from within the game loop, otherwise, this class will produce
     * "sticky" input.
     */
    public static void resetKeyStates() {
	for (Keys i : Keys.values()) {
	    keyState[i.ordinal()] = false;
	}
    }

    /**
     * Sets the state of a key
     * 
     * @param keyID
     *            Key ASCCI value.
     * @param b
     *            State to set the key to. "true" means that the key has been
     *            pressed.
     */
    private static void keySet(int keyID, boolean state) {
	if (keyID == keyMappings[0])
	    keyState[Keys.UP.ordinal()] = state;
	if (keyID == keyMappings[1])
	    keyState[Keys.RIGHT.ordinal()] = state;
	if (keyID == keyMappings[2])
	    keyState[Keys.DOWN.ordinal()] = state;
	if (keyID == keyMappings[3])
	    keyState[Keys.LEFT.ordinal()] = state;
	if (keyID == keyMappings[4])
	    keyState[Keys.BUTTON1.ordinal()] = state;
	if (keyID == keyMappings[5])
	    keyState[Keys.BUTTON2.ordinal()] = state;
	if (keyID == keyMappings[6])
	    keyState[Keys.BUTTON3.ordinal()] = state;
	if (keyID == keyMappings[7])
	    keyState[Keys.BUTTON4.ordinal()] = state;
	if (keyID == keyMappings[8])
	    keyState[Keys.BUTTON5.ordinal()] = state;
	if (keyID == keyMappings[9])
	    keyState[Keys.BUTTON6.ordinal()] = state;
    }

    /**
     * Determine if the specified key has been pressed
     * 
     * @param i
     *            Key to check for its current state
     * @return Whether or not the key is pressed
     */
    public static boolean isPressed(Keys key) {
	return keyState[key.ordinal()];
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
