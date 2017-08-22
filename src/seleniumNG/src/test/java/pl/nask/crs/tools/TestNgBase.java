package pl.nask.crs.tools;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.DefaultSelenium;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class TestNgBase extends SeleneseTestBase {

    protected Selenium selenium;
    private static Reporter reporter;
    private String testName;

    public TestNgBase(String testName) {
        this.testName = testName;
    }

    @BeforeSuite
    public void setupReporter() {
        try {
            reporter = new Reporter(new File("target" + File.separator + "screenshots"));
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    @Parameters({"selenium.host","selenium.port","selenium.browser","selenium.url"})
    public void openBrowser(String host, String port, String browser, String url){
        selenium = new ScreenshottingSelenium(host, Integer.parseInt(port), browser, url, reporter);
        selenium.start(testName);
    }

    @AfterMethod
    public void closeBrowser() {
        selenium.stop();
    }

    @AfterSuite
    public void cleanupReporter() {
        try {
            reporter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
