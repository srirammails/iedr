<?php
/**
 * Description of CurrentRenewalsModel
 *
 * @author Artur Kielak
 */

class CurrentRenewalsModel extends AccountAdvanceGridModel {

   public $returnurl = '';
   public $countDays;
   public $duration = array();

   public function __construct($obj) {
      parent::__construct($obj);
      $this->countDays = 30;
      $this->duration = Utility::getPeriodsWithPrices('REN');
      $this->columns = array(
          'PK' => array(
              'label' => 'Domain',
              'width' => 60,
              'link' => 'domains/viewdomain',
              'resultfield' => 'domainName',
              'criteriafield' => 'domainName',
              'type' => 'string',
          ),
          'H' => array(
              'label' => 'Periods',
              'width' => 35,
              'title' => 'false',
          ),
          'C' => array(
              'label' => 'Holder',
              'resultfield' => 'holder',
              'width' => 50,
              'type' => 'string',
          ),
          'D' => array(
              'label' => 'Registration',
              'width' => 20,
              'resultfield' => 'registrationDate',
              'type' => 'string',
          ),
          'K' => array(
              'label' => 'Renewal',
              'resultfield' => 'renewDate',
              'width' => 20,
              'type' => 'string',
          ),
      );
   }

   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array(
          'PK' => encode($o->name),
          'H' => ' ' . Utility::createSelect(encode($o->name), $this->duration),
          'C' => cleanString(encode($o->holder)),
          'D' => parseXmlDate($o->registrationDate),
          'K' => parseXmlDate($o->renewDate),
      );
   }
   
   public function getSearchBase() {
      return new CRSDomainAppService_domainForRenewalCriteriaVO();
   }
   
   public function getSort($sortparams) {
      $sort = parent::getSort($sortparams);
      return $sort;
   }
}

?>
