<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase/SauceOnDemandTestCase.php';
include_once 'login.php';
include_once 'functions.php';

class CheckStatsTest extends PHPUnit_Extensions_SeleniumTestCase_SauceOnDemandTestCase {
    public static $browsers = array(
      array(
        'name' => 'Testing NRC CheckStats',
        'browser' => 'firefox',
        'os' => 'Windows 2003',
        'browserVersion' => '7',
      ),
      array(
        'name' => 'Testing NRC CheckStats',
        'browser' => 'iexplore',
        'os' => 'Windows 2008',
        'browserVersion' => '9',
      ),
      array(
        'name' => 'Testing NRC CheckStats',
        'browser' => 'firefox',
        'os' => 'Linux',
        'browserVersion' => '8',
      ),
	array(
        'name' => 'Testing NRC CheckStats',
        'browser' => 'googlechrome',
        'os' => 'Linux',
        'browserVersion' => '',
      ),
 	array(
        'name' => 'Testing NRC CheckStats',
        'browser' => 'googlechrome',
        'os' => 'Windows 2008',
        'browserVersion' => '',
      )
    );

    function setUp() {
        $this->setBrowserUrl('http://newregcon.iedr.ie');
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

$newreg = getNewRegCount();

    $this->assertEquals("$newreg", $this->getText("stats_NewRegs"));
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
