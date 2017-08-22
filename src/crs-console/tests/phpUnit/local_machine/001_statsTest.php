<?php

require_once 'PHPUnit/Autoload.php';
require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
include_once 'login.php';
include_once '/usr/bin/pear/share/pear/PHPUnit/Framework/ExpectationFailedException.php';
include_once 'testSuite/functions.php';

class CheckStats extends PHPUnit_Extensions_SeleniumTestCase
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
     * @group StatisDisplayTest
     */

  public function testStats()
  {
    $this->open("/index.php");
login($this);
$this->setSpeed(1500);
$this->click("link=All Domains");
     $this->waitForPageToLoad("6000000");
$this->assertEquals("0", $this->getText("id=stats_PassedTickets"));
	$this->assertEquals("0", $this->getText("stats_TicketCount"));
    $this->assertEquals("123", $this->getText("stats_NewRegs"));
    $this->assertEquals("4", $this->getText("stats_TransfersOut"));
    $this->assertEquals("1,001", $this->getText("stats_DomainCount"));
    $this->assertEquals("0", $this->getText("stats_PendingTickets"));
    $this->assertEquals("34", $this->getText("stats_TransfersIn"));
    $this->assertEquals("1,545.00", $this->getText("stats_StatementBalance"));
    $this->assertEquals("1.00", $this->getText("stats_DepositBalance"));
logout($this);
  }
}
?>
