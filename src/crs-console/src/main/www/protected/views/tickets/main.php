<!-- START protected/views/tickets/main.php -->
<?php
/**
 * View page for Ticket Grid
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       AllTicketsModel, TicketsController::actionDeleteticket()
 */

$pg = 'Tickets';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
<?php
	if($model->message != null){
		echo '<div class="flash-notice">' . $model->message . '</div>';
	}

	$form = $this->beginWidget('CActiveForm', array('id'=>'ticketRange','enableAjaxValidation'=>true,));
	echo '<div class="row"><h2>';
		echo CHtml::encode($pg).' created over the last ';
		echo CHtml::textField('days',$model->days,array('size'=>4, 'maxlength'=>3));

		$form->textField($model,'days');
		echo ' days ';
		echo CHtml::submitButton('Update');
	echo '</h2></div>';

	$this->endWidget();
echo '</div>';

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> 'Tickets for \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('tickets/menu',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('tickets/getgriddata',array('model'=>$model_serialised)),
	'model'		=> $model,
	'searching'	=> true,
	'sorting'	=> true,
	'allowAll'	=> true,
	));
?>
<!-- END protected/views/tickets/main.php -->
