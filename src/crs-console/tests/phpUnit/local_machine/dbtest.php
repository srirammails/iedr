<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';


class Example extends PHPUnit_Extensions_SeleniumTestCase
{
    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';

  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testAllDomains()
  {
    $this->open("/index.php");
    login($this);
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

    $this->assertEquals("View 1 - 15 of 1,001", $this->getText("css=div.ui-paging-info"));
    $this->click("link=example0006.ie");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("Registrar co Limited", $this->getValue("//input[@id=\"ViewDomainModel_domain_billingContacts_companyName\"]"));

//START DB TEST 
$arr = getDomainStatus("example0006.ie");

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
