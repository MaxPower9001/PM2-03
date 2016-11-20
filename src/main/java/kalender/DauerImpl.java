package main.java.kalender;

import java.util.Calendar;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Uhrzeit;
import main.java.kalender.interfaces.Woche;

public class DauerImpl implements Dauer {

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int anteilStunden() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int anteilTage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int anteilWochen() {
		// TODO Auto-generated method stub
		return 0;
	}

}
