package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	/**
	 * Check if expected name of currency is equal to returned by getName() method.
	 * **/
	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	/**
	 * Check if expected rate of currency is equal to returned by getRate() method.
	 * **/
	@Test
	public void testGetRate() {
		assertEquals((Double)0.15, SEK.getRate());
		assertEquals((Double)0.20, DKK.getRate());
		assertEquals((Double)1.5, EUR.getRate());
	}
	
	/**
	 * Check if expected rate of currency is equal to returned by setRate() method.
	 * **/
	@Test
	public void testSetRate() {
		SEK.setRate(0.17);
		assertEquals((Double)0.17, SEK.getRate());
		
		DKK.setRate(0.23);
		assertEquals((Double)0.23, DKK.getRate());
		
		EUR.setRate(1.8);
		assertEquals((Double)1.8, EUR.getRate());
	}
	
	/**
	 * Check if value of amount in universal value is equal to returned by universalValue(amount) method.
	 * **/
	@Test
	public void testGlobalValue() {
		assertSame((int) Math.round(10/0.15), SEK.universalValue(10));
		assertSame((int) Math.round(10/0.20), DKK.universalValue(10));
		assertSame((int) Math.round(10/1.5), EUR.universalValue(10));
	}
	
	/**
	 * Check conversion amount of money in one currency to another.
	 * **/
	@Test
	public void testValueInThisCurrency() {
		NOK = new Currency("NOK", 0.4);
		
		assertSame((int) Math.round((10/0.4)*0.15), SEK.valueInThisCurrency(10, NOK));
		assertSame((int) Math.round((10/0.4)*0.20), DKK.valueInThisCurrency(10, NOK));
		assertSame((int) Math.round((10/0.4)*1.5), EUR.valueInThisCurrency(10, NOK));
	}

}
