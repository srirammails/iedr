package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class TestForFeature4031 extends TestNgBase {

    public TestForFeature4031() {
        super(TestForFeature4031.class.getName());
    }

    @Test
    public void changeStatusesTest() throws Exception {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("domains-search_searchCriteria_domainName", "autocreated.ie");
        selenium.click("domains-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "autocreated.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Post-Registration Audit");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Yes Billable");
        selenium.click("openAlterStatusDialog");
        assertEquals(selenium.getText("//option[@value='Active']"), "Active");
        assertEquals(selenium.getText("//option[@value='Deleted']"), "Deleted");
        selenium.select("alterStatus_status", "label=Deleted");
        selenium.type("alterStatus_hostmastersRemark", "Alter status changed to Deleted.");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Deleted");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Yes Billable");
        assertTrue(selenium.isTextPresent("Alter status changed to Deleted. by IDL2-IEDR"));
        selenium.click("openAlterStatusDialog");
        assertEquals(selenium.getText("//option[@value='PRA']"), "PRA");
        selenium.select("alterStatus_status", "label=PRA");
        selenium.type("alterStatus_hostmastersRemark", "Alter status changed to Review");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Post-Registration Audit");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Yes Billable");
        selenium.click("openAlterStatusDialog");
        selenium.select("alterStatus_status", "label=Active");
        selenium.type("alterStatus_hostmastersRemark", "Alter status changed to Active");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Active");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Yes Billable");
        assertTrue(selenium.isTextPresent("Alter status changed to Active by IDL2-IEDR"));
        selenium.click("openAlterStatusDialog");
        assertEquals(selenium.getText("//option[@value='Active']"), "Active");
        assertEquals(selenium.getText("//option[@value='Deleted']"), "Deleted");
        assertEquals(selenium.getText("//option[@value='Suspended']"), "Suspended");
        selenium.select("alterStatus_status", "label=Suspended");
        selenium.type("alterStatus_hostmastersRemark", "Alter status changed to Suspended");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Suspended");
        assertTrue(selenium.isTextPresent("Alter status changed to Suspended by IDL2-IEDR"));
        selenium.click("link=Logout");
    }

    @Test
    public void notEnoughtFoundsTest() throws Exception {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("domains-search_searchCriteria_domainName", "autocreated2.ie");
        selenium.click("domains-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "autocreated2.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Post-Registration Audit");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Yes Billable");
        selenium.click("openAlterStatusDialog");
        selenium.select("alterStatus_status", "label=Active");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Post-Registration Audit");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Transaction Failed");
        assertTrue(selenium.isTextPresent("Payment transaction failed - not enought deposit funds by IDL2-IEDR"));
        selenium.click("link=Logout");
    }
}
