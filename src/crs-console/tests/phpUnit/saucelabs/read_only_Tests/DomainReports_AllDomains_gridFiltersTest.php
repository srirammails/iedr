<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase/SauceOnDemandTestCase.php';
include_once 'login.php';
include_once 'functions.php';

class DomainReportsAllTest extends PHPUnit_Extensions_SeleniumTestCase_SauceOnDemandTestCase {
    public static $browsers = array(
      array(
        'name' => 'Testing NRC DomainReportsAll',
        'browser' => 'firefox',
        'os' => 'Windows 2003',
        'browserVersion' => '7',
      ),
      array(
        'name' => 'Testing NRC DomainReportsAll',
        'browser' => 'iexplore',
        'os' => 'Windows 2008',
        'browserVersion' => '9',
      ),
      array(
        'name' => 'Testing NRC DomainReportsAll',
        'browser' => 'firefox',
        'os' => 'Linux',
        'browserVersion' => '8',
      ),
        array(
        'name' => 'Testing NRC DomainReportsAll',
        'browser' => 'googlechrome',
        'os' => 'Linux',
        'browserVersion' => '',
      ),
        array(
        'name' => 'Testing NRC DomainReportsAll',
        'browser' => 'googlechrome',
        'os' => 'Windows 2008',
        'browserVersion' => '',
      )
    );


    function setUp() {
        $this->setBrowserUrl('http://newregcon.iedr.ie');
    }

/**
     * @group DomainReports
     */

  public function testAllDomainsGridFilters()
  {
//START TEST
    $this->open("/index.php");
//LOG USER IN
    login($this);
//LOAD REPORT
    $this->open("/index.php");
    $this->click("link=All Domains");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("IEDR Registrar's Console - All Domains", $this->getTitle());
    $this->assertEquals("All Domains", $this->getText("//div[@id='content']/h2/a[3]"));
//USE THE GRID FILTER TO SEARCH FOR A KNOWN HOLDER
$this->type("id=gs_B", "Test Holder 0872");
    $this->keyPress("id=gs_B", "\\13");
    $this->keyPress("id=gs_B", "\\13");

for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("Test Holder 0872")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->assertTextPresent("Test Holder 0872");
//RELOAD THE GRID
$this->click("xpath=//td[@id='refresh_thisJqGrid']/div/span");
//USE THE GRID FILTER TO SEARCH FOR A CATEGORY and CLASS
$this->type("id=gs_C", "Sole Trader");
    $this->keyPress("id=gs_C", "\\13");
    $this->keyPress("id=gs_C", "\\13");
    
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("15 of 160")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->assertTextPresent("View 1 - 15 of 160");


$this->type("id=gs_D", "Registered Business Name");
    $this->keyPress("id=gs_D", "\\13");
    $this->keyPress("id=gs_D", "\\13");

for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("15 of 100")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->assertTextPresent("View 1 - 15 of 100");

//RELOAD THE GRID
$this->click("xpath=//td[@id='refresh_thisJqGrid']/div/span");
//USE THE GRID FILTER TO SHOW DOMAINS FROM A SPECIFIC DATE PERIOD

$today = date('Y-m-d');
$yest = date("Y-m-d", strtotime("yesterday"));

$this->type("id=gs_F_to", "$today");
    $this->keyPress("id=gs_F_to", "\\13");
    $this->keyPress("id=gs_F_to", "\\13");
    $this->type("id=gs_F_from", "$yest");
    $this->keyPress("id=gs_F_from", "\\13");
    $this->keyPress("id=gs_F_from", "\\13");

for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("15 of 174")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->assertTextPresent("View 1 - 15 of 174");


//LOGOUT USER  
    logout($this);
}
    }
?>
