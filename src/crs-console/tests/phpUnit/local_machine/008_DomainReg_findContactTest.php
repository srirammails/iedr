<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once 'testSuite/functions.php';
include_once 'testSuite/db.inc';


class DomainRegFind extends PHPUnit_Extensions_SeleniumTestCase
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
     * @group DomainReg
     */

  public function testDomainRegFind()
  {
    
    $this->open("/index.php");
    login($this);
    $this->click("link=Register New");
    $this->waitForPageToLoad("60000");
    $this->type("id=Domains_RegNew_domain_names", "new-example0002.ie");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->type("id=Domains_Details_holder", "'Holder Name' goes here %");
    $this->type("id=Domains_Details_remarks", "CRO NUMber: 12345");
    $this->select("id=Domains_Details_registration_period", "5");
    $this->click("id=nicWidgetFnd_admin_contact_nic_1");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//tr[@id='ANC356-IEDR']//td[.='ANC356-IEDR']");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    $this->type("id=Domains_Details_nameserver_name_1", "ns1.new-example0002.ie");
    $this->type("id=Domains_Details_nameserver_addr_1", "10.10.128.130");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("I accept the terms and conditions. must be on"));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }
    $this->check("id=Domains_Details_accept_tnc");
    $this->click("name=yt0");
    $this->waitForPageToLoad("60000");
    try {
        $this->assertTrue($this->isTextPresent("Your ticket for new-example0002.ie has been received and will be processed by our hostmasters in due course."));
    } catch (PHPUnit_Framework_AssertionFailedError $e) {
        array_push($this->verificationErrors, $e->toString());
    }


}

/**
     * @depends testDomainReg1
     * @group PaymentSuccessDbTest
     */

        public function testDomainReg1dbTest()
{

$arr = array('new-example0002.ie');
foreach($arr as $dom)
$arr=getTicketStatus("$dom", "5");

$dom=$arr["D_Name"];
$type=$arr["T_Type"];
$owner=$arr["Bill_NH"];
$remark=$arr["T_Remark"];
$holder=$arr["D_Holder"];
$class=$arr["T_Class"];
$cat=$arr["T_Category"];
$ren=$arr["T_Ren_Dt"];
$admin=$arr["Admin_NH1"];
$tech=$arr["Tech_NH"];
$ns1=$arr["DNS_Name1"];
$ns2=$arr["DNS_Name2"];
$ns3=$arr["DNS_Name3"];
$ip1=$arr["DNS_IP1"];
$ip2=$arr["DNS_IP2"];
$ip3=$arr["DNS_IP3"];
$checkout=$arr["CheckedOut"];
$expren=$arr["Ren"];

               $this->assertEquals($dom, 'new-example0002.ie');
               $this->assertEquals($type, 'R');
               $this->assertEquals($owner, 'ABC1-IEDR');
               $this->assertEquals($holder, "'Holder Name' goes here %");
               $this->assertEquals($remark, 'CRO NUMber: 12345');
               $this->assertEquals($class, 'Body Corporate (Ltd,PLC,Company)');
               $this->assertEquals($cat, 'Corporate Name');
               $this->assertEquals($ren, $expren);
               $this->assertEquals($admin, 'ANC356-IEDR');
               $this->assertEquals($tech, 'ABC1-IEDR');
               $this->assertEquals($ns1, 'ns1.new-example0002.ie');
               $this->assertEquals($ns2, 'ns1.abc2default.ie');
               $this->assertEquals($ip1, '10.10.128.130');
               $this->assertEquals($ip2, NULL);
               $this->assertEquals($ip3, NULL);
               $this->assertEquals($checkout, 'N');

}

  }
?>
