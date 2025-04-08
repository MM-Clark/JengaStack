// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import javax.swing.JFrame;


// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Runner 
{
    private static JFrame frame = new JFrame();
    private static GameBoard gameScreen = new GameBoard();
    private static Key_binding controlKeys;
    //--------------------------------------------------------------------------------------------
    // creates GUI initially, sets up JFrame
    //--------------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception 
    {
        controlKeys = new Key_binding(frame, gameScreen); // see Key_Binding Class, controls functionality of keyboard input
                                    // ******* can still change to mouse if preferred (easier to test with poor dexterity)
    }
}
