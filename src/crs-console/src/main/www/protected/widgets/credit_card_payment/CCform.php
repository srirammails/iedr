<!-- START protected/widgets/credit_card_payment/CCform.php -->
<?php
/**
 * defines html view rendering for CreditCardFormSegmentModel data as part of CreditCardFormSegmentWidget
 * 
 * Part of this for is rendered conditionally:
 * 
 * - if the model's scenario is 'topup', then a euros-amount field is rendered.
 * 
 * - if the model's scenario is NOT 'topup', then a euros-amount field is rendered hidden and set with a value of 1 euro (which the backend is expected to ignore).
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       CreditCardFormSegmentModel, CreditCardFormSegmentWidget
 */
?>

<script type="text/javascript">
   function setcardtype(type) {
      
      var models = new Array();
      models.push('Domains_Creation_Details');
      models.push('GridSelectionModel');
      models.push('AccountTopUpModel');
      models.push('ViewDomainModel');
      models.push('ViewDomainToTransferModel');
      models.push('CurrentNRPStatusesSelectionModel');
      models.push('DomainStatusNRPSelectionModel');
      models.push('CurrentRenewalsSelectionModel');
      models.push('DomainsTransferDetails');
      models.push('ReauthorizePayModel');
      models.push('ReauthorizeCCTransactionPayModel');
      
      var element = null;
      for(var i = 0;i < models.length; i++) {
         element = document.getElementById(models[i] + '_cardtype');
         if(element != null) {
            switch(type) {
               case 'MC':
                  element.value = 'MC';
                  break;
               case 'VISA':
                  element.value = 'VISA';
                  break;
            }
         }
      }
      
   }
</script>

<p class="note">Please fill in the form below. <font color="red"><br>Note:</font> Fields marked with an asterisk (*) must be completed.</p>
<table class="formfields">
	<tr>
		<td><div class="row"><?php echo $this->form->labelEx($this->model,'cardholder'); ?></div>
			<div class="row"><?php echo $this->form->error($this->model,'cardholder'); ?></div></td>
		<td colspan="3"><div class="row"><?php echo $this->form->textField($this->model,'cardholder', array('size' => '20')); ?></div></td>
    </tr>
	<tr>
		<td><div class="row"><?php echo $this->form->labelEx($this->model,'creditcardno'); ?></div>
			<div class="row"><?php echo $this->form->error($this->model,'creditcardno'); ?></div></td>
		<td colspan="3">
			<div class="row"><?php echo $this->form->textField($this->model,'creditcardno',array('maxlength'=>19,'size'=>20, 'autocomplete'=>'off')); ?></div>		
		</td>
    </tr>
   
   <tr>
		<td><div class="row"><?php echo $this->form->labelEx($this->model,'cardtype'); ?></div>
			<div class="row"><?php echo $this->form->error($this->model,'cardtype'); ?></div></td>
		<td colspan="3">
			<img src="images/master.gif" width="54" height="36" onclick="setcardtype('MC')" style="cursor:pointer">
			<img src="images/nvisa.gif" width="54" height="36" onclick="setcardtype('VISA')" style="cursor:pointer">
			<img src="images/nvisadeb.gif" width="54" height="36" onclick="setcardtype('VISA')" style="cursor:pointer">
			<br/>
			<div class="row"><?php echo $this->form->textField($this->model,'cardtype',array('maxlength'=>20,'size'=>20,'readonly'=>true)); ?></div>
		</td>
   </tr>

	<tr>
		<td><div class="row"><?php echo $this->form->labelEx($this->model,'exp_date'); ?></div>
			<div class="row"><?php
            echo $this->form->error($this->model,'exp_month');
            echo $this->form->error($this->model,'exp_year');
            ?></div></td>
		<td colspan="3"><div class="row"><?php
			echo $this->form->textField($this->model,'exp_month',array('maxlength'=>2,'size'=>2));
			echo ' / 20';
			echo $this->form->textField($this->model,'exp_year',array('maxlength'=>2,'size'=>2));
		?></div></td>
    </tr>

<?php
if(!(stristr($this->model->getScenario(),'topup')===false))
	{
?>
	<tr>
		<td><div class="row"><?php echo $this->form->labelEx($this->model,'euros_amount'); ?></div>
			<div class="row"><?php echo $this->form->error($this->model,'euros_amount'); ?></div></td>
		<td colspan="3"><div class="row"><?php echo $this->form->textField($this->model,'euros_amount', array('size' => '20')); ?></div></td>
    </tr>
<?php
	}
else
	{
	$this->model->euros_amount=1;
	echo $this->form->hiddenField($this->model,'euros_amount');
	}
?>
	<tr>
		<td><div class="row"><?php echo $this->form->labelEx($this->model,'cvn'); ?></div>
			<div class="row"><?php echo $this->form->error($this->model,'cvn'); ?></div></td>
		<td colspan="3"><div class="row"><?php echo $this->form->textField($this->model,'cvn',array('maxlength'=>3,'size'=>3, 'autocomplete'=>'off')); ?></div></td>
    </tr>
</table>
<!-- END protected/widgets/credit_card_payment/CCform.php -->
