<!-- START protected/views/site/ChangePassword.php -->
<?php
/**
 * View page for Password Change
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see      ChangePasswordForm, SiteController::actionChangePassword()
 */

?>
<div class="form">
<?php $form=$this->beginWidget('CActiveForm', array(
        'id'=>'change-password',
        'enableAjaxValidation'=>false,
        ));

if($model->msg != ""){
	echo "<div class=\"flash-success\">" . $model->msg . "</div>";
}
if(Yii::app()->user->authenticatedUser->passwordChangeRequired) {
	echo "<div class='error'>Password change is required</div>"; 
}

?>


<center><h2>Change Password</h2></center>

   <div class="row">
      <?php echo $form->labelEx($model,'old_password'); ?>
      <?php echo $form->passwordField($model,'old_password',array('size'=>20,'maxlength'=>20)); ?>
      <?php echo $form->error($model,'old_password'); ?>
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
      <?php echo CHtml::submitButton('Change Password?'); ?>
   </div>

<?php $this->endWidget(); ?>
<!-- END protected/views/site/ChangePassword.php -->

