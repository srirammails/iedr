<?php
$pg = 'View Deposit Top-ups';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
   <?php
   $form = $this->beginWidget('CActiveForm', array('id' => 'xferredDoms', 'enableAjaxValidation' => true,));

   echo "Deposit Balance: € " . number_format($model->depositBalance, 2, '.', '') . "<br>";
   echo "Deposit Reservation: € " . number_format($model->depositReservation, 2, '.', '') . "<br>";
   echo "Available Balance: € " . number_format($model->availableBalance, 2, '.', '') . "<br>";

   echo '<div class="row"><h2>';
   echo $form->error($model, 'searchParams');
   echo CHtml::encode($pg) . ' over the last ';

   echo CHtml::textField('days', $model->getDays(), array('size' => 4, 'maxlength' => 3));

   echo ' days';
   echo CHtml::submitButton('Update');
   echo '</h2></div>';

   $this->endWidget();
   echo '</div>';
   echo "The Following displays the set of Deposit Top Ups over the last " . $model->getDays() . " Days.";

   $model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

   
   $this->widget('application.widgets.jqGrid.jqGridWidget', array(
       'caption' => $pg . ' for \'' . Yii::app()->user->name . '\'',
       'thisfile' => $this->createUrl('account_view_deposit_topups/menu', array('model' => $model_serialised)),
       'datasource' => $this->createUrl('account_view_deposit_topups/getGridDataTopUpHistory', array('model' => $model_serialised)),
       'model' => $model,
       'searching' => false,
       'sorting' => false,
       'selections' => false,
   ));
   ?>
