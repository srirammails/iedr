<?php

$acc=$arr["type"];
$count=$arr["count"];
$dom=$arr["dom"];
$rate=$arr["rate"];
$receipt=$arr["ReceiptsCount"];
$amt=$arr["Amt"];
$ppayment=$arr["PendingPayment"];
$Locked=$arr["D_Locked"];
$expStatus=$arr["ExpStatus"];
$vat_r=$arr["VAT"];
$tot=$arr["Total"];
$lock=$arr["LockedCount"];
$xfer=$arr["XferCount"];
$bstatus=$arr["BillStatus"];
$dstatus=$arr["DomainStatus"];
$dreg=$arr["RegDate"];
$dren=$arr["RenDate"];
$expect_ren=$arr["ExpRenDt"];


$price=$GLOBALS ["arr_results"] [$rate];
$Vat=$GLOBALS ["vat_rate"];
$priceVat=$price*$Vat;
$total=($price+$priceVat)*$count;
settype($priceVat, "string");
settype($total, "string");

if ($acc == "vat")
{
               $this->assertEquals($receipt , 0);
               $this->assertEquals($ppayment , 1);
               $this->assertEquals($amt , $GLOBALS ["arr_results"] [$rate]);
               $this->assertEquals($vat_r , $priceVat);
               $this->assertEquals($tot , $total);
               $this->assertEquals($lock , $Locked);
               $this->assertEquals($bstatus , "Y");
               $this->assertEquals($dren , $expect_ren);
               $this->assertEquals($dstatus , $expStatus);
}

if ($acc == "novat")
{
//echo "<pre>" . print_r($arr,true) . "</pre>";

               $this->assertEquals($receipt , 0);
               $this->assertEquals($ppayment , 1);
               $this->assertEquals($amt , $GLOBALS ["arr_results"] [$rate]);
               $this->assertEquals($vat_r , 0);
               $this->assertEquals($tot , $GLOBALS ["arr_results"] [$rate] * $count);
               $this->assertEquals($lock , 0);
               $this->assertEquals($bstatus , "Y");
               $this->assertEquals($dren , $expect_ren);
               $this->assertEquals($dstatus , $expStatus);
}
?>

