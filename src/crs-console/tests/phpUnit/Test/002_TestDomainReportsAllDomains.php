<?php

require_once './SetupPhpUnit.php';

class TestDomainReportsAll extends SetupPhpUnit {

	public function testFiltersOnGrid() {
		$this->login();

		$this->click("link=All Domains");
		$this->waitForPageToLoad("60000");

		TestUtility::equalsContent("IEDR Registrar's Console - All Domains", $this->getTitle());
		TestUtility::equalsContent("All Domains", $this->getText("//div[@id='content']/h2/a[3]"));

		//Check domain count after load module
		TestUtility::equalsCount(11);

		//Check comment on Grid after load
		TestUtility::equalsContent("View 1 - 11 of 11", $this->getText("css=div.ui-paging-info"));

		//searchText on field 'DomainName'
		TestUtility::searchText("pizzaonline.ie", "id=gs_A");
		TestUtility::equalsCount(1, "On field id=gs_A");
		TestUtility::cleanField("id=gs_A");


		//searchText on field 'Holder'
		TestUtility::searchText("DSQ GROUP LIMITED", "id=gs_B");
		TestUtility::equalsCount(1, "On field id=gs_B");
		TestUtility::cleanField("id=gs_B");


		//searchText on field 'Class'
		TestUtility::searchText("Body Corporate", "id=gs_C");
		TestUtility::equalsCount(7, "On field id=gs_C");
		TestUtility::cleanField("id=gs_C");


		//searchText on field 'Category'
		TestUtility::searchText("Corporate Name", "id=gs_D");
		TestUtility::equalsCount(5, "On field id=gs_D");
		TestUtility::cleanField("id=gs_D");

		//searchText on field 'Status'
		TestUtility::setField("id=gs_F_to", "2014-09-28");
		$this->keyPress("id=gs_F_to", "\\13");
		$this->keyPress("id=gs_F_to", "\\13");
		TestUtility::equalsContent("View 1 - 2 of 2", $this->getText("css=div.ui-paging-info"));

		TestUtility::cleanField("id=gs_F_to");

		//searchText on field 'Date from'
		TestUtility::searchText("2009-07-03", "id=gs_F_from");
		TestUtility::equalsCount(1, "On field id=gs_F_from");
		TestUtility::cleanField("id=gs_F_from");

			
		$this->logout();
	}
}
?>
