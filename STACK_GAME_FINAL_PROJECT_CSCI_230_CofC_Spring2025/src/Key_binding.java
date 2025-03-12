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
    private WinLoseScreen endScreen;
    private GameBoard gameScreen;
    private final int FRAME_SIZE = 900;

    private boolean gameOver = false;

    public Key_binding()
    {
        //---------------- FRAME SET UP ------------------------------------------------------------
        frame = new JFrame();
        gameScreen = new GameBoard();
        endScreen = new WinLoseScreen();

        frame.setTitle("Start Screen");
        frame.setSize(FRAME_SIZE, FRAME_SIZE); //--------> manually set size, easier for placing boxes on x/y coordinates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //-------------------------------------
        //   SET UP ARROW KEYS
        //-------------------------------------
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
        //-------------------------------------------------------------------------------------------------

        // ----------------------- TIMER SET UP ----------------------------------------------------------
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
                    Timer timer = (Timer)e.getSource();
                    timer.stop();
                    System.out.println("END");
                }
                //repaint the screen
                gameScreen.repaint();
            }
        });

        //start the timer after it's been created
        timer.start();

        if(gameOver)
        {
            try {
                endScreen.showLoseScreen(frame);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
