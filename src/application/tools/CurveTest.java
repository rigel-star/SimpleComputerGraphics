package application.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.QuadCurve2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CurveTest extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	
	private QuadCurve2D.Double curve;
	private int mouseX = 0, mouseY = 0;
	
	public CurveTest() {
		setPreferredSize(new Dimension(600, 600));
		setBackground(Color.black);
		addMouseMotionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.white);
		
		curve = new QuadCurve2D.Double(0, getHeight()/2, mouseX, mouseY, getWidth(), getHeight()/2);
		
		//GradientPaint gp = new Gra
		
		g2d.draw(curve);
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	
	public static void main(String[] args) {
		
		JFrame f = new JFrame("Curve");
		CurveTest ct = new CurveTest();
		f.add(ct);
		f.setSize(600, 600);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

}
