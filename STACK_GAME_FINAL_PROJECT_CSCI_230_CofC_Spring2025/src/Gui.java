// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Gui implements MouseMotionListener, KeyListener
{
    private JFrame frame;
    
    // private JButton start; 

    // private JRadioButton yes;
    // private JRadioButton no;

    // private ButtonGroup yesNo;

    // private JPanel startScreen;
    // private JPanel guessScreen;

    // private JLabel prompt;
    // private JLabel continueTower;
    // private JLabel welcome;

    // private JPanel towerPanel; 

    private StackArray<SingleBlock> stackTower;
    private WinLoseScreen endScreen;
    private StartScreen startScreen;

    private final int FRAME_SIZE = 900;
    private boolean keyPressed = false;
    // creates GUI initially, sets up JFrame
    public Gui() 
    {
        // declarations
        frame = new JFrame();
        // startScreen = new JPanel();
        // guessScreen = new JPanel();

        startScreen = new StartScreen();
        endScreen = new WinLoseScreen();

        // frame set up
        frame.setTitle("Start Screen");
        frame.setSize(FRAME_SIZE, FRAME_SIZE); //--------> manually set size, easier for placing boxes on x/y coordinates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // getStartPanel(); // calls method below to get start panel
        // showStartScreen();
        frame.add(startScreen);
        getStackMaxSize();         //-------> get stack set up/instantiated

        frame.addMouseMotionListener(this);
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

        // frame.pack();            //---------> predetermined size by system
        frame.setVisible(true);

        //make a new Timer
        Timer timer = new Timer(33, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

                startScreen.updateBlockPos();
                //repaint the screen
                startScreen.repaint();
            }
        });

        //start the timer after it's been created
        timer.start();

    }

    // gets random stack size
    private void getStackMaxSize()
    {
        // --------------- RANDOMLY DETERMINED SIZE OF STACK -----------------------------------------
        //-----------*********will need tweaking to improve*******--------------------
        int stackSize = 10; // **** trying to determine how many blocks to top of screen still *********
        // int min = 5; 
        // int max = 10; 
        // int rangeRandom = min + (int)(Math.random() * ((max - min) + 1));
        // int stackSize = rangeRandom;
        stackTower = new StackArray<SingleBlock>(stackSize);
    }


    // action listener calls if user chooses yes
    public void yesAction() throws InterruptedException, IOException
    {
        SingleBlock newBlock = new SingleBlock(1, "block"); // temp block to put in stack
        if(!stackTower.isFull())
            stackTower.push(newBlock); // stack tower is the stack
        else
        {
            System.out.println("Stack full...."); // error check
            userLoses();
        }
    }

    public void noAction() throws IOException
    {
        if(!stackTower.isFull())
        {
           System.out.println("User guessed less than stack size."); // error checking
           userLoses();
        }
        else if(stackTower.isFull())
        {
            System.out.println("User guessed correct stack size."); // error checking
            userWins();
        }
    }

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
            startScreen.moveBlockLeft(e.getX());
        }
        // if mouse on right of center scren, move block right
        else if(e.getX() > (FRAME_SIZE/2))
        {
            // int repeats = (int) ((e.getX() - (FRAME_SIZE/2))/ 20);
            // for(int i=0; i < repeats; i++)
            startScreen.moveBlockRight(e.getX());
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
                    startScreen.dropBlock();
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
        startScreen.repaint();
    }
}
