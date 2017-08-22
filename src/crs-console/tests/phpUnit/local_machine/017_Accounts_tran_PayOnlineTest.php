<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountTranOnline extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testPayTranOnline()
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
            if ($this->isTextPresent("example0214.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THE DETAILS IN THE INVOICE SUMMARY GRID
$this->assertEquals("INV990982", $this->getText("xpath=//table[@class='gridtable']//td[.='INV990982']"));
    $this->assertEquals("25", $this->getText("xpath=//table[@class='gridtable']//td[.='25']"));
    $this->assertEquals("€ 350.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[3]"));
    $this->assertEquals("€ 73.50", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    $this->assertEquals("€ 423.50", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    $this->assertEquals("2011-11-30", $this->getText("xpath=//table[@class='gridtable']//td[.='2011-11-30']"));
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);



//VERIFY THAT THE RATE SHOWN FOR THE TEST DOMAINS IS CORRECT
   // $this->assertEquals("$price", $this->getText("xpath=//tr[@id='example0214.ie']//td[.='$price']"));
   // $this->assertEquals("$vat", $this->getText("xpath=//tr[@id='example0214.ie']//td[.='$vat']"));
//SELECT TWO DOMAINS FOR PAYMENT
    $this->click("id=jqg_thisJqGrid_example0214.ie");
    $this->click("id=jqg_thisJqGrid_example0228.ie");
//SELECT FOR ONLINE PAYMENT AND ENTER CREDIT CARD DETAILS
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

$arr = array('example0228.ie', 'example0214.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "2", "INV990982");
include("testSuite/paymentCheck.php");
}

}

}



?>
