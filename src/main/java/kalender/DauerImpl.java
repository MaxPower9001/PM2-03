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
	}

	public DauerImpl(int minuten) {
	}
	
	public DauerImpl(int stunden, int minuten) {
	}

	public DauerImpl(int tage, int stunden, int minuten) {
	}

	@Override
	public int compareTo(Dauer o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int inMinuten() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int inStunden() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int inTagen() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int inWochen() {
		// TODO Auto-generated method stub
		return 0;
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
