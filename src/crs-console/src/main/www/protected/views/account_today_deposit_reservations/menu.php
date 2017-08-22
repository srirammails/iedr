<!-- START protected/views/accounts/menu.php -->
<?php
$pg = 'View Today`s Deposit Reservations';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<?php 
   echo "<div style='display:inline'>
         <table class='gridtable'>
         <tr>
            <td class='gridtablecell'><strong>Total Domains</strong></td>
            <td class='gridtablecell'><strong>Fee Amount</strong></td>
            <td class='gridtablecell'><strong>Vat Amount</strong></td>
            <td class='gridtablecell'><strong>Total Amount with Vat</strong></td>
         </tr>
         <tr>
            <td class='gridtablecell'>".$model->getTotalResults()."</td>
            <td class='gridtablecell'>€ ".number_format($model->getTotalAmount(),2)."</td>
            <td class='gridtablecell'>€ ".number_format($model->getTotalVat(),2)."</td>
            <td class='gridtablecell'>€ ".number_format($model->getTotalWithVat(),2)."</td>
         </tr>
         </table>
         </div>
       ";
?>
<?php

$model_serialised = Account_today_deposit_reservationsController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array(
        'caption'               => 'View Today`s Deposit Reservations for user \''.Yii::app()->user->name.'\' ',
        'thisfile'              => $this->createUrl('account_today_deposit_reservations/menu',array('model'=>$model_serialised)),
        'datasource'            => $this->createUrl('account_today_deposit_reservations/getgriddataDepositBalanceReady',array('model'=>$model_serialised)),
        'model'                 => $model,
        ));

?>