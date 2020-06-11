package c.cmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import c.GrayColorModel;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		var img = ImageIO.read(new FileInputStream(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg")));
		
		var cm = new GrayColorModel(24);
		
		for(int x=0; x<img.getWidth(); x++) {
			for(int y=0; y<img.getHeight(); y++) {
				
				var pixel = img.getRGB(x, y);
				
				var alpha = cm.getAlpha(pixel);
				var red = cm.getRed(pixel);
				var green = cm.getGreen(pixel);
				var blue = cm.getBlue(pixel);
				
				final var lum = cm.getFinalGray();
				
				red = lum << 16;
				green = lum << 8;
				blue = lum;
				
				var color = alpha + red + green + blue;
				
				img.setRGB(x, y, color);
			}
		}
		
		ImageIO.write(img, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\lolo.jpg"));
		
	}

}
