package application.stretching;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//Basic image snapping tool
public class LazySnapping extends JFrame {
	private static final long serialVersionUID = 1L;

	JLabel paint;
	
	public LazySnapping() {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\styletransfer\\boy.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int h = img.getHeight();
		int w = img.getWidth();
		
		JFrame f = new JFrame("Lazy Snapping");
		f.setSize(w, h);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);
		
		Container c = f.getContentPane();
		
		paint = new JLabel(new ImageIcon(img));
		c.add(paint, BorderLayout.CENTER);
		
		SnappingCanvas sc = new SnappingCanvas(img);
		paint.addMouseListener(sc);
	}
	
	public static void main(String[] args) {
		new LazySnapping();
	}
	
	//for drawing canvas
	class SnappingCanvas extends MouseAdapter {
		
		List<Shape> shapes = new ArrayList<Shape>();
		
		BufferedImage img;
		Graphics2D g2d;
		
		public SnappingCanvas(BufferedImage img) {
			this.img = img;
			g2d = (Graphics2D) img.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(2));
			//transparency
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			g2d.setPaint(Color.blue);
		}
		
		public void paintImage() {
			for(int i=0; i<shapes.size(); i++) {
				g2d.fill(shapes.get(i));
				
				if(i != shapes.size() - 1) {
					Point2D p1 = shapes.get(i).getBounds().getLocation();
					Point2D p2 = shapes.get(i+1).getBounds().getLocation();
					g2d.draw(new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
				}	
			}
			paint.repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			int x = e.getX();
			int y = e.getY();
			Rectangle2D r = new Rectangle2D.Double(x, y+15, 5, 5);
			shapes.add(r);
			this.paintImage();
		}
	}

}
