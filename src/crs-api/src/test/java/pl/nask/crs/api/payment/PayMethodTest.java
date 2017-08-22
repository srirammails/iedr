package pl.nask.crs.api.payment;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static pl.nask.crs.api.Helper.createBasicCCPaymentRequest;
import static pl.nask.crs.api.Helper.createBasicDebitPaymentRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.DomainWithPeriodVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.PaymentSummaryVO;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.InvoiceDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.ReservationHistDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.dao.TransactionHistDAO;
import pl.nask.crs.payment.exceptions.DomainIncorrectStateForPaymentException;
import pl.nask.crs.payment.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class PayMethodTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSPaymentAppService crsPaymentAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    InvoiceDAO invoiceDAO;

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Resource
    ReservationHistDAO reservationHistDAO;
    
    @Autowired
    DomainDAO domainDao;
    
    @Autowired
    DomainService domainService;
    
    @Mocked
    EmailTemplateSenderImpl templateSender;

    AuthenticatedUserVO user;

    private final static String userName = "APITEST-IEDR";

    private final static PaymentRequestVO paymentRequest = createBasicCCPaymentRequest();

    private static ReservationSearchCriteria readyCriteria;

    static {
        readyCriteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        readyCriteria.setBillingNH(userName);
    }

    @BeforeMethod
	public void authenticate() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException {
    	user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    @Test
    public void payADPOkTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1), new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, false);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(130), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(26), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(156), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(6, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(9, transactions.size());
        Reservation reservation = reservationDAO.getReservations(getCriteriaForDomain("payDomain.ie"), 0, 1, null).getResults().get(0);
        assertTrue(reservation.isReadyForSettlement());
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertNotNull(transaction.getFinanciallyPassedDate());
    }
    
    @Test
    public void payAdpAllDomainsPerformanceTest() throws Exception {
    	StopWatch watch = new StopWatch();
    	DomainSearchCriteria domainSearchCriteria = new DomainSearchCriteria();
    	domainSearchCriteria.setAccountId(666L);
    	domainSearchCriteria.setNrpStatusForName("Active");
    	domainSearchCriteria.setDomainHolderTypes(DomainHolderType.Billable);
		List<String> domains = domainDao.findDomainNames(domainSearchCriteria, 0, 1000);
		List<DomainWithPeriodVO> domainsWithPeriod = domainsWithPeriod(domains, 1);
		watch.start();
		crsPaymentAppService.pay(user, domainsWithPeriod, PaymentMethod.ADP, null, false);
		watch.stop();
		System.err.println("pay notest" + watch.getTime());
    }

    private List<DomainWithPeriodVO> domainsWithPeriod(List<String> domains, int period) {
		List<DomainWithPeriodVO> ret = new ArrayList<DomainWithPeriodVO>(domains.size());
		List<String> invalidDomains = domainService.checkEventAvailable(domains, DsmEventName.PaymentInitiated);
		System.err.println("Got " + invalidDomains.size() + " invalid domains");
		for (String domain: domains) {
			if (invalidDomains.contains(domain)) {
				System.err.println("Invalid domain: " + domain);
			} else {
				ret.add(new DomainWithPeriodVO(domain, period));
			}
		}
		return ret ;
	}

	@Test
    public void payADPOkWithDifferentPeriodsTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1), new DomainWithPeriodVO("payDomain2.ie", 2));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, false);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(105), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(21), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(126), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(6, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(9, transactions.size());
        Reservation reservation = reservationDAO.getReservations(getCriteriaForDomain("payDomain.ie"), 0, 1, null).getResults().get(0);
        assertTrue(reservation.isReadyForSettlement());
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertNotNull(transaction.getFinanciallyPassedDate());
    }

    private ReservationSearchCriteria getCriteriaForDomain(String domainName) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        criteria.setDomainName(domainName);
        return criteria;
    }

    @Test
    public void payADPOkWithTestFlagTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1), new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, true);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(130), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(26), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(156), paymentSummaryVO.getTotal());
        assertEquals("test-order-id", paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());
    }

    @Test(expectedExceptions = DomainNotFoundException.class)
    public void payADPNotExistingDomainTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("notExisting.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, false);
    }

    @Test(expectedExceptions = DomainManagedByAnotherResellerException.class)
    public void payADPDomainManagedByAnotherResellerTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, false);
    }

    @Test(expectedExceptions = DomainIncorrectStateForPaymentException.class)
    public void payADPDomainInInvalidStateTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("pizzaonline2.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, false);
    }

    @Test(expectedExceptions = NotAdmissiblePeriodException.class)
    public void payADPNotAdmissiblePeriodTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 12));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, false);
    }

    @Test(expectedExceptions = NotEnoughtDepositFundsException.class)
    public void payAPDNotEnoughFundsTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain3.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.ADP, null, false);
    }

    @Test
    public void payCCOkTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1), new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.CC, paymentRequest, false);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(130), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(26), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(156), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(6, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(9, transactions.size());
        Reservation reservation = reservationDAO.getReservations(getCriteriaForDomain("payDomain.ie"), 0, 1, null).getResults().get(0);
        assertTrue(reservation.isReadyForSettlement());
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test
    public void payCCOkWithTestFlagTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1), new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.CC, paymentRequest, true);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(130), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(26), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(156), paymentSummaryVO.getTotal());
        assertEquals("test-order-id", paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());
    }

    @Test
    public void payDebitTest() throws Exception {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newSettledInstance(true);
        List<Reservation> settledReservations = reservationDAO.getReservations(criteria, 0, 20, null).getResults();
        assertEquals(0, settledReservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        LimitedSearchResult<Reservation> histReservations = reservationHistDAO.find(criteria, 0, 20, null);
        assertEquals(11, histReservations.getTotalResults());
        LimitedSearchResult<Transaction> histTransactions = transactionHistDAO.find(null, 0, 20, null);
        assertEquals(7, histTransactions.getTotalResults());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1), new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.DEB, createBasicDebitPaymentRequest(), false);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(130), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(26), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(156), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());

        settledReservations = reservationDAO.getReservations(criteria, 0, 10, null).getResults();
        assertEquals(0, settledReservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        histReservations = reservationHistDAO.find(criteria, 0, 10, null);
        assertEquals(13, histReservations.getTotalResults());
        histTransactions = transactionHistDAO.find(null, 0, 10, null);
        assertEquals(8, histTransactions.getTotalResults());

        criteria.setDomainName("payDomain.ie");
        Reservation reservation = reservationHistDAO.find(criteria, 0, 1, null).getResults().get(0);
        Transaction transaction = transactionHistDAO.get(reservation.getTransactionId());
        Invoice invoice = invoiceDAO.get(transaction.getInvoiceId());

        assertTrue(transaction.isSettlementStarted());
        assertTrue(transaction.isSettlementEnded());
        assertNotNull(transaction.getFinanciallyPassedDate());
        assertNotNull(invoice);
        assertNotNull("renewalDate", transaction.getReservations().get(0).getEndDate());
    }
    
    void dcPaymentShouldTriggerSendingEmail(int domainState, final int... emailTemplates) throws Exception {
    	String domainName = "payDomain.ie";
    	Domain domain = domainDao.get(domainName);    	
        domainDao.update(domain, domainState); // IM
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO(domainName, 1));
        new Expectations() {{
        	for (int i: emailTemplates) {
        		if (i == 62) {// attachments!
        			templateSender.sendEmail(i, withInstanceOf(EmailParameters.class), withInstanceOf(List.class));
        		} else {
        			templateSender.sendEmail(i, withInstanceOf(EmailParameters.class));
        		}
        	}
        }};
        
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.DEB, createBasicDebitPaymentRequest(), false);
    }
    
    @Test
    public void dcPaymentForImDomainsShouldTriggerSendingEmail69And4And62() throws Exception {
    	dcPaymentShouldTriggerSendingEmail(10, 69, 62, 4);
    }
    
    @Test
    public void dcPaymentForIsDomainsShouldTriggerSendingEmail69And4And62() throws Exception {
    	dcPaymentShouldTriggerSendingEmail(11, 69, 62, 4);
    }
    
    @Test
    public void dcPaymentForVmDomainsShouldTriggerSendingEmail70And4And62() throws Exception {
    	dcPaymentShouldTriggerSendingEmail(12, 70, 62, 4);
    }
    
    @Test
    public void dcPaymentForVsDomainsShouldTriggerSendingEmail70And4And62() throws Exception {
    	dcPaymentShouldTriggerSendingEmail(13, 70, 62, 4);
    }

    @Test
    public void payDebitWithTestFlagTest() throws Exception {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newSettledInstance(true);
        List<Reservation> settledReservations = reservationDAO.getReservations(criteria, 0, 20, null).getResults();
        assertEquals(0, settledReservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        LimitedSearchResult<Reservation> histReservations = reservationHistDAO.find(criteria, 0, 20, null);
        assertEquals(11, histReservations.getTotalResults());
        LimitedSearchResult<Transaction> histTransactions = transactionHistDAO.find(null, 0, 20, null);
        assertEquals(7, histTransactions.getTotalResults());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1), new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.DEB, createBasicDebitPaymentRequest(), true);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(130), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(26), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(156), paymentSummaryVO.getTotal());
        assertEquals("test-order-id", paymentSummaryVO.getOrderId());

        settledReservations = reservationDAO.getReservations(criteria, 0, 10, null).getResults();
        assertEquals(0, settledReservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        histReservations = reservationHistDAO.find(criteria, 0, 10, null);
        assertEquals(11, histReservations.getTotalResults());
        histTransactions = transactionHistDAO.find(null, 0, 10, null);
        assertEquals(7, histTransactions.getTotalResults());
    }

    @Test(expectedExceptions = DomainNotFoundException.class)
    public void payCCNotExistingDomainTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("notExisting.ie", 1));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.CC, paymentRequest, false);
    }

    @Test(expectedExceptions = DomainManagedByAnotherResellerException.class)
    public void payCCDomainManagedByAnotherResellerTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.CC, paymentRequest, false);
    }

    @Test(expectedExceptions = DomainIncorrectStateForPaymentException.class)
    public void payCCDomainInInvalidStateTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("pizzaonline2.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.CC, paymentRequest, false);
    }

    @Test(expectedExceptions = NotAdmissiblePeriodException.class)
    public void payCCNotAdmissiblePeriodTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 12));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.CC, paymentRequest, false);
    }

    @Test
    public void payDirectVatTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("AAU809-IEDR", "Passw0rd!", "1.1.1.1", null);
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(4, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(8, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("directDomain.ie", 2));
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domainsList, PaymentMethod.CC, paymentRequest, true);
        assertNotNull(paymentSummaryVO);
        assertEquals(BigDecimal.valueOf(184), paymentSummaryVO.getAmount());
        assertEquals(BigDecimal.valueOf(174.8).setScale(2), paymentSummaryVO.getVat());
        assertEquals(BigDecimal.valueOf(358.8).setScale(2), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());
    }
}
