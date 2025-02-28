// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Gui implements KeyListener
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

    private StackArray<Block> stackTower;
    private WinLoseScreen endScreen;
    private StartScreen startScreen;

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
        frame.setSize(900, 900); //--------> manually set size, easier for placing boxes on x/y coordinates
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // getStartPanel(); // calls method below to get start panel
        // showStartScreen();
        frame.add(startScreen);
        getStackMaxSize();         //-------> get stack set up/instantiated

        frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
            // left right functionality with key arrows
			public void keyPressed(KeyEvent e) 
            {
				switch (e.getKeyCode()) 
                {
				    case KeyEvent.VK_LEFT:
					    startScreen.moveBlockLeft();
					    break;
				    case KeyEvent.VK_RIGHT:
					    startScreen.moveBlockRight();
					    break;
				} 
			}
			
			public void keyReleased(KeyEvent e) {
			}
		});

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
        stackTower = new StackArray<Block>(stackSize);
    }


    // action listener calls if user chooses yes
    public void yesAction() throws InterruptedException, IOException
    {
        Block newBlock = new Block(1, "block"); // temp block to put in stack
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

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}
