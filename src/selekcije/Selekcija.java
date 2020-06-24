package selekcije;

import java.util.ArrayList;

public class Selekcija {
	String ime;
	ArrayList<Pravougaonik> niz=new ArrayList();
	boolean aktivna;
	
	public Selekcija(String s, ArrayList<Pravougaonik> p) {
		ime=s;
	
		niz.clear();
		aktivna = false;
		for(int i=0;i<p.size();i++) {
			niz.add(p.get(i));
		}
	}
	public boolean USelekciji(int x, int y) {
		//bool temp = 0;
		for (Pravougaonik i : niz) {
			if (i.UPravougaoniku(x, y)) return true;
		}
		 return false;
		
	}
	public void setStanje(boolean a) { aktivna = a; }
	public boolean getStanje() { return aktivna; }
	public String getIme() { return ime; }
	public ArrayList<Pravougaonik> getNiz() { return niz; }
}