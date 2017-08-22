<?php

require_once './SetupPhpUnit.php';

class TestNrp extends SetupPhpUnit {
	 
	private static function putInNrp() {
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=20 where D_Name='newregisterd1.ie'");
	}
	 
	private static function removeFromNrp() {
		TestUtility::query("DELETE FROM Ticket WHERE T_Number > 0");
		TestUtility::query("DELETE FROM Reservation WHERE ID > 0");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd1.ie'");
	}

	public function testPayonline() {
		$this->putInNrp();
		$this->login();

		$this->click("link=Current NRP Statuses");
		$this->waitForPageToLoad("30000");
		$this->click("id=jqg_thisJqGrid_newregisterd1.ie");
		$this->click("id=gridactionpay_payonline");
		$this->assertEquals("Please Confirm you want to \"Pay Online\" for these Domains:\nnewregisterd1.ie,\n", $this->getConfirmation());
		$this->waitForPageToLoad("30000");
		$this->select("id=CurrentNRPStatusesSelectionModel_list_newregisterd1.ie", "label=2 Year €48.56 Inc Vat.");
		$this->type("id=CurrentNRPStatusesSelectionModel_cardholder", "John");
		$this->click("id=CurrentNRPStatusesSelectionModel_creditcardno");
		$this->type("id=CurrentNRPStatusesSelectionModel_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=CurrentNRPStatusesSelectionModel_exp_month", "12");
		$this->type("id=CurrentNRPStatusesSelectionModel_exp_year", "12");
		$this->type("id=CurrentNRPStatusesSelectionModel_cvn", "123");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("2012-09-28");
		date_default_timezone_set('Europe/Warsaw');
		TestUtility::textPresent("".date("Y-m-d")); 
		TestUtility::textPresent("2");
		TestUtility::textPresent("€ 40.00");
		TestUtility::textPresent("€ 8.56");
		TestUtility::textPresent("€ 48.56");

		$this->logout();
		$this->removeFromNrp();
	}

	public function testPayDeposit() {
		$this->putInNrp();

		$this->login();
		$this->click("link=Current NRP Statuses");
		$this->waitForPageToLoad("30000");
		$this->click("id=jqg_thisJqGrid_newregisterd1.ie");
		$this->click("id=gridactionpay_paydeposit");
		$this->assertEquals("Please Confirm you want to \"Pay From Deposit\" for these Domains:\nnewregisterd1.ie,\n", $this->getConfirmation());
		$this->select("id=CurrentNRPStatusesSelectionModel_list_newregisterd1.ie", "label=2 Year €48.56 Inc Vat.");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
		TestUtility::textPresent("€ 40.00");
		TestUtility::textPresent("€ 8.56");
		TestUtility::textPresent("€ 48.56");
		TestUtility::textPresent("newregisterd1.ie");

		$this->logout();
		$this->removeFromNrp();
	}

	public function testRemoveFormNrp() {
		$this->putInNrp();

		$this->login();
		$this->click("link=Current NRP Statuses");
		$this->waitForPageToLoad("30000");
		$this->click("id=jqg_thisJqGrid_newregisterd1.ie");
		$this->click("id=gridactionpay_removefromvoluntary");
		$this->assertEquals("Please Confirm you want to \"Remove from NRP\" these Domains:\nnewregisterd1.ie,\n", $this->getConfirmation());
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
		TestUtility::textPresent("This domain(s) removed successful from NRP : newregisterd1.ie");

		$this->logout();
		$this->removeFromNrp();
	}
	 
}

?>
