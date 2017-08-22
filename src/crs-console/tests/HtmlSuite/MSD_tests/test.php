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

<tr><td>clickAndWait</td><td>link=All Domains</td><td></td></tr>
<tr><td>type</td><td>gs_A</td><td>example0081.ie</td></tr>
<tr><td>click</td><td>//td[@id='search_thisJqGrid']/div/span</td><td></td></tr>
<tr><td>type</td><td>jqg1</td><td>example0081.ie</td></tr>
<tr><td>click</td><td>fbox_thisJqGrid_search</td><td></td></tr>
<tr><td>clickAndWait</td><td>link=example0081.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/div</td><td>No action performed, example0081.ie already in MSD</td></tr>



<?php
                }
?>
<?php test_footer(); ?>
