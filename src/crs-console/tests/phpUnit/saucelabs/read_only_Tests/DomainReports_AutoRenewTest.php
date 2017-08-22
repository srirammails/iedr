<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase/SauceOnDemandTestCase.php';
include_once 'login.php';
include_once 'functions.php';

class DomainReportsAutoRenew1Test extends PHPUnit_Extensions_SeleniumTestCase_SauceOnDemandTestCase {
    public static $browsers = array(
      array(
        'name' => 'Testing NRC DomainReportsAutoRenew1',
        'browser' => 'firefox',
        'os' => 'Windows 2003',
        'browserVersion' => '7',
      ),
      array(
        'name' => 'Testing NRC DomainReportsAutoRenew1',
        'browser' => 'iexplore',
        'os' => 'Windows 2008',
        'browserVersion' => '9',
      ),
      array(
        'name' => 'Testing NRC DomainReportsAutoRenew1',
        'browser' => 'firefox',
        'os' => 'Linux',
        'browserVersion' => '8',
      ),
        array(
        'name' => 'Testing NRC DomainReportsAutoRenew1',
        'browser' => 'googlechrome',
        'os' => 'Linux',
        'browserVersion' => '',
      ),
        array(
        'name' => 'Testing NRC DomainReportsAutoRenew1',
        'browser' => 'googlechrome',
        'os' => 'Windows 2008',
        'browserVersion' => '',
      )
    );
    function setUp() {
        $this->setBrowserUrl('http://newregcon.iedr.ie');
    }

/**
     * @group tDomainReports
     */
  public function testAutoRenewReports()
  {
    $this->open("/index.php");
    login($this);
$this->open("/index.php");
    $this->click("link=Autorenewed Domains");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("IEDR Registrar's Console - Autorenewed Domains", $this->getTitle());
    $this->assertEquals("View 1 - 15 of 30", $this->getText("css=div.ui-paging-info"));
//Goto the last page of the report and click on the last domain expected to be found in the results, if the correct resultset has not loaded the test will fail here
    $this->click("xpath=//td[@id='last_thisJqGridPager']/span");

for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ("View 16 - 30 of 30" == $this->getTable("css=table.ui-pg-table.0.2")) break;
        } catch (Exception $e) {}
        sleep(1);
    }

$this->click("link=example0795.ie");
    $this->waitForPageToLoad("30000");

//START DB TEST 
$arr = getDomainStatus("example0795.ie");

$domain1=$arr[domain];
$regDate=$arr[regDate];
$renewDate=$arr[renDate];
$status=$arr[status];
 
    $this->assertEquals("$status",$this->getValue("//input[@id=\"ViewDomainModel_domain_domainStatus\"]"));
    $this->assertEquals("$domain1" ,$this->getValue("//input[@id=\"ViewDomainModel_domain_name\"]"));
    $this->assertEquals("$regDate",$this->getValue("//input[@id=\"ViewDomainModel_domain_registrationDate\"]"));
    $this->assertEquals("$renewDate",$this->getValue("//input[@id=\"ViewDomainModel_domain_renewDate\"]"));
  
    logout($this);
}}
?>
