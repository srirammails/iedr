<?php

require_once './SetupPhpUnit.php';

class TestTransferRequest extends SetupPhpUnit {
	public static function setUpBeforeClass() {
		TestUtility::query("UPDATE Domain set DSM_State=0 where D_Name='autocreated2.ie'");
		TestUtility::query("UPDATE Domain set DSM_State=17 where D_Name='autocreated.ie'");

		TestUtility::query("DELETE FROM Ticket WHERE T_Number > 0");
	}
	private function removeRegistreredDomain() {
		TestUtility::query("UPDATE Domain set DSM_State=17 where D_Name='autocreated2.ie'");
		TestUtility::query("UPDATE Domain set DSM_State=17 where D_Name='autocreated.ie'");

		TestUtility::query("DELETE FROM Ticket WHERE T_Number > 0");
	}

	public function testPasteUniqueDomain() {
		$this->login();
			
		$this->click("link=Request Transfer");
		$this->waitForPageToLoad("30000");
			
		$this->type("id=Domains_RegNew_domain_names", "autocreated.ie");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
			
		TestUtility::textPresent("autocreated.ie");
		$this->logout();
	}

	public function testPasteNotUniqueDomain() {
		$this->login();

		$this->click("link=Request Transfer");
		$this->waitForPageToLoad("30000");
			
		$this->type("id=Domains_RegNew_domain_names", "autocreated2.ie");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("autocreated2.ie is invalid, already registered, or has a pending ticket, or has incorrect state");
			
		$this->logout();
	}

	public function testRegisterNewDomainPayOnline() {
		$this->login();

		$this->open("/index.php?r=domains/requesttransferdetails&validavailabledomains=WUW%253DWBW%25253DV1pXPXicBcExDoAgDADAv%25252FAAQ0OrUsILHB2YKy0Jk4niZPy7d2UrOcSo5hHaEoPXVZEaWKBGUEFIVeoxY2ha9pKFgd%25252FOPt0MyE6ecdbLZJhO3Vz6fqMdGC0%25253D");
		$this->select("id=DomainsTransferDetails_list", "label=2 Year €48.56 Inc Vat.");
		$this->type("id=DomainsTransferDetails_admin_contact_nic_1", "AHK459-IEDR");
		$this->click("id=DomainsTransferDetails_paymentType_0");
		$this->type("id=DomainsTransferDetails_cardholder", "John Doe");
		$this->click("id=DomainsTransferDetails_creditcardno");
		$this->type("id=DomainsTransferDetails_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=DomainsTransferDetails_exp_month", "12");
		$this->click("id=DomainsTransferDetails_exp_year");
		$this->type("id=DomainsTransferDetails_exp_year", "12");
		$this->type("id=DomainsTransferDetails_cvn", "123");
		$this->type("id=DomainsTransferDetails_charitycode", "123");
		$this->click("id=DomainsTransferDetails_accept_tnc");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("Your ticket for autocreated.ie has been received and will be processed by our hostmasters in due course.");
		$this->logout();

		$this->removeRegistreredDomain();
	}

	public function testRegisterNewDomainPayDeposit() {
		$this->login();

		$this->open("/index.php?r=domains/requesttransferdetails&validavailabledomains=WUW%253DWBW%25253DV1pXPXicBcExDoAgDADAv%25252FAAQ0OrUsILHB2YKy0Jk4niZPy7d2UrOcSo5hHaEoPXVZEaWKBGUEFIVeoxY2ha9pKFgd%25252FOPt0MyE6ecdbLZJhO3Vz6fqMdGC0%25253D");
		$this->select("id=DomainsTransferDetails_list", "label=2 Year €48.56 Inc Vat.");
		$this->type("id=DomainsTransferDetails_admin_contact_nic_1", "AHK459-IEDR");
		$this->click("id=DomainsTransferDetails_paymentType_1");
		$this->click("id=DomainsTransferDetails_accept_tnc");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("Your ticket for autocreated.ie has been received and will be processed by our hostmasters in due course.");
		$this->logout();

		$this->removeRegistreredDomain();
	}

	public function testRegisterNewDomainWithCharity() {
		$this->login();

		$this->open("/index.php?r=domains/requesttransferdetails&validavailabledomains=WUW%253DWBW%25253DV1pXPXicBcExDoAgDADAv%25252FAAQ0OrUsILHB2YKy0Jk4niZPy7d2UrOcSo5hHaEoPXVZEaWKBGUEFIVeoxY2ha9pKFgd%25252FOPt0MyE6ecdbLZJhO3Vz6fqMdGC0%25253D");
		$this->select("id=DomainsTransferDetails_list", "label=2 Year €48.56 Inc Vat.");
		$this->type("id=DomainsTransferDetails_admin_contact_nic_1", "AHK459-IEDR");
		$this->click("id=DomainsTransferDetails_paymentType_2");
		$this->type("id=DomainsTransferDetails_charitycode", "123");
		$this->click("id=DomainsTransferDetails_accept_tnc");
			
		$this->click("name=yt0");
		$this->waitForPageToLoad("80000");

		TestUtility::textPresent("Your ticket for autocreated.ie has been received and will be processed by our hostmasters in due course.");
		$this->logout();

		$this->removeRegistreredDomain();
	}

}

?>