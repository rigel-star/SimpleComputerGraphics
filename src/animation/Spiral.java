package animation;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Spiral extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;

	Animator a = new Animator();
	Thread t = new Thread(a);
	
	Spiral(){
		setSize(500, 500);
		setDefaultCloseOperation(Spiral.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Spiral Animation");
		setVisible(true);
		addKeyListener(this);
		
		Container c = this.getContentPane();
		
		c.add(a, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		new Spiral();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			t.start();
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	class Animator extends Canvas implements Runnable {
		private static final long serialVersionUID = 1L;
		
		public Animator() {
			setBackground(Color.black);
		}
		
		int radius = 10;
		int theta = 0;
		
		double x = 0, y = 0;
		Graphics2D g2d;
		Point curr = null;
		Point prev = null;
		
		List<Shape> shapes = new ArrayList<Shape>();
		Line2D l;
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			g2d = (Graphics2D) g;
			g2d.setPaint(Color.white);	
			g2d.translate(250, 250);
			
			curr = new Point((int) x, (int) y);
			
			if(prev == null) {
				prev = curr;
			}
			
			l = new Line2D.Double(curr, prev);
			Ellipse2D e = new Ellipse2D.Double(x, y, 10, 10);
			g2d.draw(e);
			shapes.add(e);
			
			for(Shape s: shapes)
				g2d.draw(s);
			
			prev = new Point((int) x, (int) y);
		}

		@Override
		public void run() {
				
			for(theta=0; theta<=300;theta++) {	
				
				x = radius * Math.cos(theta);
				y = radius * Math.sin(theta);
				
				if(x > (Math.round(x) + 0.5))
					x = Math.ceil(x);
				else
					x = Math.floor(x);
				
				if(y > (Math.round(y) + 0.5))
					y = Math.ceil(y);
				else
					y = Math.floor(y);
						
				g2d.draw(new Ellipse2D.Double(x, y, 4, 4));
				radius += 2;
				
				try {
					Ellipse2D e = new Ellipse2D.Double(x, y, 4, 4);
					g2d.draw(e);
					Thread.sleep(50);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
