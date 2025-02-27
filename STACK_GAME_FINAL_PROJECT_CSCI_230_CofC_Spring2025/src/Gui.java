// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

// provides user interface functionality of when to add/remove JPanels, etc; also for pushing/popping stack
public class Gui implements ActionListener
{
    private JFrame frame;
    
    private JButton start; 

    private JRadioButton yes;
    private JRadioButton no;

    private ButtonGroup yesNo;

    private JPanel startScreen;
    private JPanel guessScreen;

    private JLabel prompt;
    private JLabel continueTower;
    private JLabel welcome;

    // private JPanel towerPanel; 

    private StackArray<Block> stackTower;
    private WinLoseScreen wls;
    TowerBlock t;

    // creates GUI initially, sets up JFrame
    public Gui() 
    {
        // declarations
        frame = new JFrame();
        startScreen = new JPanel();
        guessScreen = new JPanel();

        wls = new WinLoseScreen();
        t = new TowerBlock();

        // frame set up
        frame.setTitle("Start Screen");
        frame.setSize(900, 900); //--------> manually set size, easier for placing boxes on x/y coordinates
        getStartPanel(); // calls method below to get start panel
        showStartScreen();

        // frame.pack();            //---------> predetermined size by system
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void getStartPanel() // start Screen JPanel
    {
        // ---------- set up startScreen --------------
        startScreen.setLayout(new BoxLayout(startScreen, BoxLayout.Y_AXIS)); // to make stuff align vertically
        startScreen.setBackground(Color.BLACK);
        //startScreen.add(Box.createVerticalGlue());        //-------> in case tries to get uncentered
        
        // welcome message
        welcome = new JLabel("Welcome to Stack Jenga Horror Game!"); // this text can definitely be edited
        welcome.setForeground(Color.RED); // so can any colors used, these are just filler for now
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        startScreen.add(welcome);

        // Add vertical space
        startScreen.add(Box.createVerticalStrut(30)); 

        // start button
        start = new JButton("START");
        start.setPreferredSize(new Dimension(1200, 500));
        start.setBackground(Color.RED);
        start.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        start.addActionListener(this);
        startScreen.add(start);

        //startScreen.add(Box.createVerticalGlue());    //---> in case tries to get uncentered
    }

    private void showStartScreen()
    {
        frame.getContentPane().removeAll();
        frame.add(startScreen);
        frame.setVisible(true);
    }

    // gets random stack size
    private void getStackSize()
    {
        // --------------- RANDOMLY DETERMINED SIZE OF STACK -----------------------------------------
        //-----------*********will need tweaking to improve*******--------------------
        int min = 5; // definitely needs adjustment
        int max = 10; // also needs adjustment
        int rangeRandom = min + (int)(Math.random() * ((max - min) + 1));
        int stackSize = rangeRandom;
        stackTower = new StackArray<Block>(stackSize);
    }

    // sets up GuessScreen JPanel 
    private void getGuessPanel() // for when user is stacking the tower
    {
        //----------set up guess panel--------------------
        guessScreen.setBackground(Color.BLACK);
        startScreen.setLayout(new BoxLayout(startScreen, BoxLayout.Y_AXIS)); // to make stuff align vertically

        // user directions
        prompt = new JLabel("A random number has been assigned to you. Build the tower to the number.");
        prompt.setForeground(Color.RED);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        guessScreen.add(prompt);

        // Add vertical space
        guessScreen.add(Box.createVerticalStrut(30)); 

        //******************************************************************************************************** */
        // ***** user prompt to keep building, HAVE HAD ISSUES GETTING BELOW PROMPT JLABEL ************* 
        continueTower = new JLabel("\nDo you want to keep building the tower?"); // will fix dialogue with int i + for loop later
        continueTower.setForeground(Color.WHITE);
        continueTower.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally --> THIS NEEDS TO BE FIXED
        guessScreen.add(continueTower);
        //******************************************************************************************************** */

        // Add vertical space
        guessScreen.add(Box.createVerticalStrut(30)); 

        // yes option
        yes = new JRadioButton("Yes! Keep Going");
        yes.addActionListener(this); // adds action listener to get user input 
        guessScreen.add(yes); // *****    SEE END OF CODE FOR ACTION LISTENER METHOD   **********

        // no option
        no = new JRadioButton("No. Stop.");
        no.addActionListener(this);
        guessScreen.add(no);

        // grouping yes/no buttons
        yesNo = new ButtonGroup();
        yesNo.add(yes);
        yesNo.add(no);

        guessScreen.add(yes);
        guessScreen.add(no);
    }

    private void showGuessScreen()
    {
        // updating screen w/o glitching
        frame.getContentPane().removeAll();;
        frame.add(guessScreen);
        frame.revalidate();
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
        //********************************************************************************************************* */
        // switches to tower screen ***** TOWERBLOCK CLASS NEEDS TO INCREASE HEIGHT OVER TIME *********
        //***************************************************************************************************** */
        t.go(frame);

        //***************************************************************************************************** */
        // Thread.sleep(1000); // to put in delay to show tower screen --> CAUSES TOWER TO NOT SHOW RIGHT NOW
        // showGuessScreen();
        //********************************************************************************************************* */
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
        wls.showLoseScreen(frame);
        
        // timer to show losing screen
        

        // restart application
        // showStartScreen();
        
        // System.out.println("User loses!!!!");
        // System.exit(0); // maybe should make replay later on 
    }

    public void userWins() throws IOException
    {
        wls.showWinScreen(frame);
        // System.out.println("User wins!!!");
        // System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) // for making buttons do things
    {
        if(e.getSource() == start) // start screen start button
        {
            getStackSize();
            getGuessPanel();
            showGuessScreen();
        }
        else if(e.getSource() == yes)
        {
            try {
                yesAction();
            } catch (InterruptedException | IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getSource() == no)
        {
            try {
                noAction();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
