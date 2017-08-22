<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayTranAdvOnline extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group PayOnlineSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testTranAdvOnline()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
//set variables
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);

//LOAD TRANSFERS ADAVNCE, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Transfers - Pay In Advance");
    $this->waitForPageToLoad("60000");
    $this->setSpeed(2500);
    $this->assertEquals("View 1 - 15 of 34", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 34']"));
    $this->click("xpath=//td[@id='next_thisJqGridPager']/span");
    $this->type("id=gs_PK", "example0792.ie");
    $this->keyPress("id=gs_PK", "\\13");
    $this->assertEquals("$price", $this->getText("xpath=//tr[@id='example0792.ie']//td[.='$price']"));
    $this->assertEquals("$vat", $this->getText("xpath=//tr[@id='example0792.ie']//td[.='$vat']"));
    $this->click("id=jqg_thisJqGrid_example0792.ie");
    $this->type("id=gs_PK", "example0014");
    $this->keyPress("id=gs_PK", "\\13");
    $this->click("id=jqg_thisJqGrid_example0014.ie");
    $this->click("id=gridaction_on");
    $this->waitForPageToLoad("60000");
    $this->type("id=GridSelectionModel_holder", "ciara conlon");
    $this->type("id=GridSelectionModel_creditcardno", "4263971921001307");
    $this->type("id=GridSelectionModel_exp_month", "12");
    $this->type("id=GridSelectionModel_exp_year", "12");
    $this->type("id=GridSelectionModel_cvn", "123");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
 
//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
//set variables
$totalPrice=number_format($price*2,2);
$totalVat=number_format($vat*2,2);
$totalPriceVat=number_format($priceVat*2,2);

    try {
        $this->assertEquals("€ $totalPrice", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ $totalVat" , $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ $totalPriceVat" , $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[6]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
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
     * @depends testDefaultMonthOnline
     * @group PaymentSuccessDbTest
     */

        public function testDefaultMonthOnlinedbTest()
{
$arr = array('example0792.ie', 'example0014.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "2", "Advance");
include("testSuite/paymentCheck.php");
}
 


}

}



?>
