<!-- START protected/views/accounts/menu.php -->
<?php
/**
 * View page for Accounts Menu
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */

$pg = 'Failed Transactions';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
<?php

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> 'Failed Transactions \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_failed_transactions/menu',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('account_failed_transactions/getgriddata',array('model'=>$model_serialised)),
	'model'		=> $model,
	'searching'	=> true,
	'sorting'	=> true,
	));
?>