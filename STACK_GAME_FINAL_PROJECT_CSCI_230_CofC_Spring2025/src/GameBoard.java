// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

//------------------------------------------------------------------------------------------------------
// J PANEL CLASS FOR DRAWING GAMEBOARD - GRID, BLACK BACKGROUND, AND INSTRUCTIONS
//-----------------------------------------------------------------------------------------------------
public class GameBoard extends JPanel
{
    //------------------------------------------------
    // FINAL/CONSTANT FIELDS 
    //------------------------------------------------
    private final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 900; // dimensions of JFrame/game window
    protected final static int COLUMNS = 24; // number of columns in grid, two are blacked out to 
                                            // serve as left/right barriers because of glitching
                                            // when doing any other way >> **22 FUNCTIONAL COLUMNS**
    protected final int ROWS = 20; // number of rows in grid
    public final static int CELL_SIZE = 32; // how big each grid cell is 

    //-------------------------------------------------
    // PRIMITIVE TYPES 
    //--------------------------------------------------
    // for falling block, initialized to start position on grid
    private int position_x = (COLUMNS/2)-2; // x position of the block initially when falling
    private int position_Y = 3; // initial y position of block when falling
    private int tallestPartOfTower = 19; // for determining if block is at bottom row + needs to stop falling
    private boolean gameOver = false; // for determining when the game is over for displaying win lose screen

    //------------------------------------------------
    //  COLORS
    //------------------------------------------------
    private static final Color COLOR_OCCUPIED = Color.RED; // for falling blocks
    private static final Color COLOR_EMPTY = Color.WHITE; // for empty grid
    private static final Color COLOR_PERMANENTLY_OCCUPIED = Color.BLUE; // for blocks that have fallen to the bottom
    private static final Color COLOR_HIDDEN = Color.BLACK; // hidden rows for left/right barriers to avoid exceptions
    private static final Color COLOR_DANGER_ZONE = Color.LIGHT_GRAY; // for cutoff of when your tower is too high + you lose, we don't have to use this idea
    
    //---------------------------------------------------------------------------------------
    //  BOARD            0=empty spot       1=falling block in this spot        2=tower here
    //----------------------------------------------------------------------------------------
    int map[][] = new int[ROWS][COLUMNS]; // grid for blocks to move and stack on

    //----------------------------------------------
    // FUNCTIONAL GAME OBJECTS AND ARRAYS
    //----------------------------------------------
    protected BlockTypes fallingShape = BlockTypes.L; // representation of falling block, determines shape type
                                                    // and how will be drawn on screen
    private StackArray<Integer> stackTower = new StackArray<Integer>(40); //for holding integers corresponding to block type
    private WinLoseScreen endScreen = new WinLoseScreen(); // end screen 
    private JFrame frame; // initialized as same frame from Gui and Key_binding class 
    //-----------------------------------------------------------------------------------------
    // constructor for making new game
    //------------------------------------------------------------------------------------------
    public void newGame(JFrame frame) 
    {
        // set frame
        this.frame = frame;

        // Clear the map, 0=empty, 1=falling block part, 2=tower part
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLUMNS; col++) 
                map[row][col] = 0;  // empty
        }
        // push in integers to stack, will use switch statement in newShape() method to determine 
        // shape type from these integers
        for(int i=0; i < 40; i++)
        {
            stackTower.push(i);     // this part needs some work for randomization
        }
    }

    // ----------------------------------------------------------------------------------
    //     GET NEW SHAPES BY RESETTING X/Y POSITIONS, GETTING NEW SHAPE BY POPPING STACK
    // ----------------------------------------------------------------------------------
    public void newShape()
    {
        // reset falling positions so does not fall starting at bottom of screen
        position_x = (COLUMNS/2)-2;
        position_Y = 3;
        int shapeType = -1;

        // stack should not be empty at any point during game b/c is size 40 
        // and shapes are greater than size 1, also grid is only 22x20 playeable area

        // integer is modded by 10 to reduce switch statement cases but 
        // allow for easy computing for programmer to check functionality 
        if(!stackTower.isEmpty())
            shapeType = stackTower.pop() % 10;

        switch(shapeType)
        {
            case 0:
            case 3:
                getTypeOfShape(0); // Z
                break;
            case 2:
            case 1:
                getTypeOfShape(1); // L
                break;
            case 4:
                getTypeOfShape(2); // O
                break;
            case 5:
                getTypeOfShape(3); // S
                break;
            case 7:
                getTypeOfShape(4); // I
                break;
            case 8:
                getTypeOfShape(5); // J
                break;
            case 9:
                getTypeOfShape(6); // T
                break;
            default:

        }   
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
        // ALL SHAPES ARE LEFT-ALIGNED TO GRID
        paintShape(1); // paint block as a falling block on grid
    }

    public void paint(Graphics g)
    {
        newShapeUpdateGrid(); // get a new shape
        //-----------------------------------------------------------------------------------------------------------------------------------
        // SET UP BACKGROUND
        //----------------------------------------------------------------------------------------------------------------------------------------------
        super.paintComponent(g); // paint existing frame components
        this.setBackground(Color.BLACK); // set background    
    


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
                if(col == 0 || col == COLUMNS-1) // hidden columns on left/right side are this case
                    g.setColor(COLOR_HIDDEN); // black
                else if(map[row][col] == 2) // tower 
                    g.setColor(COLOR_PERMANENTLY_OCCUPIED); // red tower
                else if(map[row][col] == 1) // falling block
                    g.setColor(COLOR_OCCUPIED); // blue falling blocks
                else if(row<=2) // top  3 rows are grey to represent a zone of tower being too tall, could also black them out
                    g.setColor(COLOR_DANGER_ZONE); // still coming up with ways to avoid glitching for differnet block heights
                else    
                    g.setColor(COLOR_EMPTY); // white
                g.fill3DRect(col*CELL_SIZE + xOffset, row*CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE, true); // fill cell
            }
        }
    }

    //---------------------------------------------------------------------------------
    // updates grid/fills in shape coordinate locations in integer grid with new number 
    // (updates whether shape is falling, empty area, or part of tower)
    //---------------------------------------------------------------------------------
    public void paintShape(int color)
    {
        //to clear shape from grid first
        switch(fallingShape)
        {
            case BlockTypes.Z:
                map[position_Y-2][position_x+1] = color;
                map[position_Y-1][position_x+1] = color;
                map[position_Y-1][position_x] = color;
                map[position_Y][position_x] = color;
                break;
            case BlockTypes.L:
                map[position_Y-2][position_x] = color;
                map[position_Y-1][position_x] = color;
                map[position_Y][position_x] = color;
                map[position_Y][position_x+1] = color;
                break;
            case BlockTypes.O:
                map[position_Y-1][position_x+1] = color;
                map[position_Y-1][position_x] = color;
                map[position_Y][position_x+1] = color;
                map[position_Y][position_x] = color;
                break;
            // works
            case BlockTypes.S:
            map[position_Y-2][position_x] = color;
            map[position_Y-1][position_x] = color;
            map[position_Y-1][position_x+1] = color;
            map[position_Y][position_x+1] = color;
                break;
            // works
            case BlockTypes.I:
                map[position_Y-2][position_x] = color;
                map[position_Y-1][position_x] = color;
                map[position_Y][position_x] = color;
                break;
            // works
            case BlockTypes.J:
                map[position_Y-2][position_x+1] = color;
                map[position_Y-1][position_x+1] = color;
                map[position_Y][position_x+1] = color;
                map[position_Y][position_x] = color;
                break;
            // works
            case BlockTypes.T:
                map[position_Y-1][position_x+1] = color;
                map[position_Y][position_x] = color;
                map[position_Y][position_x+1] = color;
                map[position_Y][position_x+2] = color; 
                break;
            default:
        }
    }

    //--------------------------------------------------------------------------------------
    //            DROP BLOCK
    //---------------------------------------------------------------------------------------
    
    // makes block fall
    public boolean updateBlockPos()
    {
        //clear old shape from grid first
        paintShape(0);

        //move the block one grid down
        if(atBottom() || isTouchingAnotherBlockBelow()) // if block is at bottom of screen or in stack, 
        {                                               // get new block or end the game
            paintShape(2); // makes block indicated as red in integer grid
            gameOver = checkGameOver(); // check if tower too tall/in grey danger zone
            if(!gameOver) // game is not over, keep getting new blocks and doing Tetris
            {
                newShape(); // get another shape
                repaint(); // repaint the screen to show updates
            }
            else // game is over, show lose screen
            {
                // *****************************************
                // THIS DOESN'T WORK YET
                // *****************************************
                try {
                    endScreen.showLoseScreen(frame); 
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else
            position_Y++; // make the block move one grid space down
        return gameOver; 
    }

    // tell if block is at bottom
    public boolean atBottom()
    {
        if(position_Y > ROWS - 2) 
            return true;
        for(int i=0; i<COLUMNS; i++)
        {
            if(map[tallestPartOfTower][i] == 1) // falling block is occupying row 19 
                return true;
        }
        return false;
    }

    // check if block has fallen onto another block, if so stay in place to avoid overlapping
    public boolean isTouchingAnotherBlockBelow()
    {
        //to clear shape from grid first, added one to all y values to avoid overlap
        switch(fallingShape)
        {
            case BlockTypes.Z:
                // there is a block below (original y position of each point + 1)
                if((map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            case BlockTypes.L:
                // there is a block below 
                if((map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2) ||
                    (map[position_Y+1][position_x] == 2) || (map[position_Y+1][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.O:
                // block below
                if((map[position_Y][position_x+1] == 2) || (map[position_Y][position_x] ==2 ) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            case BlockTypes.S:
                // block below
                if((map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y+1][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.I:
                // block below
                if((map[position_Y-1][position_x] == 2) || 
                    (map[position_Y][position_x] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            case BlockTypes.J:
                // block below
                if((map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            case BlockTypes.T:
                // block below
                if((map[position_Y][position_x+1] == 2) ||(map[position_Y+1][position_x] == 2) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x+2] == 2))
                    return true; 
                break;
            default:
                return false;
        }
        return false;
    }

    //---------------------------------------------------------------------
    //   LEFT-RIGHT FUNCTIONALITY
    //-----------------------------------------------------------------------

    public void moveBlockLeft()
    {
        if(!isTouchingLeftScreen() && !isTouchingAnotherBlockLeftRight())
        {
            paintShape(0); // clear old shape
            position_x--; // move shape one grid space left
        }
    }

    public boolean isTouchingLeftScreen()
    {
        return position_x<=1; // if block is partially in the farthest left operable column, don't move it
    }

    public void moveBlockRight()
    {
        if(!isTouchingRightScreen() && !isTouchingAnotherBlockLeftRight())
        {
            paintShape(0); // clear old shape
            position_x++; // move shape one grid space right
        }
    }

    // checks if a block is touching the right side of the screen
    public boolean isTouchingRightScreen()
    {
        switch(fallingShape)
        {
            // blocks with x + 1 as their rightmost coordinate
            case BlockTypes.Z:
            case BlockTypes.L:
            case BlockTypes.O:
            case BlockTypes.S:
            case BlockTypes.J:
                if(position_x + 1 >= COLUMNS-2)
                    return true;
                break;
            // blocks with x as their rightmost coordinate
            case BlockTypes.I:
                if(position_x >= COLUMNS-2)
                    return true;
                break;
            // blocks with x + 2 as their rightmost coordinate
            case BlockTypes.T:
                if(position_x + 2 >= COLUMNS-2)
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }

    public boolean isTouchingAnotherBlockLeftRight()
    {
        //to clear shape from grid first, added one to all y values to avoid overlap
        switch(fallingShape)
        {
            case BlockTypes.Z:
                // block to right (original x position of each point + 1)
                if((map[position_Y-2][position_x+2] == 2) || (map[position_Y-1][position_x+2] == 2) ||
                    (map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                //there is a block to the left (original x position of each point - 1)
                else if((map[position_Y-2][position_x] == 2) || (map[position_Y-1][position_x] == 2) ||
                    (map[position_Y-1][position_x-1] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.L:
                // there is a block to the right
                if((map[position_Y-2][position_x+1] == 2) || (map[position_Y-1][position_x+1] == 2) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y][position_x+2] == 2))
                    return true;
                // there is a block to the left
                else if((map[position_Y-2][position_x-1] == 2) || (map[position_Y-1][position_x-1] == 2) ||
                    (map[position_Y][position_x-1] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.O:
                // block to right
                if((map[position_Y-1][position_x+2] == 2) || (map[position_Y-1][position_x+1] ==2 ) ||
                    (map[position_Y][position_x+2] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                // block to left
                else if((map[position_Y-1][position_x] == 2) || (map[position_Y-1][position_x-1] ==2 ) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.S:
                // block to right
                if((map[position_Y-2][position_x+1] == 2) || (map[position_Y-1][position_x+1] == 2) ||
                    (map[position_Y-1][position_x+2] == 2) || (map[position_Y][position_x+2] == 2))
                    return true;
                // block to left
                else if((map[position_Y-2][position_x-1] == 2) || (map[position_Y-1][position_x-1] == 2) ||
                    (map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.I:
                // block to right
                if((map[position_Y-2][position_x+1] == 2) || 
                    (map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                // block to left
                else if((map[position_Y-2][position_x-1] == 2) || 
                    (map[position_Y-1][position_x-1] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.J:
                // block to right
                if((map[position_Y-2][position_x+2] == 2) || (map[position_Y-1][position_x+2] == 2) ||
                    (map[position_Y][position_x+2] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                // block to left
                else if((map[position_Y-2][position_x] == 2) || (map[position_Y-1][position_x] == 2) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.T:
                if((map[position_Y-1][position_x+2] == 2) ||(map[position_Y][position_x+1] == 2) ||
                    (map[position_Y][position_x+2] == 2) || (map[position_Y][position_x+3] == 2))
                    return true; 
                // block to left
                else if((map[position_Y-1][position_x] == 2) ||(map[position_Y][position_x-1] == 2) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y][position_x+1] == 2))
                    return true; 
                break;
            default:
                return false;
        }
        return false;
    }

    //-------------------------------------------------------------------------------
    //       Check if game over / blocks have spilled into grey danger zone
    //--------------------------------------------------------------------------------
    public boolean checkGameOver()
    {
        //**************************************************** */
        //******             THIS NEEDS WORK ***************** */
        //**************************************************** */
        return position_Y <= 2; // aka y has entered danger zone
    }
}
