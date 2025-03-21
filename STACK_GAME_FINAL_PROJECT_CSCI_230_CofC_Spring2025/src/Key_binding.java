// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

// I WAS HERE - JULIAN
//-------------Class to represent each block in tower in the stack

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;

public class Key_binding 
{
    private Action leftAction;
    private Action rightAction;

    private JFrame frame;
    private GameBoard gameScreen;
    private WinLoseScreen endScreen; // end screen 

    // private WinLoseScreen endScreen;
    private final int FRAME_SIZE = 900;

    private boolean gameOver = false;

    public Key_binding(JFrame frameIn, GameBoard gameScreenIn)
    {
        //---------------- FRAME SET UP ------------------------------------------------------------
        frame = frameIn;
        gameScreen = gameScreenIn;
        endScreen = new WinLoseScreen(); 
        // endScreen = endScreenIn;

        frame.setTitle("Start Screen");
        frame.setSize(FRAME_SIZE, FRAME_SIZE); //--------> manually set size, easier for placing boxes on x/y coordinates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //-----------------------------------------------------------------------------------------
        //   SET UP ARROW KEYS (CAN CHANGE TO MOUSE AT SOME POINT, MOUSE LISTENER DID NOT LIKE ME)
        //-------------------------------------------------------------------------------------------
        leftAction = new AbstractAction("Left")
        {
            public void actionPerformed(ActionEvent e)
            {
                // System.out.println("left key pressed");
                gameScreen.moveBlockLeft();
                gameScreen.repaint();
            }
        };

        rightAction = new AbstractAction("Right")
        {
            public void actionPerformed(ActionEvent e)
            {
                // System.out.println("right key pressed");
                gameScreen.moveBlockRight();
                gameScreen.repaint();
            }
        };

        gameScreen.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        gameScreen.getActionMap().put("left", leftAction);

        gameScreen.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        gameScreen.getActionMap().put("right", rightAction);

        //-----------------------------------------
        // add panel to frame
        //-------------------------------------------
        frame.add(gameScreen);
        gameScreen.newGame();
        // frame.pack();            //---------> set frame as predetermined size by system
        frame.setVisible(true);
        endScreen.playRandomGameSound();
        //-------------------------------------------------------------------------------------------------

        // ----------------------- TIMER SET UP TO RUN TETRIS  ----------------------------------------------------------
        //-----------------------------------------------------------------------------------------------
        //make a new Timer
        Timer timer = new Timer(275, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

                gameOver = gameScreen.updateBlockPos();

                if(gameOver)
                {
                    // show lose screen
                    try {
                        endScreen.showLoseScreen(frame); 
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Timer timer = (Timer)e.getSource(); //*************** */
                    timer.stop();
                }
                //repaint the screen if not gameover
                gameScreen.repaint();
            }
        });

        //start the timer after it's been created
        timer.start();
    }
}
