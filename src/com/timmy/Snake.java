package com.timmy;
import java.awt.Point;
import java.util.LinkedList;

public class Snake
{
	/* Directional Variables */
	final int DIRECTION_UP = 0;
	final int DIRECTION_DOWN = 1;
	final int DIRECTION_LEFT = 2;
	final int DIRECTION_RIGHT = 3;  //These are completely arbitrary numbers.

	private int currentHeading;  //Direction snake is going in, ot direction user is telling snake to go
	private int lastHeading;    //Last confirmed movement of snake. See moveSnake method

	private int snakeSquares[][];  //represents all of the squares on the screen
	//NOT pixels!
	//A 0 means there is no part of the snake in this square
	//A non-zero number means part of the snake is in the square
	//The head of the snake is 1, rest of segments are numbered in order

	private boolean ateTail = false;
	private boolean hitWall = false;

	//Maze
	private boolean mazeOn = false;
	public boolean isMazeOn() { return mazeOn; }
	public void setMazeOn(boolean mazeOn) { this.mazeOn = mazeOn; }

	//Warped Walls
	private boolean portalOn = false;
	public boolean isPortalOn() { return portalOn; }
	public void setPortalOn(boolean portalOn) { this.portalOn = portalOn; }

	private int snakeSize;   //size of snake - how many segments?

	private int growthIncrement = 2; //how many squares the snake grows after it eats a food

	private int justAteMustGrowThisMuch = 0;

	private int maxX, maxY, squareSize;  
	private int snakeHeadX, snakeHeadY; //store coordinates of head - first segment

	public int getMaxX() { return maxX; }
	public int getMaxY() { return maxY; }

	public int getSnakeHeadX() { return snakeHeadX; }
	public int getSnakeHeadY() { return snakeHeadY; }


	public Snake(int maxX, int maxY, int squareSize)
	{
		this.maxX = maxX;
		this.maxY = maxY;
		this.squareSize = squareSize;
		//Create and fill snakeSquares with 0s 
		snakeSquares = new int[maxX][maxY];
		fillSnakeSquaresWithZeros();
		createStartSnake();
	}


	protected void createStartSnake()
	{
		//snake starts as 3 horizontal squares in the center of the screen, moving left
		int screenXCenter = (int) maxX/2;  /* Cast just in case we have an odd number */
		int screenYCenter = (int) maxY/2;  /* Cast just in case we have an odd number */

		snakeSquares[screenXCenter][screenYCenter] = 1;
		snakeSquares[screenXCenter+1][screenYCenter] = 2;
		snakeSquares[screenXCenter+2][screenYCenter] = 3;

		snakeHeadX = screenXCenter;
		snakeHeadY = screenYCenter;

		snakeSize = 3;

		currentHeading = DIRECTION_LEFT;
		lastHeading = DIRECTION_LEFT;
		
		justAteMustGrowThisMuch = 0;
	}


	private void fillSnakeSquaresWithZeros()
	{
		for (int x = 0; x < this.maxX; x++)
		{
			for (int y = 0 ; y < this.maxY ; y++)
			{
				snakeSquares[x][y] = 0;
			}
		}
	}

	public LinkedList<Point> segmentsToDraw()
	{
		/* Return a list of the actual x and y coordinates of the top left of each snake segment
		 * Useful for the Panel class to draw the snake */
		LinkedList<Point> segmentCoordinates = new LinkedList<>();
		for (int segment = 1 ; segment <= snakeSize ; segment++ )
		{
			//search array for each segment number
			for (int x = 0 ; x < maxX ; x++)
			{
				for (int y = 0 ; y < maxY ; y++)
				{
					if (snakeSquares[x][y] == segment)
					{
						//make a Point for this segment's coordinates and add to list
						Point p = new Point(x * squareSize , y * squareSize);
						segmentCoordinates.add(p);
					}
				}
			}
		}
		return segmentCoordinates;
	}


	public void snakeUp()
	{
		if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) { return; }
		currentHeading = DIRECTION_UP;
	}

	public void snakeDown()
	{
		if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
		currentHeading = DIRECTION_DOWN;
	}

	public void snakeLeft()
	{
		if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
		currentHeading = DIRECTION_LEFT;
	}

	public void snakeRight()
	{
		if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
		currentHeading = DIRECTION_RIGHT;
	}

//	public void	eatKibble(){
//		//record how much snake needs to grow after eating food
//		justAteMustGrowThisMuch += growthIncrement;
//	}

	protected void moveSnake()
	{
		/* Called every clock tick */

		/* Must check that the direction snake is being sent in is not contrary to current heading
		 * So if current heading is down, and snake is being sent up, then should ignore.
		 * Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
		 */
		if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
			currentHeading = DIRECTION_UP; //keep going the same way
		}

		if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
			currentHeading = DIRECTION_DOWN; //keep going the same way
		}

		if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
			currentHeading = DIRECTION_RIGHT; //keep going the same way
		}

		if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
			currentHeading = DIRECTION_LEFT; //keep going the same way
		}
		
		//If the snake hits the wall or eats its tail, then gameStage is set to GAME_OVER
		if (hitWall || ateTail) {
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		/* Use snakeSquares array, and current heading, to move snake.
		 * Put a 1 in new snake head square.
		 * Increase all other snake segments by 1.
		 * Set tail to 0 if snake did not just eat, otherwise leave tail as is until snake has grown the correct amount.
		 * Find the head of the snake - snakeHeadX and snakeHeadY
		 * Increase all snake segments by 1.
		 * All non-zero elements of array represent a snake segment */

		for (int x = 0 ; x < maxX ; x++)
		{
			for (int y = 0 ; y < maxY ; y++)
			{
				if (snakeSquares[x][y] != 0)
				{
					snakeSquares[x][y]++;
				}
			}
		}

		//now identify where to add new snake head
		if (currentHeading == DIRECTION_UP)
		{
			//Subtract 1 from Y coordinate so head is one square up
			snakeHeadY-- ;
		}

		if (currentHeading == DIRECTION_DOWN)
		{
			//Add 1 to Y coordinate so head is 1 square down
			snakeHeadY++ ;
		}

		if (currentHeading == DIRECTION_LEFT)
		{
			//Subtract 1 from X coordinate so head is 1 square to the left
			snakeHeadX -- ;
		}

		if (currentHeading == DIRECTION_RIGHT)
		{
			//Add 1 to X coordinate so head is 1 square to the right
			snakeHeadX ++ ;
		}

		/* Does the snake hit the wall?
		*/

		if (snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0 )
		{
			//if the portal is off
			if (!isPortalOn())
			{
				hitWall = true;
				SnakeGame.setGameStage(SnakeGame.GAME_OVER);
				return;
			}
			//if the portal is on
			else if (isPortalOn())
			{
				//if the snake hits the right wall
				if (snakeHeadX >= maxX) snakeHeadX = 0;
				//if the snake hits the left wall
				if (snakeHeadX < 0) snakeHeadX = maxX - 1;
				//if the snake hits the top
				if (snakeHeadY >= maxY) snakeHeadY = 0;
				//if the snake hits the bottom
				if (snakeHeadY < 0 ) snakeHeadY = maxY - 1;
			}
		}

		/* Does the snake hit itself?
		*/

		if (snakeSquares[snakeHeadX][snakeHeadY] != 0)
		{

			ateTail = true;
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		/* Does the snake hit the maze wall?
		*/

		if (mazeOn) //if mazes are turned on
		{
			//used when the snake is on the right side of maze walls
			if (currentHeading == DIRECTION_LEFT)
			{
				//Upper Left Wall
				if ((snakeHeadX < 2 && snakeHeadX + 1 >= 2) && (snakeHeadY >= 2 && snakeHeadY < 4))
					hitWall = true;

				//Lower Left Wall
				if ((snakeHeadX < 2 && snakeHeadX + 1 >= 2) && (snakeHeadY >= 6 && snakeHeadY < 8))
					hitWall = true;

				//Upper Right Wall
				if ((snakeHeadX < 8 && snakeHeadX + 1 >= 8) && (snakeHeadY >= 2 && snakeHeadY < 4))
					hitWall = true;

				//Lower Right Wall
				if ((snakeHeadX < 8 && snakeHeadX + 1 >= 8) && (snakeHeadY >= 6 && snakeHeadY < 8))
					hitWall = true;
			}

			//used when the snake is on the left side of maze walls
			if (currentHeading == DIRECTION_RIGHT)
			{
				//Upper Left Wall
				if ((snakeHeadX == 2 && snakeHeadX - 1 < 2) && (snakeHeadY >= 2 && snakeHeadY < 4))
					hitWall = true;

				//Lower Left Wall
				if ((snakeHeadX == 2 && snakeHeadX - 1 < 2) && (snakeHeadY >= 6 && snakeHeadY < 8))
					hitWall = true;

				//Upper Right Wall
				if ((snakeHeadX == 8 && snakeHeadX - 1 < 8) && (snakeHeadY >= 2 && snakeHeadY < 4))
					hitWall = true;

				//Lower Right Wall
				if ((snakeHeadX == 8 && snakeHeadX - 1 < 8) && (snakeHeadY >= 6 && snakeHeadY < 8)) hitWall = true;
			}

			//used when the snake is below the maze walls
			if (currentHeading == DIRECTION_UP)
			{
				//Upper Left Wall
				if ((snakeHeadY < 2 && snakeHeadY + 1 >= 2) && (snakeHeadX >= 2 && snakeHeadX < 4))
					hitWall = true;

				//Lower Left Wall
				if ((snakeHeadY < 8 && snakeHeadY + 1 >= 8) && (snakeHeadX >= 2 && snakeHeadX < 4))
					hitWall = true;

				//Upper Right Wall
				if ((snakeHeadY < 2 && snakeHeadY + 1 >= 2) && (snakeHeadX >= 6 && snakeHeadX < 8))
					hitWall = true;

				//Lower Right Wall
				if ((snakeHeadY < 8 && snakeHeadY + 1 >= 8) && (snakeHeadX >= 6 && snakeHeadX < 8))
					hitWall = true;
			}

			//used when the snake is above the maze walls
			if (currentHeading == DIRECTION_DOWN)
			{
				//Upper Left Wall
				if ((snakeHeadY == 2 && snakeHeadY - 1 < 2) && (snakeHeadX >= 2 && snakeHeadX < 4))
					hitWall = true;

				//Lower Left Wall
				if ((snakeHeadY == 8 && snakeHeadY - 1 < 8) && (snakeHeadX >= 2 && snakeHeadX < 4))
					hitWall = true;

				//Upper Right Wall
				if ((snakeHeadY == 2 && snakeHeadY - 1 < 2) && (snakeHeadX >= 6 && snakeHeadX < 8))
					hitWall = true;

				//Lower Right Wall
				if ((snakeHeadY == 8 && snakeHeadY - 1 < 8) && (snakeHeadX >= 6 && snakeHeadX < 8))
					hitWall = true;
			}
		}


		//Otherwise, game is still on. Add new head
		snakeSquares[snakeHeadX][snakeHeadY] = 1;

		/* If snake did not just eat, then remove tail segment to keep snake the same length.
		 * Find highest number, which should now be the same as snakeSize+1, and set to 0 */
		if (justAteMustGrowThisMuch == 0)
		{
			for (int x = 0 ; x < maxX ; x++)
			{
				for (int y = 0 ; y < maxY ; y++)
				{
					if (snakeSquares[x][y] == snakeSize+1)
					{
						snakeSquares[x][y] = 0;
					}
				}
			}
		}
		else
		{
			//Snake has just eaten. leave tail as is.  Decrease justAte... variable by 1.
			justAteMustGrowThisMuch -- ;
			snakeSize ++;
		}
		lastHeading = currentHeading; //Update last confirmed heading
	}


	protected boolean didHitWall() { return hitWall; }
	protected boolean didEatTail() { return ateTail; }


	public boolean isSnakeSegment(int kibbleX, int kibbleY)
	{
		if (snakeSquares[kibbleX][kibbleY] == 0)
		{
			return false;
		}
		return true;
	}


	public boolean didEatKibble(Food food)
	{
		//Is this food in the snake? It should be in the same square as the snake's head
		if (food.getFoodX() == snakeHeadX && food.getFoodY() == snakeHeadY)
		{
			justAteMustGrowThisMuch += growthIncrement;
			return true;
		}
		return false;
	}


	public String toString() //FINDBUGS
	{
		StringBuilder buffer = new StringBuilder();
		//This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees.
		String textSnake = null;
		for (int y = 0; y < maxY; y++)
		{
			for (int x = 0; x < maxX; x++)
			{
				buffer.append(snakeSquares[x][y]);
			}
			textSnake = buffer.toString();
			textSnake += "\n";
		}
		return textSnake;
	}


	public boolean wonGame()
	{
		/* If all of the squares have snake segments in, the snake has eaten so much food
		 * that it has filled the screen. Win! */
		for (int x = 0 ; x < maxX ; x++)
		{
			for (int y = 0 ; y < maxY ; y++)
			{
				if (snakeSquares[x][y] == 0)
				{
					//there is still empty space on the screen, so haven't won
					return false;
				}
			}
		}
		//But if we get here, the snake has filled the screen. win!
		SnakeGame.setGameStage(SnakeGame.GAME_WON);
		
		return true;
	}


	public void reset()
	{
		hitWall = false;
		ateTail = false;
		portalOn = false;
		mazeOn = false;
		fillSnakeSquaresWithZeros();
		createStartSnake();
	}


	public boolean isGameOver()
	{
		if (hitWall || ateTail)
		{
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return true;
		}
		return false;
	}

}