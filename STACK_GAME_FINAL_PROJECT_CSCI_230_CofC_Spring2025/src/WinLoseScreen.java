import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WinLoseScreen {
    //************* must add images to outside src folder + use absolute path b/c github ****************/
    private String[] loseImagePaths = {
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/RedEyedFace.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/testerImg.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/testerImg.jpg",
            "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/RedEyedFace.jpg"
    }; //STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025\RedEyedFace.jpg
    private String winImagePath = "STACK_GAME_FINAL_PROJECT_CSCI_230_CofC_Spring2025/testerImg.jpg"; 

    public void showWinScreen(JFrame frame) throws IOException
    {
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
        // randomization, works w/ two images as tested
        Random random = new Random();
        int randomIndex = random.nextInt(loseImagePaths.length);
        String randomImagePath = loseImagePaths[randomIndex];

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
}
