package pl.nask.crs.regression;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;


/**
 * tests isse #12140 https://drotest4.nask.net.pl:3000/issues/12140
 * 
 * Email - $LOSING_BILL-C_NAME$ in E_ID = 39 is incorrectly populated with gaining Bill C
 * 
 * @author Artur Gniadzik
 *
 */
public class TransferEmailTest extends AbstractEmailsTest {
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

    @Autowired
    NicHandleDAO nicHandleDAO;

	private String domainForTransfer = "thedomena.totransfer.ie";
//	private String loosingRegistrar = "IH4-IEDR"; // account 668
	private String losingRegistrar = "APIT2-IEDR"; // account 668
	private String gainingRegistrar = "APITEST-IEDR"; // 666
	private long gainingAccountNumber = 666;
			
	@Test
	public void email39shouldBeSentWhenTransferIsCompleted() throws Exception {
		// given		
		String loosingName = "loosing";
		String gainingName = "gaining";
		setName(gainingRegistrar, gainingName);
		setName(losingRegistrar, loosingName);

		TicketRequest req = prepareTransferRequest();	
		Domain beforeTransfer = getDomain();

		AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
		
		// when
		createExpectations(39, populatedValues(loosingName, gainingName) );
		performDomainTransfer(req);
		
		// than
		// the expectations are met
//		the domain is transfered to a new account
		
	}

	private void setName(String nh, String newName) {
		NicHandle nicHandle = nicHandleDAO.get(nh);
		nicHandle.setName(newName);
		nicHandleDAO.update(nicHandle);
	}

	private  Map<ParameterNameEnum, String> populatedValues(String loosingName, String gainingName) {
		Map<ParameterNameEnum, String> map = new HashMap<ParameterNameEnum, String>();
		map.put(ParameterNameEnum.LOSING_BILL_C_NAME, loosingName);
		map.put(ParameterNameEnum.GAINING_BILL_C_NAME,gainingName);
		return map;
	}

    private TicketRequest prepareTransferRequest() throws Exception {
        final Domain d = getDomain();
        String authCode = domainService.getOrCreateAuthCode(losingRegistrar, d.getName()).getAuthcode();
        TransferRequestVO req = new TransferRequestVO();
        req.resetToNameservers(d.getNameservers());
        req.setAdminContact1NicHandle(d.getFirstAdminContactNic());
        req.setAdminContact2NicHandle(d.getSecondAdminContactNic());
        req.setTechContactNicHandle(d.getTechContactNic());
        req.setDomainHolder(d.getHolder());
        req.setDomainHolderClass(d.getHolderClass());
        req.setDomainHolderCategory(d.getHolderCategory());
        req.setDomainName(domainForTransfer);
        req.setAuthCode(authCode);
        req.setPeriod(1);
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
	
	private void performDomainTransfer(TicketRequest req) {
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
