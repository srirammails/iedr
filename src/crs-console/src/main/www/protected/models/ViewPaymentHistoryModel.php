<?php

/**
 * Description of ViewPaymentHistoryModel
 *
 * @author Artur Kielak
 */
class ViewPaymentHistoryModel extends GridModelBase {

   var $columns = array(
       'A' => array(
           'resultfield' => 'settlementDate',
           //'criteriafield' => 'settlementDate',
           'label' => 'Settlement Date',
           'width' => 60,
       ),
       'B' => array(
           'resultfield' => 'orderId',
          // 'criteriafield' => 'orderId',
           'label' => 'Order ID',
           'width' => 60,
       ),
       'C' => array(
           'resultfield' => 'totalCost',
           // 'criteriafield' => 'orderId',
           'label' => 'Amount',
           'width' => 60,
           'type' => 'currency',
       ),
       'D' => array(
           'resultfield' => 'invoiceNumber',
          // 'criteriafield' => 'invoiceId',
           'label' => 'Invoice Number',
           'width' => 60,
       	   'link' => 'account_view_invoice_history/invoiceview',
       ),
       'E' => array(
           'resultfield' => 'paymentMethod',
           //'criteriafield' => 'paymentMethod',
           'label' => 'Payment method',
           'width' => 50,
           'type' => 'string',
       ),
   		'F' => array(
   				'label' => '',
   				'width' => 15,
   				'type' => 'string',
   		),
   		
   );

   public function __construct() {
       $this->excludeFromExport = array('F');
   }


   public function addResults($o) {
       ModelUtility::hasProperty($this->columns, $o);
       switch($o->paymentMethod) {
         case 'ADP':
            $paymentType = 'Deposit account';
            break;
         case 'CC':
            $paymentType = 'Credit Card';
            break;
         case 'DEB':
            $paymentType = 'Debit Card';
            break;
         default:
            $paymentType = null;
      }
      
      return array
          (
          'A' => parseXmlDate($o->settlementDate),
          'B' => $o->orderId,
          'C' => number_format($o->totalCost, 2, '.', ' '),
          'D' => $o->invoiceNumber,
          'E' => $paymentType,
          'F' => "" . $this->createLink($o->id, $o->orderId),
      );
   }
   
   private function createLink($id, $orderId) {
   	$link = CHtml::link("View", Yii::app()->createUrl('account_view_payment_history/paymentView', array('id' => $id)));
   	return $link;
   }
}

?>
