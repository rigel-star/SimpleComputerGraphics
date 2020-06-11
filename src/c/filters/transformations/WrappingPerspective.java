package c.filters.transformations;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.cvcg.math.BasicMath;

public class WrappingPerspective extends JFrame {
	private static final long serialVersionUID = 1L;

	private BufferedImage src, copy;
	private Point startP = null, endP = null;
	private Point extraStartP = null, extraEndP = null;
	private List<Line2D> actLns = new ArrayList<Line2D>();
	private List<Point> pts = new ArrayList<Point>();
	private List<Ellipse2D> eps = new ArrayList<Ellipse2D>();
	
	
	public WrappingPerspective(BufferedImage src) {
		
		this.src = src;
		
		var height = src.getHeight();
		var width = src.getWidth();
		
		copy = getCopy(src);
		
		var p = new CanvasPanel();
		
		add(p, BorderLayout.CENTER);
		setSize(width, height);
		setResizable(false);
		setDefaultCloseOperation(WrappingPerspective.EXIT_ON_CLOSE);
		setVisible(true);
		
		var mh = new MouseHandler(p, copy.createGraphics());
		
		p.addMouseListener(mh);
		p.addMouseMotionListener(mh);
		
	}
	
	
	public BufferedImage getCopy(BufferedImage sr) {
		ColorModel cm = sr.getColorModel();
		boolean iap = sr.isAlphaPremultiplied();
		WritableRaster raster = sr.copyData(sr.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, iap, null);
	}
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		var img = ImageIO.read(new FileInputStream(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\house.jpg")));
		new WrappingPerspective(img);
		
	}
	
	
	class CanvasPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(copy, 0, 0, null);
			
			var g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g2d.setPaint(Color.yellow);
			
			if(startP != null && endP != null)
				g2d.draw(new Line2D.Double(startP, endP));
			
			/*when 3 points are selected*/
			if(extraStartP != null && extraEndP != null)
				g2d.draw(new Line2D.Double(extraStartP, extraEndP));

			for(Shape s: actLns) {
				g2d.draw(s);
			}
			for(Shape s: eps) {
				g2d.fill(s);
			}
		}
		
	}
	
	
	
	class MouseHandler extends MouseAdapter {
		
		JComponent context;
		Graphics2D g;
		
		public MouseHandler(JComponent context, Graphics2D g) {
			this.context = context;
			
			this.g = g;
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			if(e.getButton() == MouseEvent.BUTTON3) {
				System.out.println("Clearing");
				copy = getCopy(src);
				startP = null;
				endP = null;
				extraEndP = null;
				extraStartP = null;
				
				g = copy.createGraphics();
				g.setPaint(Color.yellow);
				g.setStroke(new BasicStroke(3));
				
				pts.clear();
				actLns.clear();
				eps.clear();
			}
			
			else if(e.getButton() == MouseEvent.BUTTON1) {
				if(pts.size() >= 4) {
					System.out.println("4 corners already selected");
					return;
				}
				
				System.out.println("Adding");
				
				var pt = new Point(e.getX(), e.getY());
				
				var el = new Ellipse2D.Double(pt.x - 7, pt.y - 7, 15, 15);
				
				pts.add(pt);
				eps.add(el);
				
				if(pts.size() == 2) {
					System.out.println("Until now: 2 pts selected");
					actLns.add(new Line2D.Double(pts.get(0), pts.get(1)));
				}
				else if(pts.size() == 3) {
					System.out.println("Until now: 3 pts selected");
					actLns.add(new Line2D.Double(pts.get(1), pts.get(2)));
				}
				else if(pts.size() == 4) {
					System.out.println("Until now: all 4 pts selected");
					actLns.add(new Line2D.Double(pts.get(0), pts.get(3)));
					actLns.add(new Line2D.Double(pts.get(2), pts.get(3)));
				}
				
			}
			context.repaint();
		}
		
		
		@Override
		public void mouseMoved(MouseEvent e) {
			super.mouseMoved(e);
			
			var cpt = e.getPoint();
			
			if(pts.size() == 1) {
				startP = pts.get(0);
				endP = cpt;
			}
			else if(pts.size() == 2) {
				startP = pts.get(1);
				endP = cpt;
				extraStartP = pts.get(0);
				extraEndP = cpt;
			}
			else if(pts.size() == 3) {
				startP = pts.get(2);
				endP = cpt;
				extraStartP = pts.get(0);
				extraEndP = cpt;
			}
			else {
				startP = null;
				endP = null;
			}
			
			context.repaint();
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);
			
			if(pts.size() < 4) {
				System.out.println("First select 4 points");
				return;
			}
			
			Point p = pts.get(getClosestPointIndex(e.getPoint()));
			p.x = e.getX();
			p.y = e.getY();
			g.draw(new Line2D.Double(p, e.getPoint()));
			context.repaint();
		}
		
	}

	
	public int getClosestPointIndex(Point find) {
		int index = 0;
		double close = dist(find.x, find.y, pts.get(0).x, pts.get(0).y);
		
		for(int i=1; i<pts.size(); i++) {
			
			double dist = dist(find.x, find.y, pts.get(i).x, pts.get(i).y);
			
			if(dist < close) {
				close = dist;
				index = i;
			}
		}
		return index;
	}
	
	
	private double dist(double x1, double y1, double x2,  double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	
	public List<Point> getCoords(){
		return pts;
	}
	
	
	public BufferedImage getTransformedQuadImage() {
		
		var poly = new Polygon();
		
		GeneralPath gp = new GeneralPath(new Rectangle2D.Double());
		gp.moveTo(pts.get(0).x, pts.get(0).y);
		
		for(Point p: pts) {
			gp.moveTo(p.x, p.y);
			poly.addPoint(p.x, p.y);
		}
		
		var bound = poly.getBounds();
		poly.translate(-bound.x, -bound.y);
		
		var out = new BufferedImage(bound.width, bound.height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = out.createGraphics();
		g.setClip(poly);
		g.drawImage(src, -bound.x, -bound.y, null);
		g.dispose();
		
		return out;
	}

}


