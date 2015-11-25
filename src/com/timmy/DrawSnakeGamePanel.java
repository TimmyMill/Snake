package com.timmy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/** This class is responsible for displaying the graphics used in the game:
 * snake, grid, kibble, instruction text, and high score
 * 
 * @author Clara
 * @author Timmy
 *
 */

public class DrawSnakeGamePanel extends JPanel {

    private Snake snake;
	private Kibble kibble;
	private Score score;
	
	DrawSnakeGamePanel(Snake s, Kibble k, Score sc){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
	}

	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. */
        int gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
        /* 1. Before game starts */
            case 1: {
        	displayInstructions(g);
        	break;
            }
        /* 2. During game */
            case 2 : {
        	displayGame(g);
        	break;
            }
        /* 3. Game lost aka game over */
            case 3: {
        	displayGameOver(g);
        	break;
            }
        /* 4. or, game won */
            case 4: {
        	displayGameWon(g);
        	break;
            }
        }

    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
//		g.clearRect(100,100,350,350);
        g.setColor(Color.CYAN);
        g.fillRect(100, 100, 350, 350);
        g.setColor(Color.decode("#FF8C00")); //Dark Orange
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}

	private void displayGameOver(Graphics g) {

//		g.clearRect(100,100,350,350);
        g.setColor(Color.CYAN);
        g.fillRect(100, 100, 350, 350);

        g.setColor(Color.decode("#FF8C00")); //Dark Orange
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
	}

	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;

//        g.setColor(Color.decode("#0f240f"));
        g.setColor(Color.decode("#003638"));
        g.fillRect(0, 0, maxX, maxY);

        /* Draw grid */
        g.setColor(Color.decode("#00FF00"));

        /* horizontal lines */
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		/* vertical lines */
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}
	}

	private void displayKibble(Graphics g) {

		/* Draw the kibble */
		g.setColor(Color.decode("#ff0800")); //Candy Apple Red

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

		g.fillRect(x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2);
		
	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();
		
		/* Draws head */
        g.setColor(Color.decode("#D2691E")); //Chocolate
        Point head = coordinates.pop();
        g.fillOval((int) head.getX(), (int) head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		
		/* Draws rest of snake */
        g.setColor(Color.decode("#FF8C00")); //Dark Orange
        for (Point p : coordinates) {
			g.fillOval((int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}

	}

	private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!",100,200);		
        g.drawString("Press q to quit the game",100,300);
    }

}