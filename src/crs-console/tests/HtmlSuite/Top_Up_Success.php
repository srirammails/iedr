<?php include('test_config.php'); test_header('Deposit Account - Top Up'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay/topup</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
                $balance = getDepositBalance($test_user_accountid);
		$topupamount='1000.00';
		$sum_total = $balance + $topupamount;
$total=number_format($sum_total,2);
{
                // set value, reload, check output

?>

<!-- Open Deposit top up form --> 

<td>clickAndWait</td><td>link=Top Up Deposit Account</td><td></td></tr><tr>


<!-- Populate credit card form --> 

        <td>type</td><td>AccountTopUpModel_holder</td><td>john doe</td></tr><tr>
        <td>type</td><td>AccountTopUpModel_creditcardno</td><td>4263971921001307</td></tr><tr>
        <td>type</td><td>AccountTopUpModel_exp_month</td><td>12</td></tr><tr>
        <td>type</td><td>AccountTopUpModel_exp_year</td><td>12</td></tr><tr>
        <td>type</td><td>AccountTopUpModel_euros_amount</td><td><?php echo $topupamount; ?></td></tr><tr>
        <td>type</td><td>AccountTopUpModel_cvn</td><td>123</td></tr><tr>

<!-- Submit form -->
        <td>clickAndWait</td><td>yt0</td><td></td></tr><tr>
       
<!-- validate that transaction was successful -->

	<td>waitForText</td><td>//div[@id='content']/div/h2</td><td>Transaction Successful</td></tr><tr>
        <td>assertText</td><td>//div[@id='content']/div/h2</td><td>Transaction Successful</td></tr><tr>
        
<!-- verify that the static grid has updated the balance to pre balance plus top up amount set in this form --> 

	<td>waitForText</td><td>stats_DepositBalance</td><td><?php echo $total; ?></td></tr><tr>
        <td>assertText</td><td>stats_DepositBalance</td><td><?php echo $total; ?></td></tr><tr>
        <td>clickAndWait</td><td>link=Top Up Deposit Account</td><td></td></tr>

<?php
                }
?>
<?php test_footer(); ?>
