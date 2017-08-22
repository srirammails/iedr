<?php

require_once './SetupPhpUnit.php';

class TestStats extends SetupPhpUnit {

	public function testDepositBalance() {
		$this->open("/index.php");
		TestUtility::login($this);
		$this->setSpeed(500);
		$this->waitForPageToLoad("30000");
		TestUtility::equalsContent("2,114.47", $this->getText("stats_DepositBalance"));
		TestUtility::logout($this);
	}
}

?>
