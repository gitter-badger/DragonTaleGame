package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entities.BackgroundElement;
import entities.WorldElement;

//----------Very Unstable--------------//
//FIXME BIG TIME

public class ResourceManager {

	public enum ResourceType {
		BACKGROUND_ELEMENT, WORLD_ELEMENT, TEXT_ELEMENT, MUSIC, SOUNDEFFECT, NARRATION, SPRITESHEET, TILESET, SAVEDATA, CONFIG, DIALOG, LEVELDATA
	};

	private static final String MARKER = "---";

	private final String LEVEL_DATA_PATH;
	private ArrayList<String> levelData = new ArrayList<String>();

	private BackgroundElement[] backgroundElements;
	private WorldElement[] worldElements;

	BufferedReader dataReader;

	public ResourceManager(String path) {
		this.LEVEL_DATA_PATH = path;

		try {
			dataReader = new BufferedReader(new FileReader(LEVEL_DATA_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadData() {
		String currentInput = "";
		while (!currentInput.equals(MARKER)) {
			try {
				currentInput = dataReader.readLine();
				if (!currentInput.equals(MARKER)) {
					levelData.add(currentInput);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadAllData() {
		loadBackgroundElements();
		loadWorldElements();
	}

	private void loadBackgroundElements() {
		loadData();
		backgroundElements = new BackgroundElement[levelData.size()];
		for (int i = 0; i < levelData.size(); i++) {
			String[] rawData = levelData.get(i).split(",");
			backgroundElements[i] = new BackgroundElement(rawData[0], Integer.parseInt(rawData[1]),
					Integer.parseInt(rawData[2]), Double.parseDouble(rawData[3]));
		}
		levelData.clear();
	}

	private void loadWorldElements() {
		loadData();
		worldElements = new WorldElement[levelData.size()];
		for (int i = 0; i < levelData.size(); i++) {
			String[] rawData = levelData.get(i).split(",");
			worldElements[i] = new WorldElement(rawData[0], Integer.parseInt(rawData[1]), Integer.parseInt(rawData[2]),
					Double.parseDouble(rawData[3]), Double.parseDouble(rawData[4]), Double.parseDouble(rawData[5]));
		}
	}

	public BackgroundElement[] getBackgroundElements() {
		return backgroundElements;
	}

	public WorldElement[] getWorldElements() {
		return worldElements;
	}

}
