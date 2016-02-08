package com.timmy;
import javax.swing.*;
import java.util.Timer;


public class SnakeGame
{
	/* Pixels in window. 751 to have 50-pixel squares plus 1 to draw a border on last square */
	public final static int xPixelMaxDimension = 751;
	public final static int yPixelMaxDimension = 751;

	protected static int xSquares ;
	protected static int ySquares ;

	public final static int squareSize = 75;

	protected static Snake snake ;
	protected static Food food;
	protected static Score score;

	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;
	/* The values are not important.
	 * The important thing is to use the constants instead of the values so you are clear what you are setting.
	 * Easy to forget what number is Game over vs. game won
	 * Using constant names instead makes it easier to keep it straight.
	 * Refer to these variables using statements such as SnakeGame.GAME_OVER */

	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change its value

	protected static long clockInterval = 500; //controls game speed
	/* Every time the clock ticks, the snake moves
	 * This is the time between clock ticks, in milliseconds
	 * 1000 milliseconds = 1 second */

	protected static Timer timer;

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	/* Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too
	 * http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	 * http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html */

	private static void createAndShowGUI()
	{
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

		snakePanel = new DrawSnakeGamePanel(snake, food, score);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));

		setGameStage(BEFORE_GAME);

		snakeFrame.setLocationRelativeTo(null); //center window
		snakeFrame.setVisible(true);
	}


	private static void initializeGame()
	{
		//Set up score, snake and first food
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares, squareSize);
		food = new Food(snake);
		score = new Score();

		gameStage = BEFORE_GAME;
	}

	protected static void newGame()
	{
		timer = new Timer();
		GameClock clockTick = new GameClock(snake, food, score, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}

	public static void main(String[] args)
	{
		/* Schedule a job for the event-dispatching thread:
		 * creating and showing this application's GUI. */
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				initializeGame();
				createAndShowGUI();
			}
		});
	}
	
	static void resetTimer()
	{
		timer.cancel(); //stop the timer
		timer.purge();  //terminates any remaining tasks
	}


	public static int getGameStage() { return gameStage; }
	public static void setGameStage(int gameStage) { SnakeGame.gameStage = gameStage; }

	public static boolean gameEnded()
	{
		//If gameStage is GAME_OVER or GAME_WON, boolean method returns true
		return gameStage == GAME_OVER || gameStage == GAME_WON;
	}

}