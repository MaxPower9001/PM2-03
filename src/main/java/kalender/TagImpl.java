package main.java.kalender;

import java.util.Calendar;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Tag;

public class TagImpl implements Tag {

	private Calendar intern; 
	
	public TagImpl(int jahr, int tagImJahr) {
	}
	public TagImpl(int jahr, int monat, int tagImMonat) {
	}
	
	public TagImpl(Tag tag) {
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
	public int compareTo(Tag o) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Calendar inBasis() {
		return (Calendar) intern.clone();
	}

}
