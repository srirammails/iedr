package pl.nask.crs.commons.email;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import pl.nask.crs.commons.email.Email;

/**
 * Checks if the Email class is thread-safe.
 * @author Artur Gniadzik
 *
 */
public class EmailConcurrentAccessTest {

	static final Email email = prepareEmailInstance();
	
	@Test(threadPoolSize=10, invocationCount=1000)
	public void concurrentAccessShouldNotFail() {
		email.toString();
	}
	
	private static Email prepareEmailInstance() {
		Email email = new Email();
		email.setActive(true);
		email.setHtml(true);
		email.setText("text");
		email.setSubject("subject");
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		email.setToList(list );
		email.setCcList(list);
		email.setBccList(list);
		return email;
	}

	@Test(threadPoolSize=10, invocationCount=1000, expectedExceptions=UnsupportedOperationException.class)
	public void concurrentModificationShouldNotFail() {
		email.toString();
		email.getCcList().add("e");
		email.getToList().add("e");
		email.getBccList().add("e");
	}
}
