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
    private int position_Y = 2;
    private int tallestPartOfTower = 19;

    private static final Color COLOR_OCCUPIED = Color.LIGHT_GRAY;
    private static final Color COLOR_EMPTY = Color.WHITE;
    private static final Color COLOR_PERMANENTLY_OCCUPIED = Color.RED;

    int map[][] = new int[ROWS][COLUMNS];
    // private TetrisBlock fallingBlock = new TetrisBlock();
    protected BlockTypes fallingShape = BlockTypes.L;
    private StackArray<Integer> stackTower = new StackArray<Integer>(40); //for holding integers corresponding to block type

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
        
        for(int i=0; i < 40; i++)
        {
            stackTower.push(i);     // this part needs some work for randomization
        }
    }

    public void newShape()
    {
        // Get shapes to stack >> NEEDS EDITING SEVERELY
        position_x = 0;
        position_Y = 2;
        
        int shapeType = stackTower.pop() % 10;
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
    // DROP BLOCK
    //---------------------------------------------------------------------------------------
    public void updateBlockPos()
    {
        //clear shape from grid first
        paintShape(0);

        //move the block one grid down
        if(atBottom() || isTouchingAnotherBlock())
        {
            paintShape(2);
            newShape();
            repaint(); // fixing repaint
        }
        else
            position_Y++;
    }

    //********************************************************************* */
    // working this out, have not gotten to changing block color at bottom/setting bottom
    //********************************************************************* */
    public boolean atBottom()
    {
        if(position_Y > 18)
            return true;
        for(int i=0; i<COLUMNS; i++)
        {
            if(map[tallestPartOfTower][i] == 1)
                return true;
        }
        return false;
    }

    public boolean isTouchingAnotherBlock()
    {
        //to clear shape from grid first, added one to all y values to avoid overlap
        switch(fallingShape)
        {
            // this case works
            case BlockTypes.Z:
                if((map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            // this case works
            case BlockTypes.L:
                if((map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2) ||
                    (map[position_Y+1][position_x] == 2) || (map[position_Y+1][position_x+1] == 2))
                    return true;
                break;
            //this works
            case BlockTypes.O:
                if((map[position_Y][position_x+1] == 2) || (map[position_Y][position_x] ==2 ) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            // works
            case BlockTypes.S:
                if((map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y+1][position_x+1] == 2))
                    return true;
                break;
            // works
            case BlockTypes.I:
                if((map[position_Y-1][position_x] == 2) || 
                    (map[position_Y][position_x] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            // works
            case BlockTypes.J:
                if((map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                break;
            // works
            case BlockTypes.T:
                if((map[position_Y][position_x+1] == 2) ||(map[position_Y+1][position_x] == 2) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x+2] == 2))
                    return true; 
                break;
            default:
                return false;
        }
        return false;
    }

    public void moveBlockLeft()
    {
        if(!isTouchingLeftScreen() && !isTouchingAnotherBlock())
        {
            paintShape(0);
            position_x--;
        }
    }

    public boolean isTouchingLeftScreen()
    {
        return position_x==0;
    }

    public void moveBlockRight()
    {
        if(!isTouchingRightScreen() && !isTouchingAnotherBlock())
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
                if(position_x + 1 >= 21)
                    return true;
                break;
            case BlockTypes.I:
                if(position_x >= 21)
                    return true;
                break;
            case BlockTypes.T:
                if(position_x + 2 >= 21)
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }
}
