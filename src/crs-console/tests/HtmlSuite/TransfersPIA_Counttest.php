<?php include('test_config.php'); test_header('Transfers Advance - Pay Online'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php

$count = getXfersBillableCount($test_user_nichandle)

?>
<tr>
	<td>open</td>
	<td>/index.php?r=accounts_renewpay_advpay/transfers_pia</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Transfers - Pay In Advance</td>
	<td></td>
</tr>
<tr>
	<td>verifyTitle</td>
	<td>IEDR Registrar's Console - Transfers - Pay In Advance</td>
	<td></td>
</tr>
<tr>
	<td>verifyText</td>
	<td>//td[@id='thisJqGridPager_right']/div</td>
	<td>View 1 - 15 of <?php echo $count; ?></td>
</tr>
<tr>
	<td>click</td>
	<td>//td[@id='last']/span</td>
	<td></td>
</tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr>
	<td>assertText</td>
	<td>//tr[@id='example0992.ie']/td[1]</td>
	<td><?php echo $count; ?></td>
</tr>


<?php test_footer(); ?>



