<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayTranAdvDeposit extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testTranAdvDeposit()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
//LOAD TRANSFERS ADAVNCE, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Transfers - Pay In Advance");
    $this->waitForPageToLoad("60000");
    $this->assertEquals("View 1 - 15 of 32", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 32']"));
    $this->click("id=jqg_thisJqGrid_example0015.ie");
    $this->type("id=gs_PK", "example0115.ie");
    $this->keyPress("id=gs_PK", "\\13");
    $this->click("id=jqg_thisJqGrid_example0115.ie");
    $this->click("id=gridaction_dep");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
 
//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
//set variables
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);
$totalPrice=number_format($price*2,2);
$totalVat=number_format($vat*2,2);
$totalPriceVat=number_format($priceVat*2,2);

    //THE SUBTOTAL TESTS WILL FAIL UNTIL BUG 753 IS FIXED
//try {
  //      $this->assertEquals("€ " . $totalPrice, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    //} catch (PHPUnit_Framework_AssertionFailedError $e) {
     //   array_push($this->verificationErrors, $e->toString());
//  }
   // try {
  //      $this->assertEquals("€ " . $totalVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    //} catch (PHPUnit_Framework_AssertionFailedError $e) {
      //  array_push($this->verificationErrors, $e->toString());
//    }
  //  try {
    //    $this->assertEquals("€ " .$totalPriceVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[6]"));
   // } catch (PHPUnit_Framework_AssertionFailedError $e) {
    //    array_push($this->verificationErrors, $e->toString());
    //}
   
 try {
        $this->assertEquals("€ " . $totalPrice, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[3]/td[3]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " . $totalVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[3]/td[4]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
      $this->assertEquals("€ " . $totalPriceVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[3]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }

//LOGOUT
    logout($this);
}
/**
     * @depends testDefaultMonthDeposit
     * @group PaymentSuccessDbTest
     */

        public function testDefaultMonthDepositdbTest()
{
$arr = array('example115.ie', 'example0015.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "2", "Advance");
include("testSuite/paymentCheck.php");
}
 


}

}



?>
