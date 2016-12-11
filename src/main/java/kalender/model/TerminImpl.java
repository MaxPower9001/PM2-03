package main.java.kalender.model;

import java.util.HashMap;
import java.util.Map;

import main.java.kalender.model.interfaces.Datum;
import main.java.kalender.model.interfaces.Dauer;
import main.java.kalender.model.interfaces.Monat;
import main.java.kalender.model.interfaces.Tag;
import main.java.kalender.model.interfaces.Termin;
import main.java.kalender.model.interfaces.Woche;

public class TerminImpl implements Termin {
	private String beschreibung;
	private Datum datum;
	private Dauer dauer;

	public TerminImpl(String beschreibung, Datum datum, Dauer dauer) {
		this.beschreibung = beschreibung;
		this.datum        = datum;
		this.dauer        = dauer;
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
		this.datum = datum;
		return this;
	}

	@Override
	public Map<Datum, Termin> termineIn(Monat monat) {
		Map<Datum, Termin> retVal = new HashMap<Datum, Termin>();
		
		if(datum.getMonat().equals(monat))
			retVal.put(datum, this);
		
		return retVal;
	}

	@Override
	public Map<Datum, Termin> termineIn(Woche woche) {
		Map<Datum, Termin> retVal = new HashMap<Datum, Termin>();
		
		if(datum.getWoche().equals(woche))
			retVal.put(datum, this);
		
		return retVal;
	}

	@Override
	public Map<Datum, Termin> termineAn(Tag tag) {
		Map<Datum, Termin> retVal = new HashMap<Datum, Termin>();
		
		if(datum.getTag().equals(tag))
			retVal.put(datum, this);
		
		return retVal;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + ((dauer == null) ? 0 : dauer.hashCode());
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
		TerminImpl other = (TerminImpl) obj;
		if (beschreibung == null) {
			if (other.beschreibung != null)
				return false;
		} else if (!beschreibung.equals(other.beschreibung))
			return false;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (dauer == null) {
			if (other.dauer != null)
				return false;
		} else if (!dauer.equals(other.dauer))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TerminImpl [beschreibung=" + beschreibung + ", datum=" + datum + ", dauer=" + dauer + "]";
	}
	
	
	
}
