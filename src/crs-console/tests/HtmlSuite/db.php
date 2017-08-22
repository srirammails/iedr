<?php

// database operations

// #######################

// dummy classes to enable unmodified reuse of JM's db class
class dbConnComponent
	{
	public $connectionString;
	public $username;
	public $password;
	public function dbConnComponent() {
		global $db_host,$db_user,$db_pass,$db_schema;
		$this->connectionString = 'mysql:host='.$db_host.';dbname='.$db_schema;
		$this->username = $db_user;
		$this->password = $db_pass;
		}
	}
class yiiApp
	{
	public function getComponent($componentname) {
		return new dbConnComponent();
		}
	}
class Yii
	{
	public function app() {
		return new yiiApp();
		}
	public static function log($msg,$typ,$src) {
		//echo "$src : $typ : $msg\n";
		}
	}
require_once('/var/www/html/newregcon/protected/db/db.php');

// #######################

function getNewRegDaysCounts($account_number)
	{
	// return an array of test values for "new domains" report
	$arr = array();
	$d = DB::get_db();
	$d->query('select D_Reg_Dt, datediff(now(),D_Reg_Dt) as daysago, count(*) as num from Domain where A_Number='.$account_number.' group by 1 order by 1 desc limit 10;',__FILE__,__LINE__);
	$totnum = 0;
	while($r = $d->fetch())
		{
		$totnum += $r->num;
		$arr[$r->daysago] = $totnum;
		}
	return $arr;
	}


function getDNS0($domain1, $order)
        {
        $d = DB::get_db();
        $d->query("select DNS_Name as n from DNS where D_Name IN ('".$domain1."') and DNS_Order=$order;",__FILE__,__LINE__);
	if($r = $d->fetch()) $dns = $r->n;
        return $dns;
        }

function getDNS_IP0($domain1, $order)
        {
        $d = DB::get_db();
        $d->query("select DNS_IpAddr as n from DNS where D_Name IN ('".$domain1."') and DNS_Order=$order;",__FILE__,__LINE__);
        if($r = $d->fetch()) $dnsip = $r->n;
        return $dnsip;
        }

function getInvMonthCounts($test_user_nichandle, $table)
       {
       // return an array of test values for invoice report
       $arr = array();
       $d = DB::get_db();
   $qry = 'select DATE_FORMAT(I_Inv_Dt, \'%M\') as month, DATE_FORMAT(I_Inv_Dt, \'%Y\') as year, Count(I_Dname) as num from '.$table.' where I_Bill_NH=\''.$test_user_nichandle.'\' and I_Inv_Dt >= CURDATE() - Interval 12 Month group by month;';
   #echo $qry.BR;
       $d->query($qry,__FILE__,__LINE__);
       $totnum = 0;
       while($r = $d->fetch())
               {
               $totnum += $r->num;
               $arr[$r->year][$r->month] = $r->num;
               }
       $arr['TOTAL'] = $totnum;
       return $arr;
   }

function getRCInvMonthCounts($test_user_nichandle, $table, $type)
       {
       // return an array of test values for invoice report
       $arr = array();
       $d = DB::get_db();
   $qry = 'select I_DName as domain, DATE_FORMAT(I_Inv_Dt, \'%M\') as month, DATE_FORMAT(I_Inv_Dt, \'%Y\') as year, Count(I_Dname) as num from '.$table.' where I_Bill_NH=\''.$test_user_nichandle.'\' and I_Inv_Dt >= CURDATE() - Interval 12 Month and I_Type= \''.$type.'\' group by month Order by RAND();';
   #echo $qry.BR;
       $d->query($qry,__FILE__,__LINE__);
       $totnum = 0;
       while($r = $d->fetch())
               {
               $totnum += $r->num;
               $arr[$r->year][$r->month][$r->domain] = $r->num;
               }
       $arr['TOTAL'] = $totnum;
       return $arr;
   }

function getDomainCountInStatus($account_number, $status)
	{
	// return an array of test values for "new domains" report
	$count = 0;
	$d = DB::get_db();
	$d->query('select count(*) as n from Domain where A_Number='.$account_number.' and D_Bill_Status=('."'".$status."'".');',__FILE__,__LINE__);
	if($r = $d->fetch()) $count = $r->n;
	return $count;
	}

function getDomainCharityCounts($account_number)
	{
	return getDomainCountInStatus($account_number,'C');
	}

function getDepositBalance($account_number)
        {
	$balance = 0;
        $d = DB::get_db();
        $d->query('select Close_Bal as n from Deposit d, Account a where a.Billing_NH=d.Nic_Handle and A_Number='.$account_number.';',__FILE__,__LINE__);
	if($r = $d->fetch()) $balance = $r->n;
        return $balance;
 }

function getLockStatus($domain1)
        {
        $lock = 0;
        $d = DB::get_db();
        $d->query("select Count(D_Name) as n from D_Locked where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $lock = $r->n;
        return $lock;
 }

function getRenDate($domain1, $period)
        {
        $renDate = 0;
        $d = DB::get_db();
        $d->query("select D_Ren_Dt + Interval ".$period." YEAR as n from Domain where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $renDate = $r->n;
        return $renDate;
 }



function getDomainStatus($domain1)
        {
        // return value of deposit balance before a transaction for comparison after transaction
        $status = 0;
        $d = DB::get_db();
        $d->query("select DISTINCT D_Status as n from Domain where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $status = $r->n;
        return $status;
 }

function getBillStatus($domain1)
        {
        // return value of deposit balance before a transaction for comparison after transaction
        $billStatus = 0;
        $d = DB::get_db();
        $d->query("select DISTINCT DetailedDescription as n from Domain, BillStatus where D_NAME IN ('".$domain1."') and Domain.D_Bill_Status=Description;",__FILE__,__LINE__);
        if($r = $d->fetch()) $billStatus = $r->n;
        return $billStatus;
 }


function getReceiptCount($domain1)
        {
        $receipt = 0;
        $d = DB::get_db();
        $d->query("select Count(D_Name) as n from Receipts where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $receipt = $r->n;
        return $receipt;
 }

function getMailList($domain1)
        {
        $maillist = 0;
        $d = DB::get_db();
        $d->query("select Count(D_Name) as n from MailList where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $maillist = $r->n;
        return $maillist;
 }


function getPendingPaymentCount($domain1)
        {
        $receipt = 0;
        $d = DB::get_db();
        $d->query("select Count(D_Name) as n from PendingPayment where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $ppayment = $r->n;
        return $ppayment;
 }


function getPrice($period, $type)
        {
        $price = 0;
        $d = DB::get_db();
        $d->query('select P_Price as n from Product where P_Guest=0 and P_Reg='.$type.' and P_Duration='.$period.' and CURDATE() BETWEEN P_Valid_From and P_Valid_To;',__FILE__,__LINE__);
        if($r = $d->fetch()) $price = $r->n;
        return $price;
        }

function getVatRate($account_number)
        {
        $vatrate = 0;
        $d = DB::get_db();
        $d->query('select VatRate as n from StaticTableName;',__FILE__,__LINE__);
        if($r = $d->fetch()) $vatrate = $r->n;
        return $vatrate;
        }

function getTicketCount($account_number)
        {
        $ticket = 0;
        $d = DB::get_db();
        $d->query('select Count(D_Name) as n from Ticket where A_Number='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $ticket = $r->n;
        return $ticket;
        }

function getTicketPassCount($account_number)
        {
        $ticketp = 0;
        $d = DB::get_db();
        $d->query('select Count(D_Name) as n from Ticket where Admin_Status=1 AND A_Number='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $ticketp = $r->n;
        return $ticketp;
        }
function getTicketPendingCount($account_number)
        {
        $ticketh = 0;
        $d = DB::get_db();
        $d->query('select Count(D_Name) as n from Ticket where A_Number='.$account_number.' and Admin_Status!=1;',__FILE__,__LINE__);
        if($r = $d->fetch()) $ticketh = $r->n;
        return $ticketh;
        }

function getDomainCount($account_number)
        {
        $domaincount = 0;
        $d = DB::get_db();
        $d->query('select Count(D_Name) as n from Domain where A_Number='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $domaincount = $r->n;
        return $domaincount;
        }
function getXfersBillableCount($test_user_nichandle)
        {
        $xferin = 0;
        $d = DB::get_db();
        $d->query('SELECT Count(*) as n FROM   Domain, Transfers LEFT JOIN RCInvoiceHist ON Transfers.D_Name=RCInvoiceHist.I_DName AND RCInvoiceHist.I_Inv_Dt > CURDATE() - Interval 112 DAY AND RCInvoiceHist.I_Bill_NH=\''.$test_user_nichandle.'\'       LEFT JOIN Receipts ON Transfers.D_Name=Receipts.D_Name AND Receipts.Nic_Handle=\''.$test_user_nichandle.'\' LEFT JOIN PendingPayment ON Transfers.D_Name=PendingPayment.D_Name LEFT JOIN MailList ON Transfers.D_Name = MailList.D_Name LEFT JOIN SuspendList ON Transfers.D_Name = SuspendList.D_Name LEFT JOIN DeleteList ON Transfers.D_Name = DeleteList.D_Name WHERE RCInvoiceHist.I_DName IS NULL AND Receipts.D_Name IS NULL AND PendingPayment.D_Name IS NULL         AND MailList.D_Name IS NULL AND SuspendList.D_Name IS NULL AND DeleteList.D_Name IS NULL AND Transfers.D_Name = Domain.D_Name AND D_Bill_Status != \'C\' AND New_Nic_Handle=\''.$test_user_nichandle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $xferbill = $r->n;
        return $xferbill;
        }
function getXfersInCount($test_user_nichandle)
        {
        $xferin = 0;
        $d = DB::get_db();
        $d->query('select Count(D_Name) as n from Transfers where New_Nic_Handle=\''.$test_user_nichandle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $xferin = $r->n;
        return $xferin;
        }
function getXfersOutCount($test_user_nichandle)
        {
        $xferout = 0;
        $d = DB::get_db();
        $d->query('select Count(D_Name) as n from Transfers where Old_Nic_Handle=\''.$test_user_nichandle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $xferout = $r->n;
        return $xferout;
        }
function getNewRegCount($account_number)
        {
        // return an array of test values for "new domains" report
        $nrcount = 0;
        $d = DB::get_db();
        $d->query('select count(D_Name) as n from Domain where A_Number='.$account_number.' and D_Reg_Dt BETWEEN DATE_FORMAT(CURDATE(), \'%Y-%m-01\') and LAST_DAY(CURDATE());',__FILE__,__LINE__);
        if($r = $d->fetch()) $nrcount = $r->n;
        return $nrcount;
        }
function getStatementBalance($test_user_nichandle)
        {
        $state = 0;
        $d = DB::get_db();
        $d->query('Select Total_Bal as x from Statement where Nic_Handle=\''.$test_user_nichandle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $state = $r->x;
        return $state;
        }
function getRegDate($domain1)
        {
        // return reg date of domain
        $regDate = 0;
        $d = DB::get_db();
        $d->query("select D_Reg_Dt as n from Domain where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $regDate = $r->n;
        return $regDate;
 }
function getRenewDate($domain1)
        {
        // return renew date of domain
        $renewDate = 0;
        $d = DB::get_db();
        $d->query("select D_Ren_Dt as n from Domain where D_NAME IN ('".$domain1."');",__FILE__,__LINE__);
        if($r = $d->fetch()) $renewDate = $r->n;
        return $renewDate;
 }

function getTransfersDaysCounts($test_user_nichandle)
        {
        // return an array of test values for "new Trasnfers In" report
        $arr = array();
        $d = DB::get_db();
        $d->query('select Tr_Date, datediff(now(),Tr_Date) as daysago, count(*) as num from Transfers where New_Nic_Handle=\''.$test_user_nichandle.'\' group by 1 order by 1 desc limit 10;',__FILE__,__LINE__);
        $totnum = 0;
        while($r = $d->fetch())
                {
                $totnum += $r->num;
                $arr[$r->daysago] = $totnum;
                }
        return $arr;
        }


function getTransfersOutDaysCounts($test_user_nichandle)
        {
        // return an array of test values for "Transfers Out" report
        $arr = array();
        $d = DB::get_db();
        $d->query('select Tr_Date, datediff(now(),Tr_Date) as daysago, count(*) as num from Transfers where Old_Nic_Handle=\''.$test_user_nichandle.'\' group by 1 order by 1 desc limit 10;',__FILE__,__LINE__);
        $totnum = 0;
        while($r = $d->fetch())
                {
                $totnum += $r->num;
                $arr[$r->daysago] = $totnum;
                }
        return $arr;
        }


function getRenInvLIVECount($account_number, $table)
        {
        // return invoice values
        $arr = array();
        $d = DB::get_db();  
      $d->query('select I_Inv_No as a,         COUNT(*) as b,         SUM(I_Amount) as c,         SUM(I_Inv_Vat_Amt) as d  from Domain, '.$table.' LEFT JOIN Receipts ON I_DName=Receipts.D_Name         LEFT JOIN PendingPayment ON I_DName=PendingPayment.D_Name         LEFT JOIN Transfers ON I_DName=Transfers.D_Name         LEFT JOIN MailList ON I_DName=MailList.D_Name         LEFT JOIN SuspendList ON I_DName=SuspendList.D_Name         LEFT JOIN DeleteList ON I_DName=DeleteList.D_Name        where        Receipts.D_Name IS NULL         AND         PendingPayment.D_Name IS NULL         AND         Transfers.D_Name IS NULL         AND         MailList.D_Name IS NULL         AND         SuspendList.D_Name IS NULL         AND         DeleteList.D_Name IS NULL AND I_Inv_DT = LAST_DAY(CURDATE() - Interval 1 Month) and I_Acc_No='.$account_number.' AND I_DName=Domain.D_Name and D_Bill_Status=\'Y\';',__FILE__,__LINE__);
        
	$invnum = 0;
        while($r = $d->fetch())
                {
                $invnum += $r->a;
                $arr[num] = $r->a;
                $arr[cnt] = $r->b;
                $arr[amt] = $r->c;
                $arr[vat] = $r->d;
                }
        return $arr;
 }




function getRenInvCount($account_number, $table)
        {
        // return renew date of domain
        $invcnt = 0;
        $d = DB::get_db();
        $d->query('select Count(I_DName) as n from '.$table.' where I_Inv_DT = LAST_DAY(CURDATE() - Interval 1 Month) and I_Acc_No='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invcnt = $r->n;
        return $invcnt;
 }
function getRenInvAmount($account_number, $table)
        {
        // return renew date of domain
        $invamt = 0;
        $d = DB::get_db();
        $d->query('select SUM(I_Amount) as n from '.$table.' where I_Inv_DT = LAST_DAY(CURDATE() - Interval 1 Month) and I_Acc_No='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invamt = $r->n;
        return $invamt;
 }
function getRenInvVat($account_number, $table)
        {
        // return renew date of domain
        $invvat = 0;
        $d = DB::get_db();
        $d->query('select SUM(I_Inv_Vat_Amt) as n from '.$table.' where I_Inv_DT = LAST_DAY(CURDATE() - Interval 1 Month) and I_Acc_No='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invvat = $r->n;
        return $invvat;
 }
function getRenInvTot($account_number, $table)
        {
        // return renew date of domain
        $invtot = 0;
        $d = DB::get_db();
        $d->query('select SUM(I_Inv_Vat_Amt+I_Amount) as n from '.$table.' where I_Inv_DT = LAST_DAY(CURDATE() - Interval 1 Month) and I_Acc_No='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invtot = $r->n;
        return $invtot;
 }

function getRenInvNo($account_number, $table)
        {
        // return renew date of domain
        $invno = 0;
        $d = DB::get_db();
        $d->query('select DISTINCT I_Inv_No  as n from '.$table.' where I_Inv_DT = LAST_DAY(CURDATE() - Interval 1 Month) and I_Acc_No='.$account_number.';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invno = $r->n;
        return $invno;
 }


function getRCInvLIVECount($account_number, $table)
        {
        // return invoice values
        $arr = array();
        $d = DB::get_db();
      $d->query('select I_Inv_No as a,         COUNT(*) as b,         SUM(I_Amount) as c,         SUM(I_Inv_Vat) as d, SUM(I_Batch_Total) as e  from Domain, '.$table.' LEFT JOIN Receipts ON I_DName=Receipts.D_Name         LEFT JOIN PendingPayment ON I_DName=PendingPayment.D_Name         LEFT JOIN Transfers ON I_DName=Transfers.D_Name         LEFT JOIN MailList ON I_DName=MailList.D_Name         LEFT JOIN SuspendList ON I_DName=SuspendList.D_Name         LEFT JOIN DeleteList ON I_DName=DeleteList.D_Name        where        Receipts.D_Name IS NULL         AND         PendingPayment.D_Name IS NULL         AND         Transfers.D_Name IS NULL         AND         MailList.D_Name IS NULL         AND         SuspendList.D_Name IS NULL         AND         DeleteList.D_Name IS NULL AND I_Inv_DT = LAST_DAY(CURDATE() - Interval 1 Month) and I_Bill_Nh=\''.$account_number.'\' AND I_DName=Domain.D_Name AND I_Type=\'Xfer\' AND D_Bill_Status=\'Y\';',__FILE__,__LINE__);

        $invnum = 0;
        while($r = $d->fetch())
                {
                $invnum += $r->a;
                $arr[num] = $r->a;
                $arr[cnt] = $r->b;
                $arr[amt] = $r->c;
                $arr[vat] = $r->d;
                $arr[tot] = $r->e;
                }
        return $arr;
 }






function getRCInvCount($nic_handle, $table, $type)
        {
    
        $invcnt = 0;
        $d = DB::get_db();
        $d->query('select Count(I_DName) as n from '.$table.' where I_Inv_DT BETWEEN DATE_FORMAT(CURDATE() - Interval 1 Month, \'%Y-%m-01\') AND LAST_DAY(CURDATE() - Interval 1 Month) and I_Type= \''.$type.'\' AND I_Bill_NH=\''.$nic_handle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invcnt = $r->n;
        return $invcnt;
 }
function getRCInvTot($nic_handle, $table, $type)
        {
       
        $invtot = 0;
        $d = DB::get_db();
        $d->query('select SUM(I_Batch_Total) as n from '.$table.' where I_Inv_DT BETWEEN DATE_FORMAT(CURDATE() - Interval 1 Month, \'%Y-%m-01\') AND LAST_DAY(CURDATE() - Interval 1 Month) and I_Type= \''.$type.'\' and I_Bill_NH=\''.$nic_handle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invtot = $r->n;
        return $invtot;
 }

function getRCInvNo($nic_handle, $table, $type)
        {
      
        $invno = 0;
        $d = DB::get_db();
        $d->query('select DISTINCT I_Inv_No  as n from '.$table.' where I_Inv_DT BETWEEN DATE_FORMAT(CURDATE() - Interval 1 Month, \'%Y-%m-01\') AND LAST_DAY(CURDATE() - Interval 1 Month) and I_Type= \''.$type.'\' and I_Bill_NH=\''.$nic_handle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invno = $r->n;
        return $invno;
 }


function getRCInvAmt($nic_handle, $table, $type)
        {
      
        $invamt = 0;
        $d = DB::get_db();
        $d->query('select SUM(I_Amount)  as n from '.$table.' where I_Inv_DT BETWEEN DATE_FORMAT(CURDATE() - Interval 1 Month, \'%Y-%m-01\') AND LAST_DAY(CURDATE() - Interval 1 Month) and I_Type= \''.$type.'\' and I_Bill_NH=\''.$nic_handle.'\';',__FILE__,__LINE__);
        if($r = $d->fetch()) $invamt = $r->n;
        return $invamt;
 }




