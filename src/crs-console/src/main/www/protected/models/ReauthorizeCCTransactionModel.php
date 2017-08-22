<?php

class ReauthorizeCCTransactionModel  extends GridModelBase {
   public $date;
   public $defaultSortColumn = 'A';
   public $columns = array(
       'A' => array(
           'label' => 'Transaction Id',
           'resultfield' => 'id',
           'width' => 30,
           'type' => 'string',
           'link' => 'account_reauthorise_cc_transaction/reauthorise'
       ),
       'B' => array(
           'label' => 'Domain Name',
           'resultfield' => 'domainName',
           'width' => 30,
           'type' => 'string'
       ),
       'C' => array(
           'label' => 'Operation Type',
           'resultfield' => 'operationType',
           'width' => 30,
           'type' => 'string'
       ),
       'D' => array(
           'label' => 'Old Total Cost',
           'resultfield' => 'oldTotalCost',
           'width' => 30,
           'type' => 'currency',
       ),
       'E' => array(
           'label' => 'Old Net Amount',
           'resultfield' => 'oldNetAmount',
           'width' => 20,
           'type' => 'currency',
       ),
       'F' => array(
           'label' => 'Old Vat Amount',
           'resultfield' => 'oldVatAmount',
           'width' => 30,
           'type' => 'currency',
       ),
       'G' => array(
           'label' => 'New Total cost',
           'resultfield' => 'newTotalCost',
           'width' => 30,
           'type' => 'currency',
       ),
       'H' => array(
           'label' => 'New Net Amount',
           'resultfield' => 'newNetAmount',
           'width' => 20,
           'type' => 'currency',
       ),
       'I' => array(
           'label' => 'New Vat Amount',
           'resultfield' => 'newVatAmount',
           'width' => 30,
           'type' => 'currency',
       ),
   );

   public function __construct() {
      $this->date = date("Y-m");
      parent::__construct();
      
   }
   
   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array (
          'A' => $o->id,
          'B' => $o->domainName,
          'C' => $o->operationType,
          'D' => number_format($o->oldTotalCost, 2,'.',' '),
          'E' => number_format($o->oldNetAmount, 2,'.',' '),
          'F' => number_format($o->oldVatAmount, 2,'.',' ') ,
          'G' => number_format($o->newTotalCost, 2,'.',' '),
          'H' => number_format($o->newNetAmount, 2,'.',' '),
          'I' => number_format($o->newVatAmount, 2,'.',' ') ,
      );
   }
   
   public function getSearchBase() {
         return new CRSPaymentAppService_invoiceSearchCriteria();
   }
}

?>
