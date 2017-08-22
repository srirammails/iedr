<?php include('test_config.php'); test_header('Domains_Reports_AutoRenew'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domainreports/autorenewed</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
 $domaincount = getDomainCountInStatus($test_user_accountid, A);
 $domain1='example0595.ie';
 $renewDate = getRenewDate($domain1);
 $status = getDomainStatus($domain1);
 $regDate = getRegDate($domain1);
                {

?>
<tr><td>clickAndWait</td><td>link=Autorenewed Domains</td><td></td></tr>
<tr><td>verifyTitle</td><td>IEDR Registrar's Console - Autorenewed Domains</td><td></td></tr>
<tr><td>pause</td><td>500</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>View 1 - 15 of <?php echo $domaincount; ?></td><td></td></tr>
<tr><td>assertTextPresent</td><td>View 1 - 15 of <?php echo $domaincount; ?></td><td></td></tr>
<tr><td>click</td><td>xpath=//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>xpath=//td[@class='data']/input</td><td><?php echo $domain1; ?></td></tr>
<tr><td>click</td><td>fbox_thisJqGrid_search</td><td></td></tr>
<tr><td>click</td><td>link=<?php echo $domain1; ?></td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Domain Name</td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_billingContacts_companyName"]</td><td>Registrar co Limited</td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $domain1; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_domainStatus"]</td><td><?php echo $status; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_registrationDate"]</td><td><?php echo $regDate; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_renewDate"]</td><td><?php echo $renewDate; ?></td></tr><tr>




<?php
                }
?>
<?php test_footer(); ?>


