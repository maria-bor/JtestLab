package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/**
	 * Check if expected amount of money is equal to returned by getAmount() method.
	 * **/
	@Test
	public void testGetAmount() {
		assertEquals(10000*0.01, SEK100.getAmount(), 0.001);
		assertEquals(1000*0.01, EUR10.getAmount(), 0.001);
		assertEquals(20000*0.01, SEK200.getAmount(), 0.001);
		assertEquals(2000*0.01, EUR20.getAmount(), 0.001);
		assertEquals(0*0.01, SEK0.getAmount(), 0.001);
		assertEquals(0*0.01, EUR0.getAmount(), 0.001);
		assertEquals(-10000*0.01, SEKn100.getAmount(), 0.001);
	}

	/**
	 * Check if expected currency of money is equal to returned by getCurrency() method.
	 * **/
	@Test
	public void testGetCurrency() {
		assertSame(SEK, SEK100.getCurrency());
		assertSame(EUR, EUR10.getCurrency());
		assertSame(SEK, SEK200.getCurrency());
		assertSame(EUR, EUR20.getCurrency());
		assertSame(SEK, SEK0.getCurrency());
		assertSame(EUR, EUR0.getCurrency());
		assertSame(SEK, SEKn100.getCurrency());
	}

	/**
	 * Check if toString() method looks like "(amount) (currencyname)".
	 * **/
	@Test
	public void testToString() {
		assertEquals(SEK100.getAmount() + " " + SEK.getName(), SEK100.toString());	
		assertEquals(EUR10.getAmount() + " " + EUR.getName(), EUR10.toString());	
		assertEquals(SEK200.getAmount() + " " + SEK.getName(), SEK200.toString());
		assertEquals(EUR20.getAmount() + " " + EUR.getName(), EUR20.toString());
		assertEquals(SEK0.getAmount() + " " + SEK.getName(), SEK0.toString());
		assertEquals(EUR0.getAmount() + " " + EUR.getName(), EUR0.toString());
		assertEquals(SEKn100.getAmount() + " " + SEK.getName(), SEKn100.toString());
	}

	/**
	 * Check if value of the money is equal to returned by universalValue() method.
	 * **/
	@Test
	public void testGlobalValue() {
		assertEquals(new Integer ((int)  Math.round(10000/0.15)), SEK100.universalValue());
		assertEquals(new Integer ((int)  Math.round(1000/1.5)), EUR10.universalValue());
		assertEquals(new Integer ((int)  Math.round(20000/0.15)), SEK200.universalValue());
		assertEquals(new Integer ((int)  Math.round(2000/1.5)), EUR20.universalValue());
		assertEquals(new Integer ((int)  Math.round(0/0.15)), SEK0.universalValue());
		assertEquals(new Integer ((int)  Math.round(0/1.5)), EUR0.universalValue());
		assertEquals(new Integer ((int)  Math.round(-10000/0.15)), SEKn100.universalValue());
	}

	/**
	 * Check if value of the two money is equal.
	 * **/
	@Test
	public void testEqualsMoney() {
		assertTrue((new Money(10000, new Currency("SEK100", 0.15))).equals(SEK100));
		assertTrue((new Money(1000, new Currency("EUR10", 1.5))).equals(EUR10));
		assertTrue((new Money(20000, new Currency("SEK200", 0.15))).equals(SEK200));
		assertTrue((new Money(2000, new Currency("EUR20", 1.5))).equals(EUR20));
		assertTrue((new Money(0, new Currency("SEK0", 0.15))).equals(SEK0));
		assertTrue((new Money(0, new Currency("EUR0", 1.5))).equals(EUR0));
		assertTrue((new Money(-10000, new Currency("SEKn100", 0.15))).equals(SEKn100));
	}

	/**
	 * Check if value of adding two monies is equals to expected result.
	 * **/
	@Test
	public void testAdd() {
		Money m = new Money((int) Math.round(10000 + (1000*0.15/ 1.5)), new Currency("SEK", 0.15));
		assertEquals(m.toString(), SEK100.add(EUR10).toString());
	}

	/**
	 * Check if value of subtracing two monies is equals to expected result.
	 * **/
	@Test
	public void testSub() {
		Money m = new Money((int) Math.round(10000 - (1000*0.15/ 1.5)), new Currency("SEK", 0.15));
		assertEquals(m.toString(), SEK100.sub(EUR10).toString());
	}

	/**
	 * Check if value of money is equals to zero.
	 * **/
	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());
	}

	/**
	 * Check if value of money is negative.
	 * **/
	@Test
	public void testNegate() {
		assertEquals(new Money(10000, new Currency("SEK", 0.15)).toString(), SEKn100.negate().toString());
	}

	/**
	 * Check values of two monies in different currency.
	 * **/
	@Test
	public void testCompareTo() {
		assertEquals(0, SEK0.compareTo(EUR0));
		assertTrue(EUR10.compareTo(EUR20) < 0);
		assertTrue(SEK200.compareTo(SEK100) > 0);
	}
}
