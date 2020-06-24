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
	class Dijalog extends Dialog implements ActionListener{
		TextField poljeZaTekst=new TextField("Uneti tekst ovde.");
		GUI cale;
		Dijalog(GUI roditelj) {
			super(roditelj,"Dijalog",false);
			cale=roditelj;
			setSize(200,200);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {setVisible(false);} 
			});
			dodajKomponente();
		}

		private void dodajKomponente() {
			// TODO Auto-generated method stub
			setLayout(new GridLayout(2,1));
			Label labela=new Label("Putanja do slike: ");
			add(labela);
			add(poljeZaTekst);
			poljeZaTekst.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String tekst=poljeZaTekst.getText();
			cale.putanja=tekst;
			cale.dodajSliku();
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
		setSize(9999,9999);
		gimage=new GUIImage(this);
		dodajMenije();
		dodajDesniDeo();
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
		gimage.setPutanja(putanja);
		add(gimage,BorderLayout.CENTER);
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
	
	private void dodajDesniDeo() {
		ArrayList<Pravougaonik> temp=new ArrayList();
		temp.add(new Pravougaonik(222,136,180,110));
		trenSelekcije.add(new SelekcijeGUI(new Selekcija("asd",temp)));
		// TODO Auto-generated method stub
		Panel p=new Panel();
		Panel p1=new Panel();
		Panel p2=new Panel();
		p.setLayout(new GridLayout(2,1));
		
		Font myFont = new Font("Times New Roman", Font.BOLD, 24);
		
		Label slojevi=new Label("Layers");
		slojevi.setFont(myFont);
		slojevi.setSize(50, 50);
		//slojevi.setBackground(Color.LIGHT_GRAY);
		p1.add(slojevi);
		p1.setBackground(Color.LIGHT_GRAY);
		
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
		
		p.add(p1);
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
	
}
