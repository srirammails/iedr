<!-- START protected/views/passwordReset/request.php -->
<?php
/**
 * Password reset 
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see      PasswordResetController, ResetPasswordForm
 */

?>
<div class="form">
<?php $form=$this->beginWidget('CActiveForm', array(
        'id'=>'changeTFASettings',
        'enableAjaxValidation'=>false,
        ));		
    Utility::printFlashNotice($model->msg);
   $form->focus = array($model,'username');
?>


<center><h2>Request Password Change</h2></center>	
	
	<div class="row">
		<?php echo $form->labelEx($model,'username'); ?>
		<?php echo $form->textField($model,'username', array('class'=>'showupprcase')); ?>
		<?php echo $form->error($model,'username'); ?>
	</div>
   
   <div class="row buttons">
   	<?php 
      	echo CHtml::submitButton('Request Token'); 
	?>
   </div>
<?php $this->endWidget(); ?>
<!-- END protected/views/passwordReset/request.php -->

