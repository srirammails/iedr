<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'functions.php';
include_once 'db.inc';


class Login extends PHPUnit_Extensions_SeleniumTestCase
{

  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }
  public function testDomainReg1()
  {
    
    $this->open("/index.php");
    login($this);
    $this->click("link=Register New");
    $this->waitForPageToLoad("30000");
    $this->type("id=Domains_RegNew_domain_names", "new-example0001.ie new-example0002.ie");
    $this->clickAndWait("name=yt0");
    $this->waitForPageToLoad("30000");
    $this->type("id=Domains_Details_holder", "Holder name here");
    $this->select("id=Domains_Details_registration_period", "label=9");
    $this->select("id=Domains_Details_applicant", "label=Registered Trade Mark Name");
    $this->type("id=Domains_Details_remarks", "remarks go here");
    $this->click("id=nicWidgetNew_admin_contact_nic_1");
    $this->waitForPageToLoad("30000");
    $this->type("id=Nichandle_Details_name", "Testy McTester");
    $this->type("id=Nichandle_Details_email", "tester@example.com");
    $this->type("id=Nichandle_Details_phones", "09709809809");
    $this->type("id=Nichandle_Details_faxes", "098098098098");
    $this->type("id=Nichandle_Details_companyName", "TestCo");
    $this->type("id=Nichandle_Details_address", "lkjasd askljd");
    $this->select("id=Nichandle_Details_country", "label=Ireland");
    $this->select("id=Nichandle_Details_county", "label=Co. Wicklow");
    $this->click("name=yt0");
    $this->waitForPageToLoad("30000");
    $this->click("name=yt0");
    $this->waitForPageToLoad("30000");
    $this->click("id=Domains_Details_accept_tnc");
    $this->click("name=yt0");
    $this->waitForPageToLoad("30000");
    $this->assertTextPresent("OK");
    logout($this); 

}

/**
     * @depends testDomainReg1
     * @group PaymentSuccessDbTest
     */

        public function testDomainReg1dbTest()
{

$arr = array('new-example0001.ie', 'new-example0002.ie');
foreach($arr as $dom)
$arr=getTicketStatus("$dom", "9");
include_once ("ticketCheck.php");

}


  }
?>
