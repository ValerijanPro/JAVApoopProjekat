package paket1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

//1.na random poziciju upisati
//


public class GUIImage extends Canvas implements ItemListener,ActionListener{
	Map<Integer,BufferedImage> images=new HashMap();
	Map<Integer,Checkbox> listaBoxova=new HashMap();

	Map<Integer,TextField> listaOpacitya=new HashMap();

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
	public void setPutanja(String s,int t ) {
		File f=null;
		//System.out.println(s);
		f=new File("C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\src\\"+s);
		BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		images.put(t,temp);
		Layer novi=new Layer(images.get(images.size()-1).getWidth(),images.get(images.size()-1).getHeight());

		try {
			
			temp=ImageIO.read(f);
			zastoNeRadi(temp,t);
			//images.put(t,ImageIO.read(f));
			//images.set(images.size()-1, ImageIO.read(f));
			
			kopirajLejer(novi,t);
			//f=new File("C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\src\\RAY.bmp");
		//	images.add(new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB));
		//	images.set(1, ImageIO.read(f));
			//ImageIO.write(image,"bmp",f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void zastoNeRadi(BufferedImage temp,int t) {
		BufferedImage temp2=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		// TODO Auto-generated method stub
		for(int i=0;i<temp.getWidth();i++) {
			for(int j=0;j<temp.getHeight();j++) {
				char R,G,B,A;
				 temp2.setRGB(i, j, temp.getRGB(i, j)); 
			    
				
			}
		}
		images.put(t, temp2);
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
		poljeZaTekst.addActionListener(this);
		listaOpacitya.put(brslojeva-1, poljeZaTekst);
		p.add(poljeZaTekst);
		p.add(new Label("opacity"));
		return p;
	}
	
	private void kopirajLejer(Layer novi,int t) {
		// TODO Auto-generated method stub
		
		BufferedImage poslednji=images.get(t);
		
		for(int i=0;i<poslednji.getWidth();i++) {
			for(int j=0;j<poslednji.getHeight();j++) {
				char R,G,B,A;
				 A = (char)((poslednji.getRGB(i, j) >> 24) & 0xff);
			     R = (char)((poslednji.getRGB(i, j) >> 16) & 0xff);
			     G = (char)((poslednji.getRGB(i, j) >> 8) & 0xff);
			     B = (char)((poslednji.getRGB(i, j)) & 0xff);
				//A=(char)((poslednji.getRGB(i, j)&(0xFF000000))>>24);
				//R=(char)((poslednji.getRGB(i, j)&(0xFF0000))>>16);
				//G=(char)((poslednji.getRGB(i, j)&(0xFF00))>>8);
				//B=(char)((poslednji.getRGB(i, j)&(0xFF)));
					novi.overwritepixel(i, j, new Piksel(R,G,B,0,A));
				if(i==0 && j==0) {
					System.out.println("ARGB: "+(int)A+","+(int)R+","+(int)G+","+(int)B);
				}
			}
		}
		brslojeva++;
		slike.DodajSloj(novi, t);
		//slike.DodajSloj(novi, brslojeva++);
		//System.out.println("Trenutno slojeva: "+(brslojeva));
	}
	public void paint(Graphics g) {
		System.out.println("OSVEZENO CRTANJE POCINJE");
		//for(BufferedImage i:images)
			for(Map.Entry<Integer, Checkbox> c:listaBoxova.entrySet()) {
				BufferedImage i=images.get(c.getKey());
				
				if(c.getValue().getState()==false  ) {
				
					continue;
				}
				else {
					
					//g.drawImage(i, 0, 0, 800, 600, null);
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
	public void setOpacity(int c,String newOp) {
		double newOpacity=Double.parseDouble(newOp);
		BufferedImage t=images.get(c);
		System.out.println("DOBIO SAM C:"+c);
		System.out.println("Da li su jednake: "+t.equals(images.get(0)));
		for(int i=0;i<t.getWidth();i++) {
			for(int j=0;j<t.getHeight();j++) {
				char R,G,B,A;
				 R = (char)((t.getRGB(i, j) >> 16) & 0xff);
			     G = (char)((t.getRGB(i, j) >> 8) & 0xff);
			     B = (char)((t.getRGB(i, j)) & 0xff);
				
				//A=(char)((t.getRGB(i, j)&(0xFF)));
				//System.out.println("velicina slike: "+slike.getBrlejera());
				A=(char)(slike.getLayers().get(c).getPixel(i, j).getOpacity()*(newOpacity/100));
				//A=(char)(slike.getLayers().get(c).getPixel(i, j).getOpacity());
				if(i==0 && j==0) {
					System.out.println("ARGB: "+(int)A+","+(int)R+","+(int)G+","+(int)B);
				}
				//int novo;
				int novo = (A << 24) | (R << 16) | (G << 8) | B;
				
				//novo=((A<<24)|(R<<16)|(G<<8)|B);
				t.setRGB(i, j, novo);
				
			}
		}
		//System.out.println("ASD");
		//origin.osveziSliku();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		for(Map.Entry<Integer, TextField> c:listaOpacitya.entrySet()) {
			if(!c.getValue().getText().equals("")) {
				System.out.println(c.getKey()+", "+c.getValue().getText());
				setOpacity(c.getKey(),c.getValue().getText());
				//origin.osveziSliku();
			}
			
		}
		//System.out.println(images.size());
		origin.osveziSliku();
	}
}
