package main.java.kalender;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;

public class DauerImpl implements Dauer {

	final static int STUNDEINMINUTEN = 60;
	final static int TAGINMINUTEN = 24 * STUNDEINMINUTEN;
	final static int WOCHEINMINUTEN = 7 * TAGINMINUTEN;

	private int minuten;
	
	public DauerImpl(Datum d1, Datum d2) {
		this(d2.inMinuten()>d1.inMinuten() ? d2.inMinuten()-d1.inMinuten() : d1.inMinuten()-d2.inMinuten());
	}

	public DauerImpl(int minuten) {
		this.minuten = minuten;
	}
	
	public DauerImpl(int stunden, int minuten) {
		this(stunden*STUNDEINMINUTEN + minuten);
	}

	public DauerImpl(int tage, int stunden, int minuten) {
		this(tage*TAGINMINUTEN + stunden*STUNDEINMINUTEN + minuten);
	}

	@Override
	public int compareTo(Dauer o) {
		return inMinuten() == o.inMinuten() ? 0 : (inMinuten() < o.inMinuten() ? -1 : 1);
	}

	@Override
	public int inMinuten() {
		return minuten;
	}

	@Override
	public int inStunden() {
		return minuten / STUNDEINMINUTEN;
	}

	@Override
	public int inTagen() {
		return minuten / TAGINMINUTEN;
	}

	@Override
	public int inWochen() {
		return minuten / WOCHEINMINUTEN;
	}

	@Override
	public int anteilMinuten() {
		int restAnteile = anteilWochen() * WOCHEINMINUTEN + anteilTage() * TAGINMINUTEN + anteilStunden() * STUNDEINMINUTEN;
		return this.minuten - restAnteile;
	}

	@Override
	public int anteilStunden() {
		int restAnteile = anteilWochen() * WOCHEINMINUTEN + anteilTage() * TAGINMINUTEN;
		return (this.minuten - restAnteile) / STUNDEINMINUTEN;	
	}

	@Override
	public int anteilTage() {
		int restAnteile = anteilWochen() * WOCHEINMINUTEN;
		return (this.minuten - restAnteile) / TAGINMINUTEN;		
	}

	@Override
	public int anteilWochen() {
		return this.minuten / WOCHEINMINUTEN;
	}

}
