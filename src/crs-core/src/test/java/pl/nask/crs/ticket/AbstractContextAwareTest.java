package pl.nask.crs.ticket;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(
		locations = 
		{"/ticket-config.xml", 
		 "/ticket-config-test.xml",
		 "/nic-handles-config.xml", 
		 "/users-config.xml", 
		 "/commons-base-config.xml", 
		 "/commons.xml", 
		 "/test-config.xml",
		 "/domains-config.xml"})
public abstract class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests {

}
