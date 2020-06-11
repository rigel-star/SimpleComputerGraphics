package c;

import java.awt.image.ColorModel;

public class GrayColorModel extends ColorModel {
	
	
	private int red, green, blue;

	public GrayColorModel(int bits) {
		super(bits);
	}

	
	
	/**
	 * Get final gray value.
	 * */
	public int getFinalGray() {
		return red + green + blue;
	}
	
	
	@Override
	public int getRed(int pixel) {
		red = (pixel >> 16) & 255;
		return (int) (red * 0.2126);
	}

	@Override
	public int getGreen(int pixel) {
		green = (pixel >> 8) & 255;
		return (int) (green * 0.7152);
	}

	@Override
	public int getBlue(int pixel) {
		blue = (pixel) & 255;
		return (int) (blue * 0.0722);
	}

	@Override
	public int getAlpha(int pixel) {
		return (pixel >> 24) & 255;
	}

	
	
}
