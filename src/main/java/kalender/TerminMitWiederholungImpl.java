package main.java.kalender;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.java.kalender.TerminMitWiederholungImpl.WiederholungImpl;
import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.DatumsGroesse;
import main.java.kalender.interfaces.Dauer;
import main.java.kalender.interfaces.IntervallIterator;
import main.java.kalender.interfaces.Monat;
import main.java.kalender.interfaces.Tag;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.TerminMitWiederholung;
import main.java.kalender.interfaces.Wiederholung;
import main.java.kalender.interfaces.Woche;

public class TerminMitWiederholungImpl extends TerminImpl implements TerminMitWiederholung {

	private Wiederholung wdh;

	public TerminMitWiederholungImpl(String beschreibung, Datum start, Dauer dauer, WiederholungType type, int anzahl,
			int zyklus) {
		super(beschreibung,start,dauer);
		this.wdh = new WiederholungImpl(type, anzahl, zyklus);
	}

	public TerminMitWiederholungImpl(String beschreibung, Datum start, Dauer dauer, Wiederholung wdh) {
		this(beschreibung,start,dauer,wdh.getType(),wdh.anzahl(),wdh.getZyklus());
	}
	
	
	public Wiederholung getWdh() {
		return new WiederholungImpl(wdh);
	}


	@Override
	public Map<Datum, Termin> termineIn(Monat monat) {
		return termineFuer(monat);
	}

	@Override
	public Map<Datum, Termin> termineIn(Woche woche) {
		return termineFuer(woche);
	}

	@Override
	public Map<Datum, Termin> termineAn(Tag tag) {
		return termineFuer(tag);
	}

	
	/**
	 * Beispiel für den naiven Iterator, der alle Wiederholungen explizit aufzaehlt
	 */
	@Override
	public Iterator<Termin> iterator() {
		return new Iterator<Termin>() {
			private TerminMitWiederholung current = null;
			private int howManySeen = 0;

			@Override
			public boolean hasNext() {
				return howManySeen <= wdh.anzahl();
			}

			@Override
			public Termin next() {
				if (current == null) {
					current = TerminMitWiederholungImpl.this;
				} else {
					current = new TerminMitWiederholungImpl(getBeschreibung(), current.getWdh().naechstesDatum(),
							getDauer(), current.getWdh().sub(1));
				}
				howManySeen += 1;
				return current;
			}
		};
	}


	@Override
	public IntervallIterator<Datum> intervallIterator(int von, int bis) {
		return new IntervallIterator<Datum>() {

			private Datum current = null;
			private int cursor = von;
			
			@Override
			public boolean hasNext() {
				return cursor <= bis;
			}

			@Override
			public Datum next() {
				current = TerminMitWiederholungImpl.this.getWdh().naechstesDatum(cursor);				
				cursor += 1;
				return current;
			}

		};
	}


	@Override
	public Map<Datum, Termin> termineFuer(DatumsGroesse groesse) {
		int start = wdh.naechstesIntervall(groesse.getStart());
		int ende  = wdh.vorherigesIntervall(groesse.getEnde());
		
		
		// wenn endIndex > maxIntervallIndex dann setze endIndex auf
		// maxIntervallIndex
		if(ende > getWdh().maxIntervallIndex())
		{
			ende = getWdh().maxIntervallIndex();
		}
		//
		// wenn endIndex < startIndex || endIndex < 0 || startIndex < 0 ||
		// endIndex > maxIntervallIndex ???
		// gib null zurück
		if(ende < start || ende < 0 || start < 0)
		{
			return null;
		}
		// Map erzeugen und die Wiederholungen einsammeln
		Map<Datum,Termin> retVal = new HashMap<Datum, Termin>();
		IntervallIterator<Datum> intervallIterator = this.intervallIterator(start, ende);
		while(intervallIterator.hasNext())
		{
			retVal.put(intervallIterator.next(), this);
		}
		return retVal;
	}

	public class WiederholungImpl implements Wiederholung {

		private WiederholungType wdhType;
		private int anzahl;
		private int cycle;

		public WiederholungImpl(WiederholungType wdhType, int anzahl, int cyclus) {
			this.wdhType = wdhType;
			this.anzahl = anzahl;
			this.cycle = cyclus;
		}

		public WiederholungImpl(Wiederholung wdh) {
			this(wdh.getType(), wdh.anzahl(), wdh.getZyklus());
		}
		@Override
		public WiederholungType getType() {
			return wdhType;
		}
		@Override
		public int getZyklus() {
			return cycle;
		}
		@Override
		public int anzahl() {
			return anzahl;
		}
		@Override
		public int maxIntervallIndex() {
			return anzahl;
		}
		@Override
		public int intervallLaenge() {
			return cycle * wdhType.inTagen();
		}
		/*
		 * @see kalender.interfaces.Wiederholung#naechstesIntervall(kalender.interfaces.Datum)
		 * 
		 * Methode liefert den Intervallindex für das einem Datum nachfolgendem
		 * Intervall. Es werden auch Intervalle berechnet, die außerhalb des
		 * gültigen Bereichs maxIntervallIndex liegen. Nutzer der Methode müssen
		 * sicher stellen, dass die Gültigkeit des Index geprüft wird.
		 */
		@Override
		public int naechstesIntervall(Datum dat) {
			long diff = dat.differenzInTagen(getDatum());
			long div = diff / intervallLaenge();
			long mod = diff % intervallLaenge();

			/*
			 * div <= 0 und mod < 0: tag liegt vor dem ersten Termin der
			 * Wiederholung (Intervall 0) div > 0 && mod > 0: tag liegt vor dem
			 * Termin im Intervall div+1 div >= 0 && mod == 0: tag ist ein
			 * Termin der Wiederholung im Intervall div
			 */
			int intervallIndex = -1;
			if (div <= 0 && mod < 0)
				intervallIndex = 0;
			if (diff > 0 && mod > 0)
				intervallIndex = (int) div + 1;
			if (diff >= 0 && mod == 0)
				intervallIndex = (int) div;
			return intervallIndex;
		}

		/*
		 * @see kalender.interfaces.Wiederholung#vorherigesIntervall(kalender.
		 * interfaces.Datum)
		 * 
		 * Methode liefert den Intervallindex für das einem Datum vorausgehenden
		 * Intervall. Es werden auch Intervalle berechnet, die außerhalb des
		 * gültigen Bereichs maxIntervallIndex liegen. Nutzer der Methode müssen
		 * sicher stellen, dass die Gültigkeit des Index geprüft wird.
		 */
		@Override
		public int vorherigesIntervall(Datum dat) {
			long diff = dat.differenzInTagen(getDatum());
			long div = diff / intervallLaenge();
			long mod = diff % intervallLaenge();

			/*
			 * diff < 0: dann liegt das Datum vor dem ersten Termin Fehler div
			 * >= 0 && mod = 0: dann interval = div sonst intervall =
			 * (naechstesIntervall(dat) -1)
			 */

			if (diff < 0)
				return -1;
			if (div >= 0 && mod == 0)
				return (int) div;
			return naechstesIntervall(dat) - 1;
		}

		/*
		 * @see kalender.interfaces.Wiederholung#naechstesDatum()
		 */
		@Override
		public Datum naechstesDatum() {
			return naechstesDatum(1);
		}
		/*
		 * @see kalender.interfaces.Wiederholung#naechstesDatum(int)
		 */
		@Override
		public Datum naechstesDatum(int faktor) {
			int anzahlTage = faktor * intervallLaenge();
			return new DatumImpl(getDatum()).add(new DauerImpl(anzahlTage, 0, 0));
		}
		/*
		 * @see kalender.interfaces.Wiederholung#sub(int)
		 */
		@Override
		public Wiederholung sub(int wdhCount) {
			return new WiederholungImpl(wdhType, anzahl - wdhCount, cycle);
		}
		/*
		 * @see kalender.interfaces.Wiederholung#add(int)
		 */
		@Override
		public Wiederholung add(int wdhCount) {
			return new WiederholungImpl(wdhType, anzahl + wdhCount, cycle);
		}


	}

}
