<?php

$dom=$arr["dom"];
$Mcount=$arr["Mcount"];
$Scount=$arr["Scount"];
$Dcount=$arr["Dcount"];
$bstatus=$arr["bstatus"];
$dstatus=$arr["status"];
$ren=$arr["Ren"];
$Expren=$arr["expRen"];

               $this->assertEquals($Mcount , 0);
               $this->assertEquals($Scount , 0);
               $this->assertEquals($Dcount , 0);
               $this->assertEquals($bstatus , "Y");
               $this->assertEquals($dstatus , "Active");
               $this->assertEquals($ren , $Expren);
?>
