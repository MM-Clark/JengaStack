// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import javax.swing.JFrame;
import java.util.Scanner;

// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Runner 
{
    private static JFrame frame = new JFrame();
    private static GameBoard gameScreen = new GameBoard();
    private static Key_binding controlKeys;

    private static final int EASY_FRAME_RATE = 600; 
    private static final int MED_FRAME_RATE = 400; 
    private static final int HARD_FRAME_RATE = 300;
    //--------------------------------------------------------------------------------------------
    // creates GUI initially, sets up JFrame
    //--------------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception 
    {
        controlKeys = new Key_binding(frame, gameScreen); // see Key_Binding Class, controls functionality of keyboard input
                                    // ******* can still change to mouse if preferred (easier to test with poor dexterity)
    }
    
}
