package edu.ntnu.idatt1002.frontend.utility;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * A class that plays a sound.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class SoundPlayer {


    /**
     * A method that plays a sound.
     *
     * @param filename the name of the sound file
     */
    public static void play(String filename) {
        try {
            File soundFile = new File(filename);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }
}







