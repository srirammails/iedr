<?php include('../test_config.php'); test_header('MSD PIA - Online'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_msd/recent</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0053.ie';
$invno='Advance';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = getVatRate($account_number);
$vattotal = ($price*$vatrate);
$total = $price+($price*$vatrate);

{
//TEST TO REACTIVATE DOMAINS FROM MSD BY CREDIT CARD, ENSURING THAT CORRECT AMOUNT + VAT ARE APPLIED AND THAT DOMAINS ARE IN ACTIVE STATE AFTERWARDS

                // set value, reload, check output

?>
<!--LOAD MSD GRID, SELECT DOMAINS FOR PAYMENT AND SELECT PAY ONLINE-->

<tr><td>clickAndWait</td><td>link=Mails, Suspensions &amp; Deletions</td><td></td></tr>
<tr><td>clickAndWait</td><td>//div[@id='content']/h2/a[3]</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0053.ie</td><td></td></tr>

<!--ENTER CREDIT CARD DETAILS AND WAIT FOR RESPONSE-->

<tr><td>clickAndWait</td><td>gridactionpay_payonline</td><td></td></tr>
<tr>    <td>type</td>   <td>MSDSelectionModel_holder</td><td>CIARA CONLON</td></tr>
<tr><td>click</td><td>MSDSelectionModel_creditcardno</td><td></td></tr>
<tr><td>type</td><td>MSDSelectionModel_creditcardno</td><td>4263971921001307</td></tr>
<tr><td>type</td><td>MSDSelectionModel_exp_month</td><td>12</td></tr>
<tr><td>type</td><td>MSDSelectionModel_exp_year</td><td>12</td></tr>
<tr><td>click</td><td>MSDSelectionModel_cvn</td><td></td></tr>
<tr><td>type</td><td>MSDSelectionModel_cvn</td><td>123</td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>

<!--CHECK TO ENSURE THAT THE CORRECT OS INVOICE NUMBER HAS BEEN PICKED UP AND THAT THE AMOUNT AND VAT ARE CORRECT-->

<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[4]</td><td>€ <?php echo $price; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[5]</td><td>€ <?php echo $vattotal; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[6]</td><td>€ <?php echo $total; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[2]</td><td><?php echo $invno; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>

<!--IN DOMAIN VIEW CHECK TAT THE DOMAINS STATUS HAS BEEN UPDATED TO ACTIVE BILLABLE -->
<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>click</td><td>xpath=//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>xpath=//td[@class='data']/input</td><td><?php echo $domain1; ?></td></tr>
<tr><td>click</td><td>fbox_thisJqGrid_search</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>link=<?php echo $domain1; ?></td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $domain1; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id='ViewDomainModel_domain_billingStatus']</td><td>Yes Billable</td></tr>


<?php
                }
?>
<?php test_footer(); ?>
