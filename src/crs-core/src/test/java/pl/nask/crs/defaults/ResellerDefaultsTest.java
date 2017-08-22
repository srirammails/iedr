package pl.nask.crs.defaults;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.defaults.dao.DefaultsDAO;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ResellerDefaultsTest extends AbstractTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    DefaultsDAO defaultsDAO;

    @Test
    public void getTest() {
        ResellerDefaults defaults = defaultsDAO.get("NH2-IEDR");
        AssertJUnit.assertEquals(1, (int)defaults.getDnsNotificationPeriod());
    }

    @Test
    public void getAllTest() {
        List<ResellerDefaults> defaults = defaultsDAO.getAll();
        AssertJUnit.assertEquals(38, defaults.size());
    }

    @Test
    public void createTest() {
        List<ResellerDefaults> defaults = defaultsDAO.getAll();
        AssertJUnit.assertEquals(38, defaults.size());

        ResellerDefaults newDefaults = new ResellerDefaults("X-IEDR", "TECH-IEDR", Arrays.asList("ns1", "ns2", "ns3", "ns4", "ns5"), 5, EmailInvoiceFormat.BOTH);
        defaultsDAO.create(newDefaults);

        defaults = defaultsDAO.getAll();
        AssertJUnit.assertEquals(39, defaults.size());

        ResellerDefaults fromDB = defaultsDAO.get("X-IEDR");
        compareDefaults(newDefaults, fromDB);
    }

    private void compareDefaults(ResellerDefaults newDefaults, ResellerDefaults fromDB) {
        AssertJUnit.assertEquals(newDefaults.getNicHandleId(), fromDB.getNicHandleId());
        AssertJUnit.assertEquals(newDefaults.getTechContactId(), fromDB.getTechContactId());
        AssertJUnit.assertEquals(newDefaults.getNameservers().size(), fromDB.getNameservers().size());
        for (int i = 0; i < newDefaults.getNameservers().size(); i++)
            AssertJUnit.assertEquals(newDefaults.getNameservers().get(i), fromDB.getNameservers().get(i));
        AssertJUnit.assertEquals(newDefaults.getDnsNotificationPeriod(), fromDB.getDnsNotificationPeriod());
        AssertJUnit.assertEquals(newDefaults.getEmailInvoiceFormat(), fromDB.getEmailInvoiceFormat());
    }

    @Test
    public void updateTest() {
        ResellerDefaults beforeUpdate = defaultsDAO.get("IDL2-IEDR");
        AssertJUnit.assertEquals("IDL2-IEDR", beforeUpdate.getNicHandleId());
        AssertJUnit.assertEquals("TDI2-IEDR", beforeUpdate.getTechContactId());
        AssertJUnit.assertEquals(2, beforeUpdate.getNameservers().size());
        AssertJUnit.assertEquals("ns.irishdomains.net", beforeUpdate.getNameservers().get(0));
        AssertJUnit.assertEquals("ns2.irishdomains.net", beforeUpdate.getNameservers().get(1));
        Assert.assertNull(beforeUpdate.getDnsNotificationPeriod());
        AssertJUnit.assertEquals(EmailInvoiceFormat.PDF, beforeUpdate.getEmailInvoiceFormat());

        beforeUpdate.setTechContactId("TECH-IEDR");
        beforeUpdate.setNameservers(Arrays.asList("ns1", "ns2", "ns3"));
        beforeUpdate.setDnsNotificationPeriod(8);
        beforeUpdate.setEmailInvoiceFormat(EmailInvoiceFormat.BOTH);
        defaultsDAO.update(beforeUpdate);

        ResellerDefaults afterUpdate = defaultsDAO.get("IDL2-IEDR");
        compareDefaults(beforeUpdate, afterUpdate);

        beforeUpdate.setNameservers(Arrays.asList("ns3", "ns1"));
        defaultsDAO.update(beforeUpdate);
        afterUpdate = defaultsDAO.get("IDL2-IEDR");
        compareDefaults(beforeUpdate, afterUpdate);
    }
}
