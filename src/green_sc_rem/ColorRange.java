package green_sc_rem;

import java.awt.Color;


public class ColorRange {

private double rgb;
	
	public ColorRange is(double rgb) {
		this.rgb = rgb;
		return this;
	}
	
	public boolean inRange(double v1, double v2) {
		
		if(this.rgb > v1 && this.rgb < v2) {
			return true;
		}
		
		return false;
	}
	
	public boolean inRange(Color min, Color max) {
		
		if(this.rgb > min.getRGB() && this.rgb < max.getRGB()) {
			return true;
		}
		
		return false;
	}
	
	public static int[] constrain(int[] rgb) {		//balance rgb to not go over 255 and not go below 0.	
		int[] out = new int[rgb.length];
		for(int i=0; i<rgb.length; i++) {
			if(rgb[i] > 255)
				rgb[i] = 255;
			if(rgb[i] < 0)
				rgb[i] = 0;
			out[i] = rgb[i];
		}
		return out;
	}
}
