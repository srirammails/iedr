<?php include('test_config.php'); test_header('Accounts History - Renewal Invoice'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_history_invoices/renewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>

<?php

$count = getRenInvCount($test_user_accountid, InvoiceHist);
$amt = getRenInvAmount($test_user_accountid, InvoiceHist);
$invamt=number_format($amt,2); 
$vat = getRenInvVat($test_user_accountid, InvoiceHist);
$invvat=number_format($vat,2); 
$tot = getRenInvTot($test_user_accountid, InvoiceHist);
$invtot=number_format($tot,2); 
$invno = getRenInvNo($test_user_accountid, InvoiceHist);


?>
<tr><td>assertText</td><td>//div[@id='content']/div[1]/p</td><td>There were <?php echo $count; ?> domains included in the invoice you selected.</td></tr>
<tr><td>assertTextPresent</td><td></td><td>View 1 - 15 of <?php echo $count; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[1]</td><td><?php echo $invno; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[2]</td><td>€ <?php echo $invamt; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[3]</td><td>€ <?php echo $invvat; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[4]</td><td>€ <?php echo $invtot; ?></td></tr>


<?php test_footer(); ?>
