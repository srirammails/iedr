<?php


$dom=$arr["dom"];
$bstatus=$arr["bstatus"];
$dstatus=$arr["status"];
$ren=$arr["Ren"];
$Expren=$arr["expRen"];

               $this->assertEquals($bstatus , "M");
               $this->assertEquals($dstatus , "Active");
               $this->assertEquals($ren , $Expren);
?>
