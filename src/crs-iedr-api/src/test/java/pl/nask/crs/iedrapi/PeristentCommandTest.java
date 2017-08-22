package pl.nask.crs.iedrapi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.iedrapi.persistentcommands.PersistedCommandDAO;

@ContextConfiguration("classpath:iedr-api-config.xml")
public class PeristentCommandTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	PersistedCommandDAO dao;
	
	private static long seed = new Date().getTime();
	
	@Test
	public void testStore() {
		String nicHandle = "nh-sth";
		String request = "hahahahaha" + (seed++);
		String response = "an xml command";
		dao.storeResponse(nicHandle, request, response);
	}
	
	@Test
	public void testRetrieveNoRes() {
		String request = "aaa";
		String nh = "AAP368-IEDR";
		String res = dao.getResponse(nh, request);
		Assert.assertNull(res);
	}
	
	@Test
	public void testStoreAndRetrieve() {
		String nicHandle = "nh-sth";
		String request = "hahahahaha" + (seed++);
		String response = "an xml command";
		dao.storeResponse(nicHandle, request, response);
	
		String res = dao.getResponse(nicHandle, request);
		AssertJUnit.assertEquals(response, res);
	}
}
