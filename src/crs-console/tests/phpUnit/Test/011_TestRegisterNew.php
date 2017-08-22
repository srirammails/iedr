<?php

require_once './SetupPhpUnit.php';

class TestRegisterNew extends SetupPhpUnit {

	private function removeRegistreredDomain() {
		TestUtility::query("DELETE FROM Ticket WHERE T_Number > 0");
		TestUtility::query("DELETE FROM Reservation WHERE ID > 0");
	}

	public function testPasteUniqueDomain() {
		$this->login();
			
		$this->click("link=Register New");
		$this->waitForPageToLoad("30000");
			
		$this->type("id=Domains_RegNew_domain_names", "newdomain.ie");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");
			
		TestUtility::textPresent("newdomain.ie");
		$this->removeRegistreredDomain();
		$this->logout();
	}

	public function testPasteNotUniqueDomain() {
		$this->login();

		$this->click("link=Register New");
		$this->waitForPageToLoad("30000");
			
		$this->type("id=Domains_RegNew_domain_names", "newregisterd1.ie");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("newregisterd1.ie is invalid, already registered, or has a pending ticket");
			
		$this->logout();
	}

	public function testRegisterNewDomainPayOnline() {
		$this->login();

		$this->open("index.php?r=domains/domaindetails&validavailabledomains=WUW%253DWBW%25253DV1pXPXicC%25252FcOt001NjNKtrQwMTY3MElLszQxSUqzNE%25252BzMDe2MDayTLUwTDQ1MTNJskgLDw63TbQytKrOtDKwLrYyNLJSykstT8nPTczM08tMVbKuBQBGOxZj");
		$this->type("id=Domains_Creation_Details_holder", "Foo");
		$this->type("id=Domains_Creation_Details_remarks", "Remark");
		 
		$this->type("id=Domains_Creation_Details_admin_contact_nic_1", "AEI383-IEDR");
		$this->click("id=Domains_Creation_Details_paymentType_0");
		$this->type("id=Domains_Creation_Details_cardholder", "John Doe");
		$this->click("id=Domains_Creation_Details_creditcardno");
		$this->type("id=Domains_Creation_Details_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=Domains_Creation_Details_exp_month", "12");
		$this->type("id=Domains_Creation_Details_exp_year", "12");
		$this->type("id=Domains_Creation_Details_cvn", "123");
	  
		$this->click("id=Domains_Creation_Details_accept_tnc");
	  
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("Your ticket for newdomain.ie has been received and will be processed by our hostmasters in due course.");
		$this->logout();

		$this->removeRegistreredDomain();
	}

	public function testRegisterNewDomainPayDeposit() {
		$this->login();

		$this->open("index.php?r=domains/domaindetails&validavailabledomains=WUW%253DWBW%25253DV1pXPXicC%25252FcOt001NjNKtrQwMTY3MElLszQxSUqzNE%25252BzMDe2MDayTLUwTDQ1MTNJskgLDw63TbQytKrOtDKwLrYyNLJSykstT8nPTczM08tMVbKuBQBGOxZj");
		$this->type("id=Domains_Creation_Details_holder", "Foo");
		$this->type("id=Domains_Creation_Details_remarks", "Remark");
		 
		$this->type("id=Domains_Creation_Details_admin_contact_nic_1", "AEI383-IEDR");
		$this->click("id=Domains_Creation_Details_paymentType_1");
		$this->type("id=Domains_Creation_Details_cardholder", "John Doe");
		$this->click("id=Domains_Creation_Details_creditcardno");
		$this->type("id=Domains_Creation_Details_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=Domains_Creation_Details_exp_month", "12");
		$this->type("id=Domains_Creation_Details_exp_year", "12");
		$this->type("id=Domains_Creation_Details_cvn", "123");
	  
		$this->click("id=Domains_Creation_Details_accept_tnc");
	  
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("Your ticket for newdomain.ie has been received and will be processed by our hostmasters in due course.");
		$this->logout();

		$this->removeRegistreredDomain();
	}

	public function testRegisterNewDomainWithCharity() {
		$this->login();

		$this->open("index.php?r=domains/domaindetails&validavailabledomains=WUW%253DWBW%25253DV1pXPXicC%25252FcOt001NjNKtrQwMTY3MElLszQxSUqzNE%25252BzMDe2MDayTLUwTDQ1MTNJskgLDw63TbQytKrOtDKwLrYyNLJSykstT8nPTczM08tMVbKuBQBGOxZj");
		$this->type("id=Domains_Creation_Details_holder", "Foo");
		$this->type("id=Domains_Creation_Details_remarks", "Remark");
		 
		$this->type("id=Domains_Creation_Details_admin_contact_nic_1", "AEI383-IEDR");
	  
		$this->click("id=Domains_Creation_Details_paymentType_2");
		$this->type("id=Domains_Creation_Details_charitycode", "123");
		 
		$this->type("id=Domains_Creation_Details_cardholder", "John Doe");
		$this->click("id=Domains_Creation_Details_creditcardno");
		$this->type("id=Domains_Creation_Details_creditcardno", "4263971921001307");
		$this->click("//img[@onclick=\"setcardtype('VISA')\"]");
		$this->type("id=Domains_Creation_Details_exp_month", "12");
		$this->type("id=Domains_Creation_Details_exp_year", "12");
		$this->type("id=Domains_Creation_Details_cvn", "123");
	  
		$this->click("id=Domains_Creation_Details_accept_tnc");
	  
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("Your ticket for newdomain.ie has been received and will be processed by our hostmasters in due course.");
		$this->logout();

		$this->removeRegistreredDomain();
	}
}

?>