<?php include('test_config.php'); test_header('Renewal and New Registrations'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay/outstanding_payments</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0129.ie';
$period='1';
$type='0';
$p = getPrice($period, $type);
$p1 =($p*5);
$price=number_format($p1,2); 
$vatrate = getVatRate($account_number);
$t = $price+($price*$vatrate);
$t1 =($t);
$total=number_format($t1,2); 
{
                // set value, reload, check output

?>
<tr>
	<td>clickAndWait</td>
	<td>link=Manage Offline Payments</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
<td>//table[@id='thisJqGrid']/tbody/tr[3]/td[2]/a</td>
	<td></td>
</tr>
<tr>
	<td>click</td>
	<td>jqg_thisJqGrid_example0216.ie</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>gridaction_remove_from_batch</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>//input[@value='Return']</td>
	<td></td>
</tr>
<tr>
	<td>assertTextPresent</td>
	<td>€ 72.60</td>
	<td></td>
</tr>


<?php
                }
?>
<?php test_footer(); ?>
