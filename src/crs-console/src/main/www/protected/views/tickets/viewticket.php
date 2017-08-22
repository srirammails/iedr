<!-- START protected/views/tickets/viewticket.php -->
<?php
/**
 * View page for Ticket View
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       ViewTicketModel, TicketsController::actionViewticket()
 */

$pg = 'View Ticket';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php

$allow_for_wrapping = 6;
$min_rows = 2;
global $textarea_cols;
$textarea_cols = 60;
$textarea_rows_hmr = $min_rows + math_div(strlen($model->hostmastersRemark), $textarea_cols - $allow_for_wrapping);
$textarea_rows_rqr = $min_rows + math_div(strlen($model->requestersRemark), $textarea_cols - $allow_for_wrapping);
$tagOpts = array('size' => $textarea_cols, 'maxlen' => $textarea_cols, 'disabled' => 'disabled');

/**
 * outputs a field's label and value in html with html-table-column separators
 *
 * @param  object  $m     model
 * @param  array   $o     options array for CForm html-field-function
 * @param  object  $f     form object
 * @param  string  $n     model attribute name
 * @param  string  $t     field type (used as function-name for form object)
 * @return void
 */
function displayField($m, $o, $f, $n, $t = 'textField')
{
    echo $f->label($m, $n) . /*$f->error($m,$n).*/
         '</td><td><div class="row">' . $f->$t($m, $n, $o) . '</div>';
}

/**
 * outputs a field's label and value and any associated error in html with html-table-column separators
 *
 * @param  object  $model     model
 * @param  object  $form     form instance
 * @param  string  $o     operation name
 * @return void
 */
function displayOperationField($model, $form, $o, $size=60)
{
    global $textarea_cols;
    $key_err = $o . '_failureReason';

    $lbl = $form->label($model, $o);
    $dat = $model->$o;
    $err = $model->$key_err;
    echo $lbl . '<span class="errorMessage">' . $err . '</span></td><td><div class="row"><input disabled="disabled" size="' . $size . '" type="text" value="' . encode($dat) . '"></div>';
}

?>
<div class="form">
<?php
if ($model->errors != null)
    echo '<span class="error">' . print_r($model->errors, true) . '</span>';
else
{
    if (isset(Yii::app()->session['returnAction'])) {
        $form = $this->beginWidget('CActiveForm',array('action'=> $this->createUrl(Yii::app()->session['returnAction'])));
        echo CHtml::submitButton('Back to results');
        $this->endWidget();
    }
    echo "<br>";
    if (Yii::app()->user->hasFlash('state') && (Yii::app()->user->getFlash('state') == "modified")) {
        $errorMessage = '';
        $successMessage = '';
        if (Yii::app()->user->hasFlash('ticketUpdate')) {
            $ticketUpdateFlash = Yii::app()->user->getFlash('ticketUpdate');
            if ($ticketUpdateFlash == "done") {
                $successMessage = 'Ticket modified successfully.';
            } else if ($ticketUpdateFlash == "error") {
                $errorMessage = 'Ticket is not editable because it\'s being processed by IEDR.';
            }
        }
        if (!$uploadResult->isEmpty()) {
            if (!$uploadResult->hasErrors()) {
                $joiner = empty($successMessage) ? '' : '</br>';
                $successMessage = $successMessage . $joiner . 'Documents uploaded successfully';
            } else {
                $joiner = empty($errorMessage) ? '' : '</br>';
                $errorMessage = $errorMessage . $joiner . 'There were errors uploading some documents:<ul>';
                if (isset($uploadResult->model['error'])) {
                    $errorMessage = $errorMessage . '<li>' . WSAPIError::getErrorsNotEmpty($uploadResult->model['error']) . '</li>';
                } else {
                    $errorMessage = $errorMessage . implode(array_map(function($e) {
                            return "<li>" . $e['documentName']. ": " . $e['status'] . "</li>"; },
                            $uploadResult->getHumanReadableResults()));
                }
                $errorMessage = $errorMessage . '</ul>';
            }
        }
        if (!empty($successMessage)) {
            echo '<div class="flash-success">'.$successMessage.'</div>';
        }
        if (!empty($errorMessage)) {
            echo '<div class="flash-error">'.$errorMessage.'</div>';
        }
    }

    $url_view_domain = $this->createUrl('domains/viewdomain', array('id' => $model->domainName));
    $url_edit_ticket = $this->createUrl('tickets/editticket', array('id' => $model->id));
    $url_delete_ticket = $this->createUrl('tickets/deleteticket', array('id' => $model->id, 'domainName' => $model->domainName));

    $form = $this->beginWidget('CActiveForm', array('id' => 'ViewTicketModel', 'enableAjaxValidation' => false,));
    ?>
    <table border="1" class="formfields">
        <tbody>
        <tr>
            <td>
                <?php
                    if ($model->type != 'Registration' && $model->type != 'Transfer')
                        echo 'View <a href="' . $url_view_domain . '"/>' . $model->domainName . '</a><br>';
                    $editable = empty($model->checkedOutTo);
                    $canUploadDocuments = Utility::isLoggedInAs($model->billingContact) && ($model->type == 'Registration' || $model->type == 'Modification');
                    if ($editable || $canUploadDocuments) {
                        if (!Utility::isAdminOrTech() || Utility::isLoggedInAs($model->creator) || Utility::isDirect() || Utility::isRegistrar() || Utility::isSuperRegistrar()) {
                            echo '<a href="' . $url_edit_ticket . '"/>Edit Ticket</a><br>';
                            if ($editable) {
                                echo '<a href="' . "$url_delete_ticket" . '" onclick="javascript:return confirm(\'Are you sure you want to cancel the ticket for ' . $model->domainName . '?\');"/>Cancel Ticket</a><br>';
                            }
                        }
                    }
                ?>
            </td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'domainName'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'id'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'creationDate'); ?></td>
        </tr>

        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'period'); ?></td>
        </tr>
        
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'paymentType'); ?></td>
        </tr>
        
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'charityCode'); ?></td>
        </tr>
        
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'type'); ?></td>
        </tr>
        
        <tr>
            <td><?php displayOperationField($model, $form, 'domainHolder'); ?></td>
        </tr>
        <tr>
            <td><?php displayOperationField($model, $form, 'domainHolderClass'); ?></td>
        </tr>
        <tr>
            <td><?php displayOperationField($model, $form, 'domainHolderCategory'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, array('rows' => $textarea_rows_rqr, 'cols' => $textarea_cols, 'disabled' => 'disabled'), $form, 'requestersRemark', 'textArea'); ?></td>
        </tr>
        <tr>
            <td colspan="2">
                <table>
                    <tr>
                        <td><?php displayOperationField($model, $form, 'adminContact_0', 30); ?></td>
                        <td><?php displayOperationField($model, $form, 'adminContact_1', 30); ?></td>
                    </tr>
                    <tr>
                        <td><?php displayOperationField($model, $form, 'techContact', 30); ?></td>
                        <td><?php displayOperationField($model, $form, 'billingContact', 30); ?></td>
                    </tr>
                </table>
            </td>
        </tr>
        <?php 
        $w = $this->widget('application.widgets.nameservers.nameserversWidget', array(
            'form' => $form,
            'model' => $model,
            'disabled' => true,
            'size' => 30,
            'domainId' => 'EditTicketModel_domainName',
        ));
        ?>
        <tr>
            <td><?php displayField($model, array('rows' => $textarea_rows_hmr, 'cols' => $textarea_cols, 'disabled' => 'disabled'), $form, 'hostmastersRemark', 'textArea'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'creator'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'adminStatus'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'adminStatusChangeDate'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'techStatus'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'techStatusChangeDate'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'financialStatus'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'financialChangeDate'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'changeDate'); ?></td>
        </tr>
        <tr>
            <td><?php displayField($model, $tagOpts, $form, 'expiryDate'); ?></td>
        </tr>
        </tbody>
    </table>
                <?php
                    $this->endWidget();
}
    ?>
</div>

<!-- END protected/views/tickets/viewticket.php -->
