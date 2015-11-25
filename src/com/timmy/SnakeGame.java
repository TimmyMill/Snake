package com.timmy;

import javax.swing.*;
import java.util.Timer;

/**
 *
 */

public class SnakeGame {

	/* Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square */
	public final static int xPixelMaxDimension = 501;
	public final static int yPixelMaxDimension = 501;

	public static int xSquares ;
	public static int ySquares ;

	public final static int squareSize = 50;

	protected static Snake snake ;
	protected static Kibble kibble;
	protected static Score score;

	/* Different game states used to show what's going on with the game */

	static final int BEFORE_GAME = 1; /* Used to set up and initialize the game */
	static final int DURING_GAME = 2; /* Used during gameplay */
	static final int GAME_OVER = 3;   /* Used when the game has ended */
	static final int GAME_WON = 4;    /* Used to indicate that the user has won */

	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change its value

	protected static long clockInterval = 500; //controls game speed
	/* Every time the clock ticks, the snake moves
	 * This is the time between clock ticks, in milliseconds
	 * 1000 milliseconds = 1 second */

    static Timer timer;

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	/* Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too
	 * http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	 * http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html */

    /* Create and set up the window.
    */
	private static void createAndShowGUI() {
        /* Create a new JFrame */

		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
        snakeFrame.setLocationRelativeTo(null); //sets the window in the center of the screen
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

        /* Create a new JPanel from the GamePanel class */

		snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
        snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents
        snakePanel.setFocusable(true);

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));

		setGameStage(BEFORE_GAME);

		snakeFrame.setVisible(true);
	}

	/* Initialize components needed for the game
	*/
	private static void initializeGame() {
		/* Set up score, snake and first kibble */

		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares, squareSize);
		kibble = new Kibble(snake);
		score = new Score();

		gameStage = BEFORE_GAME;
	}

	/* Create a new game
	*/
	protected static void newGame() {
		timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}

	public static void main(String[] args) {
		/* Schedule a job for the event-dispatching thread:
		 * creating and showing this application's GUI. */

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				createAndShowGUI();
			}
		});
	}

    /* If gameStage is GAME_OVER or GAME_WON, boolean method returns true
    */
	public static boolean gameEnded() {
        return gameStage == GAME_OVER || gameStage == GAME_WON;
	}

	/* Get & Set Methods for game stage */
	public static int getGameStage() {return gameStage;}
	public static void setGameStage(int gameStage) {SnakeGame.gameStage = gameStage;}
}
