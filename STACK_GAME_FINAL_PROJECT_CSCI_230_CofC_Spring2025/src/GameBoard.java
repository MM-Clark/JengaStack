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
    private final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 900;
    protected final static int COLUMNS = 24; 
    protected final int ROWS = 20;
    public final static int CELL_SIZE = 32;

    // for falling block, initialized to start position on grid
    private int position_x = 10;
    private int position_Y = 3;
    private int tallestPartOfTower = 19;
    private boolean gameOver = false;

    private static final Color COLOR_OCCUPIED = Color.RED; // for falling blocks
    private static final Color COLOR_EMPTY = Color.WHITE; // for empty grid
    private static final Color COLOR_PERMANENTLY_OCCUPIED = Color.BLUE; // for blocks that have fallen to the bottom
    private static final Color COLOR_HIDDEN = Color.BLACK; // hidden rows for left/right barriers to avoid exceptions
    private static final Color COLOR_DANGER_ZONE = Color.LIGHT_GRAY; // for cutoff of when your tower is too high + you lose, we don't have to use this idea
    
    int map[][] = new int[ROWS][COLUMNS];

    protected BlockTypes fallingShape = BlockTypes.L;
    private StackArray<Integer> stackTower = new StackArray<Integer>(40); //for holding integers corresponding to block type
    private WinLoseScreen endScreen = new WinLoseScreen();
    private JFrame frame;
    //-----------------------------------------------------------------------------------------
    // constructor for making new game
    //------------------------------------------------------------------------------------------
    public void newGame(JFrame frame) 
    {
        this.frame = frame;
        // Clear the map
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLUMNS; col++) 
                map[row][col] = 0;  // empty
        }
        
        for(int i=0; i < 40; i++)
        {
            stackTower.push(i);     // this part needs some work for randomization
        }
    }

    // -------------------------------------------------------------
    //     GET NEW SHAPE
    // -------------------------------------------------------------
    public void newShape()
    {
        // Get shapes to stack >> NEEDS EDITING SEVERELY
        position_x = COLUMNS/2;
        position_Y = 3;
        int shapeType = -1;
        if(!stackTower.isEmpty())
            shapeType = stackTower.pop() % 10;
        // System.out.println("Shape type number: " + shapeType);
        switch(shapeType)
        {
            case 0:
            case 3:
                getTypeOfShape(0);
                break;
            case 2:
            case 1:
                getTypeOfShape(1);
                break;
            case 4:
                getTypeOfShape(2);
                break;
            case 5:
                getTypeOfShape(3);
                break;
            case 7:
                getTypeOfShape(4);
                break;
            case 8:
                getTypeOfShape(5);
                break;
            case 9:
                getTypeOfShape(6);
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
                if(col == 0 || col == COLUMNS-1)
                    g.setColor(COLOR_HIDDEN);
                else if(map[row][col] == 2)
                    g.setColor(COLOR_PERMANENTLY_OCCUPIED);
                else if(map[row][col] == 1) 
                    g.setColor(COLOR_OCCUPIED); 
                else if(row<=2)
                    g.setColor(COLOR_DANGER_ZONE);
                else    
                    g.setColor(COLOR_EMPTY);
                g.fill3DRect(col*CELL_SIZE + xOffset, row*CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE, true);
            }
        }
    }

    //---------------------------------------------------------------------------------
    // updates grid/fills in shape coordinate locations in integer grid with new number
    //---------------------------------------------------------------------------------
    public void paintShape(int color)
    {
        //to clear shape from grid first
        switch(fallingShape)
        {
            // this case works
            case BlockTypes.Z:
                map[position_Y-2][position_x+1] = color;
                map[position_Y-1][position_x+1] = color;
                map[position_Y-1][position_x] = color;
                map[position_Y][position_x] = color;
                break;
            // this case works
            case BlockTypes.L:
                map[position_Y-2][position_x] = color;
                map[position_Y-1][position_x] = color;
                map[position_Y][position_x] = color;
                map[position_Y][position_x+1] = color;
                break;
            //this works
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
        //clear shape from grid first
        paintShape(0);

        //move the block one grid down
        if(atBottom() || isTouchingAnotherBlockBelow())
        {
            paintShape(2); // makes block indicated as red in integer grid
            gameOver = checkGameOver(); // check if tower too tall/in grey danger zone
            if(!gameOver)
            {
                newShape();
                repaint(); // fixing repaint
            }
            else
            {
                try {
                    endScreen.showLoseScreen(frame);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else
            position_Y++;
        return gameOver;
    }

    // tell if block is at bottom
    public boolean atBottom()
    {
        if(position_Y > ROWS - 2)
            return true;
        for(int i=0; i<COLUMNS; i++)
        {
            if(map[tallestPartOfTower][i] == 1)
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
            paintShape(0);
            position_x--;
        }
    }

    public boolean isTouchingLeftScreen()
    {
        return position_x<=1;
    }

    public void moveBlockRight()
    {
        if(!isTouchingRightScreen() && !isTouchingAnotherBlockLeftRight())
        {
            paintShape(0);
            position_x++;
        }
    }

    public boolean isTouchingRightScreen()
    {
        switch(fallingShape)
        {
            case BlockTypes.Z:
            case BlockTypes.L:
            case BlockTypes.O:
            case BlockTypes.S:
            case BlockTypes.J:
                if(position_x + 1 >= COLUMNS-2)
                    return true;
                break;
            case BlockTypes.I:
                if(position_x >= COLUMNS-2)
                    return true;
                break;
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
        return position_Y <= 2;
    }
}
