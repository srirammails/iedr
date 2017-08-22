<?php include('test_config.php'); test_header('Register Success'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>

<tr><td>open</td><td><?php echo $base; ?>/index.php/domains/regnew</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Enter a list of domains to check availability</td><td></td></tr>
<tr><td>type</td><td>Domains_RegNew_domain_names</td><td><?php echo getRandHostName(); ?>.ie</td></tr>
<tr><td>click</td><td>//input[@value="Check Availability"]</td><td></td></tr>

<tr><td>waitForTextPresent</td><td>Domains which are Valid and Available</td><td></td></tr>
<tr><td>type</td><td>Domains_Details_holder</td><td>test holder</td></tr>
<tr><td>select</td><td>Domains_Details_applicant</td><td>label=Discretionary Name</td></tr>
<tr><td>type</td><td>Domains_Details_remarks</td><td>cro: 1234</td></tr>
<tr><td>type</td><td>Domains_Details_admin_contact_nic_1</td><td>AAA074-IEDR</td></tr>
<tr><td>click</td><td>Domains_Details_accept_tnc</td><td></td></tr>
<tr><td>click</td><td>//input[@value="Register Domain(s)"]</td><td></td></tr>

<tr><td>waitForTextPresent</td><td>is not an existing NIC-Handle</td><td></td></tr>
<?php test_footer(); ?>

