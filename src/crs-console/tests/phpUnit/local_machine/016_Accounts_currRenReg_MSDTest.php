<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayMSD extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group MSDSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testMSDCurrRenReg()
  {
//LOGIN
$this->setSpeed(500);
    $this->open("/index.php");
    login($this);
$this->click("link=Renewals and New Registrations");
    $this->waitForPageToLoad("60000");
$this->select("xpath=//td[@id='thisJqGridPager_center']//select[.='51015202550100']", "50");    
    $this->click("id=jqg_thisJqGrid_example0135.ie");
    $this->click("id=jqg_thisJqGrid_example0157.ie");
    $this->click("id=jqg_thisJqGrid_example0177.ie");
    $this->click("id=jqg_thisJqGrid_example0178.ie");
    $this->click("id=jqg_thisJqGrid_example0179.ie");
    $this->click("id=jqg_thisJqGrid_example0188.ie");
    $this->click("id=jqg_thisJqGrid_example0190.ie");
    $this->click("id=jqg_thisJqGrid_example0134.ie");
    $this->click("id=gridaction_msd");
    $this->waitForPageToLoad("60000");
    $this->click("id=GridSelectionModel_domlist_example0157_confirmed");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("Your request to add the domain(s) below to MSD was successful."));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }

//LOGOUT    
    logout($this);

}
/**
     * @depends testMSDCurrRenReg
     * @group MSDSuccessDbTest
     */

        public function testMSDCurrRenRegdbTest()
{
$arr = array('example0188.ie', 'example0134.ie', 'example0190.ie', 'example0135.ie', 'example0177.ie', 'example0179.ie');

foreach($arr as $dom)
               {
$arr=getMSDStatus("$dom", "MailList");

include("testSuite/msdCheck.php");
}
 


}

}



?>
