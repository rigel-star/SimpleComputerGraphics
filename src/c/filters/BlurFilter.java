package c.filters;

import java.awt.image.ColorModel;
import java.awt.image.ImageFilter;

public class BlurFilter extends ImageFilter {
	
	
	private int savedWidth, savedHeight, savedPixels[];
	private ColorModel defaultCM;
	
	@Override
	public void setDimensions(int width, int height) {
		super.setDimensions(width, height);
		
		savedWidth = width;
		savedHeight = height;
		savedPixels = new int[width * height];
		
		consumer.setDimensions(width, height);
	}
	
	@Override
	public void setColorModel(ColorModel model) {
		super.setColorModel(model);
		consumer.setColorModel(defaultCM);
	}
	
	@Override
	public void setHints(int hints) {
		super.setHints(hints);
		consumer.setHints(TOPDOWNLEFTRIGHT | COMPLETESCANLINES | SINGLEPASS | (hints & SINGLEFRAME));
	}

}
