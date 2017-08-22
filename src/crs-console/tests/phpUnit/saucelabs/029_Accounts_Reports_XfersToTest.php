<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';




class AccountsReportXfers extends PHPUnit_Extensions_SeleniumTestCase
{
    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';

  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }
/**
     * @group DomainReports
     */


  public function testAccountsReportsXfersTo()
  {
//START TEST
    $this->open("/index.php");
//LOG USER IN
    login($this);
    $this->waitForPageToLoad("60000");
    $this->click("link=Transfers To");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("There were 34 domains transferred to your account over the last 30 days or 1.13 per day"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->assertEquals("View 1 - 15 of 34", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 34']"));
    $this->type("id=gs_B", "0792");
    $this->keyPress("id=gs_B", "\\13");
    try {
        $this->assertTrue($this->isTextPresent("View 1 - 1 of 1"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertTrue($this->isTextPresent("example0792.ie"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->click("xpath=//td[@id='refresh_thisJqGrid']/div/span");
    $this->type("id=Rpts_NicXfersModel_days", "10");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//div[@class='row']/h2");
    try {
        $this->assertTrue($this->isTextPresent("View 1 - 8 of 8"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->type("id=Rpts_NicXfersModel_days", "365");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//tr[@id='example0037.ie']//td[.='2012-01-30']");
    try {
        $this->assertTrue($this->isTextPresent("View 1 - 15 of 65"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
//LOGOUT USER  
    logout($this);
}}
?>
