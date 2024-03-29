package com.goraksh.games.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel{
	
	private Image image;
	
	public Board() {
		ImageIcon icon = new ImageIcon( "D:\\\\workspace\\GamesApplet\\register.png");
		image = icon.getImage();
	}
	

    public void paint(Graphics g)
    {
      super.paint(g);

      Graphics2D g2 = (Graphics2D) g;
      g2.drawImage(image,0,0,null);

      RenderingHints rh =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);

      rh.put(RenderingHints.KEY_RENDERING,
             RenderingHints.VALUE_RENDER_QUALITY);

      g2.setRenderingHints(rh);

      Dimension size = getSize();
      double w = size.getWidth();
      double h = size.getHeight();

      Ellipse2D e = new Ellipse2D.Double(0, 0, 80, 130);
      g2.setStroke(new BasicStroke(1));
      g2.setColor(Color.gray);
      g2.draw( e );


      for (double deg = 0; deg < 360; deg += 5) {
          AffineTransform at =
              AffineTransform.getTranslateInstance(w / 2, h/2);
          at.rotate(Math.toRadians(deg));
          g2.draw(at.createTransformedShape(e));
        }
        
    }
}