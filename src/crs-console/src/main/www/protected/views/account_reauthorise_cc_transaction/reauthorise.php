<!-- START protected/views/accounts_renewpay/topup.php -->
<?php
/**
 * View page for Accounts Renewal-Payments - Topup Deposit Account
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       AccountTopUpModel, Accounts_renewpayController::actionTopup(), CreditCardFormSegmentWidget
 */
$pg = 'Reauthorise';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
   <?php
   if ($model->message == 'TRANSACTION_OK') {
      echo '<h3>Transaction Successful</h3>';
   } else {
      if ($model->error != null) {
         echo '<h3>Transaction Failed</h3>';
         echo '<span class="error">' . $model->error . '</span>';
      }
      $submitButtonId='proceedSubmitButton';
      $form = $this->beginWidget('CActiveForm', array(
          'id' => 'ReauthorizePayModel',
          'enableAjaxValidation' => true,
		  'htmlOptions' => array('onsubmit' => "disableSubmit('$submitButtonId')"),
              ));
      $this->widget('application.widgets.credit_card_payment.CreditCardFormSegmentWidget', array(
          'form' => $form,
          'model' => $model,
      ));
      ?>
      <div class="row buttons"><?php echo CHtml::submitButton('Proccess', array('id' => $submitButtonId)); ?></div>
      <?php
      $this->endWidget();
   }
   ?>
</div>
<script>
    function disableSubmit(id) {
        document.getElementById(id).disabled=true;
        document.getElementById(id).value='Submitting, please wait...';
    }
</script>