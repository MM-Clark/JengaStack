// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

import javax.sound.sampled.*;
import java.io.File;

// CLASS TO PLAY ALL SOUND EFFECTS

// HOW TO USE: 
// declare a soundplayer in your code, like this:
// SoundPlayer soundplayer = new SoundPlayer();
// then call its methods whenever you want to play a sound!

public class SoundPlayer
{
    // all sound effects
    private Clip moveSound;     // when moving block
    private Clip landSound;     // when block lands
    private Clip cancelSound;   // when block CANNOT be moved
    private Clip fallSound;     // when block moves down (UNUSED BECAUSE REALLY ANNOYING)

    // create a sound player 
    public SoundPlayer()
    {
        // load audio files immediately
        loadSounds();
    }

    // helper method to load sound file and return it
    private Clip loadClip(String path) throws Exception
    {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(path)));
        return clip;
    }

    private void loadSounds()
    {
        // load all game sounds
        try
        {
            // this path format works on my computer
            moveSound = loadClip("sounds/Move.wav");      // when moving block before placing it
            landSound = loadClip("sounds/Land.wav");      // when block lands
            cancelSound = loadClip("sounds/Cancel.wav");  // when block cannot be moved further
            fallSound = loadClip("sounds/Fall.wav");      // when block moves down (UNUSED BECAUSE REALLY ANNOYING)
        }
        // print if loading error occurs
        catch (Exception e)
        {
            System.out.println("Error when loading sounds! Check file paths");
        }
    }

    // helper method to play sound
    private void playSound(Clip clip)
    {
        // *** WEIRD ISSUE: WHEN PRESSING ARROW KEY REPEATEDLY,
        //                  SOUND DOES NOT PLAY REPEATEDLY. WHAT THE HELL???
        //                  WHEN HOLDING IT DOWN IT WORKS THOUGH. WEIRD!

        // stop other potential audio clip from playing
        clip.stop();

        // rewind audio clip to start before playing
        clip.setFramePosition(0);

        // play audio clip
        clip.start();
    }

    // call these methods to play sound effects
    public void moveSFX() {playSound(moveSound);}
    public void landSFX() {playSound(landSound);}
    public void cancelSFX() {playSound(cancelSound);}
    public void fallSFX() {playSound(fallSound);}
}

