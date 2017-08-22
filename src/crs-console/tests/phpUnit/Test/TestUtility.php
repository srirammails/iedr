<?php

require_once 'PHPUnit/Autoload.php';
require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
require_once './Definitions.php';

class TestUtility extends PHPUnit_Framework_TestCase {
	 
	public  function logout($browser) {
		$browser->click("link=IDL2-IEDR");
	}

	public function login($browser) {
		$browser->open("index.php?r=site/login");
		$browser->type("LoginForm_username", "IDL2-IEDR");
		$browser->type("LoginForm_password", "123456");
		$browser->type("LoginForm_verifyCode", "5d2fc08a-4975-11e0-808f-080027a1156a");
		$browser->click("yt0");
		$browser->waitForPageToLoad("30000");
	}

	public function checkCountDomain($countDomain) {
		if ($this->isTextPresent("View 1 - $countDomain of $countDomain"))
		return true;
		return false;
	}

	public function searchText($content, $field = null) {
		$this->type($field, $content);
		$this->keyPress($field, "\\13");
		for ($second = 0;; $second++) {
			if ($second >= 60)
			$this->fail("timeout in " . $field);
			try {
				if ($this->isTextPresent($content))
				break;
			} catch (Exception $e) {

			}
			sleep(1);
		}
	}
	 
	public function setField($field , $content) {
		$this->type($field , $content);
	}

	public function cleanField($field) {
		$this->type($field, "");
		$this->keyPress($field, "\\13");
	}

	public function equalsCount($domainCount, $message = '') {
		$count = 0;
		if($this->isTextPresent("View 1 - $domainCount of $domainCount")) {
			$count = $domainCount;
		}
		$this->assertEquals($domainCount, $count, $message);
	}

	public function equalsContent($expectedText, $widgetContent) {
		$this->assertEquals($expectedText, $widgetContent);
	}
	 
	public function notEqualsContent($expectedText, $widgetContent) {
		$this->assertNotEquals($expectedText, $widgetContent);
	}

	public function textPresent($text) {
		$this->assertTextPresent($text);
	}
	 
	public function query($query) {
		$connection = @ mysql_connect(MYSQL_HOSTNAME, MYSQL_USER, MYSQL_PASSWORD)
		or die("Cannot connect to host " . MYSQL_HOSTNAME . " on user " . MYSQL_USER . " and password " . MYSQL_PASSWORD . "");
		mysql_select_db(MYSQL_DB, $connection);
		$result = mysql_query($query, $connection) or die('Query execute failure(' . $query . ')');
		mysql_close($connection);
	}
}

?>
