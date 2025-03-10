// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

//--------------------------------TOWER CURRENTLY DOES NOT GROW, NEED TO MAKE RETURN TO GUESS SCREEN AFTER TOWER GROWS-------------------------
import java.awt.*;
import java.util.Random;

public class TetrisBlock extends GameBoard
{
   // final ints are capitalized b/c they are constants
   private final int SCREEN_DIMENSIONS = 900;
   private final int LEFT_SCREEN_EDGE = 0, RIGHT_SCREEN_EDGE = 830, BOTTOM_OF_STACK_Y = 700;
   // private final int SCREEN_LEFT = 0;
   // private final int SCREEN_BOTTOM = 900;
   private int xPos;
   private int yPos;
   private int speedX = 2;
   private int speedY = 2; // subject to change;
   private final int ONE_BLOCK_SIZE = GameBoard.CELL_SIZE;

   private static int[][] map;    // for occupied spaces   
   private int rows, columns; // for the single shape
   private static int currShapeType;
   // private boolean[][] mapSaved = new boolean[5][5];  //FOR IF WE ADD ROTATION FUNCTIONALITY

   // All the possible shape maps
   // Use square array 3x3, 4x4, 5x5 to facilitate rotation computation.

   // all possible shapes
   private static final int[][][] SHAPES_MAP = 
   {
      {{ 0, 1,  0 },
       { 1,  1,  0 },
       { 1,  0, 0 }},  // Z       2x3
      {{ 0, 1,  0},
       { 0, 1,  0},
       { 0, 1,  1 }},  // L         2x3
      {{ 1, 1 },
       { 1, 1 }},  // O             2x2
      {{ 0, 1,  0 },
       { 0, 1,  1  },
       { 0, 0, 1  }},   // S        2x3
      {{ 0, 1,  0, 0 },
       { 0, 1,  0, 0 },
       { 0, 1,  0, 0 },
       { 0, 1,  0, 0 }},  // I      1x4
      {{ 0, 1,  0},
       { 0, 1,  0},
       { 1,  1,  0}},  // J         2x3
      {{ 0, 1,  0 },
       { 1,  1,  1  },}   // T      3x2
   };                            

   // Each shape has its own color
   private static final Color[] SHAPES_COLOR = {
         new Color(245, 45, 65),  // Z (Red #F52D41)
         Color.ORANGE, // L
         Color.YELLOW, // O
         Color.GREEN,  // S
         Color.CYAN,   // I
         new Color(76, 181, 245), // J (Blue #4CB5F5)
         Color.PINK    // T (Purple)
   };

   private static final Random rand = new Random(); // to get random shape

   private static TetrisBlock currBlock;
   public static TetrisBlock newShape()
   {
      if(currBlock == null)
         currBlock = new TetrisBlock();

      currBlock.currShapeType = rand.nextInt(SHAPES_MAP.length);
      // Set this shape's pattern. No need to copy the contents


      currBlock.map = SHAPES_MAP[currShapeType];
      currBlock.rows = map.length;
      currBlock.columns = map[0].length;

      currBlock.xPos = ((GameBoard.COLUMNS - currBlock.columns) / 2);
      currBlock.yPos = 10;



      // outerloop:
      // for (int row = 0; row < currBlock.rows; row++) {
      //    for (int col = 0; col < currBlock.columns; col++) {
      //       // Ignore empty rows, by checking the row number
      //       //   of the first occupied square
      //       if (currBlock.map[row][col]) {
      //          currBlock.yPos = -row;
      //          break outerloop;
      //       }
      //    }
      // }

      return currBlock;
   }

   public void paint(Graphics g) 
   {
      
      int yOffset = SCREEN_DIMENSIONS/10;  // Apply a small Y_OFFSET for nicer display?!
   
      g.setColor(SHAPES_COLOR[TetrisBlock.currShapeType]);
      for (int row = 0; row < rows; row++) {
         for (int col = 0; col < columns; col++) {
            if (map[row][col]==1) {
               g.fill3DRect((xPos + col) * ONE_BLOCK_SIZE, (yPos + row) * ONE_BLOCK_SIZE + yOffset,
                  ONE_BLOCK_SIZE, ONE_BLOCK_SIZE, true);
            }
         }
      }
      
      // g.setColor(color);
      // g.fillRect(xPos, yPos, ONE_BLOCK_SIZE, ONE_BLOCK_SIZE);
   }

   public void makeBlockFall()
   {
      // xPos += speedX;
      if(!lockInPlace()) // if not time to lock in place, can move down block
         yPos += ONE_BLOCK_SIZE;
   }

   public void moveBlockLeft(int newPos)
   {
      if(!touchingSideOfScreen() && !lockInPlace()) // if not time to lock in place and block not touching side of screen, move left
         while(xPos > newPos)
            xPos -= ONE_BLOCK_SIZE;
      // side touching dealt with in touchingSideOfScreen method
   }

   public void moveBlockRight(int newPos)
   {
      if(!touchingSideOfScreen() && !lockInPlace())
         while(xPos < newPos)
            xPos += ONE_BLOCK_SIZE;
      // System.out.println(xPos);
   }

   public void moveBlockLeft()
   {
      if(!touchingSideOfScreen() && !lockInPlace()) // if not time to lock in place and block not touching side of screen, move left
            xPos -= ONE_BLOCK_SIZE;
      // side touching dealt with in touchingSideOfScreen method
   }

   public void moveBlockRight()
   {
      if(!touchingSideOfScreen() && !lockInPlace())
            xPos += ONE_BLOCK_SIZE;
      // System.out.println(xPos);
   }

   public boolean touchingSideOfScreen()
   {
      if(xPos < LEFT_SCREEN_EDGE)
      {
         xPos += ONE_BLOCK_SIZE;
         return true;
      }

      else if(xPos > RIGHT_SCREEN_EDGE)
      {
         xPos -= ONE_BLOCK_SIZE;
         return true;
      }

      return false;
   } 

   public boolean lockInPlace()  // to determine if to lock block in place at bottom of screen
   {
      if(yPos >= BOTTOM_OF_STACK_Y)
         return true;
      return false;
   }

   public void dropBlock() //drop block down now
   {
      while(!lockInPlace())
         yPos += (speedY * 3);
   }
}
