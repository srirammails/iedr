<?php

require_once './SetupPhpUnit.php';

class TestInternalUsers extends SetupPhpUnit {

	public static function setUpBeforeClass() {
		TestUtility::query("UPDATE Access SET IP_Address='".IP_ADDRESS."' WHERE Nic_Handle='GEORGE-IEDR'");
	}

	public static function tearDownAfterClass() {
		TestUtility::query("UPDATE Access SET IP_Address=NULL WHERE Nic_Handle='GEORGE-IEDR'");
	}

	public function testLoginOnInternalUserAndSwitchUserSuccesful() {

		$this->open("/index.php?r=site/login");
		$this->click("link=Login");
		$this->waitForPageToLoad("30000");

		$this->open("index.php?r=site/login");
		$this->type("LoginForm_username", "IDL2-IEDR");
		$this->type("LoginForm_password", "marysia");

		$this->select("id=LoginForm_internalUser", "label=GEORGE-IEDR");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		$this->open("/index.php");
		TestUtility::textPresent("Registrar");

		//Switch user succesful
		$this->select("id=UserSwitchModel_switchUser", "label=API TESTS");
		$this->click("css=option[value=\"APITEST-IEDR\"]");
		$this->select("id=UserSwitchModel_switchUser", "label=Irish Domains");

		$this->click("link=All Domains");
		$this->waitForPageToLoad("60000");

		TestUtility::equalsContent("View 1 - 11 of 11", $this->getText("css=div.ui-paging-info"));
	}

	 
	public function testLoginOnInternalUserAndSwitchUserFailure() {
		$this->open("/index.php?r=site/login");
		$this->click("link=Login");
		$this->waitForPageToLoad("70000");

		$this->open("index.php?r=site/login");
		$this->type("LoginForm_username", "IDL2-IEDR");
		$this->type("LoginForm_password", "marysia");

		$this->select("id=LoginForm_internalUser", "label=GEORGE-IEDR");
		$this->click("name=yt0");
		$this->waitForPageToLoad("70000");

		$this->open("/index.php");
		TestUtility::textPresent("Registrar");

		//Switch user failure
		$this->select("id=UserSwitchModel_switchUser", "label=API TESTS 4");
		$this->click("name=yt0");
		$this->waitForPageToLoad("70000");
		$this->select("id=UserSwitchModel_switchUser", "label=Register.ie (Novara)");
		$this->click("name=yt0");


		$this->waitForPageToLoad("70000");
		$this->open("/index.php?r=site/login");
		$this->click("link=All Domains");
		$this->waitForPageToLoad("70000");

		TestUtility::notEqualsContent("View 1 - 11 of 11", $this->getText("css=div.ui-paging-info"));
	}
}

?>
