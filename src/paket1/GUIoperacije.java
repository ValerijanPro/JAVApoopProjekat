package paket1;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.util.Random;

public class GUIoperacije extends Component {
	Random random=new Random();
	String naziv;
	String Operand="";
	public GUIoperacije(String s) {
		naziv=s;
		
	}
	public GUIoperacije(String s,String o) {
		naziv=s;
		Operand=o;
	}
	public Panel dodajGUIoperaciju() {
		int r,g,b;
		r=random.nextInt(205);
		g=random.nextInt(205);
		b=random.nextInt(205);
		r+=50;
		g+=50;
		b+=50;
		Color boja=new Color(r,g,b);
		Panel p=new Panel();
		p.setBackground(boja);
		Label labela=new Label(naziv);
		labela.setFont(new Font("Ariel", Font.BOLD, 18));
		p.add(labela);
		if(Operand!="") {
			Label labelica=new Label();
			labelica.setText(Operand);
			p.setLayout(new GridLayout(2,1));
			p.add(labelica);
		}
		return p;
	}
	
}
