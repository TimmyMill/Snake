package com.timmy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

public class GameControls implements KeyListener{
	
	Snake snake;

	private static boolean paused = false;
	public static boolean isPaused() {return paused;}
	public static void setPaused(boolean paused) {GameControls.paused = paused;}

	GameControls(Snake s) {
		this.snake = s;
	}
	
	public void keyPressed(KeyEvent ev) {
		/*
		 * keyPressed events are for catching events like function keys, enter, arrow keys
		 * We want to listen for arrow keys to move snake
		 * Has to id if user pressed arrow key, and if so, send info to Snake object

		 * Is game running? No? tell the game to draw grid, start, etc.

		 * Get the component which generated this event
		 * Hopefully, a DrawSnakeGamePanel object.
		 * It would be a good idea to catch a ClassCastException here.
		 */

		try
		{ DrawSnakeGamePanel panel = (DrawSnakeGamePanel)ev.getComponent();

			/* If the game hasn't started yet, start the game by setting GameStage to DURING_GAME */
			if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME) {
				//Start the game
				SnakeGame.setGameStage(SnakeGame.DURING_GAME);
				SnakeGame.newGame(); //Start game by calling newGame method
				/* NewGame creates:
				 * the timer
				 * the game clock*/

				panel.repaint();
				return;
			}

			if (SnakeGame.getGameStage() == SnakeGame.GAME_OVER) {
				snake.reset();
				Score.resetScore();
				//Cancel the timer and terminate any remaining tasks
				SnakeGame.resetTimer();

				//Need to start the timer and start the game again
				SnakeGame.newGame();
				SnakeGame.setGameStage(SnakeGame.DURING_GAME);
				panel.repaint();
				return;
			}

			/* Snake Movement Controls
			*/
			if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
				//System.out.println("snake down");
				snake.snakeDown();
				panel.repaint();
				return;
			}
			if (ev.getKeyCode() == KeyEvent.VK_UP) {
				//System.out.println("snake up");
				snake.snakeUp();
				panel.repaint();
				return;
			}
			if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
				//System.out.println("snake left");
				snake.snakeLeft();
				panel.repaint();
				return;
			}
			if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
				//System.out.println("snake right");
				snake.snakeRight();
				panel.repaint();
				return;
			}

			//Pause
			if (ev.getKeyCode() == KeyEvent.VK_SPACE) {
				if (isPaused()) {
					System.out.println("resume");
					SnakeGame.newGame();
					setPaused(false);
				}
				else if (!isPaused()) {
					System.out.println("paused");
					SnakeGame.timer.cancel();
					SnakeGame.timer.purge();
					setPaused(true);
				}
			}

			/* Toggle Maze on/off
			*/
			if (ev.getKeyCode() == KeyEvent.VK_M) {
				if (snake.isMazeOn()) {
					System.out.println("maze off");
					snake.setMazeOn(false);
				}
				else if (!snake.isMazeOn()) {
					System.out.println("maze on");
					snake.setMazeOn(true);
				}
			}

			/* Toggle Portal on/off
			*/
			if (ev.getKeyCode() == KeyEvent.VK_W) {
				if (snake.isPortalOn()) {
					System.out.println("warped walls off");
					snake.setPortalOn(false);
				}
				else if (!snake.isPortalOn()) {
					System.out.println("warped walls on");
					snake.setPortalOn(true);
				}
			}

		}

		catch (ClassCastException cce) {
			cce.printStackTrace();
		}

	}

	@Override
	public void keyReleased(KeyEvent ev) {
		//We don't care about keyReleased events, but are required to implement this method anyway.		
	}

	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		if (keyPressed == q) {
			System.exit(0);    //quit if user presses the q key.
		}
	}

}
