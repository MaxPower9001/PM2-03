package test.java.kalender;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.kalender.model.DatumImpl;
import main.java.kalender.model.DauerImpl;
import main.java.kalender.model.TagImpl;
import main.java.kalender.model.TerminMitWiederholungImpl;
import main.java.kalender.model.WiederholungType;
import main.java.kalender.model.interfaces.Datum;
import main.java.kalender.model.interfaces.IntervallIterator;
import main.java.kalender.model.interfaces.Termin;
import main.java.kalender.model.interfaces.TerminMitWiederholung;

public class TerminMitWiederholungImplTest {

	@Test
	public void testTerminMitWiederholungIntervallIterator() {
		TerminMitWiederholung unserTermin = new TerminMitWiederholungImpl(
				"TestTermin",
				new DatumImpl(new TagImpl(2016,10,21)),
				new DauerImpl(3,30),
				WiederholungType.WOECHENTLICH,
				5,
				2);
		IntervallIterator<Datum> iterator = unserTermin.intervallIterator(1, 5);
		int counter = 0;
		while(iterator.hasNext())
		{
			counter++;
			iterator.next();
		}
		assertEquals(5,counter);
	}
	
}
