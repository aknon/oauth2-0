package com.goraksh.games.example;


import javax.swing.JFrame;


public class Donut extends JFrame {


    /**
	 * 
	 */
	private static final long serialVersionUID = -7755844506757399616L;

	public Donut() {

        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 600);
        setLocationRelativeTo(null);
        setTitle("Donut");
        setVisible(true);
    }

    public static void main(String[] args) {
        new Donut();
    }
}
