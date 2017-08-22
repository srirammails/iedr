<!-- START protected/views/domainreports/autorenewed.php -->
<?php
/**
 * View page for Domain Reports - Autorenewed Domains
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       ARDomainModel, DomainreportsController::actionAutorenewed(), jqGridWidget
 */

$pg = 'View Autorenew Domains';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php

$this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
	'pk_column_index'	=> 1,
	'actionurl'		=> $this->createUrl('account_view_autorenew_domains/confirm_billable'),
	'returnurl'		=> $this->createUrl('account_view_autorenew_domains/menu'),
	'commandlist'		=> array('noautorenew'  => 'Unset Autorenew in %n Domains' ),
	));

echo '<br>';

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> 'All Autorenewed Domains registered with \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_view_autorenew_domains/menu',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('account_view_autorenew_domains/getgriddata',array('model'=>$model_serialised)),
	'model'		=> $model,
   'selections'	=> true,
	));
?>
<!-- END protected/views/domainreports/autorenewed.php -->
