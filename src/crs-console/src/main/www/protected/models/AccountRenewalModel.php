<?php

/**
 * Description of AccountRenewalModel
 *
 * @author Artur Kielak
 */
class AccountRenewalModel extends AccountAdvanceGridModel {

   public $returnurl = '';
   public $countDays;

   public function __construct($obj) {
      parent::__construct($obj);
      $this->countDays = OVERDUE;
   }

   public $columns = array(
       'PK' => array(
           'label' => 'Domain',
           'width' => 60,
           'link' => 'domains/viewdomain',
           'resultfield' => 'domain',
           'criteriafield' => 'domainName',
       ),
       'C' => array(
           'label' => 'Holder',
           'resultfield' => 'holder',
           'criteriafield' => null,
           'width' => 20,
           'type' => null,
       ),
       'D' => array(
           'label' => 'Registration',
           'width' => 20,
           'resultfield' => 'registrationDate',
           'criteriafield' => null,
           'type' => null,
       ),
       'K' => array(
           'label' => 'Renewal',
           'resultfield' => 'renewDate',
           'criteriafield' => null,
           'width' => 23,
           'type' => null,
       ),
   );

   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array(
          'PK' => $o->name,
          'C' => cleanString($o->holder),
          'D' => parseXmlDate($o->registrationDate),
          'K' => parseXmlDate($o->renewDate),
      );
   }
}

?>
