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
class Domains_Creation_Details extends DynamicModelBase {

   public $toCreated = true;
   public $domainlist;
   public $accept_tnc;
   public $admin_contact_nic_1;
   public $admin_contact_nic_2;
   public $applicant;
   public $holder;
   public $nameservers = array();
   public $ipAddresses = array();
   public $nameserversCount;
   public $maxRows;
   public $minRows;
   public $registration_period;
   public $renewalPeriod;
   public $remarks;
   public $tech_contact;
   public $charitycode;
   public $paymentType;
   public $cardtype;
   public $rules;
   public $period;
   public $periodType;
   public $cardholder;
   public $creditcardno;
   public $exp_month;
   public $exp_year;
   public $cvn;
   public $euros_amount;
   public $labels = array();
   public $exp_date = 'just label';

   public $standardRules = array(
       array('holder,remarks,applicant,registration_period','required'),
       array('admin_contact_nic_1,admin_contact_nic_2,tech_contact',
           'NicHandleValidator'),
       array('paymentType,accept_tnc,admin_contact_nic_1,domainlist,tech_contact',
           'required'),
       array('accept_tnc',
           'CheckboxOnValidator', 'message'=>'You must confirm that you have read and agree to our terms and conditions'),
       array('admin_contact_nic_1,admin_contact_nic_2,tech_contact',
           'UpperCaseValidator'),
       array('domainlist', 'LowerCaseValidator'),
       array('nameservers', 'NameserverListComplexValidator'),
   );
   public $ccValidationRules;

   public function initCCValidationRules() {
       $year = date('y', time());
       $this->ccValidationRules = array(
           array('cardholder, creditcardno, exp_month, exp_year, cardtype, cvn, exp_date', 'required'),
           array('creditcardno', 'CreditCardValidator'),
           array('exp_month', 'numerical', 'min'=>1, 'max'=>12,),
           array('exp_year', 'numerical', 'min'=>$year, 'max'=>($year+15)),
           array('exp_month,', 'numerical', 'integerOnly' => true),
           array('exp_month,', 'numerical', 'integerOnly' => true),
           array('exp_year,', 'numerical', 'integerOnly' => true),
           array('cvn', 'numerical', 'numberPattern'=>'/^[0-9]{3}$/i', 'message'=>'{attribute} must be a three digit number'),
       );
   }
   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  unknown $domlist Parameter description (if any) ...
    * @return void   
    * @access public 
    */
   public function setupRules($what) {
      /* Payment */
      switch ($what['paymentType']) {
         case 'CC':
            $this->val_rules = array_merge($this->standardRules, $this->ccValidationRules);

            $this->cardtype = $what['cardtype'];
            $this->cardholder = $what['cardholder'];
            $this->creditcardno = $what['creditcardno'];
            $this->exp_month = $what['exp_month'];
            $this->exp_year = $what['exp_year'];
            $this->cvn = $what['cvn'];
            $this->euros_amount = $what['euros_amount'];
            break;
         case 'CH':
            Yii::log('set CH');
            $this->rules = array_merge($this->standardRules, array(
                array('charitycode', 'required'),
                array('charitycode', 'numerical', 'integerOnly' => true,),
                    ));

            $this->labels = array_merge($this->labels, array(
                array('charitycode', 'Charity'),
                    ));

            $this->charitycode = $what['charitycode'];
            break;
          default:
              $this->rules = $this->standardRules;
      }

      $this->val_rules = $this->rules;
   }

   public function __construct($domlist=null) {
      parent::__construct();
      CRSCommonAppService_service::getDomainSettings($result, $errs, Yii::app()->user->authenticatedUser);
      $this->maxRows = $result->maxCount;
      $this->minRows = $result->minCount;

      $this->initCCValidationRules();

      $this->val_rules = array_merge($this->standardRules, $this->ccValidationRules);

      $this->rules = $this->val_rules;

      $this->labels = array(
          'domainlist' => 'Domains which are valid and available',
          'accept_tnc' => 'I accept the <a href="https://www.iedr.ie/registrations-terms-and-conditions/" target="_blank">terms and conditions</a>',
          'admin_contact_nic_1' => 'Admin Contact 1',
          'admin_contact_nic_2' => 'Admin Contact 2',
          'applicant' => 'Class/Category of Applicant',
          'holder' => 'Domain Holder',
          'registration_period' => 'Registration period',
          'renewalPeriod' => 'Renewal Period',
          'remarks' => 'Remarks',
          'tech_contact' => 'Technical Contact',
          'charitycode' => 'Charity Code',
          'paymentType' => 'Payment Type',

          'cardtype'   => 'Card Type',
          'creditcardno'   => 'Card Number',
          'cardholder'         => 'Card Holder',
          'exp_date'       => 'Card Expiration Month / Year',
          'exp_year'       => 'Card Expiration Year',
          'exp_month'      => 'Card Expiration Month',
          'cvn'            => 'Card Verification Number',
      );

      $this->setDefaults($domlist);
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  unknown $domlist Parameter description (if any) ...
    * @return void   integerOnly
    * @access public 
    */
   public function setDefaults($domlist) {
      $this->domainlist = $domlist;
      $this->nameserversCount = $this->minRows;
      try {
         $rd = Yii::app()->user->resellerDefaults;
         $this->tech_contact = $rd->techContactId;
         if (is_array($rd->nameservers)) {
            $nameserversArray = $rd->nameservers;
         } else {
            $nameserversArray = array($rd->nameservers);
         }
         $this->nameservers = $nameserversArray;
         $this->nameserversCount = max(count($nameserversArray), $this->minRows);
      } catch (Exception $e) {
         Yii::log(print_r($e, true), 'error', 'Domains_Creation_Details::setDefaults()');
      }
   }

    public function setFromPOST($p) {
        parent::setFromPOST($p);
        foreach ($p['nameservers'] as $i => $value) {
            $this->nameservers[$i] = $value;
        }
        foreach ($p['ipAddresses'] as $i => $value) {
            $this->ipAddresses[$i] = $value;
        }
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
      return $this->rules;
   }

   public function attributeLabels() {
      return $this->labels;
   }

   public function isDebitCardAllowed() {return false;}
}

