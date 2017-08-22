package pl.nask.crs.regression;

import java.util.HashMap;
import java.util.Map;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * bug #13307
 * https://drotest4.nask.net.pl:3000/issues/13307
 * 
 * Email is being triggered even when payment fails on new reg and transfer tickets generated via Console.
 * 
 * Test steps:
 * 1. Console > Domains > Register New
 * 2. Fill in form (i selected one year but any period can be seleted)
 * 3. Choose credit card option for payment
 * 4. Enter a Mastercard number but select one of the VISA icons for payment type.
 * 5. The error is displayed to screen correctly: "Error. That Card Number does not correspond to the card type you selected "
 * 6. No ticket is created which is correct
 * 7. However an email is triggered to the client to say that the application has been received:
 * 
 * @author Artur Gniadzik
 *
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class FailedPaymentTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mocked
    EmailTemplateSenderImpl emailTemplateSender;

    @Autowired
    CommonAppService commonAppService;

    @Autowired
    DomainSearchService domainSearchService;

    @Autowired
    DomainService domainService;

    @Autowired
    NicHandleDAO nicHandleDAO;

    @Mocked
    pl.nask.crs.api.payment.PaymentSenderTestImpl paymentSender;

	private String domainForTransfer = "thedomena.totransfer.ie";
//	private String loosingRegistrar = "IH4-IEDR"; // account 668
	private String losingRegistrar = "APIT2-IEDR"; // account 668
	private String gainingRegistrar = "APITEST-IEDR"; // 666
	
	@Test(expectedExceptions=PaymentException.class)
	public void noEmailShouldBeSentWhenWrongCardDataAreProvidedWhenTransferingDomain() throws Exception {
		// given		
		String losingName = "losing";
		String gainingName = "gaining";
		setName(gainingRegistrar, gainingName);
		setName(losingRegistrar, losingName);

		TicketRequest req = prepareTransferRequest();	
		Domain beforeTransfer = getDomain();

		AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());

		// when
		createExpectations();
		performDomainTransfer(req, wrongCCPaymentRequest());

		// than
		// the expectations are met
	}
	
	@Test(expectedExceptions=PaymentException.class)
	public void noEmailShouldBeSentWhenWrongCardDataAreProvidedWhenRegisteringDomain() throws Exception {
		// given
		String losingName = "losing";
		String gainingName = "gaining";
		setName(gainingRegistrar, gainingName);
		setName(losingRegistrar, losingName);

		TicketRequest req = prepareRegRequest();	
		// when
		createExpectations();
		commonAppService.registerDomain(getAuthenticatedUser(gainingRegistrar), req, wrongCCPaymentRequest());

		// than
		// the expectations are met
	}
	
	private TicketRequest prepareRegRequest() {
		final Domain d = getDomain();
		RegistrationRequestVO req = new RegistrationRequestVO();
		req.setAdminContact1NicHandle(d.getFirstAdminContactNic());
		req.setAdminContact2NicHandle(d.getSecondAdminContactNic());
		req.setTechContactNicHandle(d.getTechContactNic());		
		req.setDomainHolder(d.getHolder());
		req.setDomainHolderClass(d.getHolderClass());
		req.setDomainHolderCategory(d.getHolderCategory());
		req.setDomainName("newDomain.ie");
		req.resetToNameservers(d.getNameservers());
		req.setPeriod(1);
		req.setPeriodType(PeriodType.Y);		
		return req;
	}

	private PaymentRequest wrongCCPaymentRequest() {
		PaymentRequest r = new PaymentRequest("EUR", "5425230000000009", "2021/01", "VISA", "John Doe", 123, 1);
		r.setAmount(MoneyUtils.getBigDecimalValueInStandardCurrencyUnit(1000));	
		return r;

	}
	
	private final static String realexErrorResponse ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<response timestamp=\"20120319115651\">\n" +
            "<merchantid>iedr</merchantid>\n" +
            "<account>internet</account>\n" +
            "<orderid>20120319125630-103067</orderid>\n" +
            "<authcode>115651</authcode>\n" +
            "<result>55</result>\n" + // some error code here
            "<cvnresult>U</cvnresult>\n" +
            "<avspostcoderesponse>U</avspostcoderesponse>\n" +
            "<avsaddressresponse>U</avsaddressresponse>\n" +
            "<batchid>-1</batchid>\n" +
            "<message>[ test system ] AUTH CODE 115651</message>\n" +
            "<pasref>133215821111512</pasref>\n" +
            "<timetaken>0</timetaken>\n" +
            "<authtimetaken>0</authtimetaken>\n" +
            "<cardissuer>\n" +
            "<bank>AIB BANK</bank>\n" +
            "<country>IRELAND</country>\n" +
            "<countrycode>IE</countrycode>\n" +
            "<region>EUR</region>\n" +
            "</cardissuer>\n" +
            "<md5hash>84cb753995cded64ff90a9a20bf2866f</md5hash>\n" +
            "</response>";

	private void createExpectations() throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
		new NonStrictExpectations() {{
			paymentSender.send(anyString); result=realexErrorResponse;
		}};
		
		new NonStrictExpectations() {{
				emailTemplateSender.sendEmail(anyInt, withInstanceOf(EmailParameters.class)); maxTimes=0;
		}};
		
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

	private void performDomainTransfer(TicketRequest req, PaymentRequest paymentRequest) throws TicketNotFoundException, NicHandleNotFoundException, DomainNotFoundException, AccountNotFoundException, NotAdmissiblePeriodException, HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException, ValidationException, NicHandleRecreateException, DomainIsNotCharityException, PaymentException, DomainIsCharityException, InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, AccessDeniedException, NicHandleNotActiveException, EmptyRemarkException {
		commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, paymentRequest);
	}

}
