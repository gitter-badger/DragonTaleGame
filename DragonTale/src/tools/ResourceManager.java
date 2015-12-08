/**
 * LICENSE header
 */
package tools;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import entities.BackgroundElement;
import entities.WorldElement;

/**
 * @author anddy
 *
 */
public class ResourceManager {

    // Don't think it is necessary to categorize them this far down
    private static HashMap<String, Clip> music = new HashMap<String, Clip>();
    private static HashMap<String, Clip> sounds = new HashMap<String, Clip>();
    private static HashMap<String, Clip> narration = new HashMap<String, Clip>();
    // keys of type String and values of type BufferedImage with default size
    // and load factor.
    private static HashMap<String, BufferedImage> spritesheets = new HashMap<String, BufferedImage>();
    private static HashMap<String, BufferedImage> backgrounds = new HashMap<String, BufferedImage>();
    private static HashMap<String, BufferedImage> tilesets = new HashMap<String, BufferedImage>();

    // Types of resources this class can load (Not all of them are implemented
    // yet)
    public enum ResourceType {
	WORLD_ELEMENT, TEXT_ELEMENT, NARRATION, SOUNDEFFECT, MUSIC, SPRITESHEET, TILESET, BACKGROUND_ELEMENT, SAVEDATA, CONFIG, DIALOG, LEVELDATA
    };

    /**
     * 
     * @param type
     *            Type of resource being loaded
     * @param path
     *            Path to file being loaded
     * @param key
     *            Name that will be used when accessing the file.
     */
    public static void loadFile(ResourceType type, String path, String key) {
	switch (type) {
	case SOUNDEFFECT:
	    loadAudio(type, path, key);
	    break;
	case MUSIC:
	    loadAudio(type, path, key);
	    break;
	case NARRATION:
	    loadAudio(type, path, key);
	    break;
	case SPRITESHEET:
	    loadImage(type, path, key);
	    break;
	case TILESET:
	    loadImage(type, path, key);
	    break;
	case BACKGROUND_ELEMENT:
	    loadImage(type, path, key);
	    break;
	case LEVELDATA:
	    break;
	case WORLD_ELEMENT:
	    break;
	case SAVEDATA:
	    break;
	case CONFIG:
	    break;
	case DIALOG:
	    break;
	case TEXT_ELEMENT:
	    break;
	default:
	    System.out.println("Unknown file type");
	}
    }

    // TODO: Fix Chadra's code present below.   
    
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
    // ---------End of Chandra's buggy code---------------------------------

    /**
     * Image loader. Loads images and stores them into the corresponding map
     * 
     * @param path
     *            Path to file being loaded
     * @param key
     *            Name that will be used when accessing the file.
     */
    private static void loadImage(ResourceType type, String path, String key) {
	// If we have already loaded a file with that name, we don't need to
	// load it again
	switch (type) {
	case BACKGROUND_ELEMENT:
	    if (backgrounds.get(key) != null) {
		return;
	    }
	case SPRITESHEET:
	    if (spritesheets.get(key) != null) {
		return;
	    }
	    break;
	case TILESET:
	    if (tilesets.get(key) != null) {
		return;
	    }
	    break;
	case WORLD_ELEMENT:
	    break;
	default:
	    return;
	}
	try {
	    // Create an InputStream from the resource at the specified path
	    InputStream in = new BufferedInputStream(ClassLoader.getSystemClassLoader().getResourceAsStream(path),
		    65536);
	    // Read the image and store it in memory
	    BufferedImage image = ImageIO.read(in);
	    // Store the loaded image onto the specified map
	    switch (type) {
	    case BACKGROUND_ELEMENT:
		backgrounds.put(key, image);
		break;
	    case SPRITESHEET:
		spritesheets.put(key, image);
		break;
	    case TILESET:
		tilesets.put(key, image);
		break;
	    default:
		break;
	    }
	    // Done with the image, so free up any memory associated with it
	    image.flush();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private static void loadAudio(ResourceType type, String path, String key) {
	switch (type) {
	case MUSIC:
	    if (music.get(key) != null) {
		return;
	    }
	    break;
	case NARRATION:
	    if (narration.get(key) != null) {
		return;
	    }
	    break;
	case SOUNDEFFECT:
	    if (sounds.get(key) != null) {
		return;
	    }
	    break;
	default:
	    return;
	}
	try {
	    InputStream in = new BufferedInputStream(ResourceManager.class.getResourceAsStream(path), 65536);
	    AudioInputStream audioStream = AudioSystem.getAudioInputStream(in);
	    // Get basic information about the audio file format
	    AudioFormat baseFormat = audioStream.getFormat();
	    // In order to be able to load MP3s, the file format needs to be
	    // decoded
	    AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
		    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
		    baseFormat.isBigEndian());
	    // Create a second AudioInputStream, this time, with the decoded
	    // format
	    AudioInputStream audioStreamDecoded = AudioSystem.getAudioInputStream(decodeFormat, audioStream);

	    Clip clip = AudioSystem.getClip();
	    // Assign the decoded audio to a clip so it can be played later
	    clip.open(audioStreamDecoded);
	    // Store the clip into the corresponding map
	    switch (type) {
	    case MUSIC:
		music.put(key, clip);
		break;
	    case NARRATION:
		narration.put(key, clip);
		break;
	    case SOUNDEFFECT:
		sounds.put(key, clip);
		break;
	    default:
		break;
	    }

	} catch (UnsupportedAudioFileException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (LineUnavailableException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * This method should be called whenever the GameState is switched
     */
    public static void unload() {
	sounds.clear();
	narration.clear();
	music.clear();
	spritesheets.clear();
	tilesets.clear();
	backgrounds.clear();
    }

    // ----------- Wild Test Below -----------
    public static void saveFile() {
	BufferedOutputStream fos = null;
	try {
	    fos = new BufferedOutputStream(new FileOutputStream("savegame.dat"));
	} catch (FileNotFoundException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	ObjectOutputStream oos = null;
	try {
	    oos = new ObjectOutputStream(fos);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try {
	    oos.writeInt(12345);
	    oos.close();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    // ----------- End of Wild Test -----------

    public static HashMap<String, Clip> getClips(ResourceType type) {
	switch (type) {
	case MUSIC:
	    return music;
	case NARRATION:
	    return narration;
	case SOUNDEFFECT:
	    return sounds;
	default:
	    return null;
	}
    }

    public static HashMap<String, BufferedImage> getImages(ResourceType type) {
	switch (type) {
	case BACKGROUND_ELEMENT:
	    return backgrounds;
	case SPRITESHEET:
	    return spritesheets;
	case TILESET:
	    return tilesets;
	default:
	    return null;
	}
    }
}
