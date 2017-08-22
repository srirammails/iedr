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
class GridSelectionModel extends DynamicDomainListModel {

   /**
    * Description for public
    * @var    unknown
    * @access public 
    */
   public $command;
   public $returnurl;
   public $needCreditCard;
   public $period;
   public $renewalDateType;
   public $defaultPeriods = array();

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return void  
    * @access public
    */
   public function setupRules($setup = true) {
      if ($setup) {
         $this->needCreditCard = 0;
         $this->val_rules = array(
             array('domainlist,command,returnurl,renewalDateType,', 'required'),
             array('command', 'validateCommand'),
             array('renewalDateType', 'validateRenewalDateType'),
             array('needCreditCard', 'boolean'),
         );
         $this->attr_labels = array('domainlist' => 'Domains to be Processed',);
      }
   }

   public function __construct($isVoluntary = false) {
      parent::__construct();

      $this->needCreditCard = 0;
      $this->renewalDateType = 'CURRENT';
     // $this->attr_labels = array('domainlist' => 'Domains to be Processed',);

      $this->val_rules = array(
           array('domainlist,command,returnurl,renewalDateType,', 'required'),
           array('command', 'validateCommand'),
           array('renewalDateType', 'validateRenewalDateType'),
           array('needCreditCard', 'boolean'),
      );

      if ($isVoluntary == false) {
         $this->needCreditCard = 1;
         Yii::log('add mixin model file=' . __FILE__ . '  line=' . __LINE__);
         $this->addMixinModel(new CreditCardFormSegmentModel());
         /*
         * Just to make asterisk available
         */
         $this->val_rules = array_merge($this->val_rules, array(
             array('cardholder, creditcardno, cardtype, exp_date, exp_month, exp_year, cvn', 'required'),
         ));
      }
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  unknown $attrib Parameter description (if any) ...
    * @param  unknown $params Parameter description (if any) ...
    * @return string  Return description (if any) ...
    * @access public 
    */
   public function validateCommand($attrib, $params) {
      $v = ' is not a valid Command';
      switch ($this->$attrib) {
         case 'payonline': case 'off': case 'paydeposit': case 'msd': case 'mra': $v = null;
            break;
         default : break;
      }
      return $v;
   }

    public function validateRenewalDateType($attrib, $params) {
      $v = ' is not a valid Renewal Date Type';
      switch ($this->$attrib) {
         default : break;
      }
      return ' ';
   }
   
   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  unknown $attrib Parameter description (if any) ...
    * @param  unknown $params Parameter description (if any) ...
    * @return string  Return description (if any) ...
    * @access public 
    */
//   public function validateInvoiceAttribute($attrib, $params) {
//      $v = ' is not a valid Invoice Type';
//      switch ($this->$attrib) {
//         default : break;
//      }
//      return $v;
//   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  unknown $p Parameter description (if any) ...
    * @return void   
    * @access public 
    */
   public function setFromPOST($p) {
      parent::setFromPOST($p);
      if ($this->needCreditCard == 1) {
         $this->setScenario(CreditCardFormSegmentModel::$without_amount_scenario);
      }
   }

   public function __toString() {
      return 'GridSelectionModel';
   }

   public function isDebitCardAllowed() {return true;}
    
}

