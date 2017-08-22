<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class AccountPayMSDtran extends PHPUnit_Extensions_SeleniumTestCase
{
 /**
     * @group MSDSuccess
     */

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testMSDtran()
  {
//LOGIN
$this->setSpeed(2500);
    $this->open("/index.php");
    login($this);
//GO TO CURRENT XFER INVOICE
    $this->click("link=Transfers - Pay Current Invoice");
    $this->waitForPageToLoad("60000");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("example0236.ie")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY THE DETAILS IN THE INVOICE SUMMARY GRID
    $this->assertEquals("INV990982", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[1]"));
    $this->assertEquals("8", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[2]"));
    $this->assertEquals("€ 120.00", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[3]"));
    $this->assertEquals("€ 25.20", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[4]"));
    $this->assertEquals("€ 145.20", $this->getText("xpath=//table[@class='gridtable']/tbody/tr[2]/td[5]"));
    $this->assertEquals("2011-11-30", $this->getText("xpath=//table[@class='gridtable']//td[.='2011-11-30']"));
    
//SELECT DOMAINS TO GO INTO MSD
    $this->click("xpath=//td[@id='next_thisJqGridPager']/span");
    $this->click("id=jqg_thisJqGrid_example0316.ie");
    $this->click("id=jqg_thisJqGrid_example0317.ie");
    $this->click("id=gridaction_msd");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("Your request to add the domain(s) below to MSD was successful."));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }

//LOGOUT    
    logout($this);

$arr = array( 'example0316.ie', 'example0317.ie');

foreach($arr as $dom)
               {
$arr=getMSDStatus("$dom", "MailList");

include("testSuite/msdCheck.php");
}
 


}

}



?>
