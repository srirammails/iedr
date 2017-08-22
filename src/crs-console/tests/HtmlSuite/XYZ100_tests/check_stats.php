<?php include('test_config.php'); test_header('Check Stats'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
                $balance = getDepositBalance($test_user_accountid_nv);
$bal=number_format($balance,2);             
   $ticket = getTicketCount($test_user_accountid_nv);
   $ticketp = getTicketPassCount($test_user_accountid_nv);
   $ticketh = getTicketPendingCount($test_user_accountid_nv);
                $xferin = getXfersInCount($test_user_nichandle_nv);
                $xferout = getXfersOutCount($test_user_nichandle_nv);
                $dc = getDomainCount($test_user_accountid_nv);
                $nrcount = getNewRegCount($test_user_accountid_nv);
                $state = getStatementBalance($test_user_nichandle_nv);
$statement=number_format($state,2);             
$domaincount=number_format($dc);             

{

?>
<tr><td>pause</td><td>2000</td><td></td></tr>
<tr><td>assertTitle</td><td>IEDR Registrar's Console - Site</td><td></td></tr>
<tr>    <td>waitForText</td><td>stats_DomainCount</td><td><?php echo $domaincount; ?></td></tr>
<tr>    <td>assertText</td><td>stats_DomainCount</td><td><?php echo $domaincount; ?></td></tr>
<tr>    <td>assertText</td><td>stats_TicketCount</td><td><?php echo $ticket; ?></td></tr>
<tr>    <td>assertText</td><td>stats_NewRegs</td><td><?php echo $nrcount; ?></td></tr>
<tr>    <td>assertText</td><td>stats_PendingTickets</td><td><?php echo $ticketh; ?></td></tr>
<tr>    <td>assertText</td><td>stats_TransfersIn</td><td><?php echo $xferin; ?></td></tr>
<tr>    <td>assertText</td><td>stats_PassedTickets</td><td><?php echo $ticketp; ?></td></tr>
<tr>    <td>assertText</td><td>stats_TransfersOut</td><td><?php echo $xferout; ?></td></tr>
<tr>    <td>assertText</td><td>stats_StatementBalance</td><td><?php echo $statement; ?></td></tr>
<tr>	<td>assertText</td><td>stats_DepositBalance</td><td><?php echo $bal; ?></td></tr><tr>


<?php
                }
?>
<?php test_footer(); ?>


