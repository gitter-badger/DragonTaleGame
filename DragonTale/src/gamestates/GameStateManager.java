package gamestates;

import java.util.ArrayList;

public class GameStateManager {

    private boolean isPaused = false;
    private ArrayList<GameState> gameStates;
    private GameStates currentState;
    private GameStates previousState;

    public static final int NUM_STATES = 1;

    private enum GameStates {
	MENU
    };

    public GameStateManager() {

	isPaused = false;

	gameStates = new ArrayList<>();
	setState(GameStates.MENU);
    }

    private void loadState() {

    }

    private void unloadState(GameStates state) {
	gameStates.remove(state.ordinal());
    }

    public void setState(GameStates state) {
	previousState = currentState;
	unloadState(previousState);
	currentState = state;
	switch (state) {
	case MENU:
	    // TODO Make the MenuState compatible with the new changes.
	    break;
	default:
	    break;

	}
    }

    public void pause(boolean p) {
	isPaused = p;
    }

    public void tick() {
	if (isPaused) {
	    return;
	} else
	    gameStates.get(currentState.ordinal()).tick();
    }
}
