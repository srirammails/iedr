<!-- ensure correct amounts are shown in receipt -->

<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>

<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[5]</td><td>€ <?php echo $price; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[6]</td><td>€ <?php echo $vat; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[7]</td><td>€ <?php echo $total; ?></td></tr>

<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[3]</td><td>€ <?php echo $price; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[4]</td><td>€ <?php echo $vat; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[5]</td><td>€ <?php echo $total; ?></td></tr>
<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>

<!-- check that the renew date of the domain name has gone forward by one year -->

<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domainreports/alldomains</td><td></td></tr>
<tr><td>verifyTitle</td><td>IEDR Registrar's Console - All Domains</td><td></td></tr>
<tr><td>click</td><td>xpath=//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>xpath=//td[@class='data']/input</td><td><?php echo $domain1; ?></td></tr>
<tr><td>click</td><td>fbox_thisJqGrid_search</td><td></td></tr>
<t><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>link=<?php echo $domain1; ?></td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $domain1; ?></td></tr><tr>
<tr><td>assertTextNotPresent</td><td>LOCKED</td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_renewDate"]</td><td><?php echo $renDate; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_billingStatus"]</td><td><?php echo $b_status; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_domainStatus"]</td><td><?php echo $d_status; ?></td></tr><tr>

