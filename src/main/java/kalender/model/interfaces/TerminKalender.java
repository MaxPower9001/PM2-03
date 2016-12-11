package main.java.kalender.model.interfaces;

import java.util.List;
import java.util.Map;

public interface TerminKalender {

	/*
	 * Trägt eine Termin in den Kalender ein
	 */
	public boolean eintragen(Termin termin);

	/*
	 * Verschiebt das Datum des Termins auf das übergebene Datum
	 * Gibt den verschobenen Termin zur�ck, null wenn der Termin nicht vorhanden
	 */
	public Termin verschiebenAuf(Termin termin, Datum datum);

	/*
	 * Löscht den Termin termin aus dem Kalender gibt true zurück, wenn termin
	 * enthalten ist, sonst false
	 */
	public boolean terminLoeschen(Termin termin);

	/*
	 * Prüft, ob termin im Kalender enthalten ist
	 */
	public boolean enthaeltTermin(Termin termin);

	/*
	 * Liefert alle Termine eines Tages als Tabelle zurück. Unter einem Datum
	 * sind alle Termine mit gleichem Datum eingetragen Der Schlüssel der
	 * Tabelle ist ein Datum. Der Wert die Liste der Termine mit gleichem Datum.
	 * 
	 * Die Termine eines Tages, sind die Termine, die zwischen 00:00 Uhr und
	 * 23:59 liegen
	 */
	public Map<Datum, List<Termin>> termineFuerTag(Tag tag);

	/*
	 * Liefert alle Termine einer Woche als Tabelle zurück. Unter einem Datum
	 * sind alle Termine mit gleichem Datum eingetragen Der Schlüssel der
	 * Tabelle ist ein Datum. Der Wert die Liste der Termine mit gleichem Datum.
	 * 
	 * Die Termine einer Woche, sind die Termine, die zwischen 00:00 Uhr des
	 * ersten Tages der Woche und 23:59 des letzen Tages der Woche liegen.
	 */
	public Map<Datum, List<Termin>> termineFuerWoche(Woche woche);

	/*
	 * Liefert alle Termine eines Monats als Tabelle zurück. Unter einem Datum
	 * sind alle Termine mit gleichem Datum eingetragen Der Schlüssel der
	 * Tabelle ist ein Datum. Der Wert die Liste der Termine mit gleichem Datum.
	 * 
	 * Die Termine eines Monats, sind die Termine, die zwischen 00:00 Uhr des
	 * ersten Tages des Monats und 23:59 des letzen Tages des Monats liegen.
	 */
	public Map<Datum, List<Termin>> termineFuerMonat(Monat monat);
}
