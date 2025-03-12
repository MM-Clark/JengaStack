// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Gui 
{
    // private final int FRAME_SIZE = 900;

    // private JFrame frame;
    // private WinLoseScreen endScreen;
    // private GameBoard gameScreen;

    private boolean keyPressed = false;

    //--------------------------------------------------------------------------------------------
    // creates GUI initially, sets up JFrame
    //--------------------------------------------------------------------------------------------
    public Gui() 
    {
        Key_binding controlKeys = new Key_binding();

        //---------------------------------------------------------------------------------------------
        // // ----------------------- TIMER SET UP ----------------------------------------------------------
        // //-----------------------------------------------------------------------------------------------
        // //make a new Timer
        // Timer timer = new Timer(275, new ActionListener() 
        // {
        //     @Override
        //     public void actionPerformed(ActionEvent e) 
        //     {

        //         gameScreen.updateBlockPos();
        //         //repaint the screen
        //         gameScreen.repaint();
        //     }
        // });

        // //start the timer after it's been created
        // timer.start();
        //------------------------------------------------------------------------------------------
    }



    //-------------------------------------------------------------------------------------------
    // Methods to show end game screens 
    //-------------------------------------------------------------------------------------------
    public void userLoses() throws IOException
    {
        // endScreen.showLoseScreen(frame);
        
        // restart application
        
        // System.out.println("User loses!!!!");
        // System.exit(0); // maybe should make replay later on 
    }

    public void userWins() throws IOException
    {
        // endScreen.showWinScreen(frame);
        // System.out.println("User wins!!!");
        // System.exit(0);
    }

    //---------------------------------------------------------------------------------------------
    // DEALS WITH MOUSE MOVEMENT
    //---------------------------------------------------------------------------------------------
    // @Override
    // public void mouseDragged(java.awt.event.MouseEvent e) {
    //     // // TODO Auto-generated method stub
    //     // throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    // }

    // @Override
    // public void mouseMoved(java.awt.event.MouseEvent e) {
    //     // System.out.println(e.getX() + " / " + FRAME_SIZE/2);

    //     // if mouse on left of center screen, move block left
    //     if(e.getX() < (FRAME_SIZE/2))
    //     {
    //         // int repeats = (int) (e.getX() / 20);
    //         // for(int i=0; i < repeats; i++)
    //         gameScreen.moveBlockLeft();
    //     }
    //     // if mouse on right of center scren, move block right
    //     else if(e.getX() > (FRAME_SIZE/2))
    //     {
    //         // int repeats = (int) ((e.getX() - (FRAME_SIZE/2))/ 20);
    //         // for(int i=0; i < repeats; i++)
    //         gameScreen.moveBlockRight();
    //     }
    // }

    //********************************************************************************************** */
    //----------------------------------------------------------------------------------------------
    // Deals with keystrokes -- CURRENTLY WORK W/O IMPLEMENTING MOUSE LISTENER BUT NOT WITH MOUSE LISTENER
    //----------------------------------------------------------------------------------------------
    //********************************************************************************************** */
    // @Override
    // public void keyTyped(KeyEvent e) {
        
    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     int keyCode = e.getKeyCode();
    //     switch (keyCode) 
    //     {   
    //         // case KeyEvent.VK_SPACE:
    //         //     if(keyPressed)
    //         //         gameScreen.dropBlock();
    //         case KeyEvent.VK_LEFT:
    //             gameScreen.moveBlockLeft();
    //             break;
    //         case KeyEvent.VK_RIGHT:
    //             gameScreen.moveBlockRight(); 
    //             break;
    //     }
    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     keyPressed = false;
    //     gameScreen.repaint();
    // }
}
