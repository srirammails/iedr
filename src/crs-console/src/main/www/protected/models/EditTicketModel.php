<?php

/**
 * defines EditTicketModel class
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
 * model for data for the ticket-editing page
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class EditTicketModel extends ViewTicketModel {

    /**
     * combined class and category (separated by a colon), one of the values returned by {@link get_class_category()}, used in a select-list
     *
     * @var    string
     * @access public 
     */
   public $applicant;
   public $domain_name;
   public $expiryDate ;
   public $editable;
    /**
     * constructor, sets ticket ID
     * 
     * @param  integer $id ticket ID
     * @return void   
     * @access public 
     */
	public function __construct($id=null)
		{
		parent::__construct($id);
		}

    /**
     * returns Yii validation rules
     * 
     * @return array  validation rules
     * @access public
     */
    public function rules()
        {
        return array(
                array('id,domainHolder,adminContact_0,techContact,applicant,requestersRemark', 'required'),
                array('ipAddresses', 'safe'),
                array('id,nameserversCount', 'numerical','integerOnly'=>true,'min'=>1,'max'=>9999999999),
                array('adminContact_0,adminContact_1,billingContact,techContact', 'NicHandleValidator'),
                array('requestersRemark', 'length', 'max'=>500),
                array('charityCode', 'length', 'max'=>20),
                array('nameservers', 'NameserverListComplexValidator', 'dname'=>'domainName'),
            );
        }

    /**
     * copies ticket-object data into this instance
     * 
     * @param  CRSTicketAppService_ticketVO $obj  Ticket object to copy data from
     * @param  string $errs errors from backend for display
     * @return void   
     * @access public 
     */
	public function fillFromObject($obj, $errs)
		{
		parent::fillFromObject($obj, $errs);
			$cls = $this->domainHolderClass;
			$cat = $this->domainHolderCategory;
		$this->applicant = $cls.':'.$cat;
		}

    /**
     * copies non-editable data from ticket-object into this instance
     * 
     * @param  CRSTicketAppService_ticketVO $ticketVO  Ticket object to copy data from
     * @return void  
     * @access public
     */
	public function setReadOnlyDataFromObj($ticketVO)
		{
		$ro_flds = array('id','domainName','adminStatus','hostmastersRemark','type','techStatus','period','paymentType','charityCode');
		foreach($ro_flds as $f)
            if (isset($ticketVO->$f))
                $this->$f = $ticketVO->$f;

        $d=new DateTime($ticketVO->creationDate);
        $d->add(Yii::app()->params['ticketExpiryDays']);
        $this->expiryDate = $d->format('Y-m-d');

        $this->billingContact = $ticketVO->operation->billingContactsField->newValue->nicHandle;
        }

    }

