<!-- START protected/views/accounts_nrp/recent.php -->
<?php
/**
 * View page for Accounts MSD - Recent
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       CurrentNRPStatusesModel
 */

$pg = 'Current NRP Statuses';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php printBreadcrumbTrail($pg); ?></h2>
<div>There are <?php echo $countDomains; ?> domains in the NRP process.</div>
<?php 
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

$commandlist = array();
if(!Utility::isDirect()) {
   $commandlist = array(
					'payonline' => 'Pay %n Domains Online  (€ %fee)',
					'paydeposit'=> 'Pay %n Domains From Deposit  (€ %fee)',
               'removefromvoluntary'=> 'Remove %n from NRP',
					);
} else {
   $commandlist = array(
					'payonline' => 'Pay %n Domains Online  (€ %fee)',
               'removefromvoluntary'=> 'Remove %n from NRP',
					);
}

$this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
	'pk_column_index'	=> 1,
	'actionurl'		=> $this->createUrl('account_current_nrp_statuses/confirm'),
	'returnurl'		=> $this->createUrl('account_current_nrp_statuses/menu'),
	'formid'		=> 'gridactionpay',
	'commandlist'		=> $commandlist,
	));
echo '<br/>';

$this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> $pg.' for \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_current_nrp_statuses/menu',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('account_current_nrp_statuses/getgriddata_menu',array('model'=>$model_serialised)),
	'model'		=> $model,
	'searching'	=> true,
	'sorting'	=> true,
   'selections' => true,
	));
?>



