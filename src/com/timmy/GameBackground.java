package com.timmy;
import javax.swing.*;
import java.awt.*;

/*********************************************************************************
 * This class is used to create and set the background image for the game panel  *
 * It extends JPanel so that all of the graphics created in the SnakeGamePanel   *
 * class can use the functionality of a JPanel and are drawn on top of this      *
 * background image                                                              *
 *                                                                               *
 * @author Timmy                                                                 *
 ********************************************************************************/

public class GameBackground extends JPanel
{
    private Image nebula;

    public GameBackground()
    {
        nebula = new ImageIcon("/home/timmy/IdeaProjects/Snake/src/com/timmy/Images/nebula.png").getImage();
        Dimension size = new Dimension(nebula.getWidth(null), nebula.getHeight(null));
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setSize(size);
        setLayout(null);
    }


    public void paintComponent(Graphics g)
    {
        g.drawImage(nebula, 0, 0, null);
    }

}