package Image;

public class Piksel {
	char r, g, b;
	char opacity;
	short offset;
	public Piksel(char rr , char gg , char bb , short off, char op) {r=rr; g=gg; b=bb; opacity=op;offset=off; }
	public Piksel() {
		r=0;
		g=0;
		b=0;
		opacity=0;
		offset=0;
	}
	public Piksel(int rr,int gg, int bb) {
		r=(char)rr;
		g=(char)gg;
		b=(char)bb;
	}public Piksel(double rr,double gg, double bb,int of,double op) {
		r=(char)rr;
		g=(char)gg;
		b=(char)bb;
		opacity=(char)op;
		offset=(short)of;
	}
	public char getR() { return r; }
	public char getG() { return g; }
	public char getB()  { return b; }
	public char getOpacity()  { return opacity; }
	public short getOffset()  { return offset; }
	
	void oboji(char rr, char gg, char bb) { 
		r = rr; g = gg; b = bb; opacity = 0xFF;
	}
}
