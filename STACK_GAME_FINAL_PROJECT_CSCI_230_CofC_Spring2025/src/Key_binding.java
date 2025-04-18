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
import java.util.Timer;
import java.util.TimerTask;

public class Key_binding 
{
    private Action leftAction;
    private Action rightAction;
    private Action spaceAction;
    private Action rAction;
    private Action downAction;


    private JFrame frame;
    private GameBoard gameScreen;
    private WinLoseScreen endScreen; // end screen 
    private Timer timer;

    private final int FRAME_SIZE = 900;

    private final int NORMAL_FRAME_RATE = 500; // avg frame rate
    private final int FAST_FRAME_RATE = 75; // will go faster b/c less time btwn timer runs

    private int fallSpeed = NORMAL_FRAME_RATE;
    private boolean gameOver = false;
    private boolean gameWon = false;

    public Key_binding(JFrame frameIn, GameBoard gameScreenIn)
    {
        //---------------- FRAME SET UP ------------------------------------------------------------
        frame = frameIn;
        gameScreen = gameScreenIn;
        endScreen = new WinLoseScreen(); 

        frame.setTitle("Start Screen");
        frame.setSize(FRAME_SIZE, FRAME_SIZE); //--------> manually set size, easier for placing boxes on x/y coordinates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //-----------------------------------------------------------------------------------------
        //   SET UP ARROW KEYS (CAN CHANGE TO MOUSE AT SOME POINT, MOUSE LISTENER DID NOT LIKE ME)
        //-------------------------------------------------------------------------------------------
        leftAction = new AbstractAction("left")
        {
            public void actionPerformed(ActionEvent e)
            {
                gameScreen.moveBlockLeft();
                gameScreen.repaint();
            }
        };

        rightAction = new AbstractAction("right")
        {
            public void actionPerformed(ActionEvent e)
            {
                gameScreen.moveBlockRight();
                gameScreen.repaint();
            }
        };

        //*************************************  */
        // REPLAY DOES NOT WORK                  */
        //************************************** */
        spaceAction = new AbstractAction("space") 
        {
            public void actionPerformed(ActionEvent e)
            {
                resetFrame();
                startGame();
            }
        };

        //---------------------------------------------------//
        //         change block type mid-fall                //
        //---------------------------------------------------//
        rAction = new AbstractAction("r")
        {
            public void actionPerformed(ActionEvent e)
            {
                if(gameScreen.isTouchingAnotherBlockLeft() || gameScreen.isTouchingAnotherBlockRight())
                    return;
                gameScreen.paintShape(0);
                gameScreen.newShape();
            }
        };

        // -------------------------------------------------
        //         drop block - increases fall speed 
        // -------------------------------------------------
        downAction = new AbstractAction("down")
        {
            public void actionPerformed(ActionEvent e)
            {
                increaseFallSpeed();
            }
        };

        // add left-right functionality to gameplay screen
        gameScreen.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        gameScreen.getActionMap().put("left", leftAction);

        gameScreen.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        gameScreen.getActionMap().put("right", rightAction);

        // add get-new-block-type to gameplay screen
        gameScreen.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "r");
        gameScreen.getActionMap().put("r", rAction);

        // drop block on gameplay screen
        gameScreen.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        gameScreen.getActionMap().put("down", downAction);

        // add space to replay to end screen
        endScreen.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "right");
        endScreen.getActionMap().put("space", spaceAction);

        startGame();
    }

    public void startGame()
    {
        //-----------------------------------------
        // add panel to frame
        //-------------------------------------------
        frame.add(gameScreen);
        gameScreen.newGame(this);
        frame.setVisible(true);

        // load sounds and start playing music
        SoundPlayer.loadSounds();
        SoundPlayer.playMusic();
        
        // ----------------------- TIMER SET UP TO RUN TETRIS  ----------------------------------------------------------
        timer = new Timer();
        startTimer(); // run timer
    }

    public void startTimer()
    {
        TimerTask task = new TimerTask() 
        {
            @Override
            public void run()
            {
                runGame();
                this.cancel();
                startTimer();
            }
        };

        timer.schedule(task, fallSpeed, fallSpeed);
        int countDeletedTasks = timer.purge(); // removes canceled tasks from timer
    }
    
    public void runGame()
    {
        gameOver = gameScreen.updateBlockPos(); // make block fall
        gameWon = gameScreen.checkScoreForWin(); // add to score

        if(gameOver || gameWon) // if user lost or user won
        {
            if(gameOver) // user lost
            {
                // show lose screen
                try {
                    //************************************************* */
                    //***** DONE IN WAY THAT DOES NOT SUPPORT  ******** */
                    //*************   REPLAY BACKGROUND MUSIC!   ****** */
                    //************************************************* */
                    endScreen.showLoseScreen(frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                // show win screen
                try {
                    endScreen.showWinScreen(frame); 
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            timer.cancel();
        }
        //repaint the screen if not gameover
        gameScreen.repaint();
    }

    //-----------------------------
    // clear out frame
    //-----------------------------
    public void resetFrame()
    {
        frame.getContentPane().removeAll();
    }

    //-----------------------------------------------
    // reset block fall speed
    //-----------------------------------------------
    public void resetFall()
    {
        fallSpeed = NORMAL_FRAME_RATE; 
    }

    //------------------------------------------------
    // make block fall faster
    //------------------------------------------------ 
    public void increaseFallSpeed()
    {
        fallSpeed = FAST_FRAME_RATE; 
    }
}
