package tools;

import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioManager {

    public enum AudioType {
	SOUNDEFFECT, NARRATION, MUSIC
    };

    // The following maps have to be initialized to something, in order to check
    // if any are empty.
    private static HashMap<String, Clip> clips = new HashMap<String, Clip>();
    // This variable is used for telling how much to skip from the beginning of
    // the clip.
    private static int gap;
    // Mute variable, master kill switch for all play functions.
    private static boolean mute = false;

    /**
     * Initializes the AudioManager with the specified type of audio resource.
     * The AudioManager must be initialized before it can play anything. Any
     * files loaded by the ResourceManager that have the same key will also be
     * overwritten by the latest map value with that key. For example, a music
     * file has the key "foo", then a narration recording also has the name
     * "foo", the narration version of foo will be the one that will be
     * available for playing for this class. It is better to have specific
     * prefix/suffix if having the same key is a must. Example: n_foo, m_foo and
     * s_foo, in this case, it is clear that the keys are associated with
     * different values.
     * 
     * @param type
     *            Type of audio file being loaded. Please note that two
     *            different values associated with the same key, different types
     *            of audio or not will have unexpected results.
     */
    public static void init(AudioType type) {
	switch (type) {
	case MUSIC:
	    clips.putAll(ResourceManager.getClips(AudioType.MUSIC));
	    break;
	case NARRATION:
	    clips.putAll(ResourceManager.getClips(AudioType.NARRATION));
	    break;
	case SOUNDEFFECT:
	    clips.putAll(ResourceManager.getClips(AudioType.SOUNDEFFECT));
	    break;
	default:
	    break;
	}
	gap = 0;
    }

    /**
     * This method plays music/sound effects once. The music will start playing
     * at frame 0 of the music/sound effect (Clips are frame-based)
     * 
     * @param s
     *            Name (key) of the clip to play.
     */
    public static void play(String s) {
	// Don't crash, panic instead
	if (clips.isEmpty()) {
	    System.out.println("AudioManager not yet initialized");
	    return;
	}
	play(s, gap);
    }

    /**
     * This method plays music/sound effects once. The music will start playing
     * at the specified frame of the music/sound effect (Clips are frame-based)
     * 
     * @param s
     *            Name (key) of the clip to play.
     * @param i
     *            Frame from which to start playing
     */
    public static void play(String s, int i) {
	if (clips.isEmpty()) {
	    System.out.println("AudioManager not yet initialized");
	    return;
	}
	// If mute is true, nothing should be played.
	if (mute)
	    return;
	// Get the clip that will be playing
	Clip c = clips.get(s);
	// The clip with that key doesn't exist, nothing to do
	if (c == null)
	    return;
	// The clip shouldn't play if it was already doing so.
	if (c.isRunning())
	    c.stop();
	// Skip the specified amount of frames in the clip.
	c.setFramePosition(i);
	// If the clip is not already playing, make it play
	while (!c.isRunning())
	    c.start();
    }

    /**
     * Stop playing the specified sound
     * 
     * @param s
     *            Name (key) of the clip to stop.
     */
    public static void stop(String s) {
	if (clips.get(s) == null)
	    return;
	if (clips.get(s).isRunning())
	    clips.get(s).stop();
    }

    /**
     * Resume a previously stopped sound
     * 
     * @param s
     *            Name (key) of the clip to resume.
     */
    public static void resume(String s) {
	if (mute)
	    return;
	if (clips.get(s).isRunning())
	    return;
	clips.get(s).start();
    }

    /**
     * This method is similar to the resume() method. The difference is that
     * this method will ensure that the audio resumed keeps looping. The
     * resume() method only guarantees that the clip will finish playing, not
     * that it will keep looping.
     * 
     * @param s
     *            Name (key) of the clip to resume.
     */
    public static void resumeLoop(String s) {
	Clip c = clips.get(s);
	// Oops, the clip with that key doesn't exist.
	if (c == null)
	    return;
	// Ensure that the clip loops.
	c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays a sound until the stop() method says otherwise
     * 
     * @param s
     *            Name (key) of the clip to loop.
     */
    public static void loop(String s) {
	if (clips.isEmpty()) {
	    System.out.println("AudioManager not yet initialized");
	    return;
	}
	loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
    }

    /**
     * Plays a sound, starting from the specified frame, until the stop() method
     * says otherwise
     * 
     * @param s
     *            Name (key) of the clip to loop.
     * @param frame
     *            Frame from which to start playing
     */
    public static void loop(String s, int frame) {
	if (clips.isEmpty()) {
	    System.out.println("AudioManager not yet initialized");
	    return;
	}
	loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
    }

    /**
     * Plays a sound range, loops from the starting frame until the end frame.
     * 
     * @param s
     *            Name (key) of the clip to loop.
     * @param start
     *            The loop's starting position (frame-based)
     * @param end
     *            The loop's ending position (frame-based)
     */
    public static void loop(String s, int start, int end) {
	if (clips.isEmpty()) {
	    System.out.println("AudioManager not yet initialized");
	    return;
	}
	loop(s, gap, start, end);
    }

    /**
     * Plays a sound range, loops from the starting frame until the end frame.
     * This method allows normal playing of sounds, but will loop forever.
     * Forever, meaning that the stop() method must be called.
     * 
     * @param s
     *            Name (key) of the clip to loop.
     * @param frame
     *            Frame from which to start playing
     * @param start
     *            The loop's starting position (frame-based)
     * @param end
     *            The loop's ending position (frame-based)
     */
    public static void loop(String s, int frame, int start, int end) {
	if (clips.isEmpty()) {
	    System.out.println("AudioManager not yet initialized");
	    return;
	}
	stop(s);
	if (mute)
	    return;
	// Set the the range that will be looping
	clips.get(s).setLoopPoints(start, end);
	// Starting from this frame
	clips.get(s).setFramePosition(frame);
	// Loop until the stop() method is called.
	clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * This method will change the current position of the specified clip
     * 
     * @param s
     *            Name (key) of the clip whose position is to be modified
     * @param frame
     *            Which position to set the clip to (frame-based)
     */
    public static void setPosition(String s, int frame) {
	clips.get(s).setFramePosition(frame);
    }

    /**
     * This method returns the length of the specified sound (in frames)
     * 
     * @param s
     *            Name (key) of the clip to get length information from.
     * @return Length of the clip (in-frames)
     */
    public static int getFrames(String s) {
	return clips.get(s).getFrameLength();
    }

    /**
     * This method returns the frame that is currently playing.
     * 
     * @param s
     *            Name (key) of the clip to get the current frame information
     *            from
     * @return Current frame the clip is on.
     */
    public static int getPosition(String s) {
	return clips.get(s).getFramePosition();
    }

    /**
     * This method invalidates a clip, that is, the clip won't be usable until
     * it is recreated. This is a cleanup method.
     * 
     * @param s
     *            Name (key) of the clip to invalidate (free its resources).
     */
    public static void close(String s) {
	// Stop the clip, otherwise, the next operation will crash
	stop(s);
	clips.get(s).close();
    }

    /**
     * This method controls the volume of the specified clip.
     * 
     * @param s
     *            Name (key) of the clip whose volume is to be manipulated.
     * @param volume
     *            Amount to decrease/increase the volume by (in dB).
     */
    public static void setVolume(String s, float volume) {
	Clip c = clips.get(s);
	// Oops, the clip with that key doesn't exist.
	if (c == null)
	    return;
	// In Java, the volume of sampled sounds can be changed using
	// FloatControl.
	FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
	vol.setValue(volume);
    }

    /**
     * This method can be used to check if a clip is playing.
     * 
     * @param s
     *            Name (key) of the clip to check for playing/stopped status.
     * @return Whether or not the clip is playing.
     */
    public static boolean isPlaying(String s) {
	Clip c = clips.get(s);
	if (c == null)
	    return false;
	return c.isRunning();
    }
}
