package application.tools;

public class Test {

	public static void main(String[] args) {
		
		double x1 = 100, y1 = 100, x2 = 300, y2 = 432;
		var val = ((y2-y1) / (x2-x1));
		System.out.println(val);
		
		var radian = Math.atan(val);
		var degree = Math.toDegrees(radian);
		
		System.out.println(degree);
		
	}

}
