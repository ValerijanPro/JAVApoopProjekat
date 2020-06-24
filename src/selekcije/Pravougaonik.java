package selekcije;

public class Pravougaonik {
	int x, y; // x levodesno, y goredole
	int sirina, visina;

	public Pravougaonik(int xx,int yy, int sir, int vis) {x=xx;y=yy;sirina=sir;visina=vis;}
	public boolean UPravougaoniku(int xkoordinata, int ykoordinata) {
		//if (xkoordinata < 0 || ykoordinata < 0) throw GreskaIndexMatrice("Koordinate pravougaonika nisu validne");
		if (xkoordinata >= x && xkoordinata <= (x + sirina)) {
			if (ykoordinata >=y && ykoordinata <= (y + visina)) {
				return true;
			}
			return false;
		}
		return false;
	}
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSirina() { return sirina; }
	public int getVisina() { return visina; }
}
