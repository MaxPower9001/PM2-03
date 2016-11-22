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
	
	List<TerminMitWiederholung> termineMitWiederholung;
	List<Termin> termine;
	
	public TerminKalenderImpl() {
		termine = new ArrayList<>();
		termineMitWiederholung = new ArrayList<>();
	}	
	
	@Override
	public boolean eintragen(Termin termin) {
		if(!(termin instanceof TerminMitWiederholung))
			return termine.add(termin);
		return termineMitWiederholung.add((TerminMitWiederholung) termin);
		
	}

	@Override
	public void verschiebenAuf(Termin termin, Datum datum) {
		int index = termineMitWiederholung.indexOf(termin);
		if(index > -1)
		{
			termineMitWiederholung.get(index).verschiebeAuf(datum);
		}
		else {
			index = termine.indexOf(termin);
			if(index > -1)
				termine.get(index).verschiebeAuf(datum);
		}		
	}

	@Override
	public boolean terminLoeschen(Termin termin) {
		boolean versuchOans = termine.remove(termin);
		boolean versuchZwoa = termineMitWiederholung.remove(termin);
		return versuchOans || versuchZwoa;
	}

	@Override
	public boolean enthaeltTermin(Termin termin) {
		boolean versuchOans = termine.contains(termin);
		boolean versuchZwoa = termineMitWiederholung.contains(termin);
		return versuchOans || versuchZwoa;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerTag(Tag tag) {
		Map<Datum, List<Termin>> retVal = new HashMap<>();
		List<Termin> terminListe = new ArrayList<>();
				
		Datum tagDatum = new DatumImpl(tag);
		
		terminListe =
				termineMitWiederholung.stream().
				map(t -> t.termineFuer(tag)).
				filter(t -> t != null).
				map(t -> t.values()).
				flatMap(l -> l.stream()).
				collect(Collectors.toList());		
		
		terminListe.addAll(
				termine.stream().
				map(t -> t.termineAn(tag)).
				filter(t -> t != null).
				map(t -> t.values()).
				flatMap(l -> l.stream()).
				collect(Collectors.toList()));
		
		retVal.put(tagDatum, terminListe);
		
		return retVal;		
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerWoche(Woche woche) {
		Map<Datum, List<Termin>> retVal = new HashMap<>();
		
		
		woche.getTageDerWoche().stream().forEach(t -> 
		{
			Datum key = new DatumImpl(t);
			retVal.put(key, termineFuerTag(t).get(key));			
		});
		return retVal;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerMonat(Monat monat) {
		Map<Datum, List<Termin>> retVal = new HashMap<>();
		
		
		monat.getTageDesMonat().stream().forEach(t -> 
		{
			System.out.println(t);
			Datum key = new DatumImpl(t);
			List<Termin> temp = termineFuerTag(t).get(key);
			retVal.put(key, temp);			
		});
		return retVal;
	}
}
