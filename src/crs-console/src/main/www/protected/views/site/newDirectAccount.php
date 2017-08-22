<!-- START protected/views/site/newDirectAccount.php -->
<?php
/**
 * View page for Nic-Handle creation
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Nichandle_Details, NichandlesController::actionCreatenichandle(), NichandlesController::actionDynamiccounties(), ReturningController::outputReturningFormHtml()
 */

$pg = 'Create Direct Account';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>

<h2>Create New Account</h2>
<div class="form">
<?php

$countryAjaxOpts = array('ajax' =>
			array(
				'type'=>'POST', //request type
				'url'=>CController::createUrl('site/dynamiccounties'), //url to call.
				'update'=>'#Nichandle_Details_county', //selector to update
				));

if($model->error != null)
	echo '<span class="error">'.$model->error.'</span>';
else
      
	if($model->message != null)
		{
		if($model->message=='DIRECT_ACCOUNT_CREATED') {
            echo '<span class="error" id="direct_account_created_message">Account Created with NicHandle: '.$model->nicHandleId.'</span>';
            if (isset($secret)) {
            	echo '<br/><span class="error" id="direct_account_created_message_secret">You secret code for use with Two Factor Authentication is: '.$secret.'</span>';
            }
		}
	}
	else
		{
		$form = $this->beginWidget('CActiveForm', array(
            'id'=>'Direct_Account_Details',
            'enableAjaxValidation'=>true,
            'clientOptions'=>array(
                'validateOnSubmit'=>true,
            ),
        ));
		$form->focus = array($model,'name');
		if (!isset($confirmation))
			$confirmation = false;

?>
<?php 
function echoTextField($confirmation, $form, $model, $name, $fieldParams) {
	if ($confirmation) {
		echo $form->hiddenField($model, $name);
		echo encode($model->$name);
	} else {
		echo $form->textField($model,$name, $fieldParams);
	}
}

function echoTextArea($confirmation, $form, $model, $name, $fieldParams) {
	if ($confirmation) {
		echo $form->hiddenField($model, $name);
		echo encode($model->$name);
	} else {
		echo $form->textArea($model,$name, $fieldParams);
	}
}

function echoDropDownList($confirmation, $form, $model, $name, $options, $fieldParams=null) {
	if ($confirmation) {
		echo $form->hiddenField($model, $name);
		echo encode($model->$name);
	} else {
		echo $form->dropDownList($model,$name, $options, $fieldParams);
	}
}
?>

<p class="note">Please fill in the form below.<br><font color="red">Note:</font> Fields marked with an asterisk (*) must be completed.</p>
<table class="formfields">
	<tr>
		<td><?php echo $form->labelEx($model,'name'); echo $form->error($model,'name');?></td>
		<td><div class="row"><?php echoTextField($confirmation, $form, $model, 'name', array('size' => '40', 'autocomplete'=>'off')); ?></div></td>
    </tr>
	<tr>
		<td><?php echo $form->labelEx($model,'email'); echo $form->error($model,'email');?></td>
		<td><div class="row"><?php echoTextField($confirmation, $form, $model,'email', array('size'=>'40'));?></div></td>
	</tr>
	<tr>
		<td><?php echo $form->labelEx($model,'phones'); echo $form->error($model,'phones');?></td>
		<td><div class="row"><?php echoTextArea($confirmation, $form, $model,'phones', array('rows'=>4, 'cols'=>27)); ?></div></td>
	</tr>
	<tr>
		<td><?php echo $form->labelEx($model,'faxes'); echo $form->error($model,'faxes');?></td>
		<td><div class="row"><?php echoTextArea($confirmation, $form, $model,'faxes', array('rows'=>4, 'cols'=>27)); ?></div></td>
	</tr>
	<tr>
		<td><?php echo $form->labelEx($model,'companyName'); echo $form->error($model,'companyName'); ?></td>
		<td><div class="row"><?php echoTextField($confirmation, $form, $model,'companyName', array('size'=>'40', 'autocomplete'=>'off')); ?></div></td>
	</tr>
	<tr>
		<td><?php echo $form->labelEx($model,'address'); echo $form->error($model,'address'); ?></td>
		<td><div class="row"><?php echoTextArea($confirmation, $form, $model,'address', array('rows'=>4, 'cols'=>45)); ?></div></td>
	</tr>
	<tr>
		<td><?php echo $form->labelEx($model,'country'); echo $form->error($model,'country'); ?></td>
		<td><div class="row"><?php echoDropDownList($confirmation, $form, $model,'country', getCountryOptions(), $countryAjaxOpts); ?></div></td>
	</tr>
	<tr>
		<td><?php echo $form->labelEx($model,'county'); echo $form->error($model,'county'); ?></td>
		<td><div class="row"><?php echoDropDownList($confirmation, $form, $model,'county', getCountyOptions($model->country));?></div></td>
	</tr>
	<?php if (!$confirmation) {?>
	<tr>
		<td><?php echo $form->labelEx($passwordModel,'new_password'); echo $form->error($passwordModel,'new_password');?></td>
		<td><div class="row"><?php echo $form->passwordField($passwordModel,'new_password',array('size'=>16,'maxlength'=>16, 'autocomplete'=>'off'));    ?></div></td></tr>
    <tr>
    	<td><?php echo $form->labelEx($passwordModel,'repeat_new_password'); echo $form->error($passwordModel,'repeat_new_password'); ?></td>
    	<td><div class="row"><?php echo $form->passwordField($passwordModel,'repeat_new_password',array('size'=>16,'maxlength'=>16, 'autocomplete'=>'off')); ?></div></td></tr>
    <tr>
    <?php } ?>
    	<td><?php echo $form->labelEx($passwordModel,'useTwoFactorAuthentication'); echo $form->error($passwordModel,'useTwoFactorAuthentication'); ?></td>
    	<td><div class="row">
    	<?php
    	if ($confirmation) {
			echo ($passwordModel->useTwoFactorAuthentication ? 'yes' : 'no');
			echo $form->hiddenField($passwordModel, 'new_password');
			echo $form->hiddenField($passwordModel, 'repeat_new_password');
			echo $form->hiddenField($passwordModel, 'useTwoFactorAuthentication');
		} else {
			echo $form->checkBox($passwordModel,'useTwoFactorAuthentication');
		}
    	?>
    	</div></td></tr>
	<tr>
		<td></td>
		<td><div class="row buttons">
		<?php
		if ($confirmation) {
			echo CHtml::submitButton('Confirm');
			echo CHtml::submitButton('Back');
		} else {
			echo CHtml::submitButton('Create');
		}
		?></div></td>
	</tr>
</table>
<?php
		$this->endWidget();
		}
?>
</div>

<!-- END protected/views/site/newDirectAccount.php -->
