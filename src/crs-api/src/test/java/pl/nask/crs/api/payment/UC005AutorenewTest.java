package pl.nask.crs.api.payment;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.batch.CRSBatchAppService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.service.PaymentService;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class UC005AutorenewTest extends AbstractTransactionalTestNGSpringContextTests {
	@Resource
	DomainDAO domainDAO;

    @Resource
    CRSBatchAppService service;

	@Resource
	PaymentService paymentService;
	
	@Resource
	DepositDAO depositDao;
	
	@Resource
	CRSAuthenticationService authService;
	
	static DsmState autorenewState = dsmState(81, RenewalMode.Autorenew, NRPStatus.Active, false);
	static DsmState renewOnceState = dsmState(49, RenewalMode.RenewOnce, NRPStatus.Active, false);
//	static DsmState noAutoState = dsmState(1, RenewalMode.NoAutoRenew, NRPStatus.Active, false);
	static DsmState wipoState = dsmState(1, RenewalMode.NoAutorenew, NRPStatus.Active, true);;
	static DsmState autorenewInvMailedState = dsmState(82, RenewalMode.Autorenew, NRPStatus.InvoluntaryMailed, false);;
	static DsmState autorenewInvSuspendedState = dsmState(83, RenewalMode.Autorenew, NRPStatus.InvoluntarySuspended, false);;
	
	Domain domain;
	final String domainName = "suka.ie";
	final String billingNh = "HIA1-IEDR";
	AuthenticatedUserVO user;
	
	@BeforeMethod
	public void prepare() throws Exception {
		user = authService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
		topUpDeposit();
	}
	
	@Test
	public void sc01renewOnceHP() {
		initDomain(renewOnceState);
		service.autorenewAll(user);
		checkReservation();
	}

	@Test
	public void sc02autorenewHP() {
		initDomain(autorenewState);
		service.autorenewAll(user);
		checkReservation();
	}
	
	@Test
	public void sc04autorenewInvMailed() {
		initDomain(autorenewInvMailedState);
		service.autorenewAll(user);
		checkReservation();
	}
	
	@Test
	public void sc05autorenewInvSusp() {
		initDomain(autorenewInvSuspendedState);
		service.autorenewAll(user);
		checkReservation();
	}
	
	@Test
	public void sc06wipo() {
		initDomain(wipoState);
		service.autorenewAll(user);
		checkNoReservation();
	}
	
	@Test
	public void sc09paymentFails() {
		initDomain(renewOnceState);
		clearDeposit();
		service.autorenewAll(user);
		checkNoReservation();
	}
			
	private void clearDeposit() {
		depositDao.update(Deposit.newInstance(billingNh, new Date(), 0, 0, 0, DepositTransactionType.MANUAL, "2", "nh", "clear"));
	}

	private void initDomain(DsmState state) {
		domain = domainDAO.get(domainName);
		domainDAO.update(domain, state.getId());
		domain = domainDAO.get(domainName);
		AssertJUnit.assertEquals("renewal mode", state.getRenewalMode(), domain.getDsmState().getRenewalMode());
		AssertJUnit.assertEquals("wipo", state.getWipoDispute(), domain.getDsmState().getWipoDispute());
		AssertJUnit.assertEquals("nrp status", state.getNRPStatus(), domain.getDsmState().getNRPStatus());
	}		
	
	private void checkReservation() {
		Reservation res = paymentService.getReadyReservation(billingNh, domainName);
		AssertJUnit.assertNotNull("Reservation for domain", res);
		AssertJUnit.assertTrue("ready for settlement", res.isReadyForSettlement());
		AssertJUnit.assertFalse("settled", res.isSettled());
	}
	
	private void checkNoReservation() {
		Reservation res = paymentService.getReadyReservation(billingNh, domainName);
		AssertJUnit.assertNull("Reservation for domain", res);
	}
	
	private void topUpDeposit() {
		depositDao.create(Deposit.newInstance(billingNh, new Date(), 0, 10000, 10000, DepositTransactionType.TOPUP, "a", null, null));
	}

	static DsmState dsmState(int id, RenewalMode mode, NRPStatus nrpStatus, boolean wipo) {
		return new DsmTestState(id, mode, nrpStatus, wipo);		
	}
	
	static class DsmTestState extends DsmState {

		public DsmTestState(int id, RenewalMode mode, NRPStatus nrpStatus, boolean wipo) {
			super(id);
			setRenewalMode(mode);
			setNRPStatus(nrpStatus);
			setWipoDispute(wipo);
		}		
	}
}
