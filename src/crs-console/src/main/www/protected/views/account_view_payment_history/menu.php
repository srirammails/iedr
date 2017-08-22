<?php
$pg = 'View Payment History';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>


<?php 

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> $pg.' for \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_view_payment_history/menu',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('account_view_payment_history/getgriddata',array('model'=>$model_serialised)),
	'model'		=> $model,
	'searching'	=> true,
	'sorting'	=> true,
	));
?>