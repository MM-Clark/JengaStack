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
   private final int ONE_BLOCK_SIZE = 30;
   private Color color;


   private char[][] map;       // changed from boolean
   private int rows, columns;
   int currIdx;

   public TetrisBlock(int x, int y, int speedHorizontal, int speedVertical, Color color)
   {
      xPos = x; // must use this keyword if any of parameter variable names are changed to match above class variables
      yPos = y;
      speedX = speedHorizontal;
      speedY = speedVertical;
      this.color = color; 
   }

   public void paint(Graphics g) 
   {
      g.setColor(color);
      g.fillRect(xPos, yPos, ONE_BLOCK_SIZE, ONE_BLOCK_SIZE);
   }

   public void makeBlockFall()
   {
      // xPos += speedX;
      yPos += speedY;
   }

   public void moveBlockLeft()
   {
      xPos -= speedX;
   }

   public void moveBlockRight()
   {
      xPos += speedX;
      // System.out.println(xPos);
   }
}
