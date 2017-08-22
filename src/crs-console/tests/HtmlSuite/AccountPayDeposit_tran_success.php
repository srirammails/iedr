<?php include('test_config.php'); test_header('Transfers Invoice: Pay from Deposit'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/transfers_paycurrent</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0031.ie';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = getVatRate($account_number);
$total = $price+($price*$vatrate);
$vat= $price*$vatrate;
$b_status= getBillStatus($domain1);
$d_status= getDomainStatus($domain1);
$renDate = getRenDate($domain1, $period);


{
//This test script is paying for a domain name from deposit account from transfers invoice and also ensuring that the correct amount has been charged

                // set value, reload, check output

?>

<tr><td>clickAndWait</td><td>link=Renewals and New Registrations</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0031.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_dep</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>


<?php
include ('singlePay_gridcheck.php');                }
?>


<?php test_footer(); ?>



