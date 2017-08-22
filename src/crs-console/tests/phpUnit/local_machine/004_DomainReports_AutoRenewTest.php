<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';


class DomainReports extends PHPUnit_Extensions_SeleniumTestCase
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
     * @group PaymentDomainReports
     */
  public function testAutoRenewReports()
  {
    $this->open("/index.php");
    login($this);
$this->open("/index.php");
    $this->click("link=Autorenewed Domains");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("IEDR Registrar's Console - Autorenewed Domains", $this->getTitle());
for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("View 1 - 15 of 30")) break;
        } catch (Exception $e) {}
        sleep(1);
}
//Goto the last page of the report and click on the last domain expected to be found in the results, if the correct resultset has not loaded the test will fail here
    $this->click("xpath=//td[@id='last_thisJqGridPager']/span");

for ($second = 0; ; $second++) {
        if ($second >= 60) $this->fail("timeout");
        try {
            if ($this->isTextPresent("View 16 - 30 of 30")) break;
        } catch (Exception $e) {}
        sleep(1);
}
$this->click("link=example0795.ie");
    $this->waitForPageToLoad("30000");
    $this->assertEquals("Registrar co Limited", $this->getValue("//input[@id=\"ViewDomainModel_domain_billingContacts_companyName\"]"));

//START DB TEST 
$arr = getDomainStatus("example0795.ie");

$domain1=$arr["domain"];
$regDate=$arr["regDate"];
$renewDate=$arr["renDate"];
$status=$arr["status"];
 
    $this->assertEquals("$status",$this->getValue("//input[@id=\"ViewDomainModel_domain_domainStatus\"]"));
    $this->assertEquals("$domain1" ,$this->getValue("//input[@id=\"ViewDomainModel_domain_name\"]"));
    $this->assertEquals("$regDate",$this->getValue("//input[@id=\"ViewDomainModel_domain_registrationDate\"]"));
    $this->assertEquals("$renewDate",$this->getValue("//input[@id=\"ViewDomainModel_domain_renewDate\"]"));
  
    logout($this);
}}
?>
