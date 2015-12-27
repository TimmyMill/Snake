package com.timmy;

import javax.swing.*;
import java.awt.*;

public class GameBackground extends JPanel {

    private Image nebula;

    public GameBackground() {
        ImageIcon background = new ImageIcon("/home/timmy/IdeaProjects/Snake/src/com/timmy/Images/nebula751.png");
        nebula = background.getImage();
        Dimension size = new Dimension(nebula.getWidth(null), nebula.getHeight(null));
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(nebula, 0, 0, null);
    }

}