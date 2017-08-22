<?php

/**
 * Description of AccountReportsDeletedModel
 *
 * @author Artur Kielak
 */
class DeletedDomainReportModel extends GridModelBase {
   public $columns = array();
   public $defaultSortColumn = 'A';
   public function __construct() {
      $this->columns = array(
       'A' => array(
           'label' => 'Domain',
           'width' => 60,
           'resultfield' => 'domainName',
           'criteriafield' => 'domainName',
       ),
       'B' => array(
           'label' => 'Holder',
           'resultfield' => 'domainHolder',
           'width' => 20,
       ),
       'C' => array(
           'label' => 'Holder Category',
           'resultfield' => 'holderCategory',
           'width' => 20,
       ),
       'D' => array(
           'label' => 'Holder Class',
           'resultfield' => 'holderClass',
           'width' => 23,
       ),
       'E' => array(
           'label' => 'Reg Date',
           'resultfield' => 'registrationDate',
           'width' => 23,
       ),
       'F' => array(
           'label' => 'Ren Date',
           'resultfield' => 'renewalDate',
           'width' => 23,
       ),
       'G' => array(
           'label' => 'Deletion Date',
           'width' => 20,
           'resultfield' => 'deletionDate',
       ),
   );
   }

   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array
          (
          'A' => encode($o->domainName),
          'B' => encode($o->domainHolder),
          'C' => encode($o->holderClass),
          'D' => encode($o->holderCategory),
          'E' => parseXmlDate($o->registrationDate),
          'F' => parseXmlDate($o->renewalDate),
          'G' => parseXmlDate($o->deletionDate),
      );
   }
}
?>
