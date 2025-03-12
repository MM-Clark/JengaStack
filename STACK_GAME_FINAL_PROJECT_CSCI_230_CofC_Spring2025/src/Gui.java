// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import java.io.IOException;

import javax.swing.JFrame;


// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Gui 
{
    // private final int FRAME_SIZE = 900;

    private JFrame frame = new JFrame();
    private GameBoard gameScreen = new GameBoard();

    private boolean keyPressed = false;

    //--------------------------------------------------------------------------------------------
    // creates GUI initially, sets up JFrame
    //--------------------------------------------------------------------------------------------
    public Gui() 
    {
        Key_binding controlKeys = new Key_binding(frame, gameScreen);
    }
}
