package pl.nask.crs.commons;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {"classpath:commons-config-test.xml"})
public abstract class AbstractTest extends AbstractTransactionalTestNGSpringContextTests {

}
