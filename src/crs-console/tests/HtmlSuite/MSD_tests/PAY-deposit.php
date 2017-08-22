<?php include('../test_config.php'); test_header('MSD PAY - Deposit'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_msd/recent</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0810.ie';
$invno='INV990972';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = getVatRate($account_number);
$vattotal = ($price*$vatrate);
$total = $price+($price*$vatrate);


//Testing paying for a domain name from MSD (using deposit account)  to ensure that the correct amount is charged and that domains status has been updated afterwards
{
                // set value, reload, check output

?>

<!--CLICK ON MSD LIST-->
<tr>
	<td>clickAndWait</td>
	<td>link=Mails, Suspensions &amp; Deletions</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>//div[@id='content']/h2/a[3]</td>
	<td></td>
</tr>
<!--SEARCH FOR DOMAIN NAME IN GRID AND SELECT FOR PAYMENT-->
<tr>
	<td>click</td>
	<td>//td[@id='search_thisJqGrid']/div/span</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>jqg1</td>
	<td>example0810.ie</td>
</tr>
<tr>
	<td>click</td>
	<td>fbox_thisJqGrid_search</td>
	<td></td>
</tr>
<tr><td>pause</td><td>2500</td><td></td></tr>

<tr>
	<td>click</td>
	<td>jqg_thisJqGrid_example0810.ie</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>gridactionpay_paydeposit</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>




<!--CHECK THAT THE CORRECT AMOUNT AND VAT HAVE BEEN APPLIED AND THAT THE CORRECT INVOICE NUMBER HAS BEEN USED-->

<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[4]</td><td>€ <?php echo $price; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[5]</td><td>€ <?php echo $vattotal; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[6]</td><td>€ <?php echo $total; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[2]</td><td><?php echo $invno; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>
<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>

<?php
                }
?>
<?php test_footer(); ?>





