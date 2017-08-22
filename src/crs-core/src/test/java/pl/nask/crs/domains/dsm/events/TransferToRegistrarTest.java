package pl.nask.crs.domains.dsm.events;

import org.testng.annotations.Test;
import pl.nask.crs.contacts.Contact;

public class TransferToRegistrarTest {
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void shouldFailIfOldBillingContactIsNotProvided() {
		TransferToRegistrar event = new TransferToRegistrar(null, new Contact("new" ));
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void shouldFailIfNewBillingContactIsNotProvided() {
		TransferToRegistrar event = new TransferToRegistrar(new Contact("old"), null );
	}
}
