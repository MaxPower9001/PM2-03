package test.java.kalender;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.kalender.model.DauerImpl;
import main.java.kalender.model.interfaces.Dauer;

public class DauerTests {
	
	int dauerInMinuten = 27609;

	@Test
	public void test() {
		Dauer dauer = new DauerImpl(dauerInMinuten);
		assertEquals(2,dauer.anteilWochen());
		assertEquals(5,dauer.anteilTage());
		assertEquals(4,dauer.anteilStunden());
		assertEquals(9,dauer.anteilMinuten());
	}
	

}
