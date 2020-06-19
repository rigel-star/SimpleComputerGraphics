package c.filters.transformations;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Jama.Matrix;


public class PerspectiveWarp extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private double x1, y1, x2, y2, x3, y3, x4, y4, X1, Y1, X2, Y2, X3, Y3, X4, Y4;
	
	private BufferedImage src, copy, computedImage;
	private Point startP = null, endP = null;
	private Point extraStartP = null, extraEndP = null;
	private List<Line2D> actLns = new ArrayList<Line2D>();
	private List<Point> pts = new ArrayList<Point>();
	private List<Ellipse2D> eps = new ArrayList<Ellipse2D>();
	private List<LineLocation> lloc = new ArrayList<PerspectiveWarp.LineLocation>();
	
	private Point draggedPoint;
	
	private Rectangle bounds;
	private BufferedImage target;
	
	public PerspectiveWarp(BufferedImage src) {
		
		this.src = src;
		
		try {
			target = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg"));
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		var height = src.getHeight();
		var width = src.getWidth();
		
		copy = getCopy(src);
		
		var p = new CanvasPanel();
		
		add(p, BorderLayout.CENTER);
		setSize(width + 50, height + 50);
		setResizable(false);
		setDefaultCloseOperation(PerspectiveWarp.EXIT_ON_CLOSE);
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
		
		var img = ImageIO.read(new FileInputStream(new File("C:\\Users\\Ramesh\\Desktop\\pers_homog\\boxes2.jpg")));
		new PerspectiveWarp(img);
		
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
			
			if(pts.size() == 4) {
				computedImage = VanishingPointEffect.Pseudo3D.computeImage(target,
						pts.get(0), pts.get(1), pts.get(2), pts.get(3));
				g2d.drawImage(computedImage, 0, 0, null);
			}
			
			/*when 3 points are selected*/
			if(extraStartP != null && extraEndP != null)
				g2d.draw(new Line2D.Double(extraStartP, extraEndP));

			
			/*lines to draw*/
			for(LineLocation llc: lloc) {
				g2d.draw(new Line2D.Double(llc.getP1(), llc.getP2()));
			}
			
			/*for every control point*/
			for(Point p: pts) {
				g2d.fill(new Ellipse2D.Double(p.x - 7, p.y - 7, 15, 15));
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
		
		
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			
			var p = e.getPoint();
			var r = 8;
			
			if(pts.size() < 4)
				return;
			
			/*check for every point if the current mouse press
			 *  locations dist to any of the point is less than 8 then drag that point*/
			if(p.distance(pts.get(0)) < r) draggedPoint = pts.get(0);
			if(p.distance(pts.get(1)) < r) draggedPoint = pts.get(1);
			if(p.distance(pts.get(2)) < r) draggedPoint = pts.get(2);
			if(p.distance(pts.get(3)) < r) draggedPoint = pts.get(3);
		}
		
		
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);
			
			if(pts.size() < 4)
				return;
			
			if (draggedPoint != null)
	        {
	            draggedPoint.setLocation(e.getX(), e.getY());
	            context.repaint();
	        }
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			if(e.getButton() == MouseEvent.BUTTON3) {
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
				lloc.clear();
			}
			
			else if(e.getButton() == MouseEvent.BUTTON1) {
				if(pts.size() >= 4) {
					
					System.out.print("4 corners already selected, enter y to save image and i for info: ");
					Scanner sc = new Scanner(System.in);
					String cmd = sc.nextLine();
					
					if("y".equals(cmd)) {
						try {
							var poly = new Polygon();
							
							for(Point p: pts) {
								poly.addPoint(p.x, p.y);
							}
							
							var bound = poly.getBounds();
							var ii = getCropedImage(src, pts, bound.width, bound.height);
							
							ImageIO.write(ii, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\uoi.jpg"));
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
					else if("i".equals(cmd)) {
						getUntransformedQuadImage();
						System.out.println("Height: " + bounds.height);
						System.out.println("Width: " + bounds.width);
						System.out.println("X Pos: " + bounds.x);
						System.out.println("Y Pos: " + bounds.y);
					}
					sc.close();
					return;
				}
				
				var pt = new Point(e.getX(), e.getY());
				
				var el = new Ellipse2D.Double(pt.x - 7, pt.y - 7, 15, 15);
				
				pts.add(pt);
				eps.add(el);
				
				if(pts.size() == 2) {
					actLns.add(new Line2D.Double(pts.get(0), pts.get(1)));
					lloc.add(new LineLocation(pts.get(0), pts.get(1)));
				}
				else if(pts.size() == 3) {
					actLns.add(new Line2D.Double(pts.get(1), pts.get(2)));
					lloc.add(new LineLocation(pts.get(1), pts.get(2)));
				}
				else if(pts.size() == 4) {
					actLns.add(new Line2D.Double(pts.get(0), pts.get(3)));
					actLns.add(new Line2D.Double(pts.get(2), pts.get(3)));
					lloc.add(new LineLocation(pts.get(0), pts.get(3)));
					lloc.add(new LineLocation(pts.get(2), pts.get(3)));
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
		
		
	}

	
	
	class LineLocation {
		
		private Point p1;
		private Point p2;
		
		public LineLocation(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
		
		public Point getP1() {
			return p1;
		}
		public Point getP2() {
			return p2;
		}
	}
	
	
	public List<Point> getCoords(){
		return pts;
	}
	
	
	public BufferedImage getUntransformedQuadImage() {
		
		var poly = new Polygon();
		
		for(Point p: pts) {
			poly.addPoint(p.x, p.y);
		}
		
		var bound = poly.getBounds();
		this.bounds = bound;
		poly.translate(-bound.x, -bound.y);
		var out = new BufferedImage(bound.width, bound.height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = src.createGraphics();
		
		g.setClip(poly);
		g.drawImage(target, -bound.x, -bound.y, null);
		g.dispose();
		
		return src;
	}
	
	
	public BufferedImage getCropedImage(BufferedImage imgBuffer,
            List<Point> cornersCordinate, int imageWidth,
            int imageHeight) throws IOException {

		X1 = Math.abs(cornersCordinate.get(0).getX());
        Y1 = Math.abs(cornersCordinate.get(0).getY());
        X2 = Math.abs(cornersCordinate.get(1).getX());
        Y2 = Math.abs(cornersCordinate.get(1).getY());
        X3 = Math.abs(cornersCordinate.get(2).getX());
        Y3 = Math.abs(cornersCordinate.get(2).getY());
        X4 = Math.abs(cornersCordinate.get(3).getX());
        Y4 = Math.abs(cornersCordinate.get(3).getY());
        x1 = 0;
        y1 = 0;
        x2 = imageWidth - 1;
        y2 = 0;
        x3 = imageWidth - 1;
        y3 = imageHeight - 1;
        x4 = 0;
        y4 = imageHeight - 1;
        

        double M_a[][] = { 
        		{ x1, y1, 1, 0, 0, 0, -x1 * X1, -y1 * X1 },
                { x2, y2, 1, 0, 0, 0, -x2 * X2, -y2 * X2 },
                { x3, y3, 1, 0, 0, 0, -x3 * X3, -y3 * X3 },
                { x4, y4, 1, 0, 0, 0, -x4 * X4, -y4 * X4 },
                { 0, 0, 0, x1, y1, 1, -x1 * Y1, -y1 * Y1 },
                { 0, 0, 0, x2, y2, 1, -x2 * Y2, -y2 * Y2 },
                { 0, 0, 0, x3, y3, 1, -x3 * Y3, -y3 * Y3 },
                { 0, 0, 0, x4, y4, 1, -x4 * Y4, -y4 * Y4 }
                };

        double M_b[][] = { { X1 }, { X2 }, { X3 }, { X4 }, { Y1 }, { Y2 },
                { Y3 }, { Y4 }, };

        Matrix A = new Matrix(M_a);
        Matrix B = new Matrix(M_b);
        Matrix C = A.solve(B);
        double a = C.get(0, 0);
        double b = C.get(1, 0);
        double c = C.get(2, 0);
        double d = C.get(3, 0);
        double e = C.get(4, 0);
        double f = C.get(5, 0);
        double g = C.get(6, 0);
        double h = C.get(7, 0);


        BufferedImage output = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                    int x = (int) (((a * i) + (b * j) + c) / ((g * i) + (h * j) + 1));
                    int y = (int) (((d * i) + (e * j) + f) / ((g * i) + (h * j) + 1));
                    int p = imgBuffer.getRGB(x, y);
                    output.setRGB(i, j, p);
            }
        }

        return output;
	}

}


