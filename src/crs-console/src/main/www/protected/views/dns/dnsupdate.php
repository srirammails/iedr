<!-- START protected/views/dns/dnsupdate.php -->

<script>

   function clear() {
      for(var i=0;i<10;i++) {
         var o = document.getElementById('row_' + i );
         document.getElementById(getModel() + '_ns_' + i).value = '';
         if(o != null) {
            o.style.display = 'none';
         }
      }
   }

   function showCurrentTable(rows) {
      var data = [];
      clear();
      var length = rows.length;
      for(var i = 0; i < length; i++) {
         
         data = (rows[i]).split('=');
         var ns = data[0];
         var ip = data[1];
         
         var o = document.getElementById('row_' + i );
         if(o != null) {
            o.style.display = 'table-row';
            document.getElementById(getModel() + '_ns_' + i).value = data[0];
         }
      }
       
      var count = document.getElementById(getModel() +'_count');
      if(count != null) {
        count.value = '' + (length);
      }
   }
   
   function saveNs(domain, nameservers) {
      var result = '';
       $.ajax({
          async: false,
          url:'<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=dns/savedns&domain='; echo $host;?>' + domain + '&nameservers=' + nameservers,
          success: function(message) { 
             result = message;
          }
      });
      return result;
   }
   
   
   function getNs(domain) {
    var result = '';
    $.ajax({
       async: false,
       url:'<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=dns/getns&domain='; echo $host;?>' + domain,
       success: function(message) { 
          result = message;
       }
   });
      return result;
   }
   
   $.ajaxSetup ({ cache: true });  
    
   function onDomainChange(obj) {
      var index = obj.selectedIndex;
      var domain = obj.options[index].innerHTML;
      var o = document.getElementById(getModel() + '_currentdomain');
      if(o != null) {
         o.value = domain;
      }
      
      var nssAsString = getNs(domain);
      var rows = nssAsString.split('~');
      showCurrentTable(rows);   
   }

</script>
<?php
/**
 * View page for DNS Update
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       DNS_BulkMod, DnsController::actionDnsupdate(), DnsController::actionMakednsupdate()
 */


/**
 * returns count of the number of commas in the model's domainlist attribute, plus 1, hopefully equals the number of domain names
 * 
 * @param  object $model object having member 'domainlist'
 * @return integer  count of the number of commas plus 1, hopefully equals the number of domain names
 */
function countDoms($model){
	return (substr_count($model->domainlist, ',') + 1);
}

/**
 * outputs a form text-field element for a nameserver entry.
 * 
 * Long description (if any) ...
 * 
 * @param  CActiveForm  $form    the yii form widget to output html for
 * @param  CModel  $model   model with data to be rendered
 * @param  string  $num     numeric index of nameserver, e.g. 1 .. 7
 * @param  unknown $type    unused
 * @param  unknown $disable unused
 * @return void
 */


#If we're dealing with multiple domains then disable the glue form textfield.
$disabled = 1;
if(countDoms($model)==1){
	$disabled = 0;
}

$pg = 'DNS Modification';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<?php

Utility::printFlashError($model->error);
Utility::printFlashNotice($model->message);

$this->widget('application.widgets.js.JavaScriptUtils', array('model' => $model));
?>

<p>This tool is designed to give Registrars the facility to modify DNS records associated with .ie domain names. 
All domains must have a zone configured correctly on each of the Nameservers you are modifying the domains to.
Correctly configured DNS must adhere to <a target="_blank" href="http://www.ietf.org/rfc/rfc1035.txt">RFC1035</a>. This system will not permit domain names be modified that are deemed to be incorrectly configured.</p>

<b>Please Note: Some Nameservers may not respond in a timely fashion. As such, this facility can take some time in verifying the DNS for multiple domain names. Please be patient.</b><br><br><br>

<div class="form">
<?php
$form = $this->beginWidget('CActiveForm', array(
   'id' => 'BulkDNSModForm',
   'enableAjaxValidation' => true,
   'clientOptions' => array(
      'validateOnSubmit' => true,
      'afterValidateAttribute' => 'js:function(form, attribute, data, hasError){
                            applyErrorsForAllFields(form, attribute, data, hasError);
                        }',
   )
));
$form->focus = array($model, 'domainlist');
?>

<table border="1" class="formfields">
	<tbody>
		<tr>
         <td colspan="4">
            <b>Please enter the new Nameservers in the text boxes below - Glue text fields will be disabled/not used for bulk updates</b>
         </td>
      </tr>
      
      <tr>
         <td colspan="4">
            <?php echo CHtml::errorSummary($model); ?>
         </td>
      </tr>
      
		<table class="formfields">
      
         <td colspan="4">
           <?php echo $form->labelEx($model,'Domains'); echo $form->hiddenField($model,'domainlist', array('id'=>'domainlist')); echo $model->domainlist;?>
         </td>
      </tr>       
		<tr>
         <td colspan="2">
           <?php echo $form->labelEx($model,'Nameservers'); ?>
         </td>
      </tr>
           <?php
           $w = $this->widget('application.widgets.nameservers.nameserversWidget', array(
               'form' => $form,
               'model' => $model,
               'labels' => false,
               'indent' => true,
               'self_arrange' => false,
               'domainId' => 'domainlist',
           ));
           ?>
</table>

<div class="row">
</div>
<div class="row buttons">
    <?php echo CHtml::submitButton('Save'); ?></div>
<?php $this->endWidget(); ?>
</div><!-- form -->
