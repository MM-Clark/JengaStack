// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

// end screen for Tetris, plays audio as well for game
public class WinLoseScreen extends JPanel {
    //************* must add images to outside src folder + use absolute path b/c github ****************/
    private String[] loseImagePaths = {
            // ABSOLUTE PATHS
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/RedEyedFace.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/StretchFace.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/ThreeRedFigures.jpg",
    
            // "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/testerImg.jpg" //commented out, got image prepared to use for demo
    };

    private String[] relativeLoseImagePaths = {
            // RELATIVE PATHS
            "images/RedEyedFace.jpg",
            "images/StretchFace.jpg",
            "images/ThreeRedFigures.jpg",
    };

    private String winImagePath = "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/images/BalloonGhostWinScreen.jpg";
    private String relativeWinImagePath = "images/BalloonGhostWinScreen.jpg";

    public void showWinScreen(JFrame frame) throws IOException
    {
        //--------------------------------------------------------
        // show winning screen -- Do people win Jenga? If so, how?
        //--------------------------------------------------------

        // creating as BufferedImage to resize
        File imageFile = new File(winImagePath);

        // check that image file exists. Try relative path if it doesn't.
        if (!imageFile.exists())
        {
            // trying relative path
            imageFile = new File(relativeWinImagePath);
        }

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

        // SoundPlayer.winSFX();     // ideally we should play a win SFX
        // SoundPlayer.stopMusic();
    }
    
    public void showLoseScreen(JFrame frame) throws IOException
    {
        //--------------------------------------------------------
        // Show random jumpscare screen
        //--------------------------------------------------------

        // randomization, works w/ two images as tested
        int randomIndex = Randomizer.getRandomNumber(0, loseImagePaths.length-1);
        String randomImagePath = loseImagePaths[randomIndex]; // change randomIndex to loseImagePaths.length-1 for demo if need to avoid disturbing imagery

        // creating as BufferedImage to resize
        File imageFile = new File(randomImagePath);

        // check that image file exists. Try relative path if it doesn't.
        if (!imageFile.exists())
        {
            // trying relative path
            imageFile = new File(relativeLoseImagePaths[randomIndex]);
        }

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

        // stop music and play jumpscare sound
        SoundPlayer.stopMusic();
        SoundPlayer.jumpscareSFX();
    }
}
