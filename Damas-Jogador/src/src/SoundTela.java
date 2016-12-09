package src;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundTela extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Clip clip;
	// Constructor
	public SoundTela() {

		try {
			// 	Open an audio input stream.
			URL url = getClass().getResource("/audio/zelda.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// 	Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void Toca()
	{
		clip.start();
	}
	
	public void Para()
	{
		clip.stop();
	}
}