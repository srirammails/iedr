<?php

require_once './SetupPhpUnit.php';

class TestViewSinglePayments extends SetupPhpUnit {
	public function testSearchByDomain() {
		$this->login();
		$this->open("/index.php?r=account_single_payment_search/menu");
		$this->click("link=Single Payment Search");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 11 of 11", $this->getText("css=div.ui-paging-info"));

		$this->type("id=ViewSinglePaymentSearchModel_domainName", "newregisterd1.ie");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 1 of 1", $this->getText("css=div.ui-paging-info"));
		TestUtility::textPresent("newregisterd1.ie");
		TestUtility::textPresent("2012-09-28");
		TestUtility::textPresent("Deposit account");
		TestUtility::textPresent("1");
		TestUtility::textPresent("2012-09-28");
		TestUtility::textPresent("€40");
		TestUtility::textPresent("€8.56");
		TestUtility::textPresent("€48.56");

		$this->type("id=ViewSinglePaymentSearchModel_domainName", "newr");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("View 1 - 10 of 10", $this->getText("css=div.ui-paging-info"));

		$this->type("id=ViewSinglePaymentSearchModel_domainName", "notfound");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("No records to view", $this->getText("css=div.ui-paging-info"));

		$this->logout();
	}
}

?>