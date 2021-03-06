package main.java.kalender.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.kalender.model.interfaces.Datum;
import main.java.kalender.model.interfaces.DatumsGroesse;
import main.java.kalender.model.interfaces.Monat;
import main.java.kalender.model.interfaces.Tag;
import main.java.kalender.model.interfaces.Termin;
import main.java.kalender.model.interfaces.TerminKalender;
import main.java.kalender.model.interfaces.TerminMitWiederholung;
import main.java.kalender.model.interfaces.Woche;

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
	public Termin verschiebenAuf(Termin termin, Datum datum) {
		int index = termine.indexOf(termin);
		if(index > -1)
		{
			termine.get(index).verschiebeAuf(datum);
			return termine.get(index);
		}
		return null;
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
