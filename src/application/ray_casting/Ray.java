package application.ray_casting;

import org.cvcg.math.Vec2d;

public class Ray {

	public Vec2d pos;
	public Vec2d dir;
	public Vec2d ptOfIntsec;
	
	public Ray(int x1, int y1, int x2, int y2) {
		
		this.pos = new Vec2d(x1, y1);
		this.dir = new Vec2d(x2, y2);
		this.ptOfIntsec = new Vec2d(0, 0);
	}

	public boolean hitWall(Wall wall) {
		
		final int x1 = wall.a.x;
		final int y1 = wall.a.y;
		final int x2 = wall.b.x;
		final int y2 = wall.b.y;
		
		final int x3 = this.pos.x;
		final int y3 = this.pos.y;
		final int x4 = this.dir.x;
		final int y4 = this.dir.y;
		
		final float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		
		if(den == 0) {
			System.out.println("Den is 0");
			return false;
		}
		
		final float T = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		final float U = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
		
		if(T > 0 && T < 1 && U > 0) {
			
			this.ptOfIntsec.x = (int) (x1 + T * (x2 - x1));
			this.ptOfIntsec.y = (int) (y1 + T * (y2 - y1));
			
			//System.out.println("T: " + T + " U: " + U);
			return true;
		}
		
		return false;
	}
}
