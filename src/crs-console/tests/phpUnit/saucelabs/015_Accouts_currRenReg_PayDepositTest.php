<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayDeposit extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testPayCurrRenRegDeposit()
  {
//set prices
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);

//LOGIN
$this->setSpeed(2500);
    $this->open("/index.php");
    login($this);
   $this->waitForPageToLoad("60000");
//GO TO CURRENT R&R INVOICE 
    $this->click("link=Renewals and New Registrations");
    $this->waitForPageToLoad("60000");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("example0059.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THAT THE RATE SHOWN FOR THE TEST DOMAINS IS CORRECT
    $this->assertEquals("$price", $this->getText("xpath=//tr[@id='example0059.ie']//td[.='$price']"));
    $this->assertEquals("$priceVat", $this->getText("xpath=//tr[@id='example0059.ie']//td[.='$priceVat']"));
//SELECT DOMAINS FOR PAYMENT
    $this->click("id=jqg_thisJqGrid_example0059.ie");
    $this->click("id=jqg_thisJqGrid_example0094.ie");
    $this->click("id=jqg_thisJqGrid_example0033.ie");
//SELECT AND CONFIRM ACTION
    $this->click("id=gridaction_dep");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
//VERIFY ACTION CANT BE COMPLETED because not enough funds exist
//$this->assertTextPresent("You do not have enough founds in your account to complete this action.");

//TOP UP DEPOSIT ACCOUNT
    $this->click("link=Top Up Deposit Account");
    $this->waitForPageToLoad("60000");
    $this->type("id=AccountTopUpModel_holder", "john doe");
    $this->type("id=AccountTopUpModel_creditcardno", "4263971921001307");
    $this->type("id=AccountTopUpModel_exp_month", "12");
    $this->type("id=AccountTopUpModel_exp_year", "12");
    $this->type("id=AccountTopUpModel_euros_amount", "10000.00");
    $this->type("id=AccountTopUpModel_cvn", "123");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//html/body");
    try {
        $this->assertTrue($this->isTextPresent("Transaction Successful"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }


//TRY TRANSACTION AGAIN
//GO TO CURRENT R&R INVOICE
    $this->click("link=Renewals and New Registrations");
    $this->waitForPageToLoad("60000");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("example0059.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THAT THE RATE SHOWN FOR THE TEST DOMAINS IS CORRECT
    $this->assertEquals("15", $this->getText("xpath=//tr[@id='example0059.ie']//td[.='15']"));
    $this->assertEquals("3.15", $this->getText("xpath=//tr[@id='example0059.ie']//td[.='3.15']"));
//SELECT DOMAINS FOR PAYMENT
    $this->click("id=jqg_thisJqGrid_example0059.ie");
    $this->click("id=jqg_thisJqGrid_example0094.ie");
    $this->click("id=jqg_thisJqGrid_example0033.ie");
//SELECT AND CONFIRM ACTION
    $this->click("id=gridaction_dep");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");

//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
$this->waitForPageToLoad("60000");

$totalPrice=number_format($price*3,2);
$totalVat=number_format($vat*3,2);
$totalPriceVat=number_format($priceVat*3,2);
//THE SUBTOTAL TESTS WILL FAIL UNTIL BUG 753 IS FIXED
try {
        $this->assertEquals("€ " . $totalPrice, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " . $totalVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " .$totalPriceVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[6]"));
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

$arr = array('example0059.ie', 'example0094.ie', 'example0033.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "3", "INV990992");
include("testSuite/paymentCheck.php");
}




}}




?>
