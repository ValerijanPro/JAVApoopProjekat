package paket1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import selekcije.Pravougaonik;
import selekcije.Selekcija;
import java.lang.Math;



public class GUIImage extends Canvas implements ItemListener,ActionListener,MouseMotionListener,MouseListener{
	Map<Integer,BufferedImage> images=new HashMap();
	Map<Integer,Checkbox> listaVidljiva=new HashMap();
	Map<Integer,Checkbox> listaAktivne=new HashMap();
	ArrayList<Checkbox> aktivneSel=new ArrayList();
	Map<Integer,TextField> listaOpacitya=new HashMap();

	GUI origin;
	int width,height;
	Image slike=new Image();
	int brslojeva=0;
	Point pocetak,kraj;
	public GUIImage(GUI o) {
		origin=o;
		 //width=origin.getWidth();
		 width=1500;
		 //height=origin.getHeight();
		 height=1500;
		 setBackground(Color.lightGray);
		 addMouseMotionListener(this);
		 addMouseListener(this);
		
	}
	public void setPutanja(String s,int t ) {
		
		File f=null;
		//System.out.println(s);
		f=new File("C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\src\\"+s);
		BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		images.put(t,temp);
		Layer novi=new Layer(images.get(t).getWidth(),images.get(t).getHeight());
		//System.out.println("NOVI w:"+novi.getSirina()+", novi h:"+novi.getvisina());
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
	
	public void konstrFinLejer(String fajl) {
		ArrayList<Integer> aktivniLejeri=new ArrayList();
		for(Map.Entry<Integer, Checkbox> c:listaAktivne.entrySet()) {
			aktivniLejeri.add(c.getKey());
		}
		//IZMENA ORIDJIDJI
		kopirajBufferedUPraveLejere(aktivniLejeri);
		
		Layer finalni=slike.konstruisiFinalniLayer(aktivniLejeri);
		BufferedImage i=kopirajUBuffered(finalni);
		
		File f=new File("C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\src\\"+fajl);
		try {
			//System.out.println("pisemo"+finalni.getSirina()+", "+finalni.getvisina());
			System.out.println(ImageIO.write(i,"png",new File(fajl)));
			//ImageIO.write
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	private void kopirajBufferedUPraveLejere(ArrayList<Integer> aktivniLejeri) {
		for(int k:aktivniLejeri) {
			System.out.println("KA: "+k);
			Layer tren=slike.layers.get(k);
			BufferedImage tren1=images.get(k);
			for(int i=0;i<tren1.getWidth();i++) {
				for(int j=0;j<tren1.getHeight();j++) {
					char R,G,B,A;
					 A = (char)((tren1.getRGB(i, j) >> 24) & 0xff);
				     R = (char)((tren1.getRGB(i, j) >> 16) & 0xff);
				     G = (char)((tren1.getRGB(i, j) >> 8) & 0xff);
				     B = (char)((tren1.getRGB(i, j)) & 0xff);
					
				     tren.overwritepixel(i, j, new Piksel(R,G,B,0,A));
					
				}
			}
		}
		
	}
	private BufferedImage kopirajUBuffered(Layer f) {
		// TODO Auto-generated method stub
		BufferedImage fin=new BufferedImage(f.getSirina(),f.getvisina(),BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0;i<f.getSirina();i++) {
			for(int j=0;j<f.getvisina();j++) {
				char R,G,B,A;
				R=f.getPixel(i, j).getR();
				G=f.getPixel(i, j).getG();
				B=f.getPixel(i, j).getB();
				A=f.getPixel(i, j).getOpacity();

				int novo = (A << 24) | (R << 16) | (G << 8) | B;
				fin.setRGB(i, j, novo);
			
			}
		}
		return fin;
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
	public Panel dodajLejer(int t) {
		Panel p=new Panel();
		p.add(new Label("Sloj "+t));
		Checkbox vidljiv=new Checkbox("Vidljiv",true);
		Checkbox aktivan=new Checkbox("Aktivan",true);
		aktivan.addItemListener(this);
		vidljiv.addItemListener(this);
		listaVidljiva.put(t, vidljiv);
		listaAktivne.put(t, aktivan);
		//aktivan.addItemListener(origin);
		p.add(aktivan);
		p.add(vidljiv);
		TextField poljeZaTekst=new TextField("");
		poljeZaTekst.addActionListener(this);
		listaOpacitya.put(t, poljeZaTekst);
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
		//System.out.println("OSVEZENO CRTANJE POCINJE");
		//for(BufferedImage i:images)
			for(Map.Entry<Integer, Checkbox> c:listaVidljiva.entrySet()) {
				BufferedImage i=images.get(c.getKey());
				
				if(c.getValue().getState()==false  ) {
					
					continue;
				}
				else {
					
					//g.drawImage(i, 0, 0, 800, 600, null);
					g.drawImage(i, 0,0,  null);
				}
			}
			for(int i=0;i<origin.trenSelekcije.size();i++) {
				if(aktivneSel.get(i).getState()) {
					slike.getSelekcije().get(i).aktivna=true;
					g.setColor(Color.black);
					 Graphics2D asd = (Graphics2D) g.create();
					 Stroke d = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
					 asd.setStroke(d);
					asd.drawRect(
							origin.trenSelekcije.get(i).s.niz.get(0).getX(), 
							origin.trenSelekcije.get(i).s.niz.get(0).getY(), 
							origin.trenSelekcije.get(i).s.niz.get(0).getSirina(), 
							origin.trenSelekcije.get(i).s.niz.get(0).getVisina()
							);
				}
				else {
					slike.getSelekcije().get(i).aktivna=false;
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
		
		
		for(Map.Entry<Integer, TextField> c:listaOpacitya.entrySet()) {
			if(!c.getValue().getText().equals("")) {
				//System.out.println(c.getKey()+", "+c.getValue().getText());
				setOpacity(c.getKey(),c.getValue().getText());
				//origin.osveziSliku();
			}
			//System.out.println("asd");
			
		}
		//System.out.println(images.size());
		origin.osveziSliku();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		//Point end=e.getPoint();
		//System.out.println("X"+end.getX()+" Y"+end.getY());
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
		//Point start=e.getPoint();
		//System.out.println("X"+start.getX()+" Y"+start.getY());
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		pocetak=e.getPoint();
		
		//System.out.println(e.getPoint());
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		kraj=e.getPoint();
		Pravougaonik p;
		if(kraj.x<pocetak.x && kraj.y>pocetak.y) {
			p=new Pravougaonik(kraj.x,pocetak.y,
					Math.abs(kraj.x-pocetak.x),Math.abs(kraj.y-pocetak.y));
		}
		else if(kraj.x<pocetak.x && kraj.y<pocetak.y) {
			p=new Pravougaonik(kraj.x,kraj.y,
					Math.abs(kraj.x-pocetak.x),Math.abs(kraj.y-pocetak.y));
		}
		else if (pocetak.x<kraj.x && kraj.y<pocetak.y) {
			p=new Pravougaonik(pocetak.x,kraj.y,
					Math.abs(kraj.x-pocetak.x),Math.abs(kraj.y-pocetak.y));
		}
		else p=new Pravougaonik(pocetak.x,pocetak.y,
				Math.abs(kraj.x-pocetak.x),Math.abs(kraj.y-pocetak.y));
		ArrayList<Pravougaonik> te=new ArrayList();
//		te.add(new Pravougaonik(pocetak.x,pocetak.y,
//				Math.abs(kraj.x-pocetak.x),Math.abs(kraj.y-pocetak.y)));
		te.add(p);
		
		
		origin.trenSelekcije.add(new SelekcijeGUI(new Selekcija("asd",te)));
		origin.selekcije.setLayout(new GridLayout(origin.trenSelekcije.size()+1,1));
		
		slike.dodajSelekciju("asd", te, true);
		
		Panel ptemp=new Panel();
		ptemp.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).dodajGUIselekciju());
		ptemp.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).akt());
		aktivneSel.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).akt());
		aktivneSel.get(aktivneSel.size()-1).addItemListener(this);
		origin.selekcije.add(ptemp);
		origin.osveziSelekcije();
		repaint();
		System.out.println("Broj selekcija: "+origin.trenSelekcije.size());
		//System.out.println(e.getPoint());
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
}
