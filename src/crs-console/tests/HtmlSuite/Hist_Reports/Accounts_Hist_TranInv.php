<?php include('test_config.php'); test_header('Accounts History - Transfer Invoice'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_history_invoices/transfers</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>

<?php

$arr = getRCInvMonthCounts($test_user_nichandle, RCInvoiceHist, Xfer);
foreach($arr as $year => $montharr)
       {
   if($year!='TOTAL')
       {
       foreach($montharr as $month => $count)
{
if($count < 15){ $records = $count; }
        else {$records='15';}

?>
<tr><td>select</td><td>Rpts_nrc_xfer_inv_histModel_month</td><td>label=<?php echo $month; ?></td></tr>
<tr><td>select</td><td>Rpts_nrc_xfer_inv_histModel_year</td><td>label=<?php echo $year; ?></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForText</td><td>//td[@id='thisJqGridPager_right']/div</td><td>View 1 - <?php echo $records; ?> of <?php echo $count; ?></td></tr>
<tr><td>assertTextPresent</td><td></td><td>View 1 - <?php echo $records; ?> of <?php echo $count; ?></td></tr>

<?php
}}                }
?>

<?php test_footer(); ?>
