<?php

require_once './SetupPhpUnit.php';

class TestTomcatSessionTest extends SetupPhpUnit {
   public function testOnStoppedTomcat() {
      $this->stopTomcat();
      $this->open("/index.php");
      $this->waitForPageToLoad("60000");
      $this->click("link=Login");
      $this->waitForPageToLoad("60000");
      TestUtility::textPresent("Could not connect to the backend server, please try again later.");
      $this->startTomcat();
      sleep(30);
   }
}

?>
