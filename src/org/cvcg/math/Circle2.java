package org.cvcg.math;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Circle2 {

	public static void main(String[] args) {
		
		BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR);
		
		int radius = 150;
		int theta = 0;
		
		int _x = 0, _y = 0;
		
		double x = 0, y = 0;
		Graphics2D g = img.createGraphics();
		g.translate(250, 250);
		
		for(theta=0; theta<=300; theta++) {	
			
			x = radius * Math.cos(theta);
			y = radius * Math.sin(theta);
			
			if(theta == 5) {
				_x = (int) x;
				_y = (int) y;
			}
			
			if(x > (Math.round(x) + 0.5))
				x = Math.ceil(x);
			else x = Math.floor(x);
			
			if(y > (Math.round(y) + 0.5))
				y = Math.ceil(y);
			else y = Math.floor(y);
			
			g.fillRect((int) x, (int) y, 4, 4);
			//g.fillOval((int) x, (int) y, 4, 4);
		}
		
		try {
			
			//debug
			System.out.println(_x + " " + _y);
			
			ImageIO.write(img, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\circle.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
