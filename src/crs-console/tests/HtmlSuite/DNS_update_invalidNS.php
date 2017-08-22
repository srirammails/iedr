<?php include('test_config.php'); test_header('Domain Reports - All Domains'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domainreports/alldomains</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$dc = getDomainCount($test_user_accountid);
$domaincount=number_format($dc);  
$domain1='example1.ie';
$renewDate = getRenewDate($domain1);
$status = getDomainStatus($domain1);
$regDate = getRegDate($domain1);

?>
<tr><td>open</td><td>/index.php?r=nsreports/dnsserversearch</td><td></td></tr>
<tr><td>clickAndWait</td><td>link=DNS</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_1_example0002.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_dnsMod</td><td></td></tr>
<tr><td>type</td><td>DNS_BulkMod_ns_1</td><td>ns1.example0002.ie</td></tr>
<tr><td>type</td><td>DNS_BulkMod_ip_1</td><td>12.123.123.123</td></tr>
<tr><td>type</td><td>DNS_BulkMod_ns_2</td><td>ns1.example.ie</td></tr>
<tr><td>type</td><td>DNS_BulkMod_ns_3</td><td>ns2.example.ie</td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>assertTextNotPresent</td><td>Glue needed for 1 (ns1.example0002.ie).</td><td></td></tr>
<tr>
	<td>assertTextPresent</td>
	<td>Verification result for example0002.ie on ns1.example0002.ie....Invalid<br />Verification result for example0002.ie on ns1.example.ie....Invalid<br />Verification result for example0002.ie on ns2.example.ie....Invalid</td>
	<td></td>
</tr>


<!-- todo : add check domain view that DNS has not been updated --> 

<?php test_footer(); ?>
