<!-- START protected/views/grid/confirm_action.php -->
<?php
/**
 * View page for Grid Action Confirmation
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       GridController, GridSelectionModel
 */

$pg = 'Confirm Action';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;

$cmdString = '';
switch ($model->command) {
    case 'noautorenew':
        $cmdString = 'Unset Autorenew/RenewOnce';
        break;
    case 'revert':
        $cmdString = 'Billable';
        break;
    case 'autorenew':
        $cmdString = 'Autorenew';
        break;
    case 'authcodedownload':
        $cmdString = 'Download Authcodes for';
        break;
    default:
        $cmdString = '(Do Something)';
        break;
}
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<h4>Please Confirm you want to <b><i><?php echo $cmdString; ?></i></b> these Domains:</h4>
<?php
echo '<div class="form">';
$submitButtonId='proceedSubmitButton';
$form = $this->beginWidget('CActiveForm', array(
    'id' => 'ConfirmAction',
    'enableAjaxValidation' => true,
	'htmlOptions' => array('onsubmit' => "disableSubmit('$submitButtonId')"),
    'clientOptions' => array(
        'validateOnSubmit' => true,
        'beforeValidate' => "js:checkSelected",    
    ),
));

echo $form->hiddenField($model, 'domainlist');
echo $form->hiddenField($model, 'command');
echo $form->hiddenField($model, 'returnurl');
echo $form->hiddenField($model, 'period');

$this->widget('application.widgets.js.JavaScriptUtils', array('model' => $model));

echo '<table class="formfields gridtable">';
foreach ($model->domainListToArray() as $k => $value) {
    $mbrnam = DynamicDomainListModel::domainToVarPrefix($value);
    $mbr_conf = $mbrnam . '_confirmed';
    echo '<tr><td class="gridtablecell"><div class="row">';
    echo $form->error($model, $mbr_conf);
    echo $form->checkBox($model, $mbr_conf, array('checked' => true));
    echo '</div></td><td class="gridtablecell"><div class="row">';
    echo $form->labelEx($model, $mbr_conf);
    echo '</div></td>';
    if ($model->command == 'autorenew') {
        echo '<td class="gridtablecell">' . $form->dropDownList($model, 'list_' . $value, array('Autorenew' => 'Autorenew', 'RenewOnce' => 'RenewOnce'), array('onchange' => 'javascript:setup(this,"' . $value . '");')) . '</td>';
    }
    echo '</tr>';
}
echo '</table></div>';
echo '<div class="row buttons">' . CHtml::submitButton('Proceed', array('id' => $submitButtonId)) . '</div><br>';

$this->endWidget();
echo AuthOnlyBaseController::returnToUrlButton($model->returnurl);
?>
<!-- END protected/views/grid/confirm_action.php -->
