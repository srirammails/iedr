<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayFutRenMSD extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group PayMSDSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testMonthplus2MSD()
  {
//LOGIN
    login($this);
$this->setSpeed(1500);
$this->click("link=Future Renewals");
    $this->waitForPageToLoad("60000");
//LOAD FUTRENs for current month + 2
    $this->select("id=Acc_RnP_AdvPay_RnR_Model_date", "January-2012");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->assertEquals("View 1 - 15 of 123", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 123']"));
    $this->click("id=jqg_thisJqGrid_example0128.ie");
    $this->click("id=jqg_thisJqGrid_example0191.ie");
    $this->click("id=jqg_thisJqGrid_example0218.ie");
    $this->click("xpath=//td[@id='next_thisJqGridPager']/span");
    $this->click("id=jqg_thisJqGrid_example0261.ie");
    $this->click("id=jqg_thisJqGrid_example0276.ie");
    $this->click("id=gridaction_msd");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//div[@id='content']/form/input");
    $this->waitForPageToLoad("60000");

//TODO: add in verifcation of confirmation text - waiting for NASK to insert this in SPRINT3
//LOGOUT
    logout($this);
}

/**
     * @depends testMonthplus2MSD
     * @group MSDDbTest
     */

        public function testMonthplus2MSDdbTest()
{
$arr = array('example0276.ie', 'example0261.ie', 'example0218.ie','example0191.ie','example0128.ie');

foreach($arr as $dom)
            {
$arr=getMSDStatus("$dom", "MailList");

include("testSuite/msdCheck.php");
}


}
}



?>
