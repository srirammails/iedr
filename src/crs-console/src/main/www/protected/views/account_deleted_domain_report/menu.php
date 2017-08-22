<?php
$pg = 'Deleted Domain Report';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>


<?php
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> $pg.' for \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_deleted_domain_report/menu',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('account_deleted_domain_report/getgriddatadeleted',array('model'=>$model_serialised)),
	'model'		=> $model,
	'searching'	=> false,
	'sorting'	=> true,
	));

?>
