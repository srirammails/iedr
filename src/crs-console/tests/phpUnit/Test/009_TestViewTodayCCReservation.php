<?php

require_once './SetupPhpUnit.php';

class TestViewTodayCCReservation extends SetupPhpUnit {
	 
	public static function setUpBeforeClass() {

		date_default_timezone_set('Europe/Warsaw');
		$date = "".date("Y-m-d");
		TestUtility::query("INSERT INTO Reservation VALUES(9,'IDL2-IEDR','newregisterd1.ie',12,'".$date."','65.00',3,'13.91','Y','N',NULL,NULL,55,'Std1Year','transfer',NULL,NULL,'Credit Card')");
		TestUtility::query("INSERT INTO Reservation VALUES(10,'IDL2-IEDR','newregisterd2.ie',12,'".$date."','65.00',3,'13.91','Y','N',NULL,NULL,55,'Std1Year','renewal',NULL,NULL,'Credit Card')");
		TestUtility::query("INSERT INTO Reservation VALUES(11,'IDL2-IEDR','newregisterd3.ie',12,'".$date."','65.00',3,'13.91','Y','N',NULL,NULL,55,'Std1Year','registration',NULL,NULL,'Credit Card')");
	}

	public static function tearDownAfterClass() {
		TestUtility::query("DELETE FROM Reservation WHERE ID > 0");
	}

	public function testViewCCTodayReservation() {
		$this->login();
		$this->click("link=View Today`s CC Reservations");
		$this->waitForPageToLoad("30000");
		 
		TestUtility::equalsContent("View 1 - 3 of 3", $this->getText("css=div.ui-paging-info"));

		TestUtility::textPresent("3");
		TestUtility::textPresent("195.0");
		TestUtility::textPresent("41.73");
		TestUtility::textPresent("236.73");

		$this->logout();
	}
	 
	public function testSearchOnDomain() {
		$this->login();
		$this->click("link=View Today`s CC Reservations");
		$this->waitForPageToLoad("30000");
		 
		TestUtility::equalsContent("View 1 - 3 of 3", $this->getText("css=div.ui-paging-info"));

		//searchText on field 'DomainName'
		TestUtility::searchText("newregisterd1.ie", "id=gs_A");
		TestUtility::equalsCount(1, "On field id=gs_A");
		TestUtility::equalsContent("View 1 - 1 of 1", $this->getText("css=div.ui-paging-info"));
		TestUtility::cleanField("id=gs_A");

		//searchText on field 'DomainName'
		TestUtility::searchText("newregisterd2.ie", "id=gs_A");
		TestUtility::equalsCount(1, "On field id=gs_A");
		TestUtility::equalsContent("View 1 - 1 of 1", $this->getText("css=div.ui-paging-info"));
		TestUtility::cleanField("id=gs_A");


		//searchText on field 'DomainName'
		TestUtility::searchText("newregisterd3.ie", "id=gs_A");
		TestUtility::equalsCount(1, "On field id=gs_A");
		TestUtility::equalsContent("View 1 - 1 of 1", $this->getText("css=div.ui-paging-info"));
		TestUtility::cleanField("id=gs_A");

		$this->logout();
	}
	 
	public function testOnReservationTypeWhenIsSetRegistration() {
		$this->login();
		$this->click("link=View Today`s CC Reservations");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 3 of 3", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_O", "value=REGISTRATION");
		TestUtility::equalsContent("View 1 - 1 of 1", $this->getText("css=div.ui-paging-info"));

		$this->logout();
	}
	 
	public function testOnReservationTypeWhenIsSetTransfer() {
		//TRANSFER
		$this->login();
		$this->click("link=View Today`s CC Reservations");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 3 of 3", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_O", "value=TRANSFER");
		TestUtility::equalsContent("View 1 - 1 of 1", $this->getText("css=div.ui-paging-info"));

		$this->logout();
	}
	 
	public function ReservationTypeWhenIsSetRenewal() {
		//RENEWAL
		$this->login();
		$this->click("link=View Today`s CC Reservations");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 3 of 3", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_O", "value=RENEWAL");
		TestUtility::equalsContent("View 1 - 1 of 1", $this->getText("css=div.ui-paging-info"));
		$this->logout();
	}
}

?>