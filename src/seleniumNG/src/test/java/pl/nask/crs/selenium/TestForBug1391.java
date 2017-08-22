package pl.nask.crs.selenium;

import org.testng.annotations.*;
import pl.nask.crs.tools.TestNgBase;

public class TestForBug1391 extends TestNgBase {

    //wymagane do poprawnego nazywania screenshotow
    public TestForBug1391(){
        super(TestForBug1391.class.getName());
    }
    //should pass
    @Test    
    public void test1Bug1391() throws Exception {

        selenium.open("/crs/log-input.action");
		selenium.click("log-in_0");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
		selenium.waitForPageToLoad("30000");
        selenium.type("domains-search_searchCriteria_domainName", "castlebargolfclub.ie");
        selenium.click("domains-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
		selenium.waitForPageToLoad("30000");
		selenium.click("domainview_domainedit_input");
		selenium.waitForPageToLoad("30000");
        selenium.type("adminContact1", "ABC718-IEDR");
        selenium.type("adminContact2", "AHD731-IEDR");
		selenium.type("domainedit_domainWrapper_newRemark", "bug #1391 test");
		selenium.click("domainedit__save");
		selenium.waitForPageToLoad("30000");
        assertEquals("ABC718-IEDR", selenium.getText("testId1"));
        assertEquals("AHD731-IEDR", selenium.getText("testId2"));
		selenium.click("link=Logout");
		selenium.waitForPageToLoad("30000");
	}
//    @Test
//    wont pass becouse of bug 1391
//    public void test2Bug1391() throws Exception {
//
//        selenium.open("/crs/log-input.action");
//        selenium.click("log-in_0");
//        selenium.waitForPageToLoad("30000");
//        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[6]/td/a");
//        selenium.waitForPageToLoad("30000");
//        selenium.click("//img[@alt='View']");
//        selenium.waitForPageToLoad("30000");
//        selenium.click("domainview_domainedit_input");
//        selenium.waitForPageToLoad("30000");
//        selenium.type("adminContact1", "ABC718-IEDR");
//        selenium.type("adminContact2", "AAI538-IEDR");
//        selenium.type("domainedit_domainWrapper_newRemark", "bug #1391 test");
//        selenium.click("domainedit__save");
//        selenium.waitForPageToLoad("30000");
//        assertEquals("ABC718-IEDR", selenium.getText("testId1"));
//        assertEquals("AAI538-IEDR", selenium.getText("testId2"));
//        selenium.click("link=Logout");
//        selenium.waitForPageToLoad("30000");
//    }
}
