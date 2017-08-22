<!-- START protected/views/accounts/menu.php -->
<?php
$pg = 'Top up my deposit account';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form">
   <?php
   if ($model->message == 'TRANSACTION_OK') {   	   	 
      echo '<H2>Transaction Successful</H2>';
      
      echo "<table class='gridtable'>";
      echo "<tr><td class='gridtablecell'><strong>Transaction date</strong></td><td class='gridtablecell'>".parseXmlDate($model->result->transactionDate)."</td></tr>";
      echo "<tr><td class='gridtablecell'><strong>Transaction type</strong></td><td class='gridtablecell'>".$model->result->transactionType."</td></tr>";
      echo "<tr><td class='gridtablecell'><strong>OrderId</strong></td><td class='gridtablecell'>".$model->result->orderId."</td></tr>";
      echo "<tr><td class='gridtablecell'><strong>Amount</strong></td><td class='gridtablecell'>€ ".number_format($model->result->transactionAmount, 2)."</td></tr>";
	  echo "</table>";      
   } else {
      if ($model->error != null) {
         echo '<H2 style="color: red;">Transaction Failed</H2>';
         echo '<div class="error">' . WSAPIError::getErrorsNotEmpty($model->error) . '</div><br>';
      }
      $submitButtonId = 'topupsubmit';
      $form = $this->beginWidget('CActiveForm', array(
          'id' => 'AccountTopUpForm',
          'enableAjaxValidation' => true,
          'htmlOptions' => array('onsubmit' => "disableSubmit('$submitButtonId')"),
              ));
      $this->widget('application.widgets.credit_card_payment.CreditCardFormSegmentWidget', array(
          'form' => $form,
          'model' => $model,
      ));
      ?>
      <div class="row buttons"><?php echo CHtml::submitButton('Top Up', array('id' => $submitButtonId)); ?></div>
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
