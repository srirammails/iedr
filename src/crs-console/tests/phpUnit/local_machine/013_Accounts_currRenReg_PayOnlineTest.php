<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayOnlineCurrRenReg extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testPayCurrRenRegOnline()
  {
    //test: pay for example0022.ie online, select 0024 also, but in payment confirmation deselect it and ensure only one domain is charged. assert that the correct rates are charged and that a receipt is created corrected, domain unlocked etc. 
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
            if ($this->isTextPresent("example0032.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THE DETAILS IN THE INVOICE SUMMARY GRID
$this->assertEquals("INV990992", $this->getText("xpath=//table[@class='gridtable']//td[.='INV990992']"));
    $this->assertEquals("168", $this->getText("xpath=//table[@class='gridtable']//td[.='168']"));
    $this->assertEquals("€ 2,352.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[3]"));
    $this->assertEquals("€ 493.92", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    $this->assertEquals("€ 2845.92", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    $this->assertEquals("2011-11-30", $this->getText("xpath=//table[@class='gridtable']//td[.='2011-11-30']"));
//VERIFY THAT THE RATE SHOWN FOR THE TEST DOMAINS IS CORRECT
    $this->assertEquals("14", $this->getText("xpath=//tr[@id='example0022.ie']//td[.='14']"));
    $this->assertEquals("2.94", $this->getText("xpath=//tr[@id='example0022.ie']//td[.='2.94']"));
//SELECT TWO DOMAINS FOR PAYMENT
    $this->click("id=jqg_thisJqGrid_example0022.ie");
    $this->click("id=jqg_thisJqGrid_example0024.ie");
//SELECT FOR ONLINE PAYMENT AND ENTER CREDIT CARD DETAILS
    $this->click("id=gridaction_on");
    $this->waitForPageToLoad("60000");
    $this->type("id=GridSelectionModel_holder", "ciara conlon");
    $this->type("id=GridSelectionModel_creditcardno", "4263971921001307");
    $this->type("id=GridSelectionModel_exp_month", "12");
    $this->type("id=GridSelectionModel_exp_year", "12");
    $this->type("id=GridSelectionModel_cvn", "123");
//UNSELECT EXAMPLE0024.IE
    $this->click("id=GridSelectionModel_domlist_example0024_confirmed");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
//VERIFY THAT PAYMENT CONFIRMATION SHOWS THE CORRECT RATE
$this->waitForPageToLoad("60000");
    try {
        $this->assertEquals("€ " . $GLOBALS [arr_results] [RRen1Yr], $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " . $GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate], $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " .($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])), $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[6]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " . $GLOBALS [arr_results] [RRen1Yr], $this->getText("xpath=//table[@class='gridtable']/tbody/tr[3]/td[3]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("€ " . $GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate], $this->getText("xpath=//table[@class='gridtable']/tbody/tr[3]/td[4]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
      $this->assertEquals("€ " . ($GLOBALS [arr_results] [RRen1Yr] + ($GLOBALS [arr_results] [RRen1Yr] * $GLOBALS [vat_rate])), $this->getText("xpath=//table[@class='gridtable']/tbody/tr[3]/td[5]"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }

//LOGOUT    
    logout($this); 

$arr = getPaymentStatus("example0022.ie", "RRen1Yr", "vat", "1", "INV990992");
include("testSuite/paymentCheck.php");

}

}



?>
