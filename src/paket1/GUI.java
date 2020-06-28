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
	
	ArrayList<Integer>redniBrSloja=new ArrayList();
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
			
			//setLayout(new GridLayout(3,1));

			
			add(poljeZaTekst);
			
			poljeZaTekst.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String tekst=poljeZaTekst.getText();
			//System.out.println("DESILO SE NESTO");
			if(!tekst.equals("") && !tekst.equals("Uneti ime izlaznog fajla ovde.")) {
				cale.gimage.konstrFinLejer(tekst); //SAVE POZOVI
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
			
			String tekst=poljeZaTekst.getText();
			System.out.println("DESILO SE NESTO");
			if(!tekst.equals("") && !tekst.equals("Uneti tekst ovde.")) {
				
				cale.putanja=tekst;
				//System.out.println("Putanja nova "+cale.putanja);
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
	class DijalogZaOperacije extends Dialog implements ItemListener,ActionListener{
		Label poljeZaTekst=new Label("Izaberite operaciju koju zelite da primenite: ");
		Choice izbor=new Choice();
		TextField operand=new TextField("Operand: ");
		Button dugme=new Button("Dodaj");
		GUI cale;
		String koja;
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
			Panel p=new Panel();
			setLayout(new GridLayout(3,1));
			add(poljeZaTekst);
			dodajOper();
			p.add(izbor);
			p.add(operand);
			add(p);
			add(dugme);
			izbor.addItemListener(this);
			operand.addActionListener(this);
			operand.setEnabled(false);
			dugme.addActionListener(this);
		}

		private void dodajOper() {
			izbor.add("Apsolutna vrednost");
			izbor.add("Add");
			izbor.add("Crnobela");
			izbor.add("Div");
			izbor.add("DivInvert");
			izbor.add("Inverzija");
			izbor.add("Log");
			izbor.add("Max");
			izbor.add("Min");
			izbor.add("Mul");
			izbor.add("Pow");
			izbor.add("Siva");
			izbor.add("Sub");
			izbor.add("SubInvert");
			izbor.add("Medijana");
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
		//	System.out.println("ITEM CHANGED");
			String tekst=izbor.getSelectedItem();
		//	System.out.println(tekst);
			koja=tekst;
			if(tekst=="Apsolutna vrednost") {
				operand.setEnabled(false);
				
			}
			else if(tekst=="Add") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="Crnobela") {
				operand.setEnabled(false);
				
			} 
			else if(tekst=="Div") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="DivInvert") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="Inverzija") {
				operand.setEnabled(false);
				
			} 
			else if(tekst=="Log") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="Max") {
				operand.setEnabled(false);
				
			} 
			else if(tekst=="Min") {
				operand.setEnabled(false);
				
			} 
			else if(tekst=="Mul") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="Pow") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="Siva") {
				operand.setEnabled(false);
				
			} 
			else if(tekst=="Sub") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="SubInvert") {
				operand.setEnabled(true);
				
			} 
			else if(tekst=="Medijana") {
				operand.setEnabled(false);
				
			} 
			//cale.dodajOperaciju();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//System.out.println("AKCIJA");
			String tek=operand.getText();
			
			String tekst=izbor.getSelectedItem();
			koja=tekst;
			if(e.getActionCommand()=="Dodaj") {
				if(tekst=="Apsolutna vrednost") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				}
				else if(tekst=="Add") {
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Crnobela") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Div") {
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="DivInvert") {
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Inverzija") {
				
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Log") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
				} 
				else if(tekst=="Max") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Min") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Mul") {
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Pow") {
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Siva") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Sub") {
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="SubInvert") {
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Medijana") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				}
				cale.dodajOperaciju(); //osvezni panel sa operacijama
				
			}
			
			
			
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
	
	public void dodajSliku() {
		
		gimage.setPutanja(putanja,temp);
		add(gimage,BorderLayout.CENTER);
		osveziDodateLejere();
		//for(Integer i:redniBrSloja)System.out.println("i:"+i);
		//System.out.println();
		//gimage.paint(getGraphics());
		
	}

	public void osveziDodateLejere() {
		panLejeri.setLayout(new GridLayout(gimage.brslojeva+1,1));
		
		panLejeri.add(gimage.dodajLejer(temp));
		redniBrSloja.add(temp);
		revalidate();
	}
	public void osveziObrisaneLejere(int x) {
		System.out.println("x="+x);
		panLejeri.setLayout(new GridLayout(gimage.brslojeva+1,1));
		for(int i=redniBrSloja.size()-1;i>=0;i--) {
			if(redniBrSloja.get(i)==x) {
				System.out.println("i="+i);
				panLejeri.remove(i+1);
				redniBrSloja.remove(i);
				break;
			}
		}
		//revalidate();
	}
	private void dodajOperacije() {
		
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
		
//		for(int i=0;i<trenSelekcije.size();i++) {
//			if(trenSelekcije.get(i).aktivna.getState()) {
//				//System.out.println("postala");
//			}
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
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
