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
class PendingPaymentsModel extends ViewGridModelBase {

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
       'A' => array(
           'resultfield' => 'domainName',
           'criteriafield' => 'domainName',
           'label' => 'Domain Name',
           'width' => 60,
           'link' => 'domains/viewdomain',
           'type' => 'string',
       ),
       'B' => array(
           'resultfield' => 'invNumber',
           'criteriafield' => 'invNumber',
           'label' => 'Invoice Number',
           'width' => 20,
           'type' => 'string',
       ),
       'C' => array(
           'resultfield' => 'transactionDate',
           'criteriafield' => 'transactionDate',
           'label' => 'Transaction Date',
           'width' => 20,
           'type' => 'date',
       ),
       'D' => array(
           'resultfield' => 'sessId',
           'criteriafield' => 'sessId',
           'label' => 'Sess_ID',
           'width' => 60,
           'type' => 'string',
       ),
       'E' => array(
           'label' => 'VAT',
           'resultfield' => 'vat',
           'criteriafield' => 'vat',
           'width' => 20,
           'type' => 'currency',
       ),
       'F' => array(
           'label' => 'Amount',
           'resultfield' => 'amount',
           'criteriafield' => 'amount',
           'width' => 20,
           'type' => 'currency',
       ),
       'G' => array(
           'label' => 'Batch Total',
           'resultfield' => 'batchTotal',
           'criteriafield' => 'batchTotal',
           'width' => 20,
           'type' => 'currency',
       ),
   );

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  unknown $sortparams Parameter description (if any) ...
    * @return object  Return description (if any) ...
    * @access public 
    */

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
          'A' => $o->domainName,
          'B' => cleanString($o->invNumber),
          'C' => parseXmlDate($o->transactionDate),
          'D' => $o->sessId,
          'E' => number_format($o->vat, 2, '.', ''),
          'F' => number_format($o->amount, 2, '.', ''),
          'G' => number_format($o->batchTotal, 2, '.', ''),
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
      return 'PendingPaymentsModel_';
   }

}

