<?php include('test_config.php'); test_header('Account Reports - Transfers To'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_reports/transferto</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
		
<?php
        $arr = getTransfersDaysCounts($test_user_nichandle);
        foreach($arr as $daysago => $count)


	{

if($count < 15){ $records = $count; }
	else {$records='15';}
?>

<!-- Load the Transfers to report and check the number of records displayed for each value returned from the array --> 
<tr><td>verifyTitle</td><td>IEDR Registrar's Console - Transfers To</td><td></td></tr>
                <tr><td>type</td><td>//input[@id="Rpts_NicXfersModel_days"]</td><td><?php echo $daysago; ?></td></tr>
                <tr><td>click</td><td>//input[@value="Update"]</td><td></td></tr>
                <tr><td>pause</td><td>1500</td><td></td></tr>
                <tr><td>waitForTextPresent</td><td>View 1 - <?php echo $records; ?> of <?php echo $count; ?></td><td></td></tr>
                <tr><td>assertTextPresent</td><td>View 1 - <?php echo $records; ?> of <?php echo $count; ?></td><td></td></tr>


<?php
}
?>

<?php test_footer(); ?>
