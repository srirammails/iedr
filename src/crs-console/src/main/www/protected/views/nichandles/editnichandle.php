<!-- START protected/views/nichandles/editnichandle.php -->
<?php
/**
 * View page for Edit Nic-Handle
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Nichandle_Details, NichandlesController::actionEditnichandle(), ReturningController::outputReturningFormHtml()
 */

$pg = 'Edit NIC';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
    <?php
    $countryAjaxOpts = array('ajax' =>
    array(
        'type' => 'POST', //request type
        'url' => CController::createUrl('nichandles/dynamiccounties'), //url to call.
        'update' => '#Nichandle_Details_county', //selector to update
    ));
    if ($model->error != null)
        echo '<span class="error">' . WSAPIError::getErrorsNotEmpty($model->error) . '</span>';
    else {
        $this->outputReturningFormHtml($model, 'NicSearchModel_returningData_nic', 'Return to Form');
        if ($model->message == 'NIC_UPDATED') {
            echo '<span class="error" id="nic_updated_message">NicHandle saved successfully</span>';
            if (isset($_GET['from'])) {
                ?>
                <script>
                    var returnToDetails = document.getElementsByName('yt0');
                    returnToDetails[0].click();
                </script>
                <?php
            }
        } else {
            $form = $this->beginWidget('CActiveForm', array(
                'id' => 'Nichandle_Details',
                'enableAjaxValidation' => true,
                'clientOptions'=>array(
                    'validateOnSubmit'=>true,
                    'afterValidateAttribute' => 'js:function(form, attribute, data, hasError){
                            applyErrorForField(form, attribute, data, hasError);
                        }',
                ),
            ));
            $form->focus = array($model, 'name');
            foreach ($model->getReturningDataNames() as $v)
                echo $form->hiddenField($model, 'returningData[' . $v . ']');
            ?>
            <p class="note">Please fill in the form below.<font color="red"><br>Note:</font> Fields marked with an asterisk
                (*) must be completed.</p>
            <table class="formfields">
                <tr>
                    <td><?php echo $form->labelEx($model, 'nicHandleId'); echo $form->error($model, 'nicHandleId'); ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->textField($model, 'nicHandleId', array('disabled' => 'disabled', 'size' => '31'));         ?></div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'name');        echo $form->error($model, 'name');        ?></td>
                    <td>
                        <div class="row">
                            <?php
                            $nameOptions = array('size' => '31');
                            $selfModify = $model->nicHandleId == Yii::app()->user->name;
                            if (!$selfModify) {
                                $nameOptions['disabled'] = 'disabled';
                                echo $form->hiddenField($model, 'name');
                            };
                            echo $form->textField($model, 'name', $nameOptions);
                            ?>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'email');       echo $form->error($model, 'email');       ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->textField($model, 'email', array('size' => '31'));                   ?></div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'phones');      echo $form->error($model, 'phones');      ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->textArea($model, 'phones', array('rows' => 4, 'cols' => 45));          ?></div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'faxes');       echo $form->error($model, 'faxes');       ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->textArea($model, 'faxes', array('rows' => 4, 'cols' => 45));          ?></div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'companyName'); echo $form->error($model, 'companyName'); ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->textField($model, 'companyName', array('size' => '31'));                   ?></div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'address');     echo $form->error($model, 'address');     ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->textArea($model, 'address', array('rows' => 4, 'cols' => 45));          ?></div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'country');     echo $form->error($model, 'country');     ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->dropDownList($model, 'country', getCountryOptions(), $countryAjaxOpts); ?></div>
                    </td>
                </tr>
                <tr>
                    <td><?php echo $form->labelEx($model, 'county');      echo $form->error($model, 'county');      ?></td>
                    <td>
                        <div
                            class="row"><?php echo $form->dropDownList($model, 'county', getCountyOptions($model->country));     ?></div>
                    </td>
                </tr>
                <?php if ($model-> billCInd) { ?>
                    <tr>
                        <td><?php echo $form->labelEx($model, 'vatNo');       echo $form->error($model, 'vatNo');       ?></td>
                        <td>
                            <div
                                class="row"><?php echo $form->textField($model, 'vatNo', array('size' => '31', 'disabled' => 'disabled'));                    ?></div>
                        </td>
                    </tr>
                <?php } ?>
                <tr>
                    <td></td>
                    <td>
                        <div class="row buttons">
                            <?php echo CHtml::submitButton('Update'); ?> </div>
                    </td>
                </tr>
            </table>
            <?php
            $this->endWidget();
        }
    }
    ?>
    </tbody></table>
</div>

<script><?php include_once 'protected/widgets/js/common.js'; ?></script>

<!-- END protected/views/nichandles/editnichandle.php -->
