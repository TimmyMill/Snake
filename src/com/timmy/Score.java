package com.timmy;

/********************************************************
 * Class keeps track of, and displays the user's score  *
 *                                                      *
 * @author Clara                                        *
 * @author Timmy                                        *
 *******************************************************/

public class Score
{
	protected static int score;
	protected static int highScore = 0;
	protected static int increment;
	
	Score()
	{
		score = 0;
		increment = 1;  //how many points for eating a food
		//Possible TODO get more points for eating kibbles, the longer the snake gets?
	}


	public static void resetScore() { score = 0; }

	public static void increaseScore() { score = score + increment; }

	public int getScore() { return score; }
	
	/* Checks if current score is greater than the current high score.
	 * updates high score and returns true if so. */

    public boolean gameOver()
	{
		if (score > highScore)
		{
			highScore = score;
			return true;
		}
		return false;
	}

	/* These methods are useful for displaying score at the end of the game */
	
	public String getStringScore()
	{
		return Integer.toString(score);
	}


	public String newHighScore()
	{
		if (score > highScore)
		{
			highScore = score;
			return "New High Score!!";
		} else {
			return "";
		}
	}

	public String getStringHighScore() { return Integer.toString(highScore); }
	
}

