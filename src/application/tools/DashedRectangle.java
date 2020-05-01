package application.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;

public class DashedRectangle extends JFrame implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	
	final float dash1[] = {5.0f};
    final BasicStroke dashed =
        new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f);
    
    
    int startX = 0, startY = 0, w = 0, h = 0;
    
    Graphics2D g2d;
    RoundRectangle2D rect;
    
    MouseEventHandler meh = new MouseEventHandler();
	
	public DashedRectangle() {
		setSize(new Dimension(600, 600));
		setTitle("Dotted");
		setDefaultCloseOperation(DashedRectangle.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setBackground(Color.white);
		addMouseMotionListener(this);
		addMouseListener(meh);
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g2d = (Graphics2D) g;
		g2d.setPaint(Color.black);
		
	    g2d.setStroke(dashed);
	    
	    rect = new RoundRectangle2D.Double(startX, startY, w, h, 10, 10);
	    
	    g2d.draw(new RoundRectangle2D.Double(300, 300,
                100,
                100,
                10, 10));
	    
	    g2d.draw(rect);
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		var endX = e.getX();
		var endY = e.getY();
		
		w = startX - endX;
		h = startY - endY;
		
		repaint();
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	private class MouseEventHandler extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			startX = e.getX();
			startY = e.getY();
			
		}
	}
	
	public static void main(String[] args) {
		new DashedRectangle();
	}

}
