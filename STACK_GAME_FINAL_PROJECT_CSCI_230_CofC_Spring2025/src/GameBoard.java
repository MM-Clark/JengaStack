// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston

import java.awt.*;

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
    private final int LEFT_CORNER_X = 30; // for where score is shown
    protected final static int COLUMNS = 24; // number of columns in grid, two are blacked out to 
                                            // serve as left/right barriers because of glitching
                                            // when doing any other way >> **22 FUNCTIONAL COLUMNS**
    protected final int ROWS = 20; // number of rows in grid
    public final static int CELL_SIZE = 32; // how big each grid cell is 
    
    // smallest block is size 3, board is 440 square blocks area (22x20 playable space)
    // 440/3 is 147 rounded up, but only 1/7 blocks is size 3, all others are size 4
    // so 440/4 = 110 and the average between 147 and 110 is 129 --> should account with padding for 
    // if player somehow succeeds and fills most of board
    private final int STACK_MAX_LOAD = 300; // amount of blocks in stack -- could make lower to force user to use what they get
    private final int BARRIER_ROw = 1; // top of tower barrier

    //-------------------------------------------------
    // PRIMITIVE TYPES 
    //--------------------------------------------------
    // for falling block, initialized to start position on grid
    private int position_x = (COLUMNS/2)-2; // x position of the block initially when falling
    private int position_Y = 3; // initial y position of block when falling
    private int tallestPartOfTower = 19; // for determining if block is at bottom row + needs to stop falling
    private boolean gameOver = false; // for determining when the game is over for displaying win lose screen
    // private int score; // user's current score
    private int thresholdScore = -1; // amount of tower blocks that must be covered to win
    private int fallSpeed = 1; // speed at which block falls

    //------------------------------------------------
    //  COLORS
    //------------------------------------------------
    // REDS FOR FALLING BLOCKS (can change)
    private final Color COLOR_OCCUPIED = new Color(254, 32, 32); // RED - for falling blocks
    private final Color COLOR_BORDER_TEMP = new Color(144,21,0); // DARK RED - border for falling blocks

    // BEIGES FOR TOWER BLOCKS AT BOTTOM (makes it creepier...? -- but probably needs to be more subtle)
    // (right now this beige has too much red/green probably because it looks alive instead of dead)
    private final Color COLOR_PERMANENTLY_OCCUPIED = new Color(168, 147, 126); // DARK BEIGE - for blocks that have fallen to the bottom
    private final Color COLOR_BORDER_PERMANENT = new Color(148, 127, 106); //DARKER BEIGE - for permanently occupying block borders
    
    // BOARD COLORS -- not for falling/fallen blocks
    // off white -- can change (note pure white is harsh on eyes)
    private final Color COLOR_EMPTY = new Color(255, 252, 232); // OFF WHITE - for empty grid
    //*********** CANNOT change hidden row color b/c won't match board (unless change board color too) ***********
    // off-black to lower eye strain
    private final Color COLOR_HIDDEN = new Color(16, 16, 0); // hidden rows for left/right barriers to avoid exceptions
    // almond-ish -- can change color
    private final Color COLOR_DANGER_ZONE = new Color(228, 207, 186); // for cutoff of when your tower is too high + you lose, we don't have to use this idea
    // yellow-ish -- definitely can change this color
    private final Color COLOR_TEXT = new Color(255, 239, 0); // CANARY YELLOW - text/directions
    // grey??? -- goal tower color
    private final Color COLOR_GOAL_TOWER = new Color(211, 211, 211); // LIGHT GREY 

    //---------------------------------------------------------------------------------------
    //  BOARD SETUP          0=empty spot       1=falling block in this spot        2=tower here
    //----------------------------------------------------------------------------------------
    int map[][] = new int[ROWS][COLUMNS];               // grid for blocks to move and stack on
    int originalMap[][] = new int[ROWS][COLUMNS];

    //----------------------------------------------
    // FUNCTIONAL GAME OBJECTS AND ARRAYS
    //----------------------------------------------
    protected BlockTypes fallingShape = BlockTypes.L;   // representation of falling block, determines shape type
                                                        // and how will be drawn on screen
    private StackArray<Integer> stackTower;             //for holding integers corresponding to block type
    private Key_binding controlKeys; // for resetting fall speed/frame rate in key binding class 
    
    //-----------------------------------------------------------------------------------------
    // constructor for making new game
    //------------------------------------------------------------------------------------------
    public void newGame(Key_binding inKeyBinding) 
    {
        controlKeys = inKeyBinding; // setting up class instance so that we can reset fall speed 
        // Clear the map, 0=empty, 1=falling block part, 2=tower part
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLUMNS; col++) 
            {
                map[row][col] = 0;  // empty
                originalMap[row][col] = 0;
            }
        }
        //declare stack size
        stackTower = new StackArray<Integer>(STACK_MAX_LOAD);
        // push in integers to stack, will use switch statement in newShape() method to determine 
        // shape type from these integers

        for(int i=0; i < STACK_MAX_LOAD; i++)
        {
            int randomShapeNum = Randomizer.getRandomNumber(0, 7);
            stackTower.push(randomShapeNum);     
        }   
        // score = 0; // reset score

        createGoalTower(); // ************************** make goal tower ************************************
    }

    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
    //                  Function to make a random tower for user to replicate -- switch cases 
    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
    public void createGoalTower()
    {
        int randomBaseSize = Randomizer.getRandomNumber(COLUMNS/3, COLUMNS-1);
        int randomDecreaseColumnsSize = -1;

        int rowNum = ROWS - 1; 

        while(randomBaseSize > 0 && rowNum > BARRIER_ROw)
        {
            for(int i=randomBaseSize; i > 0; i--)
            {
                map[rowNum][i] = 4; 
                originalMap[rowNum][i] = 4; 
            }
            randomDecreaseColumnsSize = Randomizer.getRandomNumber(0, 3);
            randomBaseSize -= randomDecreaseColumnsSize;
            rowNum--;
        }

        thresholdScore = 0; 
    }
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------

    public void makeFallingBlockAtTopOfScreen()
    {
        // reset falling positions so does not fall starting at bottom of screen
        position_x = (COLUMNS/2)-2; 
        position_Y = 2; 
    }
    // ----------------------------------------------------------------------------------
    //     GET NEW SHAPES BY RESETTING X/Y POSITIONS, GETTING NEW SHAPE BY POPPING STACK
    // ----------------------------------------------------------------------------------
    public void newShape()
    {
        int shapeType = -1; // purposely invalid shape type

        // stack should not be empty at any point during game b/c is size 40 
        // and shapes are greater than size 1, also grid is only 22x20 playeable area

        // integer is modded by 10 to reduce switch statement cases but 
        // allow for easy computing for programmer to check functionality 
        if(!stackTower.isEmpty())
            shapeType = stackTower.pop();
        else
        {
            gameOver = true; // user switched too many blocks 
            return; // **************** this could be an error message
        }

        getTypeOfShape(shapeType);
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

    // METHOD FOR PAINTING NEW FALLING SHAPE 
    public void newShapeUpdateGrid()
    {
        // ALL SHAPES ARE LEFT-ALIGNED TO GRID
        paintShape(1); // paint block as a falling block on grid
    }

    //------------------------------------------------------------------------------------
    // PAINT METHOD
    //-------------------------------------------------------------------------------------
    public void paint(Graphics g)
    {
        newShapeUpdateGrid(); // paint falling shape
        //-----------------------------------------------------------------------------------------------------------------------------------
        // SET UP BACKGROUND
        //----------------------------------------------------------------------------------------------------------------------------------------------
        super.paintComponent(g); // paint existing frame components
        this.setBackground(COLOR_HIDDEN); // set background    
    
        //------------------------------------------------------------------------------------------------------------------------------
        // SCORE + INSTRUCTIONS 
        // **** could make text different size + update x/y positions to avoid overlapping text **********
        //-------------------------------------------------------------------------------------------------------------------------------
        g.setColor(COLOR_TEXT); 
        g.drawString("Welcome to Jenga Stack!", (WINDOW_WIDTH/2) - 70, (WINDOW_HEIGHT/30)); //weird divided numbers are (x, y) positions
        g.drawString("Move mouse to move block. Try to completely cover the grey tower.", (WINDOW_WIDTH/2) - 170, ((WINDOW_HEIGHT/30) + 20));
        // g.drawString("SCORE: " + , LEFT_CORNER_X, (WINDOW_HEIGHT/30));
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
                //-------------- FILL CELL COLORS -------------------------------------------
                if(col == 0 || col == COLUMNS-1) // hidden columns on left/right side are this case
                    g.setColor(COLOR_HIDDEN); // black
                else if(map[row][col] == 2) // tower 
                    g.setColor(COLOR_PERMANENTLY_OCCUPIED); // red tower
                else if(map[row][col] == 1) // falling block
                    g.setColor(COLOR_OCCUPIED); // blue falling blocks
                else if(map[row][col] == 4)
                    g.setColor(COLOR_GOAL_TOWER);
                else if(row <= BARRIER_ROw) // top 2 rows are grey to represent a zone of tower being too tall, could also black them out
                    g.setColor(COLOR_DANGER_ZONE); 
                else    
                    g.setColor(COLOR_EMPTY); // white
                g.fill3DRect(col*CELL_SIZE + xOffset, row*CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE, true); // fill cell
                
                // ----------- BORDER OF CELL COLORS --------------------------------------------------
                if(map[row][col] == 2 || map[row][col] == 1)
                {
                    if(map[row][col]==2)
                        g.setColor(COLOR_BORDER_PERMANENT);
                    else
                        g.setColor(COLOR_BORDER_TEMP);
                    g.draw3DRect(col*CELL_SIZE + xOffset, row*CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE, true); // fill cell
                }
            }
        }
    }

    //---------------------------------------------------------------------------------
    // updates grid/fills in shape coordinate locations in integer grid with new number 
    // (updates whether shape is falling, empty area, or part of tower)
    //---------------------------------------------------------------------------------
    public void paintShape(int color)
    {
        //to paint shape a certain color (0, 1, or 2) on grid
        switch(fallingShape)
        {
            case BlockTypes.Z:
                if(originalMap[position_Y-2][position_x+1] == 0 || color!=0)
                    map[position_Y-2][position_x+1] = color;
                else
                    map[position_Y-2][position_x+1] = 4;
                if(originalMap[position_Y-1][position_x+1] == 0 || color!=0)
                    map[position_Y-1][position_x+1] = color;
                else
                    map[position_Y-1][position_x+1] = 4;
                if(originalMap[position_Y-1][position_x] == 0 || color!=0)
                    map[position_Y-1][position_x] = color;
                else
                    map[position_Y-1][position_x] = 4;
                if(originalMap[position_Y][position_x] == 0 || color!=0)
                    map[position_Y][position_x] = color;
                else
                    map[position_Y][position_x] = 4;
                break;
            case BlockTypes.L:
                if(originalMap[position_Y-2][position_x] == 0 || color!=0)
                    map[position_Y-2][position_x] = color;
                else
                    map[position_Y-2][position_x] = 4;
                if(originalMap[position_Y-1][position_x] == 0 || color!=0)
                    map[position_Y-1][position_x] = color;
                else
                    map[position_Y-1][position_x] = 4;
                if(originalMap[position_Y][position_x] == 0 || color!=0)
                    map[position_Y][position_x] = color;
                else
                    map[position_Y][position_x] = 4;
                if(originalMap[position_Y][position_x+1] == 0 || color!=0)
                    map[position_Y][position_x+1] = color;
                else
                    map[position_Y][position_x+1] = 4;
                break;
            case BlockTypes.O:
                if(originalMap[position_Y-1][position_x+1] == 0 || color!=0)
                    map[position_Y-1][position_x+1] = color;
                else
                    map[position_Y-1][position_x+1] = 4;
                if(originalMap[position_Y-1][position_x] == 0 || color!=0)
                    map[position_Y-1][position_x] = color;
                else
                    map[position_Y-1][position_x] = 4;
                if(originalMap[position_Y][position_x+1] == 0 || color!=0)
                    map[position_Y][position_x+1] = color; 
                else
                    map[position_Y][position_x+1] = 4;
                if(originalMap[position_Y][position_x] == 0 || color!=0)
                    map[position_Y][position_x] = color;
                else
                    map[position_Y][position_x] = 4; 
                break;
            case BlockTypes.S:
                if(originalMap[position_Y-2][position_x] == 0 || color!=0)
                    map[position_Y-2][position_x] = color;
                else
                    map[position_Y-2][position_x] = 4;
                if(originalMap[position_Y-1][position_x] == 0 || color!=0) 
                    map[position_Y-1][position_x] = color;
                else
                    map[position_Y-1][position_x] = 4;
                if(originalMap[position_Y-1][position_x+1] == 0 || color!=0)
                    map[position_Y-1][position_x+1] = color;
                else
                    map[position_Y-1][position_x+1] = 4;
                if(originalMap[position_Y][position_x+1] == 0 || color!=0)
                    map[position_Y][position_x+1] = color;
                else
                    map[position_Y][position_x+1] = 4;
                break;
            case BlockTypes.I:
                if(originalMap[position_Y-2][position_x] == 0 || color!=0)
                    map[position_Y-2][position_x] = color;
                else
                    map[position_Y-2][position_x] = 4;
                if(originalMap[position_Y-1][position_x] == 0 || color!=0)
                    map[position_Y-1][position_x] = color;
                else
                    map[position_Y-1][position_x] = 4;
                if(originalMap[position_Y][position_x] == 0 || color!=0)
                    map[position_Y][position_x] = color;
                else
                    map[position_Y][position_x] = 4;
                break;
            case BlockTypes.J:
                if(originalMap[position_Y-2][position_x+1] == 0 || color!=0)
                    map[position_Y-2][position_x+1] = color;
                else
                    map[position_Y-2][position_x+1] = 4;
                if(originalMap[position_Y-1][position_x+1] == 0 || color!=0)
                    map[position_Y-1][position_x+1] = color;
                else
                    map[position_Y-1][position_x+1] = 4;
                if(originalMap[position_Y][position_x+1] == 0 || color!=0)
                    map[position_Y][position_x+1] = color;
                else
                    map[position_Y][position_x+1] = 4;
                if(originalMap[position_Y][position_x] == 0 || color!=0)
                    map[position_Y][position_x] = color;
                else
                    map[position_Y][position_x] = 4;
                break;
            case BlockTypes.T:
                if(originalMap[position_Y-1][position_x+1] == 0 || color!=0)
                    map[position_Y-1][position_x+1] = color;
                else
                    map[position_Y-1][position_x+1] = 4;
                if(originalMap[position_Y][position_x] == 0 || color!=0)
                    map[position_Y][position_x] = color;
                else
                    map[position_Y][position_x] = 4;
                if(originalMap[position_Y][position_x+1] == 0 || color!=0)
                    map[position_Y][position_x+1] = color;
                else
                    map[position_Y][position_x+1] = 4;
                if(originalMap[position_Y][position_x+2] == 0 || color!=0)
                    map[position_Y][position_x+2] = color; 
                else
                    map[position_Y][position_x+2] = 4;
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
            SoundPlayer.landSFX(); // sound when block has landed
            gameOver = checkTowerTooTall(); // check if tower too tall/in grey danger zone

            if(!gameOver) // game is not over, keep getting new blocks and doing Tetris
            {
                controlKeys.resetFall(); // reset fall speed after block lands
                makeFallingBlockAtTopOfScreen(); // reset x/y position of falling block 
                newShape();            // get another shape
                repaint();             // repaint the screen to show updates
            }
        }
        else
            position_Y += fallSpeed; // make the block move one grid space down
            SoundPlayer.fallSFX();  // play fall SFX // NEVERMIND, THIS SOUND IS REALLY ANNOYING. Might get a better one later
        return gameOver;  // return whether game is over to key_binding class to stop timer
    }

    public boolean checkScoreForWin()
    {
        // if(score >= 20000) // 20,000 is 100 blocks on grid
        //     return true;
        int tempScore = 0;
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLUMNS; col++) 
                if(map[row][col] == 4)
                    tempScore++;
        }

        return tempScore <= thresholdScore;
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
                // on same block
                if((map[position_Y-2][position_x+1] == 2) || (map[position_Y-1][position_x+1] == 2) ||
                    (map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.L:
                // there is a block below 
                if((map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2) ||
                    (map[position_Y+1][position_x] == 2) || (map[position_Y+1][position_x+1] == 2))
                    return true;
                // on another block
                if((map[position_Y-2][position_x] == 2) || (map[position_Y-1][position_x] == 2) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.O:
                // block below
                if((map[position_Y][position_x+1] == 2) || (map[position_Y][position_x] ==2 ) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                // on block
                if((map[position_Y-1][position_x+1] == 2) || (map[position_Y-1][position_x] ==2 ) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.S:
                // block below
                if((map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y+1][position_x+1] == 2))
                    return true;
                // on block
                if((map[position_Y-2][position_x] == 2) || (map[position_Y-1][position_x] == 2) ||
                    (map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.I:
                // block below
                if((map[position_Y-1][position_x] == 2) || 
                    (map[position_Y][position_x] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                // on block
                if((map[position_Y-2][position_x] == 2) || 
                    (map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.J:
                // block below
                if((map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x] == 2))
                    return true;
                // on block
                if((map[position_Y-2][position_x+1] == 2) || (map[position_Y-1][position_x+1] == 2) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.T:
                // block below
                if((map[position_Y][position_x+1] == 2) ||(map[position_Y+1][position_x] == 2) ||
                    (map[position_Y+1][position_x+1] == 2) || (map[position_Y+1][position_x+2] == 2))
                    return true; 
                // on block
                if((map[position_Y-1][position_x+1] == 2) ||(map[position_Y][position_x] == 2) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y][position_x+2] == 2))
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
        if(!isTouchingLeftScreen() && !isTouchingAnotherBlockLeft())
        {
            paintShape(0); // clear old shape
            position_x--; // move shape one grid space left ****************************************************************************
            SoundPlayer.moveSFX(); // play move sound --> ***may want to check if creates a lag w/barriers b/c thread might stop to play
        }                                                   // *************************************************************************
        else
        {
            SoundPlayer.cancelSFX(); // sound when block cannot move
        }
    }

    public boolean isTouchingLeftScreen()
    {
        return position_x<=1; // if block is partially in the farthest left operable column, don't move it
    }

    public void moveBlockRight()
    {
        if(!isTouchingRightScreen() && !isTouchingAnotherBlockRight())
        {
            paintShape(0); // clear old shape
            position_x++; // move shape one grid space right
            SoundPlayer.moveSFX(); // play move sound
        }
        else
        {
            SoundPlayer.cancelSFX(); // sound when block cannot move
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

    public boolean isTouchingAnotherBlockLeft()
    {
        //to clear shape from grid first, added one to all y values to avoid overlap
        switch(fallingShape)
        {
            case BlockTypes.Z:
                //there is a block to the left (original x position of each point - 1)
                if((map[position_Y-2][position_x] == 2) || (map[position_Y-1][position_x] == 2) ||
                    (map[position_Y-1][position_x-1] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.L:
                // there is a block to the left
                if((map[position_Y-2][position_x-1] == 2) || (map[position_Y-1][position_x-1] == 2) ||
                    (map[position_Y][position_x-1] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.O:
                // block to left
                if((map[position_Y-1][position_x] == 2) || (map[position_Y-1][position_x-1] ==2 ) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.S:
                // block to left
                if((map[position_Y-2][position_x-1] == 2) || (map[position_Y-1][position_x-1] == 2) ||
                    (map[position_Y-1][position_x] == 2) || (map[position_Y][position_x] == 2))
                    return true;
                break;
            case BlockTypes.I:
                // block to left
                if((map[position_Y-2][position_x-1] == 2) || 
                    (map[position_Y-1][position_x-1] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.J:
                // block to left
                if((map[position_Y-2][position_x] == 2) || (map[position_Y-1][position_x] == 2) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y][position_x-1] == 2))
                    return true;
                break;
            case BlockTypes.T:
                if((map[position_Y-1][position_x] == 2) ||(map[position_Y][position_x-1] == 2) ||
                    (map[position_Y][position_x] == 2) || (map[position_Y][position_x+1] == 2))
                    return true; 
                break;
            default:
                return false;
        }
        return false;
    }

    public boolean isTouchingAnotherBlockRight()
    {
        //to clear shape from grid first, added one to all y values to avoid overlap
        switch(fallingShape)
        {
            case BlockTypes.Z:
                // block to right (original x position of each point + 1)
                if((map[position_Y-2][position_x+2] == 2) || (map[position_Y-1][position_x+2] == 2) ||
                    (map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.L:
                // there is a block to the right
                if((map[position_Y-2][position_x+1] == 2) || (map[position_Y-1][position_x+1] == 2) ||
                    (map[position_Y][position_x+1] == 2) || (map[position_Y][position_x+2] == 2))
                    return true;
                break;
            case BlockTypes.O:
                // block to right
                if((map[position_Y-1][position_x+2] == 2) || (map[position_Y-1][position_x+1] ==2 ) ||
                    (map[position_Y][position_x+2] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.S:
                // block to right
                if((map[position_Y-2][position_x+1] == 2) || (map[position_Y-1][position_x+1] == 2) ||
                    (map[position_Y-1][position_x+2] == 2) || (map[position_Y][position_x+2] == 2))
                    return true;
                break;
            case BlockTypes.I:
                // block to right
                if((map[position_Y-2][position_x+1] == 2) || 
                    (map[position_Y-1][position_x+1] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.J:
                // block to right
                if((map[position_Y-2][position_x+2] == 2) || (map[position_Y-1][position_x+2] == 2) ||
                    (map[position_Y][position_x+2] == 2) || (map[position_Y][position_x+1] == 2))
                    return true;
                break;
            case BlockTypes.T:
                if((map[position_Y-1][position_x+2] == 2) ||(map[position_Y][position_x+1] == 2) ||
                    (map[position_Y][position_x+2] == 2) || (map[position_Y][position_x+3] == 2))
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
    public boolean checkTowerTooTall()
    {
        // T shape is only 2 blocks tall (y, y-1 are filled)
        if(fallingShape == BlockTypes.T)
            return position_Y - 1 <= BARRIER_ROw; // top two blocks
        // all other shapes are 3 blocks tall (y, y-1, y-2 are filled)
        return position_Y - 2 <= 1; // max top coordinate-y has entered danger zone -- top two blocks
    }
}