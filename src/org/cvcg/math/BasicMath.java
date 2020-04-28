package org.cvcg.math;

public class BasicMath {

	/**
	 * Linear Interpolation between two values.
	 * @param v1		the first value 
	 * @param v2		the second value
	 * @param perc		percentage
	 * @return {@code float}		lerp value between v1 and v2
	 * */
	public static float lerp(float v1, float v2, float perc) {
		
		if(perc < 0.0 || perc > 1.0)
			throw new IllegalArgumentException("Percentage must be between 0.0 to 1.0");
			
		return v1 + perc * (v2 - v1);
	}
	
	static public final float map(float value, float istart, float istop, float ostart, float ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}
}
