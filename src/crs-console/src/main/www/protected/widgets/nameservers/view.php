<?php
    $maxRows = $this->model->maxRows;
    $minRows = $this->model->minRows;
    $nameservers = $this->nameservers;
    $ipAddresses = $this->ipAddresses;
    $nameserversCount = $this->nameserversCount;
?>

<script>

   function appendRow() {
      var count = document.getElementById('ns_count');
      if(parseInt(count.value) < <?php echo $maxRows; ?>) {
         var o = document.getElementById('row_' + (parseInt(count.value)));
         var ns = document.getElementById('ns_' + (parseInt(count.value)));
         if(ns != null) {
            ns.value = "";
         }
         var ip = document.getElementById('ip_' + (parseInt(count.value)));
         if(ip != null) {
            ip.value = "";
         }
         if(o != null) {
            o.style.display = 'table-row';
            count.value = (parseInt(count.value) + 1);
         }
      } else {
         alert('There should be <?php echo $maxRows; ?> nameservers at most.');
      }
   }

   function removeRow(object) {
      var count = document.getElementById('ns_count');
      if(parseInt(count.value) > <?php echo $minRows; ?>) {
         var buttonId = object.id;
         var id = parseInt(buttonId.replace('ns_del_buttton_', ''));
         var o = document.getElementById('row_' + id);
         if(o != null) {
            copyNextFieldsValues(id, count.value);
            count.value = (parseInt(count.value) - 1);
            disableField(count.value);
         }
      } else {
         alert('There should be at least <?php echo $minRows; ?> nameservers.');
      }
   }

   function copyNextFieldsValues(fieldId, fieldsCount) {
       for (var i = fieldId; i < fieldsCount - 1; i++) {
           var ns = document.getElementById('ns_' + parseInt(i));
           var nextNs = document.getElementById('ns_' + (parseInt(i) + 1));
           if (ns != null && nextNs != null) {
               ns.value = nextNs.value;
           }
           var ip = document.getElementById('ip_' + parseInt(i));
           var nextIp = document.getElementById('ip_' + (parseInt(i) + 1));
           if (ip != null && nextIp != null) {
               ip.value = nextIp.value;
           }
       }
   }

   function disableField(fieldId) {
       var o = document.getElementById('row_' + parseInt(fieldId));
       o.style.display = 'none';
   }

   <?php require_once('protected/views/domains/verifyDns.js'); ?>
   function collectDomains() {
      var domains = new Array();
      <?php if ($this->domainsAsArray) { ?>
          var domainList = $('#<?php echo $this->domainId; ?> option:selected');
          domains = $.map(domainList, function(x) {
              return x.value;
          });
      <?php } else { ?>
         domains = $('#<?php echo $this->domainId; ?>').val().split(',');
      <?php } ?>
      return domains;
   }

   function collectNameservers() {
      var result = new Array();
      var o_count = document.getElementById('ns_count');
      var ns_count = parseInt(o_count.value);
      for(var i = 0; i < ns_count; i++) {
         var o_name = document.getElementById('ns_' + (parseInt(i)));
         if (o_name != null) {
             var name = o_name.value;
             var ipAddress = "";
             o_ip = document.getElementById('ip_' + (parseInt(i)));
             if (o_ip != null) {
                 ipAddress = o_ip.value;
             }
         }
         if ($.trim(name) != "") {
             result.push({name:name, ipAddress:ipAddress});
         }
      }
      return result;
   }

</script>



<?php
    echo $form->hiddenField($this->model, "$nameserversCount", array('id' => 'ns_count'));

    if ($this->self_arrange) {
        echo "<tr><td colspan = '4'><table>";
    }
    for($i = 0; $i < $maxRows; $i++) {
        if($i < $this->model->$nameserversCount) {
            echo "<tr id='row_$i' style='table-row'>";
        } else {
            echo "<tr id='row_$i' style='display:none'>";
        }
        if ($this->indent) {
            echo "<td>&nbsp;</td>";
        }

        $nsArray = array('id' => 'ns_'.$i);
        $nserrArray = array('id' => 'ns_'.$i.'_err');
        $ipArray = array('id' => 'ip_'.$i);
        $iperrArray = array('id' => 'ip_'.$i.'_err');
        $delButtonArray = array('id'=> 'ns_del_buttton_'.$i, 'onclick' => "javascript:removeRow(this);");
        $addButtonArray = array('id'=> 'ns_add_buttton', 'onclick' => "javascript:appendRow()");
        $verButtonArray = array('id'=> 'ns_ver_buttton', 'onclick' => 'verifyDNS("' . $form->id . '", collectDomains(), collectNameservers()); return false');
        if ($this->disabled) {
            $nsArray['disabled'] = 'disabled';
            $ipArray['disabled'] = 'disabled';
            $delButtonArray['style'] = 'display:none';
            $addButtonArray['style'] = 'display:none';
            $verButtonArray['style'] = 'display:none';
        }
        if (isset($this->size)) {
            $nsArray['size'] = $this->size;
            $ipArray['size'] = $this->size;
        }
        echo "<td class='nameserver'>";
        if ($this->labels) {
            echo $this->form->label($this->model, "$nameservers"."[$i]", array('for' => 'ns_'.$i, 'label' => 'Nameserver '.($i+1)));
        }
        echo $this->form->error($this->model, "$nameservers"."[$i]", $nserrArray);
        if ($this->labels) {
            echo "</td><td class='nameserver'>";
        }
        //echo "<div class='row'>";
        echo ' '.$this->form->textField($this->model, "$nameservers"."[$i]", $nsArray)./*"</div>*/"</td>";
        if (isset($this->model->$ipAddresses)) {
            echo "<td class='nameserver'>";
            if ($this->labels) {
                echo $this->form->label($this->model, "$ipAddresses"."[$i]", array('for' => 'ip_'.$i, 'label' => 'IP Address '.($i+1)));
            }
            echo $this->form->error($this->model, "$ipAddresses"."[$i]", $iperrArray);
            if ($this->labels) {
                echo "</td><td class='nameserver'>";
            }
            //echo "<div class='row'>";
            echo ' '.$this->form->textField($this->model, "$ipAddresses"."[$i]", $ipArray)./*"</div>*/"</td>";
        }
        echo "<td colspan='3'>";
        echo CHtml::button('Delete', $delButtonArray);
        echo "</td></tr>";
    }
    if ($this->self_arrange) {
        echo "</table></td></tr>";
    }
?>

<tr>
    <td colspan="4">
        <div class="row buttons">
            <?php echo CHtml::button('Add entry', $addButtonArray);
            if ($this->dnsCheck) {
                echo CHtml::button('Verify', $verButtonArray); 
            } ?>
        </div>
    </td>
</tr>