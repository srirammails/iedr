<?php

require_once './SetupPhpUnit.php';

class TestCurrentRenewals extends SetupPhpUnit {

	private function cleanUpDomains() {
		TestUtility::query("DELETE FROM Reservation WHERE ID > 0");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd1.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd2.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd3.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd4.ie'");
		TestUtility::query("update Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd5.ie'");
	}

	public static function setUpBeforeClass() {
		TestCurrentRenewals::cleanUpDomains();
	}
	
	public static function tearDownAfterClass() {
		TestCurrentRenewals::cleanUpDomains();
	}

	public function testSearchOnRenewalThisMonthState() {
		$this->login();
		
		$this->cleanUpDomains();
		
		$this->click("link=Current Renewals");
		$this->waitForPageToLoad("3000");

		TestUtility::equalsContent("IEDR Registrar's Console - Current Renewals", $this->getTitle());
		$this->select("id=CurrentRenewalsModel_countDays", "value=RENEWAL_THIS_MONTH");
		$this->click("name=yt0");
		$this->waitForPageToLoad("3000");
		TestUtility::equalsContent("View 1 - 5 of 5", $this->getText("css=div.ui-paging-info"));

		TestUtility::searchText("newregisterd1.ie", "id=gs_PK");
		TestUtility::equalsCount(1, "On field id=gs_PK");
		TestUtility::cleanField("id=gs_PK");
		
		$this->logout();
	}

	public function testSearchOnRenewalTodayState() {
		$this->login();
		$this->cleanUpDomains();
		
		$this->click("link=Current Renewals");
		$this->waitForPageToLoad("3000");
		TestUtility::equalsContent("IEDR Registrar's Console - Current Renewals", $this->getTitle());
		$this->select("id=CurrentRenewalsModel_countDays", "value=RENEWAL_TODAY");
		$this->click("name=yt0");
		$this->waitForPageToLoad("3000");
		
		TestUtility::equalsContent("View 1 - 5 of 5", $this->getText("css=div.ui-paging-info"));
		TestUtility::searchText("newregisterd1.ie", "id=gs_PK");
		TestUtility::equalsCount(1, "On field id=gs_PK");
		TestUtility::cleanField("id=gs_PK");
		
		$this->logout();
	}

	public function testSearchOnOverdueState() {
		$this->login();
		$this->cleanUpDomains();
		
		$this->click("link=Current Renewals");
		$this->waitForPageToLoad("3000");
		
		TestUtility::equalsContent("IEDR Registrar's Console - Current Renewals", $this->getTitle());
		
		$this->select("id=CurrentRenewalsModel_countDays", "value=OVERDUE");
		$this->click("name=yt0");
		$this->waitForPageToLoad("3000");
		
		TestUtility::equalsContent("No records to view", $this->getText("css=div.ui-paging-info"));

		$this->logout();
	}

	public function testConfirmOkWithTreeDomain() {
		$this->login();
		$this->cleanUpDomains();
		
		$this->click("link=Current Renewals");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("IEDR Registrar's Console - Current Renewals", $this->getTitle());
		TestUtility::equalsContent("No records to view", $this->getText("css=div.ui-paging-info"));
		
		$this->select("id=CurrentRenewalsModel_countDays", "value=RENEWAL_THIS_MONTH");

		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 5 of 5", $this->getText("css=div.ui-paging-info"));

		$this->click("id=jqg_thisJqGrid_newregisterd1.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd2.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd3.ie");
		$this->select("id=period_newregisterd1.ie", "value=RM2Yr");
		$this->select("id=period_newregisterd3.ie", "value=RM2Yr");
		$this->select("id=period_newregisterd3.ie", "value=RM2Yr");

		$this->click("id=gridactionpay_payonline");
		TestUtility::equalsContent("Please Confirm you want to \"Pay Online\" for these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\n", $this->getConfirmation());
		$this->waitForPageToLoad("30000");

		$this->logout();
	}

	public function testPayonlineForm() {
		$this->login();
		$this->cleanUpDomains();

		$this->open("/index.php?r=account_current_renewals/confirmaction&id=WUW%253DWBW%25253DV1pXPXic7VfbbuM2EM2nCEax6EWb6GJbtoKg2HoXKFBEDeJ0jaIXgZaYmIBEaikqsWG4394hKdm0rfjy0uxD8yQPOWfOkIczk8kvk5uuF%25252FiDAPkDhIL0MU18NPCS3qCHp8Nhv9tNpn23G%25252BBhMBlPbn4NvWHYGVWcYyruMcUvKCvHOMOJIIzeshRnndB3w2UZBmEnYXmOaNq5LkPwKtCC0YxQXP%25252FmWFScVjyTv7te2LkiNMXzy2JW%25252FMhvUJKwioo40bFiXge7yjGtpIfbDTsU43TEcUrECHGIQ0JXrvQkuNr%25252BEQn8sChUSCA0%25252Bu3%25252B%25252FlP0IH%25252F1gRDmhIFXJH3ctY%25252FMojZCiBQ%25252FoioTd2pvqXACGfiF4ydSCsxT95Lgf8YidX%25252FHiNvmivfqim%25252BuqGwcCMVyROCESiEtPWc%25252FzB76HqjCgsO9%25252BP4i3uB94BwtOtco9OXNuP19ZOV3ot070%25252B4ftK%25252FK0HPN5CdEzMYVKIcvpBccg3L2VFLPKIt5leFSZgO2JQkd%25252BKo%25252FQEddE8quFWivtWbvCUNrZiCv%25252F0tFQExASdoMUFPKG4kBFZICzKhZAjfPdGvVofb3%25252FI3%25252F%25252Fe4WwPG3cFxnV%25252Bo25CgTjLfuK04YfSQ8x%25252B3r3pF1f7Pe8ITMp4xlGFFFq6slpGn14cwSxSgBRpTZuOKsjFEu360tbTOWpZjbeF7EC%25252FkC5EfOqJipVQHJ2skzbb0COKKww6j6ADkLVlSF%25252FQLKYJWoQ8RlgimCZ6mo9YBar6EGUE3QBh1AaJXDK06QKjhwATmhdSDXa0xoLh284HwC%25252FR0CTYoNARB%25252FhumTmJnRpWLMwG7v%25252FMCBGXioM1enfCx1vWoE984PPjCDA5Rxn6eGHcLf%25252BYGHZmBZHEwpnkChZ3BIofzu%25252FJ1PyJV1aGgyMt%25252FDMUaaglaOYGyco0z3RdhwSyjJq9x64IiWSHVai5TWO4l%25252F%25252FY5Oy%25252BLatcH70nE6LVlp7QHoT%25252BRJQUJZukXzo5COY4C6viI%25252BxfwOCSgW6mB8qEtXf%25252F%25252FhvB%25252F%25252B9cO3f16qj6Vre6vvlo7trr65InXfzXFZoifVBGSBXgIEJ9NK4JWVIGrN0DO2YDZYWOKFWSlOCKRvFRlKcLlVCnr1RegD36rRr0lANsNN3fysay6DsrBa6QYPTUXSiTM0xdm6rbR1ZFm0PypLaQlmTbF1xxlQLHXB2nrz6qZlRKv5vU9QTx1qU6SOttMqHH34D6x4XxXWh43R0fF0kd1C%25252B3lt2iqEugOrHZ%25252FmBeELq5k%25252BtqsG7Au29902CzWebFvqRLot%25252B6yrNW5TEIydn0H7j6B%25252BJblN2r4eAQ71tEMjymvu3mF374i7f9h9PcCALjHnUlXRjuCbMbKeHzguYZgsazu4XYzU0HwRK38lPydcruohrll8bmTbeAbG4qYKbYYlqesRDCYFozA8A%25252FqGyZY913Y5qDRDVdDMVF9po3ffutF7b9Xo%25252Fbds9N23avS9r63R9%25252F%25252Fv8%25252F9hn1%25252B1XXrUevJRS1eMdotEtPd2IkPQ0e6Tjk7vTeBn%25252Fkd%25252FYks60cs%25252F4LX6F%25252FL108E%25253D");
		$this->select("id=CurrentRenewalsSelectionModel_list_newregisterd1.ie", "label=2 Year €48.56 Inc Vat.");
		$this->select("id=CurrentRenewalsSelectionModel_list_newregisterd2.ie", "label=2 Year €48.56 Inc Vat.");
		$this->type("id=CurrentRenewalsSelectionModel_cardholder", "John Doe");
		$this->type("id=CurrentRenewalsSelectionModel_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=CurrentRenewalsSelectionModel_exp_month", "12");
		$this->type("id=CurrentRenewalsSelectionModel_exp_year", "12");
		$this->click("id=CurrentRenewalsSelectionModel_cvn");
		$this->type("id=CurrentRenewalsSelectionModel_cvn", "123");
		$this->click("name=yt0");
		$this->waitForPageToLoad("60000");
		
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd1.ie");

		TestUtility::textPresent("€ 145.00");
		TestUtility::textPresent("€ 31.03");
		TestUtility::textPresent("€ 176.03");

		$this->logout();
	}

	public function testPaydepositForm() {
		$this->login();
		$this->cleanUpDomains();

		$this->open("/index.php?r=account_current_renewals/menu");
		$this->select("id=CurrentRenewalsModel_countDays", "label=Due for renewal this month");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
		$this->click("id=cb_thisJqGrid");
		$this->click("id=gridactionpay_paydeposit");
		$this->assertEquals("Please Confirm you want to \"Pay From Deposit\" for these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\nnewregisterd4.ie,\nnewregisterd5.ie,\n", $this->getConfirmation());
		$this->waitForPageToLoad("30000");

		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd1_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd2_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd3_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd4_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd5_confirmed"));
			
		$this->open("/index.php?r=account_current_renewals/confirmaction&id=WUW%253DWBW%25253DV1pXPXicnVVNb9swDM1PKXwcgjaWP5KoKIbB3WlYVyQdgp0M2aZbAbaUyfK2oMh%25252B%25252Byg5bdS5cdLebD4%25252BkiIfpdWX1VWWT%25252BOAZH48J6xkWeRPp2FJcihmUTnPZnE0KWcQx%25252BVqubr6RsmcekmrFAi9AAG%25252FWdUsoYJccym%25252BygIqj5KIPjZ0Sr1c1jUThXfZUH9CvTXbFLCWDdfGgnEU6FaJVlXmPyTUu%25252BCigD%25252Fn64f1R3XF8ly2Qqd5ly1Vu3QXNYjWxgypJwCKREHBdcIUZuJ0YpDIBLfu10zD3WYNhoAlJd8Xi883d%25252BYvxopAcYmsG8PxnznmHDsjpiigZG2lb61vYxObJqCngnveaFCFf87h71IX%25252Fg9gauwi5CASHETCg0jkIk9tLWTNuKjQw1hmYb%25252B0XkW9Qnr5e2ltNjz26MMo3Wf8pBTbeJeM2pH7cT%25252B35Z1oJ2%25252B0B2%25252B0h2%25252B0R4P2bUOJ77Z%25252FxfXDskXJq41h4Wg6sdim%25252FWJVqtoKGtOtkD4aoTJKug9Uf%25252BhGGu82Z%25252Fy8IeOenDn1zbiNaH%25252B2HFcAKzI2J6i7gp27WQyshBcYJnmCkEZc2qvb0%25252FFJsOcv%25252FnfBOMGLONPego7xjOaA6Qs5pLkUJVc1vI6TI3hwBA%25252BP4NEefzondi6TsgIm8Fjb7h7AITKtVVqxDCo7xtiKvreCpknX1tKcaXmWwdmtkjk0DdirMOhEM9SGoaU5RCfDdHKEHgzTgyP0cJgeHqFHw%25252FTnhUNlglJSdbczTqnGtrJ757beCV5Bg3e2ndKEPm47UY8S%25252B0KNUhvDBecOuNO3dbBRpw7Y5CAYPgTughttJLhNaynwncLo%25252B2pe2OvOfuL8cbPdm%25252F7EsZ%25252FICt7FCt%25252FFigZY23%25252BBcu4Q");
		
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
			
		TestUtility::textPresent("€ 325.00");
		TestUtility::textPresent("€ 69.55");
		TestUtility::textPresent("€ 394.55");
			
		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd4.ie");
		TestUtility::textPresent("newregisterd5.ie");

		$this->logout();
	}

	public function testPutInVoluntary() {
		$this->login();
		$this->cleanUpDomains();
		
		$this->open("/index.php?r=account_current_renewals/menu");
		$this->select("id=CurrentRenewalsModel_countDays", "label=Due for renewal this month");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		$this->click("id=cb_thisJqGrid");
		$this->click("id=gridactionpay_voluntary");
		$this->assertEquals("Please Confirm you want to \"Put into Voluntary NRP \" these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\nnewregisterd4.ie,\nnewregisterd5.ie,\n", $this->getConfirmation());

		$this->waitForPageToLoad("30000");

		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd1_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd2_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd3_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd4_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd5_confirmed"));

		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd4.ie");
		TestUtility::textPresent("newregisterd5.ie");

		TestUtility::textPresent("2012-09-28");
		date_default_timezone_set('Europe/Warsaw');
		$date = "".date("Y-m-d");
		TestUtility::textPresent("".$date);


		$this->logout();
	}

	public function testSetDomainAutorenew() {
		$this->login();
		$this->cleanUpDomains();
		
		$this->open("/index.php?r=account_current_renewals/menu");
		$this->select("id=CurrentRenewalsModel_countDays", "label=Due for renewal this month");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
		$this->click("id=cb_thisJqGrid");
		$this->click("id=gridactionpay_autorenew");
		$this->assertEquals("Please Confirm you want to \"Set Auto renew\" these Domains:\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\nnewregisterd4.ie,\nnewregisterd5.ie,\n", $this->getConfirmation());
			
		$this->waitForPageToLoad("30000");
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd1_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd2_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd3_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd4_confirmed"));
		$this->assertTrue($this->isChecked("CurrentRenewalsSelectionModel_domlist_newregisterd5_confirmed"));
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("newregisterd2.ie");
		TestUtility::textPresent("newregisterd3.ie");
		TestUtility::textPresent("newregisterd4.ie");
		TestUtility::textPresent("newregisterd5.ie");

		TestUtility::textPresent("2012-09-28");
		date_default_timezone_set('Europe/Warsaw');
		$date = "".date("Y-m-d");
		TestUtility::textPresent("".$date);

		TestUtility::textPresent("Autorenew");

		$this->logout();
	}
}

?>
