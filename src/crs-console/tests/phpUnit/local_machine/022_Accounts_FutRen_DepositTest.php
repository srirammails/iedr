<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayFutRenDeposit extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group PayDepositSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testDefaultMonthDeposit()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
$this->click("link=Future Renewals");
    $this->waitForPageToLoad("60000");
    $this->select("id=Acc_RnP_AdvPay_RnR_Model_date", "December-2011");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertEquals("View 1 - 15 of 15", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 15']"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->click("id=cb_thisJqGrid");
    $this->click("id=gridaction_dep");
    $this->waitForPageToLoad("60000");
    $this->select("id=GridSelectionModel_domlist_example0139_prodcode", "3");
    $this->select("id=GridSelectionModel_domlist_example0160_prodcode", "4");
    $this->select("id=GridSelectionModel_domlist_example0163_prodcode", "5");
    $this->select("id=GridSelectionModel_domlist_example0164_prodcode", "2");
    $this->select("id=GridSelectionModel_domlist_example0165_prodcode", "6");
    $this->select("id=GridSelectionModel_domlist_example0166_prodcode", "7");
    $this->select("id=GridSelectionModel_domlist_example0167_prodcode", "8");
    $this->select("id=GridSelectionModel_domlist_example0168_prodcode", "9");
    $this->select("id=GridSelectionModel_domlist_example0169_prodcode", "10");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//html/body");

//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
//TODO: set variables for multi year prices
 
//Comment out verification of subtotals until this has been fixed
 //   try {
     //   $this->assertEquals("€ $totalPrice", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
   // } catch (PHPUnit_Framework_AssertionFailedError $e) {
    //    array_push($this->verificationErrors, $e->toString());
   // }
   // try {
     //   $this->assertEquals("€ $totalVat" , $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[6]"));
    //} catch (PHPUnit_Framework_AssertionFailedError $e) {
    //    array_push($this->verificationErrors, $e->toString());
    //}
    //try {
   //     $this->assertEquals("€ $totalPriceVat" , $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[7]"));
    //} catch (PHPUnit_Framework_AssertionFailedError $e) {
  //      array_push($this->verificationErrors, $e->toString());
//   }

  try {
        $this->assertEquals("€ 772.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[12]/td[3]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ 162.12", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[12]/td[4]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
      $this->assertEquals("€ 934.12", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[12]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }

//LOGOUT
    logout($this);

}

/**
     * @depends testDefaultMonthDeposit
     * @group PaySuccessdbTest
     */

  public function testDefaultMonthDepositdbTest()
{
$arr = array('example0138.ie', 'example0139.ie', 'example0158.ie','example0160.ie','example0162.ie','example0163.ie', 'example0164.ie', 'example0165.ie', 'example0166.ie', 'example0167.ie', 'example0168.ie', 'example0169.ie', 'example0170.ie', 'example0171.ie', 'example0172.ie');

foreach($arr as $dom)
               {
$arr=getMYRPaymentStatus("$dom", "Advance", "vat");
include("testSuite/MYRpaymentCheck.php");
}

}

}


?>
