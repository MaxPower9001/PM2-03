package main.java.kalender.model;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import main.java.kalender.model.interfaces.Datum;
import main.java.kalender.model.interfaces.Tag;
import main.java.kalender.model.interfaces.Uhrzeit;

public class TagImpl implements Tag {

	private Calendar intern; 
	
	public TagImpl(int jahr, int tagImJahr) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, jahr);
		intern.set(Calendar.DAY_OF_YEAR, tagImJahr);
	}
	public TagImpl(int jahr, int monat, int tagImMonat) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, jahr);
		intern.set(Calendar.MONTH, monat);
		intern.set(Calendar.DAY_OF_MONTH, tagImMonat);
	}
	
	public TagImpl(Tag tag) {
		intern = tag.inBasis();
	}

	@Override
	public Datum getStart() {
		Tag tag = new TagImpl(this);
		Uhrzeit uhrzeit = new UhrzeitImpl(0,0);
		return new DatumImpl(tag,uhrzeit);
	}

	@Override
	public Datum getEnde() {
		Tag tag = new TagImpl(this);
		Uhrzeit uhrzeit = new UhrzeitImpl(23,59);
		return new DatumImpl(tag,uhrzeit);
	}

	@Override
	public int compareTo(Tag o) {
		return intern.compareTo(o.inBasis());
	}

	@Override
	public int getJahr() {
		return intern.get(Calendar.YEAR);
	}

	@Override
	public int getMonat() {
		return intern.get(Calendar.MONTH);
	}

	@Override
	public int getTagImJahr() {
		return intern.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public int getTagImMonat() {
		return intern.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public long differenzInTagen(Tag other) {
		long thisMillis = intern.getTimeInMillis();
		long otherMillis = other.inBasis().getTimeInMillis();
		long diffMillis = thisMillis-otherMillis;
		//long diffMillis = thisMillis > otherMillis ? thisMillis - otherMillis : otherMillis - thisMillis;
		return diffMillis / 1000 / 60 / 60 / 24;
	}

	@Override
	public Calendar inBasis() {
		return (Calendar) intern.clone();
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
		TagImpl other = (TagImpl) obj;
		if (intern == null) {
			if (other.intern != null)
				return false;
		} else if (this.intern.compareTo(other.intern) != 0)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return DateFormat.getDateInstance(DateFormat.SHORT).format(intern.getTime());
	}
	
	

}
