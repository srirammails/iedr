<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class RevertBillable extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group OfflinePaySuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testRevert()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->click("link=Autorenewed Domains");
    $this->waitForPageToLoad("60000");
    $this->click("id=jqg_thisJqGrid_example0633.ie");
    $this->click("id=jqg_thisJqGrid_example0659.ie");
    $this->click("id=jqg_thisJqGrid_example0695.ie");
    $this->click("id=jqg_thisJqGrid_example0724.ie");
    $this->click("id=gridaction_revert");
    $this->waitForPageToLoad("60000");
    $this->click("id=GridSelectionModel_domlist_example0724_confirmed");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("Confirmation"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->click("xpath=//div[@id='content']/form/input");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("View 1 - 24 of 24"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
//LOGOUT
    logout($this);
}

/**
     * @depends testRevert
     * @group testRevertSuccessDbTest
     */

        public function testRevertdbTest()
{
$arr = array( 'example0633.ie', 'example0659.ie', 'example0695.ie');

foreach($arr as $dom)
            {
$arr=getRevertVerify("$dom");

include("testSuite/RevertCheck.php");
}


}

}



?>
