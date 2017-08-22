<?php

require_once './SetupPhpUnit.php';

class TestDomainReportsGrid extends SetupPhpUnit {

	public function testAllDomainsGridFilters() {
		$this->login();

		$this->click("link=All Domains");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("IEDR Registrar's Console - All Domains", $this->getTitle());

		TestUtility::searchText("SHANNON LNG LIMITED", "id=gs_B");
		TestUtility::equalsCount(1);
		TestUtility::cleanField("id=gs_B");

		TestUtility::searchText("Corporate Name", "id=gs_D");
		TestUtility::equalsCount(5);
		TestUtility::cleanField("id=gs_D");

		//Reload grid
		$this->click("xpath=//td[@id='refresh_thisJqGrid']/div/span");

		TestUtility::setField("id=gs_F_to", "2014-09-28");
		$this->keyPress("id=gs_F_to", "\\13");
		$this->keyPress("id=gs_F_to", "\\13");
		TestUtility::equalsContent("View 1 - 2 of 2", $this->getText("css=div.ui-paging-info"));

		TestUtility::cleanField("id=gs_F_to");

		TestUtility::setField("id=gs_F_from", "2014-09-28");
		$this->keyPress("id=gs_F_from", "\\13");
		$this->keyPress("id=gs_F_from", "\\13");
		TestUtility::equalsContent("View 1 - 2 of 2", $this->getText("css=div.ui-paging-info"));

		$this->logout();
	}
}

?>
