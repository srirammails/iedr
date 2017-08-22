<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'functions.php';


//test: This test opens the Domains >All Domains report, verifies that the correct number of records is contained within the report and searches the grid for a simple domain, verifing that the grid search works. Click on the domain it serached for and verifies that the domain info being shown is correct. 


class MyTest extends PHPUnit_Extensions_SeleniumTestCase
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


  public function testAllDomains()
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
    for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ("View 1 - 15 of 1,001" == $this->getTable("css=table.ui-pg-table.0.2")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//VERIFY CORRECT NUMBER OF RECORDS
    $this->assertEquals("View 1 - 15 of 1,001", $this->getText("css=div.ui-paging-info"));
//USE THE GRID SEARCH TO FIND ONE DOMAIN NAME
   $this->type("id=gs_A", "example0100.ie");
    $this->keyPress("id=gs_A", "\\13");
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("Test Holder 0100")) break;
        } catch (Exception $e) {}
        sleep(1);
    }
//CLICK ON THE DOMAIN TO GO INTO DOMAIN VIEW AND VERIFY THAT THE CORRECT DOMAIN DETAILS ARE SHOWN
$this->click("link=example0100.ie");
    $this->waitForPageToLoad("60000");
    $this->assertEquals("Registrar co Limited", $this->getValue("//input[@id=\"ViewDomainModel_domain_billingContacts_companyName\"]"));

//RETRIEVE EXPECTED DATA FROM DATABASE TO COMPARE
$arr = getDomainStatus("example0100.ie");

$domain1=$arr[domain];
$regDate=$arr[regDate];
$renewDate=$arr[renDate];
$status=$arr[status];
 
    $this->assertEquals("$status",$this->getValue("//input[@id=\"ViewDomainModel_domain_domainStatus\"]"));
    $this->assertEquals("$domain1" ,$this->getValue("//input[@id=\"ViewDomainModel_domain_name\"]"));
    $this->assertEquals("$regDate",$this->getValue("//input[@id=\"ViewDomainModel_domain_registrationDate\"]"));
    $this->assertEquals("$renewDate",$this->getValue("//input[@id=\"ViewDomainModel_domain_renewDate\"]"));
//LOGOUT USER  
    logout($this);
}}
?>
