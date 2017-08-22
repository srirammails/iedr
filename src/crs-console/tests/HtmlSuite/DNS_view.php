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
<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>
<tr><td>verifyTitle</td><td>IEDR Registrar's Console - All Domains</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>View 1 - 15 of <?php echo $domaincount; ?></td><td></td></tr>
<tr><td>assertTextPresent</td><td>View 1 - 15 of <?php echo $domaincount; ?></td><td></td></tr>
<tr><td>click</td><td>xpath=//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>xpath=//td[@class='data']/input</td><td><?php echo $domain1; ?></td></tr>
<tr><td>click</td><td>xpath=//div[@id='fbox_thisJqGrid']/table/tfoot/tr/td[1]/span[2]/span[2]</td><td></td></tr>
<tr><td>click</td><td>link=example1.ie</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_billingContacts_companyName"]</td><td>Registrar co Limited</td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $domain1; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_domainStatus"]</td><td><?php echo $status; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_registrationDate"]</td><td><?php echo $regDate; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_renewDate"]</td><td><?php echo $renewDate; ?></td></tr><tr>




<?php test_footer(); ?>



