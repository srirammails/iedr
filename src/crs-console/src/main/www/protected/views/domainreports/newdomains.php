<!-- START protected/views/domainreports/newdomains.php -->
<?php
/**
 * View page for Domain Reports - Newly Registered Domains
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       NewDomainsModel, DomainreportsController::actionNewdomains(), jqGridWidget
 */

$pg = 'Newly Registered Domains';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
<?php
$form = $this->beginWidget('CActiveForm', array('id'=>'NewDomains','enableAjaxValidation'=>true,));
echo '<div class="row"><h2>';
echo CHtml::encode($pg).' in the last ';
echo $form->textField($model,'days',array('size'=>4));
echo $form->error($model,'days');
echo ' days';
echo CHtml::submitButton('Update');
echo '</h2></div>';
$this->endWidget();
echo '</div>';

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);


$this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
	'pk_column_index'	=> 0,
	'actionurl'		=> $this->createUrl('domainreports/confirm'),
	'returnurl'		=> $this->createUrl('domainreports/newdomains'),
	'commandlist'		=> array(
               'autorenew' => 'Set %n Domains Autorenew',
					),
	));

echo '<br/>';

$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'		=> 'New Domains registered with \''.Yii::app()->user->name.'\'',
	'thisfile'		=> $this->createUrl('domainreports/newdomains',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('domainreports/getgriddata',array('model'=>$model_serialised)),
	'model'			=> $model,
   'selections'	=> true,
	));
?>
<!-- END protected/views/domainreports/newdomains.php -->
