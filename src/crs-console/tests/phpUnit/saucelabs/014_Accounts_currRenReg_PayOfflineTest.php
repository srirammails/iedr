<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayOffline extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testPayCurrRenRegOffline()
  {
//set prices
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);    
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);
//LOGIN
    $this->open("/index.php");
    login($this);
   $this->waitForPageToLoad("60000");
//GO TO CURRENT R&R INVOICE 
    $this->click("link=Renewals and New Registrations");
    $this->waitForPageToLoad("60000");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("example0031.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THAT THE RATE SHOWN FOR THE TEST DOMAINS IS CORRECT
    $this->assertEquals('$price', $this->getText("xpath=//tr[@id='example0031.ie']//td[.='$price']"));
    $this->assertEquals('$priceVat', $this->getText("xpath=//tr[@id='example0031.ie']//td[.='$priceVat']"));
//SELECT TWO DOMAINS FOR PAYMENT
    $this->click("id=jqg_thisJqGrid_example0031.ie");
    $this->click("id=jqg_thisJqGrid_example0057.ie");
    $this->click("id=jqg_thisJqGrid_example0088.ie");
    $this->click("id=jqg_thisJqGrid_example0095.ie");
//SELECT FOR OFFLINE PAYMENT AND CONFIRM ACTION
    $this->click("id=gridaction_off");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
$this->waitForPageToLoad("60000");

$totalPrice=number_format($price*4,2);
$totalVat=number_format($vat*4,2);
$totalPriceVat=number_format($priceVat*4,2);
try {
        $this->assertEquals("€ " . $totalPrice, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " . $totalVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[6]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " .$totalPriceVat, $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[7]"));
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

$arr = array('example0057.ie', 'example0031.ie', 'example0088.ie', 'example0095.ie');

foreach($arr as $dom)
               {
$arr=getPayOfflineStatus("$dom", "RRen1Yr", "vat", "4");
include("testSuite/payOfflineCheck.php");
}
 


}

}



?>
