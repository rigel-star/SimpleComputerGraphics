package application.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PolygonTest extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static final double DELTA_THETA = Math.PI / 45; // 4°
    private static final double DELTA_SCALE = 0.1;
    private int[] p1x = {200, 200, 240, 240, 220, 220, 200};
    private int[] p1y = {200, 260, 260, 240, 240, 200, 200};
    private int[] p2x = {600, 600, 620, 620, 640, 640, 660, 660, 600};
    private int[] p2y = {400, 420, 420, 460, 460, 420, 420, 400, 400};
    private int[] p3x = {400, 400, 460, 460, 440, 440, 420, 420, 400};
    private int[] p3y = {400, 460, 460, 400, 400, 440, 440, 400, 400};
    private Polygon p1 = new Polygon(p1x, p1y, p1x.length);
    private Polygon p2 = new Polygon(p2x, p2y, p2x.length);
    private Polygon p3 = new Polygon(p3x, p3y, p3x.length);
    private AffineTransform at = new AffineTransform();
    private double dt = DELTA_THETA;
    private double theta;
    private double ds = DELTA_SCALE;
    private double scale = 1;
    private javax.swing.Timer timer = new javax.swing.Timer(50, this);
    
    
    
    private int[] p4x = {300, 320, 320, 360, 360, 320, 320, 300, 300};
    private int[] p4y = {300, 360, 360, 300, 300, 340, 340, 300, 300};
    private Polygon p4 = new Polygon(p4x, p4y, p3x.length);

    
	public PolygonTest() {
		this.setPreferredSize(new Dimension(700, 700));
        this.setBackground(Color.white);
        p1.translate(-50, +100);
        p2.translate(-100, -100);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		theta += dt;
        scale += ds;
        if (scale < .5 || scale > 4) {
            ds = -ds;
        }
        repaint();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = this.getWidth();
        int h = this.getHeight();
        g2d.drawLine(w / 2, 0, w / 2, h);
        g2d.drawLine(0, h / 2, w, h / 2);
        g2d.rotate(theta, w / 2, h / 2);
        g2d.drawPolygon(p1);
        g2d.drawPolygon(p2);
        g2d.drawPolygon(p4);
        at.setToIdentity();
        at.translate(w / 2, h / 2);
        at.scale(scale, scale);
        at.translate(-p3x[5] + 10, -p3y[5]);
        g2d.setPaint(Color.blue);
        g2d.fill(at.createTransformedShape(p3));

        
	}
	
	
	
	public void start() {
        timer.start();
    }
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("PolygonTest");
		PolygonTest sl = new PolygonTest();
        frame.add(sl);
        frame.pack();
        frame.setVisible(true);
        sl.start();
	}


	

}
