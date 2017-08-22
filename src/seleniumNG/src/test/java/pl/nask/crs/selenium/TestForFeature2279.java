package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;

/**
 * @author: Marcin Tkaczyk
 */
public class TestForFeature2279 extends TestNgBase {

    public TestForFeature2279() {
        super(TestForFeature2279.class.getName());
    }

    @Test
    public void testForFeature2279no1() throws Exception {
		selenium.open("/crs/log-input.action");
		selenium.click("log-in_0");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[12]/td/a");
		selenium.waitForPageToLoad("30000");
		selenium.click("//table[@id='documentRow']/tbody/tr[5]/td[3]/a/img");
		selenium.waitForPageToLoad("30000");
		selenium.type("documents-nameUpdate_document_domainNames", "easy.ie, george.ie, additional.ie");
		selenium.click("documents-nameUpdate_0");
		selenium.waitForPageToLoad("30000");
		verifyEquals(selenium.getTable("documentRow.5.1"), "additional.ie, easy.ie, george.ie");
	}    
}
