package pl.nask.crs.domains;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {
		"classpath:domains-config.xml", 
		"classpath:commons-base-config.xml",
		"classpath:nic-handles-config.xml",
		"classpath:domains-config-test.xml", 
		"classpath:users-config.xml",
		"classpath:emails.xml", 
		"classpath:test-config.xml"})

public abstract class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests {

}
