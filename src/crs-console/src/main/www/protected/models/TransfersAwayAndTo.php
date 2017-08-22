<?php

/**
 * Short description for file
 * 
 * Long description (if any) ...
 * 
 * PHP version 5
 * 
 * New Registration Console (C) IEDR 2011
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */

/**
 * Short description for class
 * 
 * Long description (if any) ...
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
class TransfersAwayAndTo extends ViewGridModelBase {

   var $transferDirection = '';
   var $transferDateFrom = '';
   var $transferDateTo = '';

   /**
    * Transfers in the last DEFAULT_DAYS will be displayed for the first time and
    * every time user enters incorrect value for number of days.
    */
   const DEFAULT_DAYS = 30;

   /**
    * Number of days for which we display transfers.
    * @var    integer
    * @access public 
    */
   var $days = self::DEFAULT_DAYS;

   /**
    * Description for public
    * @var    string
    * @access public
    */
   public $defaultSortColumn = 'A';

   /**
    * Description for var
    * @var    array 
    * @access public
    */
   var $columns = array(
       'A' => 'initialized later',
       'B' => array(
           'resultfield' => 'domainHolder',
           'criteriafield' => 'domainHolder',
           'label' => 'Holder',
           'width' => 60,
           'type' => 'string',
       ),
       'C' => array(
           'resultfield' => 'transferDate',
           'criteriafield' => 'transferDate',
           'label' => 'Date Transferred',
           'width' => 20,
           'type' => 'date',
       ),
       'D' => array(
           'resultfield' => 'registrationDate',
           'criteriafield' => 'registrationDate',
           'label' => 'Reg Date',
           'width' => 20,
           'type' => 'date',
       ),
       'E' => array(
           'resultfield' => 'renewalDate',
           'criteriafield' => 'renewalDate',
           'label' => 'Ren Date',
           'width' => 20,
           'type' => null,
           //'type' => 'date',
       ),
   );

   public function resetDays() {
      $this->days = self::DEFAULT_DAYS;
   }

   public function __construct($transferDirection) {
      $this->transferDirection = $transferDirection;

      $to = date_create();
      $from = date_modify($to, '-' . $this->days . ' day');
      $to = date_create();

      $this->transferDateFrom = date_format($from, 'Y-m-d');
      $this->transferDateTo = date_format($to, 'Y-m-d');
      $this->initDomainField();
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return mixed  Return description (if any) ...
    * @access public
    */
   public function rules() {
      return array(
          array('days', 'required'),
          array('days', 'length', 'min' => 1, 'max' => 4),
          array('days', 'numerical', 'min' => 1, 'max' => 9999, 'integerOnly' => true),
      );
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  array  $o Parameter description (if any) ...
    * @return mixed  Return description (if any) ...
    * @access public
    */
   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array
          (
          'A' => cleanString(encode($o->name)),
          'B' => cleanString(encode($o->holder)),
          'C' => parseXmlDate($o->transferDate),
          'D' => parseXmlDateWithDefault($o->registrationDate),
          'E' => parseXmlDateWithDefault($o->renewDate),
      );
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return string    Return description (if any) ...
    * @access protected
    */
   protected function getExportFilenameTag() {
      return 'TransfersAwayAndTo_';
   }

   public function setSearchCriteria() {
      $to = date_create();
      $from = date_modify($to, '-' . $this->days . ' day');
      $to = date_create();

      $this->transferDateFrom = date_format($from, 'Y-m-d');
      $this->transferDateTo = date_format($to, 'Y-m-d');
   }

   public function getSearchBase() {
      $criteria = new CRSDomainAppService_transferedDomainSearchCriteria();
      $criteria->transferDateFrom = $this->transferDateFrom;
      $criteria->transferDateTo = $this->transferDateTo;
      $criteria->transferDirection = $this->transferDirection;
      return $criteria;
   }

   private function initDomainField() {
       if ($this->transferDirection == 'INBOUND') {
           $this->columns['A'] = array(
               'resultfield' => 'domainName',
               'criteriafield' => 'domainName',
               'label' => 'Domain Name',
               'width' => 60,
               'link' => 'domains/viewdomain',
               'type' => 'string',
           );
       } else {
           $this->columns['A'] = array(
               'resultfield' => 'domainName',
               'criteriafield' => 'domainName',
               'label' => 'Domain Name',
               'width' => 60,
               'type' => 'string',
           );
       }
   }

}

