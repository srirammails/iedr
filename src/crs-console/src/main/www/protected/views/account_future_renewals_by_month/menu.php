<!-- START protected/views/accounts_renewpay_advpay/current_newandrenewals.php -->
<?php
/**
 * View page for Accounts Renewal-Payments - Future Renewals
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Acc_RnP_AdvPay_RnR_Model, Accounts_renewpay_advpayController::actionCurrent_newandrenewals(), Accounts_renewpay_advpayController::actionGetgriddatacurrrnr(), GridUtility::makeMonthYearDateSelectUpdateForm(), jqGridComandButtonsWidget, jqGridWidget
 */

$pg = 'Future Renewals by month';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php printBreadcrumbTrail($pg); ?></h2>
<?php

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

GridUtility::makeMonthYearDateSelectUpdateForm($this,$pg,'account_future_renewals_by_month', $model, 'date');


$commandlist = array();
if(!Utility::isDirect()) {
   $commandlist = array(
					'payonline'  => 'Pay %n Domains Online (%fee €)',
					'paydeposit' => 'Pay %n Domains From Deposit (%fee €)',
               'voluntary' => 'Put %n Domains into Voluntary NRP',
               'autorenew' => 'Set %n Domains Autorenew',
					);
} else {
   $commandlist = array(
					'payonline'  => 'Pay %n Domains Online (%fee €)',
               'voluntary' => 'Put %n Domains into Voluntary NRP',
					);
}


$this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
	'pk_column_index'	=> 0,
	'actionurl'		=> $this->createUrl('account_future_renewals_by_month/confirm_currnr'),
	'returnurl'		=> $this->createUrl('account_future_renewals_by_month/menu'),
	'commandlist'		=> $commandlist,
	));

echo '<br/>';

$this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> $pg.' for \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_future_renewals_by_month/menu',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('account_future_renewals_by_month/getgriddatacurrrnr',array('model'=>$model_serialised)),
	'model'		=> $model,
	'searching'	=> true,
	'sorting'	=> true,
	'selections'	=> true,
	));
?>
