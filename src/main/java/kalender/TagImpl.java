package main.java.kalender;

import java.util.Calendar;
import java.util.GregorianCalendar;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Uhrzeit;

public class TagImpl implements Tag {

	private Calendar intern; 
	
	public TagImpl(int jahr, int tagImJahr) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, jahr);
		intern.set(Calendar.DAY_OF_YEAR, tagImJahr);
	}
	public TagImpl(int jahr, int monat, int tagImMonat) {
		intern = new GregorianCalendar(jahr,monat,tagImMonat);
	}
	
	public TagImpl(Tag tag) {
		intern = tag.inBasis();
	}

	@Override
	public Datum getStart() {
		Tag tag = new TagImpl(intern.get(Calendar.YEAR),intern.get(Calendar.MONTH),intern.get(Calendar.DAY_OF_MONTH));
		Uhrzeit uhrzeit = new UhrzeitImpl(0,0);
		return new DatumImpl(tag,uhrzeit);
	}

	@Override
	public Datum getEnde() {
		Tag tag = new TagImpl(intern.get(Calendar.YEAR),intern.get(Calendar.MONTH),intern.get(Calendar.DAY_OF_MONTH));
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
		long diffMillis = thisMillis > otherMillis ? thisMillis - otherMillis : otherMillis - thisMillis;
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
		} else if (!(this.intern.compareTo(other.intern) != 0))
			return false;
		return true;
	}
	
	

}
