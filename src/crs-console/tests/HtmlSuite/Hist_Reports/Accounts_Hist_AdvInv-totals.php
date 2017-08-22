<?php include('test_config.php'); test_header('Accounts History - Transfer Invoice'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>

<?php

$table='RCInvoiceHist';
$type='Advance';


$arr = getRCInvMonthCounts($test_user_nichandle, $table, $type);


foreach($arr as $year => $montharr)
       {
   if($year!='TOTAL')
       {
       foreach($montharr as $month => $domainarr)
{
       foreach($domainarr as $domain => $count)

if($count < 15){ $records = $count; }
        else {$records='15';}

?>
<!-- Open the report and enter the year and month to test-->
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_history_invoices/payments</td><td></td></tr>
<tr><td>select</td><td>Rpts_nrc_adv_pay_inv_histModel_month</td><td>label=<?php echo $month; ?></td></tr>
<tr><td>select</td><td>Rpts_nrc_adv_pay_inv_histModel_year</td><td>label=<?php echo $year; ?></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>

<!-- verify that the correct number of records are shown in the report-->
<tr><td>waitForText</td><td>//td[@id='thisJqGridPager_right']/div</td><td>View 1 - <?php echo $records; ?> of <?php echo $count; ?></td></tr>
<tr><td>assertTextPresent</td><td></td><td>View 1 - <?php echo $records; ?> of <?php echo $count; ?></td></tr>

<!-- a random domain name is choosen using MYSQL RAND() from the valid data, check that this domain name is in the resultset displayed and verify the domain view -->
<tr><td>click</td><td>css=div.ui-pg-div &gt; span.ui-icon.ui-icon-search</td><td></td></tr>
<tr><td>type</td><td>jqg1</td><td><?php echo $domain; ?></td></tr>
<tr><td>click</td><td>fbox_thisJqGrid_search</td><td></td></tr>
<tr><td>clickAndWait</td><td>link=<?php echo $domain; ?></td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $domain; ?></td></tr><tr>

<?php
}
}}
?>

<?php test_footer(); ?>
