package pl.nask.crs.tools;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.CommandProcessor;

public class ScreenshottingSelenium extends DefaultSelenium {
    private Reporter reporter;
    private String screenStream;

    public ScreenshottingSelenium(CommandProcessor processor, Reporter reporter) {
        super(processor);
        this.reporter = reporter;
        System.out.println("In strange place");
    }

    public ScreenshottingSelenium(String host, int port, String browser, String url, Reporter reporter) {
        super(host, port, browser, url);
        this.reporter = reporter;
    }

    public void start(String testName) {
        reporter.startTest(testName);
        super.start();
//        windowMaximize();
    }

    public void stop() {
        super.stop();
        reporter.endTest();
    }

    public void click(String locator) {
//        windowFocus();
        super.click(locator);
        screenStream = super.captureEntirePageScreenshotToString("background=#CCFFDD");
        reporter.recordStep(screenStream, locator);
    }

    public void open(String url) {
//        windowFocus();
        super.open(url);
        screenStream = super.captureEntirePageScreenshotToString("background=#CCFFDD");
        reporter.recordStep(screenStream, "Opened" + url);
    }

    public void type(String locator, String value) {
//        windowFocus();
        super.type(locator, value);
        screenStream = super.captureEntirePageScreenshotToString("background=#CCFFDD");
        reporter.recordStep(screenStream, "Typed '" + value + "'");
    }

    public void waitForPageToLoad(String timeout) {
        //TODO:dlaczego wykrzacza sie gdy uzyjemy windowFocus()?
        super.waitForPageToLoad(timeout);
        screenStream = super.captureEntirePageScreenshotToString("background=#CCFFDD");
        reporter.recordStep(screenStream, "Page loaded");            
    }

}
