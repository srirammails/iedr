package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketTest extends TestNgBase {
    public TicketTest() {
        super(TicketTest.class.getName());
    }

    @Test
    public void editAndRejectTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Search");
        selenium.waitForPageToLoad("30000");
        selenium.type("tickets-search_searchCriteria_domainName", "taga.ie");
        selenium.click("tickets-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Check Out']");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Revise and Edit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div/div"), "256768");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[1]/div"), "taga.ie");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[5]/div[2]/div[2]/div[1]/div"), "Taga B.V.");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[7]/div[2]/div[2]/div[1]/div"), "BODY CORPORATE (LTD,PLC,COMPANY)");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[8]/div[2]/div[2]/div[1]/div"), "REGISTERED BUSINESS NAME");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[3]/div[2]/div/div[1]/div[2]/div[2]/div[1]/div"), "AHJ843-IEDR");
        selenium.click("ticketrevise-input__edit");
        selenium.waitForPageToLoad("30000");
        selenium.type("domainHolder", "Taga B.V.C.");
        selenium.select("list1", "label=Natural Person");
        selenium.select("list2", "label=Discretionary Name");
        selenium.type("adminContact1", "AAX317-IEDR");
        selenium.type("hostmasterMessage", "edit");
        selenium.click("ticketedit-input__forceSave");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[5]/div[2]/div[2]/div[1]/div"), "Taga B.V.C.");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[7]/div[2]/div[2]/div[1]/div"), "Natural Person");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[8]/div[2]/div[2]/div[1]/div"), "Discretionary Name");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[3]/div[2]/div/div[1]/div[2]/div[2]/div[1]/div"), "AAX317-IEDR");
        selenium.select("nameserverWrappers[0].nameFr", "label=IP dest host unreachable");
        selenium.type("hostmasterMessage", "bad ip");
        selenium.click("ticketrevise-input__save");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("nameserverWrappers[0].nameFr"), "(none) Incorrect IP buffer too small IP Destination net unreachable IP dest host unreachable IP dest port unreachable IP dest port unreachable IP no resources IP bad option IP hw_error IP packet too_big IP req timed out IP bad req IP bad route IP ttl expired transit IP ttl expired reassem IP param_problem IP source quench IP option too_big IP bad destination IP addr deleted IP spec mtu change IP mtu_change IP unload IP addr added IP general failure IP pending ping timeout unknown msg returned Bad IP format");
        selenium.type("hostmasterMessage", "bad ns");
        selenium.click("openRejectDialog");
        selenium.select("ticketrevise-input_newAdminStatus", "label=Cancelled");
        selenium.click("ticketrevise-input__reject");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[5]"), "Cancelled");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void acceptFailedTest() throws Exception {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[3]/td/a/img");
        selenium.waitForPageToLoad("30000");
        selenium.type("tickets-search_searchCriteria_domainName", "legalcareer.ie");
        selenium.click("tickets-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Check Out']");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Revise and Edit']");
        selenium.waitForPageToLoad("30000");
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            if (selenium.isElementPresent("messageTemplates")) break;
            Thread.sleep(1000);
        }
        selenium.click("messageTemplates");
        selenium.select("messageTemplates", "label=aa ACCEPT");
        selenium.click("//option[@value='77']");
        selenium.click("ticketrevise-input__accept");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("There should be no failure reason for holder class"));
        assertTrue(selenium.isTextPresent("There should be no failure reason for holder category"));
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div/div"), "257244");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");        
    }

    @Test
    public void acceptTest() throws Exception {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Search");
        selenium.waitForPageToLoad("30000");
        selenium.type("tickets-search_searchCriteria_domainName", "chinwagger.ie");
        selenium.click("tickets-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Check Out']");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Revise and Edit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div/div"), "257241");
        assertEquals(selenium.getText("//form[@id='ticketrevise-input']/table/tbody/tr[1]/td[1]/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[1]/div"), "chinwagger.ie");
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            if (selenium.isElementPresent("messageTemplates")) break;
            Thread.sleep(1000);
        }
        selenium.click("messageTemplates");
        selenium.select("messageTemplates", "label=aa ACCEPT");
        selenium.click("//option[@value='77']");
        selenium.click("ticketrevise-input__accept");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[3]"), "chinwagger.ie");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[5]"), "Passed");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void checkInTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[3]/td/a/img");
        selenium.waitForPageToLoad("30000");
        selenium.type("tickets-search_searchCriteria_domainName", "wyki.ie");
        selenium.click("tickets-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Check Out']");
        selenium.waitForPageToLoad("30000");
        selenium.click("openCheckInDialog257240");
        selenium.select("newAdminStatus", "label=Passed");
        selenium.click("//input[@value='Submit' and @type='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[3]"), "wyki.ie");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[5]"), "Passed");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void alterStatusChangeTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Search");
        selenium.waitForPageToLoad("30000");
        selenium.type("tickets-search_searchCriteria_domainName", "shortstaydublin.ie");
        selenium.click("tickets-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Check Out']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[3]"), "shortstaydublin.ie");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[5]"), "Hold Paperwork");        
        selenium.click("openAlterStatusDialog257235");
        selenium.select("//div[@id='alterStatusDialog257235']/div/div[2]/div/form/center[2]/select", "label=Document(s) submitted");
        selenium.click("//div[@id='alterStatusDialog257235']/div/div[2]/div/form/center[3]/input[1]");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[3]"), "shortstaydublin.ie");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[5]"), "Document(s) submitted");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void reassignTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[3]/td/a/img");
        selenium.waitForPageToLoad("30000");
        selenium.type("tickets-search_searchCriteria_domainName", "makingadifference.ie");
        selenium.click("tickets-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='Check Out']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[3]"), "makingadifference.ie");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[2]"), "GUEST ACCOUNT");        
        selenium.click("openReassignDialog257028");
        selenium.select("newHostmaster", "label=Iron Mountain Inc");
        selenium.click("//div[@id='reassignDialog257028']/form/div/div[2]/div/center[3]/input[1]");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='ticketRow']/tbody/tr/td[7]"), "AAE359-IEDR");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }
}
