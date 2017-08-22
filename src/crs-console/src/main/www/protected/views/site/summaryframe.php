<!-- START protected/views/site/summaryframe.php -->
<?php
/**
 * View page for Account Summary IFrame
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       SiteController::actionSummaryFrame()
 */
?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="en" />
	<!-- blueprint CSS framework -->
	<link rel="stylesheet" type="text/css" href="css/screen.css" media="screen, projection" />
	<link rel="stylesheet" type="text/css" href="css/print.css" media="print" />
	<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="css/ie.css" media="screen, projection" />
	<![endif]-->
	<link rel="stylesheet" type="text/css" href="css/main.css" />
	<link rel="stylesheet" type="text/css" href="css/form.css" />

    <script><?php include_once 'protected/widgets/js/common.js'; ?></script>
	<script src="jqGrid4/js/jquery-1.6.1.src.js"></script>
   <script>
	   function asyncload(tag){
	    $.ajax({
		    url: "<?php echo $this->createUrl('site/summary&id='); ?>"+tag,
		    success: function(str) { $("#stats_"+tag).html(str); }
		});
	    }
	$.ajaxSetup ({ cache: true });   
   
    function goToPage(page, details) {
      var host = '<?php echo "".Yii::app()->request->hostInfo; ?>';
      var address = host + page;
      if(details != null) {
         address += '&id=';
         address += details;
      } 
      window.top.location = address;
    }
	</script>
</head>
<table align="center" width="100%" border="0">
   <tr> <td>
   <strong>User: </strong>
   <?php 
      if(Utility::isRegistrar() || Utility::isSuperRegistrar()) {
      ?>
         <?php echo Yii::app()->user->accountName; ?> <strong>Available Deposit Balance :</strong>&nbsp;&euro;<span id="stats_DepositBalance"><img width="7" height="7" src='images/loading.gif' alt='loading...' /></span>&nbsp;&nbsp;
		 <?php if(!Yii::app()->user->authenticatedUser->passwordChangeRequired) {?>
          <a href="" onclick="goToPage('/index.php?r=account_topup_my_deposit_account/menu')">Top up</a>
          <?php }?>
      <?php
         } else {
			echo Yii::app()->user->nicHandle->name;
		 }
      ?>
      </td>
      <td style="text-align: right" width="200px">
      <?php if(!Yii::app()->user->authenticatedUser->passwordChangeRequired) {?>
      	<a href="" onclick="goToPage('/index.php?r=nichandles/viewnichandle','<?php echo Yii::app()->user->name;?>')">My Account</a>&nbsp;&nbsp;
   		<?php } else {?>
      	<b>My Account</b>&nbsp;&nbsp;
      	<?php 
		 }		
		 ?>
      <a href="" onclick="goToPage('/index.php?r=site/logout')">Logout&nbsp;</a></td>
   </tr> 
</table>
<script>
    function getAccountStats() {
        asyncload("DepositBalance");
        }
    setTimeout(getAccountStats,100); // milliseconds
</script>

<!-- END protected/views/site/summaryframe.php -->
