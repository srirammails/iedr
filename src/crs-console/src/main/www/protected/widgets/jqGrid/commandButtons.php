<!-- START protected/widgets/jqGrid/commandButtons.php -->
<?php
/**
 * defines view html for Command Button widget.
 *
 * This page outputs some javascript functions (only once if there are multiple commandButton widgets on the same page;)
 * Then the commandButton Html form is output.
 * The jqGrid on the same page has a callback function 'checkBoxClicked' which will call a function 'handleCheckBoxClicked' here.
 * 'handleCheckBoxClicked' will then add the selected ID (usually a domain name) to an array in an instance of the javascript class 'SelectedDomainsClass'.
 * When a command Button is clicked, this array is sent by HTTP POST to the 'actionUrl'.
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       view.php, jqGridWidget, jqGridComandButtonsWidget, GridModelBase
 */
if (!defined('COMMANDBUTTON_INC_JSCRIPT')) {
   /**
    * in case of multiple command button widgets, only output the scripts the first time.
    */
   define('COMMANDBUTTON_INC_JSCRIPT', 1);
   ?>
   <script><?php include_once 'protected/widgets/js/common.js'; ?></script>
   <script>

       function getDomainPrice(periodCode) {
           <?php
           $prices = Utility::getPeriodsWithNumericFees('REN');
           if (is_array($prices) && sizeof($prices) > 0) {
               echo "switch (periodCode) {";
               foreach ($prices as $code => $price) {
                   echo "case '$code': return $price[0];";
               }
               echo "}";
           }
           ?>
           return 0;
       }

      function SelectedDomainsClass()
      {
         this.list = new Array();
         this.dpush = function(domain) { if(this.list.indexOf(domain) === -1) { /* if unique */ this.list.push(domain); } };
         this.dpop = function (domain) { var n = this.list.indexOf(domain); if(n >= 0) { this.list.splice(n, 1); } };
         this.getCount = function () { return this.list.length; };
         this.domainSelected = function (isChecked,domain) { if(isChecked) { this.dpush(domain); } else { this.dpop(domain); } };
         this.contains  = function (domain) {return this.list.indexOf(domain) != -1; };
      }

      var selectedDomains = new SelectedDomainsClass();

      function SelectedDomainsWithPeriodClass()
      {
         this.list = new Array();
         this.dpush = function(domain,period) { if(this.list.indexOf(domain) === -1) { /* if unique */ this.list.push(domain);this.list.push(period);} };
         this.dpop = function (domain) { var n = this.list.indexOf(domain); if(n >= 0) { this.list.splice(n, 2); } };
         this.getCount = function () { return this.list.length; };
         this.domainSelected = function (isChecked,domain,period) {
            if(isChecked) {
               this.dpush(domain,period);
            } else {
               this.dpop(domain);
            }
         };
         this.fee = function() {
            var sum = 0;
			for (var i=1; i < this.list.length; i+=2) {
				sum += getDomainPrice(this.list[i]);
			};
			return Math.round(sum * 100) / 100;
         };
         this.periodChanged = function(domain, period) {
             var dindex = this.list.indexOf(domain);
             if (dindex >= 0) {
                 this.list[dindex+1] = period;
             }
         };
         this.getPeriod = function(domain) {
             var dindex = this.list.indexOf(domain);
             if (dindex >= 0) {
                 return this.list[dindex+1];
             }
         };
      }

      var selectedDomainsWithSummary = new SelectedDomainsWithPeriodClass();

      function handlePageLoading(rowids) {
          if (rowids!=null) {
              for (var i=0; i < rowids.length; i++) {
                  var id = rowids[i];
                  if (selectedDomains.contains(id)) {
                      var o = document.getElementById('period_' + id);
                      o.disabled = false;
                      o.value = selectedDomainsWithSummary.getPeriod(id);
                  }
              }
          }
      };

      function handleSelectedRow(id) {
         handleCheckBoxClicked(id);
      }

      var alertShown = new Date();

      function handleCheckBoxClicked(id) {
         if(id) {
            var cell = jQuery('#thisJqGrid').getCell(id, 1);
            if(cell)
            {
               var o = document.getElementById('period_' + id);
               var cbIsChecked = false;
               if(jQuery(cell).attr('checked') == 'checked') {
                   var limit = <?php echo Yii::app()->params['bulk_operation_limit'] ?>;
                   if (selectedDomains.contains(id) || selectedDomains.getCount() < limit) {
                  	 cbIsChecked =  true;
                  	 if(o != null)
                       o.disabled = false;
                   } else {
                       if ((new Date()).getTime() - alertShown.getTime() > 500) {
                	   		alert("No more than " + limit + " domains may be selected for the operation");
                	   		alertShown = new Date();
                       }
                	   jQuery("#thisJqGrid").jqGrid('setSelection',id);
                	   cbIsChecked =  false;
                       if(o != null){
                           o.disabled = true;
                           unselectPeriods(id);
                        }
                   }
               } else {
                  cbIsChecked =  false;
                  if(o != null){
                     o.disabled = true;
                     unselectPeriods(id);
                  }
               }

               selectedDomains.domainSelected(cbIsChecked,id);
               selectedDomainsWithSummary.domainSelected(cbIsChecked, id, getPeriod(id));
            }
         }
      }

    function validDomains(domains) {
       var result = '';
       $.ajax({
          async: false,
		    url:'<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=site/validdomains&list='; echo $host;?>' + domains,
		    success: function(message) {
             result = message;
          }
		});
      return result;
    }
    $.ajaxSetup ({ cache: true });

   </script>

   <?php
} // end if not defined COMMANDBUTTON_INC_JSCRIPT
?>
   <form id="<?php echo $this->formid; ?>" action="<?php echo $this->actionurl; ?>" method="post" onsubmit="return checkSelected();">
   <input id="<?php echo $this->formid; ?>_returnurl"	name="<?php echo $this->formid; ?>[returnurl]" type="hidden" value="<?php echo $this->returnurl; ?>">
   <input id="<?php echo $this->formid; ?>_command"	name="<?php echo $this->formid; ?>[command]" type="hidden" value="to_be_filled_by_javascript">
   <input id="<?php echo $this->formid; ?>_domainlist"	name="<?php echo $this->formid; ?>[domainlist]" type="hidden" value="to_be_filled_by_javascript">
   <input id="<?php echo $this->formid; ?>_domainlistWithSummary"	name="<?php echo $this->formid; ?>[domainlistWithSummary]" type="hidden" value="to_be_filled_by_javascript">
<?php
$hr = Yii::app()->getRequest();
echo '	<input name="' . $hr->csrfTokenName . '" type="hidden" value="' . $hr->getCsrfToken() . '">' . BR;
foreach ($this->commandlist as $cmd => $str)
   echo '	<input id="' . $this->formid . '_' . $cmd . '" name="' . $this->formid . '[do_' . $cmd . ']" type="submit" value="" onclick="jQuery(\'#' . $this->formid . '_command\').val(\'' . $cmd . '\'); return true;">' . BR;
?>
</form>
<script>
   function setButtonText_<?php echo $this->formid; ?>(n, fee) {
      <?php
         foreach ($this->commandlist as $cmd => $str) {
            $str = str_replace('%n', '"+n+"', $str);
            $str = str_replace('%fee', '"+fee+"', $str);
            echo '	jQuery(\'#' . $this->formid . '_' . $cmd . '\').val("' . $str . '");' . BR;
         }
      ?>
   }

   function periodChanged(id) {
	   if(id != null) {
	      var o = document.getElementById('period_' + id);
	      	if(o != null) {
		       selectedDomainsWithSummary.periodChanged(id, o.value);
		       jQuery('#<?php echo $this->formid; ?>_domainlistWithSummary').val(selectedDomainsWithSummary.list.toString());
		       setButtonText_<?php echo $this->formid; ?>(selectedDomains.getCount(), selectedDomainsWithSummary.fee());
	        }
	   }
   }

   function unselectPeriods(id) {
      if(id != null) {
         var o = document.getElementById('period_' + id);
            if(o != null) {
               o.selectedIndex = 0;
               o.disabled = true;
            }
      } else {
         for(var i=0;i<selectedDomains.list.length;i++) {
            var o = document.getElementById('period_' + selectedDomains.list[i]);
            if(o != null) {
               o.selectedIndex = 0;
               o.disabled = true;
            }
         }
      }
   }

   function cleanSelection() {
      jQuery("#thisJqGrid").jqGrid('resetSelection');
      unselectPeriods();
      selectedDomains.list.length = 0;
      selectedDomainsWithSummary.list.length = 0;
      setButtonText_<?php echo $this->formid; ?>(0, 0);
   }

   function getPeriod(domain) {
      var o =  document.getElementById('period_' + domain);
      if(o != null)
         return o.options[o.selectedIndex].value;
      return 'StrYear';
   }

   function getTitle(value) {
         var title = "Please confirm you want to ";
         switch(value) {
            case 'payonline':
               title += "\"Pay Online\"";
               break;
            case 'paydeposit':
               title += "\"Pay From Deposit\"";
               break;
            case 'voluntary':
               title += "\"Put into Voluntary NRP \"";
               break;
            case 'revert':
               title += "\"Revert to Billable\"";
               break;
            case 'dnsMod':
               title += "\"Modify DNS\"";
               break;
            case 'removefromvoluntary':
               title += "\"Remove from NRP\"";
               break;
            case 'remove_from_batch':
               title += "\"Remove From Batch\"";
               break;
            case 'autorenew':
               title += "\"Set Autorenew\"";
               break;
            case 'authcodedownload':
                title += "\"Download Authcodes\"";
                break;
            default:
               return false;
               break;
         }

         title += " skipping the following domains: \n";
         return title;
   }

   function checkSelected() {
      if(selectedDomains.list.length == 0) {
         alert('You must select some domains.');
         return false;
      } else {
         var domainlist = document.getElementById('<?php echo $this->formid;?>_domainlist');
         var splitedDomains = domainlist.value.split(',');
         var command = document.getElementById('<?php echo $this->formid;?>_command');

         var title = getTitle(command.value);

         var invalidDomains = [];
         for(var i = 0; i < splitedDomains.length; i++) {
            if(command.value == 'payonline' || command.value == 'paydeposit') {
               var message = validDomains(splitedDomains[i]);
               if(message != 'ok' && message.length > 0) {
                  title += message;
                  invalidDomains.push(splitedDomains[i]);
	              if (i < splitedDomains.length -1) {
    	           	title += ",\n";
        	      }
               } else {
                  //title += splitedDomains[i];
               }
            } else {
               //title += splitedDomains[i];
               //title += ",\n";
            }
         }
         // removing confirmation: #9265
		// cannot remove the invocation of checkSelected (bug #9380 describes what's happening)
         var yes = (invalidDomains.length == 0) || confirm(title);

         //var yes = true;
         if(typeof yes == 'boolean' && yes == false) {
           cleanSelection();
         } else {
            var domainsList = document.getElementById("<?php echo $this->formid; ?>" + '_domainlist');
            if(domainsList != null) {
               var domainsValues = (domainsList.value).split(',');
               for(var i = 0; i < invalidDomains.length; i++) {
                  for(var j = 0;j < domainsValues.length; j++) {
                     if(invalidDomains[i] == domainsValues[j])
                        domainsValues.splice(j, 1);
                  }
               }

               if(command.value == 'payonline' || command.value == 'paydeposit') {
                  for(var i=0;i<domainsValues.length;i++) {
                     domainsValues[i] = domainsValues[i] + '~' + selectedDomainsWithSummary.getPeriod(domainsValues[i]);
                  }
               }

               if(domainsValues.length == 0) {
                  alert('There are no domains with correct state.');
                  cleanSelection();
                  return false;
               }

               domainsList.value = domainsValues.join(',');
            }
         }
         return yes;
      }
   }


   handleCheckBoxClicked_pre<?php echo $this->formid; ?> = handleCheckBoxClicked;
   handleCheckBoxClicked = function (id) {
      handleCheckBoxClicked_pre<?php echo $this->formid; ?>(id);
      jQuery('#<?php echo $this->formid; ?>_domainlist').val(selectedDomains.list.toString());
      jQuery('#<?php echo $this->formid; ?>_domainlistWithSummary').val(selectedDomainsWithSummary.list.toString());
      setButtonText_<?php echo $this->formid; ?>(selectedDomains.getCount(), selectedDomainsWithSummary.fee());
   };

   setButtonText_<?php echo $this->formid; ?>(0, 0);
</script>
<!-- END protected/widgets/jqGrid/commandButtons.php -->
