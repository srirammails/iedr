package pl.nask.crs.domains.dsm.events;

import org.testng.annotations.Test;
import pl.nask.crs.contacts.Contact;

public class TransferToDirectTest {
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void shouldFailIfOldBillingContactIsNotProvided() {
		TransferToDirect event = new TransferToDirect(null, new Contact("new") );
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void shouldFailIfNewBillingContactIsNotProvided() {
		TransferToDirect event = new TransferToDirect(new Contact("old"), null );
	}
}
