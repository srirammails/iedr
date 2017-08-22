<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountTranOffline extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group PayOfflineSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testPayTranOffline()
  {

//LOGIN
    $this->open("/index.php");
    login($this);
   $this->waitForPageToLoad("60000");
//GO TO CURRENT XFER INVOICE 
$this->setSpeed(500);
    $this->click("link=Transfers - Pay Current Invoice");
    $this->waitForPageToLoad("60000");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("example0136.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THE DETAILS IN THE INVOICE SUMMARY GRID
$this->assertEquals("INV990982", $this->getText("xpath=//table[@class='gridtable']//td[.='INV990982']"));
    $this->assertEquals("23", $this->getText("xpath=//table[@class='gridtable']//td[.='23']"));
    $this->assertEquals("€ 345.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[3]"));
    $this->assertEquals("€ 72.45", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    $this->assertEquals("€ 417.45", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    $this->assertEquals("2011-11-30", $this->getText("xpath=//table[@class='gridtable']//td[.='2011-11-30']"));

//set variables
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);

//VERIFY THAT THE RATE SHOWN FOR THE TEST DOMAINS IS CORRECT
    $this->assertEquals("$price", $this->getText("xpath=//tr[@id='example0136.ie']//td[.='$price']"));
    $this->assertEquals("$vat", $this->getText("xpath=//tr[@id='example0136.ie']//td[.='$vat']"));
//SELECT all DOMAINS FOR PAYMENT
    $this->click("id=cb_thisJqGrid");
//SELECT FOR OFFLINE PAYMENT AND CONFIRM ACTION
    $this->click("id=gridaction_off");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
//set variables
$totalPrice=number_format($price*15,2);
$totalVat=number_format($vat*15,2);
$totalPriceVat=number_format($priceVat*15,2);

    try {
        $this->assertEquals("€ $totalPrice", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ $totalVat" , $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[6]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ $totalPriceVat" , $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[7]"));
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

$arr = array('example0128.ie', 'example0136.ie', 'example0129.ie', 'example0130.ie','example0137.ie','example0215.ie','example0216.ie','example0217.ie','example0218.ie','example0219.ie','example0225.ie','example0226.ie','example0227.ie','example0229.ie','example0230.ie');

foreach($arr as $dom)
               {
$arr=getPayOfflineStatus("$dom", "RRen1Yr", "vat", "15");
include("testSuite/payOfflineCheck.php");
}

}

}



?>
