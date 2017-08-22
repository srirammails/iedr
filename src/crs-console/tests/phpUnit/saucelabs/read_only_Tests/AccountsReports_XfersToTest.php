<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase/SauceOnDemandTestCase.php';
include_once 'login.php';
include_once 'functions.php';

class AccountsReportsXfersToTest extends PHPUnit_Extensions_SeleniumTestCase_SauceOnDemandTestCase {
    public static $browsers = array(
      array(
        'name' => 'Testing NRC AccountsReportsXfersTo',
        'browser' => 'firefox',
        'os' => 'Windows 2003',
        'browserVersion' => '7',
      ),
      array(
        'name' => 'Testing NRC AccountsReportsXfersTo',
        'browser' => 'iexplore',
        'os' => 'Windows 2008',
        'browserVersion' => '9',
      ),
      array(
        'name' => 'Testing NRC AccountsReportsXfersTo',
        'browser' => 'firefox',
        'os' => 'Linux',
        'browserVersion' => '8',
      ),
	array(
        'name' => 'Testing NRC AccountsReportsXfersTo',
        'browser' => 'googlechrome',
        'os' => 'Linux',
        'browserVersion' => '',
      ),
 	array(
        'name' => 'Testing NRC AccountsReportsXfersTo',
        'browser' => 'googlechrome',
        'os' => 'Windows 2008',
        'browserVersion' => '',
      )
    );

    function setUp() {
        $this->setBrowserUrl('http://newregcon.iedr.ie');
    }


 /**
     * @group ReportTest
     */

  public function testXfersTo()
  {
    $this->open("/index.php");
  //START TEST
    $this->open("/index.php");
//LOG USER IN
    login($this);
    $this->waitForPageToLoad("60000");
    $this->click("link=Transfers To");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("There were 59 domains transferred to your account over the last 30 days or 1.9 per day"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->assertEquals("View 1 - 15 of 59", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 59']"));
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
        $this->assertTrue($this->isTextPresent("View 1 - 35 of 34"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->type("id=Rpts_NicXfersModel_days", "365");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
        $this->assertTrue($this->isTextPresent("example0037.ie"));
    try {
        $this->assertTrue($this->isTextPresent("View 1 - 15 of 65"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
//LOGOUT USER 
    logout($this);
}
}
?>
