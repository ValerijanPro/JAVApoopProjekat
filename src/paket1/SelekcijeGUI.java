package paket1;
import selekcije.*;
import java.awt.Component;
import java.awt.*;
public class SelekcijeGUI extends Component{

	public Selekcija s;
	public Checkbox aktivna;
	public Button dugme;
	public Button oboji;
	public SelekcijeGUI(Selekcija ss) {
		s=ss;
		dugme=new Button("Obrisi selekciju");
		oboji=new Button("Oboji selekciju");
		aktivna=new Checkbox("Aktivna",true);
	}
	public Label dodajGUIselekciju() {
		Panel p=new Panel();
		Label labela=new Label("("+s.getNiz().get(0).getX()+","+s.getNiz().get(0).getY()+","
				+s.getNiz().get(0).getSirina()+","+s.getNiz().get(0).getVisina()+")");
		return labela;
	}
	public Checkbox akt() {return aktivna;}
	public Button dug() {return dugme;}
	public Button oboj() {return oboji;}
	//public String toString() {
		
	//}
}
