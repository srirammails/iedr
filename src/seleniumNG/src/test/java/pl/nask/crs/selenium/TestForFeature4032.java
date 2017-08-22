package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class TestForFeature4032 extends TestNgBase {

    public TestForFeature4032() {
        super(TestForFeature4032.class.getName());
    }

    @Test
    public void searchByStatusesTest() throws Exception {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//select[@id='domains-search_searchCriteria_domainStatus']/option[1]"), "[All]");
        assertEquals(selenium.getText("//option[@value='Active']"), "Active");
        assertEquals(selenium.getText("//option[@value='Deleted']"), "Deleted");
        assertEquals(selenium.getText("//option[@value='Suspended']"), "Suspended");
        assertEquals(selenium.getText("//option[@value='PRA']"), "Post-Registration Audit");
        selenium.select("domains-search_searchCriteria_domainStatus", "label=Suspended");
        selenium.click("domains-search_0");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='domainRow']/tbody/tr[2]/td[8]"), "Suspended");
        assertEquals(selenium.getText("//table[@id='domainRow']/tbody/tr[1]/td[8]"), "Suspended");
        selenium.click("link=Logout");
    }
}
