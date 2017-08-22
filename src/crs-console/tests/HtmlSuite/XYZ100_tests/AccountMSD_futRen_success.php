<?php include('test_config.php'); test_header('Future Renewals - put in MSD'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_advpay/current_newandrenewals</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>

<?php
$domain1='nvexample-0105.ie';
$arr[dom1]='nvexample-0012.ie';
$arr[dom2]='nvexample-0013.ie';
$arr[dom3]='nvexample-0105.ie';
$arr[dom4]='nvexample-0087.ie';
$arr[dom4]='nvexample-0112.ie';
$period='1';
$type='0';
$price = getPrice($period, $type);
$vatrate = '0';
$total = $price+($price*$vatrate);
                // PUT DOMAINS IN MSD
?>
<tr><td>waitForText</td><td>jqg_thisJqGrid_nvexample-0012.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_nvexample-0012.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_nvexample-0013.ie</td><td></td></tr>
<tr><td>click</td><td>//td[@id='next']/span</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_nvexample-0105.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_nvexample-0112.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_nvexample-0087.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_msd</td><td></td></tr>
<tr><td>assertTextPresent</td><td>Send to Mail/Suspend/Delete</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>
<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>
<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>


<?php
        foreach($arr as $dom => $name)
                {
                // FROM DOMAIN VIEW, VERIFY THAT THE DOMAIN STATUS HAS BEEN UPDATED
?>
<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>click</td><td>xpath=//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>xpath=//td[@class='data']/input</td><td><?php echo $name; ?></td></tr>
<tr><td>click</td><td>xpath=//div[@id='fbox_thisJqGrid']/table/tfoot/tr/td[1]/span[2]/span[2]</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>link=<?php echo $name; ?></td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $name; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id='ViewDomainModel_domain_billingStatus']</td><td>Mailed</td></tr>

<?php
                }
?>
<?php test_footer(); ?>
