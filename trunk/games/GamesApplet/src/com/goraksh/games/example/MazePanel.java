package com.goraksh.games.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MazePanel extends JPanel {
	
	public static int[][] actual = new int[][] { 
		{ 0, 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 1, 1, 0, 1, 0, 1,  1 }, 
		{ 0, 1, 1, 0, 1, 0, 0, 1 },
		{ 0, 1, 0, 0, 1, 1, 0,  1 }, 
		{ 0, 1, 0, 1, 1, 1, 0, 1 },
		{ 0, 1, 0, 1, 0, 1, 0, 1 },
		{ 0, 1, 0, 0, 0, 1, 0,  1 },
		{ 1, 1, 1, 0, 1, 0, 0,  1 },
		{ 1, 1, 1, 0, 0, 0, 1, 1 }
		};
	
	private Dimension dim;
	
	private int[][] maze = actual;//BackTrackingMaze.maze;
	// for maze, movement along WIDTH is  movement along Y , and movement along HEIGHT is movement along X
	private int X;
	private int Y;
	
	public MazePanel() {
		X = maze.length;
		Y = maze[0].length;
		
		int rows = X;
		int cols = Y;
		
		dim = new Dimension( rows, cols );
				
		System.out.println("Grid width:" + dim.width + " ,Grid Height: " + dim.height);
		setLayout( new GridLayout(rows, cols ) );
		
   addLabels();
		
	}
	
	private void addLabels() {
		for ( int i = 0; i < X;  i ++ ) {
			for ( int j = 0; j < Y; j ++ ) {
				JLabel label  = getLabel(i, j);
				//label.setBorder( new BevelBorder(BevelBorder.LOWERED));
				add( label, BorderLayout.CENTER );
			}
		}
	}
	
	//public void paint( Graphics g ) {
		//dopaintComponent(g);
	//}
	
	public void paintComponent( Graphics g ) {
		System.out.println("Calling panel.paintComponent");
		Component[] comps = getComponents();
		for ( Component comp : comps ) {
		if ( comp instanceof GridLabel ) {
			System.out.println("Painting this grid label");
			((GridLabel) comp).dopaintComponent(g);
		}
		}
	}
	
	
	private JLabel getLabel( int mazeX, int mazeY ) {
		Color color = Color.green;
		String image = null;
		if ( maze[mazeX][mazeY] == 1 ) {
			color = Color.blue;
			System.out.println(mazeX + " : " + mazeY + " :::color=" + color.toString());
			
		}else {
				System.out.println(mazeX + " : " + mazeY + " :::color="+color.toString());
				image = Constants.STAR_PNG;
		}
		
		return new GridLabel("",  color, image);
	
	}
}


class GridLabel extends JLabel {
	
	private Color color;
	private Image image;
	
	public GridLabel( String text, Color color, String imagePath ) {
		super( text );
		this.color = color;
		
		setBorder( new BevelBorder(BevelBorder.LOWERED));
		
		if ( imagePath != null ) {
		  ImageIcon ii =
		            new ImageIcon( imagePath );
		  image = ii.getImage();
	}
	}
	
	private Color getColor() {
		return this.color;
	}
	
	
	public void  dopaintComponent( Graphics g ) {
		//super.paintComponent(g);
		g.setPaintMode();
		System.out.println("Painting Jlabel with Color: " + g.getColor().toString() );
		g.setColor( getColor() );
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight() );
		
		addImage(g, this.getX(), this.getY(), this.getWidth()/2, this.getHeight()/2 );
				
	}
	
	private void addImage( Graphics g, int x, int y, int width, int height ) {
		g.drawImage(this.image, x, y, width, height, null );
	}
	
	
	
}
