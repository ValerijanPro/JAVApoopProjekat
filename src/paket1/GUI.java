package paket1;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public DijalogZaUnos dijalogDodavanje;
	public DijalogZaIspis dijalogSacuvaj;
	public DijalogZaOperacije dijalogOperacije;
	public DijalogZaIzlaz dijalogIzlaz;
	public DijalogZaRIPGreske dijalogZaRIPGreske;
	public DijalogZaOpomene dijalogZaOpomene;
	public DijalogZaBojenje dijalogZaBojenje;
	//TODO: TEMP JE SAMO DOK NE STAVIM FILECHOOSER, PA CU BROJ GDE UPISUJEM LAYER DRUGACIJE SLATI
	int temp=0;
	
	
	Panel panLejeri;
	ArrayList<Integer>redniBrSelekcije=new ArrayList();
	ArrayList<Integer>redniBrSloja=new ArrayList();
	ArrayList<Integer> redniBrOperacije=new ArrayList();
	Panel panSelekcije;
	Panel panOperacije;
	
	
	
	
	class DijalogZaIspis extends Dialog implements ActionListener,ItemListener{
		Label labela2=new Label("Uneti ime izlaznog fajla BEZ ekstenzije");
		TextField poljeZaTekst=new TextField();
		Label labela=new Label("Izlazni format:");
		Choice izbor=new Choice();
		Button dugme=new Button("Sacuvaj");
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

			izbor.add("BMP");
			izbor.add("PAM");
			izbor.add("XML");
			Panel p=new Panel();
			p.add(labela);
			p.add(izbor);
			Panel p2=new Panel();
			p2.setLayout(new GridLayout(2,1));
			p2.add(labela2);
			p2.add(poljeZaTekst);
			
			setLayout(new GridLayout(3,1));
			add(p);
			add(p2);
			add(dugme);
			izbor.addItemListener(this);
			dugme.addActionListener(this);
			poljeZaTekst.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String tekst=poljeZaTekst.getText();
			
			
			if(e.getActionCommand()=="Sacuvaj") {
				if(!tekst.matches("^[a-zA-Z]\\w*$")|| tekst.matches("^.*\\..*$")) {
					cale.dijalogZaOpomene.labela.setText("Nevalidno ime fajla, promenite ga");
					cale.dijalogZaOpomene.setVisible(true);
					return;
				}
				if(izbor.getSelectedItem().compareTo("BMP")==0) {
					cale.gimage.konstrFinLejer(tekst,1); 
				}
				else if(izbor.getSelectedItem().compareTo("PAM")==0) {
					cale.gimage.konstrFinLejer(tekst,2); 
				}
				else cale.gimage.konstrFinLejer(tekst,3); 
					
				
			}
			
		
			
	
			
			
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			String tekst=izbor.getSelectedItem();
		}
	}
	class DijalogZaUnos extends Dialog implements ActionListener{
		Label label1=new Label("Ime slike sa ekstenzijom: ");
		Label label2=new Label("Pozicija na koju upisujemo layer: ");
		TextField poljeZaTekst=new TextField();
		TextField poljeZaBroj=new TextField();
		Choice izbor=new Choice();
		Button dodaj=new Button("Dodaj lejer");
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
			
			Panel p11=new Panel();
			Panel p12=new Panel();
			p11.setLayout(new GridLayout(2,1));
			p12.setLayout(new GridLayout(2,1));
			Panel p1=new Panel();
			p1.setLayout(new GridLayout(2,1));
			setLayout(new GridLayout(3,1));
			Label labela=new Label("Unesite poziciju lejera i ime slike sa ekstenzijom ");
			add(labela);
			p11.add(label2);
			p11.add(poljeZaBroj);
			p1.add(p11);
			
			p12.add(label1);
			p12.add(poljeZaTekst);
			p1.add(p12);
			
			
			add(p1);
			add(dodaj);
			dodaj.addActionListener(this);
			poljeZaBroj.addActionListener(this);
			poljeZaTekst.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String tekst=poljeZaTekst.getText();
			//System.out.println("DESILO SE NESTO");
			if(!tekst.equals("") && !tekst.equals("Uneti tekst ovde.")) {
				Pattern pattern = Pattern.compile("^[a-zA-Z]\\w*\\.(.+)$");
				Matcher matcher = pattern.matcher(tekst);
				if(!matcher.matches()) {
					cale.dijalogZaOpomene.labela.setText("Nevalidno ime fajla, unesite ispravno ime");
					cale.dijalogZaOpomene.setVisible(true);
					return;
				}
				if(matcher.group(1).toLowerCase().compareTo("bmp")!=0 && matcher.group(1).toLowerCase().compareTo("pam")!=0 && matcher.group(1).toLowerCase().compareTo("xml")!=0)
				{
					cale.dijalogZaOpomene.labela.setText("Nevalidan format ulaznog fajla. Dozvoljeni: BMP,PAM ili XML");
					cale.dijalogZaOpomene.setVisible(true);
					return;
				}
				cale.putanja=tekst;
			}
			tekst=poljeZaBroj.getText();
			if(!tekst.equals("")) {
				
				if(!tekst.matches("^\\d+$")) {
					cale.dijalogZaOpomene.labela.setText("Nevalidan broj lejera, unesite ispravan broj lejera");
					cale.dijalogZaOpomene.setVisible(true);
					return;
				}
				int provera;
				provera=Integer.parseInt(tekst);
				if(provera<0) {
					cale.dijalogZaOpomene.labela.setText("Nevalidan broj lejera, unesite ispravan broj lejera");
					cale.dijalogZaOpomene.setVisible(true);
					return;
				}
				cale.temp=provera;
				//poljeZaBroj.setText("");
				if(e.getActionCommand()=="Dodaj lejer") {
					if(poljeZaTekst.getText().compareTo("")==0 ) {
						cale.dijalogZaOpomene.labela.setText("Unesite ime slike");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					if(tekst.compareTo("")==0 ) {
						cale.dijalogZaOpomene.labela.setText("Unesite broj lejera");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					for(int i=0;i<cale.redniBrSloja.size();i++) {
						if(cale.redniBrSloja.get(i)==provera) {
							cale.dijalogZaOpomene.labela.setText("Vec postoji lejer na zadatoj poziciji");
							cale.dijalogZaOpomene.setVisible(true);
							return;
						}
					}
					cale.dodajSliku();
				}
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
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Crnobela") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Div") {
					if(Integer.parseInt(tek)==0) {
						cale.dijalogZaOpomene.labela.setText("Nije dovozljeno deljenje nulom!");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="DivInvert") {
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Inverzija") {
				
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Log") {
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
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
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Pow") {
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Siva") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				} 
				else if(tekst=="Sub") {
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="SubInvert") {
					if(!tek.matches("^\\d+$")) {
						cale.dijalogZaOpomene.labela.setText("Nevalidan operand");
						cale.dijalogZaOpomene.setVisible(true);
						return;
					}
					cale.trenOperacije.add(new GUIoperacije(tekst,tek));
					cale.gimage.dodajOperaciju(tekst,Integer.parseInt(tek));
					
				} 
				else if(tekst=="Medijana") {
					
					cale.trenOperacije.add(new GUIoperacije(tekst));
					cale.gimage.dodajOperaciju(tekst,0);
				}
				cale.dodajOperaciju(); //osvezni panel sa operacijama
				cale.redniBrOperacije.add(redniBrOperacije.size()); //za brisanje sluzi
				
			}
			
			
			
		}

		
		
	}
	
	class DijalogZaIzlaz extends Dialog implements ActionListener{
		
		Label labela=new Label("Da li zelite da sacuvate sliku pre izlaska?");
		Button sacuvati=new Button("Sacuvaj pre izlaska");
		Button exit=new Button("Izlaz");
		GUI cale;
		DijalogZaIzlaz(GUI roditelj) {
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
			Panel p=new Panel();
			p.add(sacuvati);
			p.add(exit);
			add(labela);
			add(p);
			sacuvati.addActionListener(this);
			exit.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().compareTo("Izlaz")==0) {
				
				cale.dispose();
			}
			else if(e.getActionCommand().compareTo("Sacuvaj pre izlaska")==0) {
				cale.dijalogSacuvaj.setVisible(true);
			}
			
			
		}
	}
	
	class DijalogZaRIPGreske extends Dialog implements ActionListener{
		
		public Label labela=new Label("Desila se neocekivana greska");
		
		Button exit=new Button("Izlaz");
		GUI cale;
		DijalogZaRIPGreske(GUI roditelj) {
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
			Panel p=new Panel();
			
			p.add(exit);
			add(labela);
			add(p);
			
			exit.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().compareTo("Izlaz")==0) {
				
				cale.dispose();
			}
			
			
			
		}
	}
	
	class DijalogZaOpomene extends Dialog implements ActionListener{
		
		public Label labela=new Label("Desila se neocekivana greska");
		
		Button exit=new Button("OK");
		GUI cale;
		DijalogZaOpomene(GUI roditelj) {
			super(roditelj,"Dijalog",false);
			cale=roditelj;
			setSize(400,400);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {setVisible(false);} 
			});
			dodajKomponente();
		}

		private void dodajKomponente() {
			
			setLayout(new GridLayout(2,1));
			Panel p=new Panel();
			
			p.add(exit);
			add(labela);
			add(p);
			
			exit.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().compareTo("OK")==0) {
				
				setVisible(false);
			}
			
			
			
		}
	}
	
	class DijalogZaBojenje extends Dialog implements ActionListener{
		
		public Label labela=new Label("RGB komponente za bojenje selekcije:");
		public TextField R=new TextField("R:");
		public TextField G=new TextField("G:");
		public TextField B=new TextField("B:");
		Button exit=new Button("OK");
		GUI cale;
		DijalogZaBojenje(GUI roditelj) {
			super(roditelj,"Dijalog",false);
			cale=roditelj;
			setSize(400,400);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {setVisible(false);} 
			});
			dodajKomponente();
		}

		private void dodajKomponente() {
			
			setLayout(new GridLayout(3,1));
			Panel p=new Panel();
			p.add(R);
			p.add(G);
			p.add(B);
			Panel pexit=new Panel();
			
			pexit.add(exit);
			add(labela);
			add(p);
			add(pexit);
			
			exit.addActionListener(this);
			R.addActionListener(this);
			G.addActionListener(this);
			B.addActionListener(this);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String t1=R.getText();
			String t2=G.getText();
			String t3=B.getText();
			
			
			if(e.getActionCommand().compareTo("OK")==0) {
				
				//cale.gimage.novaBoja=Color(Float.parseFloat(t1),Float.parseFloat(t2),Float.parseFloat(t3));
				cale.gimage.novaBoja=new Color(Integer.parseInt(t1),Integer.parseInt(t2),Integer.parseInt(t3));
				setVisible(false);
				cale.gimage.stvarnoObojiSelekciju();
			}
			
			
			
		}
	}
	public GUI() {
		
		super("Slike");
		addWindowListener(new WindowAdapter(){  
	          public void windowClosing(WindowEvent e) {  
	        	  //dijalogSacuvaj.setVisible(true);
	        	  dijalogIzlaz.setVisible(true);
	        	 
	         }   
	     });
		//dodajScenu();
		
		dijalogDodavanje=new DijalogZaUnos(this);
		dijalogSacuvaj=new DijalogZaIspis(this);
		dijalogOperacije=new DijalogZaOperacije(this);
		dijalogIzlaz=new DijalogZaIzlaz(this);
		dijalogZaRIPGreske=new DijalogZaRIPGreske(this);
		dijalogZaOpomene=new DijalogZaOpomene(this);
		dijalogZaBojenje=new DijalogZaBojenje(this);
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
		if(gimage.postojiSlika) {add(gimage,BorderLayout.CENTER);
			osveziDodateLejere();}
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
		//System.out.println("x="+x);
		panLejeri.setLayout(new GridLayout(gimage.brslojeva+1,1));
		for(int i=redniBrSloja.size()-1;i>=0;i--) {
			if(redniBrSloja.get(i)==x) {
				//System.out.println("i="+i);
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
		trenOperacije.get(trenOperacije.size()-1).brisanje.addActionListener(gimage);
		//remove(panOperacije);
		//add(panOperacije,BorderLayout.EAST);
		revalidate();
		//panOperacije.add(comp)
	}
	public void obrisiOperaciju(int koja) {
		for(int i=0;i<redniBrOperacije.size();i++) {
			if(redniBrOperacije.get(i)==koja) {
				redniBrOperacije.remove(i);
				panOperacije.remove(i+1);
				trenOperacije.remove(i);
			}
		}
		
		revalidate();
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
