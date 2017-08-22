package pl.nask.crs.domains.services;

import java.util.Date;

import javax.annotation.Resource;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;


public class VoluntaryNrpTest extends AbstractContextAwareTest {
	@Resource
	DomainService domainService;
		
	@Resource
	DomainDAO domainDAO;		
	
	@Mocked 
	EmailTemplateSenderImpl emailSender;
	
	String domain1 = "payDomain.ie";
	String domain2 = "payDomain2.ie";
	String domain3 = "payDomain3.ie";
	String domain4 = "payDomain4.ie";
	String domain5 = "payDomain5.ie";
	
	Date current15 = DateUtils.addDays(new Date(), 15);
	
	OpInfo opInfo = new OpInfo("test");
//			
////	@Before
//	public void prepareDomainStates() {
//		updateState(domain1, 17);
//		updateState(domain2, 17);
//		updateState(domain3, 20);
//		updateState(domain4, 20);
//		updateState(domain5, 385);
//	}
// TODO: CRS-72
//	@Test
//	public void enterVoluntaryNrp2Domains() {
//		updateState(domain1, 17);
//		updateState(domain2, 17);
//		domainService.enterVoluntaryNRP(opInfo, domain1, domain2);
//		// check the dates
//		checkVNRP(domain1, current15);
//		checkVNRP(domain2, current15);
//	}
//
//	/**
//	 * this checks happy path scenarios for uc010 (sc01-05, sc08
//	 * @throws EmailSendingException 
//	 * @throws TemplateInstantiatingException 
//	 * @throws TemplateNotFoundException 
//	 */
//	@Test
//	public void enterVoluntaryNrpUC010success() throws Exception {
//		// domain1: sc01
//        updateState(domain1, 17);
//        // domain2: sc02
//		updateState(domain2, 25);
//		// domain3: sc03
//		updateState(domain3, 81);
//		// domain4: sc04
//		updateState(domain4, 121);
//		// domain5: sc05
//		updateState(domain5, 305);
//	
//		new NonStrictExpectations() {{
//			emailSender.sendEmail(64, withInstanceOf(EmailParameters.class));minTimes=5;forEachInvocation = new Object() {
//				public void sendEmail(int emailId, EmailParameters params) {
//					AssertJUnit.assertNotNull(params.getParameterValue(ParameterNameEnum.BILL_C_CO_NAME.getName(), false));
//				}
//			};
//		}};
//		
//		domainService.enterVoluntaryNRP(opInfo, domain1, domain2, domain3, domain4, domain5);
//		// check the dates
//		checkVNRP(domain1, current15);
//		checkVNRP(domain2, current15);
//		checkVNRP(domain3, current15);
//		checkVNRP(domain4, current15);
//		checkVNRP(domain5, current15);
//	}
//	
	/**
	 * uc010-sc13
	 */
	@Test
	public void enterVnprTransferPending() {
		updateState(domain1, 390);
		domainService.enterVoluntaryNRP(null, opInfo, domain1);
		AssertJUnit.assertEquals("nrp state", NRPStatus.TransferPendingVolNRP, domainDAO.get(domain1).getDsmState().getNRPStatus());
	}
	
	/**
	 * this implements uc010-sc06, sc07
	 */
	@Test(expectedExceptions=DomainIllegalStateException.class)
	public void enterVoluntaryNrpFailedWipo() {
		updateState(domain1, 1);
		domainService.enterVoluntaryNRP(null, opInfo, domain1);
	}
	
	/**
	 * domain3 is VM, implements uc010-sc09, sc11
	 */
	@Test(expectedExceptions=DomainIllegalStateException.class)
	public void enterVoluntaryNrpFailedVm() {
		updateState(domain1, 17);
		updateState(domain3, 20);
		domainService.enterVoluntaryNRP(null, opInfo, domain1, domain3);
	}
	
	/**
	 * domain3 is VS, implements uc010-sc10, sc12
	 */
	@Test(expectedExceptions=DomainIllegalStateException.class)
	public void enterVoluntaryNrpFailedVs() {
		updateState(domain1, 21);
		domainService.enterVoluntaryNRP(null, opInfo, domain1);
	}
	
	/**
	 * uc010-sc14
	 */
	@Test(expectedExceptions=DomainIllegalStateException.class)
	public void enterVoluntaryNrpFailedXPI() {
		updateState(domain1, 438);
		domainService.enterVoluntaryNRP(null, opInfo, domain1);
	}
	
	/**
	 * uc010-sc15
	 */
	@Test(expectedExceptions=DomainIllegalStateException.class)
	public void enterVoluntaryNrpFailedXPV() {
		updateState(domain1, 486);
		domainService.enterVoluntaryNRP(null, opInfo, domain1);
	}
	
	/* ********************************
	 * 
	 * remove from NRP
	 *  
	 * ********************************
	 */
	
	/**
	 * uc011 sc01,
	 */
	@Test
	public void removeFromVoluntaryNrp() {
		updateState(domain1, 21, DateUtils.addDays(new Date(), 1)); // VS, sc01
		updateState(domain2, 20, DateUtils.addDays(new Date(), 1)); // VM, sc02, sc04, 
		updateState(domain3, 308, DateUtils.addDays(new Date(), 1)); //VM, NOnBillable, sc05
		updateState(domain4, 486, DateUtils.addDays(new Date(), 1)); // XPV, sc08
		domainService.removeFromVoluntaryNRP(null, opInfo, domain1, domain2, domain3, domain4);
		checkStateActive(domain1, NRPStatus.Active);
		checkStateActive(domain2, NRPStatus.Active);
		checkStateActive(domain3, NRPStatus.Active);
		checkStateActive(domain4, NRPStatus.TransferPendingActive);
	}
// TODO: CRS-72
//	@Test
//	public void removeFromVoluntaryNrpShouldCleanDeletionDate() {
//		updateState(domain1, 17, DateUtils.addDays(new Date(), 1)); // VS, sc01
//		domainService.enterVoluntaryNRP(opInfo, domain1);
//// deletion date should be set
//		checkVNRP(domain1, current15);
//		domainService.removeFromVoluntaryNRP(opInfo, domain1);
//		checkStateActive(domain1, NRPStatus.Active);
//	}
	
	@Test(expectedExceptions=DomainIllegalStateException.class)
	public void removeFromVoluntaryNrpFailedInvNrp() {
		updateState(domain1, 18);
		domainService.removeFromVoluntaryNRP(null, opInfo, domain1);
	}

	/**
	 * uc011-sc09
	 */
	@Test(expectedExceptions=DomainIllegalStateException.class)
	public void removeFromVoluntaryNrpFailed() {
		domainService.removeFromVoluntaryNRP(null, opInfo, domain3, domain5);
	}
	
	@Test
	public void removeFromVsAfterRenewDatePassesRequiresPayment() {
		updateState(domain1, 21, pl.nask.crs.commons.utils.DateUtils.getCurrDate(-1));
		try {
			domainService.removeFromVoluntaryNRP(null, opInfo, domain1);
			AssertJUnit.fail("removing from vnrp requires payment!");
		} catch (DomainIllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void removeFromVsBeforeRenewDatePassesDoesNotRequirePayment() {
		updateState(domain1, 21, pl.nask.crs.commons.utils.DateUtils.getCurrDate(0));
		domainService.removeFromVoluntaryNRP(null, opInfo, domain1);
	}
	

	
	private void updateState(String dname, int state, Date renDt) {
		Domain d= domainDAO.get(dname);
		d.setRenewDate(renDt);
		domainDAO.update(d, state);
	}

	private void checkStateActive(String domain, NRPStatus status) {
		Domain d = domainDAO.get(domain);
		AssertJUnit.assertEquals("nrp status", status, d.getDsmState().getNRPStatus());
		AssertJUnit.assertNull("suspension date", d.getSuspensionDate());
		AssertJUnit.assertNull("deletion date", d.getDeletionDate());
	}

	
	private void updateState(String domain, int state) {
		Domain d = domainDAO.get(domain);
		domainDAO.update(d, state);
	}
	
	private void checkVNRP(String domain, Date dt) {		
		Domain d = domainDAO.get(domain);
		AssertJUnit.assertTrue("suspension date", DateUtils.isSameDay(d.getSuspensionDate(), dt));
		AssertJUnit.assertEquals("NRP Status", NRPStatus.VoluntaryMailed, d.getDsmState().getNRPStatus());
		AssertJUnit.assertNotNull("deletion date should be set", d.getDeletionDate());
	}
}
