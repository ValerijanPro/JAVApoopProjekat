package Image;
import java.util.*; 
import selekcije.*;

public class Image {
	//typedef std::vector<Layer> slojevi;
	Map<Integer,Layer> layers=new HashMap<Integer,Layer>();  
	int sirina, visina, brlejera;
	int brbitapopixelu;
	ArrayList<Selekcija> sel=new ArrayList();
	//op operacije;
	ArrayList<Integer> akt;
	boolean medijana;
	
	public Image(int s , int v , int bbpp  ) {
		sirina = s;
		visina = v;
		brlejera = 0;
		brbitapopixelu = bbpp;
		sel.clear();
		medijana = false;
		//operacije.clear();
		dodajPush();
	}
	
	public Image() {
		sirina=0;
		visina=0;
		brlejera=0;
		brbitapopixelu=32;
		
	}
	public boolean getMedijana() { return medijana; }
	public void setMedijana(boolean a) { medijana = a; }
	public void ObrisiLejer(int pozicija) {
		if (layers.containsKey(pozicija))
			return;
		layers.remove(pozicija);

		/*return;*/
		//if (layers[pozicija] == nullptr) return; // THROW GRESKA U INDEKSIRANJU
		//std::shared_ptr<Layer> temp = layers[pozicija];
		//for (int i = pozicija; i != brlejera - 1; i++) {
		//	layers[i] = layers[i + 1]; 
		//}
		//layers[brlejera - 1] = temp;
		//layers[brlejera-1]->~Layer();
		//brlejera--;
	}
	//public op getOperacije() { return operacije; }
	public void dodajPush() {
		//operacije.push_back(std::make_shared<Push>(0));
	}
	
	public void dodajAktivan(int novi) {
		akt.add(novi);
	}
	public void dodajSelekciju(String s, ArrayList<Pravougaonik> pp, boolean stanje) {

		boolean VecPostoji = false;
		for (int i = sel.size() - 1; i >= 0; i--) {
			if (sel.get(i).getIme().compareTo(s) == 0) {
				
				VecPostoji = true;
			}
		}
		if (!VecPostoji) {
			Selekcija nova = new Selekcija(s, pp);
			nova.setStanje(stanje);
			sel.add(nova);
		}
		else {
			//throw GreskaNepostojecaSelekcija("Selekcija sa datim imenom koju dodajete vec postoji");
		}
	

	}
	public void ObrisiSelekciju(String poz) {
		//sel.erase(sel.begin()+poz-1);
		boolean obrisan=false;
		for (int i = sel.size()-1; i >=0; i--) {
			if (sel.get(i).getIme().compareTo(poz) == 0) {
				sel.remove(i);
				obrisan = true;
			}
		}
		if (!obrisan) {
			//throw GreskaNepostojecaSelekcija();
		}
		//if los index greska
	}
	public ArrayList<Selekcija> getSelekcije() { return sel; }
	public void DodajSloj(Layer l, int pozicija) {


		if (l.getvisina() > visina) {
			//realociram sve lejere
			for (Integer i:layers.keySet()) {
				Layer temp=layers.get(i);
				temp.realocirajPovecaj(sirina, l.getvisina());
				//(i).second->realocirajPovecaj(sirina, l->getvisina());

			}
			visina = l.getvisina();

			// na poziciju upisati novi lejer

			//matricu piksela prosiris praznim pikselima tj crnim TREBA PROVIDNIM
		}
		if (l.getSirina() > sirina) {
			//realociram sve lejere
			/*for (std::vector<Layer*>::iterator i = layers.begin(); i != layers.end(); i++) {
				(*i)->realocirajPovecaj(l ->getSirina(), visina);
			}*/
			// na poziciju upisati novi lejer
			for (Integer i:layers.keySet()) {
				Layer temp=layers.get(i);
				temp.realocirajPovecaj(l.getSirina(), visina);
				

			}
				

			
			sirina = l.getSirina();
			//matricu piksela prosiris praznim pikselima tj crnim TREBA PROVIDNIM
		}
		if (l.getSirina() < sirina) {
			// l resize sirina
			l.realocirajPovecaj(sirina, l.getvisina());
		}
		if (l.getvisina() < visina) {
			//l resize visina
			l.realocirajPovecaj(l.getSirina(), visina);
		}
		//insert na poziciju
		if (layers.containsKey(pozicija)) {
			layers.put(pozicija, l) ;
			sirina = l.getSirina();
			visina = l.getvisina();
			brlejera++;
		}
		else {
			//throw GreskaPostojiKljuc("Greska vec postoji dati layer");
		}

		//else { // ako je istih dimenzija samo dodam na kraj
		//	//layers.push_back(l);
		//	brlejera++;
		//}

	}
	boolean inAktivni(int x,ArrayList<Integer> a){
		for (Integer i:a) {
			if (i == x) return true;
		}
		return false;
	}
	public Layer konstruisiFinalniLayer(ArrayList<Integer> a) {
		//return layers[0];// TODO: PRVO OVO OD ZIZE, a posle toga AKTIVNE LEJERE, a posle toga UVEK PUSH PAJA
		Layer lejer = new Layer(sirina, visina);
		ArrayList<Layer> aktivniLejeri=new ArrayList();

		if (a.size() == 0) return lejer;
		for ( Integer i : layers.keySet()) {
			if (inAktivni(i, a)) {
				aktivniLejeri.add(layers.get(i));
			}
		}
		if (aktivniLejeri.size() == 0) return lejer;
		for (int i = 0; i < sirina; i++) {
			for (int j = 0; j < visina; j++) {
				Piksel piksel = aktivniLejeri.get(0).getPixel(i, j);
				double r = piksel.getR() * 1.0 / 255;
				double g = piksel.getG() * 1.0 / 255;
				double b = piksel.getB() * 1.0 / 255;
				double opacity = piksel.getOpacity() * 1.0 / 255;

				
				if (i == 410 && j == 40) {
					//std::cout << "s";
				}
				if (i == 40 && j == 410) {
					//std::cout << "s";
				}
					for (int k = 1; k != aktivniLejeri.size(); k++) {
						if (opacity == 1.0) { continue; }
						
						Piksel p1 = aktivniLejeri.get(k).getPixel(i,j);
						double r1 = p1.getR() * 1.0 / 255;
						double g1 = p1.getG() * 1.0 / 255;
						double b1 = p1.getB() * 1.0 / 255;
						double opacity1= p1.getOpacity() * 1.0 / 255;
						if (opacity1 == 0)continue;
						double temp = (1 - opacity)* opacity1 ;
						double opt = opacity + temp;
						double temp2 = temp / opt;
						double temp3 = opacity / opt;
						double rt = r * temp3 + r1 * temp2;
						double gt = g * temp3 + g1 * temp2;
						double bt = b * temp3 + b1 * temp2;
						
						r = rt;
						b = bt;
						g = gt;
						opacity = opt;

					}
				
				Piksel novi = new Piksel(r*255, g*255, b*255, 0, opacity*255);
				lejer.overwritepixel(i, j, novi);
			}
		}

		return lejer;
	





	}
	
	public ArrayList<Integer> getAkt() { return akt; }
	public void setAkt(ArrayList<Integer> a) { akt = a; }
	public Map<Integer,Layer> getLayers() { return layers; }
	public int getSirina() { return sirina; }
	public int getvisina() { return visina; }
	public int getBrlejera() { return brlejera; }
}
