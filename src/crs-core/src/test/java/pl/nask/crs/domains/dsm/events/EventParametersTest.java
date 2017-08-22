package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.domains.dsm.DsmEventName;

public class EventParametersTest {
	
	private TestEvent event;
	
	@BeforeMethod
	public void initTestEvent() {
		event = new TestEvent();
	}
	
	@Test
	public void testObjectParam() {
		event.setParameter("param", new Object());
		Object p = event.getParameter("param");
		AssertJUnit.assertNotNull(p);
	}
	
	@Test
	public void testStringParam() {
		event.setParameter("param", "p");
		String p = event.getStringParameter("param");
		Assert.assertNotNull(p);
		AssertJUnit.assertEquals("p", p);
	}
	
	@Test
	public void testDateParam() {
		Date date = new Date();
		event.setParameter("p", date);
		Date p = event.getDateParameter("p");
		Assert.assertNotNull(p);
		AssertJUnit.assertEquals(date, p);
	}
	
	@Test
	public void testIntParam() {
		event.setParameter("p", 1);
		int p = event.getIntParameter("p");
		Assert.assertNotNull(p);
		AssertJUnit.assertEquals(1, p);
	}
	
	@Test
	public void testNumberParam() {
		event.setParameter("p", 1);
		Number res = event.getParameter("p", Number.class);
		AssertJUnit.assertTrue(res instanceof Integer);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void testLongAsIntParam() {
		event.setParameter("p", 1L);
		event.getIntParameter("p");
	}
			
	public class TestEvent extends AbstractEvent {

		public TestEvent() {
			super(DsmEventName.CreateBillableDomainDirect);
		}
		
		@Override
		public void setParameter(String name, Object param) {
			super.setParameter(name, param);
		}
		
		@Override
		public <T> T getParameter(String name, Class<T> clazz) {
			return super.getParameter(name, clazz);
		}
	}
}
