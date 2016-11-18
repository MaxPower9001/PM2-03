package kalender;

import java.util.Calendar;

import kalender.interfaces.Datum;
import kalender.interfaces.Dauer;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Uhrzeit;
import kalender.interfaces.Woche;

public class DatumImpl implements Datum {


	private Calendar intern;
	
	public DatumImpl(Tag tag){
	}
	public DatumImpl(Tag tag, Uhrzeit uhrzeit ) {
	}

	public DatumImpl(Datum d) {
	}

	private DatumImpl(Calendar intern) {
	}
	
	
	@Override
	public int compareTo(Datum o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tag getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Woche getWoche() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Monat getMonat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uhrzeit getUhrzeit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getJahr() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTagImMonat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTagImJahr() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWocheImMonat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWocheImJahr() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMonatImJahr() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Datum add(Dauer dauer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datum sub(Dauer dauer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dauer abstand(Datum d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long differenzInTagen(Datum d) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int inMinuten() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Calendar inBasis() {
		// TODO Auto-generated method stub
		return null;
	}

}
