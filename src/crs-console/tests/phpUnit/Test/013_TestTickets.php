<?php

require_once './SetupPhpUnit.php';

class TestTicket extends SetupPhpUnit {
	public static function setUpBeforeClass() {
		TestUtility::query("INSERT INTO `Ticket` VALUES (359661,'R','newdomain1.ie',NULL,'Holder1',NULL,00000101,NULL,'Body Corporate (Ltd,PLC,Company)','Corporate Name','wewe','AGZ082-IEDR',NULL,NULL,NULL,'TDI2-IEDR',NULL,'IDL2-IEDR',NULL,'IDL2-IEDR','ns.irishdomains.net',NULL,NULL,NULL,'ns2.irishdomains.net',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2012-10-10',0,'2012-10-10','N',NULL,NULL,'2012-10-10','2012-10-10 11:33:35','',NULL,NULL,'2012-10-10 13:33:35','N','N',1,NULL,0,'2012-10-10 11:33:35',0,'2012-10-10 11:33:35');");
		TestUtility::query("INSERT INTO `Ticket` VALUES (359662,'R','newdomain2.ie',NULL,'Holder2',NULL,00000101,NULL,'Body Corporate (Ltd,PLC,Company)','Corporate Name','wewe','AGZ082-IEDR',NULL,NULL,NULL,'TDI2-IEDR',NULL,'IDL2-IEDR',NULL,'IDL2-IEDR','ns.irishdomains.net',NULL,NULL,NULL,'ns2.irishdomains.net',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2012-10-10',0,'2012-10-10','N',NULL,NULL,'2012-10-10','2012-10-10 11:33:35','',NULL,NULL,'2012-10-10 13:33:35','N','N',1,NULL,0,'2012-10-10 11:33:35',0,'2012-10-10 11:33:35');");
		TestUtility::query("INSERT INTO `Ticket` VALUES (359663,'R','newdomain3.ie',NULL,'Holder3',NULL,00000101,NULL,'Body Corporate (Ltd,PLC,Company)','Corporate Name','wewe','AGZ082-IEDR',NULL,NULL,NULL,'TDI2-IEDR',NULL,'IDL2-IEDR',NULL,'IDL2-IEDR','ns.irishdomains.net',NULL,NULL,NULL,'ns2.irishdomains.net',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2012-10-10',0,'2012-10-10','N',NULL,NULL,'2012-10-10','2012-10-10 11:33:35','',NULL,NULL,'2012-10-10 13:33:35','N','N',1,NULL,0,'2012-10-10 11:33:35',0,'2012-10-10 11:33:35');");
		TestUtility::query("INSERT INTO `Ticket` VALUES (359664,'R','newdomain4.ie',NULL,'Holder4',NULL,00000101,NULL,'Body Corporate (Ltd,PLC,Company)','Corporate Name','wewe','AGZ082-IEDR',NULL,NULL,NULL,'TDI2-IEDR',NULL,'IDL2-IEDR',NULL,'IDL2-IEDR','ns.irishdomains.net',NULL,NULL,NULL,'ns2.irishdomains.net',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2012-10-10',0,'2012-10-10','N',NULL,NULL,'2012-10-10','2012-10-10 11:33:35','',NULL,NULL,'2012-10-10 13:33:35','N','N',1,NULL,0,'2012-10-10 11:33:35',0,'2012-10-10 11:33:35');");
		TestUtility::query("INSERT INTO `Ticket` VALUES (359665,'R','newdomain5.ie',NULL,'Holder5',NULL,00000101,NULL,'Body Corporate (Ltd,PLC,Company)','Corporate Name','wewe','AGZ082-IEDR',NULL,NULL,NULL,'TDI2-IEDR',NULL,'IDL2-IEDR',NULL,'IDL2-IEDR','ns.irishdomains.net',NULL,NULL,NULL,'ns2.irishdomains.net',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2012-10-10',0,'2012-10-10','N',NULL,NULL,'2012-10-10','2012-10-10 11:33:35','',NULL,NULL,'2012-10-10 13:33:35','N','N',1,NULL,0,'2012-10-10 11:33:35',0,'2012-10-10 11:33:35');");
		TestUtility::query("INSERT INTO `Ticket` VALUES (359666,'R','newdomain6.ie',NULL,'Holder6',NULL,00000101,NULL,'Body Corporate (Ltd,PLC,Company)','Corporate Name','wewe','AGZ082-IEDR',NULL,NULL,NULL,'TDI2-IEDR',NULL,'IDL2-IEDR',NULL,'IDL2-IEDR','ns.irishdomains.net',NULL,NULL,NULL,'ns2.irishdomains.net',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2012-10-10',0,'2012-10-10','N',NULL,NULL,'2012-10-10','2012-10-10 11:33:35','',NULL,NULL,'2012-10-10 13:33:35','N','N',1,NULL,0,'2012-10-10 11:33:35',0,'2012-10-10 11:33:35');");
	}

	public static function tearDownAfterClass() {
		TestUtility::query("DELETE FROM Ticket WHERE T_Number > 0");
	}

	public function testTicketAllView() {
		$this->login();
		$this->click("link=Tickets");
		$this->waitForPageToLoad("30000");
		TestUtility::equalsContent("View 1 - 6 of 6", $this->getText("css=div.ui-paging-info"));
		$this->logout();
	}

	public function testGridOnTicket() {
		$this->login();
		$this->click("link=Tickets");
		$this->waitForPageToLoad("30000");
		TestUtility::equalsContent("View 1 - 6 of 6", $this->getText("css=div.ui-paging-info"));
			
		//searchText on field 'DomainName'
		TestUtility::searchText("newdomain1.ie", "id=gs_B");
		TestUtility::equalsCount(1, "On field id=gs_B");
		TestUtility::cleanField("id=gs_B");
			
		//searchText on field 'Holder'
		TestUtility::searchText("Holder1", "id=gs_C");
		TestUtility::equalsCount(1, "On field id=gs_C");
		TestUtility::cleanField("id=gs_C");
			
		$this->logout();
	}

	public function testViewOneTicket() {
		$this->login();
		$this->click("link=Tickets");
		$this->waitForPageToLoad("30000");

		$this->open("/index.php?r=tickets/viewticket&id=359661");
		$this->click("link=Edit Ticket");
		$this->waitForPageToLoad("30000");
		$this->select("id=EditTicketModel_applicant", "label=Discretionary Name");
		$this->type("id=EditTicketModel_operation_domainHolderField_newValue", "Holder10");
		$this->type("id=EditTicketModel_requestersRemark", "wewewe");
		$this->type("id=EditTicketModel_operation_nameservers_0_name_newValue", "ns6.irishdomains.net");
		$this->type("id=EditTicketModel_operation_nameservers_1_name_newValue", "ns7.irishdomains.net");
		$this->type("id=EditTicketModel_operation_adminContactsField_1_newValue_nicHandle", "AGZ082-IEDR");
		$this->click("name=yt0");
		$this->waitForPageToLoad("30000");

		$this->click("link=Edit Ticket");
		$this->waitForPageToLoad("30000");
		TestUtility::equalsContent("Holder10", $this->getValue("id=EditTicketModel_operation_domainHolderField_newValue"));
		TestUtility::equalsContent("wewewe", $this->getValue("id=EditTicketModel_requestersRemark"));
		TestUtility::equalsContent("ns6.irishdomains.net", $this->getValue("id=EditTicketModel_operation_nameservers_0_name_newValue"));
		TestUtility::equalsContent("ns7.irishdomains.net", $this->getValue("id=EditTicketModel_operation_nameservers_1_name_newValue"));
		TestUtility::equalsContent("AGZ082-IEDR", $this->getValue("id=EditTicketModel_operation_adminContactsField_1_newValue_nicHandle"));
		 
		$this->logout();
	}
}

?>