package com.timmy;

import java.util.TimerTask;

public class GameClock extends TimerTask
{
	Snake snake;
	Food food;
	Score score;
	DrawSnakeGamePanel gamePanel;
		
	public GameClock(Snake snake, Food food, Score score, DrawSnakeGamePanel gamePanel)
	{
		this.snake = snake;
		this.food = food;
		this.score = score;
		this.gamePanel = gamePanel;
	}


	@Override
	public void run()
	{
		// This method will be called every clock tick
		int stage = SnakeGame.getGameStage();

		switch (stage)
		{
			case SnakeGame.BEFORE_GAME:
			{
				//don't do anything, waiting for user to press a key to start
				break;
			}

			case SnakeGame.DURING_GAME:
			{
				snake.moveSnake();
				if (snake.didEatKibble(food)) {
					//tell food to update
					food.moveKibble(snake);
					Score.increaseScore();
				}
				break;
			}

			case SnakeGame.GAME_OVER:
			{
				this.cancel();
				break;
			}

			case SnakeGame.GAME_WON:
			{
				this.cancel();
				break;
			}

			default:
			{
				System.out.println("Not in a game stage");
				break;
			}
		}

		gamePanel.repaint();		//In every circumstance, must update screen
	}
}
