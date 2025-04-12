// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;

// CLASS TO PLAY ALL SOUND EFFECTS

// EXAMPLE OF HOW TO USE: 
// "SoundPlayer.moveSFX();" will play the move sound effect.
// class is static so no need to declare an object for it. Just play the sounds.

public class SoundPlayer
{
    // alternate path prefix to allow both versions (me and Michelle's) to work without path issues.
    private static final String ALTERNATE_PATH_PREFIX = "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/";

    // all sound effects
    private static Clip moveSound;       // when moving block
    private static Clip landSound;       // when block lands
    private static Clip cancelSound;     // when block CANNOT be moved
    private static Clip fallSound;       // when block moves down (UNUSED BECAUSE REALLY ANNOYING)
    private static Clip jumpscareSound;  // when loss occurs
    private static Clip backgroundMusic; // music playing throughout game

    // helper method to load sound file and return it
    private static Clip loadClip(String path) throws Exception
    {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(path)));
        return clip;
    }

    // helper method to try loading clips from multiple paths
    private static Clip loadClipFix(String relativePath) throws Exception
    {
        // get all paths to try out
        String[] possiblePaths = {

            // Julian's path
            relativePath,

            // Michelle's path (which is her prefix + last word in the path, aka the file name)
            ALTERNATE_PATH_PREFIX + new File(relativePath).getName()

        };

        // try loading file through all potential paths
        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {

                // return file only when it exists
                return loadClip(path);
            }
        }

        // when everything is broken :(
        throw new FileNotFoundException("Sound file not found!! :(");
    }

    public static void loadSounds()
    {
        // load all game sounds
        try
        {
            // this path format works on my computer. Check that it works on yours. ---> JUST TRIED, SAME ISSUE, PROB NEEDS ABS
            
            // RELATIVE PATHS
            // moveSound = loadClipFix("sounds/Move.wav");       // when moving block before placing it
            // landSound = loadClipFix("sounds/Land.wav");       // when block lands
            // cancelSound = loadClipFix("sounds/Cancel.wav");   // when block cannot be moved further
            // fallSound = loadClipFix("sounds/Fall.wav");       // when block moves down (UNUSED BECAUSE REALLY ANNOYING)
            // jumpscareSound = loadClipFix("sounds/StaticAudio.wav");  // game over sound *** STATIC AS A PLACEHOLDER
            // backgroundMusic = loadClipFix("sounds/StaticAudio.wav"); // background music *** STATIC AS A PLACEHOLDER

            // ABSOLUTE PATHS
            moveSound = loadClipFix("STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/Move.wav");       // when moving block before placing it
            landSound = loadClipFix("STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/Land.wav");       // when block lands
            cancelSound = loadClipFix("STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/Cancel.wav");   // when block cannot be moved further
            fallSound = loadClipFix("STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/Fall.wav");       // when block moves down (UNUSED BECAUSE REALLY ANNOYING)
            jumpscareSound = loadClipFix("STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/StaticAudio.wav");  // game over sound *** STATIC AS A PLACEHOLDER
            backgroundMusic = loadClipFix("STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/StaticAudio.wav"); // background music *** STATIC AS A PLACEHOLDER
        }

        // print if loading error occurs
        catch (Exception e)
        {
            System.out.println("Error when loading sounds! Check file paths");
        }
    }

    // helper method to play sound
    private static void playSound(Clip clip)
    {
        // *** WEIRD ISSUE: WHEN PRESSING ARROW KEY REPEATEDLY,
        //                  "MOVE" SOUND GLITCHES OUT. WHAT THE HELL???
        //                  WHEN HOLDING DOWN THE ARROW KEY IT WORKS THOUGH. WEIRD!

        // stop other potential audio clip from playing
        clip.stop();

        // rewind audio clip to start from the beginning
        clip.setFramePosition(0);

        // play audio clip
        clip.start();
    }

    // the sound is going to loop automatically here
    private static void playSoundLoop(Clip clip, float volume)
    {
        //-----------------------------------------------------------------------------------------------------------------
        // NOTE: this says clip is null at beginning of program, at lines 78, 107, 121, 106 and Runner class at line 19
        //-----------------------------------------------------------------------------------------------------------------

        // stop other potential audio clip from playing
        clip.stop();

        // set volume of clip
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMinimum();
        float result = range * (1 - volume / 100.0f);
        control.setValue(result);
        clip.start();

        // rewind audio clip to start from the beginning
        clip.setFramePosition(0);
        
        // play audio clip
        clip.start();

        // loop for the whole game
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // helper method to stop a sound
    private static void stopSound(Clip clip)
    {
        // stop audio clip from playing
        clip.stop();
    }

    // call these methods to play sound effects
    public static void moveSFX() {playSound(moveSound);}
    public static void landSFX() {playSound(landSound);}
    public static void cancelSFX() {playSound(cancelSound);}
    public static void fallSFX() {playSound(fallSound);}
    public static void jumpscareSFX() {playSound(jumpscareSound);}
    public static void playMusic() {playSoundLoop(backgroundMusic, 50);}
    public static void stopMusic() {stopSound(backgroundMusic);}
}

