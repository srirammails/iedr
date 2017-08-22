<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';

class Example extends PHPUnit_Extensions_SeleniumTestCase
{
  protected function setUp()
  {
    $this->setBrowser("{"url":"http://newregcon.iedr.ie:8088/","username":"ASD-admin","access-key":"230d44b9-ef17-4572-a097-a2ba7d8e08ba","os":"Windows 2003","browser":"firefox","browser-version":"3."}");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testMyTestCase()
  {
    $this->open("/index.php?r=domainreports/alldomains");
    $this->waitForPageToLoad("60000");
    $this->click("xpath=//td[@id='refresh_thisJqGrid']/div/span");
  }
}
?>