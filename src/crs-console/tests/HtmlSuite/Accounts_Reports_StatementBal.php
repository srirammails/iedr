<?php include('test_config.php'); test_header('Account Reports - Statement Balance'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_reports/account_aged_balance</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>

<!-- Check the Statement Balance report to ensure values equal that of test data, values are hardcoded currently, need to revise to include a function to set values -->

<tr><td>verifyTitle</td><td>IEDR Registrar's Console - Aged Balance</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='yw0']/div[2]</td><td>Displaying 1-3 of 3 result(s).</td></tr>
<tr><td>assertText</td><td>//div[@id='yw0']/table/tbody/tr[3]/td[11]</td><td>1545.00</td></tr>


<?php
?>

<?php test_footer(); ?>



