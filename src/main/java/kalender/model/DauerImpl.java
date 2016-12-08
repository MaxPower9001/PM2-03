package main.java.kalender.model;

import main.java.kalender.model.interfaces.Datum;
import main.java.kalender.model.interfaces.Dauer;

public class DauerImpl implements Dauer {

	final static int STUNDEINMINUTEN = 60;
	final static int TAGINMINUTEN = 24 * STUNDEINMINUTEN;
	final static int WOCHEINMINUTEN = 7 * TAGINMINUTEN;

	private int minuten;
	
	public DauerImpl(Datum d1, Datum d2) {
		this(d2.inMinuten()>d1.inMinuten() ? d2.inMinuten()-d1.inMinuten() : d1.inMinuten()-d2.inMinuten());
	}

	public DauerImpl(int minuten) {
		this.minuten = minuten;
	}
	
	public DauerImpl(int stunden, int minuten) {
		this(stunden*STUNDEINMINUTEN + minuten);
	}

	public DauerImpl(int tage, int stunden, int minuten) {
		this(tage*TAGINMINUTEN + stunden*STUNDEINMINUTEN + minuten);
	}

	@Override
	public int compareTo(Dauer o) {
		return inMinuten() - o.inMinuten();
	}

	@Override
	public int inMinuten() {
		return minuten;
	}

	@Override
	public int inStunden() {
		return minuten / STUNDEINMINUTEN;
	}

	@Override
	public int inTagen() {
		return minuten / TAGINMINUTEN;
	}

	@Override
	public int inWochen() {
		return minuten / WOCHEINMINUTEN;
	}

	@Override
	public int anteilMinuten() {
		int restAnteile = anteilWochen() * WOCHEINMINUTEN + anteilTage() * TAGINMINUTEN + anteilStunden() * STUNDEINMINUTEN;
		return this.minuten - restAnteile;
	}

	@Override
	public int anteilStunden() {
		int restAnteile = anteilWochen() * WOCHEINMINUTEN + anteilTage() * TAGINMINUTEN;
		return (this.minuten - restAnteile) / STUNDEINMINUTEN;	
	}

	@Override
	public int anteilTage() {
		int restAnteile = anteilWochen() * WOCHEINMINUTEN;
		return (this.minuten - restAnteile) / TAGINMINUTEN;		
	}

	@Override
	public int anteilWochen() {
		return this.minuten / WOCHEINMINUTEN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + minuten;
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
		DauerImpl other = (DauerImpl) obj;
		if (minuten != other.minuten)
			return false;
		return true;
	}
	
	
	
}
