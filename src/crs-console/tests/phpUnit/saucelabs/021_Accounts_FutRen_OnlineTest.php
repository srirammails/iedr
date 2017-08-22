<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayFutRenOnline extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testDefaultMonthOnline()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
$this->click("link=Future Renewals");
    $this->waitForPageToLoad("60000");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("example0082.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
    $this->assertTrue($this->isTextPresent("example0082.ie"));
    $this->assertEquals("View 1 - 15 of 174", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 174']"));
    $this->click("id=jqg_thisJqGrid_example0004.ie");
    $this->click("id=jqg_thisJqGrid_example0007.ie");
    $this->click("id=jqg_thisJqGrid_example0011.ie");
    $this->click("id=jqg_thisJqGrid_example0021.ie");
    $this->click("id=jqg_thisJqGrid_example0082.ie");
    $this->click("xpath=//td[@id='next_thisJqGridPager']/span");
    $this->click("id=jqg_thisJqGrid_example0085.ie");
    $this->click("id=jqg_thisJqGrid_example0104.ie");
    $this->click("id=jqg_thisJqGrid_example0107.ie");
    $this->click("xpath=//td[@id='last_thisJqGridPager']/span");
    $this->click("id=jqg_thisJqGrid_example0913.ie");
    $this->click("id=jqg_thisJqGrid_example0912.ie");
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
$price=number_format($GLOBALS [arr_results] [RRen1Yr],0);
$priceVat=number_format(($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])),2);
$vat=number_format($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate],2);
$totalPrice=number_format($price*10,2);
$totalVat=number_format($vat*10,2);
$totalPriceVat=number_format($priceVat*10,2);

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
$arr = array('example0004.ie', 'example0007.ie', 'example0011.ie', 'example0021.ie', 'example0082.ie', 'example0085.ie', 'example0104.ie', 'example0107.ie', 'example0913.ie', 'example0912.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "10", "Advance");
include("testSuite/paymentCheck.php");
}
 


}

}



?>
