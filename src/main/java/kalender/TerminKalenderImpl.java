package main.java.kalender;

import java.util.List;
import java.util.Map;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.TerminKalender;
import main.java.kalender.interfaces.Woche;

public class TerminKalenderImpl implements TerminKalender {

	@Override
	public boolean eintragen(Termin termin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void verschiebenAuf(Termin termin, Datum datum) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean terminLoeschen(Termin termin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enthaeltTermin(Termin termin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerTag(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerWoche(Woche woche) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerMonat(Monat monat) {
		// TODO Auto-generated method stub
		return null;
	}

}
