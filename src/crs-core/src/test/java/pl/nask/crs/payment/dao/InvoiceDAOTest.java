package pl.nask.crs.payment.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.CustomerType;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.ExtendedInvoice;
import pl.nask.crs.payment.ExtendedInvoiceSearchCriteria;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.InvoiceSearchCriteria;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Transaction;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareInvoices;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceDAOTest extends AbstractContextAwareTest {

    @Resource
    InvoiceDAO invoiceDAO;

    @Test
    public void createTest() {
        List<Invoice> invoices = invoiceDAO.getAll();
        int invoicesCount = invoices.size();
        Invoice invoice = new Invoice(-1, "123", "account name", 1, "address1", null, null, "BillingNH", null, "country", "county", "crsVersion", new Date(), "MD5", false, null, 50, 40, 10, null, null);
        int newId = invoiceDAO.createInvoice(invoice);
        invoices = invoiceDAO.getAll();
        AssertJUnit.assertEquals(invoicesCount + 1, invoices.size());
        Invoice fromDB = invoiceDAO.get(newId);
        compareInvoices(invoice, fromDB);
    }

    @Test
    public void getTest() {
        Invoice invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals("100", invoice.getInvoiceNumber());
        AssertJUnit.assertEquals(1, invoice.getTransactions().size());
        Transaction transaction = invoice.getTransactions().get(0);
        AssertJUnit.assertEquals(7, transaction.getId());
        AssertJUnit.assertEquals(5, (int)transaction.getInvoiceId());
        Assert.assertNotNull(invoice.getSettlementDate());
        AssertJUnit.assertEquals(PaymentMethod.ADP, invoice.getPaymentMethod());
    }

    @Test
    public void findAllTest() {
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("id", true));
        LimitedSearchResult<Invoice> invoices = invoiceDAO.find(null, 0 , 10, sortBy);
        AssertJUnit.assertEquals(5, invoices.getTotalResults());
        AssertJUnit.assertEquals(5, invoices.getResults().size());

        AssertJUnit.assertEquals(5, invoices.getResults().get(0).getId());
        AssertJUnit.assertEquals(10, invoices.getResults().get(1).getId());

        sortBy = Arrays.asList(new SortCriterion("id", false));
        invoices = invoiceDAO.find(null, 0 , 10, sortBy);

        AssertJUnit.assertEquals(13, invoices.getResults().get(0).getId());
        AssertJUnit.assertEquals(12, invoices.getResults().get(1).getId());

    }

    @Test
    public void findSimpleAllTest() {
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("id", true));
        LimitedSearchResult<Invoice> invoices = invoiceDAO.findSimple(null, 0 , 10, sortBy);
        AssertJUnit.assertEquals(5, invoices.getTotalResults());
        AssertJUnit.assertEquals(5, invoices.getResults().size());

        AssertJUnit.assertEquals(5, invoices.getResults().get(0).getId());
        AssertJUnit.assertEquals(10, invoices.getResults().get(1).getId());

        sortBy = Arrays.asList(new SortCriterion("id", false));
        invoices = invoiceDAO.findSimple(null, 0 , 10, sortBy);

        AssertJUnit.assertEquals(13, invoices.getResults().get(0).getId());
        AssertJUnit.assertEquals(12, invoices.getResults().get(1).getId());
    }


    @Test
    public void findWithCriteriaTest() {
        InvoiceSearchCriteria criteria = new InvoiceSearchCriteria();
        LimitedSearchResult<Invoice> invoices = invoiceDAO.find(criteria, 0, 10, null);
        AssertJUnit.assertEquals(5, invoices.getTotalResults());
        AssertJUnit.assertEquals(5, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setCustomerType(CustomerType.REGISTRAR);
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(3, invoices.getTotalResults());
        AssertJUnit.assertEquals(3, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setPaymentMethod(PaymentMethod.ADP);
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(2, invoices.getTotalResults());
        AssertJUnit.assertEquals(2, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setBillingNhId("APIT1-IEDR");
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(1, invoices.getTotalResults());
        AssertJUnit.assertEquals(1, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setSettledFrom(new Date(101, 0, 1));
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(5, invoices.getTotalResults());
        AssertJUnit.assertEquals(5, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setSettledTo(new Date(101, 0, 1));
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(0, invoices.getTotalResults());
        AssertJUnit.assertEquals(0, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setInvoiceNumber("102");
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(1, invoices.getTotalResults());
        AssertJUnit.assertEquals(1, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setInvoiceNumberFrom("101");
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(4, invoices.getTotalResults());
        AssertJUnit.assertEquals(4, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setInvoiceDateTo(DateUtils.endOfDay(new Date(112, 0, 1)));
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(2, invoices.getTotalResults());
        AssertJUnit.assertEquals(2, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setInvoiceDateFrom(DateUtils.startOfDay(new Date(112, 5, 21)));
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(2, invoices.getTotalResults());
        AssertJUnit.assertEquals(2, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setInvoiceDateFrom(DateUtils.startOfDay(new Date(112, 0, 1)));
        criteria.setInvoiceDateTo(DateUtils.endOfDay(new Date(112, 5, 21)));
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(4, invoices.getTotalResults());
        AssertJUnit.assertEquals(4, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setInvoiceDateLike("2012-06");
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(2, invoices.getTotalResults());
        AssertJUnit.assertEquals(2, invoices.getResults().size());

        criteria = new InvoiceSearchCriteria();
        criteria.setSettlementDateLike("2012");
        invoices = invoiceDAO.find(criteria, 0 , 10, null);
        AssertJUnit.assertEquals(5, invoices.getTotalResults());
        AssertJUnit.assertEquals(5, invoices.getResults().size());
    }

    @Test
    public void totalVatMapTest() {
        Invoice invoice = invoiceDAO.get(10);
        Map<BigDecimal, BigDecimal> map = invoice.getTotalVatMap();
        BigDecimal total = map.get(BigDecimal.valueOf(21.40).setScale(2, RoundingMode.HALF_EVEN));
        Assert.assertNotNull(total);
        AssertJUnit.assertEquals(BigDecimal.valueOf(65.27), total);
    }

    @Test
    public void getInvoiceInfoTest() {
        List<DomainInfo> domainInfoList = invoiceDAO.getInvoiceInfo("101");
        AssertJUnit.assertEquals(7, domainInfoList.size());
    }

    @Test
    public void getByNumberTest() {
        Invoice invoice = invoiceDAO.getByNumber("102");
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals(4856, (int)invoice.getTotalCost());
    }

    @Test
    public void lockTest() {
        Invoice invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);

        AssertJUnit.assertTrue(invoiceDAO.lock(5));
    }

    @Test
    public void updateTest() {
        Invoice invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals("", invoice.getMD5());
        AssertJUnit.assertFalse(invoice.isCompleted());

        String md5String = "12345";
        invoice.updateMD5(md5String);
        invoice.setCompleted(true);
        invoiceDAO.update(invoice);

        invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals(md5String, invoice.getMD5());
        AssertJUnit.assertTrue(invoice.isCompleted());
    }

    @Test
    public void testFindExtended() throws Exception {
        ExtendedInvoiceSearchCriteria criteria = new ExtendedInvoiceSearchCriteria();
        LimitedSearchResult<ExtendedInvoice> invoices = invoiceDAO.findExtended(criteria, 0, 20, null);
        AssertJUnit.assertEquals(11, invoices.getTotalResults());
        AssertJUnit.assertEquals(11, invoices.getResults().size());

        criteria.setInvoiceNumberFrom("101");
        criteria.setInvoiceNumberTo("101");
        invoices = invoiceDAO.findExtended(criteria, 0, 10, null);
        AssertJUnit.assertEquals(7, invoices.getTotalResults());
        AssertJUnit.assertEquals(7, invoices.getResults().size());
        
        // check, if the renewal date is filled
        AssertJUnit.assertNotNull("renewalDate", invoices.getResults().get(0).getRenewalDate());

        AssertJUnit.assertNotNull("orderId", invoices.getResults().get(0).getOrderId());
    }
    
    
}
