<?php include('test_config.php'); test_header('Register And Create NIC'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>

<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domains/regnew</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Enter a list of domains to check availability</td><td></td></tr>
<tr><td>type</td><td>Domains_RegNew_domain_names</td><td>register-example0001.ie, <?php echo getRandHostName(); ?>.ie,<?php echo getRandHostName(); ?>.ie, <?php echo getRandHostName(); ?>.ie, <?php echo getRandHostName(); ?>.ie</td></tr>
<tr><td>click</td><td>//input[@value="Check Availability"]</td><td></td></tr>

<tr><td>assertTextPresent</td><td>register-example0001.ie is invalid, already registered, or has a pending ticket.</td><td></td></tr>
<?php test_footer(); ?>

