<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountTranDeposit extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testPayTranDeposit()
  {

//LOGIN
    $this->open("/index.php");
    login($this);
   $this->waitForPageToLoad("60000");
//GO TO CURRENT XFER INVOICE 
    $this->click("link=Transfers - Pay Current Invoice");
    $this->waitForPageToLoad("60000");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("example0226.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THE DETAILS IN THE INVOICE SUMMARY GRID
    $this->assertEquals("INV990982", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[1]"));
    $this->assertEquals("6", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[2]"));
    $this->assertEquals("€ 84.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[3]"));
    $this->assertEquals("€ 17.64", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    $this->assertEquals("€ 101.64", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    $this->assertEquals("2011-11-30", $this->getText("xpath=//table[@class='gridtable']//td[.='2011-11-30']"));
$this->setSpeed(2500);
//set variables
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);

//VERIFY THAT THE RATE SHOWN FOR THE TEST DOMAINS IS CORRECT
    $this->assertEquals("$price", $this->getText("xpath=//tr[@id='example0236.ie']//td[.='$price']"));
    $this->assertEquals("$vat", $this->getText("xpath=//tr[@id='example0236.ie']//td[.='$vat']"));
//SELECT all DOMAINS FOR PAYMENT
    $this->click("id=cb_thisJqGrid");
//SELECT FOR PAYMENT AND CONFIRM
    $this->click("id=gridaction_dep");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
//set variables
$totalPrice=number_format($price*6,2);
$totalVat=number_format($vat*6,2);
$totalPriceVat=number_format($priceVat*6,2);

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

$arr = array('example0236.ie', 'example0237.ie', 'example0314.ie','example0315.ie','example0318.ie','example0319.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "6", "INV990982");
include("testSuite/paymentCheck.php");
}

}

}



?>
