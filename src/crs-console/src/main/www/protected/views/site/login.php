<!-- START protected/views/site/login.php -->
<?php
/**
 * View page for Login Form
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       LoginForm, NicHandleIdentity, SiteController::actionLogin()
 */

$this->pageTitle=Yii::app()->name . ' - Login';
?>
<script>
var server_time_clock = {};
function init_clock(hours, minutes, seconds) {
	server_time_clock.hours = hours;
	server_time_clock.minutes = minutes;
	server_time_clock.seconds = seconds;
	server_time_clock.initialized = true;
}

function format_time(s) {
	return s < 10 ? "0" + s : s;
}

function print_clock(id) {
	if (server_time_clock.initialized) {
		if (server_time_clock.seconds < 59) {++server_time_clock.seconds;
		} else {
			server_time_clock.seconds = 0;
			if (server_time_clock.minutes < 59) {++server_time_clock.minutes;
			} else {
				server_time_clock.minutes = 0;
				if (server_time_clock.hours < 23) {++server_time_clock.hours;
				} else {
					server_time_clock.hours = 0;
				}
			}
		}
		document.getElementById(id).innerHTML = format_time(server_time_clock.hours) + ":" + format_time(server_time_clock.minutes) + ":" + format_time(server_time_clock.seconds);
	}
	setTimeout('print_clock("' + id + '");', '1000');
}
</script>

<h1>Login</h1>

<p>Please fill out the following form with your login credentials:</p>

<div class="form">
<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'login-form',
	'enableAjaxValidation'=>false,
	));
$form->focus = array($model,'username');
?>
	<p class="note">Fields with <span class="required">*</span> are required.</p>

<?php
   if(Utility::isInternalNetwork())
	{
?>
	<div class="row">
		<?php echo $form->labelEx($model,'internalUser'); ?>
		<?php echo $form->dropdownList($model,'internalUser', Utility::getIEDRUsers()); ?>
		<?php echo $form->error($model,'internalUser'); ?>
	</div>
<?php
	} else {
      $model->internalUser = NicHandleIdentity::$NOT_INTERNAL;
      echo $form->hiddenField($model,'internalUser');
	}
?>
	<div class="row">
		<?php echo $form->labelEx($model,'username'); ?>
		<?php echo $form->textField($model,'username', array('class'=>'showupprcase', 'autocomplete'=>'off')); ?>
		<?php echo $form->error($model,'username'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'password'); ?>
		<?php echo $form->passwordField($model,'password', array('size'=>16,'maxlength'=>16, 'autocomplete'=>'off')); ?>
		<?php echo $form->error($model,'password'); ?>
	</div>

    <div class="row">
        <?php echo $form->labelEx($model,'google_pin'); ?>
        <?php echo $form->passwordField($model,'google_pin', array('autocomplete'=>'off')); ?>
        <?php echo $form->error($model,'google_pin'); ?>
        <div class="hint">Server time is: <span id="serverTime"></span></div>        
    </div>

    <div class="row buttons">
        <?php echo CHtml::submitButton('Login'); ?>
    </div>
    <div><b>Note: If you have not yet logged in since Two-Factor Authentication has been introduced, you will need to reset your password and set up Two-Factor Authentication. </b></div>
    <div><a href="<?php echo $this->createUrl('passwordReset/request'); ?>">Reset password</a></div>
    <div><a href="<?php echo $this->createUrl('site/newDirectAccount'); ?>">Create new account</a> </div>
    <div><a href="https://www.iedr.ie/site-help/">Having problems logging in?</a></div>
    <div><a href="<?php echo $this->createUrl('authcodePortal/request'); ?>">Get your authcode</a> </div>

   <script>init_clock(<?php echo $model->serverTime->hours.",".$model->serverTime->minutes.",".$model->serverTime->seconds ?>);</script>
   <script>print_clock("serverTime");</script>
<?php $this->endWidget(); ?>
</div>
   <!-- form -->
<!-- END protected/views/site/login.php -->
