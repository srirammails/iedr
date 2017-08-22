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
class AccountFutureRenewalByMonthModel extends AccountAdvanceGridModel {
   
   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  unknown $obj Parameter description (if any) ...
    * @return void   
    * @access public 
    */
   
   public $duration = array();
   
   public function __construct($obj) {
      parent::__construct($obj);
      $this->date = date("Y-m", strtotime('1 months'));
      $this->duration = Utility::getPeriodsWithPrices('REN');
      $this->columns = array(
          'PK' => array(
              'label' => 'Domain',
              'width' => 60,
              'link' => 'domains/viewdomain',
              'resultfield' => 'domainName',
              'criteriafield' => 'domainName',
          ),
           'H' => array(
                 'label' => 'Periods',
                 'width' => 30,
             ),
          'C' => array(
              'label' => 'Holder',
              'resultfield' => 'holder',
          //    'criteriafield' => null,
              'width' => 50,
              'type' => null,
          ),
          'D' => array(
              'label' => 'Registration',
              'width' => 15,
              'resultfield' => 'registrationDate',
            //  'criteriafield' => null,
              'type' => null,
          ),
          'K' => array(
              'label' => 'Renewal',
              'resultfield' => 'renewDate',
            //  'criteriafield' => null,
              'width' => 15,
              'type' => null,
          ),
          'L' => array(
           'label' => 'Pending Reservations',
//            'resultfield' => 'pendingReservations',
           'width' => 25,
           'type' => null,
          ),
      );
   }

   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
      return array(
          'PK' => encode($o->name),
          'H' => '' . Utility::createSelect(encode($o->name), $this->duration),
          'C' => cleanString(encode($o->holder)),
          'D' => parseXmlDate($o->registrationDate),
          'K' => parseXmlDate($o->renewDate),
          'L' => $this->createLink($o),
      );
   }
   
   public function getSearchBase() {
      return new CRSDomainAppService_domainForRenewalCriteriaVO();
   }

   private function createLink($o) {   	
		if (Utility::isDirect()) {           	
	     	if ($o->pendingCCReservations || $o->pendingADPReservations) {
   	    		return CHtml::link("Yes", Yii::app()->createUrl("account_today_cc_reservations/menu"));
		   	}
		} else if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {           
                   	   $link = "";
                   	   if ($o->pendingCCReservations) {                   	
                       		$link = CHtml::link("CC", Yii::app()->createUrl("account_today_cc_reservations/menu"));
                   	   }
                   	   if ($o->pendingADPReservations) {
                   	   		$link = trim($link." ".CHtml::link("DEP", Yii::app()->createUrl("account_today_deposit_reservations/menu")));
                   	   }
                       if ($link != "") {
                       		return $link;
                       }
               
		}
   	      	   
   	   return "No";   	      	   
   }
}

