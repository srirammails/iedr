<!-- START protected/views/accounts_msd/confirm_action.php -->
<?php
/**
 * View page for Accounts MSD - Action Confirmation
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Accounts_msdController::showConfirmPage(), Accounts_msdController::actionConfirm(), MSDSelectionModel
 */

$pg = 'Confirm Action';
$this->pageTitle=Yii::app()->name . ' - '.$pg;

$cmdString = '';

$isVoluntary = false;
$isAutoRenew = false;

switch($model->command) {
   case 'autorenew':
		$cmdString = 'Set Autorenew'; 
      $isAutoRenew = true;
      break;
	case 'paydeposit':
		$cmdString = 'Pay From Deposit'; break;
	case 'payonline':
		$cmdString = 'Pay Online'; break;
	case 'voluntary':
		$cmdString = 'Enter to Voluntary NRP'; 
      $isVoluntary = true;
      break;
}

$duration = array();
$pricelistInfo = '';
$pricelist = get_reg_prices();

if(array_key_exists('REN', $pricelist)) {
   foreach ($pricelist['REN'] as $code => $prod) {
     $priceWithVat = round($prod->price + $prod->vatValue, 2, PHP_ROUND_HALF_EVEN);
     $duration[$code] = $prod->duration.' Year €'.$priceWithVat.($prod->vatValue > 0 ? ' Inc Vat.' : '');
     $pricelistInfo = $pricelistInfo.','.$prod->id.','.$prod->price.','.$prod->vatValue;
   }
} else {
   echo "<div class=\"flash-notice\">No vat rates defined for current user.</div>";
}

?>

<h2><?php printBreadcrumbTrail($pg); ?></h2>
<h4>Please Confirm you want to <b><i><?php echo $cmdString; ?></i></b> these Domains:</h4>
<?php
echo '<div class="form">';
$submitButtonId='proceedSubmitButton';
$form=$this->beginWidget('CActiveForm', array(
    'id'=>'ConfirmAction',
    'enableAjaxValidation'=>true,
	'htmlOptions' => array('onsubmit' => "disableSubmit('$submitButtonId')"),
    'clientOptions'=>array(
               'validateOnSubmit'=>true,
               'beforeValidate'=>"js:checkSelected",
               'afterValidateAttribute' => 'js:function(form, attribute, data, hasError){
                                applyErrorForField(form, attribute, data, hasError);
                            }',
    ),
    ));

   if(Utility::hasProperty($model,'domainlist')) {
      echo $form->hiddenField($model,'domainlist');
   }
   if(Utility::hasProperty($model,'command')) {
      echo $form->hiddenField($model,'command');
   }
   if(Utility::hasProperty($model,'returnurl')) {
      echo $form->hiddenField($model,'returnurl'); 
   }
   if(Utility::hasProperty($model,'needCreditCard')) {
      echo $form->hiddenField($model,'needCreditCard');
   }
   if(Utility::hasProperty($model,'renewalDateType')) {
      echo $form->hiddenField($model,'renewalDateType');
   }
   if(Utility::hasProperty($model,'period')) {
      echo $form->hiddenField($model,'period');
   }
   
	foreach(array('domainlist', 'command') as $f) {
		$e = $form->error($model, $f);
		if($e) 
         echo '<span class="error">'.$e.'</span><br>';
	}

   $this->widget('application.widgets.js.JavaScriptUtils', array('model' => $model));
   
	echo '<table class="formfields gridtable">';
	foreach($model->domainListToArray() as $key => $value) {
		$prefixDomain = DynamicDomainListModel::domainToVarPrefix($value);
		echo '<tr><td class="gridtablecell"><div class="row">';
		echo $form->error($model, $prefixDomain.'_confirmed');
		echo $form->checkBox($model, $prefixDomain.'_confirmed', array('checked'=>true,'onclick'=>'javascript:setup(this,"'.$value.'");'));
		echo '</div></td><td class="gridtablecell"><div class="row">';
		echo $form->labelEx($model, $prefixDomain.'_confirmed');
		echo '</div></td>';
      if($isAutoRenew) {
         echo '<td class="gridtablecell">'.$form->dropDownList($model, 'list_'.$value, array('Autorenew'=>'Autorenew','RenewOnce'=>'RenewOnce'),  array('onchange' => 'javascript:setup(this,"'.$value.'");') ).'</td></tr>';
      } else if(!$isVoluntary) {
         echo '<td class="gridtablecell">'.$form->dropDownList($model, 'list_'.$value, $duration, array('onchange' => 'javascript:setup(this,"'.$value.'");')).'</td></tr>';
      }
   }
	echo '</table>';
   
   
if(!$isVoluntary && !$isAutoRenew) {
    echo "<div>Transaction Summary:</div>";
    echo "<div id=\"productsCodesWithFees\" style=\"display: none\">$pricelistInfo</div><br>";
    echo "<table class=\"gridtable\">";
    echo "<tr>";
    echo "<td class=\"gridtablecell\"><strong>Domain</strong></td>";
    echo "<td class=\"gridtablecell\"><strong>Fee</strong></td>";
    echo "<td class=\"gridtablecell\"><strong>Vat</strong></td>";
    echo "<td class=\"gridtablecell\"><strong>Total</strong></td>";
    echo "</tr>";
    
    foreach($model->domainListToArray() as $key => $value) {
       echo "<tr>";
       echo "<td class=\"gridtablecell\"> <div id=\"domain\">$value</div> <div style=\"display:none\" id=\"period_$value\"></div> </td>";
       echo "<td class=\"gridtablecell\"> <div id=\"fee_$value\"></div></td>";
       echo "<td class=\"gridtablecell\"> <div id=\"vat_$value\"></div></td>";
       echo "<td class=\"gridtablecell\"> <div id=\"total_$value\"></div></td>";
       echo "</tr>";
    }
    
    echo "<tr>";
    echo "<td class=\"gridtablecell\"><strong>Totals</strong></td>";
    echo "<td class=\"gridtablecell\"> <div id=\"totalfee\">  </div></td>";
    echo "<td class=\"gridtablecell\"> <div id=\"totalvat\">  </div></td>";
    echo "<td class=\"gridtablecell\"> <div id=\"totaltotal\"> </div></td>";
    echo "</tr>";
    echo "</table>";
    
    if($model->needCreditCard == 1) {
		$this->widget('application.widgets.credit_card_payment.CreditCardFormSegmentWidget', array(
			'form'		=> $form,
			'model'		=> $model,
			));
	 }
}
   echo '</div>';
   echo '<div class="row buttons">'.CHtml::submitButton('Proceed', array('id' => $submitButtonId)).'</div><br>';
   $this->endWidget();
   echo AuthOnlyBaseController::returnToUrlButton($model->returnurl);
   
Yii::log('defaultPeriods = '.print_r($model->defaultPeriods,true));
?>
<script>
<?php
   if(!$isVoluntary && !$isAutoRenew) {
      foreach($model->domainListToArray() as $key => $value) {
         echo 'refreshSummary("'.$value.'");';
         echo 'setDefaults("'.$model->defaultPeriods.'"  , "'.$value.'");';
      }
   }
?>
</script>
