package test.java.kalender;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.kalender.DatumImpl;
import main.java.kalender.DauerImpl;
import main.java.kalender.TagImpl;
import main.java.kalender.TerminMitWiederholungImpl;
import main.java.kalender.WiederholungType;
import main.java.kalender.interfaces.Datum;
import main.java.kalender.interfaces.IntervallIterator;
import main.java.kalender.interfaces.Termin;
import main.java.kalender.interfaces.TerminMitWiederholung;

public class TerminMitWiederholungImplTest {

	@Test
	public void testSth() {
		TerminMitWiederholung unserTermin = new TerminMitWiederholungImpl(
				"TestTermin",
				new DatumImpl(new TagImpl(2016,10,21)),
				new DauerImpl(3,30),
				WiederholungType.WOECHENTLICH,
				5,
				2);
		IntervallIterator<Datum> pewpew = unserTermin.intervallIterator(1, 5);
		while(pewpew.hasNext())
		{
			System.out.println(pewpew.next());
		}
	}

}
