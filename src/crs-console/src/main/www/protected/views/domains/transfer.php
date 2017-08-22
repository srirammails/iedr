<?php
/**
 * View page for accounts operation confirmation
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       GridController, AccountsGridController, GridSelectionModel
 */

$pg = 'Confirm Action';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;

$cmdString = '';

// renewal prices
$registration_option_list = array();
$registration_option_string = '';

?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<h4>Please confirm you would like to <b><i><?php echo $cmdString; ?></i></b> for these domain(s):</h4>

<?php

Yii::log('TRANSFER MODEL = '.print_r($model,true));

$form = $this->beginWidget('CActiveForm',
         array('id' => 'ViewDomainToTransferModel', 
               'enableAjaxValidation' => true,
              'clientOptions'=>array(
              'validateOnSubmit'=>true,
               'beforeValidate'=>"js:checkCharity"
               ),)
        );

if(count($model->errors)) {
   echo "$model->errors <br>";
}

echo "<div id='domain'><h4>Domain name: $model->domain_name</h4> </div><br>";

echo "<table class='formfields gridtable'>";
echo "<tr> <td class='gridtablecell'></td> <td class='gridtablecell'><strong>NIC Handle</strong></td> <td class='gridtablecell'><strong>Name</strong></td> <td class='gridtablecell'><strong>Email</strong></td> <td class='gridtablecell'><strong>Company</strong></td> <td class='gridtablecell'><strong>Country</strong></td> </tr>";

echo "<tr>";
   echo "<td class='gridtablecell'>Tech</td>";
   echo "<td class='gridtablecell'>".$model['domain_techContacts_nicHandle']."</td>";
   echo "<td class='gridtablecell'>".$model['domain_techContacts_name']."</td>";
   echo "<td class='gridtablecell'>".$model['domain_techContacts_email']."</td>";
   echo "<td class='gridtablecell'>".$model['domain_techContacts_companyName']."</td>";
   echo "<td class='gridtablecell'>".$model['domain_techContacts_country']."</td>";
echo "</tr>";

for($i=0;$i<$model['domain_adminContacts'];$i++) 
{
   echo "<tr>";
   echo "<td class='gridtablecell'>Admin".$i."</td>";
   echo "<td class='gridtablecell'> ".$model["domain_adminContacts_".$i."_nicHandle"]."</td>";
   echo "<td class='gridtablecell'>".$model["domain_adminContacts_".$i."_name"]."</td>";
   echo "<td class='gridtablecell'>".$model["domain_adminContacts_".$i."_email"]."</td>";
   echo "<td class='gridtablecell'>".$model["domain_adminContacts_".$i."_companyName"]."</td>";
   echo "<td class='gridtablecell'>".$model["domain_adminContacts_".$i."_country"]."</td>";
   echo "</tr>";
}

echo "<tr>";
   echo "<td class='gridtablecell'>Biling</td>";
   echo "<td class='gridtablecell'>".$model->domain_billingContacts_nicHandle."</td>";
   echo "<td class='gridtablecell'>".$model->domain_techContacts_name."</td>";
   echo "<td class='gridtablecell'>".$model->domain_techContacts_email."</td>";
   echo "<td class='gridtablecell'>".$model->domain_techContacts_companyName."</td>";
   echo "<td class='gridtablecell'>".$model->domain_billingContacts_country."</td>";
echo "</tr>";
echo "</table>";

$periodTypes = array('Y','M');

$registration_option_list = array();
$registration_option_string = '';
$regarr = get_reg_prices();

if(array_key_exists('REN', $regarr)) {
   foreach ($regarr['REN'] as $code => $prod) {
     $priceWithVat = round($prod->price + $prod->vatValue, 2, PHP_ROUND_HALF_EVEN);
     $registration_option_list[$code] = $prod->duration.' Year €'.$priceWithVat.($prod->vatValue > 0 ? ' Inc Vat.' : '');
     $registration_option_string = $registration_option_string.','.$prod->id.','.$prod->price.','.$prod->vatValue;
   }
} else {
   echo "<div class=\"flash-notice\">No vat rates defined for current user.</div>";
}


echo 'Period : '.$form->dropDownList($model, 'list', $registration_option_list, array('onchange' => 'javascript:setupHiddenPeriod(this);','value'=>1));
echo  "<div>Transaction Summary:</div>
       
<div id=\"productsCodesWithFees\" style=\"display: none\">$registration_option_string</div><br>

  <table class=\"gridtable\">
  <tr>
      <td class=\"gridtablecell\">Fee: </td>
      <td class=\"gridtablecell\">
        <div id=\"feeVal\"></div>
      </td>
  </tr>
  <tr>
      <td class=\"gridtablecell\">Vat: </td>
      <td class=\"gridtablecell\">
        <div id=\"vatVal\"></div>
      </td>
  </tr>
  <tr>
      <td class=\"gridtablecell\">Total: </td>
      <td class=\"gridtablecell\">
        <div id=\"totalVal\"></div>
      </td>
  </tr>
  </table>";

       
       
       echo $form->hiddenField($model,'period',array('value'=>1));
       echo $form->hiddenField($model,'periodType',array('value'=>'Y'));
       echo $form->hiddenField($model,'paymentType');
       echo $form->hiddenField($model,'domain_name');
       echo $form->hiddenField($model,'domain_remark');
       
       echo $form->hiddenField($model,'domain_nameservers_0_ipAddress');
       echo $form->hiddenField($model,'domain_nameservers_0_name');
       echo $form->hiddenField($model,'domain_nameservers_1_ipAddress');
       echo $form->hiddenField($model,'domain_nameservers_1_name');
       echo $form->hiddenField($model,'domain_nameservers_2_ipAddress');
       echo $form->hiddenField($model,'domain_nameservers_2_name');
       echo $form->hiddenField($model,'domain_nameservers_3_ipAddress');
       echo $form->hiddenField($model,'domain_nameservers_3_name');
       
       echo $form->hiddenField($model,'domain_adminContacts_0_nicHandle');
       echo $form->hiddenField($model,'domain_adminContacts_0_name');
       echo $form->hiddenField($model,'domain_adminContacts_0_email');
       echo $form->hiddenField($model,'domain_adminContacts_0_companyName');
       echo $form->hiddenField($model,'domain_adminContacts_0_country');
       
       echo $form->hiddenField($model,'domain_adminContacts_1_nicHandle');
       echo $form->hiddenField($model,'domain_adminContacts_1_name');
       echo $form->hiddenField($model,'domain_adminContacts_1_email');
       echo $form->hiddenField($model,'domain_adminContacts_1_companyName');
       echo $form->hiddenField($model,'domain_adminContacts_1_country');
       
       echo $form->hiddenField($model,'domain_techContacts_nicHandle');
       echo $form->hiddenField($model,'domain_techContacts_name');
       echo $form->hiddenField($model,'domain_techContacts_email');
       echo $form->hiddenField($model,'domain_techContacts_companyName');
       echo $form->hiddenField($model,'domain_techContacts_country');
       
       echo $form->hiddenField($model,'domain_billingContacts_nicHandle');
       echo $form->hiddenField($model,'domain_billingContacts_name');
       echo $form->hiddenField($model,'domain_billingContacts_email');
       echo $form->hiddenField($model,'domain_billingContacts_companyName');
       echo $form->hiddenField($model,'domain_billingContacts_country');
       
       echo $form->hiddenField($model,'charitycode');
       
       echo $form->hiddenField($model,'domain_holder');
       echo $form->hiddenField($model,'domain_holderCategory');
       echo $form->hiddenField($model,'domain_holderClass');
?>
<table>
<tr>
                <td colspan="3">
                    <div class="row"><?php                
                        echo $form->radioButtonList($model,'paymentType', Utility::getPaymentTypes(), array('onchange' => 'javascript:paymentTypeChanged(this)'));
                    ?></div>
                </td>
            </tr>
            <tr id="charityRow" style="display:<?php if($model->paymentType == 'CH') echo ' '; else echo 'none';  ?> ">
                <td><?php
                    echo $form->labelEx($model,'charitycode'); echo $form->error($model,'charitycode');
                ?></td>
                <td colspan="3">
                    <div class="row"><?php echo $form->textField($model,'charitycode'); ?></div>
                </td>
            </tr>
            <tr id="ccRow" style="display:<?php if($model->paymentType == 'CC') echo ' '; else echo 'none'; ?> ">
                <td>
                    <label>Credit Card</label>
                </td>
                <td colspan="3">
                    <?php
                        $this->widget('application.widgets.credit_card_payment.CreditCardFormSegmentWidget', array(
                            'form'		=> $form,
                            'model'		=> $model,
                        ));
                    ?>
                </td>
            </tr>
</table>
<?php

echo '<div class="row buttons">' . CHtml::submitButton('Proceed') . '</div><br>';
$this->endWidget();
echo AuthOnlyBaseController::returnToUrlButton("/index.php?r=domains/domainstransferall");
?>


<script>
   
    function checkCharity() {
       var period = document.getElementById('ViewDomainToTransferModel_list');
       if(period != null) {
         var index = period.selectedIndex;
         period.value = index + 1;
         return true;
       } else {
         return false;
       }
    }
      
    function paymentTypeChanged() {
        var pay = document.getElementById('ViewDomainToTransferModel_paymentType');
        var checkedRadioButtonValue = $("input[@type=radio]:checked").val();
        var charityElement = document.getElementById("charityRow");
        var ccElement = document.getElementById("ccRow");
        switch (checkedRadioButtonValue) {
            case 'CC' :
                pay.value = 'CC';
                charityElement.style.display = 'none';
                ccElement.style.display = '';
            break;
            case 'CH' :
                pay.value = 'CH';
                charityElement.style.display = '';
                ccElement.style.display = 'none';
            break;
            case 'ADP':
                pay.value = 'ADP';
                charityElement.style.display = 'none';
                ccElement.style.display = 'none';
            break;
        }
    }

   function setupHiddenPeriodTypes(obj) {
     var periodType = document.getElementById('ViewDomainToTransferModel_periodType');
     if(periodType != null) {
        var index = obj.selectedIndex;
        periodType.value = obj.options[index].innerHTML;
     }
   }
   
   function setupHiddenPeriod(obj) {
        refreshSummary();
   }
   
   function SummaryClass(obj) {
        this.tFee = 0;
        this.tVat = 0;
        this.tTotal = 0;
        this.add = function(fee, vat) {
            this.tFee += parseFloat(fee) ;
            this.tVat += parseFloat(vat) ;
            this.tTotal = (this.tFee + this.tVat) ;
        }
        this.sub = function(fee, vat) {
            this.tFee -= parseFloat(fee);
            this.tVat -= parseFloat(vat);
            this.tTotal = this.tFee + this.tVat;
        }
    }

    function currencyFormatter(num) {
        /* render a number in html with currency symbol, 2 decimal places, thousands separators, and half-even rounding (http://www.diycalculator.com/sp-round.shtml#A5) */
        csym = "&euro;";
        if (num == null) return csym + ' 0.00';
        num = num.toString().replace(/\$|\,/g, '');
        if (isNaN(num))
            num = "0";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num * 100 + 0.50000000001);
        cents = num % 100;
        num = Math.floor(num / 100).toString();
        if (cents < 10)
            cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
            num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
        return (((sign) ? '' : '-') + csym + ' ' + num + '.' + cents);
    }

    function refreshSummary(obj) {
        var select  = document.getElementById('ViewDomainToTransferModel_list');
        var index = select.selectedIndex;
        var period = 0;
        if(index != -1)
           period = select.options[index].value;

        var summary = new SummaryClass();
        // handle xfers, renewals and new registrations
        if (document.getElementById('domainsWithFees') != null && document.getElementById('domainsWithFees').innerHTML.trim().length > 0) {
            var domainsArray = document.getElementById('domainsWithFees').innerHTML.split(",");
            var allCheckedCheckboxes = $("input[@type=checkbox]:checked");
            for (var i = 0; i < allCheckedCheckboxes.length; i++) {
                var labelId = allCheckedCheckboxes[i].id;
                var label = $("label[for=" + labelId + "]").text();
                if (label) {
                    var n = domainsArray.indexOf(label);
                    summary.add(domainsArray[n + 1], domainsArray[n + 2])
                }
            }
        // handle future renewals
        } else if (document.getElementById('productsCodesWithFees') != null) {
            var productsWithFeesArray = document.getElementById('productsCodesWithFees').innerHTML.split(",");
            if (period) {
                  var n = productsWithFeesArray.indexOf(period);
                  summary.add(productsWithFeesArray[n + 1], productsWithFeesArray[n + 2]);
            }
        }
        
        
        var o = document.getElementById('ViewDomainToTransferModel_list');
        if(o != null) {
           var index = o.selectedIndex;
           document.getElementById('ViewDomainToTransferModel_period').value = index + 1;
        }
        
        document.getElementById('feeVal').innerHTML = currencyFormatter(summary.tFee);
        document.getElementById('vatVal').innerHTML = currencyFormatter(summary.tVat);
        document.getElementById('totalVal').innerHTML = currencyFormatter(summary.tTotal);
    }
    refreshSummary();
   
</script>




