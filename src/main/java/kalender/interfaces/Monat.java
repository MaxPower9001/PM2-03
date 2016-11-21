package main.java.kalender.interfaces;

import java.util.List;

public interface Monat extends DatumsGroesse {

	public int getMonat();
	public int getJahr();

	// Dont hurt me no more
	public List<Tag> getTageDesMonat();
}
