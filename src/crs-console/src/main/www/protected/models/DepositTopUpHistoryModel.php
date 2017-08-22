<?php

class DepositTopUpHistoryModel extends GridModelBase {

   /**
    * Deposit top ups in the last DEFAULT_DAYS will be displayed
    * for the first time and every time the user enters incorrect
    * value for the number of days.
    */
   const DEFAULT_DAYS = 10;

   public $depositBalance = 0.0;
   public $depositReservation = 0.0;
   public $availableBalance = 0.0;
   public $searchParams;
   public $defaultSortColumn = 'A';
   public $columns = array(
       'A' => array(
           'label' => 'Date',
           'resultfield' => 'operationDate',
           'criteriafield' => null,
           'width' => 20,
           'type' => null,
       ),
       'B' => array(
           'label' => 'Top Up Amount',
           'resultfield' => 'topUpAmount',
           'criteriafield' => null,
           'width' => 20,
           'type' => 'currency',
       ),
       'C' => array(
           'label' => 'Order ID',
           'resultfield' => 'orderId',
           'criteriafield' => null,
           'width' => 20,
           'type' => null,
       ),
       'D' => array(
           'label' => 'Closing Balance',
           'resultfield' => 'closingBalance',
           'criteriafield' => null,
           'width' => 20,
           'type' => 'currency',
       ),
   );

   public function rules() {
      return array(
         array('searchParams', 'validateSearchParams'),
      );
   }

   public function validateSearchParams($attrib, $params) {
      if (!isset($this->searchParams['days'])) {
         return true;
      }
      $daysStr = $this->searchParams['days'];
      $daysInt = intval($daysStr);
      if (((string)$daysInt == $daysStr) and //check if a string contains integer
         ($daysInt > 0) and ($daysInt <= 999)
      ) {
         return true;
      }
      $this->addError('searchParams', 'Days must be an integer between 1 and 999.');
      return false;
   }

   public function resetSearchParams() {
      $this->searchParams = null;
   }

   public function getDays() {
      if (isset($this->searchParams['days'])) {
         return $this->searchParams['days'];
      } else {
         return self::DEFAULT_DAYS;
      }
   }

   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array
          (
          'A' => parseXmlDate($o->operationDate),
          'B' => number_format($o->topUpAmount,2),
          'C' => $o->orderId,
          'D' => number_format($o->closingBalance,2)
      );
   }

   public function getSearchBase() {
      return array(
         'fromDate' => date('Y-m-d', time() - 86400 * $this->getDays()),
         'toDate' => date('Y-m-d', time())
      );
   }

}
