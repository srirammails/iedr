<?php
?>

<script><?php include_once 'protected/widgets/js/common.js'; ?></script>
<script>
   
function checkSelected() {
   var allCheckedCheckboxes = $("input[@type=checkbox]:checked");
   if(allCheckedCheckboxes.length == 0) {
      alert('You must select any domains.');
      return false;
   } else {
      // Get selected periods
      var o = document.getElementById(getModel() + '_period');
      o.value = '';
      for (var i = 0; i < allCheckedCheckboxes.length; i++) {
         var domains = allCheckedCheckboxes[i].id;
         domains = domains.split('_');
         
         var periods = document.getElementById('period_' + domains[2] + '.ie');
         if(periods != null) {
            o.value += domains[2] + '.ie ' + periods.innerHTML + ',';
         } else if(periods == null) {
            var periods = document.getElementById(getModel() + '_list_' + domains[2] + '.ie');
            if(periods != null) {
               var index = periods.selectedIndex;
               o.value += domains[2] + '.ie ' + periods.options[index].value + ',';
            }
         }
      }
      return true;
   }
}

function setup(obj, domain) {
   var period = document.getElementById('period_' + domain);
   if(period != null) { //If period null that means voluntary
//      var index = 0;
      //if(obj.type == "select") {
        //index = obj.selectedIndex;
        //period.innerHTML = obj.options[index].text.charAt(0);
      //}
      refreshSummary(domain);
   }
}

function SummaryClass() {
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

function refreshSummary(domain) {
   var select = document.getElementById(getModel() + '_list_' + domain);
   var summary = new SummaryClass();
   var end = '.ie';

   if (document.getElementById('productsCodesWithFees') != null) {
      var productsWithFeesArray = document.getElementById('productsCodesWithFees').innerHTML.split(",");
      var prodecodeElement = document.getElementById(getModel() + '_list_' + domain);
      domain = domain.replace(end,"");
      var selected = document.getElementById(getModel() + '_domlist_' + domain + '_confirmed');

      if(selected.checked == true) {
         var prodcodeSelectValue = prodecodeElement.options[prodecodeElement.selectedIndex].value;
         if (prodcodeSelectValue) {
            var n = productsWithFeesArray.indexOf(prodcodeSelectValue);
            summary.add(productsWithFeesArray[n + 1], productsWithFeesArray[n + 2]);
         }
      }
   }

   // var index = select.selectedIndex;   
   // var period = select.options[index].text.charAt(0);
   var period = select.value;
   //alert(period);
   document.getElementById('period_' + domain + '.ie').innerHTML = period;
   document.getElementById('fee_' + domain + end).innerHTML = currencyFormatter(summary.tFee);
   document.getElementById('vat_'  + domain + end).innerHTML = currencyFormatter(summary.tVat);
   document.getElementById('total_' + domain + end).innerHTML = currencyFormatter(summary.tTotal);

   var allCheckedCheckboxes = $("input[@type=checkbox]:checked");

   var fee = 0.00;
   var vat = 0.00;
   var total = 0.00;

   for (var i = 0; i < allCheckedCheckboxes.length; i++) {
      var prodcodeSelectId = allCheckedCheckboxes[i].id;
      var a = prodcodeSelectId.split('_');
      var title = 'fee_' + a[2] + end;
      var toSplit = (document.getElementById(title).innerHTML).split(' ');
      if (! (toSplit[1] === undefined)) {
      	fee += parseFloat(toSplit[1].replace(/,/g, ''));  
      }
      title = 'vat_' + a[2] + end;
      toSplit = (document.getElementById(title).innerHTML).split(' ');
      if (! (toSplit[1] === undefined)) {
      	vat += parseFloat(toSplit[1].replace(/,/g, ''));
      } 
      title = 'total_' + a[2] + end;
      toSplit = (document.getElementById(title).innerHTML).split(' ');
      if (! (toSplit[1] === undefined)) {
      	total += parseFloat(toSplit[1].replace(/,/g, ''));
      } 
   }

   document.getElementById('totalfee').innerHTML =  currencyFormatter(fee);
   document.getElementById('totalvat').innerHTML =  currencyFormatter(vat);
   document.getElementById('totaltotal').innerHTML =  currencyFormatter(total);

   var euros = document.getElementById(getModel() + '_euros_amount');
   if(euros != null) {
      document.getElementById(getModel() + '_euros_amount').value = total;
   }
}

function getModel() {
   return '<?php echo get_class($this->model); ?>';
}

function setDefaults(array, domain) {
   var s = array.split(',');
   for(var i=0;i<s.length;i++) {
      var period = s[i].split('~');
      if(period[0] == domain) {
         var select = document.getElementById(getModel() + '_list_' + domain);
         for(var j = 0 ; j < select.options.length; j++) {
            if(select.options[j].value == period[1]) {
               select.selectedIndex = j;
               var period = document.getElementById('period_' + domain);
               if(period != null) {
                  index = select.selectedIndex;
                  period.innerHTML = select.options[index].value;
                  refreshSummary(domain);
               }
            }
         }
      }
   }
}

function disableSubmit(id) {
    document.getElementById(id).disabled=true;
    document.getElementById(id).value='Submitting, please wait...';
}

</script>