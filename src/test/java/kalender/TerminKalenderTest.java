package test.java.kalender;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.java.kalender.DatumImpl;
import main.java.kalender.DauerImpl;
import main.java.kalender.MonatImpl;
import main.java.kalender.TagImpl;
import main.java.kalender.TerminImpl;
import main.java.kalender.TerminKalenderImpl;
import main.java.kalender.TerminMitWiederholungImpl;
import main.java.kalender.UhrzeitImpl;
import main.java.kalender.WiederholungType;
import main.java.kalender.WocheImpl;
import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.TerminKalender;

public class TerminKalenderTest {

	@Test
	public void terminEinfachEintragenTest() {
		TerminKalender thisKalender = new TerminKalenderImpl();
		
		Termin terminEinzutragen = new TerminImpl("TestTermin",new DatumImpl(new TagImpl(2016,10,22)), new DauerImpl(52));
		Termin terminEinzutragen3 = new TerminImpl("TestTermin3",new DatumImpl(new TagImpl(2016,10,22),new UhrzeitImpl(14, 15)), new DauerImpl(52));
		Termin terminEinzutragen2 = new TerminImpl("TestTermin2",new DatumImpl(new TagImpl(2016,10,23)), new DauerImpl(1));
		
		assertTrue(thisKalender.eintragen(terminEinzutragen));
		assertTrue(thisKalender.eintragen(terminEinzutragen2));
		assertTrue(thisKalender.eintragen(terminEinzutragen3));
		
		Map<Datum,List<Termin>> termineImNovember = thisKalender.termineFuerMonat(new MonatImpl(2016,10));
		assertEquals(3,termineImNovember.values().size());
		
		Map<Datum,List<Termin>> termineInVierterWocheDesNovembers = thisKalender.termineFuerWoche(new WocheImpl(2016, 10, 4));
		assertEquals(3,termineInVierterWocheDesNovembers.values().size());
		
		Map<Datum,List<Termin>> termineAm22Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,22));
		assertEquals(2,termineAm22Nov.values().size());
		
		Map<Datum,List<Termin>> termineAm23Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,23));
		assertEquals(1,termineAm23Nov.values().size());
	}
	
	@Test
	public void terminEintragenFailTest()
	{
		TerminKalender thisKalender = new TerminKalenderImpl();
		
		Termin terminEinzutragen = new TerminImpl("TestTermin",new DatumImpl(new TagImpl(2016,10,22)), new DauerImpl(52));
		
		assertTrue(thisKalender.eintragen(terminEinzutragen));
		
		Map<Datum,List<Termin>> termineNichtImNovember = thisKalender.termineFuerMonat(new MonatImpl(2016,5));
		
		assertTrue(termineNichtImNovember.isEmpty());
	}
	
	@Test
	public void terminWiederholungEintragenTest() {
		TerminKalender thisKalender = new TerminKalenderImpl();
		
		Termin terminSerieEintragen = new TerminMitWiederholungImpl("it is just a dream", new DatumImpl(new TagImpl(2016,0,1)), new DauerImpl(10), WiederholungType.TAEGLICH, 365, 1);
		
		assertTrue(thisKalender.eintragen(terminSerieEintragen));
		
		Map<Datum,List<Termin>> termineImNovember = thisKalender.termineFuerMonat(new MonatImpl(2016,10));
		assertEquals(30,termineImNovember.values().size());
		
		Map<Datum,List<Termin>> termineInVierterWocheDesNovembers = thisKalender.termineFuerWoche(new WocheImpl(2016, 10, 4));
		assertEquals(7,termineInVierterWocheDesNovembers.values().size());
		
		Map<Datum,List<Termin>> termineAm22Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,22));
		assertEquals(1,termineAm22Nov.values().size());
	}
	
	@Test
	public void terminVerschiebenTest() {
		TerminKalender thisKalender = new TerminKalenderImpl();
		
		Termin terminEinzutragen = new TerminImpl("TestTermin",new DatumImpl(new TagImpl(2016,10,22)), new DauerImpl(52));
		
		assertTrue(thisKalender.eintragen(terminEinzutragen));
		
		
		Map<Datum,List<Termin>> termineAm22Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,22));
		assertEquals(1,termineAm22Nov.values().size());
		
		Map<Datum,List<Termin>> termineAm23Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,23));
		assertEquals(0,termineAm23Nov.values().size());
		
		thisKalender.verschiebenAuf(terminEinzutragen, new DatumImpl(new TagImpl(2016,10,23)));
		
		termineAm22Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,22));
		assertEquals(0,termineAm22Nov.values().size());
		
		termineAm23Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,23));
		assertEquals(1,termineAm23Nov.values().size());
	}
	
	@Test
	public void terminLoeschenTest() {
		TerminKalender thisKalender = new TerminKalenderImpl();
		
		Termin terminEinzutragen = new TerminImpl("TestTermin",new DatumImpl(new TagImpl(2016,10,22)), new DauerImpl(52));
		
		assertTrue(thisKalender.eintragen(terminEinzutragen));
		
		
		Map<Datum,List<Termin>> termineAm22Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,22));
		assertEquals(1,termineAm22Nov.values().size());
		
		thisKalender.terminLoeschen(terminEinzutragen);
		
		termineAm22Nov = thisKalender.termineFuerTag(new TagImpl(2016,10,22));
		assertEquals(0,termineAm22Nov.values().size());
	}

}
