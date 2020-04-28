package animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.cvcg.math.BasicMath;
import org.cvcg.math.Vec2d;

public class RectToCircle extends JFrame implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	int mouseX = 0;
	
	public RectToCircle() {
		setSize(500, 500);
		setTitle("Circle animation");
		setDefaultCloseOperation(RectToCircle.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		getContentPane().setBackground(Color.black);
		addMouseMotionListener(this);
	}

	public static void main(String[] args) {
		
		new RectToCircle();
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(250, 250);
		g2d.setPaint(Color.white);
		ArrayList<Vec2d> pts = new ArrayList<Vec2d>();
		
		final int SPACING = (int) BasicMath.map(mouseX, 0, 500, 5, 100);
		
		for(int theta=0; theta<360; theta+=SPACING) {
			int x = (int) (100 * Math.cos(theta) + 50);
			int y = (int) (100 * Math.sin(theta) + 50);
			
			pts.add(new Vec2d(x, y));
		}
		
		for(int i=0; i<pts.size(); i++) {
			
			if(i != pts.size() - 1) {
				Vec2d v1 = pts.get(i);
				Vec2d v2 = pts.get(i+1);
				g2d.drawLine(v1.x, v1.y, v2.x, v2.y);
			}
			else {
				Vec2d last = pts.get(pts.size() - 1);
				Vec2d first = pts.get(pts.size() - 2);
				g2d.drawLine(last.x, last.y, first.x, first.y);
			}
			
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		repaint();
	}

}
