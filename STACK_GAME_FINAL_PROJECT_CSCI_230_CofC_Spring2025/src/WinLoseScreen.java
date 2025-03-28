// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import javax.sound.sampled.*;

// end screen for Tetris, plays audio as well for game
public class WinLoseScreen extends JPanel {
    //************* must add images to outside src folder + use absolute path b/c github ****************/
    private String[] loseImagePaths = {
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/RedEyedFace.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/StretchFace.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/ThreeRedFigures.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/testerImg.jpg" // MUST BE LAST PATH FOR DEMO PURPOSES
    }; //STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025\RedEyedFace.jpg
    private String winImagePath = "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/BalloonGhostWinScreen.jpg"; 

    // tried to do audio built in -- needs wav files
    private String[] audioPaths = {
        "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/StaticAudio.wav", // royalty free sound on YouTube from Dar Golan
        "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/JengaStackRec1_Reversed.wav",
        "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/sounds/QuickFoxLazyDog.wav"
    };

    // audio clips
    Clip backgroundMusic;
    Clip jumpScareMusic;

    // may add audio if have time here as path --> currently have two mp3 files 
    // QuickFoxLazyDog is "The quick fox jumped over the lazy dog" distorted, reversed, equalized, slowed
    // SallySeashells is "Sammy collects seashells by the seashores on Sundays" distorted, reversed, inverted, equalized, slowed
    // -----------------> both files could use additional editing
    // -----------------> JengaStackRec1_Reversed may be used again/edited diff. if lack time to make additional files
    
    
    public void showWinScreen(JFrame frame) throws IOException
    {
        //--------------------------------------------------------
        // show winning screen -- Do people win Jenga? If so, how?
        //--------------------------------------------------------

        // creating as BufferedImage to resize
        File imageFile = new File(winImagePath);
        BufferedImage originalImage = ImageIO.read(imageFile);

        // resize img to fit frame
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        Image resizedImage = originalImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(imageIcon);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
        frame.revalidate();
    }
    
    public void showLoseScreen(JFrame frame) throws IOException
    {
        //--------------------------------------------------------
        // Show random jumpscare screen
        //--------------------------------------------------------

        // randomization, works w/ two images as tested
        Random random = new Random();
        int randomIndex = random.nextInt(loseImagePaths.length-1);
        String randomImagePath = loseImagePaths[randomIndex]; // change randomIndex to loseImagePaths.length-1 for demo if need to avoid disturbing imagery

        // creating as BufferedImage to resize
        File imageFile = new File(randomImagePath);
        BufferedImage originalImage = ImageIO.read(imageFile);

        // resize img to fit frame
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        Image resizedImage = originalImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);

        // image icon is made to put in JFrame
        ImageIcon imageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(imageIcon);

        // update JFrame
        frame.getContentPane().removeAll();
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
        frame.revalidate();
    }

    public void playBackgroundGameSound()
    {
        //-------------------------------------------------------------
        //   Get audio path for background gameplay sound
        //-------------------------------------------------------------

        // play random game sound
        // Random random = new Random();
        // int randomIndex = random.nextInt(audioPaths.length);
        String audioPath = audioPaths[0];
        try {
            File soundFile = new File(audioPath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioIn);
            backgroundMusic.start();
            // Keep the program running until the sound finishes playing
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        //------------------------------------------------------------------------------------
        // ------------------   AUDIO TIMER    -----------------------------------------------
        //------------------------------------------------------------------------------------
        // timer on millisecond delay
        Timer audioTimer = new Timer(45000, new ActionListener() // clip is 45 sec. long; 45 seconds = 45,000 milliseconds
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                backgroundMusic.start();
            }
        });

        //start the timer after it's been created
        audioTimer.start();
    }

    public void createJumpScareSound()
    {
        String audioPath = audioPaths[2];
        try {
            File soundFile = new File(audioPath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            jumpScareMusic = AudioSystem.getClip();
            jumpScareMusic.open(audioIn);
            // Keep the program running until the sound finishes playing
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playJumpScareSound() throws InterruptedException
    {
        backgroundMusic.stop(); // halt background music
        jumpScareMusic.start(); // start jumpscare sound  
    }
}
