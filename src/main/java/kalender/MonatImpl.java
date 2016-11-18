package main.java.kalender;

import java.util.Calendar;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Uhrzeit;
import main.java.kalender.interfaces.Woche;

public class MonatImpl implements Monat {

	private Calendar intern;

	public MonatImpl(int jahr, int monat) {
	}

	@Override
	public Datum getStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datum getEnde() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMonat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getJahr() {
		// TODO Auto-generated method stub
		return 0;
	}

}
