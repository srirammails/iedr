<?php
include_once ("testSuite/db.inc");
$acc='600';
$nic='ABC1-IEDR';

//GET PRODUCT RATES
//Query
$query="Select P_Code, P_Price from Product where P_Guest=0 and NOW() BETWEEN P_VALID_FROM and P_VALID_TO and P_Active=1";
    //Connect to the DB
    $connection = @ mysql_connect($hostName,
                                $username,
                                $password)
    or die("Cannot connect 1");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";

$arr_results = array();

for ($rowCounter = 0; 
	$row = @ mysql_fetch_array($result); 
	$rowCounter++){
   
	$arr_results[$row["P_Code"]]=$row["P_Price"];
}



//SET CURRENT VAT RATE 
//Query
        $query ="Select VatRate from StaticTableName";

    //Connect to the DB
    $connection = @ mysql_connect($hostName,
                               $username,
                                $password)
    or die("Cannot connect 2");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";

for ($rowCounter = 0;
           $row = @ mysql_fetch_array($result);
           $rowCounter++)
{

$vat_rate = $row["VatRate"];
return $vat_rate;
    }


function getTicketStatus($dom, $period)
        {

$query="select D_Name, T_Type, D_Holder, T_Class, T_Category, DATE_FORMAT(T_Ren_Dt, '%Y') as T_Ren_Dt, CheckedOut, Year(CURDATE() + Interval $period Year) as Ren, T_Remark, DNS_Name1, DNS_Name2, DNS_Name3, DNS_IP1, DNS_IP2, DNS_IP3, Admin_NH1, Tech_NH, Bill_NH from Ticket where D_Name='$dom'";

//Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
    echo "Error: No value found";

$tic_arr = array();
$tic_arr = @ mysql_fetch_array($result);
        return $tic_arr;
 }



function getAllDomainsCount($acc)
        {
$query="select D_Reg_Dt, datediff(now(),D_Reg_Dt) as daysago, count(*) as num from Domain where A_Number='$acc' group by 1 order by 1 desc limit 10";
//Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
    echo "error: no value found";

$arr = array();
$arr = @ mysql_fetch_array($result);
        return $arr;
 }


function getDomainStatus($dom)
        {
$query="Select D_Name as domain, D_Ren_Dt as renDate, D_Reg_Dt as regDate, D_Status as status from Domain where D_Name='$dom'";
//Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect x");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";

$dom_arr = array();
$dom_arr = @ mysql_fetch_array($result);
        return $dom_arr;
 }

function getPayOfflineStatus($dom, $rate, $type, $count)
        {
$query="Select dn.D_Name as dom, '$count' as count, CASE WHEN D_Locked = 'Y' THEN 1 ELSE 0 end as D_Locked, Domain_Status as ExpStatus,  '$rate' as rate, '$type' as type, Count(p.D_Name) as PendingPayment, Count(r.D_Name) as ReceiptsCount, p.Amount as Amt, p.VAT as VAT,p.Batch_Total as Total, Count(d.D_Name) as LockedCount, Count(t.D_Name) as XferCount, D_Bill_Status as BillStatus, D_Status as DomainStatus, dn.D_Reg_Dt as RegDate, dn.D_Ren_Dt as RenDate, New_D_Ren_Dt as ExpRenDt from Domain dn left join example_data e on e.D_Name=dn.D_Name  left join Receipts r on dn.D_Name = r.D_Name left join D_Locked d on dn.D_Name = d.D_Name left join PendingPayment as p on dn.D_Name=p.D_Name left join Transfers t on dn.D_Name = t.D_Name where dn.D_Name='$dom'";

    //Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect x");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";

$arr = array();
$arr = @ mysql_fetch_array($result);
        return $arr;
 }


function getPaymentStatus($dom, $rate, $type, $count, $inv)
        {

$query="Select dn.D_Name as dom, '$count' as count, '$rate' as rate, '$inv' as invNo, '$type' as type, Count(r.D_Name) as ReceiptsCount, r.Inv_No as inv, r.Amount as Amt, r.VAT as VAT,r.Batch_Total as Total, Count(d.D_Name) as LockedCount, Count(t.D_Name) as XferCount, D_Bill_Status as BillStatus, D_Status as DomainStatus, dn.D_Reg_Dt as RegDate, dn.D_Ren_Dt as RenDate, New_D_Ren_Dt as ExpRenDt from Domain dn left join example_data e on e.D_Name=dn.D_Name  left join Receipts r on dn.D_Name = r.D_Name left join D_Locked d on dn.D_Name = d.D_Name left join Transfers t on dn.D_Name = t.D_Name where dn.D_Name='$dom'";
    //Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect x");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";
$arr = array();
$arr = @ mysql_fetch_array($result);
        return $arr;
 }


function getMYRPaymentStatus($dom, $inv, $type)
        {

$query="Select dn.D_Name as dom, '$inv' as ExpInvNo, CASE WHEN D_Locked = 'Y' THEN 1 ELSE 0 end as D_Locked, Domain_Status as ExpStatus, P_Code as rate, '$type' as type, Count(r.D_Name) as ReceiptsCount,r.Inv_No as InvNo,  r.Amount as Amt, r.VAT as VAT, r.Batch_Total as Total, Count(d.D_Name) as LockedCount, Count(t.D_Name) as XferCount, D_Bill_Status as BillStatus, D_Status as DomainStatus, dn.D_Reg_Dt as RegDate, dn.D_Ren_Dt as RenDate, New_D_Ren_Dt as ExpRenDt from Domain dn left join example_data e on e.D_Name=dn.D_Name  left join Receipts r on dn.D_Name = r.D_Name left join D_Locked d on dn.D_Name = d.D_Name left join Transfers t on dn.D_Name = t.D_Name left join Product on P_Price=r.Amount and P_Ren='1' where dn.D_Name='$dom'";

    //Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect x");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";
$arr = array();
$arr = @ mysql_fetch_array($result);
        return $arr;
 }



function getMSDStatus($dom, $table)        {
$query="select d.D_Name as dom, Count(m.D_Name) as count, d.D_Ren_Dt as Ren, New_D_Ren_Dt as expRen, D_Status as status, D_Bill_Status as bstatus from Domain d left join MailList m on m.D_Name=d.D_Name left join example_data e on e.D_Name=d.D_Name where d.D_Name='$dom'";

    //Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect x");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";
$arr = array();
$arr = @ mysql_fetch_array($result);
        return $arr;
 }

function getDepositValue($nic)
        {

$query="select Open_Bal as o, Close_Bal as c from Deposit where Nic_Handle='$nic'";
//Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect x");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";
$dep_arr = array();
$dep_arr = @ mysql_fetch_array($result);
        return $dep_arr;
 }


function getMSDReActiveStatus($dom)        {
$query="select d.D_Name as dom, Count(m.D_Name) as Mcount, Count(s.D_Name) as Scount, Count(dl.D_Name) as Dcount, d.D_Ren_Dt as Ren, New_D_Ren_Dt as expRen, D_Status as status, D_Bill_Status as bstatus from Domain d left join DeleteList dl on dl.D_Name=d.D_Name left join SuspendList s on s.D_Name=d.D_Name left join MailList m on m.D_Name=d.D_Name  left join example_data e on e.D_Name=d.D_Name where d.D_Name='$dom'";

    //Connect to the DB
    $connection = @ mysql_connect($GLOBALS [hostName],
                                $GLOBALS [username],
                                $GLOBALS [password])
    or die("Cannot connect x");
    //Select the DB
    mysql_select_db("phoenixdb", $connection);

    // Execute the query
    $result = mysql_query ($query, $connection)
    or showerror();

  if (mysql_num_rows($result) < 1)
 echo "error: no value found";
$arr = array();
$arr = @ mysql_fetch_array($result);
        return $arr;
 }


?>
