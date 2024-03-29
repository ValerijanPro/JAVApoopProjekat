package paket1;
//TODO: PAM, Sve sto je staticki (naziv slike itd itd), Medijana, Stream negde, Selekcije da rade, Aktivni lejeri, Greske
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


import Image.Image;
import Image.Layer;
import Image.Piksel;
import selekcije.Pravougaonik;
import selekcije.Selekcija;
import java.lang.Math;

//za XML:

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.tools.javac.util.Pair;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;

public class GUIImage extends Canvas implements ItemListener,ActionListener,MouseMotionListener,MouseListener{
	Map<Integer,BufferedImage> images=new HashMap();
	Map<Integer,Checkbox> listaVidljiva=new HashMap();
	Map<Integer,Checkbox> listaAktivne=new HashMap();
	ArrayList<Checkbox> aktivneSel=new ArrayList();
	Map<Integer,TextField> listaOpacitya=new HashMap();
	ArrayList<Pair<String,Integer>> listaOperacija=new ArrayList();
	Map<Integer, Button> brisanjeLejera=new HashMap();
	Map<Integer, Button> brisanjeSelekcija=new HashMap();
	Map<Integer,Button> brisanjeOperacija=new HashMap();
	Map<Integer,Button> bojenjeSelekcija=new HashMap();
	Map<Integer,Color> bojeSelekcija=new HashMap();
	
	public Color novaBoja;
	public int kojaSelNovaBoja;
	
	GUI origin;
	int width,height;
	Image slike=new Image();
	int brslojeva=0;
	Point pocetak,kraj;
	public int idSelekcije=0;  //sluzi za identifikaciju selekcija, da bi svaka imala jedinstveno ime
	String izlaznaPutanja;
	boolean postojiSlika=true;
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
		
		String regex = ".*\\.(.*)"; //sve iza tacke da uzmem
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if(matcher.matches()) {
			//System.out.println("BMPBMPBMPMP");
			String ekstenzija=matcher.group(1);
			ekstenzija=ekstenzija.toLowerCase();
			
			if(ekstenzija.equals("pam")) {
				//TODO:PAM OBRADA
				
				String cmd=
						"poopprojekat.exe "+
						//"C:\\Users\\Valja\\source\\repos\\poopprojekat\\poopprojekatGITHUB\\x64\\Release\\poopprojekat.exe "+
						"src\\"+s;
						//"C:\\Users\\Valja\\source\\repos\\poopprojekat\\poopprojekatGITHUB\\poopprojekat\\AS.BMP C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\temp.xml";
				Runtime runtime=Runtime.getRuntime();
				try {
					System.out.println("Poceo proces");
					Process process=runtime.exec(cmd);
					System.out.println("Zavrsen proces");
					process.waitFor();
				} catch (IOException | InterruptedException e) {
					
					e.printStackTrace();
				}
				
				ucitajBMP("javapam.bmp",t);
				
			}
			else if(ekstenzija.equals("bmp")) {
				ucitajBMP( s, t);
				
			}
			return;
		}
		
	}
	
	private void ucitajBMP(String s, int t) {
		File f=null;
		f=new File("src\\"+s);
		if(!f.exists()) {
			postojiSlika=false;
			origin.dijalogZaOpomene.labela.setText("Data slika ne postoji");
			origin.dijalogZaOpomene.setVisible(true);
			return;
		}
		postojiSlika=true;
		BufferedImage temp;
		try {
			temp=ImageIO.read(f);
			zastoNeRadi(temp,t);
			Layer novi=new Layer(images.get(t).getWidth(),images.get(t).getHeight());
			kopirajLejer(novi,t);
			//ImageIO.write(image,"bmp",f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void dodajOperaciju(String koja,int operand) {
		
		listaOperacija.add(new Pair(koja,operand));
		brisanjeOperacija.put(brisanjeOperacija.size(), origin.trenOperacije.get(origin.trenOperacije.size()-1).brisanje);
	}
	public void napraviXMLizlazni(String fajl) {
		DocumentBuilderFactory docFact=DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder docBuild=docFact.newDocumentBuilder();
			Document xmlDOC=docBuild.newDocument();
			
			Element root=xmlDOC.createElement("Image");
			root.setAttribute("Visina", ((Integer)slike.getvisina()).toString());
			root.setAttribute("Sirina", ((Integer)slike.getSirina()).toString());
			
			//layers
			Element layers=xmlDOC.createElement("Layers");
			for(Map.Entry<Integer, Layer> l:slike.getLayers().entrySet()) {
				Element layer=xmlDOC.createElement("Layer");
				layer.setAttribute("Sirina", ((Integer)l.getValue().getSirina()).toString());
				layer.setAttribute("Visina", ((Integer)l.getValue().getvisina()).toString());
				layer.setAttribute("ID", ((Integer)l.getKey()).toString());
				for(int i=0;i<l.getValue().getSirina();i++) {
				//for(int i=0;i<10;i++) {
					for(int j=0;j<l.getValue().getvisina();j++) {
					//for(int j=0;j<10;j++) {
						Piksel p=l.getValue().getPixel(i, j);
						Element pixel=xmlDOC.createElement("Piksel");
						int r,g,b,o,of;
						r=p.getR();
						g=p.getG();
						b=p.getB();
						o=p.getOpacity();
						of=p.getOffset();
						pixel.setAttribute("R", ((Integer)r).toString());
						pixel.setAttribute("G", ((Integer)g).toString());
						pixel.setAttribute("B", ((Integer)b).toString());
						pixel.setAttribute("Opacity", ((Integer)o).toString());
						pixel.setAttribute("Offset", ((Integer)of).toString());
						pixel.setAttribute("I", ((Integer)j).toString());
						pixel.setAttribute("J", ((Integer)i).toString());
						layer.appendChild(pixel);
						
					}
				}
				layers.appendChild(layer);
			}
			root.appendChild(layers);
			
			//gotovi layers
			
			//nepotrebno ne znam cemu ovo
			Element aktivne=xmlDOC.createElement("Aktivne");
			root.appendChild(aktivne);
			//OK...
			
			//SELEKCIJE
			Element selekcije=xmlDOC.createElement("Selekcije");
			for(Selekcija s:slike.sel) {
				Element selekcija=xmlDOC.createElement("Selekcija");
				selekcija.setAttribute("Ime", s.getIme());
				
				if(s.getStanje()) {
					selekcija.setAttribute("Aktivna", "true");
				}
				else {
					selekcija.setAttribute("Aktivna", "false");
				}
				
				for(Pravougaonik p:s.getNiz()) {
					Element pravougaonik = xmlDOC.createElement("Pravougaonik");
					pravougaonik.setAttribute("X", ((Integer)p.getX()).toString());
					pravougaonik.setAttribute("Y", ((Integer)p.getY()).toString());
					pravougaonik.setAttribute("sirina", ((Integer)p.getSirina()).toString());
					pravougaonik.setAttribute("visina", ((Integer)p.getVisina()).toString());
					selekcija.appendChild(pravougaonik);
				}
				
				selekcije.appendChild(selekcija);
				
			}
			root.appendChild(selekcije);
			//GOTOVE SELEKCIJE
			
			
			//operacije:
			Element rootZaOperacije=xmlDOC.createElement("Operacije");
			Element op=xmlDOC.createElement("Operacija");

			for(Pair<String,Integer> p:listaOperacija) {
				Element oper=xmlDOC.createElement("Operacija");
				oper.setAttribute("ImeOperacije", "class "+p.fst);
				oper.setAttribute("Value", p.snd.toString());
				rootZaOperacije.appendChild(oper);
			}
			root.appendChild(rootZaOperacije);
			//gotoveOperacije:
			
			xmlDOC.appendChild(root);
			
			//xmlDOC.appendChild(rootZaOperacije);
			
			
			File xmlFile=new File("src\\"+fajl+".xml");
			TransformerFactory transF=TransformerFactory.newDefaultInstance();
			try {
				Transformer trans=transF.newTransformer();
				trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				trans.setOutputProperty(OutputKeys.INDENT, "yes");
				trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				DOMSource domSo=new DOMSource(xmlDOC);
				StreamResult strRes=new StreamResult(xmlFile);
				
				trans.transform(domSo,strRes);
				
			} catch (TransformerException e) {
				
				e.printStackTrace();
			}
			

			
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} 
		
	}
	private String parsirajIme(String fajl) {
		String regex = "(.*)\\..*"; //sve iza tacke da uzmem
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fajl);
		if(matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}
	public void konstrFinLejer(String fajl,int tip) {
		
		ArrayList<Integer> aktivniLejeri=new ArrayList();
		for(Map.Entry<Integer, Checkbox> c:listaAktivne.entrySet()) {
			if(c.getValue().getState())
				aktivniLejeri.add(c.getKey());
		}
		//IZMENA ORIDJIDJI
		kopirajBufferedUPraveLejere(aktivniLejeri);
		
		Layer finalni=slike.konstruisiFinalniLayer(aktivniLejeri);
		
		slike.layers.clear();
		slike.layers.put(1, finalni);

		
		//for(int i=0;i<origin.trenSelekcije.size();i++) {
			//slike.dodajSelekciju("", origin.trenSelekcije.get(i).s.getNiz(), aktivneSel.get(i).getState());
		//}
		
		BufferedImage i=kopirajUBuffered(finalni);
		
		if(tip==1) fajl+=".bmp";
		else if(tip==2) fajl+=".pam";
		else fajl+=".xml";
		
		File f=new File("src\\"+fajl);
		izlaznaPutanja=fajl;
		try {
			//System.out.println("pisemo"+finalni.getSirina()+", "+finalni.getvisina());
			//ImageIO.write(i,"bmp",new File(fajl));
			ImageIO.write(i,"bmp",f);
			//System.out.println(i.getType());
			//ImageIO.write
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		//if(listaOperacija.size()!=0 || aktivneSel.size()!=0) {
			fajl=parsirajIme(fajl);
			napraviXMLizlazni(fajl);
			if(tip==3) return;
			CEOsaljiUCPP(fajl,tip);
		//}
		//nek udje u file manaager i nek otvori, necu da prikazujem odmah posle obrade
		
		isprazniSve();
		if(tip==1) fajl+=".BMP";
		else fajl+=".PAM";
		izlaznaPutanja=fajl;
		ubaciNovuSliku();
	}
	
	private void isprazniSve() {
		
		images.clear();
		listaVidljiva.clear();
		listaAktivne.clear();
		aktivneSel.clear();
		listaOpacitya.clear();
		listaOperacija.clear();
		origin.trenOperacije.clear();
		origin.trenSelekcije.clear();
		brisanjeLejera.clear();
		brisanjeSelekcija.clear();
		bojenjeSelekcija.clear();
		bojeSelekcija.clear();
		for(int i=origin.panLejeri.getComponentCount()-1;i>=1;i--) {
			origin.panLejeri.remove(i);
		}
		for(int i=origin.panSelekcije.getComponentCount()-1;i>=1;i--) {
			origin.panSelekcije.remove(i);
		}
		for(int i=origin.panOperacije.getComponentCount()-1;i>=1;i--) {
			origin.panOperacije.remove(i);
		}
		origin.redniBrSelekcije.clear();
		origin.redniBrSloja.clear();
		
		
		
		
		
		
		
	}
	private void ubaciNovuSliku() {
		
		origin.putanja=izlaznaPutanja;
		origin.temp=1;
		origin.dodajSliku();
		//origin.
		
	}
	private void saljiUCPP() {
		//napraviXMLizlazni();
		
		String cmd=
				"poopprojekat.exe "+
				//"C:\\Users\\Valja\\source\\repos\\poopprojekat\\poopprojekatGITHUB\\x64\\Release\\poopprojekat.exe "+
				"src\\AS.BMP src\\temp.xml";
				//"C:\\Users\\Valja\\source\\repos\\poopprojekat\\poopprojekatGITHUB\\poopprojekat\\AS.BMP C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\temp.xml";
		Runtime runtime=Runtime.getRuntime();
		try {
			System.out.println("Poceo proces");
			Process process=runtime.exec(cmd);
			System.out.println("Zavrsen proces");
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	private void CEOsaljiUCPP(String fajl,int tip) {
		//napraviXMLizlazni();
		//TODO: NE SME DA OSTANE AS.BMP NEGO PROSLEDI STRING KOJI JE UNEO
		String ekst;
		if(tip==1) ekst=".BMP";
		else ekst=".PAM";
		String cmd=
				"poopprojekat.exe "+
				//"C:\\Users\\Valja\\source\\repos\\poopprojekat\\poopprojekatGITHUB\\x64\\Release\\poopprojekat.exe "+
				"src\\"+fajl+ekst+" src\\"+fajl+".xml";
	
				//"C:\\Users\\Valja\\source\\repos\\poopprojekat\\poopprojekatGITHUB\\poopprojekat\\AS.BMP C:\\Users\\Valja\\source\\repos\\poopprojekat\\JAVApoopProjekat\\temp.xml";
		Runtime runtime=Runtime.getRuntime();
		try {
			System.out.println("Poceo proces");
			Process process=runtime.exec(cmd);
			System.out.println("Zavrsen proces");
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			
			e.printStackTrace();
		}
		origin.dijalogSacuvaj.setVisible(false);
		if(origin.dijalogIzlaz.isVisible()) origin.dispose();
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
		
		BufferedImage fin=new BufferedImage(f.getSirina(),f.getvisina(),BufferedImage.TYPE_INT_RGB);
		
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
		//BufferedImage temp2=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		BufferedImage temp2=new BufferedImage(temp.getWidth(),temp.getHeight(),BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<temp.getWidth();i++) {
			for(int j=0;j<temp.getHeight();j++) {
				char R,G,B,A;
				 temp2.setRGB(i, j, temp.getRGB(i, j)); 
			    
				
			}
		}
		images.put(t, temp2);
	}
	public Panel dodajLejer(int t) {
		Panel p2=new Panel();
		p2.setLayout(new BorderLayout());
		Button brisanje=new Button("Obrisi lejer");
		brisanje.addActionListener(this);
		//p2.add(brisanje,BorderLayout.SOUTH);
		
		brisanjeLejera.put(t, brisanje);
		
		Panel p=new Panel();
		p.add(new Label("Sloj "+t));
		Checkbox vidljiv=new Checkbox("Vidljiv",true);
		Checkbox aktivan=new Checkbox("Aktivan",true);
		aktivan.addItemListener(this);
		vidljiv.addItemListener(this);
		listaVidljiva.put(t, vidljiv);
		listaAktivne.put(t, aktivan);
		
		p.add(aktivan);
		p.add(vidljiv);
		TextField poljeZaTekst=new TextField("");
		poljeZaTekst.addActionListener(this);
		listaOpacitya.put(t, poljeZaTekst);
		p.add(poljeZaTekst);
		p.add(new Label("opacity"));
		
		p2.add(p,BorderLayout.CENTER);
		//p2.add(p);
		p2.add(brisanje,BorderLayout.SOUTH);
		brslojeva++;
		return p2;
	}
	
	private void kopirajLejer(Layer novi,int t) {
		
		
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
				
			}
		}
		brslojeva++;
		slike.DodajSloj(novi, t);
		//slike.DodajSloj(novi, brslojeva++);
		//System.out.println("Trenutno slojeva: "+(brslojeva));
	}
	private TreeMap<Integer, Checkbox> sortirajOpadajucePoKljucu() {
		
		TreeMap<Integer, Checkbox> t=new TreeMap<Integer,Checkbox>(Collections.reverseOrder());
		t.putAll(listaVidljiva);
		return t;
	}
	public void paint(Graphics g) {
		//System.out.println("OSVEZENO CRTANJE POCINJE");
		//for(BufferedImage i:images)
			listaVidljiva=sortirajOpadajucePoKljucu();
			
			listaVidljiva.entrySet().stream()   //entrySet -> lista tuplova
			.filter(l->l.getValue().getState())   //filtriramo sve ciji State nije aktivan (njih izbacujemo)
			.map(l->images.get(l.getKey())) //za svaki tupli u listi, mapiraj ga (konvertuj) u buff image. nastaje strim buffered image-a
			.forEach(i->g.drawImage(i, 0,0,  null));   //za svaki buffered image, pozovem draw image
			
//			for(Map.Entry<Integer, Checkbox> c:listaVidljiva.entrySet()) {
//				
//				
//				if(c.getValue().getState()==false  ) {
//					
//					continue;
//				}
//				
//					BufferedImage i=images.get(c.getKey());
//					
//					g.drawImage(i, 0,0,  null);
//				
//			}
			
			
			for(int i=0;i<origin.trenSelekcije.size();i++) {
				if(aktivneSel.get(i).getState()) {
					slike.getSelekcije().get(i).aktivna=true;
					if(bojeSelekcija.containsKey(i)) {
						g.setColor(bojeSelekcija.get(i));
						g.fillRect(
								origin.trenSelekcije.get(i).s.niz.get(0).getX(), 
								origin.trenSelekcije.get(i).s.niz.get(0).getY(), 
								origin.trenSelekcije.get(i).s.niz.get(0).getSirina(), 
								origin.trenSelekcije.get(i).s.niz.get(0).getVisina()
								);
						
					}
					else {
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
					
				}
				else {
					slike.getSelekcije().get(i).aktivna=false;
				}
			}
			
	}
	
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
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
		for(Map.Entry<Integer, Button> c:brisanjeLejera.entrySet()) {
		//	if(c.getValue().getActionCommand()=="Obrisi lejer") {
			if(c.getValue()==e.getSource()) {
				//System.out.println("OBRISISISISI");
				obrisiLejer(c.getKey(),c.getValue());
				break;
			}
		}
		for(Map.Entry<Integer, Button> c:brisanjeSelekcija.entrySet()) {
			//	if(c.getValue().getActionCommand()=="Obrisi lejer") {
				if(c.getValue()==e.getSource()) {
					//System.out.println("OBRISISISISI");
					obrisiSelekciju(c.getKey(),c.getValue());
					break;
				}
		}
		
		for(Map.Entry<Integer, Button> c:brisanjeOperacija.entrySet()) {
			//	if(c.getValue().getActionCommand()=="Obrisi lejer") {
				if(c.getValue()==e.getSource()) {
					//System.out.println("OBRISISISISI");
					obrisiOperaciju(c.getKey(),c.getValue());
					break;
				}
		}
		for(Map.Entry<Integer, Button> c:bojenjeSelekcija.entrySet()) {
			//	if(c.getValue().getActionCommand()=="Obrisi lejer") {
				if(c.getValue()==e.getSource()) {
					//System.out.println("OBRISISISISI");
					obojSelekciju(c.getKey(),c.getValue());
					break;
				}
		}
		
		//System.out.println(images.size());
		origin.osveziSliku();
	}

	private void obojSelekciju(Integer key, Button value) {
		origin.dijalogZaBojenje.setVisible(true);
		kojaSelNovaBoja=key;
	}
	public void stvarnoObojiSelekciju() {
		
		bojeSelekcija.put(kojaSelNovaBoja, novaBoja);
		repaint();
	}
	private void obrisiOperaciju(Integer key, Button value) {
		
		brisanjeOperacija.remove(key);
		
		origin.obrisiOperaciju(key);
		
	}
	private void obrisiSelekciju(Integer key,Button dugm) {
		//System.out.println("KEY="+key);
		bojeSelekcija.remove(key);
		bojenjeSelekcija.remove(key);
		brisanjeSelekcija.remove(key);
		//System.out.println("SIZE:"+slike.getSelekcije().size());
		slike.ObrisiSelekciju(key);
		//System.out.println("SIZE:"+slike.getSelekcije().size());
		
//		origin.trenSelekcije.remove((int)key);
		for(int i=0;i<origin.trenSelekcije.size();i++) {
			if(origin.trenSelekcije.get(i).dugme==dugm) {
				origin.trenSelekcije.remove(origin.trenSelekcije.get(i));
				break;
			}
		}
		for(int i=0;i<origin.redniBrSelekcije.size();i++) {
			if(origin.redniBrSelekcije.get(i)==key){
				origin.panSelekcije.remove(i+1);
				origin.redniBrSelekcije.remove(i);
				aktivneSel.remove((int)i);
				break;
			}
		}
		//origin.panSelekcije.remove(key+1);
		
	}
	private void obrisiLejer(Integer key, Button value) {
		
		brisanjeLejera.remove(key, value);
		listaVidljiva.remove(key);
	
		listaAktivne.remove(key);
		listaOpacitya.remove(key);
		brslojeva--;
		origin.osveziObrisaneLejere(key);
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
		
		//System.out.println(origin.trenOperacije.stream().allMatch(g->{return (g instanceof GUIoperacije);}));
		
		//SVE SA LABA ZIZA:  *U setPutanja imam regex kako se koristi vec
		//REGEX1: Pattern p=Pattern.compile("^(.*);(.*);(.*);$");
		//REGEX katastrofalni: Pattern p=Pattern.compile("^\\[\"([^\\[]*).*# ([^\\]]*)\\].*(.)\\];$");
		 //                           otvorena uglasta,  navodnici,  sve []* sto nije [  , posle tarabe sve sto nije ], karakter pred ]
		
		
		//ISPIS:
		//listaOperacija.values().stream().forEach(l->{System.out.println(l);});
		//ILI
		//listaOperacija.values().stream().forEach(System.out::println);
		//ako nestaticku:
		//listaOperacija.values().stream().forEach(this::funkcija);
		
		//Obrada u jednoj liniji:  Da je to linija cija su sva stajalista u datoj zoni, a da je najduza od svih (ima najvise stajlista)
//		Optional<Linija> rezultat=linije.values().stream().
//			filter(l->l.stajalista().stream().allMatch(s1->s1.zona==zona)).
//			max((l1,l2)->l1.stajalista().size()-l2.stajalista().size());
//		if(rezultat.isPresent()) {
//			System.out.println(rezultat.get())	;
//		}
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
		if(pocetak==null) return;
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
		
		Selekcija nova=new Selekcija(((Integer)idSelekcije).toString(),te);
		idSelekcije++;
		origin.trenSelekcije.add(new SelekcijeGUI(nova));
		origin.panSelekcije.setLayout(new GridLayout(origin.trenSelekcije.size()+1,1));
		
		slike.dodajSelekciju(nova);
		
		Panel ceo=new Panel();
		ceo.setLayout(new BorderLayout());
		Panel zaBoju=new Panel();
		
		Panel ptemp=new Panel();
		ptemp.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).dodajGUIselekciju());
		ptemp.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).akt());
		ptemp.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).dug());
		//ptemp.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).oboj());
		zaBoju.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).oboj());
		ceo.add(ptemp,BorderLayout.CENTER);
		ceo.add(zaBoju,BorderLayout.SOUTH);
		
		aktivneSel.add(origin.trenSelekcije.get(origin.trenSelekcije.size()-1).akt());
		aktivneSel.get(aktivneSel.size()-1).addItemListener(this);
		brisanjeSelekcija.put(brisanjeSelekcija.size(), origin.trenSelekcije.get(origin.trenSelekcije.size()-1).dug());
		brisanjeSelekcija.get(brisanjeSelekcija.size()-1).addActionListener(this);
		origin.panSelekcije.add(ceo);
		origin.redniBrSelekcije.add(idSelekcije-1);
		bojenjeSelekcija.put(bojenjeSelekcija.size(),origin.trenSelekcije.get(origin.trenSelekcije.size()-1).oboj());
		bojenjeSelekcija.get(bojenjeSelekcija.size()-1).addActionListener(this);
		
		origin.osveziSelekcije();
		repaint();
		//System.out.println("Broj selekcija: "+origin.trenSelekcije.size());
		//System.out.println(e.getPoint());
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
	public void Redosled() {
//		for(Map.Entry<String, Integer> o:listaOperacija.entrySet()) {
//			System.out.println(o.getKey());
//			System.out.println(o.getValue());
//			System.out.println();
//		}
		
	}
	
}
