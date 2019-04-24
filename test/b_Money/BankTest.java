package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("AAA");
		assertEquals(new Integer(0), SweBank.getBalance("AAA"));
		
		Nordea.openAccount("AAA");
		assertEquals(new Integer(0), Nordea.getBalance("AAA"));
		
		DanskeBank.openAccount("AAA");
		assertEquals(new Integer(0), DanskeBank.getBalance("AAA"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(200, SEK));
		assertEquals(new Integer(200), SweBank.getBalance("Ulrika"));
		
		Nordea.deposit("Bob", new Money(200, SEK));
		assertEquals(new Integer(200), Nordea.getBalance("Bob"));
		
		DanskeBank.deposit("Gertrud", new Money(200, DKK));
		assertEquals(new Integer(200), DanskeBank.getBalance("Gertrud"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.withdraw("Ulrika", new Money(200, SEK));
		assertEquals(new Integer(-200), SweBank.getBalance("Ulrika"));
		
		Nordea.withdraw("Bob", new Money(200, SEK));
		assertEquals(new Integer(-200), Nordea.getBalance("Bob"));
		
		DanskeBank.withdraw("Gertrud", new Money(200, DKK));
		assertEquals(new Integer(-200), DanskeBank.getBalance("Gertrud"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(new Integer(0), SweBank.getBalance("Ulrika"));
		assertEquals(new Integer(0), Nordea.getBalance("Bob"));
		assertEquals(new Integer(0), DanskeBank.getBalance("Gertrud"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException, AccountExistsException {
		SweBank.openAccount("AAA");
		SweBank.transfer("Ulrika", "AAA", new Money(200, SEK));
		assertEquals(new Integer(200), SweBank.getBalance("AAA"));
		assertEquals(new Integer(-200), SweBank.getBalance("Ulrika"));
		
		SweBank.transfer("AAA", Nordea, "Bob", new Money(200, SEK));
		assertEquals(new Integer(200), Nordea.getBalance("Bob"));
		assertEquals(new Integer(0), SweBank.getBalance("AAA"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Ulrika", "1", new Integer(30), 5, new Money(200, SEK), Nordea, "Bob");
		for(int i = 0; i < 5; i++) {
			SweBank.tick();
			assertEquals(0, (int) Nordea.getBalance("Bob"));
			assertEquals(0, (int) SweBank.getBalance("Ulrika"));
		}
		SweBank.tick();
		assertEquals(200, (int) Nordea.getBalance("Bob"));
		assertEquals(-200, (int) SweBank.getBalance("Ulrika"));
		
		for(int i = 0; i < 30; i++) {
			SweBank.tick();
			assertEquals(200, (int) Nordea.getBalance("Bob"));
			assertEquals(-200, (int) SweBank.getBalance("Ulrika"));
		}
		SweBank.tick();
		assertEquals(400, (int) Nordea.getBalance("Bob"));
		assertEquals(-400, (int) SweBank.getBalance("Ulrika"));
		
		SweBank.removeTimedPayment("Ulrika", "1");
		for(int i = 0; i < 40; i++) {
			SweBank.tick();
			assertEquals(400, (int) Nordea.getBalance("Bob"));
			assertEquals(-400, (int) SweBank.getBalance("Ulrika"));
		}
		
	}
}
