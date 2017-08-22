<?php include('../test_config.php'); test_header('MSD PAY - Deposit'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_msd/recent</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='nvexample-0810.ie';
$invno='INV990972';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = '0';
$vattotal = ($price*$vatrate);
$total = $price+($price*$vatrate);

{
                // set value, reload, check output

?>

<tr><td>clickAndWait</td><td>link=Mails, Suspensions &amp; Deletions</td><td></td></tr>
<tr><td>click</td><td>//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>select</td><td>field</td><td>label=Domain</td></tr>
<tr><td>type</td><td>//input[@type='text']</td><td><?php echo $domain1; ?></td></tr>
<tr><td>click</td><td>//div[@id='fbox_thisJqGrid']/table/tfoot/tr/td[1]/span[2]/span[2]</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>waitForText</td><td>jqg_thisJqGrid_nvexample-0810.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_nvexample-0810.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridactionpay_paydeposit</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
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





