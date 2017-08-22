<?php
$pg = 'Reauthorise CC Transaction';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<?php 
$models = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> $pg.' for \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_reauthorise_cc_transaction/menu',array('model' => $models)),
	'datasource'	=> $this->createUrl('account_reauthorise_cc_transaction/getgriddata_re',array('model' => $models)),
	'model'		=> $model,
	'searching'	=> true,
	'sorting'	=> false,
	));
?>
