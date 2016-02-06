package com.timmy;

import java.util.Random;

public class Food {

	/** Identifies a random square to display a food
	 * Any square is ok, so long as it doesn't have any snake segments in it.
	 * There is only one Food and when the snake eats it, then it moves.
	 */
	
	private int kibbleX; //This is the square number (not pixel)
	private int kibbleY;  //This is the square number (not pixel)
	
	public Food(Snake s){
		/* Food needs to know where the snake is, so it does not create a food in the snake
		 * Pick a random location for food, check if it is in the snake
		 * If in snake, try again */

		moveKibble(s);
	}
	
	protected void moveKibble(Snake s){
		
		Random rng = new Random();
		boolean kibbleInSnake = true;
		while (kibbleInSnake) {
			//Generate random food location
			kibbleX = rng.nextInt(SnakeGame.xSquares);
			kibbleY = rng.nextInt(SnakeGame.ySquares);
			kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY);
		}

	}

	/* Getters */
	public int getKibbleX() {return kibbleX;}
	public int getKibbleY() {return kibbleY;}

}