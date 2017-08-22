<script> 
    function sendEmail(number) {
       var urlWithVars = '<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=account_view_invoice_history/sendemail'; echo $host;?>';
       urlWithVars += '&number=' + number;
       $.ajax({
            url:urlWithVars,
            success: function(message) {
               if(message == 'sendemail') {
                 alert('Send email succesful.');
               } else if (message == 'sendemailfailure') {
                 alert('Send email not succesful.');
               }
            }
       });
    }
</script>

<?php
$pg = 'View Invoice History';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>


<?php 

GridUtility::getDatesForInvoice($this, $pg, 'accounts_invoice', $model, 'date');

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> $pg.' for \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('account_view_invoice_history/invoices',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('account_view_invoice_history/getgriddata_invoices',array('model'=>$model_serialised)),
	'model'		=> $model,
	'searching'	=> true,
	'sorting'	=> true,
	));
?>