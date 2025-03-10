// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

//------------------------------------------------------------------------------------------------------
// J PANEL CLASS FOR DRAWING GAMEBOARD - GRID, BLACK BACKGROUND, AND INSTRUCTIONS
//-----------------------------------------------------------------------------------------------------
public class GameBoard extends JPanel  
{
    private final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 900;
    protected final static int COLUMNS = 22; 
    protected final int ROWS = 20;
    public final static int CELL_SIZE = 32;

    private static final Color COLOR_OCCUPIED = Color.LIGHT_GRAY;
    private static final Color COLOR_EMPTY = Color.WHITE;

    int map[][] = new int[ROWS][COLUMNS];
    private TetrisBlock fallingBlock;

    //-----------------------------------------------------------------------------------------
    // constructor for making new game
    //------------------------------------------------------------------------------------------
    public void newGame() 
    {
        // Clear the map
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLUMNS; col++) 
                map[row][col] = 0;  // empty
        }
      
      // Get a new random shape
      fallingBlock = TetrisBlock.newShape();
    }

    public void paint(Graphics g)
    {
        //-----------------------------------------------------------------------------------------------------------------------------------
        // SET UP BACKGROUND
        //----------------------------------------------------------------------------------------------------------------------------------------------
        super.paintComponent(g);
        this.setBackground(Color.BLACK);     
    
        //------------------------------------------------------------------------------------------------------------------------------
        // SCORE + INSTRUCTIONS 
        // **** could make text different size + update x/y positions to avoid overlapping text **********
        //-------------------------------------------------------------------------------------------------------------------------------
        g.setColor(Color.YELLOW);
        g.drawString("Welcome to Jenga Stack!", (WINDOW_WIDTH/2) - 70, (WINDOW_HEIGHT/30)); //weird divided numbers are (x, y) positions
        g.drawString("Move mouse to move block", (WINDOW_WIDTH/2) - 70, ((WINDOW_HEIGHT/30) + 20));
        //--------------------------------------------------------------------------------------------------------------------------------

        //-------------------------------------------------------------------------------------------------------------------------------------------------
        // PAINT/UPDATE GRID
        //------------------------------------------------------------------------------------------------------------------------------------------------
        int xOffset = WINDOW_WIDTH/10;   // apply offsets to make the grid centered, 20 rows x 27 columns works for 90 offset (WINDOW_WIDTH or HEIGHT/10)
        int yOffset = WINDOW_HEIGHT/10;
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLUMNS; col++) 
            {
                if(map[row][col] == 1) 
                    g.setColor(COLOR_OCCUPIED); 
                else    
                    g.setColor(COLOR_EMPTY);
                g.fill3DRect(col*CELL_SIZE + xOffset, row*CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE, true);
            }
        }
        //-------------------------------------------------------------------------------------------------------------------------------------------------------------------


        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // *************************************************************************************************************
        // PAINT FALLING BLOCK
        // **********************************************************************************************************************
        //--------------------------------------------------------------------------------------------------------------------------------------
        // Also paint the Shape encapsulated
        // fallingBlock.paint(g);
        //------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void updateBlockPos()
    {
        //move the ball one frame
        fallingBlock.makeBlockFall();
    }

    public void moveBlockLeft(int newPos)
    {
        fallingBlock.moveBlockLeft(newPos);
    }

    public void moveBlockRight(int newPos)
    {
        fallingBlock.moveBlockRight(newPos);
    }

    // public void moveBlockLeft()
    // {
    //     fallingBlock.moveBlockLeft();
    // }

    // public void moveBlockRight()
    // {
    //     fallingBlock.moveBlockRight();
    // }

    public void dropBlock()
    {
        fallingBlock.dropBlock();
    }

    /**
    * Process + remove filled rows in game board to clear space at bottom in memory.
    * return the number of rows removed in the range of [0, 4]
    */
   public int clearLines() 
    {
        // Starting from the last rows, check if a row is filled if so, move down
        // the occupied square. Need to check all the way to the top-row
        int row = ROWS - 1;
        int rowsRemoved = 0;
        boolean removeThisRow;

        while (row >= 0) 
        {
            removeThisRow = true;
            for (int col = 0; col < COLUMNS; col++) 
            {
                if (map[row][col] == 0) 
                {
                    removeThisRow = false;
                    break;
                }
            }

            if (removeThisRow) 
            {
                // delete the row by moving down the occupied slots.
                for (int row1 = row; row1 > 0; row1--) 
                {
                    for (int col1 = 0; col1 < COLUMNS; col1++) 
                    {
                        map[row1][col1] = map[row1 - 1][col1];
                    }
                }
                rowsRemoved++;
                // The top row shall be empty now.
                for (int col = 0; col < COLUMNS; col++)
                    map[0][col] = 0;

                // No change in row number. Check this row again (recursion).
            } 
            else 
            {
                // next row on top
                row--;
            }
        }
        return rowsRemoved;
    }

}
