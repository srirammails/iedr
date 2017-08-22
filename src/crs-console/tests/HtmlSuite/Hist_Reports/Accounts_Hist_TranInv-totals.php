<?php include('test_config.php'); test_header('Accounts History - Transfer Invoice'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_history_invoices/transfers</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>

<?php

$table='RCInvoiceHist';
$type='Xfer';

$count = getRCInvCount($test_user_nichandle, $table, $type);
$invamt = getRCInvAmt($test_user_nichandle, $table, $type);
$invtot = getRCInvTot($test_user_nichandle, $table, $type);
$invno = getRCInvNo($test_user_nichandle, $table, $type);
$invvat = number_format($invtot-$invamt);

?>
<tr><td>waitForText</td><td>//td[@id='thisJqGridPager_right']/div</td><td>View 1 - 15 of <?php echo $count; ?></td></tr>
<tr><td>assertTextPresent</td><td></td><td>View 1 - 15 of <?php echo $count; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[1]</td><td><?php echo $invno; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[2]</td><td>€ <?php echo $invamt; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[3]</td><td>€ <?php echo $invvat; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[4]</td><td>€ <?php echo $invtot; ?></td></tr>

<?php test_footer(); ?>
