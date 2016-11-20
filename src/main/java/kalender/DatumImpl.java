package main.java.kalender;

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
		this(new GregorianCalendar(tag.getJahr(), tag.getMonat(), tag.getTagImMonat(), uhrzeit.getStunde(), uhrzeit.getMinuten()));
	}

	public DatumImpl(Datum d) {
		this(d.getTag(), d.getUhrzeit());
	}

	private DatumImpl(Calendar intern) {
		this.intern = intern;
	}
	
	
	@Override
	public int compareTo(Datum o) {
		return getTag().compareTo(o.getTag()) == 0 ? 0 : getUhrzeit().compareTo(o.getUhrzeit());
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
		intern.add(Calendar.MINUTE, dauer.inMinuten());
		return this;
	}

	@Override
	public Datum sub(Dauer dauer) {
		intern.add(Calendar.MINUTE, -dauer.inMinuten());
		return this;
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
		return new GregorianCalendar(getJahr(), getMonatImJahr(), getTagImMonat());
	}

}
