<?php

$dom=$arr[dom];
$count=$arr[count];
$bstatus=$arr[bstatus];
$dstatus=$arr[status];
$ren=$arr[Ren];
$Expren=$arr[expRen];

               $this->assertEquals($count , 1);
               $this->assertEquals($bstatus , M);
               $this->assertEquals($dstatus , Active);
               $this->assertEquals($ren , $Expren);
?>
