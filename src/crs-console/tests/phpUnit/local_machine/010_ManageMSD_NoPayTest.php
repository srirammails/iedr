<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class ManageMSDNoPay extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testMSDReActivate()
  {
//LOGIN
    $this->open("/index.php");
    login($this);
$this->setSpeed(1500);
//LOAD MSD grid, VERIFY CORRECT NUMBER OF RESULTS, SEARCH AND SELECT DOMAINS TO TEST
$this->click("link=Mails, Suspensions & Deletions");
$this->assertEquals("View 1 - 15 of 46", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 46']"));
$this->select("xpath=//td[@id='thisJqGridPager_center']//select[.='51015202550100']", "50");
    $this->select("id=gs_C", "Reactivate W/O Pay");
    $this->click("id=content");
    $this->click("id=jqg_thisJqGrid_example0161.ie");
    $this->click("id=jqg_thisJqGrid_example0968.ie");
    $this->click("id=jqg_thisJqGrid_example0969.ie");
    $this->click("id=jqg_thisJqGrid_example0970.ie");
    $this->click("id=jqg_thisJqGrid_example0971.ie");
    $this->click("id=jqg_thisJqGrid_example0973.ie");
    $this->click("id=jqg_thisJqGrid_example0974.ie");
    $this->click("id=jqg_thisJqGrid_example0975.ie");
    $this->click("id=jqg_thisJqGrid_example0976.ie");
    $this->click("id=gridactionnopay_reactivate");
    $this->waitForPageToLoad("60000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
 

//LOGOUT
    logout($this);
}
/**
     * @depends testMSDReActivate
     * @group MSDReActivateSuccessDbTest
     */

        public function testMSDReActivedbTest()
{
$arr = array('example0161.ie', 'example0968.ie', 'example0969.ie', 'example0970.ie', 'example0971.ie', 'example0973.ie', 'example0974.ie', 'example0975.ie', 'example0976.ie');

foreach($arr as $dom)
            {
$arr=getMSDReActiveStatus("$dom");

include("testSuite/msdReActiveCheck.php");
} 


}

}



?>
