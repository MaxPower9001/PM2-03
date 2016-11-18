package kalender;

import java.util.List;
import java.util.Map;

import kalender.interfaces.Datum;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Termin;
import kalender.interfaces.TerminKalender;
import kalender.interfaces.Woche;

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
