package main.java.kalender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Woche;

public class WocheImpl implements Woche {

	private Calendar intern;

	public WocheImpl(int jahr, int monat, int wocheImMonat) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, jahr);
		intern.set(Calendar.MONTH, monat);
		intern.set(Calendar.WEEK_OF_MONTH, wocheImMonat);
	}

	@Override
	public List<Tag> getTageDerWoche()
	{
		List<Tag> retVal = new ArrayList<>();
				
		retVal.add(getStart().getTag());
		
		Datum iterator = new DatumImpl(getStart().getTag());		
		
		while(iterator.abstand(getEnde()).inTagen() > 0)
		{
			iterator = iterator.add(new DauerImpl(1, 0, 0));
			retVal.add(iterator.getTag());
		}	
		
		return retVal;
	}
	
	@Override
	public int getJahr() {
		return intern.get(Calendar.YEAR);
	}

	@Override
	public int getMonat() {
		return intern.get(Calendar.MONTH);
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
	public Datum getStart() {
		Calendar copy = (Calendar) intern.clone();
		copy.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return new DatumImpl(
				new TagImpl(copy.get(Calendar.YEAR), copy.get(Calendar.MONTH), copy.get(Calendar.DAY_OF_MONTH)));
	}

	@Override
	public Datum getEnde() {
		Calendar copy = (Calendar) intern.clone();
		copy.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new DatumImpl(
				new TagImpl(copy.get(Calendar.YEAR), copy.get(Calendar.MONTH), copy.get(Calendar.DAY_OF_MONTH)),
				new UhrzeitImpl(23, 59));
	}


	@Override
	public String toString() {
		return String.format("Woche %d,%d.%d [" + getStart() + "," + getEnde() + "]", getWocheImMonat(), getMonat() + 1,
				getJahr());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intern == null) ? 0 : intern.hashCode());
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
		WocheImpl other = (WocheImpl) obj;
		if (intern == null) {
			if (other.intern != null)
				return false;
		} else if (!(this.intern.compareTo(other.intern) != 0))
			return false;
		return true;
	}
	
	

}
