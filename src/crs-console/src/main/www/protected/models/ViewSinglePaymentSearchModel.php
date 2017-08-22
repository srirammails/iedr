<?php
/**
 * Description of ViewSinglePaymentSearchModel
 *
 * @author Artur Kielak
 */
class ViewSinglePaymentSearchModel extends GridModelBase {
   public $domainName = '';
   public $defaultSortColumn = 'A';

   public function __construct() {
   $this->columns = array(
       'A' => array(
           'resultfield' => 'domainName',
//           'criteriafield' => 'domainName',
           'label' => 'Domain name',
           'width' => 50,
       ),
       'B' => array(
           'resultfield' => 'settledDate',
           //'criteriafield' => 'settledDate',
           'label' => 'Settlement Date',
           'width' => 50,
       ),
       'C' => array(
           'resultfield' => 'paymentMethod',
           //'criteriafield' => 'paymentMethod',
           'label' => 'Payment method',
           'width' => 50,
           'type' => 'string',
       ),
       'D' => array(
           'resultfield' => 'orderId',
           'label' => 'Order ID',
           'width' => 50,
           'type' => 'string',
       ),
       'E' => array(
           'resultfield' => 'invoiceNumber',
           //'criteriafield' => 'paymentMethod',
           'label' => 'Invoice number',
           'width' => 30,
           'type' => 'string',
       ),
       'F' => array(
           'resultfield' => 'creationDate',
           //'criteriafield' => 'paymentMethod',
           'label' => 'Invoice Date',
           'width' => 30,
           'type' => 'string',
       ),
       'G' => array(
           'resultfield' => 'netAmount',
           //'criteriafield' => 'paymentMethod',
           'label' => 'Base cost',
           'width' => 30,
           'type' => 'currency',
       ),
       'H' => array(
           'resultfield' => 'vatAmount',
           //'criteriafield' => 'paymentMethod',
           'label' => 'VAT',
           'width' => 30,
           'type' => 'currency',
       ),
       'J' => array(
           'resultfield' => 'total',
           'label' => 'Total cost',
           'width' => 30,
           'type' => 'currency',
       ),
   );
   }

   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      $paymentType = '';
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
          'A' => $o->domainName,
          'B' => parseXmlDate($o->settledDate), 
          'C' => $paymentType,
          'D' => $o->orderId,
          'E' => $o->invoiceNumber,
          'F' => parseXmlDate($o->creationDate),
          'G' => number_format($o->netAmount,2),
          'H' => number_format($o->vatAmount,2),
          'J' => number_format($o->total,2)
      );
   }
   
   public function getSearchBase() {
      return new CRSPaymentAppService_reservationSearchCriteria();
   }
}

?>
