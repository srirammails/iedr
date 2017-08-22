
<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
?>

<?php

/**
 * defines ViewDomainModel class
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */


/**
 * Model class for viewing a Domain
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       CRSDomainAppService_extendedDomainInfoVO
 */
class ViewDomainToTransferModel
    extends DynamicModelBase
    {
    /**
     * is used to store backend error message or exception string, for a view to render
     *
     * @var    string
     * @access public
     */
    public $errors;

    /**
     * domain name
     *
     * @var    unknown
     * @access public 
     */
    public $domain;

    /**
     * is used to messages about backend operations, for a view to render
     *
     * @var    array 
     * @access public
     */
    public $message = array();

    /**
     * Company name
     *
     * @var    string
     * @access public 
     */
    
    public $domain_adminContacts;
    public $domain_adminContacts_companyName;
    public $domain_adminContacts_0_companyName;
    public $domain_adminContacts_1_companyName;
    

    /**
     * Admin-Contact Country
     *
     * @var    string
     * @access public 
     */
    public $domain_adminContacts_0_country;
    public $domain_adminContacts_1_country;

    /**
     * Admin-Contact Email
     *
     * @var    string
     * @access public 
     */
    public $domain_adminContacts_0_email;
    public $domain_adminContacts_1_email;

    /**
     * Admin-Contact Name
     *
     * @var    string
     * @access public 
     */
    
    public $domain_adminContacts_0_name;
    public $domain_adminContacts_1_name;

    /**
     * Admin-Contact Nic Handle
     *
     * @var    string
     * @access public 
     */
    
    public $domain_adminContacts_0_nicHandle;
    public $domain_adminContacts_1_nicHandle;

    /**
     * Billing-Contact Company Name
     *
     * @var    string
     * @access public 
     */
    public $domain_billingContacts_companyName;

    /**
     * Billing-Contact Country
     *
     * @var    string
     * @access public 
     */
    public $domain_billingContacts_country;

    /**
     * Billing-Contact Email
     *
     * @var    string
     * @access public 
     */
    public $domain_billingContacts_email;

    /**
     * Billing-Contact Name
     *
     * @var    string
     * @access public 
     */
    public $domain_billingContacts_name;

    /**
     * Billing-Contact Nic Handle
     *
     * @var    string
     * @access public 
     */
    public $domain_billingContacts_nicHandle;

    /**
     * Last Change Date
     *
     * @var    string
     * @access public 
     */
    public $domain_changeDate;

    /**
     * ? unused
     *
     * @var    boolean
     * @access public 
     */
    public $domain_clikPaid;

    /**
     * Creator-Contact Comany-Name
     *
     * @var    string
     * @access public 
     */
    public $domain_creator_companyName;

    /**
     * Creator-Contact Country
     *
     * @var    string
     * @access public 
     */
    public $domain_creator_country;

    /**
     * Creator-Contact Email
     *
     * @var    string
     * @access public 
     */
    public $domain_creator_email;

    /**
     * Creator-Contact Name
     *
     * @var    string
     * @access public 
     */
    public $domain_creator_name;

    /**
     * Creator-Contact Nic Handle
     *
     * @var    string
     * @access public 
     */
    public $domain_creator_nicHandle;

    /**
     * ? unused
     *
     * @var    integer
     * @access public 
     */
    public $domain_dateRoll;

    /**
     * Domain Status Date
     *
     * @var    string
     * @access public 
     */
    public $domain_domainStatusDate;

    /**
     * Domain Holder Name
     *
     * @var    string
     * @access public 
     */
    public $domain_holder;

    /**
     * Registrant Category
     *
     * @var    string
     * @access public 
     */
    public $domain_holderCategory;

    /**
     * Registrant Class
     *
     * @var    string
     * @access public 
     */
    public $domain_holderClass;

    /**
     * Locked Status
     *
     * @var    boolean
     * @access public 
     */
    public $domain_lockedStatus;

    /**
     * Domain Name
     *
     * @var    string
     * @access public
     */
    public $domain_name;

    /**
     * Nameservers
     *
     * @var    array
     * @access public 
     */
    public $domain_nameservers;

    /**
     * Nameserver 0 IP Address
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_0_ipAddress;

    /**
     * Nameserver 0 Hostname
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_0_name;

    /**
     * Nameserver 1 IP Address
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_1_ipAddress;

    /**
     * Nameserver 1 Hostname
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_1_name;

    /**
     * Nameserver 2 IP Address
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_2_ipAddress;

    /**
     * Nameserver 2 Hostname
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_2_name;

    /**
     * Nameserver 3 IP Address
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_3_ipAddress;

    /**
     * Nameserver 3 Hostname
     *
     * @var    string
     * @access public 
     */
    public $domain_nameservers_3_name;

    /**
     * Domain registration Date
     *
     * @var    string
     * @access public 
     */
    public $domain_registrationDate;

    /**
     * Remark
     *
     * @var    string
     * @access public 
     */
    public $domain_remark;

    /**
     * Renewal Date
     *
     * @var    string
     * @access public 
     */
    public $domain_renewDate;

    /**
     * Reseller Account Address
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_address;

    /**
     * Reseller Account Has-Agreement-Signed
     *
     * @var    boolean
     * @access public 
     */
    public $domain_resellerAccount_agreementSigned;

    /**
     * Reseller Account Billing Contact (Nic Handle)
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_billingContact;

    /**
     * Reseller Account Last-Changed-Date
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_changeDate;

    /**
     * Reseller Account Country
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_country;

    /**
     * Reseller Account County
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_county;

    /**
     * Reseller Account Creation Date
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_creationDate;

    /**
     * Reseller Account Fax Number
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_fax;

    /**
     * Reseller Account ID
     *
     * @var    integer
     * @access public 
     */
    public $domain_resellerAccount_id;

    /**
     * Reseller Account Invoice Frequency ('Monthly')
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_invoiceFreq;

    /**
     * Reseller Account Name
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_name;

    /**
     * Reseller Account Next Invoice Month
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_nextInvMonth;

    /**
     * Reseller Account Phone Number
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_phone;

    /**
     * Reseller Account Remark
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_remark;

    /**
     * Reseller Account Status (Active/Suspended/Deleted)
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_status;

    /**
     * Reseller Account Status Change Date
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_statusChangeDate;

    /**
     * Reseller Account Tarriff (e.g. 'TradeAChar')
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_tariff;

    /**
     * Reseller Account Can-Edit-Tickets
     *
     * @var    boolean
     * @access public 
     */
    public $domain_resellerAccount_ticketEdit;

    /**
     * Reseller Account web address
     *
     * @var    string
     * @access public 
     */
    public $domain_resellerAccount_webAddress;

    /**
     * Reseller Account Company Name
     *
     * @var    string
     * @access public 
     */
    public $domain_techContacts_companyName;

    /**
     * Tech-Contact Country
     *
     * @var    string
     * @access public 
     */
    public $domain_techContacts_country;

    /**
     * Tech-Contact Email
     *
     * @var    string
     * @access public 
     */
    public $domain_techContacts_email;

    /**
     * Tech-Contact Name
     *
     * @var    string
     * @access public 
     */
    public $domain_techContacts_name;

    /**
     * Tech-Contact Nic-Handle
     *
     * @var    string
     * @access public 
     */
    public $domain_techContacts_nicHandle;

    /**
     * has Pending Tickets
     *
     * @var    boolean
     * @access public 
     */
    public $tickets;


    /**
     * has (recent) documents
     *
     * type=xs:boolean, (NOT NULL), min=1
     *
     * @var    boolean
     * @access public 
     */
    public $documents;


    /**
     * related domain names, not used
     *
     * @var    unknown
     * @access public 
     */
    public $relatedDomainNames;// type=xs:string, (null), min=0, max=unbounded


    /**
     * Pending Domain Names
     *
     * @var    unknown
     * @access public 
     */
    public $pendingDomainNames;// type=xs:string, (null), min=0, max=unbounded

    /**
     * DSM State
     *
     * @access public
     */
    public $domain_dsmState_customerType;
    public $domain_dsmState_nrpStatus;
    public $domain_dsmState_renewalMode;
    public $domain_dsmState_holderType;
    public $domain_dsmState_wipo;
    public $domain_billingStatus;
    public $domain_dsmState_voluntaryNRP = 0;
    public $domain_dsmState_removeFromVoluntaryNRPPossible = 0;
    public $domain_dsmState_enterVoluntaryNRPPossible = 0;
    public $list;
    public $period;
    public $periodType;
    public $charitycode;
    public $paymentType;
    
    public $rules;
    
    public $domain_suspensionDate;
    public $domain_deletionDate;
    /**
     * Constructor, initialises domain name
     * 
     * @param  string $name Domain Name
     * @return void   
     * @access public 
     */
    public function __construct($name=null)
	{
      parent::__construct();
      $this->domain_name = $name;
      $this->val_rules = array();
      $this->rules = $this->val_rules;
      $this->addMixinModel(new CreditCardFormSegmentModel());
	}

    /**
     * copies attributes from an object instance into this model
     * 
     * @param  CRSDomainAppService_extendedDomainInfoVO  $obj  CRSDomainAppService_extendedDomainInfoVO instance to copy data from
     * @param  string $errs error string from domain retrieval, copied into model for view to display
     * @return void   
     * @access public 
     */
   public function fillFromObject($obj,$errs)
	{
	$flds = array( 'tickets', 'documents', 'relatedDomainNames', 'pendingDomainNames', );
   
	foreach($flds as $f)
		if(isset($this->$f) and isset($obj->$f))
			$this->$f = $obj->$f;
	if(isset($obj->domain) and $obj->domain!=null)
		{
		$d = $obj->domain;
      
		addToObjectAsProperties($this, 'domain', $d);
      
      if(isset($d->adminContacts) && is_object($d->adminContacts)){
         $this->domain_adminContacts_0_companyName = $d->adminContacts->companyName;
         $this->domain_adminContacts_0_country = $d->adminContacts->country;
         $this->domain_adminContacts_0_name = $d->adminContacts->name;
         $this->domain_adminContacts_0_email = $d->adminContacts->email;
         $this->domain_adminContacts_0_nicHandle = $d->adminContacts->nicHandle;
      } else if(isset($d->adminContacts) && is_array($d->adminContacts)) {
         Yii::log('IS_ARRAY ADMIN CONTACTS!= '.print_r($d->adminContacts,true));
         $this->domain_adminContacts_0_companyName = $d->adminContacts[0]->companyName;
         $this->domain_adminContacts_0_country = $d->adminContacts[0]->country;
         $this->domain_adminContacts_0_name = $d->adminContacts[0]->name;
         $this->domain_adminContacts_0_email = $d->adminContacts[0]->email;
         $this->domain_adminContacts_0_nicHandle = $d->adminContacts[0]->nicHandle;
         
         $this->domain_adminContacts_1_companyName = $d->adminContacts[1]->companyName;
         $this->domain_adminContacts_1_country = $d->adminContacts[1]->country;
         $this->domain_adminContacts_1_name = $d->adminContacts[1]->name;
         $this->domain_adminContacts_1_email = $d->adminContacts[1]->email;
         $this->domain_adminContacts_1_nicHandle = $d->adminContacts[1]->nicHandle;
         
      }
      
      if(property_exists($d, 'period') && property_exists($d->period, 'period')) {
         $this->period = $d->period;
      }
      
      if( property_exists($d, 'dsmState') && property_exists($d->dsmState, 'shortNrpStatus')) {
         $this->domain_dsmState_nrpStatus = $d->dsmState->shortNrpStatus;
      }
      
      if(property_exists($d,'dsmState') && property_exists($d->dsmState,'removeFromVoluntaryNRPPossible')) {
         $this->domain_dsmState_removeFromVoluntaryNRPPossible = $d->dsmState->removeFromVoluntaryNRPPossible;
      }
      
      if (property_exists($d, 'dsmState') && property_exists($d->dsmState, 'enterVoluntaryNRPPossible')) {
      	$this->domain_dsmState_enterVoluntaryNRPPossible = $d->dsmState->enterVoluntaryNRPPossible;
      }
      
      if(property_exists($d,'changeDate')) {
         $this->domain_changeDate	= parseXmlDate($d->changeDate);
      }
      
      if(property_exists($d,'domainStatusDate')) {
         $this->domain_domainStatusDate	= parseXmlDate($d->domainStatusDate);
      }
      
      if(property_exists($d,'registrationDate')) {
         $this->domain_registrationDate	= parseXmlDate($d->registrationDate);
      }
      
      if(property_exists($d,'renewDate')) {
         $this->domain_renewDate		= parseXmlDate($d->renewDate);
      }
      
      if(property_exists($d,'suspensionDate')) {
         $this->domain_suspensionDate		= parseXmlDate($d->suspensionDate);
      }
      if(property_exists($d,'deletionDate')) {
         $this->domain_deletionDate		= parseXmlDate($d->deletionDate);
      }
     
		if(isset($d->resellerAccount))
      {
         $r = $d->resellerAccount;
         if(isset($r->changeDate))	$this->domain_resellerAccount_changeDate	= parseXmlDate($r->changeDate);
         if(isset($r->creationDate))	$this->domain_resellerAccount_creationDate	= parseXmlDate($r->creationDate);
         if(isset($r->statusChangeDate))	$this->domain_resellerAccount_statusChangeDate	= parseXmlDate($r->statusChangeDate);
      }
		if(isset($d->nameservers))
			$this->domain_nameservers = count($d->nameservers);
      
      if(isset($d->adminContacts)) {
         if(is_object($d->adminContacts)) {
            $this->domain_adminContacts = 1;
         } else if(is_array($d->adminContacts)) {
            $this->domain_adminContacts = count($d->adminContacts);
         }
      }
      
		}
      
      
      Yii::log('fill dsm is null');
	$this->errors = $errs; 
	Yii::log($this->domain_name.' -> '.$this->errors, 'debug', 'ViewDomainModel::fillFromObject()');
   Yii::log($this->domain_dsmState_nrpStatus.' -> '.$this->errors, 'debug', 'ViewDomainModel::fillFromObject()');
	}

    /**
     * Yii Form validation Rules
     * 
     * @return array  validation rules
     * @access public
     */
    public function rules()
      {
   //       $this->rules = array(
   //               array(
   //                     'domain_name', 
   //                     'IEDomainValidator'
   //                    ),
   //               array(
   //                     'domain_name, domain_adminContacts_0_nicHandle, domain_adminContacts_1_nicHandle, domain_billingContacts_nicHandle,domain_creator_nicHandle,domain_techContacts_nicHandle]',
   //                     'required'
   //                   ),
   //               array(
   //                     'domain_adminContacts_1_nicHandle,domain_adminContacts_0_nicHandle, domain_billingContacts_nicHandle,domain_creator_nicHandle,domain_techContacts_nicHandle]',
   //                     'NicHandleValidator'
   //                   ),
   //               );
          //$this->val_rules = array_merge($this->rules,$this->val_rules);
          return $this->rules;
      }

    /**
     * Yii Form Display Labels
     * 
     * @return array  display labels
     * @access public
     */
   
   public function setupRules($what) {
      switch($what) {
         case 'CC':
          $this->rules = array_merge($this->rules, array(
             array('cardholder,creditcardno,exp_month,exp_year,cvn,cardtype','required'),
             array('exp_month,exp_year','numerical','integerOnly'=>true),
             array('cvn', 'numerical', 'numberPattern'=>'/^[0-9]{3}$/i', 'message'=>'{attribute} must be a three digit number'),
             array('cardtype', 'length','min'=>2),
             //array('cardholder', 'numerical','integerOnly'=>true,),
          ));
            break;
         
         case 'CH':
            $this->rules = array_merge($this->rules, array (
                    array('charitycode','required'),
                    array('charitycode','numerical','integerOnly'=>true,),
            ));
            
            break;
      }
      $this->val_rules = $this->rules;
   }
   
   
    public function attributeLabels()
	{
      $this->labels = array(
		'tickets'					=> 'Has Tickets',
		'documents'					=> 'Has Documents',
		'relatedDomainNames'				=> 'Related Domains',
		'pendingDomainNames'				=> 'Pending Domains',
		'domain_adminContacts_0_companyName'		=> 'Admin Contact companyName',
		'domain_adminContacts_0_country'			=> 'Admin Contact country',
		'domain_adminContacts_0_email'			=> 'Admin Contact email',
		'domain_adminContacts_0_name'			=> 'Admin Contact name',
		'domain_adminContacts_0_nicHandle'		=> 'Admin Contact nicHandle',
		'domain_adminContacts_1_companyName'		=> 'Admin Contact companyName',
		'domain_adminContacts_1_country'			=> 'Admin Contact country',
		'domain_adminContacts_1_email'			=> 'Admin Contact email',
		'domain_adminContacts_1_name'			=> 'Admin Contact name',
		'domain_adminContacts_1_nicHandle'		=> 'Admin Contact nicHandle',
		'domain_billingContacts_companyName'		=> 'Billing Contact companyName',
		'domain_billingContacts_country'		=> 'Billing Contact country',
		'domain_billingContacts_email'			=> 'Billing Contact email',
		'domain_billingContacts_name'			=> 'Billing Contact name',
		'domain_billingContacts_nicHandle'		=> 'Billing Contact nicHandle',
		'domain_changeDate'				=> 'Change Date',
		'domain_clikPaid'				=> 'ClikPaid',
		'domain_creator_companyName'			=> 'Creator companyName',
		'domain_creator_country'			=> 'Creator country',
		'domain_creator_email'				=> 'Creator email',
		'domain_creator_name'				=> 'Creator name',
		'domain_creator_nicHandle'			=> 'Creator nicHandle',
		'domain_dateRoll'				=> 'Renewal Rollover',
		'domain_domainStatusDate'			=> 'Domain Status Date',
		'domain_holder'					=> 'Holder',
		'domain_holderCategory'				=> 'Holder Category',
		'domain_holderClass'				=> 'Holder Class',
		'domain_lockedStatus'				=> 'Locked Status',
		'domain_name'					=> 'Domain Name',
		'domain_nameservers_0_ipAddress'		=> 'Nameserver 1 IP Address',
		'domain_nameservers_0_name'			=> 'Nameserver 1 Name',
		'domain_nameservers_1_ipAddress'		=> 'Nameserver 2 IP Address',
		'domain_nameservers_1_name'			=> 'Nameserver 2 Name',
		'domain_nameservers_2_ipAddress'		=> 'Nameserver 3 IP Address',
		'domain_nameservers_2_name'			=> 'Nameserver 3 Name',
		'domain_nameservers_3_ipAddress'		=> 'Nameserver 4 IP Address',
		'domain_nameservers_3_name'			=> 'Nameserver 4 Name',
		'domain_registrationDate'			=> 'Registration Date',
		'domain_remark'					=> 'Remark',
		'domain_renewDate'				=> 'Renewal Date',
		'domain_resellerAccount_address'		=> 'Reseller Account Address',
		'domain_resellerAccount_agreementSigned'	=> 'Reseller Account agreement Signed',
		'domain_resellerAccount_billingContact'		=> 'Reseller Account Billing Contact',
		'domain_resellerAccount_changeDate'		=> 'Reseller Account Change Date',
		'domain_resellerAccount_country'		=> 'Reseller Account Country',
		'domain_resellerAccount_county'			=> 'Reseller Account County',
		'domain_resellerAccount_creationDate'		=> 'Reseller Account Creation Date',
		'domain_resellerAccount_fax'			=> 'Reseller Account Fax',
		'domain_resellerAccount_id'			=> 'Reseller Account ID',
		'domain_resellerAccount_invoiceFreq'		=> 'Reseller Account Invoice Frequency',
		'domain_resellerAccount_name'			=> 'Reseller Account Name',
		'domain_resellerAccount_nextInvMonth'		=> 'Reseller Account Next Inv Month',
		'domain_resellerAccount_phone'			=> 'Reseller Account Phone',
		'domain_resellerAccount_remark'			=> 'Reseller Account Remark',
		'domain_resellerAccount_status'			=> 'Reseller Account Status',
		'domain_resellerAccount_statusChangeDate'	=> 'Reseller Account Status Change Date',
		'domain_resellerAccount_tariff'			=> 'Reseller Account Tariff',
		'domain_resellerAccount_ticketEdit'		=> 'Reseller Account Ticket Edit',
		'domain_resellerAccount_webAddress'		=> 'Reseller Account Web Address',
		'domain_techContacts_companyName'		=> 'Tech Contact companyName',
		'domain_techContacts_country'			=> 'Tech Contact country',
		'domain_techContacts_email'			=> 'Tech Contact email',
		'domain_techContacts_name'			=> 'Tech Contact name',
		'domain_techContacts_nicHandle'			=> 'Tech Contact nicHandle',
		'domain_dsmState_customerType'			=> 'Customer type',
		'domain_dsmState_nrpStatus'			=> 'NRP status',
		'domain_dsmState_renewalMode'			=> 'Renewal mode',
		'domain_dsmState_holderType'			=> 'Holder type',
      'domain_dsmState_wipo' => 'Wipo'
		);
      return $this->labels;
	}
    }


?>