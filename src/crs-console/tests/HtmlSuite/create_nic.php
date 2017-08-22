<?php include('test_config.php'); test_header('Create NIC');
// BUG: TODO: add tests for country & county selection etc
?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>

<!-- load form page -->
	<tr><td>open</td><td><?php echo $base; ?>/index.php?r=nichandles/createnichandle</td><td></td></tr>
	<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
	<tr><td>assertTextPresent</td><td>NicHandle Name</td><td></td></tr>
<!-- Fill Form : INVALID -->
	<!-- NicHandle Name * -->
	<tr><td>type</td><td>Nichandle_Details_name</td><td></td></tr>
	<!-- Company Name * -->
	<tr><td>type</td><td>Nichandle_Details_companyName</td><td></td></tr>
	<!-- Address * -->
	<tr><td>type</td><td>Nichandle_Details_address</td><td></td></tr>
	<!-- Email * -->
	<tr><td>type</td><td>Nichandle_Details_email</td><td>VAL@VAL@.com</td></tr>
<!-- post and wait -->
	<tr><td>clickAndWait</td><td>//input[@value="Create"]</td><td></td></tr>
	<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<!-- check error messages -->
	<tr><td>assertTextPresent</td><td>NicHandle Name cannot be blank</td><td></td></tr>
	<tr><td>assertTextPresent</td><td>Company Name cannot be blank</td><td></td></tr>
	<tr><td>assertTextPresent</td><td>Address cannot be blank</td><td></td></tr>
	<tr><td>assertTextPresent</td><td>Email is not a valid Email Address</td><td></td></tr>

<!-- Fill Form : VALID -->
	<!-- NicHandle Name * -->
	<tr><td>type</td><td>Nichandle_Details_name</td><td>Mary McTester</td></tr>
	<!-- Company Name * -->
	<tr><td>type</td><td>Nichandle_Details_companyName</td><td>Marys Test Co</td></tr>
	<!-- Address * -->
	<tr><td>type</td><td>Nichandle_Details_address</td><td>1 Test Straid,<br/>Dé MáirStreet, <br/>Cork</td></tr>
	<!-- Email * -->
	<tr><td>type</td><td>Nichandle_Details_email</td><td>tester@example.com</td></tr>
	<!-- Phones -->
	<tr><td>type</td><td>Nichandle_Details_phones</td><td>39843948</td></tr>
	<!-- Faxes -->
	<tr><td>type</td><td>Nichandle_Details_faxes</td><td>2852342345</td></tr>
<!-- post and wait -->
	<tr><td>clickAndWait</td><td>//input[@value="Create"]</td><td></td></tr>
	<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
	<tr><td>assertTextPresent</td><td>NIC Handle Created</td><td></td></tr>

<?php test_footer(); ?>

