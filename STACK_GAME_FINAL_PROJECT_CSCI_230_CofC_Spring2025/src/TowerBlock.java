// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

//--------------------------------TOWER CURRENTLY DOES NOT GROW, NEED TO MAKE RETURN TO GUESS SCREEN AFTER TOWER GROWS-------------------------
import java.awt.*;
import javax.swing.*;
public class TowerBlock extends JPanel 
{
   // final ints are capitalized b/c they are constants
   private final int SCREEN_DIMENSIONS = 900;
   private final int SCREEN_LEFT = 0;
   private final int SCREEN_BOTTOM = 900;
   private final int X_POS = 450;
   private final int Y_POS = 600;
   private final int WIDTH = 50;
   private int height = 50; // subject to change;

   @Override
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      // questionable way to achieve black background
      g.setColor(Color.BLACK);
      g.fillRect(SCREEN_LEFT, SCREEN_BOTTOM, SCREEN_DIMENSIONS, SCREEN_DIMENSIONS);

      g.setColor(Color.BLACK);
      g.fillRect(X_POS, Y_POS, WIDTH, height);
   }

   public void go(JFrame frame) 
   {
      // ---- the block will only show if directly added to JFrame, JPanel is no show for block ------
      TowerBlock block = new TowerBlock();
      frame.getContentPane().removeAll();

      // JLabel adding = new JLabel("Adding another level to the tower!");
      // adding.setForeground(Color.GREEN);
      // currPanel.add(adding); // add label 

      frame.add(block, BorderLayout.CENTER); // add the tower block centered
      frame.revalidate(); // update frame without glitching out (REPAINT DOES NOT WORK)
   }

   public void updateHeight()
   {
      height += 300;
   }
}
