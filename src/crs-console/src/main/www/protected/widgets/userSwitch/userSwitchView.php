<!-- START protected/widgets/userSwitch/userSwitchView.php -->
<?php

/**
 * View file which outputs Html Form Widget for switching users
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       UserSwitchWidget, userSwitchView, UserSwitchModel, UserSwitchModel::getSwitchActionUrl()
 */

if(/*!Yii::app()->user->isGuest and*/ (Utility::getPermission() > 0) && Utility::isInternalNetwork() && NicHandleIdentity::canSwitchAccounts())
	{
	$switchmodel = new UserSwitchModel();
	$form=$this->beginWidget('CActiveForm', array(
		'method'=>'POST',
		'id'=>'userswitch_form',
		'action'=>$switchmodel->getSwitchActionUrl(),
		'enableAjaxValidation'=>false,
		));
	echo $form->label($switchmodel,'switchUser');
	echo $form->error($switchmodel,'switchUser');
	if (Utility::isDirect()) {
		echo $form->textField($switchmodel, 'switchUser', array('class'=>'showupprcase', 'autocomplete'=>'off'));
	} else {
		echo $form->dropDownList($switchmodel,'switchUser', getRegistrarsForLogin());	
	}
	echo $form->hiddenField($switchmodel,'returnUrl');
	echo CHtml::submitButton('Switch');
	$this->endWidget();
	}
?>
<!-- END protected/widgets/userSwitch/userSwitchView.php -->
