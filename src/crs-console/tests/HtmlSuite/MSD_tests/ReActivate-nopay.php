<?php include('../test_config.php'); test_header('MSD ReActivate No Pay'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0081.ie';

{
//TEST OF REACTIVATING A DOMAIN NAME THAT SHOULD NOT REQUIRE PAYMENT
// update: their was a bug intially which NASK addressed in Bug #4299 for this reason this test has been changed to firstly pay for a domain in advance, then put it into MSD, then check that it can be reactivated without payment.


?>

<!-- PAY FOR DOMAIN AS ADVANCE INVOICE -->

<tr><td>clickAndWait</td><td>link=Future Renewals</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_<?php echo $domain1; ?></td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_dep</td><td></td></tr>
<tr><td>select</td><td>GridSelectionModel_domlist_example0081_prodcode</td><td>label=Renewal for 5 Year</td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>

<!-- SEND DOMAIN TO MSD FROM DOMAIN VIEW -->

<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>
<tr><td>type</td><td>gs_A</td><td><?php echo $domain1; ?></td></tr>
<tr><td>click</td><td>//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>jqg1</td><td><?php echo $domain1; ?></td></tr>
<tr><td>click</td><td>fbox_thisJqGrid_search</td><td></td></tr>
<tr><td>clickAndWait</td><td>link=<?php echo $domain1; ?></td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/div</td><td>Domain <?php echo $domain1; ?> added to MSD</td></tr>

<!-- LOAD MSD LIST, CHECK DOMAIN AND REACTIVATE WITHOUT PAYMENT, ENSURE FEE IS 0 -->

<tr><td>clickAndWait</td><td>link=Mails, Suspensions &amp; Deletions</td><td></td></tr>
<tr><td>pause</td><td>1500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_<?php echo $domain1; ?></td><td></td></tr>
<tr><td>clickAndWait</td><td>gridactionnopay_reactivate</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>assertTextPresent</td><td>€ 0.00</td><td></td></tr>
<tr><td>verifyText</td><td>//div[@id='content']/div/table/tbody/tr[2]/td[5]</td><td>€ 0.00</td></tr>


<!-- VIEW DOMAIN NAME IN DOMAIN VIEW AND ENSURE THAT BILLING STATUS HAS BEEN CORRECTLY UPDATED-->
<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>

<tr>
	<td>click</td>
	<td>//td[@id='search_thisJqGrid']/div/span</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>jqg1</td>
	<td>example0081.ie</td>
</tr>
<tr>
	<td>click</td>
	<td>fbox_thisJqGrid_search</td>
	<td></td>
</tr>

<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>link=<?php echo $domain1; ?></td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>assertValue</td><td>//input[@id="ViewDomainModel_domain_name"]</td><td><?php echo $domain1; ?></td></tr><tr>
<tr><td>assertValue</td><td>//input[@id='ViewDomainModel_domain_billingStatus']</td><td>Yes Billable</td></tr>


<?php
                }
?>
<?php test_footer(); ?>
