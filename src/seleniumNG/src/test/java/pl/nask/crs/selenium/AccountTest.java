package pl.nask.crs.selenium;

import pl.nask.crs.tools.TestNgBase;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

/**
 * @author: Marcin Tkaczyk
 */
public class AccountTest extends TestNgBase {

    public AccountTest() {
        super(AccountTest.class.getName());
    }

    @Test
    public void editTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[15]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("accounts-search_searchCriteria_id", "114");
        selenium.click("accounts-search_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//img[@alt='View']");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Register 365");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[5]/div[2]/div/div"), "January");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Monthly");
        selenium.click("account-view-view_account-edit-input");
        selenium.waitForPageToLoad("30000");
        selenium.select("account-edit-input_account_nextInvMonth", "label=February");
        selenium.click("account-edit-input_account-edit-save");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Register 365");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[5]/div[2]/div/div"), "February");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @Test
    public void createTest() {
        selenium.open("/crs/log-input.action");
        selenium.click("log-in_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='nav']/div/div[2]/div/table/tbody/tr[16]/td/a");
        selenium.waitForPageToLoad("30000");
        selenium.type("accountCreateWrapper.name", "Account Create Test");
        selenium.type("accountCreateWrapper.webAddress", "www.accountcreatetest.ie");
        selenium.click("account-create-input_accountCreateWrapper_agreementSigned");
        selenium.click("ticketEditChkbx");
        selenium.type("billingContact", "AAH905-IEDR");
        selenium.type("account-create-input_accountCreateWrapper_address", "test adress");
        selenium.select("//table[@id='details']/tbody/tr/td[2]/div[1]/div[2]/div/div[3]/div[2]/div/select", "label=Afghanistan");
        selenium.type("accountCreateWrapper.phone", "11111");
        selenium.type("accountCreateWrapper.fax", "1111");
        selenium.type("account-create-input_hostmastersRemark", "Created test account");
        selenium.click("account-create-input_account-create_create");
        selenium.waitForPageToLoad("30000");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[2]/div[2]/div/div"), "Account Create Test");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[3]/div[2]/div/div"), "www.accountcreatetest.ie");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[4]/div[2]/div/div"), "Monthly");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[5]/div[2]/div/div"), "January");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[6]/div[2]/div/div"), "Trade");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[7]/div[2]/div/div"), "YES");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[1]/div[2]/div/div[8]/div[2]/div/div"), "YES");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[1]/div[2]/div[2]/div/div[1]/div[2]/div/div[1]"), "AAH905-IEDR");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[1]/div[2]/div/div"), "test adress");
        assertEquals(selenium.getText("//table[@id='details']/tbody/tr/td[2]/div[2]/div[2]/div/div[3]/div[2]/div/div"), "Afghanistan");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }
}
