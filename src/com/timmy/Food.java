package com.timmy;
import java.util.Random;

/***************************************************************************
 * Class is responsible for generating food for the snake to "eat"         *
 * Identifies a random square to display a food                            *
 * Any square is ok, so long as it doesn't have any snake segments in it.  *
 * There is only one Food and when the snake eats it, then it moves.       *
 *                                                                         *
 * @author Clara                                                           *
 * @author Timmy                                                           *
 **************************************************************************/

public class Food
{
	//x and y coordinates for the food
	//this number is the square size, not pixel size
	private int foodX;
	private int foodY;

	/* Food needs to know where the snake is, so it does not create a food in the snake
	 * Pick a random location for food, check if it is in the snake, if in snake, try again */

	public Food(Snake s)
	{
		moveFood(s);
	}


	protected void moveFood(Snake s)
	{
		Random rng = new Random();
		boolean foodInSnake = true;
		while (foodInSnake)
		{
			//Generate random food location
			foodX = rng.nextInt(SnakeGame.xSquares);
			foodY = rng.nextInt(SnakeGame.ySquares);
			foodInSnake = s.isSnakeSegment(foodX, foodY);
		}
	}


	/* Getters */
	public int getFoodX() { return foodX; }
	public int getFoodY() { return foodY; }

}