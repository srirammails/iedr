<?php include('test_config.php'); test_header('Check Grid - Transfer Invoice'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/transfers_paycurrent</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php

$arr = getRCInvLIVECount($test_user_nichandle, RCInvoiceHist);

$invno=$arr[num];
$count=$arr[cnt];
$invamt=number_format($arr[amt],2);
$invtot=number_format($arr[tot],2);
$invvat=number_format($invtot-$invamt,2);

//This test is checking the validity of the values shown in the summary grid about the current invoices
{
                // set value, reload, check output

?>

<tr><td>verifyText</td><td>//div[@id='content']/table/tbody/tr[2]/td[1]</td><td><?php echo $invno; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[2]</td><td><?php echo $count; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[3]</td><td>€ <?php echo $invamt; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[4]</td><td>€ <?php echo $invvat; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/table/tbody/tr[2]/td[5]</td><td>€ <?php echo $invtot; ?></td></tr>

<?php
                }
?>
<?php test_footer(); ?>



