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
//LOAD TRANSFERS ADAVNCE, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Mails, Suspensions & Deletions");
$this->assertEquals("View 1 - 15 of 27", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 27']"));
$this->select("xpath=//td[@id='thisJqGridPager_center']//select[.='51015202550100']", "50");
    $this->click("id=cb_thisJqGrid");
    $this->click("id=gridactionpay_paydeposit");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->assertEquals("€ 378.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[29]/td[3]"));
    $this->assertEquals("€ 79.38", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[29]/td[3]"));
    $this->assertEquals("€ 457.38", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[29]/td[3]"));
    $this->click("xpath=//div[@id='content']/form/input");
    $this->waitForPageToLoad("60000");
$this->assertEquals("No records to view", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='No records to view']"));
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
