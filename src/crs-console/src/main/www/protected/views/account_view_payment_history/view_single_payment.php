<!-- START protected/views/account_view_payment_history/view_single_payment.php -->
<?php
/**
* View page for payment transaction details
*
* @category  NRC
* @package   IEDR_New_Registrars_Console
* @author    IEDR <asd@iedr.ie>
* @copyright 2011 IEDR
* @license   http://www.iedr.ie/ (C) IEDR 2011
* @version   CVS: $Id:$
* @link      https://statistics.iedr.ie/
*/


$pg = 'View Payment Details';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<?php
if (isset(Yii::app()->session['returnAction'])) {
    $form = $this->beginWidget('CActiveForm',array('action'=> $this->createUrl(Yii::app()->session['returnAction'])));
    echo CHtml::submitButton('Back to results');
    $this->endWidget();
}
echo '<br>';
?>

	<div class="row">
        <?php
        $orderId = null;
        if (is_array($model->transaction)) {
            $orderId = $model->transaction[0]->orderId;
        } else if (is_object($model->transaction)) {
            $orderId = $model->transaction->orderId;
        }
        ?>
		<?php echo "Order ID: $orderId"; ?>
	</div>
	
	<?php 
echo "<table class='formfields gridtable'>";
echo "<tr class='formfields gridtable'> 
         <td class='gridtablecell'><strong>Domain Name</strong></td>
         <td class='gridtablecell'><strong>Operation Type</strong></td>
         <td class='gridtablecell'><strong>Amount</strong></td> 
         <td class='gridtablecell'><strong>Vat</strong></td> 
         <td class='gridtablecell'><strong>Total</strong></td> 
      </tr>";
if(is_array($model->transaction)) {
      foreach($model->transaction as $key) {
            
            echo "<tr class='formfields gridtable'>";
            echo "<td class='formfields gridtable'>".$key->domainName."</td>";
            echo "<td class='formfields gridtable'>".$key->operationType."</td>";
            echo "<td class='formfields gridtable'>€ ".number_format($key->netAmount,2)."</td>";
            echo "<td class='formfields gridtable'>€ ".number_format($key->vatAmount,2)."</td>";
            echo "<td class='formfields gridtable'>€ ".number_format($key->total,2)."</td>";
            echo "</tr>";
      }
} else if(is_object($model->transaction)) {
            echo "<tr class='formfields gridtable'>";
            echo "<td class='formfields gridtable'>".$model->transaction->domainName."</td>";
            echo "<td class='formfields gridtable'>".$model->transaction->operationType."</td>";
            echo "<td class='formfields gridtable'>€ ".number_format($model->transaction->netAmount,2)."</td>";
            echo "<td class='formfields gridtable'>€ ".number_format($model->transaction->vatAmount,2)."</td>";
            echo "<td class='formfields gridtable'>€ ".number_format($model->transaction->total,2)."</td>";
            echo "</tr>";
} else if(!isset($model->transaction)) {
   Yii::log('res is null!');
   $this->redirect('index.php?r=/account_view_payment_history/menu');
}
     echo "</table>";
?>		
<!-- END protected/views/account_view_payment_history/view_single_payment.php -->