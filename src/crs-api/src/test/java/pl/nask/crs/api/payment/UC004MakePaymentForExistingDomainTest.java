package pl.nask.crs.api.payment;

import static pl.nask.crs.api.Helper.createBasicCCPaymentRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.DomainWithPeriodVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.PaymentSummaryVO;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.NotEnoughtDepositFundsException;
import pl.nask.crs.security.authentication.AuthenticationException;
/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class UC004MakePaymentForExistingDomainTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSPaymentAppService crsPaymentAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    AuthenticatedUserVO user;

    @BeforeMethod
	public void authenticate() throws IllegalArgumentException, AuthenticationException {
    	user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
    }


    @Test
    public void SC03Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 2), new DomainWithPeriodVO("payDomain2.ie", 2));
        PaymentRequestVO paymentRequest = createBasicCCPaymentRequest();
        PaymentSummaryVO summaryVO = crsPaymentAppService.pay(user, domains, PaymentMethod.CC, paymentRequest, false);
        AssertJUnit.assertEquals(BigDecimal.valueOf(80), summaryVO.getAmount());
        AssertJUnit.assertEquals(BigDecimal.valueOf(16).setScale(2), summaryVO.getVat().setScale(2));
        AssertJUnit.assertEquals(BigDecimal.valueOf(96).setScale(2), summaryVO.getTotal().setScale(2));
        Assert.assertNotNull(summaryVO.getOrderId());
    }

    @Test(expectedExceptions = NotEnoughtDepositFundsException.class)
    public void SC07Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomain3.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        PaymentSummaryVO paymentSummaryVO = crsPaymentAppService.pay(user, domains, PaymentMethod.ADP, null, false);
    }

    @Test
    public void SC17Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomainWIPODisputed.ie", 2));
        PaymentSummaryVO summaryVO = crsPaymentAppService.pay(user, domains, PaymentMethod.ADP, null, false);
        AssertJUnit.assertEquals(BigDecimal.valueOf(40), summaryVO.getAmount());
        AssertJUnit.assertEquals(BigDecimal.valueOf(8).setScale(2), summaryVO.getVat().setScale(2));
        AssertJUnit.assertEquals(BigDecimal.valueOf(48).setScale(2), summaryVO.getTotal().setScale(2));
        Assert.assertNotNull(summaryVO.getOrderId());
    }

    @Test
    public void SC18Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomainWIPODisputedAutorenew.ie", 2));
        PaymentSummaryVO summaryVO = crsPaymentAppService.pay(user, domains, PaymentMethod.ADP, null, false);
        AssertJUnit.assertEquals(BigDecimal.valueOf(40), summaryVO.getAmount());
        AssertJUnit.assertEquals(BigDecimal.valueOf(8).setScale(2), summaryVO.getVat().setScale(2));
        AssertJUnit.assertEquals(BigDecimal.valueOf(48).setScale(2), summaryVO.getTotal().setScale(2));
        Assert.assertNotNull(summaryVO.getOrderId());
    }


}
