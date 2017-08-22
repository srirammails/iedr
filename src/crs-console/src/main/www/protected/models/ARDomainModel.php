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

class ARDomainModel extends AllDomainsModel {

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return object Return description (if any) ...
    * @access public
    */
   
   public function __construct() {
        $this->columns = array(
          'A' => array(
              'resultfield' => 'name',
              'criteriafield' => 'domainName',
              'label' => 'Domain Name',
              'width' => 40,
              'link' => 'domains/viewdomain',
              'type' => 'string',
          ),
          'B' => array(
              'resultfield' => 'holder',
              'criteriafield' => 'domainHolder',
              'label' => 'Holder',
              'width' => 40,
              'type' => 'string',
          ),
          'C' => array(
              'resultfield' => 'holderClass',
              'criteriafield' => 'holderClass',
              'label' => 'Class',
              'width' => 50,
              'type' => 'string',
          ),
          'D' => array(
              'resultfield' => 'holderCategory',
              'criteriafield' => 'holderCategory',
              'label' => 'Category',
              'width' => 50,
              //'type' => 'string',
          ),
          'E' => array(
              'resultfield' => 'dsmRenewalMode',
              'criteriafield' => 'renewalModes',
              'label' => 'Autorenew Status',
              'width' => 24,
              'wildcardpadding'=>'NONE',
              'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getRenewalsMode()),
          ),
          'F' => array(
              'resultfield' => 'dsmNrpStatus',
              'criteriafield' => 'shortNRPStatus',
              'label' => 'Status',
              'width' => 24,
              'wildcardpadding'=>'NONE',
              'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getAllShortStatuses()),
          ),
          'G' => array(
              'resultfield' => 'renewDate',
              'criteriafield' => 'renewDate',
              'label' => 'Renewal Date',
              'width' => 60,
              'type' => 'datefilter',
          ),
      );
   }

   public function getSearchBase() {
      // override to set the filter
      $criteria = parent::getSearchBase();
      $criteria->billingStatus = array('A', 'R');
      if(isset($criteria->accountId)) {
         $criteria->accountId = null;
      }
      return $criteria;
   }

   public function getSearch($searchparams) {
      $criteria = parent::getSearch($searchparams);
      foreach ($searchparams as $key => $value) {
         if (is_object($value) && isset($value->field)) {
            $field = $this->columns[$value->field]['criteriafield'];
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
      Yii::log('SEARCH CRITERIA = ' . print_r($criteria, true).' IN ARDomainModel');
      return $criteria;
   }
   
   public function getSort($sortparams) {
      Yii::log('SORTPARAMS= '.print_r($sortparams,true));
      return parent::getSort($sortparams);
   }

   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array
          (
          'A' => encode($o->name),
          'B' => cleanString(encode($o->holder)),
          'C' => encode($o->holderClass),
          'D' => encode($o->holderCategory),
          'E' => $o->dsmState->renewalMode,
          'F' => $o->dsmState->shortNrpStatus,
          'G' => parseXmlDate($o->renewDate),
      );
   }

}

