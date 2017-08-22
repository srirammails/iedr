<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase/SauceOnDemandTestCase.php';
include_once 'login.php';
include_once 'functions.php';

class AccountsReportsXfersAwayTest extends PHPUnit_Extensions_SeleniumTestCase_SauceOnDemandTestCase {
    public static $browsers = array(
      array(
        'name' => 'Testing NRC AccountsReportsXfersAway',
        'browser' => 'firefox',
        'os' => 'Windows 2003',
        'browserVersion' => '7',
      ),
      array(
        'name' => 'Testing NRC AccountsReportsXfersAway',
        'browser' => 'iexplore',
        'os' => 'Windows 2008',
        'browserVersion' => '9',
      ),
      array(
        'name' => 'Testing NRC AccountsReportsXfersAway',
        'browser' => 'firefox',
        'os' => 'Linux',
        'browserVersion' => '8',
      ),
	array(
        'name' => 'Testing NRC AccountsReportsXfersAway',
        'browser' => 'googlechrome',
        'os' => 'Linux',
        'browserVersion' => '',
      ),
 	array(
        'name' => 'Testing NRC AccountsReportsXfersAway',
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

  public function testXfersAway()
  {

//START TEST
    $this->open("/index.php");
//LOG USER IN
    login($this);
    $this->waitForPageToLoad("60000");
  $this->click("link=Transfers Away");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertEquals("View 1 - 4 of 4", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 4 of 4']"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->type("id=gs_A", "black");
    $this->keyPress("id=gs_A", "\\13");
    $this->click("link=blacknight.ie");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertFalse($this->isTextPresent("Blacknight Internet Solutions Limited"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
//LOGOUT USER
    logout($this);

}
}
?>
