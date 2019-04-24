package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", new Integer(30), new Integer(5), new Money(200, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("1"));
		
		testAccount.removeTimedPayment("1");
		assertTrue(!testAccount.timedPaymentExists("1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1", new Integer(30), new Integer(5), new Money(200, SEK), SweBank, "Alice");
		for(int i = 0; i < 5; i++) {
			testAccount.tick();
			assertEquals(1000000, (int)SweBank.getBalance("Alice"));
			assertEquals(10000000, (int)(testAccount.getBalance().getAmount()*100));
		}
		testAccount.tick();
		assertEquals(1000000+200, (int)SweBank.getBalance("Alice"));
		assertEquals(10000000-200, (int)(testAccount.getBalance().getAmount()*100));
		
		for(int i = 0; i < 30; i++) {
			testAccount.tick();
			assertEquals(1000000+200, (int)SweBank.getBalance("Alice"));
			assertEquals(10000000-200, (int)(testAccount.getBalance().getAmount()*100));
		}
		testAccount.tick();
		assertEquals(1000000+200+200, (int)SweBank.getBalance("Alice"));
		assertEquals(10000000-200-200, (int)(testAccount.getBalance().getAmount()*100));
	}

	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(200, SEK));
		assertEquals(10000000-200, (int)(testAccount.getBalance().getAmount()*100));
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(new Money(10000000, SEK).toString(), testAccount.getBalance().toString());
	}
}
