<?php include('test_config.php'); test_header('Put an R&R domain into MSD'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>

<?php
$domain1='example0024.ie';
$arr[dom1]='example0024.ie';
$arr[dom2]='example0057.ie';
$arr[dom3]='example0059.ie';
$arr[dom4]='example0177.ie';
$arr[dom4]='example0178.ie';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = getVatRate($account_number);
$total = $price+($price*$vatrate);
                
//The purpose of this test is to put domain names into MSD from the current invoice. Then check the domain names in Domain View and ensure that their billing status has been updated accordingly

// PUT DOMAINS IN MSD

?>

<tr><td>clickAndWait</td><td>link=Renewals and New Registrations</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_<?php echo $domain1; ?></td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0057.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0059.ie</td><td></td></tr>
<tr><td>click</td><td>//td[@id='next']/span</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0177.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0178.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_msd</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>
<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>

<?php
        foreach($arr as $dom => $name)
                {
                // FROM DOMAIN VIEW, VERIFY THAT THE DOMAIN STATUS HAS BEEN UPDATED
?>
<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>click</td><td>xpath=//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>xpath=//td[@class='data']/input</td><td><?php echo $name; ?></td></tr>
<tr><td>click</td><td>fbox_thisJqGrid_search</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>link=<?php echo $name; ?></td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $name; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id='ViewDomainModel_domain_billingStatus']</td><td>Mailed</td></tr>

<?php
                }
?>
<?php test_footer(); ?>

