package kalender;

import java.util.Calendar;

import kalender.interfaces.Datum;
import kalender.interfaces.Dauer;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Uhrzeit;
import kalender.interfaces.Woche;

public class MonatImpl implements Monat {

	private Calendar intern;

	public MonatImpl(int jahr, int monat) {
	}

	@Override
	public Datum getStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datum getEnde() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMonat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getJahr() {
		// TODO Auto-generated method stub
		return 0;
	}

}
