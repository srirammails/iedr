<?php

$acc=$arr[type];
$dom=$arr[dom];
$expIN=$arr[ExpInvNo];
$IN=$arr[InvNo];
$rate=$arr[rate];
$receipt=$arr[ReceiptsCount];
$amt=$arr[Amt];
$vat_r=$arr[VAT];
$lock=$arr[LockedCount];
$xfer=$arr[XferCount];
$bstatus=$arr[BillStatus];
$dstatus=$arr[DomainStatus];
$dreg=$arr[RegDate];
$dren=$arr[RenDate];
$expect_ren=$arr[ExpRenDt];

$price=$GLOBALS [arr_results] [$rate];
$Vat=$GLOBALS [vat_rate];
$priceVat=$price*$Vat;

settype($priceVat, "string");

if ($acc == vat)
{
               $this->assertEquals($receipt , 1);
               $this->assertEquals($amt , $GLOBALS [arr_results] [$rate]);
               $this->assertEquals($vat_r , $priceVat);
               //$this->assertEquals($tot , ($GLOBALS [arr_results] [$rate])+($GLOBALS [arr_results] [$rate]*$GLOBALS [arr_results] [vat_rate]));
               $this->assertEquals($lock , 0);
               $this->assertEquals($bstatus , Y);
               $this->assertEquals($dren , $expect_ren);
               $this->assertEquals($dstatus , Active);
               $this->assertEquals($expIN , $IN);
}

if ($acc == novat)
{
//echo "<pre>" . print_r($arr,true) . "</pre>";

               $this->assertEquals($receipt , 1);
               $this->assertEquals($amt , $GLOBALS [arr_results] [$rate]);
               $this->assertEquals($vat_r , 0);
               //$this->assertEquals($tot , ($GLOBALS [arr_results] [$rate] * $count)+($GLOBALS [arr_results] [$rate]*$GLOBALS [arr_results] [vatrate]));
               $this->assertEquals($lock , 0);
               $this->assertEquals($bstatus , Y);
               $this->assertEquals($dren , $expect_ren);
               $this->assertEquals($dstatus , Active);
               $this->assertEquals($expIN , $IN);
}
?>

