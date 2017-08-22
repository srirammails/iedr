<?php

require_once './SetupPhpUnit.php';

class TestAutoRenew extends SetupPhpUnit {
	public static function setUpBeforeClass() {
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=81 where D_Name='newregister.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=81 where D_Name='newregisterd1.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=81 where D_Name='newregisterd2.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=49 where D_Name='newregisterd3.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=49 where D_Name='newregisterd4.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=49 where D_Name='newregisterd5.ie'");
	}

	public static function tearDownAfterClass() {
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregister.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd1.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd2.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd3.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd4.ie'");
		TestUtility::query("UPDATE Domain set D_Ren_Dt=CURDATE(), DSM_State=17 where D_Name='newregisterd5.ie'");
	}
	
	public function testFiltersByField() {
		$this->login();
		$this->click("link=View Auto Renew Domains");
		$this->waitForPageToLoad("30000");
		TestUtility::equalsContent("View 1 - 6 of 6", $this->getText("css=div.ui-paging-info"));

		//searchText on field 'DomainName'
		TestUtility::searchText("newregister.ie", "id=gs_A");
		TestUtility::equalsCount(1, "On field id=gs_A");
		TestUtility::cleanField("id=gs_A");
		
		//searchText on field 'Holder'
		TestUtility::searchText("Co", "id=gs_B");
		TestUtility::equalsCount(5, "On field id=gs_B");
		TestUtility::cleanField("id=gs_B");

		//searchText on field 'Class'
		TestUtility::searchText("Natural Person", "id=gs_C");
		TestUtility::equalsCount(1, "On field id=gs_C");
		TestUtility::cleanField("id=gs_C");

		//searchText on field 'Category'
		TestUtility::searchText("Corporate Name", "id=gs_D");
		TestUtility::equalsCount(2, "On field id=gs_D");
		TestUtility::cleanField("id=gs_D");
		
		$this->select("id=gs_E", "value=Autorenew");
		TestUtility::equalsContent("View 1 - 3 of 3", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_E", "value=");
		
		$this->select("id=gs_E", "value=RenewOnce");
		TestUtility::equalsContent("View 1 - 3 of 3", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_E", "value=");
		
		$this->select("id=gs_F", "value=Active");
		TestUtility::equalsContent("View 1 - 6 of 6", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_F", "value=");
		
		$this->select("id=gs_F", "value=InvoluntaryMailed");
		TestUtility::equalsContent("No records to view", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_F", "value=");
		
		$this->select("id=gs_F", "value=InvoluntaryMailed");
		TestUtility::equalsContent("No records to view", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_F", "value=");
		
		
		date_default_timezone_set('Europe/Warsaw');
		$date = "".date("Y-m-d");
		
		TestUtility::searchText($date, "id=gs_G_from");
		TestUtility::searchText($date, "id=gs_G_to");
		TestUtility::equalsContent("View 1 - 6 of 6", $this->getText("css=div.ui-paging-info"));
		$this->select("id=gs_E", "value=");
		
		$this->logout();
	}
}

?>