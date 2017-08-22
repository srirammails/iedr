<?php
/**
 * View page for DNS Server Search
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       EmailDisablerModel, EmailDisablerController::actionMenu(), jqGridComandButtonsWidget, jqGridWidget
 */

$pg = 'Email Disabler';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
echo "<p>Please choose the emails you wish to suppress or allow and click submit button.</br> 
		<span style=\"font-size: 85%\">Be sure you have authorization from your company to enable/disable these emails.</span></p>";

Utility::printFlashNotice($model->successSubmitMessage);
Utility::printFlashError($model->errorSubmitMessage);

?>
<script type="text/javascript">

	var disabledValuesByEmailId = {};

	function disableEmail(emailId) {
		disabledValuesByEmailId[emailId] = true;
	}

	function enableEmail(emailId) {
		disabledValuesByEmailId[emailId] = false;
	}

	function putModifiedDataIntoForm() {
		jQuery('#EmailDisablerModel_disabledValuesByEmailId').val(JSON.stringify(disabledValuesByEmailId));
	}
</script>

<?php

if ($model->errorDisplayMessage == ''){
	$form = $this->beginWidget('CActiveForm', array('id' => 'ModifyEmailDisablersForm'));
	echo $form->hiddenField($model, 'disabledValuesByEmailId', array('value' => 'To be filled by javascript'));
	echo CHtml::submitButton('Submit Change', array('onclick' => 'javascript: putModifiedDataIntoForm();'));
	echo '<br/>';
	echo '<br/>';

	$groupIndex = 0;
	$hugeNumRows= 100000;
	$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

	foreach ($model->viewDataGrouped as $group => $grouppagedata) {
		$datastr = CJSON::encode($grouppagedata);
		$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
			'caption'	=> $group,
			'thisfile'	=> $this->createUrl('emailDisabler/menu',array('model'=>$model_serialised)),
			'datatype'	=> 'jsonstring',
			'datastr' => $datastr,
			'model'	 => $model,
			'selections'	=> false,
			'tableId'	=> 'thisJqGrid' . $groupIndex,
			'numrows' => $hugeNumRows,
			'navGrid' => false,
			'pager' => false,
			'filterBar' => false,
		));
		$groupIndex += 1;
		echo '<br/>';
		echo '<br/>';
	}

	echo CHtml::submitButton('Submit Change', array('onclick' => 'javascript: putModifiedDataIntoForm();'));
	$this->endWidget();
}
else {
	Utility::printFlashError($model->errorDisplayMessage);
}

?>

<!-- END protected/views/emailDisabler/menu.php -->
