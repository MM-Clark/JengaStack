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
      if(!lockInPlace()) // if not time to lock in place, can move down block
         yPos += speedY;
   }

   public void moveBlockLeft(int newPos)
   {
      if(!touchingSideOfScreen() && !lockInPlace()) // if not time to lock in place and block not touching side of screen, move left
         while(xPos > newPos)
            xPos -= speedX;
      // side touching dealt with in touchingSideOfScreen method
   }

   public void moveBlockRight(int newPos)
   {
      if(!touchingSideOfScreen() && !lockInPlace())
         while(xPos < newPos)
            xPos += speedX;
      // System.out.println(xPos);
   }

   public void moveBlockLeft()
   {
      if(!touchingSideOfScreen() && !lockInPlace()) // if not time to lock in place and block not touching side of screen, move left
            xPos -= speedX;
      // side touching dealt with in touchingSideOfScreen method
   }

   public void moveBlockRight()
   {
      if(!touchingSideOfScreen() && !lockInPlace())
            xPos += speedX;
      // System.out.println(xPos);
   }

   public boolean touchingSideOfScreen()
   {
      if(xPos < 0)
      {
         xPos += 10;
         return true;
      }

      else if(xPos > 830)
      {
         xPos -= 10;
         return true;
      }

      return false;
   } 

   public boolean lockInPlace()  // to determine if to lock block in place at bottom of screen
   {
      if(yPos >= 700)
         return true;
      return false;
   }
}
