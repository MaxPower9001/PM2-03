package kalender;

import java.util.Map;

import kalender.interfaces.Datum;
import kalender.interfaces.Dauer;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Termin;
import kalender.interfaces.Woche;

public class TerminImpl implements Termin {

	public TerminImpl(String beschreibung, Datum datum, Dauer dauer) {
	}


	@Override
	public int compareTo(Termin o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBeschreibung() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datum getDatum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dauer getDauer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Termin verschiebeAuf(Datum datum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Datum, Termin> termineIn(Monat monat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Datum, Termin> termineIn(Woche woche) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Datum, Termin> termineAn(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

}
