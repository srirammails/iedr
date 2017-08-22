<?php include('test_config.php'); test_header('Domain Reports - Newly Registered'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domainreports/newdomains</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
        $arr = getNewRegDaysCounts($test_user_accountid);
        foreach($arr as $daysago => $count)
                {
                // set value, reload, check output

?>
                <tr><td>pause</td><td>1500</td><td></td></tr>
                <tr><td>type</td><td>//input[@id="NewDomainsModel_days"]</td><td><?php echo $daysago; ?></td></tr>
                <tr><td>click</td><td>//input[@value="Update"]</td><td></td></tr>
                <tr><td>waitForTextPresent</td><td>View 1 - 15 of <?php echo $count; ?></td><td></td></tr>
                <tr><td>assertTextPresent</td><td>View 1 - 15 of <?php echo $count; ?></td><td></td></tr>
<?php
                }
?>
<?php test_footer(); ?>



