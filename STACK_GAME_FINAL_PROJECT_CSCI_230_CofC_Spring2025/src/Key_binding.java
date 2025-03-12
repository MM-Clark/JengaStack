// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

// I WAS HERE - JULIAN
//-------------Class to represent each block in tower in the stack

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

// -------------- THIS CLASS DOES NOT WORK AT ALL --------------------------
public class Key_binding 
{
    private Key_binding.LeftAction leftAction;
    private Key_binding.RightAction rightAction;
    private JFrame frame;
    private WinLoseScreen endScreen;
    private GameBoard gameScreen;
    private final int FRAME_SIZE = 900;

    public Key_binding()
    {
        //---------------- FRAME SET UP ------------------------------------------------------------
        frame = new JFrame();
        gameScreen = new GameBoard();
        endScreen = new WinLoseScreen();

        // frame set up
        frame.setTitle("Start Screen");
        frame.setSize(FRAME_SIZE, FRAME_SIZE); //--------> manually set size, easier for placing boxes on x/y coordinates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameScreen);
        gameScreen.newGame();
        // frame.pack();            //---------> set frame as predetermined size by system
        frame.setVisible(true);
        //-------------------------------------------------------------------------------------------------


        //-------------------------------------
        //   SET UP ARROW KEYS
        //-------------------------------------
        leftAction = new LeftAction();
        rightAction = new RightAction();

        gameScreen.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), leftAction);
        gameScreen.getActionMap().put("leftAction", leftAction);

        gameScreen.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), rightAction);
        gameScreen.getActionMap().put("rightAction", rightAction);

        // ----------------------- TIMER SET UP ----------------------------------------------------------
        //-----------------------------------------------------------------------------------------------
        //make a new Timer
        Timer timer = new Timer(275, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

                gameScreen.updateBlockPos();
                //repaint the screen
                gameScreen.repaint();
            }
        });

        //start the timer after it's been created
        timer.start();
    }

    private class LeftAction extends AbstractAction
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("left key pressed");
            gameScreen.moveBlockLeft();
            gameScreen.repaint();
        }
    }

    private class RightAction extends AbstractAction
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("right key pressed");
            gameScreen.moveBlockRight();
            gameScreen.repaint();
        }
    }
}
