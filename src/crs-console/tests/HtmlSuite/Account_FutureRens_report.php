<?php include('test_config.php'); test_header('Domain Reports - Newly Registered'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domainreports/newdomains</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
        $arr = getNewRegDaysCounts($test_user_accountid);
        foreach($arr as $daysago => $count)
                {
                // set value, reload, check output

?>
                <tr><td>pause</td><td>1500</td><td></td></tr>
                <tr><td>type</td><td>//input[@id="NewDomainsModel_days"]</td><td><?php echo $daysago; ?></td></tr>
                <tr><td>click</td><td>//input[@value="Update"]</td><td></td></tr>
                <tr><td>waitForTextPresent</td><td>View 1 - 15 of <?php echo $count; ?></td><td></td></tr>
                <tr><td>assertTextPresent</td><td>View 1 - 15 of <?php echo $count; ?></td><td></td></tr>
<?php
                }
?>
<?php test_footer(); ?>



<?php include('test_config.php'); test_header('Renewal and New Registrations'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>index.php?r=accounts_renewpay_advpay/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example11.ie';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = getVatRate($account_number);
$total = $price+($price*$vatrate);

{
                // set value, reload, check output

?>

<tr><td>clickAndWait</td><td>link=Future Renewals</td><td></td></tr>
<tr><td>waitForText</td><td>jqg_thisJqGrid_example11.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example11.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_on</td><td></td></tr>
<tr><td>type</td><td>GridSelectionConfirmModel_holder</td><td>4</td></tr>
<tr><td>type</td><td>GridSelectionConfirmModel_holder</td><td>ciara conlon</td></tr>
<tr><td>type</td><td>GridSelectionConfirmModel_creditcardno</td><td>4263971921001307</td></tr>
<tr><td>type</td><td>GridSelectionConfirmModel_exp_month</td><td>12</td></tr>
<tr><td>type</td><td>GridSelectionConfirmModel_exp_year</td><td>12</td></tr>
<tr><td>type</td><td>GridSelectionConfirmModel_cvn</td><td>123</td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[3]</td><td>€ <?php echo $price; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[5]</td><td>€ <?php echo $total; ?></td></tr>
<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>


<?php
                }
?>
<?php test_footer(); ?>



