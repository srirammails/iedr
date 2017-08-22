<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';


//test: this test verifies that the grid filters (holder/class/category/status/date etc) are working

class DomainReportsAutoRenewGrid extends PHPUnit_Extensions_SeleniumTestCase
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

  public function testAutoRenewDomainsGridFilters()
  {
//START TEST
    $this->open("/index.php");
//LOG USER IN
    login($this);
//LOAD REPORT
    $this->open("/index.php");
    $this->click("link=Autorenewed Domains");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("IEDR Registrar's Console - Autorenewed Domains", $this->getTitle());
    $this->assertEquals("Autorenewed Domains", $this->getText("//div[@id='content']/h2/a[3]"));
//USE THE GRID FILTER TO SEARCH FOR A KNOWN HOLDER
$this->type("id=gs_B", "Test Holder 0788");
    $this->keyPress("id=gs_B", "\\13");
    $this->keyPress("id=gs_B", "\\13");
    $this->keyPress("id=gs_B", "\\13");
    $this->keyPress("id=gs_B", "\\13");
    $this->keyPress("id=gs_B", "\\13");
    $this->keyPress("id=gs_B", "\\13");

for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("Test Holder 0788")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->assertTextPresent("Test Holder 0788");
//RELOAD THE GRID
$this->click("xpath=//td[@id='refresh_thisJqGrid']/div/span");
//USE THE GRID FILTER TO SEARCH FOR A CATEGORY and CLASS
$this->type("id=gs_C", "Statutory Body");
    $this->keyPress("id=gs_C", "\\13");
    $this->keyPress("id=gs_C", "\\13");
    $this->keyPress("id=gs_C", "\\13");
    $this->keyPress("id=gs_C", "\\13");
    $this->keyPress("id=gs_C", "\\13");
    $this->keyPress("id=gs_C", "\\13");
    
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("2 of 2")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->assertTextPresent("View 1 - 2 of 2");

//RELOAD THE GRID
$this->click("xpath=//td[@id='refresh_thisJqGrid']/div/span");

$this->type("id=gs_D", "Registered");
    $this->keyPress("id=gs_D", "\\13");
    $this->keyPress("id=gs_D", "\\13");
    $this->keyPress("id=gs_D", "\\13");
    $this->keyPress("id=gs_D", "\\13");
    $this->keyPress("id=gs_D", "\\13");
    $this->keyPress("id=gs_D", "\\13");
    $this->keyPress("id=gs_D", "\\13");

for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("8 of 8")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->assertTextPresent("View 1 - 8 of 8");



//LOGOUT USER  
    logout($this);
}
    }
?>
