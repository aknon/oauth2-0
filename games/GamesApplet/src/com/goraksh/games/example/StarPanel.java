package com.goraksh.games.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StarPanel extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7013253679962968333L;
	Image star;
    Timer timer;
    int x, y;

    public StarPanel() {
        setBackground(Color.blue);

        ImageIcon ii =
            new ImageIcon( Constants.STAR_PNG );
        star = ii.getImage();

        setDoubleBuffered(true);

        x = y = 10;
        timer = new Timer(25, this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
       g.dispose();
    }

    public void actionPerformed(ActionEvent e) { 
        x += 1;
        y += 1;

        if (y > 240) {
            y = -45;
            x = -45;
        }
        repaint();  
    }
}