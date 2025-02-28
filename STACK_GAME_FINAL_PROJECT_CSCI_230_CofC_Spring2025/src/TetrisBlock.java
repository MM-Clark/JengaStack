// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

//--------------------------------TOWER CURRENTLY DOES NOT GROW, NEED TO MAKE RETURN TO GUESS SCREEN AFTER TOWER GROWS-------------------------
import java.awt.*;

public class TetrisBlock 
{
   // final ints are capitalized b/c they are constants
   private final int SCREEN_DIMENSIONS = 900;
   // private final int SCREEN_LEFT = 0;
   // private final int SCREEN_BOTTOM = 900;
   private int xPos;
   private int yPos;
   private int speedX;
   private int speedY; // subject to change;
   private int speed;
   private int size;
   private Color color;

   public TetrisBlock(int x, int y, int speedHorizontal, int speedVertical, int speed, Color color, int size)
   {
      xPos = x; // must use this keyword if any of parameter variable names are changed to match above class variables
      yPos = y;
      speedX = speedHorizontal;
      speedY = speedVertical;
      this.speed = speed;
      this.color = color; 
      this.size = size;
   }

   public void paint(Graphics g) 
   {
      g.setColor(color);
      g.fillRect(xPos, yPos, size, size);
   }

   // public void showTower(JFrame frame) 
   // {
   //    // ---- the block will only show if directly added to JFrame, JPanel is no show for block ------
   //    TetrisBlock block = new TetrisBlock();
   //    frame.getContentPane().removeAll();

   //    // JLabel adding = new JLabel("Adding another level to the tower!");
   //    // adding.setForeground(Color.GREEN);
   //    // currPanel.add(adding); // add label 

   //    frame.add(block, BorderLayout.CENTER); // add the tower block centered
   //    frame.revalidate(); // update frame without glitching out (REPAINT DOES NOT WORK)
   // }

   // public void updateHeight()
   // {
   //    X_POS += 10;
   // }
}
