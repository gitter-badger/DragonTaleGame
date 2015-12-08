package gamestates;

public class GameStateManager {

    private boolean isPaused = false;

    public GameStateManager() {
	
    }

    private void loadState() {

    }

    private void unloadState() {

    }

    public void setState(int state) {

    }

    public void pause(boolean p) {
	isPaused = p;
    }

    public void tick() {
	if (isPaused) {
	    return;
	}
    }
}
