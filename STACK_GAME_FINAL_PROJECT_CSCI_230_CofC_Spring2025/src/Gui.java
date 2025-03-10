// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Gui implements MouseMotionListener, KeyListener
{
    private final int FRAME_SIZE = 900;

    private JFrame frame;
    private StackArray<SingleBlock> stackTower;
    private WinLoseScreen endScreen;
    private GameBoard gameScreen;

    private boolean keyPressed = false;

    //--------------------------------------------------------------------------------------------
    // creates GUI initially, sets up JFrame
    //--------------------------------------------------------------------------------------------
    public Gui() 
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
        getStackMaxSize();         //-------> get stack set up/instantiated

        //-------------------------------------------------------------------------------------------
        //************************************************************************************* */
        //------------ REINCORPORATE WHEN FALLING BLOCK IS NOT NULL!!!!!!!!!!!!!!!!!-------------
        //************************************************************************************* */
        // frame.addMouseMotionListener(this);
        //***************************************************************************************/
        //------------------------------------------------------------------------------------------- 

        //-----------------------------------------------------------------------------------------  
        // IF USING ARROW KEY OPTION
        // frame.addKeyListener(new KeyListener() {
		// 	public void keyTyped(KeyEvent e) {
		// 	}
			
        //     // left right functionality with key arrows
		// 	public void keyPressed(KeyEvent e) 
        //     {
		// 		switch (e.getKeyCode()) 
        //         {
		// 		    case KeyEvent.VK_LEFT:
		// 			    startScreen.moveBlockLeft();
		// 			    break;
		// 		    case KeyEvent.VK_RIGHT:
		// 			    startScreen.moveBlockRight();
		// 			    break;
		// 		} 
		// 	}
			
		// 	public void keyReleased(KeyEvent e) {
		// 	}
		// });
        //------------------------------------------------------------------------------------------------------

        // frame.pack();            //---------> set frame as predetermined size by system
        frame.setVisible(true);
        //-------------------------------------------------------------------------------------------------


        //---------------------------------------------------------------------------------------------
        // ----------------------- TIMER SET UP ----------------------------------------------------------
        //-----------------------------------------------------------------------------------------------
        //make a new Timer
        Timer timer = new Timer(33, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

                // gameScreen.updateBlockPos();
                //repaint the screen
                gameScreen.repaint();
            }
        });

        //start the timer after it's been created
        timer.start();
        //------------------------------------------------------------------------------------------
    }

    // gets stack size
    private void getStackMaxSize()
    {
        int stackSize = 20; // **** trying to determine how many blocks to top of screen still *********
        stackTower = new StackArray<SingleBlock>(stackSize);
    }


    // action listener calls if user chooses yes
    // public void yesAction() throws InterruptedException, IOException
    // {
    //     SingleBlock newBlock = new SingleBlock(1, "block"); // temp block to put in stack
    //     if(!stackTower.isFull())
    //         stackTower.push(newBlock); // stack tower is the stack
    //     else
    //     {
    //         System.out.println("Stack full...."); // error check
    //         userLoses();
    //     }
    // }

    // public void noAction() throws IOException
    // {
    //     if(!stackTower.isFull())
    //     {
    //        System.out.println("User guessed less than stack size."); // error checking
    //        userLoses();
    //     }
    //     else if(stackTower.isFull())
    //     {
    //         System.out.println("User guessed correct stack size."); // error checking
    //         userWins();
    //     }
    // }

    //-------------------------------------------------------------------------------------------
    // Methods to show end game screens 
    //-------------------------------------------------------------------------------------------
    public void userLoses() throws IOException
    {
        endScreen.showLoseScreen(frame);
        
        // restart application
        
        // System.out.println("User loses!!!!");
        // System.exit(0); // maybe should make replay later on 
    }

    public void userWins() throws IOException
    {
        endScreen.showWinScreen(frame);
        // System.out.println("User wins!!!");
        // System.exit(0);
    }

    //---------------------------------------------------------------------------------------------
    // DEALS WITH MOUSE MOVEMENT
    //---------------------------------------------------------------------------------------------
    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        // System.out.println(e.getX() + " / " + FRAME_SIZE/2);

        // if mouse on left of center screen, move block left
        if(e.getX() < (FRAME_SIZE/2))
        {
            // int repeats = (int) (e.getX() / 20);
            // for(int i=0; i < repeats; i++)
            gameScreen.moveBlockLeft(e.getX());
        }
        // if mouse on right of center scren, move block right
        else if(e.getX() > (FRAME_SIZE/2))
        {
            // int repeats = (int) ((e.getX() - (FRAME_SIZE/2))/ 20);
            // for(int i=0; i < repeats; i++)
            gameScreen.moveBlockRight(e.getX());
        }
    }

    //********************************************************************************************** */
    //----------------------------------------------------------------------------------------------
    // Deals with keystrokes -- CURRENTLY WORK W/O IMPLEMENTING MOUSE LISTENER BUT NOT WITH MOUSE LISTENER
    //----------------------------------------------------------------------------------------------
    //********************************************************************************************** */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) 
        {   
            case KeyEvent.VK_SPACE:
                if(keyPressed)
                    gameScreen.dropBlock();
            // case KeyEvent.VK_LEFT:
            //     startScreen.moveBlockLeft();
            //     break;
            // case KeyEvent.VK_RIGHT:
            //     startScreen.moveBlockRight();
            //     break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
        gameScreen.repaint();
    }
}
