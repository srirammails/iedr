package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainTest extends TestNgBase {
    public DomainTest() {
        super(DomainTest.class.getName());
    }

    @Test
    public void editTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("domains-search_searchCriteria_domainName", "klatt.ie");
        selenium.click("domains-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "klatt.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Mateusz Klatt");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Sole Trader");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[5]/div[2]/div/div"), "Registered Business Name");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[3]/div[2]/div/div[3]/div[2]/div/div[1]"), "ABG704-IEDR");
        selenium.click("domainview_domainedit_input");
        selenium.waitForPageToLoad("30000");
        selenium.select("list1", "label=Statutory Body");
        selenium.select("list2", "label=State Agency Name");
        selenium.type("adminContact1", "AHK242-IEDR");
        selenium.type("domainedit_domainWrapper_newRemark", "category, class, admin contact change");
        selenium.click("domainedit__save");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "klatt.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Mateusz Klatt");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Letshost.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Statutory Body");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[5]/div[2]/div/div"), "State Agency Name");
        assertEquals(selenium.getText("testId1"), "AHK242-IEDR");
        assertTrue(selenium.isTextPresent("category, class, admin contact change"));
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void alterStatusChangeTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("domains-search_searchCriteria_domainName", "klatt.ie");
        selenium.click("domains-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Active");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "klatt.ie");
        selenium.click("openAlterStatusDialog");
        selenium.select("alterStatus_status", "label=Suspended");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Suspended");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "klatt.ie");
        assertTrue(selenium.isTextPresent("Alter status."));
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void transferTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("domains-search_searchCriteria_domainName", "klatt.ie");
        selenium.click("domains-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "klatt.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Letshost.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[5]/div[2]/div/div/span"), "unlocked");
        selenium.click("domainview_domaintransfer_input");
        selenium.waitForPageToLoad("30000");
        selenium.select("selectAccount", "label=Blacknight.ie");
        selenium.click("//img[@title='Set to default']");
        selenium.type("adminContact1", "AAE553-IEDR");
        selenium.type("domain_form_domainWrapper_newRemark", "transfer");
        selenium.click("domain_form__transfer");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "klatt.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Blacknight.ie");
        assertEquals(selenium.getText("testId1"), "AAE553-IEDR");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[3]/div[2]/div/div[3]/div[2]/div/div[1]"), "AAM456-IEDR");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[3]/div[2]/div/div[4]/div[2]/div/div[1]"), "AAE553-IEDR");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[5]/div[2]/div/div/span"), "LOCKED");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }
}
