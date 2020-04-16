package org.cvcg.math;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Circle {

	static BufferedImage image;
	
	public static void main(String[] args) throws IOException {
		
		image = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
		drawCircle(300, 300, 100);
		ImageIO.write(image, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\outttt.jpg"));
	}
	
	static void drawCircle(final int centerX, final int centerY, final int radius) {
		int d = (5 - radius * 4)/4;
		int x = 0;
		int y = radius;
		Color circleColor = Color.white;
		
		do {
			image.setRGB(centerX + x, centerY + y, circleColor.getRGB());
			image.setRGB(centerX + x, centerY - y, circleColor.getRGB());
			image.setRGB(centerX - x, centerY + y, circleColor.getRGB());
			image.setRGB(centerX - x, centerY - y, circleColor.getRGB());
			image.setRGB(centerX + y, centerY + x, circleColor.getRGB());
			image.setRGB(centerX + y, centerY - x, circleColor.getRGB());
			image.setRGB(centerX - y, centerY + x, circleColor.getRGB());
			image.setRGB(centerX - y, centerY - x, circleColor.getRGB());
			if (d < 0) {
				d += 2 * x + 1;
			} else {
				d += 2 * (x - y) + 1;
				y--;
			}
			x++;
		} while (x <= y);
 
	}

}
