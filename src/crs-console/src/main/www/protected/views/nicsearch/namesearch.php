<!-- START protected/views/nicsearch/namesearch.php -->
<?php
/**
 * View page for Nic-Handle Search
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       NicSearchModel, NicsearchController::actionNamesearch(), jqGridWidget
 */

$pg = 'Find';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
$this->outputReturningFormHtml($model,'NicSearchModel_returningData_nic','Return to Form');
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> 'All Nic Handles registered with \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('contacts/namesearch',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('nicsearch/getgriddata',array('model'=>$model_serialised)),
	'model'		=> $model,
   'searching'	=> true,
	'sorting'	=> true,
	'selections'	=> false,
   'get_nic_handle' => true,
	));
?>
<!-- END protected/views/nicsearch/namesearch.php -->
