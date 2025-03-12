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
   private final int ONE_BLOCK_SIZE = GameBoard.CELL_SIZE;

   private int currShapeType;

   // All the possible shape maps
   // Use square array 3x3, 4x4, 5x5 to facilitate rotation computation.

   // all possible shapes
   // private static final int[][][] SHAPES_MAP = 
   // {
   //    {{ 0, 1,  0 },
   //     { 1,  1,  0 },
   //     { 1,  0, 0 }},  // Z       2x3
   //    {{ 0, 1,  0},
   //     { 0, 1,  0},
   //     { 0, 1,  1 }},  // L         2x3
   //    {{ 1, 1 },
   //     { 1, 1 }},  // O             2x2
   //    {{ 0, 1,  0 },
   //     { 0, 1,  1  },
   //     { 0, 0, 1  }},   // S        2x3
   //    {{ 0, 1,  0, 0 },
   //     { 0, 1,  0, 0 },
   //     { 0, 1,  0, 0 },
   //     { 0, 1,  0, 0 }},  // I      1x4
   //    {{ 0, 1,  0},
   //     { 0, 1,  0},
   //     { 1,  1,  0}},  // J         2x3
   //    {{ 0, 1,  0 },
   //     { 1,  1,  1  },}   // T      3x2
   // };  

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

   private final Random rand = new Random(); // to get random shape

   private TetrisBlock currBlock;

   public void newShape()
   {
      // if(currBlock == null)
      //    currBlock = new TetrisBlock();
      getCurrShapeType();
      // Set this shape's pattern. No need to copy the contents
      
   }
      
   public int getCurrShapeType()
   {
      int currShapeType = rand.nextInt(BlockTypes.values().length);
      return currShapeType;
   }
}
