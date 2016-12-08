package main.java.kalender.model;

import java.text.DateFormat;
import java.util.Calendar;

import main.java.kalender.model.interfaces.Uhrzeit;

public class UhrzeitImpl implements Uhrzeit {
	private Calendar intern;

	public UhrzeitImpl(){
		this(0,0);
	}
	
	public UhrzeitImpl(int stunde, int minute) {
		if (stunde < 0 || stunde > 24 || (stunde == 24 && minute != 0) || minute < 0 || minute > 59) {
			throw new IllegalArgumentException("Intervall fuer Stunde [0,24), fuer Minute [0,59] oder 24:00");
		}
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.HOUR_OF_DAY, stunde);
		intern.set(Calendar.MINUTE, minute);
	}

	public UhrzeitImpl(Uhrzeit o) {
		this(o.getStunde(), o.getMinuten());
	}

	@Override
	public int compareTo(Uhrzeit o) {
		if(o.getStunde() == this.getStunde() && o.getMinuten() == this.getMinuten())
			return 0;
		if(o.getStunde() < this.getStunde())
			return -1;
		if(o.getStunde() > this.getStunde())
			return 1;
		if(o.getMinuten() < this.getMinuten())
			return -1;
		return 1;
	}

	@Override
	public int getStunde() {
		return intern.get(Calendar.HOUR_OF_DAY);
	}

	@Override
	public int getMinuten() {
		return intern.get(Calendar.MINUTE);
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
		UhrzeitImpl other = (UhrzeitImpl) obj;
		if (intern == null) {
			if (other.intern != null)
				return false;
		} else if (!(this.intern.compareTo(other.intern) != 0))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(intern.getTime());
	}

	
}
