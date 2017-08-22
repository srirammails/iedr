<?php

$dom=$arr[D_Name];
$type=$arr[T_Type];
$owner=$arr[Bill_NH];
$remark=$arr[T_Remark];
$holder=$arr[D_Holder];
$class=$arr[T_Class];
$cat=$arr[T_Category];
$ren=$arr[T_Ren_Dt];
$admin=$arr[Admin_NH1];
$tech=$arr[Tech_NH];
$ns1=$arr[DNS_Name1];
$ns2=$arr[DNS_Name2];
$ns3=$arr[DNS_Name3];
$ip1=$arr[DNS_IP1];
$ip2=$arr[DNS_IP2];
$ip3=$arr[DNS_IP3];
$checkout=$arr[CheckedOut];
$expren=$arr[Ren];

               $this->assertEquals($type, 'R');
               $this->assertEquals($owner, 'ABC1-IEDR');
               $this->assertEquals($holder, 'Holder name here');
               $this->assertEquals($remark, 'remarks');
               $this->assertEquals($class, 'Body Corporate (Ltd,PLC,Company)');
               $this->assertEquals($cat, 'Registered Trade Mark Name');
               $this->assertEquals($ren, $expren);
               $this->assertEquals($admin, 'ABC1-IEDR');
               $this->assertEquals($tech, 'ABC1-IEDR');
               $this->assertEquals($ns1, 'ns1.abcdefault.ie');
               $this->assertEquals($ns2, 'ns1.abc2default.ie');
               $this->assertEquals($ip1, NULL);
               $this->assertEquals($ip2, NULL);
               $this->assertEquals($ip3, NULL);
               $this->assertEquals($checkout, 'N');

?>
