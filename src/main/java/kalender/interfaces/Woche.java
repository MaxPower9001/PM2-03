package main.java.kalender.interfaces;

import java.util.List;

public interface Woche extends DatumsGroesse {

	public int getJahr();
	public int getMonat();
	public int getWocheImMonat();
	public int getWocheImJahr();
	
	public List<Tag> getTageDerWoche();
}
