package pl.nask.crs;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public abstract class AbstractCrsTest extends AbstractTransactionalTestNGSpringContextTests {

}
