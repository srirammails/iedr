<?php
$pg = 'View Registrar Details';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php printBreadcrumbTrail($pg); ?></h2>

<script>
    function removeNic(param) {
        if (confirm('Are you sure you want to remove this contact?')) {
            switch (param) {
                case 'tech':
                    document.getElementById('RegistrarDetailsModel_techContact').value = '';
                    break;
            }
        }
    }


    function getDataAndPostForm(mode, nictype, srcf, serializeUrl, editNIC) {
        // this fn called on click (link or button) does:
        // (1) send form to server, gets serialized model back
        // (2) sets up hidden form in this page
        // (2) submits the form, navigating away to the next page
//      
//      var domain = document.getElementById('ViewDomainModel_domain_name').value;
//      var u = document.getElementById('returnurl').value;
//      var url = u + '&id=' + domain;
        document.getElementById('returnurl').value = 'index.php?r=registrar_details/viewregistrar';

        var dispatchUrl;
        switch (mode) {
            case "new":
                dispatchUrl = 'index.php?r=nichandles/createnichandle&from=1';
                break;
            case "find":
                dispatchUrl = 'index.php?r=nicsearch/namesearch&from=1';
                break;
            case "edit":
                var theNic = $('#' + editNIC).val();
                if (nictype == 'techContact') {
                    theNic = document.getElementById('RegistrarDetailsModel_techContact').value;
                }

                dispatchUrl = 'index.php?r=nichandles/editnichandle&id=' + theNic + '&from=1';
                $("#hid #nic").val(theNic);
                break;
        }
        // (1) post form ('srcf') to server, retrieve serialized model via ajax
        $.post(serializeUrl, $("#" + srcf).serialize(), function (ajaxdata) {
            // (2) set up form
            $("#hid").attr("action", dispatchUrl);
            $("#hid #data").val(ajaxdata); // the serialised form data
            $("#hid #mode").val(mode); // new/find/edit
            $("#hid #nictype").val(nictype); // which nic role for the mode (for updating the correct field, on return to this page)

            // Yii doesn't set an ID for the token hidden-input, so find by name
            var itok = $('#' + srcf + ' input[name="YII_CSRF_TOKEN"]')[0];
            // set hidform token value
            var hitok = $("#hid #tok")[0];
            $(hitok).val($(itok).val());
            // (2) post form with serialized model
            $("#hid").submit();
        });
    }
</script>

<div class="form">


    <?php

    function nicHandleWidget($controller, $nic_type, $inpIdNic2edit, $what)
    {
        $urlDDS = $controller->createUrl('registrar_details/domaindetailsserialized');
        $form = 'ViewRegistraForm';
        $params = "'$nic_type','$form','$urlDDS','$inpIdNic2edit'";
        $what = "'$what'";

        echo '<div id=' . $what . ' >';
        echo '<a href="#" id="nicWidgetNew_' . $nic_type . '" onclick="getDataAndPostForm(\'new\',' . $params . ');">New</a> / ';
        echo '<a href="#" id="nicWidgetFnd_' . $nic_type . '" onclick="getDataAndPostForm(\'find\',' . $params . ');">Find</a> / ';
        echo '<a href="#" id="nicWidgetEdt_' . $nic_type . '" onclick="getDataAndPostForm(\'edit\',' . $params . ');">Edit</a> / ';
        echo '<a href="#" id="nicWidgetDel_' . $nic_type . '" onclick="removeNic(' . $what . ');">Delete</a>';
        echo '</div>';
    }

    ?>

    <form method="post" id="hid">
        <input type="hidden" id="tok" name="YII_CSRF_TOKEN" value="unset">
        <input type="hidden" id="data" name="formdata" value="unset">
        <input type="hidden" id="mode" name="mode" value="unset">
        <input type="hidden" id="nictype" name="nictype" value="unset">
        <input type="hidden" id="nic" name="nic" value="unset">
        <input type="hidden" id="returnurl" name="returnurl" value="index.php?r=registrar_details/viewregistrar">
        <input type="hidden" id="modify" value="0">
    </form>

    <?php

    $roOpts = array('readonly' => 'false');
    $roEnabled = array('enabled' => 'true');
    $roEnabled['size'] = 15;
    $roOptsSmall = $roOpts;
    $roOptsSmall['size'] = 12;

    if (strlen($model->message) > 0) {
        echo "<div class =\"flash-notice\">";
        echo $model->message . "<br>";
        echo "</div><br>";
    }

    $form = $this->beginWidget('CActiveForm', array(
        'id' => 'ViewRegistraForm',
        'enableAjaxValidation' => true,
    ));
    ?>
    <table border="1" class="formfields">
        <tbody>
        <tr>
            <td><?php echo $form->labelEx($model, 'techContact'); echo $form->error($model, 'techContact'); ?></td>
            <td>
                <div
                    class="row"><?php echo $form->textField($model, 'techContact'); nicHandleWidget($this, 'techContact', 'techContact1', 'tech'); ?></div>
            </td>
        </tr>
        <?php $this->widget('application.widgets.nameservers.nameserversWidget', array(
            'form' => $form,
            'model' => $model,
            'self_arrange' => false,
            'dnsCheck' => false,
        )); ?>
        <tr>
            <td><?php echo $form->label($model, 'dnsNotificationPeriod'); echo $form->error($model, 'dnsNotificationPeriod'); ?></td>
            <td>
                <div
                    class="row"><?php echo $form->textField($model, 'dnsNotificationPeriod', array('maxlength' => 2, 'size' => 2)); ?></div>
            </td>
        </tr>
        <tr>
            <td>
                <?php echo $form->labelEx($model, 'emailInvoiceFormat');
                echo $form->error($model, 'emailInvoiceFormat');?></td>
            <td>
                <div
                    class="row"><?php echo $form->radioButtonList($model, 'emailInvoiceFormat', Utility::getEmailInvoiceTypes(), array('size' => '40', 'separator' => ' ', 'labelOptions' => array('style' => 'display:inline')));?></div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <?php echo CHtml::submitButton('Update', array('id' => 'update'));?>
            </td>
        </tr>
        </tbody>
    </table>
    <?php
    $this->endWidget();
    ?>
</div>

