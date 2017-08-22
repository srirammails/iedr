package pl.nask.crs.documents;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {
		"/commons-base-config.xml",
		"/commons.xml",
		"/incoming-docs-config.xml", 
		"/incoming-docs-config-test.xml", 
		"/nic-handles-config.xml",
		"/users-config.xml",
		"/domains-config.xml",
		"/ticket-config.xml",
		"/test-config.xml"})
public abstract class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests {

}
