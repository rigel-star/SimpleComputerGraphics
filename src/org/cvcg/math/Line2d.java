package org.cvcg.math;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Line2d extends JFrame {
	private static final long serialVersionUID = 1L;
	
	int x1 = 100, y1 = 100, x2 = 300, y2 = 430;
	
	Line2d(){
		setSize(500, 500);
		getContentPane().setBackground(Color.black);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Line 2D");
	}

	public static void main(String[] args) {
		new Line2d();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		//g.translate(this.getWidth()/2, this.getHeight()/2);
		
		var v1 = new Vec2d(x1, y1);
		var v2 = new Vec2d(x2, y2);
		
		//delta x
		var dx = v2.x - v1.x;
		//delta y
		var dy = v2.y - v1.y;
		
		var x = 0;
		var y = 0;
		
		for(x=v1.x; x<=v2.x; x++) {
			y = v1.y + dy * (x - v1.x) / dx;
			
			g.fillOval(x, y, 2, 2);
		}
		
		System.out.println(x + " " + y);
	}

}
