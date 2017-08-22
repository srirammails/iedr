<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayTranAdvMSD extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group PayMSDSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testTranAdvMSD()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
//LOAD TRANSFERS ADAVNCE, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Transfers - Pay In Advance");
    $this->waitForPageToLoad("60000");
    $this->setSpeed(1500);
    $this->assertEquals("View 1 - 15 of 32", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 32']"));
    $this->click("id=jqg_thisJqGrid_example0019.ie");
    $this->click("id=jqg_thisJqGrid_example0029.ie");
    $this->type("id=gs_PK", "example0892");
    $this->keyPress("id=gs_PK", "\\13");
    $this->click("id=jqg_thisJqGrid_example0892.ie");
    $this->click("id=gridaction_msd");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
 

//LOGOUT
    logout($this);
}
/**
     * @depends testTranAdvMSD
     * @group PaymentSuccessDbTest
     */

        public function testTranAdvMSDdbTest()
{
$arr = array('example0019.ie', 'example0029.ie');

foreach($arr as $dom)
            {
$arr=getMSDStatus("$dom", "MailList");
include("testSuite/msdCheck.php");
} 


}

}



?>
