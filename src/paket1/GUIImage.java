package paket1;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import Image.Image;
import Image.Layer;
import Image.Piksel;


public class GUIImage extends Canvas implements ItemListener{
	ArrayList<BufferedImage> images=new ArrayList();
	Map<Integer,Checkbox> listaBoxova=new HashMap();


	GUI origin;
	int width,height;
	Image slike=new Image();
	int brslojeva=0;
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
	//	System.out.println(images.size());
		//System.out.println(images.get(images.size()-1).getWidth());
		Layer novi=new Layer(images.get(images.size()-1).getWidth(),images.get(images.size()-1).getHeight());
		//kopirajLejer(novi);
		
		
		//image.
		try {
			//for(int i=0;i<images.size();i++)
			images.set(images.size()-1, ImageIO.read(f));
			kopirajLejer(novi);
			//f=new File("C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\src\\RAY.bmp");
		//	images.add(new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB));
		//	images.set(1, ImageIO.read(f));
			//ImageIO.write(image,"bmp",f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Panel dodajLejer() {
		Panel p=new Panel();
		p.add(new Label("Sloj "+brslojeva));
		Checkbox aktivan=new Checkbox("Aktivan",true);
		aktivan.addItemListener(this);
		listaBoxova.put(brslojeva-1, aktivan);
		//aktivan.addItemListener(origin);
		p.add(aktivan);
		TextField poljeZaTekst=new TextField("");
		p.add(poljeZaTekst);
		p.add(new Label("opacity"));
		return p;
	}
	
	private void kopirajLejer(Layer novi) {
		// TODO Auto-generated method stub
		BufferedImage poslednji=images.get(images.size()-1);
		
		for(int i=0;i<poslednji.getWidth();i++) {
			for(int j=0;j<poslednji.getHeight();j++) {
				char R,G,B,A;
				R=(char)((poslednji.getRGB(i, j)&(0xFF000000))>>24);
				G=(char)((poslednji.getRGB(i, j)&(0xFF0000))>>16);
				B=(char)((poslednji.getRGB(i, j)&(0xFF00))>>8);
				A=(char)((poslednji.getRGB(i, j)&(0xFF)));
					novi.overwritepixel(i, j, new Piksel(R,G,B,0,A));
			}
		}
		brslojeva++;
		//slike.DodajSloj(novi, brslojeva++);
		//System.out.println("Trenutno slojeva: "+(brslojeva));
	}
	public void paint(Graphics g) {
		System.out.println("OSVEZENO CRTANJE POCINJE");
		//for(BufferedImage i:images)
			for(Map.Entry<Integer, Checkbox> c:listaBoxova.entrySet()) {
				BufferedImage i=images.get(c.getKey());
				if(c.getValue().getState()==false && images.indexOf(i)==c.getKey()) {
					//System.out.println("Sloj broj "+c.getKey()+" je neaktivan "+images.indexOf(i));
					continue;
				}
				else {
					g.drawImage(i, 0,0,  null);
				}
			}
			
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		origin.osveziSliku();
//		for(Map.Entry<Integer, Checkbox> c:listaBoxova.entrySet()) {
//			if(c.getValue().getState()==false) {
//				System.out.println("PROMENA");
//				
//			}
//		}
		
	}
}
