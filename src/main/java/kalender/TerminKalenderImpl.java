package main.java.kalender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.DatumsGroesse;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.TerminKalender;
import main.java.kalender.interfaces.TerminMitWiederholung;
import main.java.kalender.interfaces.Woche;

public class TerminKalenderImpl implements TerminKalender {
	
	List<Termin> termine;
	
	public TerminKalenderImpl() {
		termine = new ArrayList<>();
	}	
	
	@Override
	public boolean eintragen(Termin termin) {
		return termine.add(termin);		
	}

	@Override
	public void verschiebenAuf(Termin termin, Datum datum) {
		int index = termine.indexOf(termin);
		if(index > -1)
		{
			termine.get(index).verschiebeAuf(datum);
		}
	}

	@Override
	public boolean terminLoeschen(Termin termin) {
		return termine.remove(termin);
	}

	@Override
	public boolean enthaeltTermin(Termin termin) {
		return termine.contains(termin);
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerTag(Tag tag) {
		Map<Datum, List<Termin>> retVal = new HashMap<>();
		
		for(Termin t : termine)
		{
			Map<Datum,Termin> termineIn = t.termineAn(tag);
			termineIn.forEach((k,v) -> {
				if(!retVal.containsKey(k))
				{
					retVal.put(k, new ArrayList<Termin>());
				}
					retVal.get(k).add(t);
			});
		}
		return retVal;		
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerWoche(Woche woche) {
		Map<Datum, List<Termin>> retVal = new HashMap<>();
		
		for(Termin t : termine)
		{
			Map<Datum,Termin> termineIn = t.termineIn(woche);
			termineIn.forEach((k,v) -> {
				if(!retVal.containsKey(k))
				{
					retVal.put(k, new ArrayList<Termin>());
				}
					retVal.get(k).add(t);
			});
		}
		return retVal;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerMonat(Monat monat) {
		Map<Datum, List<Termin>> retVal = new HashMap<>();
				
		termine.forEach(t ->
		{
			Map<Datum,Termin> termineIn = t.termineIn(monat);
			termineIn.forEach((k,v) -> {
				if(!retVal.containsKey(k))
				{
					retVal.put(k, new ArrayList<Termin>());
				}
					retVal.get(k).add(t);
			});
		});
		
		return retVal;		
	}
}
