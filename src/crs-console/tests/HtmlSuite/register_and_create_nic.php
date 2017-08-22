<?php include('test_config.php'); test_header('Register And Create NIC'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>

<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domains/regnew</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Enter a list of domains to check availability</td><td></td></tr>
<tr><td>type</td><td>Domains_RegNew_domain_names</td><td><?php echo getRandHostName(); ?>.ie,<?php echo getRandHostName(); ?>.ie<br/><?php echo getRandHostName(); ?>.ie<br/><?php echo getRandHostName(); ?>.ie</td></tr>
<tr><td>click</td><td>//input[@value="Check Availability"]</td><td></td></tr>

<tr><td>waitForTextPresent</td><td>Domains which are Valid and Available</td><td></td></tr>

<tr><td>type</td><td>Domains_Details_holder</td><td>test holder</td></tr>
<tr><td>select</td><td>Domains_Details_applicant</td><td>label=Discretionary Name</td></tr>
<tr><td>type</td><td>Domains_Details_remarks</td><td>cro: 1234</td></tr>
<tr><td>click</td><td>Domains_Details_accept_tnc</td><td></td></tr>

<!-- click new, create new Nic, return to this form with new Nic ID -->
<tr><td>click</td><td>//a[@id="nicWidgetNew_admin_contact_nic_1"]</td><td></td></tr>
	<!-- load form page -->
		<tr><td>waitForPageToLoad</td><td><td></td></tr>
		<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<tr><td>pause</td><td>2500</td><td></td></tr>
		<tr><td>assertTextPresent</td><td>NicHandle Name</td><td></td></tr>
	<!-- Fill Form : VALID -->
		<tr><td>type</td><td>Nichandle_Details_name</td><td>Testy McTester</td></tr>
		<tr><td>type</td><td>Nichandle_Details_companyName</td><td>TestCo</td></tr>
		<tr><td>type</td><td>Nichandle_Details_address</td><td>99 Test St,<br/>Bothar maith,<br/>Elsewhere</td></tr>
		<tr><td>type</td><td>Nichandle_Details_email</td><td>tester@example.com</td></tr>
		<tr><td>select</td><td>Nichandle_Details_county</td><td>Co. Dublin</td></tr>
		<tr><td>type</td><td>Nichandle_Details_phones</td><td>94856739486</td></tr>
		<tr><td>type</td><td>Nichandle_Details_faxes</td><td>28565</td></tr>
	<!-- post and wait -->
		<tr><td>clickAndWait</td><td>//input[@value="Create"]</td><td></td></tr>
	<!-- click option to retunr to DomainDetails page -->
		<tr><td>clickAndWait</td><td>//input[@value="Return to Form"]</td><td></td></tr>
<tr>
	<td>clickAndWait</td>
	<td>name=yt0</td>
	<td></td>
</tr>

<tr><td>click</td><td>//input[@value="Register Domain(s)"]</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>OK</td><td></td></tr>
<?php test_footer(); ?>

