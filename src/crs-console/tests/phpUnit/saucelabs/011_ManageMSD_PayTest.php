<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class ManageMSDOnline extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testMSDReActivateOnline()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
//LOAD TRANSFERS ADAVNCE, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Mails, Suspensions & Deletions");
$this->assertEquals("View 1 - 15 of 37", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 37']"));
$this->click("id=jqg_thisJqGrid_example0150.ie");
    $this->click("id=jqg_thisJqGrid_example0151.ie");
    $this->click("id=jqg_thisJqGrid_example0202.ie");
    $this->click("xpath=//td[@id='next_thisJqGridPager']/span");
    $this->click("id=jqg_thisJqGrid_example0450.ie");
    $this->click("id=jqg_thisJqGrid_example0451.ie");
    $this->click("id=jqg_thisJqGrid_example0452.ie");
    $this->click("xpath=//td[@id='next_thisJqGridPager']/span");
    $this->click("id=jqg_thisJqGrid_example0978.ie");
    $this->click("id=jqg_thisJqGrid_example0979.ie");
    $this->click("id=jqg_thisJqGrid_example0983.ie");
    $this->type("id=gs_PK", "example0887");
    $this->keyPress("id=gs_PK", "\\13");
    $this->click("id=jqg_thisJqGrid_example0887.ie");
    $this->click("id=gridactionpay_payonline");
    $this->waitForPageToLoad("60000");
    $this->type("id=MSDSelectionModel_holder", "CIARA CONLON");
    $this->type("id=MSDSelectionModel_creditcardno", "4263971921001307");
    $this->keyPress("id=MSDSelectionModel_creditcardno", "\\13");
    $this->type("id=MSDSelectionModel_exp_month", "12");
    $this->type("id=MSDSelectionModel_exp_year", "12");
    $this->type("id=MSDSelectionModel_cvn", "123");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->assertEquals("€ 140.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[12]/td[3]"));
    $this->assertEquals("€ 29.40", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[12]/td[4]"));
    $this->assertEquals("€ 179.40", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[12]/td[5]"));

//LOGOUT
    logout($this);
}
/**
     * @depends testMSDReActivateOnline
     * @group MSDReActivateSuccessDbTest
     */

        public function testMSDReActivedbTest()
{

{ 
$arr = array('example0151.ie', 'example0150.ie', 'example0887.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "10", "INV990982");
include("testSuite/paymentCheck.php");
}
}

{
$arr = array('example0450.ie', 'example0451.ie', 'example0452.ie', 'example0983.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "10", "Advance");
include("testSuite/paymentCheck.php");
}
}
{
$arr = array('example0979.ie', 'example0978.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "10", "INV990992");
include("testSuite/paymentCheck.php");
}
}
{
$arr = array('example0202.ie');

foreach($arr as $dom)
               {
$arr=getPaymentStatus("$dom", "RRen1Yr", "vat", "10", "INV880992");
include("testSuite/paymentCheck.php");
}
}


}

}



?>
