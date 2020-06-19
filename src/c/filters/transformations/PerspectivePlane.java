package c.filters.transformations;

import java.awt.Point;
import java.util.List;

public class PerspectivePlane {

	private Point p1, p2, p3, p4;
	
	private List<Point> plis;
	
	public PerspectivePlane(Point p1, Point p2, Point p3, Point p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		
		plis.add(this.p1);
		plis.add(this.p2);
		plis.add(this.p3);
		plis.add(this.p4);
		
	}
	
	public List<Point> getCoords(){
		return plis;
	}
	
}
