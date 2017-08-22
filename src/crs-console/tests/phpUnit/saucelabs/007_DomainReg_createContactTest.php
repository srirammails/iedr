<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class DomainRegCreate extends PHPUnit_Extensions_SeleniumTestCase
{

    protected $captureScreenshotOnFailure = TRUE;
    protected $screenshotPath = '/Users/ciaraconlon/Development/NRC/screenshots';
    protected $screenshotUrl = '/Users/ciaraconlon/Development/NRC/screenshots';


  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }
  public function testDomainReg1()
  {
    
    $this->open("/index.php");
    login($this);
$this->setSpeed(500);
    $this->click("link=Register New");
    $this->waitForPageToLoad("30000");
    $this->type("id=Domains_RegNew_domain_names", "new-example0001.ie");
    $this->clickAndWait("name=yt0");
    $this->waitForPageToLoad("30000");
    $this->type("id=Domains_Details_holder", "Holder name here");
    $this->select("id=Domains_Details_registration_period", "label=9");
    $this->select("id=Domains_Details_applicant", "label=Registered Trade Mark Name");
    $this->type("id=Domains_Details_remarks", "remarks");
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
    $this->click("id=Domains_Details_accept_tnc");
    $this->click("name=yt0");
    $this->waitForPageToLoad("30000");
    $this->assertTextPresent("Your ticket for new-example0001.ie has been received and will be processed by our hostmasters in due course");
    logout($this); 

}

/**
     * @depends testDomainReg1
     * @group PaymentSuccessDbTest
     */

        public function testDomainReg1dbTest()
{

$arr = array('new-example0001.ie');
foreach($arr as $dom)
$arr=getTicketStatus("$dom", "9");

$dom=$arr[D_Name];
$type=$arr[T_Type];
$owner=$arr[Bill_NH];
$remark=$arr[T_Remark];
$holder=$arr[D_Holder];
$class=$arr[T_Class];
$cat=$arr[T_Category];
$ren=$arr[T_Ren_Dt];
$admin=$arr[Admin_NH1];
$tech=$arr[Tech_NH];
$ns1=$arr[DNS_Name1];
$ns2=$arr[DNS_Name2];
$ns3=$arr[DNS_Name3];
$ip1=$arr[DNS_IP1];
$ip2=$arr[DNS_IP2];
$ip3=$arr[DNS_IP3];
$checkout=$arr[CheckedOut];
$expren=$arr[Ren];

               $this->assertEquals($dom, 'new-example0001.ie');
               $this->assertEquals($type, 'R');
               $this->assertEquals($owner, 'ABC1-IEDR');
               $this->assertEquals($holder, 'Holder name here');
               $this->assertEquals($remark, 'remarks');
               $this->assertEquals($class, 'Body Corporate (Ltd,PLC,Company)');
               $this->assertEquals($cat, 'Registered Trade Mark Name');
               $this->assertEquals($ren, $expren);
 //todo: find a way to get phpunit to store the new niccreated and verify it here:              $this->assertEquals($admin, 'ABC1-IEDR');
               $this->assertEquals($tech, 'ABC1-IEDR');
               $this->assertEquals($ns1, 'ns1.abcdefault.ie');
               $this->assertEquals($ns2, 'ns1.abc2default.ie');
               $this->assertEquals($ip1, NULL);
               $this->assertEquals($ip2, NULL);
               $this->assertEquals($ip3, NULL);
               $this->assertEquals($checkout, 'N');

}

  }
?>
