<?php

/**
 * defines ViewTicketModel class
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
 * Model of Ticket data, for rendering ticket-view page; based on {@link CRSTicketAppService_ticketVO}
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class ViewTicketModel
    extends CFormModel
{

    /**
     * ticket ID; type=xs:long, (NOT NULL), min=1
     *
     * @var    long
     * @access public
     */
    var $id;


    /**
     * action string ; unused
     *
     * @var    string
     * @access public
     */
    var $action;

    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $type;


    /**
     * field for CRSTicketAppService_ticketVO; type=tns:failureReasonsVO, (NOT NULL), min=0
     *
     * @var    failureReasonsVO
     * @access public
     */

    var $charityCode;

    var $period;

    var $paymentType;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $domainHolder;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $domainHolder_failureReason;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $domainHolderClass;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $domainHolderClass_failureReason;

    /**
     * field for CRSTicketAppService_ticketVO; type=tns:simpleDomainFieldChangeVO, (NOT NULL), min=0
     *
     * @var    simpleDomainFieldChangeVO
     * @access public
     */
    var $domainHolderCategory;


    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $domainHolderCategory_failureReason;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $resellerAccount;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $resellerAccount_failureReason;

    /**
     * field for CRSTicketAppService_ticketVO; xs:string
     *
     * @var    string
     * @access public
     */
    var $adminContact_0;

    /**
     * field for CRSTicketAppService_ticketVO; xs:string
     *
     * @var    string
     * @access public
     */
    var $adminContact_0_failureReason;


    /**
     * field for CRSTicketAppService_ticketVO; xs:string
     *
     * @var    string
     * @access public
     */
    var $adminContact_1;


    /**
     * field for CRSTicketAppService_ticketVO; xs:string
     *
     * @var    string
     * @access public
     */
    var $adminContact_1_failureReason;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $techContact;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $techContact_failureReason;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $billingContact;

    /**
     * field for CRSTicketAppService_ticketVO
     *
     * @var    string
     * @access public
     */
    var $billingContact_failureReason;

    /**
     * array of nameservers
     *
     * @var    array
     * @access public
     */
    var $nameservers;

    /**
     * array of IP addresses of the nameservers
     *
     * @var    array
     * @access public
     */
    var $ipAddresses;

    /**
     * number of nameservers
     *
     * @var    integer
     * @access public
     */
    var $nameserversCount;

    var $maxRows;
    var $minRows;

    /**
     * field for CRSTicketAppService_ticketVO; type=tns:adminStatusEnum, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $adminStatus;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:dateTime, (NOT NULL), min=0
     *
     * @var    dateTime
     * @access public
     */
    var $adminStatusChangeDate;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $techStatus;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:dateTime, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $techStatusChangeDate;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $requestersRemark;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $hostmastersRemark;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $creator;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:dateTime, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $creationDate;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:dateTime, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $changeDate;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $checkedOutTo;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:boolean, (NOT NULL), min=1
     *
     * @var    boolean
     * @access public
     */
    var $clikPaid;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:boolean, (NOT NULL), min=1
     *
     * @var    boolean
     * @access public
     */
    var $hasDocuments;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $domainName;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $ticketHolder;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:string, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $previousHolder;


    /**
     * field for CRSTicketAppService_ticketVO; type=xs:dateTime, (NOT NULL), min=0
     *
     * @var    string
     * @access public
     */
    var $domainRenevalDate;


    /**
     * computed ticket expiry date (27 days, set in config/main.php)
     *
     * @var    string
     * @access public
     */
    var $expiryDate;


    /**
     * is used to store backend error message or exception string, for a view to render
     *
     * @var    string
     * @access public
     */
    var $errors;


    /**
     * is used to store the can-user-edit-tickets boolean setting; is set by the controller (but SHOULD be in this object's constructor)
     *
     * @todo   initialise this variable in the constructor to Yii::app()->user->ticketEdit
     * @var    boolean
     * @access public
     */
    var $ticketEdit;

    var $financialStatus;
    var $financialChangeDate;

    /**
     * constructor, sets ticket ID
     *
     * @param  integer $id ticket ID
     * @return void
     * @access public
     */
    public function __construct($id = null)
    {
        $this->id = $id;
        $this->ticketEdit = Yii::app()->user->ticketEdit;
        $this->action = 'view';

        CRSCommonAppService_service::getDomainSettings($result, $errs, Yii::app()->user->authenticatedUser);
        $this->maxRows = $result->maxCount;
        $this->minRows = $result->minCount;
    }

    /**
     * runs before validation is performed; trims leading, trailing and repeating newlines from 'remark' textarea-fields data
     *
     * @param  CModelEvent $aCModelEvent event; ignored
     * @return void
     * @access public
     */
    public function onBeforeValidate($aCModelEvent)
    {
        parent::onBeforeValidate($aCModelEvent);
        $this->requestersRemark = FormModelBase::trimNewlines($this->requestersRemark);
        $this->hostmastersRemark = FormModelBase::trimNewlines($this->hostmastersRemark);
    }

    /**
     * fills this model's attributes with corresponding data from the supplied CRSTicketAppService_ticketVO instance
     *
     * @param  CRSTicketAppService_ticketVO  $obj  CRS-WS-API ticket object
     * @param  string $errs error string from back-end ticket object retrieval
     * @return void
     * @access public
     */
    public function fillFromObject($obj, $errs)
    {
        // fill from WSAPI-returned value
        $flds = array('id', 'checkedOutTo', 'creator', 'domainName', 'hasDocuments',
            'hostmastersRemark', 'previousHolder', 'requestersRemark', 'ticketHolder', 'type', 'charityCode');
        foreach ($flds as $f)
            if (isset($obj->$f))
                $this->$f = $obj->$f;

        if (isset($obj->adminStatus)) $this->adminStatus = $obj->adminStatus;
        if (isset($obj->adminStatusChangeDate)) $this->adminStatusChangeDate = parseXmlDate($obj->adminStatusChangeDate);
        if (isset($obj->changeDate)) $this->changeDate = parseXmlDate($obj->changeDate);
        if (isset($obj->clikPaid)) $this->clikPaid = $obj->clikPaid == 'Y' ? true : false;
        if (isset($obj->creationDate)) $this->creationDate = parseXmlDate($obj->creationDate);
        if (isset($obj->domainRenevalDate)) $this->domainRenevalDate = parseXmlDate($obj->domainRenevalDate);
        if (isset($obj->techStatus)) $this->techStatus = $obj->techStatus;
        if (isset($obj->techStatusChangeDate)) $this->techStatusChangeDate = parseXmlDate($obj->techStatusChangeDate);
        if (isset($obj->financialStatus)) $this->financialStatus = $obj->financialStatus;
        if (isset($obj->financialStatusChangeDate)) $this->financialChangeDate = parseXmlDate($obj->financialStatusChangeDate);
        if (isset($obj->charityCode)) $this->charityCode = $obj->charityCode;
        if (isset($obj->period)) $this->period = $obj->period;
        if (isset($obj->paymentType)) $this->paymentType = $obj->paymentType;

        $this->errors = $errs;
        $d = new DateTime($this->creationDate);
        $d->add(Yii::app()->params['ticketExpiryDays']);
        $this->expiryDate = $d->format('Y-m-d');

        if (isset($obj->operation->adminContactsField)) {
            if (is_object($obj->operation->adminContactsField)) {
                $aa = array (0 => $obj->operation->adminContactsField);
                $obj->operation->adminContactsField = $aa;
            }
            if (is_array($obj->operation->adminContactsField)) {
                if (isset($obj->operation->adminContactsField[0])) {
                    $this->adminContact_0 = $obj->operation->adminContactsField[0]->newValue->nicHandle;
                    $this->adminContact_0_failureReason = $obj->operation->adminContactsField[0]->failureReason;
                }
                if (isset($obj->operation->adminContactsField[1])) {
                    $this->adminContact_1 = $obj->operation->adminContactsField[1]->newValue->nicHandle;
                    $this->adminContact_1_failureReason = $obj->operation->adminContactsField[1]->failureReason;
                }
            }
        }

        if (isset($obj->operation->billingContactsField)) {
            $this->billingContact = $obj->operation->billingContactsField->newValue->nicHandle;
            $this->billingContact_failureReason = $obj->operation->billingContactsField->failureReason;
        }
        if (isset($obj->operation->techContactsField)) {
            $this->techContact = $obj->operation->techContactsField->newValue->nicHandle;
            $this->techContact_failureReason = $obj->operation->techContactsField->failureReason;
        }
        if (isset($obj->operation->domainHolderCategoryField)) {
            $this->domainHolderCategory = $obj->operation->domainHolderCategoryField->newValue;
            $this->domainHolderCategory_failureReason = $obj->operation->domainHolderCategoryField->failureReason;
        }
        if (isset($obj->operation->domainHolderClassField)) {
            $this->domainHolderClass = $obj->operation->domainHolderClassField->newValue;
            $this->domainHolderClass_failureReason = $obj->operation->domainHolderClassField->failureReason;
        }
        if (isset($obj->operation->domainHolderField)) {
            $this->domainHolder = $obj->operation->domainHolderField->newValue;
            $this->domainHolder_failureReason = $obj->operation->domainHolderField->failureReason;
        }
        if (isset($obj->operation->resellerAccountField)) {
            $this->resellerAccount = $obj->operation->resellerAccountField->newValue;
            $this->resellerAccount_failureReason = $obj->operation->resellerAccountField->failureReason;
        }
        if (isset($obj->operation->nameservers)) {
            $this->nameserversCount = 0;
            foreach($obj->operation->nameservers as $i => $value) {
                $this->nameservers[$i] = $value->name->newValue;
                $this->ipAddresses[$i] = $value->ipAddress->newValue;
                $this->nameserversCount++;
            }
        }
    }

    /**
     * returns validation rules
     *
     * @return array  Yii validation rules
     * @access public
     */
    public function rules()
    {
        return array(
            array('id', 'required'),
            array('id', 'numerical', 'integerOnly' => true, 'min' => 1, 'max' => 9999999999),
        );
    }

    /**
     * returns Yii attribute labels
     *
     * @return array  Yii model attribute labels
     * @access public
     */
    public function attributeLabels()
    {
        return array(
            'adminStatus' => 'Admin Status',
            'adminStatusChangeDate' => 'Admin Status Date',
            'changeDate' => 'Change Date',
            'checkedOutTo' => 'Checked Out To',
            'clikPaid' => 'ClikPaid',
            'creationDate' => 'Created',
            'creator' => 'Creator',
            'domainName' => 'Domain Name',
            'domainRenevalDate' => 'Domain Renewal Date',
            'hasDocuments' => 'Has Docs',
            'hostmastersRemark' => 'Hostmasters\' Remark',
            'id' => 'Ticket ID',
            'previousHolder' => 'Prev Holder',
            'requestersRemark' => 'Ticket Remark',
            'techStatus' => 'DNS Status',
            'techStatusChangeDate' => 'DNS Status Date',
            'ticketHolder' => 'Domain Holder',
            'type' => 'Ticket Type',
            'charityCode' => 'Charity Code',
            'paymentType' => 'Payment type',
            'period' => 'Period',
            'financialStatus' => 'Financial Status',
            'financialChangeDate' => 'Financial Status Date',

            'adminContact_0' => 'Admin Contact 1',
            'adminContact_0_failureReason' => '',

            'adminContact_1' => 'Admin Contact 2',
            'adminContact_1_failureReason' => '',

            'billingContact' => 'Billing Contact',
            'billingContact_failureReason' => '',

            'domainHolderCategory' => 'Holder Category',
            'domainHolderCategory_failureReason' => '',

            'domainHolderClass' => 'Holder Class',
            'domainHolderClass_failureReason' => '',

            'domainHolder' => 'Domain Holder',
            'domainHolder_failureReason' => '',

            'resellerAccount' => 'Reseller Account',
            'resellerAccount_failureReason' => '',

            'techContact' => 'Tech Contact',
            'techContact_failureReason' => '',
        );
    }
}

