package main.java.kalender;

import java.util.Calendar;
import java.util.GregorianCalendar;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Uhrzeit;
import main.java.kalender.interfaces.Woche;

public class MonatImpl implements Monat {

	private Calendar intern;

	public MonatImpl(int jahr, int monat) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, jahr);
		intern.set(Calendar.MONTH, monat);
	}

	@Override
	public Datum getStart() {
		Tag tag = new TagImpl(intern.get(Calendar.YEAR),intern.get(Calendar.MONTH),intern.getActualMinimum(Calendar.DAY_OF_MONTH));
		Uhrzeit uhrzeit = new UhrzeitImpl(0,0);
		return new DatumImpl(tag,uhrzeit);
	}

	@Override
	public Datum getEnde() {
		Tag tag = new TagImpl(intern.get(Calendar.YEAR),intern.get(Calendar.MONTH),intern.getActualMaximum(Calendar.DAY_OF_MONTH));
		Uhrzeit uhrzeit = new UhrzeitImpl(0,0);
		return new DatumImpl(tag,uhrzeit);	
	}

	@Override
	public int getMonat() {
		return intern.get(Calendar.MONTH);
	}

	@Override
	public int getJahr() {
		return intern.get(Calendar.YEAR);
	}

}
