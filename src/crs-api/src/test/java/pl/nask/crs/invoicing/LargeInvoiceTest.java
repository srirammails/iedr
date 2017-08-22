package pl.nask.crs.invoicing;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

/**
 * 
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 *
 * @author Artur Gniadzik
 *
 * This is to reproduce bug #9266
 * <pre>
 In a round of user testing, not all transactions were followed by the expected pdf and xml files.
 The invoice is created in the database correctly and payment settled etc., but no files are generated. 
 In the files that are created, the invoice number that was generated is skipped. 
 ie. in this example Inv0060048 was written to Invoice table but no file was generated for it, 
 a file however was generated for INV0060047 and INV0060049 and INV0060050 
 so crs seems to have just had a problem creating that particular invoice. 
 It seems to be happening for larger invoices but not always.
 * </pre>
 */

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class LargeInvoiceTest extends AbstractTestNGSpringContextTests {

	@Resource
	AccountAppService accountAppService;

	@Resource
	AuthenticationService authenticationService;
	
	@Resource
	PaymentAppService paymentAppService;
	
	@Resource
	CommonAppService commonApService;
	
	@Resource
	TicketAppService ticketAppService;
	
	@Resource
	TriplePassAppService triplePassAppService;
	
	@Resource
	InvoicingAppService invoicingAppService;

	private final String password = "Marysia1";

	
	AuthenticatedUser adminUser;

	String billingContactNicHandle;
	private Account registrar;

	private AuthenticatedUser registrarUser; 
	
	@Test
	public void foo() {
		// to make surefire happy
	}
	
//	@Test
	@Test(enabled = false)
	public void createLargeInvoice() throws Exception {
		adminLogIn();
		createRegistrar();
		topUpRegistrarDeposit();
		resellerLogIn();
		registerDomains();
		runInvoicing();
		checkDbInvoice();
		checkPdfInvoice();
		checkXmlInvioce();
		logger.info("Done!");
	}

	private void resellerLogIn() throws Exception {		
		registrarUser = authenticationService.authenticate(billingContactNicHandle, password, false, "1.1.1.1", false, null, true, "ws");
	}

	private void checkXmlInvioce() {
		// TODO Auto-generated method stub
		
	}

	private void checkPdfInvoice() {
		// TODO Auto-generated method stub
		
	}

	private void checkDbInvoice() {
		// TODO Auto-generated method stub
	}

	private void runInvoicing() {
		logger.info("triplePass");
		triplePassAppService.triplePass();
		logger.info("invoicing");
		invoicingAppService.runInvoicing(null);
	}

	private void registerDomains() throws Exception {
		logger.info("register domains");
		for (int i=0; i<500; i++) {
			registerDomain(i);
		}
	}

	private void registerDomain(int i) throws Exception {
		String domainName = i + "domain" + registrar.getId() + ".ie";
		registerDomain(domainName);
	}

	private void registerDomain(String domainName) throws Exception {
		RegistrationRequestVO request = new RegistrationRequestVO();
		request.setAdminContact1NicHandle(billingContactNicHandle);
		request.setTechContactNicHandle(billingContactNicHandle);
		request.setDomainHolder("any holder");
		request.setDomainName(domainName);
		request.setDomainHolderClass("Natural Person");
		request.setDomainHolderCategory("Discretionary Name");
		request.setPeriod(1);
		request.setPeriodType(PeriodType.Y);
		request.setRequestersRemark("any remark");
		request.setNameservers(Arrays.asList(
			new NameserverVO("ns.iedr.ie", null),
			new NameserverVO("ns1.iedr.ie", null)));
		
		long ticketId = commonApService.registerDomain(registrarUser, request , null);
		
		ticketAppService.accept(adminUser, ticketId, "auto accept");
	}

	private void topUpRegistrarDeposit() throws Exception {
		paymentAppService.correctDeposit(adminUser, billingContactNicHandle, 100*1000*100, "Initial top-up");
	}

	private void adminLogIn() throws Exception {
		adminUser = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
	}

	private void createRegistrar() throws Exception {
		NewNicHandle nh = new NewNicHandle();
		nh.setName("any name");
		nh.setAddress("any address");
		nh.setCompanyName("any name");
		nh.setCountry("Poland");
		nh.setCounty("");
		nh.setEmail("arturg@nask.pl");
		nh.setFaxes(Collections.singleton("111111"));
		nh.setPhones(Collections.singleton("111111"));
		nh.setVatNo("A");	
		
		NewAccount na = accountAppService.createDirectAccount(nh , password, false);;
		billingContactNicHandle = na.getNicHandleId();
		CreateAccountContener createAccountContener = new CreateAccountContener("Any name", "http://anyhost.ie/", billingContactNicHandle, "any account", true, true);
		registrar = accountAppService.create(adminUser, createAccountContener , "Account to test bug#9266");
	}

}

