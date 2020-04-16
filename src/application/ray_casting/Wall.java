package application.ray_casting;

import org.cvcg.math.Vec2d;

public class Wall {

	public Vec2d a;
	public Vec2d b;
	
	public Wall(int x1, int y1, int x2, int y2) {
		this.a = new Vec2d(x1, y1);
		this.b = new Vec2d(x2, y2);
	}

	
}
