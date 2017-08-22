package pl.nask.crs.api.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * Check if the domain transfer operations produce right data.
 * 
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 *
 * @author Artur Gniadzik
 *
 */
@ContextConfiguration(locations = {"/crs-api-config.xml"})
public class DomainTransferTest extends AbstractTransactionalTestNGSpringContextTests {
	@Autowired 
	private CommonAppService commonAppService;
	@Autowired 
	private TriplePassAppService triplePassAppService;
	@Autowired
	private DomainSearchService domainSearchService;
	@Autowired
	private DomainService domainService;
	@Autowired
	private TicketAppService ticketAppService;
	
	
	private String domainForTransfer = "thedomena.totransfer.ie";
//	private String loosingRegistrar = "IH4-IEDR"; // account 668
	private String losingRegistrar = "APIT2-IEDR"; // account 668
	private String gainingRegistrar = "APITEST-IEDR"; // 666
	private String gainingDirect = "AAU809-IEDR";
	private long gainingAccountNumber = 666;
	
	@Test
	public void theDomainShouldBeTransferredToTheNewAccount() throws Exception {
		// given		
		TicketRequest req = prepareTransferRequest(gainingRegistrar);	
		// when		
		performDomainTransfer(gainingRegistrar, req);
		
		// than
//		the domain is transfered to a new account
		
		Domain d = getDomain();
		AssertJUnit.assertEquals("Account number", gainingAccountNumber, d.getResellerAccount().getId());
		AssertJUnit.assertEquals("Biling contact", gainingRegistrar, d.getBillingContact().getNicHandle());
	}
	
	@Test
	public void email39shouldBeSentWhenTransferIsCompleted() throws Exception {
		// given		
		TicketRequest req = prepareTransferRequest(gainingRegistrar);	
		Domain beforeTransfer = getDomain();
		AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
		// when		
		performDomainTransfer(gainingRegistrar, req);
		
		// than
//		the domain is transfered to a new account
		
		Domain d = getDomain();
		AssertJUnit.assertEquals("Account number", gainingAccountNumber, d.getResellerAccount().getId());
		AssertJUnit.assertEquals("Biling contact", gainingRegistrar, d.getBillingContact().getNicHandle());
	}

    @Test(expectedExceptions=PaymentException.class)
    public void transferToDirectWithADPPaymentShouldFail() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingDirect);
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        // when
        commonAppService.transfer(getAuthenticatedUser(gainingDirect), req, null);        // than exception is thrown indicating, that the ADP payment is not possible
    }

    @Test(expectedExceptions=InvalidAuthCodeException.class)
    public void transferWithNoAuthCodeShouldFail() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar, null);
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
    }

    @Test(expectedExceptions=InvalidAuthCodeException.class)
    public void transferWithWrongAuthCodeShouldFail() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar, "ABC123456789");
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
    }

    private TicketRequest prepareTransferRequest(String nh) throws Exception {
        final Domain d = getDomain();
        String authCode = domainService.getOrCreateAuthCode(losingRegistrar, d.getName()).getAuthcode();
        TransferRequestVO req = new TransferRequestVO();
        req.resetToNameservers(d.getNameservers());
        req.setAdminContact1NicHandle(nh);
        req.setTechContactNicHandle(nh);
        req.setDomainHolder(d.getHolder());
        req.setDomainHolderClass(d.getHolderClass());
        req.setDomainHolderCategory(d.getHolderCategory());
        req.setDomainName(domainForTransfer);
        req.setAuthCode(authCode);
        req.setPeriod(2);
        req.setPeriodType(PeriodType.Y);
        return req;
    }

    private TicketRequest prepareTransferRequest(String nh, String authCode) {
        final Domain d = getDomain();
        TransferRequestVO req = new TransferRequestVO();
        req.resetToNameservers(d.getNameservers());
        req.setAdminContact1NicHandle(nh);
        req.setTechContactNicHandle(nh);
        req.setDomainHolder(d.getHolder());
        req.setDomainHolderClass(d.getHolderClass());
        req.setDomainHolderCategory(d.getHolderCategory());
        req.setDomainName(domainForTransfer);
        req.setAuthCode(authCode);
        req.setPeriod(2);
        req.setPeriodType(PeriodType.Y);
        return req;
    }

	private AuthenticatedUser getAuthenticatedUser(final String username) {		
		return new AuthenticatedUser() {
			@Override
			public String getUsername() {			
				return username;
			}

			@Override
			public String getSuperUserName() {
				return null;
			}

			@Override
			public String getAuthenticationToken() {
				return "";
			}
		};
	}
	
	private Domain getDomain() {
		try {
			return domainSearchService.getDomain(domainForTransfer);
		} catch (DomainNotFoundException e) {
			throw new IllegalStateException("Domain not found: " + domainForTransfer, e);
		}
	}
	
	private void performDomainTransfer(String gainingRegistrar, TicketRequest req) {
		try {
			long ticketId = commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
			AssertJUnit.assertTrue("ticket.id != 0", ticketId !=0);
			ticketAppService.accept(getAuthenticatedUser("IDL2-IEDR"), ticketId, "remarks");

			boolean passed = triplePassAppService.triplePass(getAuthenticatedUser(gainingRegistrar), domainForTransfer);
			AssertJUnit.assertTrue("transfer for " + domainForTransfer + " triplePassed", passed);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Unexpected error when creating transfer ticket", e);
		}
	}
}
