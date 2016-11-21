package main.java.kalender;

import java.util.Calendar;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Uhrzeit;
import main.java.kalender.interfaces.Woche;

public class DauerImpl implements Dauer {

	final int STUNDEINMINUTEN = 60;
	final int TAGINMINUTEN = 24 * STUNDEINMINUTEN;
	final int WOCHEINMINUTEN = 7 * TAGINMINUTEN;

	private int minuten;
	
	public DauerImpl(Datum d1, Datum d2) {
		this(d2.inMinuten()>d1.inMinuten() ? d2.inMinuten()-d1.inMinuten() : d1.inMinuten()-d2.inMinuten());
	}

	public DauerImpl(int minuten) {
		this.minuten = minuten;
	}
	
	public DauerImpl(int stunden, int minuten) {
		this(stunden*60 + minuten);
	}

	public DauerImpl(int tage, int stunden, int minuten) {
		this(tage*24*60 + stunden*60 + minuten);
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
		return minuten / 60;
	}

	@Override
	public int inTagen() {
		return minuten / 60 / 24;
	}

	@Override
	public int inWochen() {
		return minuten / 60 / 24 / 7;
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
