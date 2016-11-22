package main.java.kalender;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Uhrzeit;
import main.java.kalender.interfaces.Woche;

public class DatumImpl implements Datum {

	private Calendar intern;
	
	public DatumImpl(Tag tag){
		this(tag,new UhrzeitImpl());
	}
	public DatumImpl(Tag tag, Uhrzeit uhrzeit ) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, tag.getJahr());
		intern.set(Calendar.MONTH, tag.getMonat());
		intern.set(Calendar.DAY_OF_MONTH,tag.getTagImMonat());
		intern.set(Calendar.HOUR_OF_DAY,uhrzeit.getStunde());
		intern.set(Calendar.MINUTE,uhrzeit.getMinuten());
	}

	public DatumImpl(Datum d) {
		this(d.getTag(), d.getUhrzeit());
	}

	private DatumImpl(Calendar intern) {
		this.intern = intern;
	}
	
	
	@Override
	public int compareTo(Datum o) {
		return intern.compareTo(o.inBasis());
	}

	@Override
	public Tag getTag() {
		return new TagImpl(intern.get(Calendar.YEAR),intern.get(Calendar.DAY_OF_YEAR));
	}

	@Override
	public Woche getWoche() {
		return new WocheImpl(intern.get(Calendar.YEAR),intern.get(Calendar.MONTH),intern.get(Calendar.WEEK_OF_MONTH));
	}

	@Override
	public Monat getMonat() {
		return new MonatImpl(intern.get(Calendar.YEAR),intern.get(Calendar.MONTH));
	}

	@Override
	public Uhrzeit getUhrzeit() {
		return new UhrzeitImpl(intern.get(Calendar.HOUR_OF_DAY),intern.get(Calendar.MINUTE));
	}

	@Override
	public int getJahr() {
		return intern.get(Calendar.YEAR);
	}

	@Override
	public int getTagImMonat() {
		return intern.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public int getTagImJahr() {
		return intern.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public int getWocheImMonat() {
		return intern.get(Calendar.WEEK_OF_MONTH);
	}

	@Override
	public int getWocheImJahr() {
		return intern.get(Calendar.WEEK_OF_YEAR);
	}

	@Override
	public int getMonatImJahr() {
		return intern.get(Calendar.MONTH);
	}

	@Override
	public Datum add(Dauer dauer) {
		Calendar newCalendar = this.inBasis();
		newCalendar.add(Calendar.MINUTE, dauer.inMinuten());
		return new DatumImpl(newCalendar);
	}

	@Override
	public Datum sub(Dauer dauer) {
		Calendar newCalendar = this.inBasis();
		newCalendar.add(Calendar.MINUTE, -dauer.inMinuten());
		return new DatumImpl(newCalendar);
	}

	@Override
	public Dauer abstand(Datum d) {
		return new DauerImpl(inMinuten()-d.inMinuten());
	}

	@Override
	public long differenzInTagen(Datum d) {
		return getTag().differenzInTagen(d.getTag());
	}

	@Override
	public int inMinuten() {
		return (int) (intern.getTimeInMillis() / 1000L / 60L);
	}

	@Override
	public Calendar inBasis() {
		return (Calendar) intern.clone();
	}
	
	@Override
	public String toString() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(intern.getTime());
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
		DatumImpl other = (DatumImpl) obj;
		if (intern == null) {
			if (other.intern != null)
				return false;
		} else if (this.compareTo(other) != 0)
			return false;
		return true;
	}
	
	
}
