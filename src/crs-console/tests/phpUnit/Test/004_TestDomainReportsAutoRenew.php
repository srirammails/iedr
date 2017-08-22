<?php

require_once './SetupPhpUnit.php';

class TestDomainReports extends SetupPhpUnit {
	private $url = "/index.php?r=domainreports/confirmaction&id=WUW%253DWBW%25253DV1pXPXic7VbbbuM2EM2nCEKx6EUbi7Lki4Kg2HoXLVBEDeJ0jaIXg5LomIBEaikqsWH43zskpZh2HCR%25252BafahfpKHM%25252BcMh8M5nP06uxwt8lG0GOZp5mfhOAvQIk3JMAhCtMD9bBxGYThEA5zOprPL32I0it2fBc2npCCZpJxd8ZwUbhyM4k0dD2M342WJWe5e1PE4dnEjuSCMPLT%25252FBZGNYI0o1P8%25252BuPcoy8nqvFpWP4rLnJeYMkEqLmTdw0VhDLVyRmHsMkLyiSA5lRMsgILGPqwMYrcignIwJMoxUjRAiYuPWJLbdUVUPHBNfr%25252B5%25252BZTcdmg5WeCmkNc6Fjhw7MebLaz5sKaJC1rLHfeDIHdgIOKcakQE%25252Bzn7%25252Fmy%25252B8%25252F0gBF4rIKSK8UzUESuQBsgmnVG5nDZQSLFWMZCQDg004T0u5qIpiE4ZbBtVBhy3H3UcRjaU1x6I91h676A6niokgsCRKtyXhkKFISdls1DtozXuqtCQC80BZ9ItQVhghx09DRMf9HfxN4cugNPf29ST4%25252Fdgj2qDc6ua84yzBRUleUwS0k45LwhmGjMEzH6HOYANZxovAzzGPdIIXs9xyRsGdQPbkhc5ER5ZVfM1weaj5Ewu9apUxcvu2dH6wf5ilzP9AY0iedVU3gOcK29kSzGvM8IwdJ9OLYLUoi41gOpIO3QAYU0JzZphc3tit6SsJUJBZ8IrFRAMT09gcJBAt8UuAbhnBWF3cmmzq%25252BO2iVF0OvHQJh6bnesqv7R1s2qRB6eTj2xygLLO87W0Y%25252FidTjy2idXVtlvxFSlEVg45DK2D3%25252BkJITVFxnZG9n14KSOTgukcyfm0hPGt1kNwuKKMlk3p3ArMaqxlw6G1807hX7xjaV1dIA%25252Biz33fPbIr03sA%25252BhO9c9sBd4VXL0L6vgWK%25252BjrxlIhrLGFU6ML0YbL2%25252FvnTfz%25252F%25252B%25252B4dv%25252FzrXHxvkBdvvNr6Htt%25252F0aCsdJalrfKcHuJpEG4AQNG0k2ToZZs4S3xOHs2LtyAfu5CSjsH2nKnBG6r1RELUHYQq%25252BN2CfawElM7up99kMTA5jYbs1agKSoNKZFzglhREFX%25252BvPEx1TE%25252FejkVRHciclzrXgkGJtBtbendcnrRid7v%25252FTBMGGWqdEl9Y92jim%25252BLe8et9Uzoed0Td8Zsjuof3yaNobhOAxaD0%25252BrSoq1s4frX1%25252FaoDfcN%25252Fvqlto8ZTm6IqER%25252Fyc3iNuNxAsz8%25252FQ%25252Bwvoft1yu22rO%25252FaCIj0r%25252FtAVRAh1pslBuyVtUCu9gtTwYql3D52ziX5%25252Fnc11vP2IGVuL913TdJFDa3E3A0yDPDsi2m6bgNZXnBEmgXWX4Z69NHal%25252Fd1DZdi9U75S%25252BUVvLb%25252FBW8lv%25252Fy3lN3wr%25252BY2%25252BNvkd%25252FK%25252B%25252B%25252F6H6bo8denK08skRrUoOh0Ty5O4kVkMnh1c6ea1iQNRU5sjo0fZfymUVcA%25253D%25253D";

	public function testConfirmOkWithOneDomain() {
		$this->login();
		$this->click("link=All Domains");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("IEDR Registrar's Console - All Domains", $this->getTitle());
		TestUtility::equalsContent("View 1 - 11 of 11", $this->getText("css=div.ui-paging-info"));

		$this->click("id=jqg_thisJqGrid_newregister.ie");
		$this->click("id=gridaction_autorenew");
		$this->assertEquals("Please Confirm you want to \"Set Auto renew\" these Domains:\nnewregister.ie,\n", $this->getConfirmation());

		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("Confirm Action");
		TestUtility::textPresent("Please Confirm you want to Auto renew these Domains:");

		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregister_confirmed"));

		$this->logout();
	}

	public function testConfirmOkWithFiveDomain() {
		$this->login();
		$this->click("link=All Domains");
		$this->waitForPageToLoad("30000");

		TestUtility::equalsContent("IEDR Registrar's Console - All Domains", $this->getTitle());
		TestUtility::equalsContent("View 1 - 11 of 11", $this->getText("css=div.ui-paging-info"));

		$this->click("id=jqg_thisJqGrid_newregister.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd1.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd2.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd3.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd4.ie");
		$this->click("id=jqg_thisJqGrid_newregisterd5.ie");

		$this->click("id=gridaction_autorenew");
		$this->assertEquals("Please Confirm you want to \"Set Auto renew\" these Domains:\nnewregister.ie,\nnewregisterd1.ie,\nnewregisterd2.ie,\nnewregisterd3.ie,\nnewregisterd4.ie,\nnewregisterd5.ie,\n", $this->getConfirmation());

		$this->waitForPageToLoad("30000");

		TestUtility::textPresent("Confirm Action");
		TestUtility::textPresent("Please Confirm you want to Auto renew these Domains:");

		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregister_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd1_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd2_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd3_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd4_confirmed"));
		$this->assertTrue($this->isChecked("GridSelectionModel_domlist_newregisterd5_confirmed"));

		$this->logout();
	}

	public function testUncheckedDomain() {
		$this->login();

		//Test unchecked domain
		$this->open($this->url);
		$this->click("id=GridSelectionModel_domlist_newregister_confirmed");
		$this->click("name=yt0");
		$this->assertEquals("You must select any domains.", $this->getAlert());

		$this->logout();
	}

	public function testForSettingAutorenew() {
		$this->login();

		//Test for setting Renew mode AutoRenew
		$this->open($this->url);
		$this->select("id=GridSelectionModel_list_newregister.ie", "label=Autorenew");
		$this->click("name=yt0");
		$this->waitForPageToLoad("70000");
		TestUtility::textPresent("2012-09-28");
		TestUtility::textPresent("2012-09-28");
		TestUtility::textPresent("newregister.ie");
		TestUtility::textPresent("Autorenew");

		$this->logout();
	}

	public function testForSettingRenewOnce() {
		$this->login();

		//Test for setting Renew mode RenewOnce
		$this->open($this->url);
		$this->select("id=GridSelectionModel_list_newregister.ie", "label=RenewOnce");
		$this->click("name=yt0");
		
		$this->waitForPageToLoad("70000");
		TestUtility::textPresent("2012-09-28");
		TestUtility::textPresent("2012-09-28");
		TestUtility::textPresent("newregister.ie");
		TestUtility::textPresent("RenewOnce");

		$this->logout();
	}
}

?>
