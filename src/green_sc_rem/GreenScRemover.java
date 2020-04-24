package green_sc_rem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GreenScRemover {

	public static void main(String[] args) throws IOException {
		
		BufferedImage img = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\styletransfer\\gsc.jpg"));
		BufferedImage vfx = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\styletransfer\\road.jpg"));
		
		//green screen color
		Color col = new Color(18, 200, 34);
		new ColRemover(img, col);
		
		ImageIO.write(GreenScRemover.applyVFX(img, vfx), "jpg", new File("C:\\Users\\Ramesh\\Desktop\\gsc.jpg"));
	}
	
	static BufferedImage applyVFX(BufferedImage target, BufferedImage vfx) {
		
		int h = target.getHeight();
		int w = target.getWidth();
		
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		Color c1 = null, c2 = null, res = null;
		Color marker = new Color(11, 11, 11);
		
		for(int x=0; x<w; x++) {
			for(int y=0; y<h; y++) {
				
				c1 = new Color(target.getRGB(x, y));
				c2 = new Color(vfx.getRGB(x, y));
				
				if(c1.getRGB() == marker.getRGB()) {
					
					res = new Color(c2.getRGB());
					out.setRGB(x, y, res.getRGB());
					
				} else {
					
					//fading effect OR image multiplying
					//u can try without multiplying which gives more awesome result
					int[] rgb = {(c1.getRed() * c2.getRed())/255,
							(c1.getGreen() * c2.getGreen())/255,
							(c1.getBlue() * c2.getBlue())/255};
					
					rgb = ColorRange.constrain(rgb);
					
					//res = c1;		//without multiplying
					res = new Color(rgb[0], rgb[1], rgb[2]);
					out.setRGB(x, y, res.getRGB());
				}
			}
		}
		
		return out;
	}
}
