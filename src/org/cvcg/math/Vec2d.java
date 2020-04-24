package org.cvcg.math;

public class Vec2d {
	
	public int x;
	public int y;
	
	public Vec2d(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	public Vec2d add(Vec2d v2) {
		
		int x = this.getX() + v2.getX();
		int y = this.getY() + v2.getY();
		
		return new Vec2d(x, y);
	}
	
	public Vec2d sub(Vec2d v2) {
		int x = this.getX() - v2.getX();
		int y = this.getY() - v2.getY();
		
		return new Vec2d(x, y);
	}
	
	public Vec2d mult(Vec2d v2) {
		int x = this.getX() * v2.getX();
		int y = this.getY() * v2.getY();
		
		return new Vec2d(x, y);
	}
	
	public Vec2d midpoint(Vec2d v2) {
		//add v2 with v1
		//this.add(v2);
		this.x = this.x + v2.x;
		this.y = this.y + v2.y;
		//multiply v1 with 1 and divide by 2
		int x = (1 * this.getX()) / 2;
		int y = (1 * this.getY()) / 2;
		
		return new Vec2d(x, y);
	}
	
	//magnitude of a vector
	//formulae: square root of x1 power 2 + x2 power 2
	public double mag() {
		var mag = Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
		return mag;
	}
	
	//direction of a vector
	//formulae: -> dir = y2-y1/x2-x1 -> atan(dir) or tan-1(dir)
	public double dirTo(Vec2d v) {
		var val = (((double) v.y - (double) this.y) / ((double) v.x - (double) this.x));
		var radian = Math.atan(val);
		var degree = Math.toDegrees(radian);
		return degree;
	}
	
	//dist of two vectors
	//formulae: square root of x2 - x1 power of 2 + y2 - y2 power of 2
	public double dist(Vec2d v2) {
		var dist = Math.sqrt(Math.pow((double) v2.x - (double) this.x, 2) +
				Math.pow(((double) v2.y - (double) this.y), 2));
		return dist;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
