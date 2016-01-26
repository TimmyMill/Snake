package com.timmy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text, and high score
 * 
 * @author Clara
 * @author Timmy
 *
 */
public class DrawSnakeGamePanel extends GameBackground {

	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint

	private Snake snake;
	private Kibble kibble;
	private Score score;

	//Images
	private Image cupcake;
	private Image leftPortal;
	private Image rightPortal;

	DrawSnakeGamePanel(Snake s, Kibble k, Score sc){
		this.snake = s;
		this.kibble = k;
		this.score = sc;

		ImageIcon food = new ImageIcon("/home/timmy/IdeaProjects/Snake/src/com/timmy/Images/cupcake.png");
		cupcake = food.getImage();
		ImageIcon left = new ImageIcon("/home/timmy/IdeaProjects/Snake/src/com/timmy/Images/leftPortal.png");
		leftPortal = left.getImage();
		ImageIcon right = new ImageIcon("/home/timmy/IdeaProjects/Snake/src/com/timmy/Images/rightPortal.png");
		rightPortal = right.getImage();

	}
	
	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
			case 1: {
				displayInstructions(g);
				break;
			}
			case 2 : {
				displayGame(g);
				break;
			}
			case 3: {
				displayGameOver(g);
				break;
			}
			case 4: {
				displayGameWon(g);
				break;
			}
			default: {
				System.out.println("Not in a game stage? Say what?");
				break;
			}
        }

    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}
	private void displayGameOver(Graphics g) {

		g.clearRect(100,100,350,350);
		g.drawString("GAME OVER", 150, 150);
		
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 250);
		
		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		
		g.drawString("press a key to play again", 150, 350);
		g.drawString("Press q to quit the game",150,400);		

	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
		displayMazes(g);
		displayPortal(g);
	}

	private void displayGameGrid(Graphics g) {
		//Display game grid if mazes are turned off
		if (!snake.isMazeOn()) {

			int maxX = SnakeGame.xPixelMaxDimension;
			int maxY = SnakeGame.yPixelMaxDimension;
			int squareSize = SnakeGame.squareSize;

			g.setColor(Color.decode("#9932CC"));

			//Draw grid - horizontal lines
			for (int y = 0; y <= maxY; y += squareSize) {
				g.drawLine(0, y, maxX, y);
			}
			//Draw grid - vertical lines
			for (int x = 0; x <= maxX; x += squareSize) {
				g.drawLine(x, 0, x, maxY);
			}
		}

	}

	private void displayMazes(Graphics g) {
		//Display mazes if they are turned on
		if (snake.isMazeOn()) {

		}
	}

	private void displayPortal(Graphics g) {
		int maxX = snake.getMaxX();
		int maxY = snake.getMaxY();

		if (snake.isPortalOn()) {

			LinkedList<Point> coordinates = snake.segmentsToDraw();
//			Point head = coordinates.pop();

			for (Point p : coordinates) {
				if (p.getX() < 0) {
					System.out.println("Left");
					g.drawImage(leftPortal, 0, (int)p.getY(), this);
				}
				if (p.getX() > maxX) {
					System.out.println(p.getX());
//					System.out.println("Right");
					g.drawImage(rightPortal, maxX - 1, (int)p.getY(), this);
				}
//				if (snakeHeadY >= maxY) snakeHeadY = 0;
//				if (snakeHeadY < 0 ) snakeHeadY = maxY - 1;
//
//				if (p.getX() < 0 ) {
//					System.out.println("Left");
//					g.drawImage(leftPortal, 0, (int)p.getY(), this);
//				}
//				else if (p.getX() > right) {
//					System.out.println("Right");
//					g.drawImage(rightPortal, right, (int)p.getY(), this);
//				}
			}
		}
	}

	private void displayKibble(Graphics g) {

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

		g.drawImage(cupcake, x, y, this);

	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();
		
		//Draw head
		g.setColor(Color.decode("#D2691E")); //chocolate
		Point head = coordinates.pop();
		g.fillOval((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		
		//Draw rest of snake
		g.setColor(Color.decode("#FF8C00")); //dark orange
		for (Point p : coordinates) {
			g.fillOval((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}

	}

	private void displayInstructions(Graphics g) {
		double centerX = SnakeGame.xPixelMaxDimension / 2;
		double centerY = SnakeGame.yPixelMaxDimension / 2;
		double x = SnakeGame.xPixelMaxDimension / 3;
		double y = SnakeGame.yPixelMaxDimension / 3;

		g.setColor(Color.decode("#9932CC"));
//		g.setColor(Color.decode("#8A2BE2"));
		g.fillRect( (int)centerX / 2, (int)centerY / 2, (int)centerX, (int)centerY);

		g.setColor(Color.decode("#FF8C00")); //dark orange
		g.drawString("Press any key to begin!", (int)x, (int)y);
		g.drawString("Press q to quit the game", (int)x, (int)y + 25);
		g.drawString("Press w to toggle portals on/off", (int)x, (int)y + 50);
	}

}