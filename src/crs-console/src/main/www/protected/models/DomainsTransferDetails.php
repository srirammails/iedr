<?php

class DomainsTransferDetails extends DynamicModelBase {

   public $toCreated = true;
   public $domain_name;
   public $authcode;
   public $authcode_failures = 0;
   public $retries;
   public $accept_tnc;
   public $admin_contact_nic_1;
   public $admin_contact_nic_2;
   public $nameservers = array();
   public $nameserversCount;
   public $ipAddresses = array();
   public $maxRows;
   public $minRows;
   public $renewalPeriod;
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
   public $charityPaymentForced=false;
   public $exp_date = 'just label';    
   public $labels = array();   

   public $errors;
   public $message_displayed;

   public $standardRules = array(
   		array('admin_contact_nic_1,admin_contact_nic_2,tech_contact',
   				'NicHandleValidator'),
   		array('accept_tnc,admin_contact_nic_1,authcode,tech_contact',
   				'required'),
   		array('accept_tnc',
   				'CheckboxOnValidator', 'message'=>'You must confirm that you have read and agree to our terms and conditions'),
   		array('admin_contact_nic_1,admin_contact_nic_2,tech_contact',
   				'UpperCaseValidator'),
   		array('domain_name',
   				'LowerCaseValidator'),
   		array('nameservers', 'NameserverListComplexValidator'),
   );
   public $ccValidationRules;
   
   public function initCCValidationRules() {
   	$year = date('y', time());
   	$this->ccValidationRules = array(
   			array('cardholder, creditcardno, exp_month, exp_year, exp_date, cardtype, cvn', 'required'),
   			array('creditcardno', 'CreditCardValidator'),
   			array('exp_month', 'numerical', 'min'=>1, 'max'=>12,),
   			array('exp_year', 'numerical', 'min'=>$year, 'max'=>($year+15)),
   			array('exp_month,', 'numerical', 'integerOnly' => true),
   			array('exp_month,', 'numerical', 'integerOnly' => true),
   			array('exp_year,', 'numerical', 'integerOnly' => true),
   			array('cvn', 'numerical', 'numberPattern'=>'/^[0-9]{3}$/i', 'message'=>'{attribute} must be a three digit number'),
   	);
   }
  
   public function setupRules($what) {
      Yii::log("Setting up validation rules for ".print_r($what, true));
      Yii::log("Setting up validation rules for[domain_name] ".print_r($what['domain_name'], true));
      /* Payment */

      CRSDomainAppService_service::isCharity($this->charityPaymentForced, $this->errors, Yii::app()->user->authenticatedUser, $what['domain_name']);

      Yii::log("Setting up validation rules, charity = ".$this->charityPaymentForced);
      $this->val_rules = $this->standardRules;

      if (!$this->charityPaymentForced) {
         $this->val_rules[] = array('renewalPeriod, paymentType', 'required');
      }

      $this->val_rules[] = array('charityPaymentForced', 'safe');
      $this->val_rules[] = array('paymentType', 'safe');

       if (array_key_exists('paymentType', $what)) {
   			switch ($what['paymentType']) {
   				case 'CC':
   					$this->val_rules = array_merge($this->val_rules, $this->ccValidationRules);

   					$this->cardtype = $what['cardtype'];
   					$this->cardholder = $what['cardholder'];
   					$this->creditcardno = $what['creditcardno'];
   					$this->exp_month = $what['exp_month'];
   					$this->exp_year = $what['exp_year'];
   					$this->cvn = $what['cvn'];
   					$this->euros_amount = $what['euros_amount'];
   					break;
   			}
   		}
   		$this->rules = $this->val_rules;
   }

   public function __construct($transferdata=null) {
      parent::__construct();
      $this->setDefaults($transferdata);

      if (isset($this->domain_name)) {
        CRSDomainAppService_service::isCharity($this->charityPaymentForced, $this->errors, Yii::app()->user->authenticatedUser, $this->domain_name);
      }

      $this->val_rules = $this->standardRules;
      $this->val_rules[] = array('paymentType', 'safe');
      $this->val_rules[] = array('charityPaymentForced', 'safe');

      if (!$this->charityPaymentForced) {
        $this->val_rules[] = array('renewalPeriod, paymentType', 'required');
        $this->initCCValidationRules();	
        $this->val_rules = array_merge($this->val_rules, $this->ccValidationRules);
      } else {
      }

      $this->rules = $this->val_rules;

      $this->labels = array(
          'domain_name' => 'Domain which is valid and available',
          'accept_tnc' => 'I accept the <a href="https://www.iedr.ie/registrations-terms-and-conditions/" target="_blank">terms and conditions</a>',
          'admin_contact_nic_1' => 'Admin Contact 1',
          'admin_contact_nic_2' => 'Admin Contact 2',
          'renewalPeriod' => 'Renewal Period',
           'remarks' => 'Remarks',
          'tech_contact' => 'Technical Contact',
          'charitycode' => 'Charity Code',
          'paymentType' => 'Payment Type',
      		
      		'cardtype'   => 'Card Type',
      		'creditcardno'   => 'Card Number',
      		'cardholder'         => 'Card Holder',
      		'exp_date'       => 'Card Expiry Month / Year',
      		'exp_year'       => 'Card Expiration Year',
      		'exp_month'      => 'Card Expiration Month',
      		'cvn'            => 'Card Verification Number',
      );
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

   public function setDefaults($transferdata) {
      if (isset($transferdata['domain'])) {
         $this->domain_name = $transferdata['domain'];
      }

      CRSCommonAppService_service::getDomainSettings($result, $errs, Yii::app()->user->authenticatedUser);
      $this->maxRows = $result->maxCount;
      $this->minRows = $result->minCount;
      $this->retries = $result->failureLimit;

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
         Yii::log('No Resseler Defaults found', 'info', 'Domains_Creation_Details::setDefaults()');
      }
   }

   public function rules() {
      return $this->rules;
   }

   public function attributeLabels() {
      return $this->labels;
   }   
}

