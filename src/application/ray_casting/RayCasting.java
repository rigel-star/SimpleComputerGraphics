package application.ray_casting;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;

public class RayCasting {

	private CanvasPanel canvas;
	
	public RayCasting() {
		
		this.canvas = new CanvasPanel();
		
		JFrame f = new JFrame("Ray Casting");
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);
		
		Container c = f.getContentPane();
		c.add(this.canvas);
	}
	
	public static void main(String[] args) {
		
		new RayCasting();
		
	}

}


class CanvasPanel extends Canvas implements MouseMotionListener, KeyListener{

	private static final long serialVersionUID = 1L;

	//positions
	private int boxX = 400, boxY = 60, boxX1 = 400, boxY1 = 200;
	private int width = 500;
	
	//vec2d
	private Ray ray;
	private Wall wall;
	
	//reflection
	private int refX = 0, refY = 0;
	
	//mousePos
	private MousePos mp = new MousePos(10, 10);
	private int rayEndX = this.width;
	private int rayEndY = this.mp.mouseY() + 10;
	
	public CanvasPanel() {
		setBackground(Color.black);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		this.init();
	}
	
	private void init() {
		this.ray = new Ray(0, 0, this.rayEndX, this.rayEndY);
		this.wall = new Wall(this.boxX, this.boxY, this.boxX1, this.boxY1);
	}
	
	private void update(MouseEvent e) {
		this.ray.pos.x = e.getX();
		this.ray.pos.y = e.getY();
		
		this.mp = new MousePos(e.getX(), e.getY());
		this.rayEndX = this.width;
		this.rayEndY = this.mp.mouseY() + 10;
		
		this.ray.dir.y = this.mp.mouseY() + 10;
		
		this.repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.update(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Line2D wall = new Line2D.Double(this.wall.a.x, this.wall.a.y, this.wall.b.x, this.wall.b.y);
		Line2D rayBeam = new Line2D.Double(this.mp.mouseX(), this.mp.mouseY() + 10, this.rayEndX, this.rayEndY);
		Ellipse2D light = new Ellipse2D.Double(this.mp.mouseX(), this.mp.mouseY(), 25, 25);
		Ellipse2D ref = new Ellipse2D.Double(this.refX - 15, this.refY - 15, 15, 15);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.white);
		
		g2d.draw(wall);
		g2d.fill(light);
		
		if(this.ray.hitWall(this.wall)) {
			this.refX = this.ray.ptOfIntsec.x;
			this.refY = this.ray.ptOfIntsec.y;
			this.rayEndX = this.ray.ptOfIntsec.x;
			this.rayEndY = this.ray.ptOfIntsec.y;
			g2d.draw(ref);
			rayBeam = new Line2D.Double(this.mp.mouseX(), this.mp.mouseY() + 10, this.rayEndX, this.rayEndY);
		}
		

		//g2d.draw(rayBeam);
		
		for(int x=(int) rayBeam.getY2() - 30; x<rayBeam.getY2(); x+=10) {
			//rayBeam = new Line2D.Double(this.mp.mouseX(), this.mp.mouseY() + 10, this.rayEndX, x);
			g2d.draw(new Line2D.Double(this.mp.mouseX(), this.mp.mouseY() + 10, this.rayEndX, x));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		System.out.println("Typed");
		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			this.mp.mouseY -= 5;
			this.repaint();
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			this.mp.mouseX += 5;
			this.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {	}
	
}



