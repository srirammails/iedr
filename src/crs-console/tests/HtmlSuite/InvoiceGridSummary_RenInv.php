<?php include('test_config.php'); test_header('Renewal and New Registrations'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php

$arr = getRenInvLIVECount($test_user_accountid, InvoiceHist);

$invno=$arr[num];
$count=$arr[cnt];
$invvat=number_format($arr[vat],2);
$invamt=number_format($arr[amt],2);
$sum = $arr[vat] + $arr[amt];
$invtot=number_format($sum,2);

//This test is checking the validity of the values shown in the summary grid about the current invoices

                // set value, reload, check output

?>

<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[1]</td><td><?php echo $invno; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[2]</td><td><?php echo $count; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[3]</td><td>€ <?php echo $invamt; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[4]</td><td>€ <?php echo $invvat; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[5]</td><td>€ <?php echo $invtot; ?></td></tr>

<?php test_footer(); ?>



