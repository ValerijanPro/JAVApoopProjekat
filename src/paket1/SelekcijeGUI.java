package paket1;
import selekcije.*;
import java.awt.Component;
import java.awt.*;
public class SelekcijeGUI extends Component{

	public Selekcija s;
	public Checkbox aktivna;
	public SelekcijeGUI(Selekcija ss) {
		s=ss;
		aktivna=new Checkbox("Aktivna");
	}
	public Label dodajGUIselekciju() {
		Label labela=new Label("("+s.getNiz().get(0).getX()+","+s.getNiz().get(0).getY()+","
				+s.getNiz().get(0).getSirina()+","+s.getNiz().get(0).getVisina()+")");
		return labela;
	}
	public Checkbox akt() {return aktivna;}
	//public String toString() {
		
	//}
}
