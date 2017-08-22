package pl.nask.crs.domains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.services.impl.DomainEmailParameters;
import pl.nask.crs.nichandle.dao.NicHandleDAO;

public class DomainEmailParametersTest extends AbstractContextAwareTest {
	@Autowired
	DomainDAO domainDAO;
	
	@Autowired
	NicHandleDAO nicHandleDAO;
	
	@Test
	public void testParamValueForBillCtel() {
		Domain domain = getDomain();
		DomainEmailParameters params = new DomainEmailParameters(null, nicHandleDAO, domain );
		String value = params.getParameterValue("BILL-C_TEL", false);
		Assert.assertNotNull(value);
		AssertJUnit.assertEquals("+35312222222", value);
	}
	
	@Test
	public void testParamValueForAdminEmailOneAdmin() {
		Domain domain = getDomain();
		AssertJUnit.assertNull(domain.getSecondAdminContact());
		DomainEmailParameters params = new DomainEmailParameters(null, nicHandleDAO, domain );
		String value = params.getParameterValue("ADMIN-C_EMAIL", false);
		Assert.assertNotNull(value);
		AssertJUnit.assertEquals("NHEmail000877@server.kom", value);
	}
	
	@Test
	public void testParamValueForAdminEmailTwoAdmins() {
		Domain domain = getDomain();
		domain.getAdminContacts().add(domain.getBillingContact());
		AssertJUnit.assertNotNull(domain.getSecondAdminContact());
		DomainEmailParameters params = new DomainEmailParameters(null, nicHandleDAO, domain );
		String value = params.getParameterValue("ADMIN-C_EMAIL", false);
		Assert.assertNotNull(value);
		AssertJUnit.assertEquals("NHEmail000877@server.kom,NHEmail000878@server.kom", value);
	}

	private Domain getDomain() {
		return domainDAO.get("thedomena.ie");
	}
}
