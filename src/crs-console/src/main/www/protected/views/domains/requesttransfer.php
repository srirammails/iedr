<?php
$pg = 'Request Transfer';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>


<div class="form">
<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'RegnewForm',
	'enableAjaxValidation'=>true,
	));

$form->focus = array($model,'domain_names');

?>
<p class="note">Enter domain to check availability to request transfer</p>
<div class="row">
    <?php echo $form->error($model,'domain_name'); ?>
    <?php echo $form->labelEx($model,'domain_name'); ?>
    <?php echo $form->textField($model,'domain_name', array('rows'=>6, 'cols'=>50)); ?>
</div>
<div class="row buttons"><?php echo CHtml::submitButton('Transfer'); ?></div>
<?php $this->endWidget(); ?>
</div><!-- form -->

<script type="text/javascript">
    <?php if (Yii::app()->user->hasFlash('error')) { ?>
         alert("<?php echo Yii::app()->user->getFlash('error') ?>");
    <?php } ?>
</script>
