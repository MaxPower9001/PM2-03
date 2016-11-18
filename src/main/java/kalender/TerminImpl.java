package main.java.kalender;

import java.util.Map;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.Woche;

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
