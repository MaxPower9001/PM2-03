package main.java.kalender;

import java.util.HashMap;
import java.util.Map;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.Woche;

public class TerminImpl implements Termin {
	String beschreibung;
	Datum datum;
	Dauer dauer;

	public TerminImpl(String beschreibung, Datum datum, Dauer dauer) {
	}


	@Override
	public int compareTo(Termin o) {
		return o.getDatum().compareTo(this.datum);
	}

	@Override
	public String getBeschreibung() {
		return beschreibung;
	}

	@Override
	public Datum getDatum() {
		return datum;
	}

	@Override
	public Dauer getDauer() {
		return dauer;
	}

	@Override
	public Termin verschiebeAuf(Datum datum) {
		return new TerminImpl(this.beschreibung, datum, this.dauer);
	}

	@Override
	public Map<Datum, Termin> termineIn(Monat monat) {
		Map<Datum, Termin> retVal = new HashMap<Datum, Termin>();
		
		if(datum.getMonat() == monat)
			retVal.put(datum, this);
		
		return retVal;
	}

	@Override
	public Map<Datum, Termin> termineIn(Woche woche) {
		Map<Datum, Termin> retVal = new HashMap<Datum, Termin>();
		
		if(datum.getWoche() == woche)
			retVal.put(datum, this);
		
		return retVal;
	}

	@Override
	public Map<Datum, Termin> termineAn(Tag tag) {
		Map<Datum, Termin> retVal = new HashMap<Datum, Termin>();
		
		if(datum.getTag() == tag)
			retVal.put(datum, this);
		
		return retVal;
	}

}
