<!-- START protected/views/domains/domaindetails.php -->
<?php
/**
 * View page for Domain Details
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Domains_Creation_Details, DomainsController::actionDomainDetails()
 */

$pg = 'Domain Details';
$this->pageTitle=Yii::app()->name . ' - '.$pg;

/**
 * nicHandleWidget Function: Outputs three hyperlinks which go to (and return from) pages for creating, finding and editing Nic-Handles
 * 
 * On some forms with Nic-Handle fields, it is desirable to be able to, at that point in the session,
 * divert to a second form to create, find, or update Nic_Handle details.
 * 
 * This Fn assits in this in the following way:
 * 
 * - each nic-handle field in an originating form, to be returned to, has beside it the three
 *    html links output by this function
 * 
 * - Each of these links has a javascript click-event handler which calls javascript function 'getDataAndPostForm'
 * 
 * - getDataAndPostForm() then reads the nic-handle from the adjacent nic-handle field, along with other
 *   info including the URL to return to (i.e., the controller action rendering this view page), puts the data
 *   into a hidden form, and posts the form to the appropriate URL.
 * 
 * @param  Controller $controller    controller handling the current request
 * @param  string $nic_type      which nic-role (for updating the correct field, on return to this page)
 * @param  string $inpIdNic2edit The html id attribute of the input tag of the corresponding Nic-handle form field
 * @return void  
 */
function nicHandleWidget($controller,$nic_type,$inpIdNic2edit='LeftBlank') {
	$urlDDS = $controller->createUrl('domains/domaindetailsserialized');
	$form	= 'DomainDetailsForm';
	$params = "'$nic_type','$form','$urlDDS','$inpIdNic2edit'";
	echo '<a href="#" id="nicWidgetNew_'.$nic_type.'" onclick="return getDataAndPostForm(\'new\','.$params.');">New</a> / ';
	echo '<a href="#" id="nicWidgetFnd_'.$nic_type.'" onclick="return getDataAndPostForm(\'find\','.$params.');">Find</a> / ';
	echo '<a href="#" id="nicWidgetEdt_'.$nic_type.'" onclick="return getDataAndPostForm(\'edit\','.$params.');">Edit</a>';
}

$registration_option_list = array();
$registration_option_string = '';
$regarr = get_reg_prices();

if(array_key_exists('REG', $regarr)) {
   foreach ($regarr['REG'] as $code => $prod) {
     $priceWithVat = round($prod->price + $prod->vatValue, 2, PHP_ROUND_HALF_EVEN);
     $registration_option_list[$code] = $prod->duration.' Year €'.$priceWithVat.($prod->vatValue > 0 ? ' Inc Vat.' : '');
     $registration_option_string = $registration_option_string.','.$prod->id.','.$prod->price.','.$prod->vatValue;
   }
} else {
  echo "<div class=\"flash-notice\">No vat rates defined for current user.</div>";
}

?>
<form method="post" id="hid">
	<input type="hidden" id="tok"		name="YII_CSRF_TOKEN"	value="unset">
	<input type="hidden" id="data"		name="formdata"		value="unset">
	<input type="hidden" id="mode"		name="mode"		value="unset">
	<input type="hidden" id="nictype"	name="nictype"		value="unset">
	<input type="hidden" id="nic"		name="nic"		value="unset">
	<input type="hidden" id="returnurl"	name="returnurl"	value="index.php?r=domains/domaindetails">
</form>

   <script><?php include_once 'protected/widgets/js/common.js'; ?></script>
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

function getDataAndPostForm(mode,nictype,srcf,serializeUrl,editNIC) {
	// this fn called on click (link or button) does:
	// (1) send form to server, gets serialized model back
	// (2) sets up hidden form in this page
	// (3) submits the form, navigating away to the next page
    if (uploaderDocumentsPresentWarning !== null && !confirm("Leaving this page will cause resetting some values:\n" + uploaderDocumentsPresentWarning)) {
        return false;
    }
	var dispatchUrl
	switch(mode) {
		case "new":  dispatchUrl = 'index.php?r=nichandles/createnichandle&from=1'; break;
		case "find": dispatchUrl = 'index.php?r=nicsearch/namesearch&from=1'; break;
		case "edit":
			var theNic = $('#'+editNIC).val();
         if($('#'+editNIC).val().length == 0) {
           return 0;
         }
			dispatchUrl = 'index.php?r=nichandles/editnichandle&id='+theNic+'&from=1';
			$("#hid #nic").val(theNic);
			break;
		}
	// (1) post form ('srcf') to server, retrieve serialized model via ajax
	$.post(serializeUrl, $("#"+srcf).serialize(), function(ajaxdata) {
		// (2) set up form
		$("#hid").attr("action",dispatchUrl);
		$("#hid #data").val(ajaxdata); // the serialised form data
		$("#hid #mode").val(mode); // new/find/edit
		$("#hid #nictype").val(nictype); // which nic role for the mode (for updating the correct field, on return to this page)
		// Yii doesn't set an ID for the token hidden-input, so find by name
		var itok = $('#'+srcf+' input[name="YII_CSRF_TOKEN"]')[0];
		// set hidform token value
		var hitok = $("#hid #tok")[0];
		$(hitok).val($(itok).val());
		// (3) post form with serialized model
		$("#hid").submit();
		});
	}
</script>

<script>

   $(document).ready(function() {
       $(window).keydown(function(event){
           if(event.keyCode == 13) {
               event.preventDefault();
               return false;
           }
       });
   });
</script>

<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<div class="form" >
<?php
$form = $this->beginWidget('CActiveForm', array(
    'id' => 'DomainDetailsForm',
    'enableAjaxValidation' => true,
    'clientOptions' => array(
        'validateOnSubmit' => true,
        'beforeValidate' => "js:checkCharity",
        'afterValidateAttribute' => 'js:function(form, attribute, data, hasError){
                            applyErrorForField(form, attribute, data, hasError);
                        }',
    ),
    'htmlOptions' => array('enctype' => 'multipart/form-data'),
));

//echo  "<div>Transaction Summary:</div><div id=\"domainsWithFees\" style=\"display: none\">$model->domainlistWithSummary</div><div id=\"productsCodesWithFees\" style=\"display: none\">$registration_option_string</div><br>";

$form->focus = array($model,'holder');
?>
	<p class="note">Please fill in the form below. </br><font color="red">Note:</font> Fields marked with an asterisk (*) must be completed.</p>
	<table border="1" class="formfields">
		<tbody>
			<tr>
				<td><?php // (available/valid) domains to be registered
					echo $form->labelEx($model,'domainlist'); echo $form->error($model,'domainlist');
				?></td>
				<td colspan="3">
					<div class="row"><?php
							$domainlist_selections = array();
							if($model->domainlist)
								foreach($model->domainlist as $k => $v)
									$domainlist_selections[$v] = array('selected'=>true);
							else
								$model->domainlist = array();
							$selOptions = array();
							foreach($model->domainlist as $k => $v)
								$selOptions[$v] = $v;
							echo $form->listBox($model,'domainlist',$selOptions,array('id' => 'domainname','multiple'=>'multiple','options' => $domainlist_selections));
					?></div>
				</td>
			</tr>
			<tr>
				<td><?php
					echo $form->labelEx($model,'holder'); echo $form->error($model,'holder');
				?></td>
				<td colspan="3">
					<div class="row"><?php echo $form->textField($model,'holder'); ?></div>
				</td>
			</tr>
			<tr>
				<td><?php // applicant class
					echo $form->labelEx($model,'applicant'); echo $form->error($model,'applicant');
					?><ul>
						<li><a href="https://www.iedr.ie/quick-guide-to-registrations-policy/" target="_blank">Class/Category guidelines</a><br>
					</ul>
				</td>
				<td colspan="3">
					<div class="row"><?php echo $form->dropDownList($model,'applicant',get_class_category()); ?></div>
				</td>
			</tr>
			<tr>
				<td><?php // registration period [] =1-year
					echo $form->labelEx($model,'registration_period'); echo $form->error($model,'registration_period');
				?></td>
				<td colspan="3">
					<div class="row"><?php
						echo $form->dropDownList($model,'registration_period',$registration_option_list, array('onchange' => 'javascript:setupHidden(this);'));
					?></div>
				</td>
			</tr>
			<tr>

            <tr>
               <td><div font-size="0.7em"><strong>Transaction Summary:</strong></div></td>
               <td colspan="3">
                     <div id="domainsWithFees" style="display:none"><?php echo $model->domainlistWithSummary; ?></div>
                     <div id="productsCodesWithFees" style="display: none"><?php echo $registration_option_string; ?></div>
                       <table class="gridtable" style="display:hidden" >
                       <tr>
                           <td class="gridtablecell">Fee: </td>
                           <td class="gridtablecell">
                             <div id="feeVal"></div>
                           </td>
                       </tr>
                       <tr>
                           <td class="gridtablecell">Vat: </td>
                           <td class="gridtablecell">
                             <div id="vatVal"></div>
                           </td>
                       </tr>
                       <tr>
                           <td class="gridtablecell">Total: </td>
                           <td class="gridtablecell">
                             <div id="totalVal"></div>
                           </td>
                       </tr>
                       </table>
                 </td>
               </tr>
            <tr>
				<td><?php // remarks (textarea) / w/chars-left
				echo $form->labelEx($model,'remarks'); echo $form->error($model,'remarks');
				?><br>eg. RBN/CRO No., CTM Number, links to supporting information etc
				</td>
				<td colspan="3">
					<div class="row"><?php
					echo $form->textArea($model,'remarks',array('rows'=>3, 'cols'=>50));
					?></div>
				</td>
			</tr>
			<tr>
				<td><?php // admin contact 1 (nic) {must be from domain-holder organisation}
				echo $form->labelEx($model,'admin_contact_nic_1'); echo $form->error($model,'admin_contact_nic_1');
				?></td>
				<td colspan="3">
					<div class="row"><?php //
					echo $form->textField($model,'admin_contact_nic_1', array('class'=>'showupprcase')); 
               nicHandleWidget($this,'admin_contact_nic_1','Domains_Creation_Details_admin_contact_nic_1');
					?></div>
				</td>
			</tr>
			<tr>
				<td><?php // admin contact 2 (nic)
					echo $form->labelEx($model,'admin_contact_nic_2'); echo $form->error($model,'admin_contact_nic_2');
				?></td>
				<td colspan="3">
					<div class="row"><?php
						echo $form->textField($model,'admin_contact_nic_2', array('class'=>'showupprcase')); 
                  nicHandleWidget($this,'admin_contact_nic_2','Domains_Creation_Details_admin_contact_nic_2');
					?></div>
				</td>
			</tr>
			<tr>
				<td><?php // tech contact (nic)
					echo $form->labelEx($model,'tech_contact'); echo $form->error($model,'tech_contact');
				?></td>
				<td colspan="3">
					<div class="row"><?php
						echo $form->textField($model,'tech_contact', array('class'=>'showupprcase')); nicHandleWidget($this,'tech_contact','Domains_Creation_Details_tech_contact');
					?></div>
				</td>
			</tr>
            <?php
                $this->widget('application.widgets.nameservers.nameserversWidget', array(
                    'form' => $form,
                    'model' => $model,
                    'domainsAsArray' => true,
                ));
            ?>
            <tr>
                <td>
                  <?php
                     echo $form->hiddenField($model,'paymentType');
                     echo $form->labelEx($model,'paymentType'); echo $form->error($model,'paymentType');
                  ?>
                </td>
                <td colspan="3" class="payment_type_box">
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
                    <div class="hint">Please note, that charity domains can only have a one year registration period</div>                    
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
            <?php if ($uploaderModel->hasErrors()): ?>
            <tr>
                <td colspan="4">
                <?php echo $form->errorSummary($uploaderModel); ?>
                </td>
            </tr>
            <?php endif; ?>
            <tr>
                <td colspan="4">
                <?php
                    $this->widget('application.widgets.documentUploader.DocumentUploaderWidget', array(
                        'uploaderModel' => $uploaderModel,
                        'domains' => $model->domainlist,
                    ));
                ?>
                </td>
            </tr>
			<tr>
				<td><?php // accept-t-and-c (checkbox)
					echo $form->labelEx($model,'accept_tnc'); echo $form->error($model,'accept_tnc');
					?></td>
				<td colspan="3">
					<div class="row"><?php
						echo $form->checkBox($model,'accept_tnc');
					?></div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="row buttons"><?php
		echo CHtml::submitButton('Register Domain(s)');
	?></div>
<?php $this->endWidget(); ?>
</div>

<script type="text/javascript">   
      function setupHidden(obj) {
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
        var select  = document.getElementById('Domains_Creation_Details_registration_period');
        var index = select.selectedIndex;
        var period = 0;
        if(index != -1)
         period = select.options[index].value;
        
        var summary = new SummaryClass();
        var domainsWithFeesLength = document.getElementById('domainsWithFees').innerHTML;
        var domainsWithFeesTrimedLength = domainsWithFeesLength.trim();
        domainsWithFeesLength = domainsWithFeesTrimedLength;
        
        if (document.getElementById('domainsWithFees') != null && domainsWithFeesLength > 0) {
            var domainsArray = document.getElementById('domainsWithFees').innerHTML.split(",");
            var domainsSelect = document.getElementById('Domains_Creation_Details_domainlist');
            var domainsItem = domainsSelect.getElementsByTagName("option");
            
            var allCheckedCheckboxes = $("input[@type=checkbox]:checked");
            for (var i = 0; i < allCheckedCheckboxes.length; i++) {
                var labelId = allCheckedCheckboxes[i].id;
                var label = $("label[for=" + labelId + "]").text();
                if (label) {
                    var n = domainsArray.indexOf(label);
                    summary.add(domainsArray[n + 1], domainsArray[n + 2]);
                }
            }
        } else if (document.getElementById('productsCodesWithFees') != null) {
            var productsWithFeesArray = document.getElementById('productsCodesWithFees').innerHTML.split(",");
            var domainsSelect = document.getElementById('domainname');
            var domainsItem = domainsSelect.getElementsByTagName("option");
            var n = productsWithFeesArray.indexOf(period);
            summary.add(productsWithFeesArray[n + 1] * domainsItem.length, productsWithFeesArray[n + 2] * domainsItem.length);
        }
        
        var o = document.getElementById('Domains_Creation_Details_registration_period');
        if(o != null) {
           var index = o.selectedIndex;
           var euros = document.getElementById('Domains_Creation_Details_euros_amount');
           if(euros != null) {
               document.getElementById('Domains_Creation_Details_euros_amount').value = summary.tTotal;
           }
        }
        document.getElementById('feeVal').innerHTML = currencyFormatter(summary.tFee);
        document.getElementById('vatVal').innerHTML = currencyFormatter(summary.tVat);
        document.getElementById('totalVal').innerHTML = currencyFormatter(summary.tTotal);
    }
    refreshSummary();
      
    function checkCharity() {
       var period = document.getElementById('Domains_Creation_Details_registration_period');
       var index = period.selectedIndex;
       var value = period.options[index].innerHTML; 
       var isChecked = false;      
       var checkedRadioButtonValue = $("input[@type=radio]:checked").val();
       if(checkedRadioButtonValue == 'CH') {
          isChecked = true;
       }
      
       if(value != 1 && isChecked) {
          /*Select period one*/
          period.options[0].selected = true;
       }
       return true;
    }
      
    function paymentTypeChanged() {
        var pay = document.getElementById('Domains_Creation_Details_paymentType');
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
       // pay.value = obj.value;
    }
</script>
<!-- END protected/views/domains/domaindetails.php -->
