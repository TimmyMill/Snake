package com.timmy;

import java.util.TimerTask;

/** This class is needed to run the game clock so that animation can happen
 *  Every time the clock ticks, the game is updated and the run method in here is called
 *
 *  @author Clara
 *  @author Timmy
 */

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	Score score;
	DrawSnakeGamePanel gamePanel;
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick
						
		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {
				/* Move the snake
				* if the snake eats, move the kibble and increase the score */
				snake.moveSnake();
				if (snake.didEatKibble(kibble)) {
					//tell kibble to update
					kibble.moveKibble(snake);
					Score.increaseScore();
				}
				break;
			}
			case SnakeGame.GAME_OVER: {
				this.cancel();		//Stop the Timer	
				break;	
			}
			case SnakeGame.GAME_WON: {
				this.cancel();   //stop timer
				break;
			}

		}
				
		gamePanel.repaint();		//In every circumstance, must update screen
		
	}
}
