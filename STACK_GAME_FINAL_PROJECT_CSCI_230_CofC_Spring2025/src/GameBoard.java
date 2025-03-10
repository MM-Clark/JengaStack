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

    // for falling block, initialized to start position on grid
    private int position_x = 0;
    private int position_Y = 0;
    private int tallestPartOfTower = 19;

    private static final Color COLOR_OCCUPIED = Color.LIGHT_GRAY;
    private static final Color COLOR_EMPTY = Color.WHITE;
    private static final Color COLOR_PERMANENTLY_OCCUPIED = Color.RED;

    int map[][] = new int[ROWS][COLUMNS];
    private TetrisBlock fallingBlock;
    protected BlockTypes fallingShape;
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
        int fallingBlockType = fallingBlock.newShape();
        getTypeOfShape(fallingBlockType);
    }

    public void getTypeOfShape(int currShapeType)
    {
        switch(currShapeType)
        {
            case 0:
                fallingShape = BlockTypes.Z;
                break;
            case 1: 
                fallingShape = BlockTypes.L;
                break;
            case 2:
                fallingShape = BlockTypes.O;
                break;
            case 3: 
                fallingShape = BlockTypes.S;
                break;
            case 4: 
                fallingShape = BlockTypes.I;
                break;
            case 5:
                fallingShape = BlockTypes.J;
                break;
            case 6:
                fallingShape = BlockTypes.T;
                break;
            default:
        }
    }

    public void newShapeUpdateGrid()
    {
        fallingShape = BlockTypes.T;
        // ALL SHAPES ARE LEFT-ALIGNED TO GRID
        paintShape(1);
    }

    public void paint(Graphics g)
    {
        newShapeUpdateGrid();
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
                else if(map[row][col] == 2)
                    g.setColor(COLOR_PERMANENTLY_OCCUPIED);
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

    public void paintShape(int color)
    {
        //to clear shape from grid first
        switch(fallingShape)
        {
            // this case works
            case BlockTypes.Z:
                map[position_Y][position_x+1] = color;
                map[position_Y+1][position_x+1] = color;
                map[position_Y+1][position_x] = color;
                map[position_Y+2][position_x] = color;
                break;
            // this case works
            case BlockTypes.L:
                map[position_Y][position_x] = color;
                map[position_Y+1][position_x] = color;
                map[position_Y+2][position_x] = color;
                map[position_Y+2][position_x+1] = color;
                break;
            //this works
            case BlockTypes.O:
                map[position_Y][position_x+1] = color;
                map[position_Y][position_x] = color;
                map[position_Y+1][position_x+1] = color;
                map[position_Y+1][position_x] = color;
                break;
            // works
            case BlockTypes.S:
            map[position_Y][position_x] = color;
            map[position_Y+1][position_x] = color;
            map[position_Y+1][position_x+1] = color;
            map[position_Y+2][position_x+1] = color;
                break;
            // works
            case BlockTypes.I:
                map[position_Y][position_x] = color;
                map[position_Y+1][position_x] = color;
                map[position_Y+2][position_x] = color;
                map[position_Y+3][position_x] = color;
                break;
            // works
            case BlockTypes.J:
                map[position_Y][position_x+1] = color;
                map[position_Y+1][position_x+1] = color;
                map[position_Y+2][position_x+1] = color;
                map[position_Y+2][position_x] = color;
                break;
            // works
            case BlockTypes.T:
                map[position_Y][position_x+1] = color;
                map[position_Y+1][position_x] = color;
                map[position_Y+1][position_x+1] = color;
                map[position_Y+1][position_x+2] = color; 
                break;
            default:
        }
    }

    //--------------------------------------------------------------------------------------
    // DROP BLOCK
    //---------------------------------------------------------------------------------------
    public void updateBlockPos()
    {
        //clear shape from grid first
        paintShape(0);

        //move the block one grid down
        if(!atBottom() && position_Y < 19)
            position_Y++;
        else
            paintShape(2);
    }

    //********************************************************************* */
    // working this out, have not gotten to changing block color at bottom/setting bottom
    //********************************************************************* */
    public boolean atBottom()
    {
        for(int i=0; i<COLUMNS; i++)
        {
            if(map[tallestPartOfTower][i] == 1)
                return true;
        }
        return false;
    }

    public void moveBlockLeft()
    {
        paintShape(0);
        if(!isTouchingLeftScreen())
            position_x--;
    }

    public boolean isTouchingLeftScreen()
    {
        return position_x==0;
    }

    public void moveBlockRight()
    {
        paintShape(0);
        position_x++;
    }

    // public void dropBlock()
    // {
    //     fallingBlock.dropBlock();
    // }

    /**
    * Process + remove filled rows in game board to clear space at bottom in memory.
    * return the number of rows removed in the range of [0, 4]
    */
    // public int clearLines() 
    // {
    //     // Starting from the last rows, check if a row is filled if so, move down
    //     // the occupied square. Need to check all the way to the top-row
    //     int row = ROWS - 1;
    //     int rowsRemoved = 0;
    //     boolean removeThisRow;

    //     while (row >= 0) 
    //     {
    //         removeThisRow = true;
    //         for (int col = 0; col < COLUMNS; col++) 
    //         {
    //             if (map[row][col] == 0) 
    //             {
    //                 removeThisRow = false;
    //                 break;
    //             }
    //         }

    //         if (removeThisRow) 
    //         {
    //             // delete the row by moving down the occupied slots.
    //             for (int row1 = row; row1 > 0; row1--) 
    //             {
    //                 for (int col1 = 0; col1 < COLUMNS; col1++) 
    //                 {
    //                     map[row1][col1] = map[row1 - 1][col1];
    //                 }
    //             }
    //             rowsRemoved++;
    //             // The top row shall be empty now.
    //             for (int col = 0; col < COLUMNS; col++)
    //                 map[0][col] = 0;

    //             // No change in row number. Check this row again (recursion).
    //         } 
    //         else 
    //         {
    //             // next row on top
    //             row--;
    //         }
    //     }
    //     return rowsRemoved;
    // }

}
