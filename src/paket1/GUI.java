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
	GUIImage gimage;
	String putanja;
	private Dijalog dijalog;
	//TODO: TEMP JE SAMO DOK NE STAVIM FILECHOOSER, PA CU BROJ GDE UPISUJEM LAYER DRUGACIJE SLATI
	int temp=0;
	
	
	Panel lejeri;
	Panel selekcije;
	
	class Dijalog extends Dialog implements ActionListener{
		TextField poljeZaTekst=new TextField("Uneti tekst ovde.");
		TextField poljeZaBroj=new TextField("Pozicija na koju upisujemo layer: ");
		GUI cale;
		Dijalog(GUI roditelj) {
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
	
	public GUI() {
		
		super("Slike");
		addWindowListener(new WindowAdapter(){  
	          public void windowClosing(WindowEvent e) {  
	        	 
	              dispose();  
	         }   
	     });
		//dodajScenu();
		
		dijalog=new Dijalog(this);
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
		dodavanje.add("Dodaj novu sliku");
		dodavanje.addActionListener(this);
		traka.add(dodavanje);
		setMenuBar(traka);
	}

	private void dodajSliku() {
		// TODO Auto-generated method stub
		gimage.setPutanja(putanja,temp);
		add(gimage,BorderLayout.CENTER);
		lejeri.setLayout(new GridLayout(gimage.brslojeva+1,1));
		lejeri.add(gimage.dodajLejer(temp));
		revalidate();
		
		//gimage.paint(getGraphics());
		
	}

	private void dodajOperacije() {
		// TODO Auto-generated method stub
		Panel p=new Panel();
		p.setBackground(Color.LIGHT_GRAY);
		Font myFont = new Font("Times New Roman", Font.BOLD, 24);
		
		Label operacije=new Label("Operacije");
		operacije.setFont(myFont);
		p.add(operacije);
		add(p,BorderLayout.EAST);
	}
	
	private void dodajLeviDeo() {
		ArrayList<Pravougaonik> temp=new ArrayList();
		temp.add(new Pravougaonik(222,136,180,110));
		trenSelekcije.add(new SelekcijeGUI(new Selekcija("asd",temp)));
		// TODO Auto-generated method stub
		Panel p=new Panel();
		lejeri=new Panel();
		lejeri.setLayout(new GridLayout());
		Panel p2=new Panel();
		p.setLayout(new GridLayout(2,1));
		
		Font myFont = new Font("Times New Roman", Font.BOLD, 24);
		
		Label slojevi=new Label("Layers");
		slojevi.setFont(myFont);
		slojevi.setSize(50, 50);
		//slojevi.setBackground(Color.LIGHT_GRAY);
		lejeri.add(slojevi); //labela Layers
		lejeri.setBackground(Color.LIGHT_GRAY);
		
		
		
		Label selekcije=new Label("Selekcije");
		selekcije.setFont(myFont);
		selekcije.setSize(50, 50);
		//selekcije.setBackground(Color.LIGHT_GRAY);
		p2.setLayout(new GridLayout(trenSelekcije.size()+1,1));
		p2.add(selekcije);
		p2.setBackground(Color.LIGHT_GRAY);
		
		for(int i=0;i<trenSelekcije.size();i++) {
			trenSelekcije.get(i).aktivna.addItemListener(this);
			Panel ptemp=new Panel();
			ptemp.add(trenSelekcije.get(i).dodajGUIselekciju());
			ptemp.add(trenSelekcije.get(i).akt());
			p2.add(ptemp);
		}
		
		p.add(lejeri);
		p.add(p2);
		add(p,BorderLayout.WEST);
		
	
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<trenSelekcije.size();i++) {
			if(trenSelekcije.get(i).aktivna.getState()) {
				System.out.println("postala");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String komanda=e.getActionCommand();
		if(komanda.equals("Dodaj novu sliku")) dijalog.setVisible(true);
	}

	public void osveziSliku() {
		
		remove(gimage);
		add(gimage,BorderLayout.CENTER);
		//lejeri.setLayout(new GridLayout(gimage.brslojeva+1,1));
		//lejeri.add(gimage.dodajLejer());
		//revalidate();
	}
	
}
