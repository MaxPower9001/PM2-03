package kalender;

import java.util.Calendar;

import kalender.interfaces.Datum;
import kalender.interfaces.Tag;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMonat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTagImJahr() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTagImMonat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long differenzInTagen(Tag other) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Calendar inBasis() {
		// TODO Auto-generated method stub
		return null;
	}

}
