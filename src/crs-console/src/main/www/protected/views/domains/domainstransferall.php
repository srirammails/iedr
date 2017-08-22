<?php

$pg = 'Domains Transfer';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> 'Domains To Transfer \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('domains/domainstransferall',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('domainreports/getgriddata',array('model'=>$model_serialised)),
	'model'		=> $model,
	));
?>

