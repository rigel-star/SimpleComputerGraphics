package animation;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;

public class MovingCircle extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;

	CircleAnimator c = new CircleAnimator();
	Thread t = new Thread(c);
	
	public MovingCircle() {
		setSize(500, 500);
		setDefaultCloseOperation(Spiral.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Moving Circle");
		setVisible(true);
		addKeyListener(this);
		add(c, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new MovingCircle();
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
	
	class CircleAnimator extends Canvas implements Runnable {
		private static final long serialVersionUID = 1L;
		
		double x, y;
		
		int theta = 0, radius = 150;
		Graphics2D g2d;
		
		public CircleAnimator() {
			setBackground(Color.black);
		}
		
		@Override
		public void paint(Graphics g){
			g2d = (Graphics2D) g;
			g2d.translate(250, 250);
			g2d.setPaint(Color.white);
			g2d.draw(new Line2D.Double(0, 0, x, y));
			g2d.draw(new Ellipse2D.Double(x-10, y-10, 15, 15));
		}

		public void playAnim() {
			while(theta <= 360) {
				
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
				
//				g2d.draw(new Line2D.Double(0, 0, x, y));
//				g2d.draw(new Ellipse2D.Double(x, y, 15, 15));
				
				theta++;
				radius--;
				
				if(radius <= 10)
					radius = 150;		
				if(theta >= 360)
					theta = 0;
				
				try {
					Thread.sleep(20);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void run() {
			playAnim();
		}
	}
}
