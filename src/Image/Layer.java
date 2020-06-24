package Image;

import java.util.*;
public class Layer {

private ArrayList<ArrayList<Piksel>> m=new ArrayList<ArrayList<Piksel>>();
	
private int sirina, visina;


public int getSirina() { return sirina; }
public	int getvisina() { return visina; }
	public Layer( int sir, int vis){
//	Layer( Image otac){
		sirina = sir;
		visina = vis;
		
		
		for (int i = 0; i < sirina; i++) {
			ArrayList<Piksel> pikseli=new ArrayList();
			for (int j = 0; j < visina; j++) {
					Piksel p = new Piksel();
					pikseli.add(p);
				
			}
			m.add(pikseli);
		}
	}
	public void overwritepixel(int i, int j,Piksel p) {
		//if (i > sirina || j > visina || i<0 || j<0) {
		//	throw GreskaIndexMatrice();
		//}
		m.get(i).set(j, p);
	}
	public Piksel getPixel(int i,int j) {
		//if (i > sirina || j > visina || i < 0 || j < 0) throw GreskaIndexMatrice();
		return m.get(i).get(j);
	}
	//matrica& getM() { return m; }


public void realocirajPovecaj(int sir, int vis) { //a= sirina , b=visina
	
		//dodam samo na postojece
		//auto kr = m.end();
		
		for (int j = 0; j < sirina; j++) {
			
				//ArrayList<Piksel> kraj = m.get(j).
				for(int i=0;i<(vis-visina);i++) {
					m.get(j).add(new Piksel((char)0,(char)0,(char)0,(short)0,(char)0));
				}
				//m[j].insert(kraj, vis - visina, new Piksel((char)0,(char)0,(char)0,(short)0,(char)0));
			
		}
		//std::cout << std::endl;
		//std::cout << *this;
		ArrayList<Piksel> niz=new ArrayList();
		//dodam koliko fali uspravnih stangli
		for (int j = 0; j < sir - sirina; j++) {
			//napravim jednu novu uspravnu stanglu
			
			niz.clear();
			for (int i = 0; i < vis; i++) {

				niz.add(new Piksel((char)0,(char)0,(char)0,(short)0,(char)0));
			}
			//auto kraj = m.end();
			m.add( niz);
		}
		

	
		//nove vrednosti sirine i visine
		sirina = sir;
		visina = vis;
		//std::cout << std::endl;
		//std::cout << *this;
	}

	
	public Piksel Medijana(int x,int y) {
		
		int sumaR = 0,sumaG=0,sumaB=0;
		sumaR = getPixel(x - 1, y - 1).getR()+ getPixel(x , y - 1).getR()+ getPixel(x + 1, y - 1).getR()+ getPixel(x - 1, y ).getR()+
			getPixel(x , y ).getR()+
			getPixel(x + 1, y ).getR()+ getPixel(x - 1, y + 1).getR()+ getPixel(x , y+ 1).getR()+ getPixel(x+ 1, y + 1).getR();
		sumaG = getPixel(x - 1, y - 1).getG() + getPixel(x, y - 1).getG() + getPixel(x + 1, y - 1).getG() + getPixel(x - 1, y).getG() +
			getPixel(x, y).getG() +
			getPixel(x + 1, y).getG() + getPixel(x - 1, y + 1).getG() + getPixel(x, y + 1).getG() + getPixel(x + 1, y + 1).getG();
		sumaB = getPixel(x - 1, y - 1).getB() + getPixel(x, y - 1).getB() + getPixel(x + 1, y - 1).getB() + getPixel(x - 1, y).getB() +
			getPixel(x, y).getB() +
			getPixel(x + 1, y).getB() + getPixel(x - 1, y + 1).getB() + getPixel(x, y + 1).getB() + getPixel(x + 1, y + 1).getB();
		sumaR = sumaR / 9;
		sumaG = sumaG / 9;
		sumaB = sumaB / 9;
		return new Piksel(sumaR, sumaG, sumaB);
		//getPixel(x, y).oboji(sumaR, sumaG, sumaB);
	}
	public Piksel IvicaMedijana(int x, int y) {
		//slucaj kad je x == 0
		
		int sumaR = 0,sumaG=0, sumaB=0;
		if (x == 0) {
			if (y == 0) {
				//samo 4
				sumaR = getPixel(0, 0).getR() + getPixel(0, 1).getR() + getPixel(1, 0).getR()+ getPixel(1, 1).getR() ;
				sumaG = getPixel(0, 0).getG() + getPixel(0, 1).getG() + getPixel(1, 0).getG() + getPixel(1, 1).getG();
				sumaB = getPixel(0, 0).getB() + getPixel(0, 1).getB() + getPixel(1, 0).getB() + getPixel(1, 1).getB();
				sumaR = sumaR / 4;
				sumaG = sumaG / 4;
				sumaB = sumaB / 4;
				//getPixel(0, 0).oboji(sumaR, sumaG, sumaB);
				return new Piksel(sumaR,sumaG,sumaB);
			}
			else if (y==visina-1){
				//samo 4 dole levo
				sumaR = getPixel(0, visina - 1).getR() + getPixel(0, visina - 2).getR() + getPixel(1, visina - 1).getR() + getPixel(1, visina - 2).getR();
				sumaG = getPixel(0, visina - 1).getG() + getPixel(0, visina - 2).getG() + getPixel(1, visina - 1).getG() + getPixel(1, visina - 2).getG();
				sumaB = getPixel(0, visina - 1).getB() + getPixel(0, visina - 2).getB() + getPixel(1, visina - 1).getB() + getPixel(1, visina - 2).getB();
				sumaR = sumaR / 4;
				sumaG = sumaG / 4;
				sumaB = sumaB / 4;
				//getPixel(x, y).oboji(sumaR, sumaG, sumaB);
				return new Piksel(sumaR, sumaG, sumaB);
				
			}
			else {
				//normalnih 6
				sumaR = getPixel(0, y - 1).getR() + getPixel(0, y).getR() + getPixel(0, y + 1).getR() + 
					    getPixel(1, y - 1).getR() + getPixel(1, y).getR() + getPixel(1, y + 1).getR();
				sumaG = getPixel(0, y - 1).getG() + getPixel(0, y).getG() + getPixel(0, y + 1).getG() +
					    getPixel(1, y - 1).getG() + getPixel(1, y).getG() + getPixel(1, y + 1).getG();
				sumaB = getPixel(0, y - 1).getB() + getPixel(0, y).getB() + getPixel(0, y + 1).getB() +
					    getPixel(1, y - 1).getB() + getPixel(1, y).getB() + getPixel(1, y + 1).getB();
				sumaR = sumaR / 6;
				sumaG = sumaG / 6;
				sumaB = sumaB / 6;
				//getPixel(x, y).oboji(sumaR, sumaG, sumaB);
				return new Piksel(sumaR, sumaG, sumaB);
			}
			 // da ne ide dalje u else
		}
		if (y == 0) {
			//samo 4
			if (x == sirina - 1) { //gore desno
				sumaR = getPixel(x, 0).getR() + getPixel(x, 1).getR() + getPixel(x-1, 0).getR() + getPixel(x-1, 1).getR();
				sumaG = getPixel(x, 0).getG() + getPixel(x, 1).getG() + getPixel(x-1, 0).getG() + getPixel(x-1, 1).getG();
				sumaB = getPixel(x, 0).getB() + getPixel(x, 1).getB() + getPixel(x-1, 0).getB() + getPixel(x-1, 1).getB();
				sumaR = sumaR / 4;
				sumaG = sumaG / 4;
				sumaB = sumaB / 4;
				//getPixel(x, y).oboji(sumaR, sumaG, sumaB);
				return new Piksel(sumaR, sumaG, sumaB);
			}
			else {//6 normalnih
				sumaR = getPixel(x-1, 0).getR() + getPixel(x, 0).getR() + getPixel(x+1, 0).getR() +
					getPixel(x-1, 1).getR() + getPixel(x, 1).getR() + getPixel(x+1, 1).getR();
				sumaG = getPixel(x - 1, 0).getG() + getPixel(x, 0).getG() + getPixel(x + 1, 0).getG() +
					getPixel(x - 1, 1).getG() + getPixel(x, 1).getG() + getPixel(x + 1, 1).getG();
				sumaB = getPixel(x - 1, 0).getB() + getPixel(x, 0).getB() + getPixel(x + 1, 0).getB() +
					getPixel(x - 1, 1).getB() + getPixel(x, 1).getB() + getPixel(x + 1, 1).getB();
				sumaR = sumaR / 6;
				sumaG = sumaG / 6;
				sumaB = sumaB / 6;
				//getPixel(x, y).oboji(sumaR, sumaG, sumaB);
				return new Piksel(sumaR, sumaG, sumaB);
			}
		}
		if (y == visina - 1) {
			if (x == sirina - 1) {
				//samo 4
				sumaR = getPixel(x, y).getR() + getPixel(x-1, y).getR() + getPixel(x - 1, y-1).getR() + getPixel(x , y-1).getR();
				sumaG = getPixel(x, y).getG() + getPixel(x - 1, y).getG() + getPixel(x - 1, y - 1).getG() + getPixel(x, y - 1).getG();
				sumaB = getPixel(x, y).getB() + getPixel(x - 1, y).getB() + getPixel(x - 1, y - 1).getB() + getPixel(x, y - 1).getB();
				sumaR = sumaR / 4;
				sumaG = sumaG / 4;
				sumaB = sumaB / 4;
			//	getPixel(x, y).oboji(sumaR, sumaG, sumaB);
				return new Piksel(sumaR, sumaG, sumaB);
			}
			else {
				//normalnih 6
				sumaR = getPixel(x - 1, y).getR() + getPixel(x, y).getR() + getPixel(x + 1, y).getR() +
					getPixel(x - 1, y-1).getR() + getPixel(x, y-1).getR() + getPixel(x + 1, y-1).getR();
				sumaG = getPixel(x - 1, y).getG() + getPixel(x, y).getG() + getPixel(x + 1, y).getG() +
					getPixel(x - 1, y - 1).getG() + getPixel(x, y - 1).getG() + getPixel(x + 1, y - 1).getG();
				sumaB = getPixel(x - 1, y).getB() + getPixel(x, y).getB() + getPixel(x + 1, y).getB() +
					getPixel(x - 1, y - 1).getB() + getPixel(x, y - 1).getB() + getPixel(x + 1, y - 1).getB();
				sumaR = sumaR / 6;
				sumaG = sumaG / 6;
				sumaB = sumaB / 6;
			//	getPixel(x, y).oboji(sumaR, sumaG, sumaB);
				return new Piksel(sumaR, sumaG, sumaB);
			}
		}
		 // desni deo
			//normalnih 6
		sumaR = getPixel(x ,y + 1).getR() + getPixel(x, y).getR() + getPixel(x ,y - 1).getR() +
			getPixel(x - 1, y + 1).getR() + getPixel(x - 1, y).getR() + getPixel(x - 1, y - 1).getR();
		sumaG = getPixel(x, y + 1).getG() + getPixel(x, y).getG() + getPixel(x, y - 1).getG() +
			getPixel(x - 1, y + 1).getG() + getPixel(x - 1, y).getG() + getPixel(x - 1, y - 1).getG();
		sumaB = getPixel(x, y + 1).getB() + getPixel(x, y).getB() + getPixel(x, y - 1).getB() +
			getPixel(x - 1, y + 1).getB() + getPixel(x - 1, y).getB() + getPixel(x - 1, y - 1).getB();
		sumaR = sumaR / 6;
		sumaG = sumaG / 6;
		sumaB = sumaB / 6;
		//getPixel(x, y).oboji(sumaR, sumaG, sumaB);
		return new Piksel(sumaR, sumaG, sumaB);
		
	

	}
	public boolean normalnaMedijana(int x,int y) {
		return (x != 0 && y != 0 && x!=sirina-1 && y!=visina-1);
	}
	Layer IzvrsiMedijanu() {
		Layer nova = this;
		
		for (int i = 0; i < sirina; i++) {
			for (int j = 0; j < visina; j++) {
				Piksel p;
				if (nova.normalnaMedijana(i, j)) {
					//nova->Medijana(i, j);
					p=Medijana(i, j);
				}
				else {
					//nova->IvicaMedijana(i, j);
					p=IvicaMedijana(i, j);
				}
				nova.getPixel(i, j).oboji(p.getR(), p.getG(), p.getB());
			}
		}

		return nova;
	}

}
