<?php
$pg = 'View Invoice';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<?php
#Yii::log('result00= ' . print_r($result, true));

if (isset(Yii::app()->session['returnAction'])) {
    $form = $this->beginWidget('CActiveForm',array('action'=> $this->createUrl(Yii::app()->session['returnAction'])));
    echo CHtml::submitButton('Back to results');
    $this->endWidget();
}
echo '<br>';
echo "<table class='formfields gridtable'";
echo "<tr class='formfields'>
         <td class='gridtablecell'><strong>Domain Name</strong></td>
         <td class='gridtablecell'><strong>Payment Ref</strong></td>
         <td class='gridtablecell'><strong>Reg Date</strong></td>
         <td class='gridtablecell'><strong>Ren Date</strong></td>
         <td class='gridtablecell'><strong>Xfer Date</strong></td>
         <td class='gridtablecell'><strong>Type</strong></td>
         <td class='gridtablecell'><strong>Years</strong></td>
         <td class='gridtablecell'><strong>Amount</strong></td> 
         <td class='gridtablecell'><strong>Vat</strong></td> 
         <td class='gridtablecell'><strong>Total</strong></td> 
      </tr>";
if (is_array($result)) {
    foreach ($result as $key) {

        echo "<tr class='formfields'>";
        echo "<td class='formfields gridtablecell'>" . $key->domainName . "</td>";
        echo "<td class='formfields gridtablecell'>" . $key->orderId . "</td>";
        echo "<td class='formfields gridtablecell'>" . parseXmlDate($key->regDate) . "</td>";
        echo "<td class='formfields gridtablecell'>" . parseXmlDate($key->renDate) . "</td>";
        if (isset($key->transferDate)) {
            echo "<td class='formfields gridtablecell'>" . parseXmlDate($key->transferDate) . "</td>";
        } else {
            echo "<td class='formfields gridtablecell'/>";
        }
        echo "<td class='formfields gridtablecell'>" . $key->operationType . "</td>";
        echo "<td class='formfields gridtablecell'>" . $key->durationYears . "</td>";
        echo "<td class='formfields gridtablecell'>€ " . number_format($key->netAmount, 2) . "</td>";
        echo "<td class='formfields gridtablecell'>€ " . number_format($key->vatAmount, 2) . "</td>";
        echo "<td class='formfields gridtablecell'>€ " . number_format($key->total, 2) . "</td>";
        echo "</tr>";
    }
} else if (is_object($result)) {
    echo "<tr class='formfields'>";
    echo "<td class='formfields gridtablecell'>" . $result->domainName . "</td>";
    echo "<td class='formfields gridtablecell'>" . $result->orderId . "</td>";
    echo "<td class='formfields gridtablecell'>" . parseXmlDate($result->regDate) . "</td>";
    echo "<td class='formfields gridtablecell'>" . parseXmlDate($result->renDate) . "</td>";
    if (isset($result->transferDate)) {
        echo "<td class='formfields gridtablecell'>" . parseXmlDate($result->transferDate) . "</td>";
    } else {
        echo "<td class='formfields gridtablecell'/>";
    }
    echo "<td class='formfields gridtablecell'>" . $result->operationType . "</td>";
    echo "<td class='formfields gridtablecell'>" . $result->durationYears . "</td>";
    echo "<td class='formfields gridtablecell'>€ " . number_format($result->netAmount, 2) . "</td>";
    echo "<td class='formfields gridtablecell'>€ " . number_format($result->vatAmount, 2) . "</td>";
    echo "<td class='formfields gridtablecell'>€ " . number_format($result->total, 2) . "</td>";
    echo "</tr>";
} else if (!isset($result)) {
    Yii::log('res is null!');
    $this->redirect('index.php?r=/account_view_invoice_history/menu');
}
echo "</table>";

?>