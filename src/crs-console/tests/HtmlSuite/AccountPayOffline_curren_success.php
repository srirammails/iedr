<?php include('test_config.php'); test_header('Renewal and New Registrations - Offline Payment'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0094.ie';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = getVatRate($account_number);
$total = $price+($price*$vatrate);

{
//This test script is selecting for a domain name for offline payment from the current invoice and also ensuring that the correct amount has been charged

                // set value, reload, check output

?>

<tr><td>clickAndWait</td><td>link=Renewals and New Registrations</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_<?php echo $domain1; ?></td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_off</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>
<tr><td>verifyText</td><td>//div[@id='content']/div/h4[2]</td><td>NB - Promise to pay, not an actual payment</td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[3]</td><td>€ <?php echo $price; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[5]</td><td>€ <?php echo $total; ?></td></tr>

<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>


<?php
                }
?>
<?php test_footer(); ?>



