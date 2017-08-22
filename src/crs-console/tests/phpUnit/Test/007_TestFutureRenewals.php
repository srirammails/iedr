<?php

require_once './SetupPhpUnit.php';

class TestFutureRenewals extends SetupPhpUnit {
	private function deleteReservation() {
		TestUtility::query("DELETE FROM Reservation WHERE ID > 0");
	}

	private function removeFromReservationAnsSetState() {
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd1.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd2.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd3.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd4.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd5.ie'");
	}

	public static function setUpBeforeClass() {
		TestFutureRenewals::deleteReservation();
		TestFutureRenewals::prepareDomains();
	}

	public static function prepareDomains() {
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE()+INTERVAL 1 MONTH, DSM_State=17 where D_Name='newregisterd1.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE()+INTERVAL 1 MONTH, DSM_State=17 where D_Name='newregisterd2.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE()+INTERVAL 1 MONTH, DSM_State=17 where D_Name='newregisterd3.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE()+INTERVAL 1 MONTH, DSM_State=17 where D_Name='newregisterd4.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE()+INTERVAL 1 MONTH, DSM_State=17 where D_Name='newregisterd5.ie'");
	}

	public static function tearDownAfterClass() {
		TestFutureRenewals::removeFromReservationAnsSetState();
		TestFutureRenewals::deleteReservation();
	}

	public function testSearchOnNextMonthState() {
		$this->login();
		$this->click("link=Future Renewals by month");
		$this->waitForPageToLoad("3000");

		TestUtility::equalsContent("IEDR Registrar's Console - Future Renewals by month", $this->getTitle());
      
		date_default_timezone_set('Europe/Warsaw');
		$date = date("Y-m", strtotime("+1 month") );
      
		$this->select("id=AccountFutureRenewalByMonthModel_date", "value=$date");
		$this->click("name=yt0");
		$this->waitForPageToLoad("3000");
		TestUtility::equalsContent("View 1 - 5 of 5", $this->getText("css=div.ui-paging-info"));

		TestUtility::searchText("newregisterd1.ie", "id=gs_PK");
		TestUtility::equalsCount(1, "On field id=gs_PK");
		TestUtility::cleanField("id=gs_PK");

		$this->logout();
	}

	public function testSearchOnThisMonthState() {
		$this->login();
		$this->click("link=Future Renewals by month");
		$this->waitForPageToLoad("3000");


		TestUtility::equalsContent("IEDR Registrar's Console - Future Renewals by month", $this->getTitle());
      date_default_timezone_set('Europe/Warsaw');
      $date = date("Y-m");
		$this->select("id=AccountFutureRenewalByMonthModel_date", "value=$date");
		$this->click("name=yt0");
		$this->waitForPageToLoad("3000");
      

		TestUtility::equalsContent("No records to view", $this->getText("css=div.ui-paging-info"));
		$this->logout();
	}

	public function testConfirmOkWithTreeDomain() {
		$this->login();
		$this->click("link=Future Renewals by month");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("IEDR Registrar's Console - Future Renewals by month", $this->getTitle());
		TestUtility::equalsContent("View 1 - 5 of 5", $this->getText("css=div.ui-paging-info"));
      
      date_default_timezone_set('Europe/Warsaw');
		$date = date("Y-m", strtotime("+1 month") );
      
		$this->select("id=AccountFutureRenewalByMonthModel_date", "value=$date");

		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 5 of 5", $this->getText("css=div.ui-paging-info"));

		$this->click("id=jqg_thisJqGrid_newregisterd1.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd2.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd3.ie");

		$this->select("id=period_newregisterd1.ie", "value=RM2Yr");
		$this->select("id=period_newregisterd2.ie", "value=RM2Yr");
		$this->select("id=period_newregisterd3.ie", "value=RM2Yr");

		$this->click("id=gridaction_payonline");
		TestUtility::equalsContent("Please Confirm you want to \"Pay Online\" for these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\n", $this->getConfirmation());
		$this->waitForPageToLoad("30000");

		$this->logout();
	}

	public function testPayFromDepositForm() {
		$this->login();

		TestFutureRenewals::prepareDomains();

		$this->open("/index.php?r=account_future_renewals_by_month/confirmaction&id=WUW%253DWBW%25253DV1pXPXicnVVNb9swDM1PKXwcgjb%25252BkOOoKIYhHXYY1hVJh2AnQ7HpVoAtZZK8LijS3z5Kbhq3bpy0t4SPj6TIR3rxfXGxLJIimZCk8BMYQUbG2ZiRmBGSRXGRxQiPYj%25252FxYTFfXPykfkK9b4rncyghM1yKHzKH0qNBRB80HVMvk1XFRO6da%25252BqPqLdi6xxWUnNjLRPqKTC1ErUq7X%25252BCHmdc5PDvdHW3%25252BqwuWJbJWpi0qNELUgUC7lmp0%25252BU6raQwd2cViNrFjqgnAPKpgpybKVOYkdMRIjEmBcUlGq6sI7E5XZhLZuBmvQLLx0qnv2azr1c322g5FKwuzbXjamcNJjbJvYJbrg2o3D%25252Fl8Dg3uf8bmBq2kWAvEu5For0IaSPbTuayYlyU6GEtSdQtrVNRp5BO%25252Fk5alw2fPfg0SHcZvyjF1t45o8RO2Y%25252B7uR3vSHvwTnv4Tnv0TjvptW80Dfx2%25252Bxfc3M1rVLlaWxaOphGLa9pfVqaqLkHbbuFOWE0yGjQ%25252FNI1IO9LwaVmGz0sxfCXVoVW1b%25252BdtVfyn5ih3LMnaWlHba9e4W9VjKTzHONMthLSgTXtzNRp%25252BEO74s9cuGCd8EWfcWcYhPtK%25252BMH2hhzSTouCqgrfx4AAeHsCjAzjZ4dt3YueWUpbABD5r0xwCnCIzRqUlW0Lp5hg71Xd20Dbp0ln0iZEnSzi5VjIDrcGdv7BRTV8b%25252BrZmHz3opwcH6GE%25252FPTxAj%25252Frp0QE66ac%25252FbxwqE5SSqrnhOKUK28pu4emmR8%25252BCV6DxaLspjejDphH1YOq%25252BSoPUxWiDkxb4pG%25252Fn4KKOW6DOQDD8ErQ33Gpjitu0kgKEwei7al7Yq8Z%25252B5Pxxs9un%25252FsixH8kKP8SKPsQiPazNf2Rw5jU%25253D");
		$this->select("id=GridSelectionModel_list_newregisterd1.ie", "label=2 Year €48.56 Inc Vat.");
		$this->select("id=GridSelectionModel_list_newregisterd2.ie", "label=2 Year €48.56 Inc Vat.");
		$this->select("id=GridSelectionModel_list_newregisterd3.ie", "label=2 Year €48.56 Inc Vat.");
		$this->select("id=GridSelectionModel_list_newregisterd4.ie", "label=2 Year €48.56 Inc Vat.");
		$this->select("id=GridSelectionModel_list_newregisterd5.ie", "label=2 Year €48.56 Inc Vat.");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("newregisterd5.ie");
		TestUtility::textPresent("newregisterd4.ie");
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("€ 200.00");
		TestUtility::textPresent("€ 42.80");
		TestUtility::textPresent("€ 242.80");

		TestFutureRenewals::deleteReservation();
		$this->logout();
	}

	public function testPayOnlineForm() {
		$this->login();

		TestFutureRenewals::prepareDomains();

		$this->click("link=Future Renewals by month");
		$this->waitForPageToLoad("60000");
		$this->click("id=cb_thisJqGrid");
		$this->click("id=gridaction_payonline");
		$this->assertEquals("Please Confirm you want to \"Pay Online\" for these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\nnewregisterd4.ie,\nnewregisterd5.ie,\n", $this->getConfirmation());
		$this->waitForPageToLoad("60000");

		$this->type("id=GridSelectionModel_cardholder", "John Doe");
		$this->type("id=GridSelectionModel_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=GridSelectionModel_exp_month", "12");
		$this->type("id=GridSelectionModel_exp_year", "12");
		$this->type("id=GridSelectionModel_cvn", "123");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
			
		TestUtility::textPresent("newregisterd5.ie");
		TestUtility::textPresent("newregisterd4.ie");
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("€ 394.55");
		TestUtility::textPresent("€ 69.55");
		TestUtility::textPresent("€ 325.00");

		TestFutureRenewals::deleteReservation();

		$this->logout();
	}

	public function testPutInVoluntary() {
		$this->login();

		TestFutureRenewals::prepareDomains();

		$this->click("link=Future Renewals by month");
		$this->waitForPageToLoad("30000");
		$this->click("id=cb_thisJqGrid");
		$this->click("id=gridaction_voluntary");
		$this->assertEquals("Please Confirm you want to \"Put into Voluntary NRP \" these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\nnewregisterd4.ie,\nnewregisterd5.ie,\n", $this->getConfirmation());
		$this->waitForPageToLoad("60000");


		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd1_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd2_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd3_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd4_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd5_confirmed"));

		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd4.ie");
		TestUtility::textPresent("newregisterd5.ie");

		TestFutureRenewals::deleteReservation();

		$this->logout();
	}

	public function testSetDomainAutorenew() {
		$this->login();

		TestFutureRenewals::prepareDomains();

		$this->click("link=Future Renewals by month");
		$this->waitForPageToLoad("30000");
		$this->click("id=cb_thisJqGrid");
		$this->click("id=gridaction_autorenew");
		$this->assertEquals("Please Confirm you want to \"Set Auto renew\" these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\nnewregisterd4.ie,\nnewregisterd5.ie,\n", $this->getConfirmation());
		$this->waitForPageToLoad("60000");

		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd1_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd2_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd3_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd4_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd5_confirmed"));

		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd4.ie");
		TestUtility::textPresent("newregisterd5.ie");
		TestUtility::textPresent("Autorenew");
		TestUtility::textPresent("2012-09-28");

		TestFutureRenewals::deleteReservation();

		$this->logout();
	}

}

?>
