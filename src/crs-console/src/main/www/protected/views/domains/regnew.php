<!-- START protected/views/domains/regnew.php -->
<?php
/**
 * View page for Domains - Register New
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Domains_RegNew, DomainsController::actionRegnew()
 */

$pg = 'Register New';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'RegnewForm',
	'enableAjaxValidation'=>true,
    'clientOptions'=>array(
        'validateOnSubmit'=>true,
    ),
));
$form->focus = array($model,'domain_names');
?>
<p class="note">Register a domain / multiple domain names (all for the same registrant)</p>
<p class="note">Enter a list of domains to check availability</p>
<div class="row">
	<?php echo $form->error($model,'domain_names'); ?>
	<?php echo $form->labelEx($model,'domain_names'); ?>
	<?php echo $form->textArea($model,'domain_names',array('rows'=>6, 'cols'=>50)); ?>
</div>


<div class="row buttons"><?php echo CHtml::submitButton('Check Availability'); ?></div>
<?php $this->endWidget(); ?>
</div><!-- form -->

<!-- END protected/views/domains/regnew.php -->
