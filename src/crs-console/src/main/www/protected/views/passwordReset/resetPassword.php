<!-- START protected/views/passwordReset/resetPassword.php -->
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
    Utility::printFlashError($this->backend_errors);
    if (empty($model->errMsg)) {
        Utility::printFlashNotice($model->msg);
    } else {
        Utility::printFlashError($model->msg);
    }

    $form->focus = array($model,'username');
?>


<center><h2>Reset Password</h2></center>	
	<?php if (empty($model->secret)) {?>
	<div class="row">
		<?php echo $form->labelEx($model,'username'); ?>
		<?php echo $form->textField($model,'username', array('class'=>'showupprcase')); ?>
		<?php echo $form->error($model,'username'); ?>
	</div>
	<div class="row">
		<?php echo $form->labelEx($model,'token'); ?>
		<?php echo $form->textField($model,'token'); ?>
		<?php echo $form->error($model,'token'); ?>
	</div>
	<div class="row">
      <?php echo $form->labelEx($model,'new_password'); ?>
      <?php echo $form->passwordField($model,'new_password',array('size'=>20,'maxlength'=>16)); ?>
      <?php echo $form->error($model,'new_password'); ?>
    </div>
    <div class="row">
      <?php echo $form->labelEx($model,'repeat_new_password'); ?>
      <?php echo $form->passwordField($model,'repeat_new_password',array('size'=>20,'maxlength'=>16)); ?>
      <?php echo $form->error($model,'repeat_new_password'); ?>
    </div>
   
   <div class="row buttons">
   	<?php 
      	echo CHtml::submitButton('Change Password'); 
	?>
   </div>
   <?php } else {?>
   	<div class="hint">Your secret for Two Factor Authentication is: <?php echo $model->secret ?></div>
   <?php } ?>
<?php $this->endWidget(); ?>
<!-- END protected/views/passwordReset/resetPassword.php -->

