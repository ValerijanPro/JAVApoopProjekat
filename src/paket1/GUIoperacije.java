package paket1;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.util.Random;

public class GUIoperacije extends Component {
	Random random=new Random();
	String naziv;
	public GUIoperacije(String s) {
		naziv=s;
		
	}
	public Panel dodajGUIoperaciju() {
		int r,g,b;
		r=random.nextInt(255);
		g=random.nextInt(255);
		b=random.nextInt(255);
		Color boja=new Color(r,g,b);
		Panel p=new Panel();
		p.setBackground(boja);
		Label labela=new Label(naziv);
		
		labela.setFont(new Font("Ariel", Font.BOLD, 18));
		p.add(labela);
		return p;
	}
	
}
