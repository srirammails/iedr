<!-- START protected/views/tickets/editticket.php -->
<?php
/**
 * View page for Ticket Edit
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       EditTicketModel, TicketsController::actionEditticket()
 */
$pg = 'Edit Ticket';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>

<form method="post" id="hid">
    <input type="hidden" id="tok" name="YII_CSRF_TOKEN" value="unset">
    <input type="hidden" id="data" name="formdata" value="unset">
    <input type="hidden" id="mode" name="mode" value="unset">
    <input type="hidden" id="nictype" name="nictype" value="unset">
    <input type="hidden" id="nic" name="nic" value="unset">
    <input type="hidden" id="returnurl" name="returnurl" value="index.php?r=tickets/editticket">
</form>

<?php

function nicHandleWidget($controller, $nic_type, $inpIdNic2edit = 'LeftBlank', $showFull)
{
    $urlDDS = $controller->createUrl('tickets/editticketserialized');
    $form = 'EditTicketModel';
    $it = Yii::app()->request->csrfToken;
    $params = "'$nic_type','$form','$urlDDS','$inpIdNic2edit','$it' ";
    echo '<a href="#" id="nicWidgetNew_' . $nic_type . '" onclick="getDataAndPostForm(\'new\',' . $params . ');">New</a> ';
    if ($showFull) {
        echo ' / <a href="#" id="nicWidgetFnd_' . $nic_type . '" onclick="return getDataAndPostForm(\'find\',' . $params . ');">Find</a> / ';
        echo '<a href="#" id="nicWidgetEdt_' . $nic_type . '" onclick="return getDataAndPostForm(\'edit\',' . $params . ');">Edit</a>';
    }
}

?>

<script>

    var uploaderDocumentsPresentWarning = null;
    $(document).bind("uploaderFileCountChange", function(event, documentCount) {
        if (documentCount >0) {
            var docMsg = "";
            if (documentCount > 1) {
                docMsg = "are " + documentCount + " documents";
            } else {
                docMsg = "is " + documentCount + " document";
            }
            uploaderDocumentsPresentWarning = "There " + docMsg + " selected for upload";
        } else {
            uploaderDocumentsPresentWarning = null;
        }
    });

    function getDataAndPostForm(mode, nictype, srcf, serializeUrl, editNIC, it) {
        // this fn called on click (link or button) does:
        // (1) send form to server, gets serialized model back
        // (2) sets up hidden form in this page
        // (3) submits the form, navigating away to the next page
        if (uploaderDocumentsPresentWarning !== null && !confirm("Leaving this page will cause resetting some values:\n" + uploaderDocumentsPresentWarning)) {
            return false;
        }

        var dispatchUrl;
        switch (mode) {
            case "new":
                dispatchUrl = 'index.php?r=nichandles/createnichandle&from=1';
                break;
            case "find":
                dispatchUrl = 'index.php?r=nicsearch/namesearch&from=1';
                break;
            case "edit":

                var theNic = document.getElementById('EditTicketModel_' + nictype).value;
                dispatchUrl = 'index.php?r=nichandles/editnichandle&id=' + theNic + '&from=1';
                $("#hid #nic").val(theNic);
                break;
        }
        // (1) post form ('srcf') to server, retrieve serialized model via ajax
        //serializeUrl = 'tickets/main';
        $.post(serializeUrl, $("#" + srcf).serialize(), function (ajaxdata) {
            $("#hid").attr("action", dispatchUrl);
            $("#hid #data").val(ajaxdata); // the serialised form data
            $("#hid #mode").val(mode); // new/find/edit
            $("#hid #nictype").val(nictype); // which nic role for the mode (for updating the correct field, on return to this page)

            // Yii doesn't set an ID for the token hidden-input, so find by name
            var url = document.getElementById('returnurl').value;
            var ticket = document.getElementById('EditTicketModel_id').value;
            document.getElementById('returnurl').value = (url + '&id=' + ticket);
            // set hidform token value
            var hitok = $("#hid #tok")[0];
            $(hitok).val(it);
            // (3) post form with serialized model
            $("#hid").submit();
        });
    }
</script>

<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
    <?php
    if ($model->errors != null)
        echo '<span class="error">' . print_r($model->errors, true) . '</span>';
    else {
        $formEditable = empty($model->checkedOutTo);
        $canUploadDocuments = isset($uploaderModel) && Utility::isLoggedInAs($model->billingContact) && ($model->type == 'Registration' || $model->type == 'Modification');
        if (!$formEditable) {
            echo '<div class="flash-notice">';
            echo   'Ticket cannot be edited right now as it\'s being processed by IEDR.';
            if ($canUploadDocuments) {
                echo '<br/>You can still upload new documents regarding this ticket';
            }
            echo '</div>';
        }
        $form = $this->beginWidget('CActiveForm', array(
            'id' => 'EditTicketModel',
            'method' => 'post',
            'enableAjaxValidation' => true,
            'clientOptions' => array(
                'validateOnSubmit' => $formEditable,
            ),
            'htmlOptions' => array('enctype' => 'multipart/form-data'),
        ));
        echo '<table border="1" class="formfields"><tbody>';

        echo $form->hiddenField($model, 'id');
        echo $form->hiddenField($model, 'editable');

        $ro_flds = array('domainName', 'hostmastersRemark', 'type', 'expiryDate', 'adminStatus', 'techStatus', 'financialStatus', 'billingContact', 'period', 'paymentType', 'charityCode');
        foreach ($ro_flds as $ro_fld_model_name) {
            echo '<tr><td>' .
                $form->label($model, $ro_fld_model_name) . '</td><td>' .
                $form->textField($model, $ro_fld_model_name, array('disabled' => 'disabled')) . '</td></tr>';
        }

        $htmlOptions = $formEditable ? array() : array('disabled' => 'disabled');
        echo BR . '<tr><td>';
        echo $form->labelEx($model, 'applicant');
        echo $form->error($model, 'applicant') . '</td><td><div class="row">';
        echo $form->dropDownList($model, 'applicant', get_class_category(), $htmlOptions) . '</div></td></tr>';

        $fullNicHandleWidget = Utility::isDirect() || Utility::isRegistrar() || Utility::isSuperRegistrar();

        foreach (array('domainHolder', 'requestersRemark') as $rw_fld_model_name) {
            echo '<tr><td>' .
                $form->label($model, $rw_fld_model_name) .
                $form->error($model, $rw_fld_model_name) . '</td><td><div class="row">' .
                $form->textField($model, $rw_fld_model_name, $htmlOptions) . '</div></td></tr>';
        }

        $w = $this->widget('application.widgets.nameservers.nameserversWidget', array(
                'form' => $form,
                'model' => $model,
                'domainId' => 'EditTicketModel_domainName',
                'disabled' => !$formEditable,
        ));

        foreach (array('adminContact_0', 'adminContact_1', 'techContact') as $rw_fld_model_name) {
            echo '<tr><td>';
            echo $form->label($model, $rw_fld_model_name);
            echo $form->error($model, $rw_fld_model_name) . '</td><td><div class="row">';
            echo $form->textField($model, $rw_fld_model_name, $htmlOptions);
            if ($formEditable){
                nicHandleWidget($this, $rw_fld_model_name, $rw_fld_model_name, $fullNicHandleWidget);
            }
            echo '</div></td></tr>';
        }
        // Transfer is string defined in DomainOperationType java enum, passed as string to php
        if ($canUploadDocuments) {
            if ($uploaderModel->hasErrors()) {
                echo "<tr><td colspan=\"2\">";
                echo $form->errorSummary($uploaderModel);
                echo "</td></tr>";
            }

            echo "<tr><td colspan=\"2\">";
            $this->widget('application.widgets.documentUploader.DocumentUploaderWidget', array(
                'uploaderModel' => $uploaderModel,
                'domains' => $model->domainName,
            ));
            echo "</td></tr>";
        }
        if ($formEditable || $canUploadDocuments) {
            echo '<tr><td></td><td>' . CHtml::submitButton('Update Ticket') . '</td></tr>';
        }
        echo '</tbody></table>';
        $this->endWidget();
    }
    ?>
</div>
<!-- END protected/views/tickets/editticket.php -->

