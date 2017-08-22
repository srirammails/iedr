<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase/SauceOnDemandTestCase.php';
include_once 'login.php';
include_once 'functions.php';

class DomainReportsNewRegTest extends PHPUnit_Extensions_SeleniumTestCase_SauceOnDemandTestCase {
    public static $browsers = array(
      array(
        'name' => 'Testing NRC DomainReportsNewReg',
        'browser' => 'firefox',
        'os' => 'Windows 2003',
        'browserVersion' => '7',
      ),
      array(
        'name' => 'Testing NRC DomainReportsNewReg',
        'browser' => 'iexplore',
        'os' => 'Windows 2008',
        'browserVersion' => '9',
      ),
      array(
        'name' => 'Testing NRC DomainReportsNewReg',
        'browser' => 'firefox',
        'os' => 'Linux',
        'browserVersion' => '8',
      ),
        array(
        'name' => 'Testing NRC DomainReportsNewReg',
        'browser' => 'googlechrome',
        'os' => 'Linux',
        'browserVersion' => '',
      ),
        array(
        'name' => 'Testing NRC DomainReportsNewReg',
        'browser' => 'googlechrome',
        'os' => 'Windows 2008',
        'browserVersion' => '',
      )
    );
    function setUp() {
        $this->setBrowserUrl('http://newregcon.iedr.ie');
    }

/**
     * @group PaymentDomainReports
     */
  public function testNewRegReports()
  {
    $this->open("/index.php");
    login($this);
$this->open("/index.php");
    $this->click("link=Newly Registered Domains");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("IEDR Registrar's Console - Newly Registered Domains", $this->getTitle());
    $this->assertEquals("Newly Registered Domains", $this->getText("css=div#content > h2 > a:nth-of-type(3)"));
    $this->assertEquals("View 1 - 15 of 242", $this->getText("xpath=//td[@id='thisJqGridPager_right']//div[.='View 1 - 15 of 242']"));
    $this->assertEquals("View 1 - 15 of 242", $this->getText("css=div.ui-paging-info"));
//Goto the last page of the report and click on the last domain expected to be found in the results, if the correct resultset has not loaded the test will fail here
    $this->click("xpath=//td[@id='last_thisJqGridPager']/span");
    $this->assertEquals("View 241 - 242 of 242", $this->getText("css=div.ui-paging-info"));
    $this->click("link=example0999.ie");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("Registrar co Limited", $this->getValue("//input[@id=\"ViewDomainModel_domain_billingContacts_companyName\"]"));

//START DB TEST 
$arr = getDomainStatus("example0999.ie");

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
