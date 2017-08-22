<?php

function logout($browser){
  $browser->click("link=Logout (ABC1-IEDR)");
}


   function login($browser)
  {
    $browser->open("index.php?r=site/login");
    $browser->type("LoginForm_username", "ABC1-IEDR");
    $browser->type("LoginForm_password", "password");
    $browser->type("LoginForm_verifyCode", "5d2fc08a-4975-11e0-808f-080027a1156a");
    $browser->click("yt0");
    $browser->waitForPageToLoad("30000");
  }
?>
