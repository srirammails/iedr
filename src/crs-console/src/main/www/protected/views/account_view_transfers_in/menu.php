<?php
$pg = 'View Transfers In';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
<?php
$form = $this->beginWidget('CActiveForm', array('id'=>'xferredDoms','enableAjaxValidation'=>true,));
echo '<div class="row"><h2>';
echo $form->error($model, 'days');
echo CHtml::encode($pg).' in the last ';
echo $form->textField($model, 'days', array('size' => 4));
echo ' days';
echo CHtml::submitButton('Update');
echo '</h2></div>';

$this->endWidget();
echo '</div>';

echo "<p>There were " . $model->totalrows . " domains transferred to your account over the last " . $model->days . " days or " . round($model->totalrows/date('t'),2) . " per day</p>";

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
        'caption'               => 'Domains Transferred in \''.Yii::app()->user->name.'\' in the last ' . $model->days . ' days',
        'thisfile'              => $this->createUrl('account_view_transfers_in/menu',array('model'=>$model_serialised)),
        'datasource'            => $this->createUrl('account_view_transfers_in/getgriddata_in',array('model'=>$model_serialised)),
        'model'                 => $model,
        ));
?>
<!-- END protected/views/accounts_reports/transferto.php -->




















