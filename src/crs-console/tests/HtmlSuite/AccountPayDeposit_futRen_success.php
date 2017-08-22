<?php include('test_config.php'); test_header('Future Renewals by Deposit (1-10 Years)'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = getVatRate($account_number);
$total = $price+($price*$vatrate);

{
//This test script is paying for a domain name from deposit account from the future renewals grid and also ensuring that the correct amount has been charged
                // set value, reload, check output

?>


<tr><td>open</td><td>/index.php?r=accounts_renewpay_advpay/current_newandrenewals</td><td></td></tr>
<tr><td>clickAndWait</td><td>link=Future Renewals</td><td></td></tr>
<tr><td>select</td><td>Acc_RnP_AdvPay_RnR_Model_date</td><td>label=November-2011</td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0363.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0364.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0366.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0368.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0370.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0372.ie</td><td></td></tr>
<tr><td>click</td><td>//td[@id='next']/span</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0960.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0964.ie</td><td></td></tr>
<tr><td>click</td><td>//td[@id='next']/span</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0972.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0989.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_dep</td><td></td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0363_prodcode</td><td>label=Renewal for 1 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0364_prodcode</td><td>label=Renewal for 2 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0366_prodcode</td><td>label=Renewal for 3 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0368_prodcode</td><td>label=Renewal for 4 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0370_prodcode</td><td>label=Renewal for 5 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0372_prodcode</td><td>label=Renewal for 6 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0960_prodcode</td><td>label=Renewal for 7 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0964_prodcode</td><td>label=Renewal for 8 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0972_prodcode</td><td>label=Renewal for 9 Year</td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0989_prodcode</td><td>label=Renewal for 10 Year</td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>assertTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>
<tr><td>assertTextPresent</td><td>697.00</td><td></td></tr>
<tr><td>assertTextPresent</td><td>146.37</td><td></td></tr>
<tr><td>assertTextPresent</td><td>843.37</td><td></td></tr>


<?php
                }
?>
<?php test_footer(); ?>
