package pl.nask.crs.payment;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {
		"classpath:payment-full-config.xml", 
		"classpath:commons-base-config.xml",
		"classpath:commons.xml",
		"classpath:nic-handles-config.xml",
		"classpath:users-config.xml",
		"classpath:domains-config.xml",
		"classpath:payment-config-test.xml", 
		"classpath:test-config.xml"})
public abstract class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests{

}
