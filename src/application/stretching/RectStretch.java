package application.stretching;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import javax.swing.JFrame;

public class RectStretch {

	private ResizeRectangle canvas;
	
	public RectStretch() {
		this.canvas = new ResizeRectangle();
		
		JFrame f = new JFrame("Rect Stretch");
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);
		
		Container c = f.getContentPane();
		c.add(canvas);
	}
	
	public static void main(String[] args) {
		new RectStretch();
	}

}

class ResizeRectangle extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	private int startX = 0, startY = 0;
	private int w = 150, h = 100;
	
	public ResizeRectangle() {
		this.setBackground(Color.black);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Rectangle2D rect = new Rectangle2D.Double(startX, startY, w, h);
		Ellipse2D ball = new Ellipse2D.Double(startX + w - 10, startY + h - 10, 20, 20);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.white);
		g2d.draw(rect);
		g2d.draw(ball);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int finalX = e.getX(), finalY = e.getY();
		this.w = Math.abs(this.startX - finalX);
		this.h = Math.abs(this.startY - finalY);
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.startX = e.getX();
		this.startY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		int finalX = e.getX(), finalY = e.getY();
//		this.w = this.startX - finalX;
//		this.h = this.startY - finalY;
//		this.repaint();
	}
	
	
	
	
	
	
	  
}
