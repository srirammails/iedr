<?php

require_once './TestUtility.php';

class SetupPhpUnit extends PHPUnit_Extensions_SeleniumTestCase {

	protected function setUp() {
		$this->setBrowser("*chrome");
		$this->setBrowserUrl(LOCAL_ADDRESS);
	}

	protected function login() {
		$this->open("/index.php");
		TestUtility::login($this);
		$this->setSpeed(500);
	}

	protected function logout($login=null) {
		TestUtility::logout($this,$login);
	}
	 
	protected function startTomcat() {
		system(CATALINA_BIN.' start');
	}
	 
	protected function stopTomcat() {
		system('kill `ps h -C java -o "%p:%a" | grep catalina | cut -d: -f1`');
		sleep(10);
	}
}

?>
