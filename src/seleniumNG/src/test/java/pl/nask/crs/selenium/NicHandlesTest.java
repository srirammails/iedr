package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;

/**
 * @author: Marcin Tkaczyk
 */
public class NicHandlesTest extends TestNgBase {
    public NicHandlesTest() {
        super(NicHandlesTest.class.getName());
    }

    @Test
    public void editTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[9]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("nic-handles-search_searchCriteria_nicHandleId", "AAA906-IEDR");
        selenium.click("nic-handles-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "AAA906-IEDR");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "ELive Technical");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Elive Ltd");
        selenium.click("nic-handle-view_nic-handle-edit_input");
        selenium.waitForPageToLoad("30000");
        selenium.type("nicHandle.name", "ELive 1 Technical");
        selenium.type("nicHandle.companyName", "Elive 1 Ltd");
        selenium.type("nicHandle.email", "newemail@server.kom");
        selenium.type("nic-handle-edit_nicHandle_address", "new adress");
        selenium.select("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[4]/div[2]/div/select", "label=USA");
        selenium.select("//div[@id='countySelect_nicHandle.county_265']/select", "label=Hawaii");
        selenium.click("add");
        selenium.type("//input[@name='wrapper.phonesWrapper.phone' and @type='text']", "666");
        selenium.click("//input[@name='add' and @value='Add entry' and @type='button' and @onclick='addRow_Fax()']");
        selenium.type("//input[@name='wrapper.faxesWrapper.phone' and @type='text']", "666");
        selenium.type("nic-handle-edit_hostmastersRemark", "edit test");
        selenium.click("nic-handle-edit_nic-handle-edit_save");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "AAA906-IEDR");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "ELive 1 Technical");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Elive 1 Ltd");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[1]/div[2]/div/div"), "newemail@server.kom");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[2]/div[2]/div/div"), "new adress");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[3]/div[2]/div/div"), "Hawaii");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[4]/div[2]/div/div"), "USA");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "ELive Technical");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Elive Ltd");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[1]/div[2]/div/div"), "NHEmail000004@server.kom");
        selenium.click("nic-handle-view_nic-handle-view");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void alterStatusChangeTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[9]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("nic-handles-search_searchCriteria_nicHandleId", "AAA906-IEDR");
        selenium.click("nic-handles-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[1]/div[2]/div/div"), "AAA906-IEDR");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Active");
        selenium.click("openAlterStatusDialog");
        selenium.select("alterStatus_status", "label=Suspended");
        selenium.type("alterStatus_hostmastersRemark", "sus");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Suspended");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");           
    }

    @Test
    public void createTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Create");
        selenium.waitForPageToLoad("30000");
        selenium.type("nicHandleCreateWrapper.name", "Test Nic Handle Name");
        selenium.type("nicHandleCreateWrapper.companyName", "Test Comapny");
        selenium.select("nic-handle-create-input_nicHandleCreateWrapper_accountNumber", "label=Elive");
        selenium.type("nicHandleCreateWrapper.email", "newemail@kom.ie");
        selenium.type("nic-handle-create-input_nicHandleCreateWrapper_address", "new address");
        selenium.select("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[4]/div[2]/div/select", "label=Northern Ireland");
        selenium.select("nic-handle-create-input_", "label=Co. Down");
        selenium.click("add");
        selenium.type("//input[@name='nicHandleCreateWrapper.phonesWrapper.phone' and @type='text']", "123");
        selenium.click("//input[@name='add' and @value='Add entry' and @type='button' and @onclick='addRow_Fax()']");
        selenium.type("//input[@name='nicHandleCreateWrapper.faxesWrapper.phone' and @type='text']", "123");
        selenium.click("nic-handle-create-input_nic-handle-create_create");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Test Nic Handle Name");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "Test Comapny");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Active");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[1]/div[2]/div/div"), "newemail@kom.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[2]/div[2]/div/div"), "new address");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[3]/div[2]/div/div"), "Co. Down");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[4]/div[2]/div/div"), "Northern Ireland");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void accessLevelChangeTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[9]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.click("//table[@id='nicHandleRow']/tbody/tr[5]/td[7]/div/a/img");
        selenium.waitForPageToLoad("30000");
        selenium.click("nic-handle-view_nic-handle-access-level_input");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//form[@id='nic-handle-access-level']/table/tbody/tr[1]/td/div/div[2]/div/div/div[2]/div/div"), "AAA906-IEDR");
        assertEquals(selenium.getText("//form[@id='nic-handle-access-level']/table/tbody/tr[2]/td/div/div[2]/div/div/div[1]/div[1]"), "Hostmaster");
        selenium.click("nic-handle-access-level__edit");
        selenium.waitForPageToLoad("30000");
        selenium.click("nic-handle-access-level_wrapper_permissionGroupsWrapper_Technical");
        selenium.click("nic-handle-access-level__save");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//form[@id='nic-handle-access-level']/table/tbody/tr[1]/td/div/div[2]/div/div/div[2]/div/div"), "AAA906-IEDR");
        assertEquals(selenium.getText("//form[@id='nic-handle-access-level']/table/tbody/tr[2]/td/div/div[2]/div/div[1]/div[1]/div[1]"), "Technical");
        assertEquals(selenium.getText("//form[@id='nic-handle-access-level']/table/tbody/tr[2]/td/div/div[2]/div/div[2]/div[1]/div[1]"), "Hostmaster");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");        
    }
}
