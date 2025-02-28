// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston
import java.awt.*;
import javax.swing.JPanel;

public class StartScreen extends JPanel 
{
    private final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 900;

    public void paintComponent(Graphics g)
    {
        //draw the background, set color to BLACK and fill in a rectangle
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH - 10, WINDOW_HEIGHT - 15);
    
        //update score
        g.setColor(Color.YELLOW);
        //the drawString method needs a String to print, and a location to print it at.
        g.drawString("Welcome to Jenga Stack!", (WINDOW_WIDTH/2) - 50, (WINDOW_HEIGHT/30));
    }
}
