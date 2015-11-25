package com.timmy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 */

public class GameControls implements KeyListener{
	
	Snake snake;
	
	GameControls(Snake s){
		this.snake = s;
	}

    /* KeyPressed events are for catching events like function keys, enter, arrow keys
     * We want to listen for arrow keys to move snake
     * Has to ID if user pressed arrow key, and if so, send info to Snake object
     * Is game running? No? tell the game to draw grid, start, etc.
     */

	public void keyPressed(KeyEvent ev) {

        /* Get the component which generated this event, hopefully, a DrawSnakeGamePanel object.
        * It would be a good idea to catch a ClassCastException here. */

		try {
            DrawSnakeGamePanel panel = (DrawSnakeGamePanel) ev.getComponent();

		/* If the game hasn't started yet, start the game by setting GameStage to DURING_GAME */
            if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME) {
                //Start the game
                SnakeGame.setGameStage(SnakeGame.DURING_GAME);
                SnakeGame.newGame(); //Start game by calling newGame method
			/* NewGame creates:
			* the timer
			* the game clock
			*/

                panel.repaint();
                return;
            }

            if (SnakeGame.getGameStage() == SnakeGame.GAME_OVER) {
                snake.reset();
                Score.resetScore();

                //Need to start the timer and start the game again
                SnakeGame.newGame();
                SnakeGame.setGameStage(SnakeGame.DURING_GAME);
                panel.repaint();
                return;
            }

		/* This is used to control the snake's movement
		*  Each time a key is pressed, the corresponding method is called
		*  to control snake movement*/

            if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
                //System.out.println("snake down");
                snake.snakeDown();
            }
            if (ev.getKeyCode() == KeyEvent.VK_UP) {
                //System.out.println("snake up");
                snake.snakeUp();
            }
            if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
                //System.out.println("snake left");
                snake.snakeLeft();
            }
            if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
                //System.out.println("snake right");
                snake.snakeRight();
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
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
		if( keyPressed == q){
			System.exit(0);    //quit if user presses the q key.
		}
	}

}
