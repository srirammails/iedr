<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class ManageOfflinePayment extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testCurRenRegOffline()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
    $this->setSpeed(1500);
    $this->click("link=Renewals and New Registrations");
    $this->waitForPageToLoad("60000");
    $this->type("id=gs_PK", "example019");
    $this->keyPress("id=gs_PK", "\\13");
    $this->click("id=jqg_thisJqGrid_example0194.ie");
    $this->click("id=jqg_thisJqGrid_example0195.ie");
    $this->click("id=jqg_thisJqGrid_example0196.ie");
    $this->click("id=jqg_thisJqGrid_example0199.ie");
    $this->click("id=gridaction_off");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
//    $order_id = $this->getText("xpath=//table[@class='gridtable']//td[2]/tr[4]");
    $this->click("link=Manage Offline Payments");
    $this->waitForPageToLoad("60000");
    $this->assertEquals("67.76", $this->getText("xpath=//tr[@id='$order_id']//td[.='€ 67.76']"));
    $this->click("link=$order_id");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//html/body");
    try {
        $this->assertTrue($this->isTextPresent("€ 67.76"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertTrue($this->isTextPresent("€ 2.94"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertTrue($this->isTextPresent("€ 14.00"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->click("id=jqg_thisJqGrid_example0194.ie");
    $this->click("id=jqg_thisJqGrid_example0195.ie");
    $this->click("xpath=//html/body");
    $this->click("id=gridaction_remove_from_batch");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//div[@class='container']//h1[.='Exception']");
//TODO: BUG waiting to be fixed - exception is shown on confirmation page (action completes correctly)
 
//LOGOUT
    logout($this);
}
/**
     * @depends testCurRenRegOffline
     * @group payOfflinedbTest
     */

        public function testtestCurRenRegOfflinedbTest($order_id)
{
$arr = array('example0057.ie', 'example0031.ie', 'example0088.ie', 'example0095.ie');

foreach($arr as $dom)
{
function getOfflineVerify($order_id, $rate, $count, $vat) 
               {
$arr=getPayOfflineStatus("$order_id", "RRen1Yr", "2", "vat");
include("testSuite/manageOfflineCkeck.php");
}}


}

}



?>
