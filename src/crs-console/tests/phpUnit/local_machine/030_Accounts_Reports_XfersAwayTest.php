<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';




class AccountsReportXfersAway extends PHPUnit_Extensions_SeleniumTestCase
{
    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';

  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }
/**
     * @group DomainReports
     */


  public function testAccountsReportsXfersAway()
  {
//START TEST
    $this->open("/index.php");
//LOG USER IN
    login($this);
    $this->waitForPageToLoad("60000");
  $this->click("link=Transfers Away");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertEquals("Accounts / View Reports / Domains Transferred / Transfers Away", $this->getText("id=content"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    try {
        $this->assertEquals("View 1 - 4 of 4", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 4 of 4']"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->type("id=gs_A", "black");
    $this->keyPress("id=gs_A", "\\13");
    $this->click("link=blacknight.ie");
    try {
        $this->assertFalse($this->isTextPresent("Blacknight Internet Solutions Limited"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
//LOGOUT USER  
    logout($this);
}}
?>
