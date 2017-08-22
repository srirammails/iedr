<?php

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';

class CheckStats extends PHPUnit_Extensions_SeleniumTestCase
{
  protected function setUp()
  {
    $this->setBrowser("*chrome");
    $this->setBrowserUrl("http://newregcon.iedr.ie:8080/");
  }

  public function testStats()
  {
    $this->open("/index.php");
     $this->waitForPageToLoad("30000");
    $this->assertEquals("2", $this->getText("stats_PassedTickets"));
	$this->assertEquals("5", $this->getText("stats_TicketCount"));
    $this->assertEquals("21", $this->getText("stats_NewRegs"));
    $this->assertEquals("3", $this->getText("stats_TransfersOut"));
    $this->assertEquals("100", $this->getText("stats_DomainCount"));
    $this->assertEquals("3", $this->getText("stats_PendingTickets"));
    $this->assertEquals("10", $this->getText("stats_TransfersIn"));
    $this->assertEquals("0.00", $this->getText("stats_StatementBalance"));
    $this->assertEquals("2,000.00", $this->getText("stats_DepositBalance"));
    $this->assertTrue($this->isTextPresent("exact:User: ABC1-IEDR"));
$this->setAutoStop(FALSE);
  }
}
?>
