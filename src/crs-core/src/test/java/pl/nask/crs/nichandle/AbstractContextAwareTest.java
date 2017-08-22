package pl.nask.crs.nichandle;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {
		"/nic-handles-config.xml", 
		"/users-config.xml",
		"/commons-base-config.xml",
		"/commons.xml",
		"/accounts-config-test.xml", 
		"/test-config.xml"})
public abstract class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests{

}
