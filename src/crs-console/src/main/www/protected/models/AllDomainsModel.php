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
class AllDomainsModel extends GridModelBase {

   public $columns = array();
   public $defaultSortColumn = 'A';
   
   public function __construct() {
      $this->columns = array(
          'A' => array(
              'resultfield' => 'name',
              'criteriafield' => 'domainName',
              'label' => 'Domain Name',
              'width' => 55,
              'link' => 'domains/viewdomain',
              'type' => 'string',
          ),
          'B' => array(
              'resultfield' => 'holder',
              'criteriafield' => 'domainHolder',
              'label' => 'Holder',
              'width' => 55,
              'type' => 'string',
          ),
          'C' => array(
              'resultfield' => 'holderClass',
              'criteriafield' => 'holderClass',
              'label' => 'Class',
              'width' => 60,
              'type' => 'string',
          ),
          'D' => array(
              'resultfield' => 'holderCategory',
              'criteriafield' => 'holderCategory',
              'label' => 'Category',
              'width' => 50,
              'type' => 'string',
          ),
          'E' => array(
              'resultfield' => 'dsmNrpStatus',
              'criteriafield' => 'shortNRPStatus',
              'label' => 'Status',
              'width' => 40,
              'wildcardpadding'=>'NONE',
              'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getAllShortStatuses()),
          ),
          'F' => array(
              'resultfield' => 'renewDate',
              'criteriafield' => 'renewDate',
              'label' => 'Renewal Date',
              'width' =>60,
              'type' => 'datefilter',
          ),
      );
   }
   
   public function getSearchBase() {
		$criteria = new CRSDomainAppService_domainSearchCriteria();
		return $criteria;
	}
   
   
   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  array  $searchparams Parameter description (if any) ...
    * @return object Return description (if any) ...
    * @access public
    */
   public function arrayAsColModelSelectOptions($arr) {
      $s = ':(any);';
      $i = 0;
      $n = count($arr);
      foreach ($arr as $k => $v)
         $s .= $k . ':' . $v . (++$i < $n ? ';' : '');
      return $s;
   }

   public function getSearch($searchparams) {
      $criteria = parent::getSearch($searchparams);
      if (isset($criteria->accountId))
         $criteria->accountId = null;
      
      foreach ($searchparams as $key => $value) {
         if (is_object($value) && isset($value->field)) {
            $field = $this->columns[$value->field]['criteriafield'];
             Yii::log('SEARCH CRITERIA ITEM = ' . print_r($field, true));
            if ($field != null) {
               switch($field) {
                  case 'renewDate':
                      $dates = split(" ",$value->data);
                      $criteria->renewFrom = $dates[0];
                      $criteria->renewTo = $dates[1];
                      unset($criteria->$field);
                      break;
               }
            }
         }
      }
      Yii::log('SEARCH CITERIA= ' . print_r($criteria, true) . ' IN AllDomainsModel');
      return $criteria;
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  object $o Parameter description (if any) ...
    * @return mixed  Return description (if any) ...
    * @access public
    */
   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      $dsm = $o->dsmState->shortNrpStatus;
      return array
          (
          'A' => encode($o->name),
          'B' => cleanString(encode($o->holder)),
          'C' => encode($o->holderClass),
          'D' => encode($o->holderCategory),
          'E' => $dsm,
          'F' => parseXmlDate($o->renewDate),
      );
   }

}

