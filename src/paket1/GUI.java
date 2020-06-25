package paket1;
import java.awt.*;
import java.awt.event.*;
import java.util.*; 
import selekcije.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class GUI extends Frame implements ActionListener,ItemListener{
//BufferedImage imageIO.read
	ArrayList<SelekcijeGUI> trenSelekcije=new ArrayList();
	ArrayList<GUIoperacije> trenOperacije=new ArrayList();
	GUIImage gimage;
	String putanja;
	private DijalogZaUnos dijalogDodavanje;
	private DijalogZaIspis dijalogSacuvaj;
	private DijalogZaOperacije dijalogOperacije;
	//TODO: TEMP JE SAMO DOK NE STAVIM FILECHOOSER, PA CU BROJ GDE UPISUJEM LAYER DRUGACIJE SLATI
	int temp=0;
	
	
	Panel panLejeri;
	Panel panSelekcije;
	Panel panOperacije;
	
	class DijalogZaIspis extends Dialog implements ActionListener{
		TextField poljeZaTekst=new TextField("Uneti ime izlaznog fajla ovde.");
		
		GUI cale;
		DijalogZaIspis(GUI roditelj) {
			super(roditelj,"Dijalog",false);
			cale=roditelj;
			setSize(300,300);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {setVisible(false);} 
			});
			dodajKomponente();
		}

		private void dodajKomponente() {
			// TODO Auto-generated method stub
			//setLayout(new GridLayout(3,1));

			
			add(poljeZaTekst);
			
			poljeZaTekst.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String tekst=poljeZaTekst.getText();
			//System.out.println("DESILO SE NESTO");
			if(!tekst.equals("") && !tekst.equals("Uneti ime izlaznog fajla ovde.")) {
				cale.gimage.konstrFinLejer(tekst);
				//cale.putanja=tekst;
				//System.out.println("Putanja nova "+cale.putanja);
				//cale.dodajSliku();
				//poljeZaTekst.setText("");
				//System.out.println("test");
			}
			
			//System.out.println("asd");
			
			
		}
	}
	class DijalogZaUnos extends Dialog implements ActionListener{
		TextField poljeZaTekst=new TextField("Uneti tekst ovde.");
		TextField poljeZaBroj=new TextField("Pozicija na koju upisujemo layer: ");
		GUI cale;
		DijalogZaUnos(GUI roditelj) {
			super(roditelj,"Dijalog",false);
			cale=roditelj;
			setSize(300,300);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {setVisible(false);} 
			});
			dodajKomponente();
		}

		private void dodajKomponente() {
			// TODO Auto-generated method stub
			setLayout(new GridLayout(3,1));
			Label labela=new Label("Ime slike sa ekstenzijom: ");
			add(labela);
			add(poljeZaBroj);
			add(poljeZaTekst);
			poljeZaBroj.addActionListener(this);
			poljeZaTekst.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String tekst=poljeZaTekst.getText();
			System.out.println("DESILO SE NESTO");
			if(!tekst.equals("") && !tekst.equals("Uneti tekst ovde.")) {
				
				cale.putanja=tekst;
				System.out.println("Putanja nova "+cale.putanja);
				cale.dodajSliku();
				//poljeZaTekst.setText("");
				//System.out.println("test");
			}
			tekst=poljeZaBroj.getText();
			if(!tekst.equals("")) {
				cale.temp=Integer.parseInt(tekst);
				//poljeZaBroj.setText("");
			}
			//System.out.println("asd");
			
			
		}
	}
	class DijalogZaOperacije extends Dialog implements ItemListener{
		Label poljeZaTekst=new Label("Izaberite operaciju koju zelite da primenite: ");
		Choice izbor=new Choice();
		GUI cale;
		DijalogZaOperacije(GUI roditelj) {
			super(roditelj,"Dijalog",false);
			cale=roditelj;
			setSize(300,300);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {setVisible(false);} 
			});
			dodajKomponente();
		}

		private void dodajKomponente() {
	
			setLayout(new GridLayout(2,1));
			add(poljeZaTekst);
			dodajOper();
			add(izbor);
			izbor.addItemListener(this);
			
		}

		private void dodajOper() {
			izbor.add("Apsolutna vrednost");
			izbor.add("add");
			izbor.add("CrnoBela");
			izbor.add("Div");
			izbor.add("DivInvert");
			izbor.add("Inverzija");
			izbor.add("log");
			izbor.add("max");
			izbor.add("min");
			izbor.add("mul");
			izbor.add("pow");
			izbor.add("siva");
			izbor.add("sub");
			izbor.add("subInvert");
			izbor.add("Medijana");
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			String tekst=izbor.getSelectedItem();
			if(tekst=="Apsolutna vrednost") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(1);
			}
			else if(tekst=="add") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(2);
			} 
			else if(tekst=="CrnoBela") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(3);
			} 
			else if(tekst=="Div") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(4);
			} 
			else if(tekst=="DivInvert") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(5);
			} 
			else if(tekst=="Inverzija") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(6);
			} 
			else if(tekst=="log") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(7);
			} 
			else if(tekst=="max") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(8);
			} 
			else if(tekst=="min") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(9);
			} 
			else if(tekst=="mul") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(10);
			} 
			else if(tekst=="pow") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(11);
			} 
			else if(tekst=="siva") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(12);
			} 
			else if(tekst=="sub") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(13);
			} 
			else if(tekst=="subInvert") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(14);
			} 
			else if(tekst=="Medijana") {
				cale.trenOperacije.add(new GUIoperacije(tekst));
				cale.gimage.dodajOperaciju(15);
			} 
			cale.dodajOperaciju(); //osvezni panel sa operacijama
			
		}

		
		
	}
	public GUI() {
		
		super("Slike");
		addWindowListener(new WindowAdapter(){  
	          public void windowClosing(WindowEvent e) {  
	        	 
	              dispose();  
	         }   
	     });
		//dodajScenu();
		
		dijalogDodavanje=new DijalogZaUnos(this);
		dijalogSacuvaj=new DijalogZaIspis(this);
		dijalogOperacije=new DijalogZaOperacije(this);
		setSize(3000,3000);
		gimage=new GUIImage(this);
		
		dodajMenije();
		dodajLeviDeo();
		dodajOperacije();
		//dodajSliku();
		setVisible(true);
		
	}

	private void dodajMenije() {
		// TODO Auto-generated method stub
		MenuBar traka=new MenuBar();
		Menu dodavanje=new Menu("Dodavanje slike");
		Menu sacuvaj=new Menu("Sacuvaj sliku");
		Menu operacije=new Menu("Operacije");
		operacije.add("Dodaj operaciju");
		operacije.addActionListener(this);
		sacuvaj.add("Sacuvaj sliku");
		sacuvaj.addActionListener(this);
		dodavanje.add("Dodaj novu sliku");
		dodavanje.addActionListener(this);
		traka.add(dodavanje);
		traka.add(sacuvaj);
		traka.add(operacije);
		setMenuBar(traka);
	}

	private void dodajSliku() {
		// TODO Auto-generated method stub
		gimage.setPutanja(putanja,temp);
		add(gimage,BorderLayout.CENTER);
		panLejeri.setLayout(new GridLayout(gimage.brslojeva+1,1));
		panLejeri.add(gimage.dodajLejer(temp));
		revalidate();
		
		//gimage.paint(getGraphics());
		
	}

	private void dodajOperacije() {
		// TODO Auto-generated method stub
		panOperacije=new Panel();
		panOperacije.setBackground(Color.LIGHT_GRAY);
		Font myFont = new Font("Times New Roman", Font.BOLD, 24);
		
		Label operacije=new Label("Operacije");
		operacije.setFont(myFont);
		panOperacije.add(operacije);
		add(panOperacije,BorderLayout.EAST);
	}
	public void dodajOperaciju() {
		panOperacije.setLayout(new GridLayout(trenOperacije.size()+2,1));
		panOperacije.add(trenOperacije.get(trenOperacije.size()-1).dodajGUIoperaciju());
		//remove(panOperacije);
		//add(panOperacije,BorderLayout.EAST);
		revalidate();
		//panOperacije.add(comp)
	}
	private void dodajLeviDeo() {
		ArrayList<Pravougaonik> temp=new ArrayList();
		temp.add(new Pravougaonik(222,136,180,110));
		//trenSelekcije.add(new SelekcijeGUI(new Selekcija("asd",temp)));
		// TODO Auto-generated method stub
		Panel p=new Panel();
		panLejeri=new Panel();
		panLejeri.setLayout(new GridLayout());
		panSelekcije=new Panel();
		p.setLayout(new GridLayout(2,1));
		
		Font myFont = new Font("Times New Roman", Font.BOLD, 24);
		
		Label slojevi=new Label("Layers");
		slojevi.setFont(myFont);
		slojevi.setSize(50, 50);
		//slojevi.setBackground(Color.LIGHT_GRAY);
		panLejeri.add(slojevi); //labela Layers
		panLejeri.setBackground(Color.LIGHT_GRAY);
		
		
		
		Label selekcijeL=new Label("Selekcije");
		selekcijeL.setFont(myFont);
		selekcijeL.setSize(50, 50);
		//selekcije.setBackground(Color.LIGHT_GRAY);
		panSelekcije.setLayout(new GridLayout(trenSelekcije.size()+1,1));
		panSelekcije.add(selekcijeL);
		panSelekcije.setBackground(Color.LIGHT_GRAY);
		
		for(int i=0;i<trenSelekcije.size();i++) {
			trenSelekcije.get(i).aktivna.addItemListener(this);
			Panel ptemp=new Panel();
			ptemp.add(trenSelekcije.get(i).dodajGUIselekciju());
			ptemp.add(trenSelekcije.get(i).akt());
			panSelekcije.add(ptemp);
		}
		
		p.add(panLejeri);
		p.add(panSelekcije);
		add(p,BorderLayout.WEST);
		
	
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
//		for(int i=0;i<trenSelekcije.size();i++) {
//			if(trenSelekcije.get(i).aktivna.getState()) {
//				//System.out.println("postala");
//			}
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String komanda=e.getActionCommand();
		if(komanda.equals("Dodaj novu sliku")) dijalogDodavanje.setVisible(true);
		else if (komanda.equals("Sacuvaj sliku")) {
			dijalogSacuvaj.setVisible(true);
		}
		else if (komanda.equals("Dodaj operaciju")) {
			dijalogOperacije.setVisible(true);
		}
	}

	public void osveziSelekcije() {
		//remove(selekcije);
		
		
		//add(selekcije);
		revalidate();
		
	}
	public void osveziSliku() {
		
		remove(gimage);
		add(gimage,BorderLayout.CENTER);
		//lejeri.setLayout(new GridLayout(gimage.brslojeva+1,1));
		//lejeri.add(gimage.dodajLejer());
		//revalidate();
	}
	
}
