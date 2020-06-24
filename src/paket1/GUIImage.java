package paket1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GUIImage extends Canvas {
	ArrayList<BufferedImage> images=new ArrayList();
	GUI origin;
	int width,height;
	public GUIImage(GUI o) {
		origin=o;
		 width=origin.getWidth();
		//int width=1000;
		 height=origin.getHeight();
		//int height=1000;
		
		
	}
	public void setPutanja(String s) {
		File f=null;
		//System.out.println(s);
		f=new File("C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\src\\"+s);
		images.add(new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB));
		
		
		
		//image.
		try {
			//for(int i=0;i<images.size();i++)
			images.set(images.size()-1, ImageIO.read(f));
			//f=new File("C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\src\\RAY.bmp");
		//	images.add(new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB));
		//	images.set(1, ImageIO.read(f));
			//ImageIO.write(image,"bmp",f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paint(Graphics g) {
		for(BufferedImage i:images)
			g.drawImage(i, 0,0,  null);
	}
}
