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
import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.TerminKalender;

public class TerminKalenderTest {

	@Test
	public void terminEintragenTest() {
		TerminKalender thisKalender = new TerminKalenderImpl();
		
		Termin terminEinzutragen = new TerminImpl("TestTermin",new DatumImpl(new TagImpl(2016,10,22)), new DauerImpl(52));
		
		assertTrue(thisKalender.eintragen(terminEinzutragen));
		
		Map<Datum,List<Termin>> termineImNovember = thisKalender.termineFuerMonat(new MonatImpl(2016,10));
		assertEquals(1,termineImNovember.values().size());
	}
	
	@Test
	public void terminEintragenFailTest()
	{
		
	}

}
