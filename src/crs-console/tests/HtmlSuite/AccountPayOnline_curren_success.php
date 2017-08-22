<?php include('test_config.php'); test_header('Renewal and New Registrations'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0079.ie';
$period='1';
$type='0';
$price = getPrice($period, $type);
$renDate = getRenDate($domain1, $period);
$vatrate = getVatRate($account_number);
$total = $price+($price*$vatrate);
$vat=$price*$vatrate;
$b_status= getBillStatus($domain1);
$d_status= getDomainStatus($domain1);


{
//This test script is selecting for a domain name for online payment from the current invoice, ensuring that the correct elements are present in the credit card form and that the correct amount has been charged

                // set value, reload, check output

?>
<!-- search and pay for domain name -->

<tr><td>clickAndWait</td><td>link=Renewals and New Registrations</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_<?php echo $domain1; ?></td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_on</td><td></td></tr>
<tr><td>type</td><td>GridSelectionModel_holder</td><td>ciara conlon</td></tr>
<tr><td>type</td><td>GridSelectionModel_creditcardno</td><td>4263971921001307</td></tr>
<tr><td>type</td><td>GridSelectionModel_exp_month</td><td>12</td></tr>
<tr><td>type</td><td>GridSelectionModel_exp_year</td><td>12</td></tr>
<tr><td>type</td><td>GridSelectionModel_cvn</td><td>123</td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>


<?php
include ('singlePay_gridcheck.php');                }
?>
<?php test_footer(); ?>



