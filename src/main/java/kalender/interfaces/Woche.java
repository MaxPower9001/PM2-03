package main.java.kalender.interfaces;

import java.util.List;

public interface Woche extends DatumsGroesse {

	public int getJahr();
	public int getMonat();
	public int getWocheImMonat();
	public int getWocheImJahr();
	
	// Baby dont hurt me
	public List<Tag> getTageDerWoche();
}
