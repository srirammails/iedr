<?php

require_once './SetupPhpUnit.php';

class TestTopUpMyDepositAccount extends SetupPhpUnit {
	public static function tearDownAfterClass() {
		TestUtility::query("UPDATE Deposit set Close_Bal=2114.47 WHERE Nic_Handle='IDL2-IEDR'");	
	}
	
	public function testTopUp() {
		$this->login();
		$this->open("/index.php?r=account_topup_my_deposit_account/menu");
		$this->click("link=Top up");
		$this->waitForPageToLoad("30000");
		$this->selectWindow("null");
		$this->type("id=AccountTopUpModel_cardholder", "John Doe");
		$this->type("id=AccountTopUpModel_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=AccountTopUpModel_exp_month", "12");
		$this->type("id=AccountTopUpModel_exp_year", "12");
		$this->type("id=AccountTopUpModel_euros_amount", "1000");
		$this->type("id=AccountTopUpModel_cvn", "123");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
		
		TestUtility::textPresent("Transaction Successful");
		$this->logout();
	}
}

?>