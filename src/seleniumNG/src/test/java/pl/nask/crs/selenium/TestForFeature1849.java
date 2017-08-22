package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;

/**
 * User: Marcin Tkaczyk
 * Date: 2010-03-16
 * Time: 13:30:07
 */
public class TestForFeature1849 extends TestNgBase {

    //wymagane do poprawnego nazywania screenshotow
    public TestForFeature1849(){
        super(TestForFeature1849.class.getName());
    }
    @Test
    public void testForFeature1849no1() throws Exception {
        selenium.open("/crs/log-input.action");
		selenium.click("log-in_0");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
		selenium.waitForPageToLoad("30000");
		selenium.type("domains-search_searchCriteria_domainName", "suka.ie");
		selenium.click("domains-search_0");
		selenium.waitForPageToLoad("30000");
		selenium.click("//img[@alt='View']");
		selenium.waitForPageToLoad("30000");
		selenium.click("domainview_domainedit_input");
		selenium.waitForPageToLoad("30000");
		selenium.click("domainedit_domainview");
		selenium.waitForPageToLoad("30000");
		assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Suspended");
		assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Mailed");
		assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[5]/div[2]/div/div/span"), "LOCKED");
		selenium.click("domainview_domainedit_input");
        selenium.waitForPageToLoad("30000");
        selenium.select("id=billStatusId", "label=Charity Non-Billable");
        selenium.type("domainedit_domainWrapper_newRemark", "charity test");
		selenium.click("domainedit__save");
        selenium.waitForPageToLoad("30000");        
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Active");
		assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Charity Non-Billable");
		assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[5]/div[2]/div/div/span"), "unlocked");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");        
    }
}
