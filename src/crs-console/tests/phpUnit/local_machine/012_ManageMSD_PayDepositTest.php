<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class ManageMSDDeposit extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group MSDReActivateSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testMSDReActivateDeposit()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
//LOAD MSD, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Mails, Suspensions & Deletions");
$this->select("xpath=//td[@id='thisJqGridPager_center']//select[.='51015202550100']", "50");
$this->click("id=jqg_thisJqGrid_example0809.ie");
$this->click("id=jqg_thisJqGrid_example0810.ie");
    $this->click("id=gridactionpay_paydeposit");


//VERIFY ACTION CANT BE COMPLETED because not enough funds exist
//$this->assertTextPresent("You do not have enough founds in your account to complete this action.");

//TOP UP DEPOSIT ACCOUNT
    $this->click("link=Top Up Deposit Account");
    $this->waitForPageToLoad("60000");
    $this->type("id=AccountTopUpModel_holder", "john doe");
    $this->type("id=AccountTopUpModel_creditcardno", "4263971921001307");
    $this->type("id=AccountTopUpModel_exp_month", "12");
    $this->type("id=AccountTopUpModel_exp_year", "12");
    $this->type("id=AccountTopUpModel_euros_amount", "460.00");
    $this->type("id=AccountTopUpModel_cvn", "123");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//html/body");
    try {
        $this->assertTrue($this->isTextPresent("Transaction Successful"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }

//LOAD MSD, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Mails, Suspensions & Deletions");
$this->select("xpath=//td[@id='thisJqGridPager_center']//select[.='51015202550100']", "50");
$this->click("id=jqg_thisJqGrid_example0355.ie");
$this->click("id=jqg_thisJqGrid_example0254.ie");
$this->click("id=jqg_thisJqGrid_example0255.ie");
$this->click("id=jqg_thisJqGrid_example0256.ie");
$this->click("id=jqg_thisJqGrid_example0350.ie");
$this->click("id=jqg_thisJqGrid_example0351.ie");
$this->click("id=jqg_thisJqGrid_example0352.ie");
$this->click("id=jqg_thisJqGrid_example0353.ie");
$this->click("id=jqg_thisJqGrid_example0354.ie");
$this->click("id=jqg_thisJqGrid_example0356.ie");
$this->click("id=jqg_thisJqGrid_example0250.ie");
$this->click("id=jqg_thisJqGrid_example0453.ie");
$this->click("id=jqg_thisJqGrid_example0880.ie");
$this->click("id=jqg_thisJqGrid_example0881.ie");
$this->click("id=jqg_thisJqGrid_example0882.ie");
$this->click("id=jqg_thisJqGrid_example0883.ie");
$this->click("id=jqg_thisJqGrid_example0884.ie");
$this->click("id=jqg_thisJqGrid_example0885.ie");
$this->click("id=jqg_thisJqGrid_example0886.ie");
$this->click("id=jqg_thisJqGrid_example0904.ie");
$this->click("id=jqg_thisJqGrid_example0905.ie");
$this->click("id=jqg_thisJqGrid_example0977.ie");
$this->click("id=jqg_thisJqGrid_example0251.ie");
$this->click("id=jqg_thisJqGrid_example0252.ie");
$this->click("id=jqg_thisJqGrid_example0253.ie");
$this->click("id=jqg_thisJqGrid_example0809.ie");
$this->click("id=jqg_thisJqGrid_example0810.ie");
    $this->click("id=gridactionpay_paydeposit");


    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->assertEquals("€ 378.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[29]/td[3]"));
    $this->assertEquals("€ 79.38", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[29]/td[4]"));
    $this->assertEquals("€ 457.38", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[29]/td[5]"));
//LOGOUT
    logout($this);
}
/**
     * @depends testMSDReActivateDeposit
     * @group MSDReActivateSuccessDbTest
     */

        public function testMSDReActivedbTest()
{

{$arr = array('example0355.ie', 'example0254.ie', 'example0255.ie', 'example0256.ie', 'example0350.ie', 'example0351.ie', 'example0352.ie', 'example0353.ie', 'example0354.ie', 'example0356.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "27", "INV880992");
include("testSuite/paymentCheck.php");
}
}

{
$arr = array('example0250.ie', 'example0453.ie', 'example0880.ie', 'example0881.ie', 'example0882.ie', 'example0883.ie', 'example0884.ie', 'example0885.ie', 'example0886.ie', 'example0904.ie', 'example0905.ie', 'example0977.ie', 'example0251.ie', 'example0252.ie', 'example0253.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "27", "Advance");
include("testSuite/paymentCheck.php");
}
}
{
$arr = array('example0810.ie', 'example0809.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "27", "INV990972");
include("testSuite/paymentCheck.php");
}
}


}

}



?>
