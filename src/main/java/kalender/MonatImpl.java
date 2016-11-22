package main.java.kalender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
		Uhrzeit uhrzeit = new UhrzeitImpl(23,59);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intern == null) ? 0 : intern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonatImpl other = (MonatImpl) obj;
		if (intern == null) {
			if (other.intern != null)
				return false;
		} else if (this.intern.compareTo(other.intern) != 0)
			return false;
		return true;
	}
}
