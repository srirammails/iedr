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
class DynamicDomainListModel
	extends DynamicModelBase
	{

    /**
     * Description for public
     * @var    unknown
     * @access public 
     */
	public $domainlist;

    /**
     * Description for protected
     * @var    array    
     * @access protected
     */
	protected $_domainlistArray;

    public $domainlistWithSummary;
    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     */
	public function __construct()
		{
		parent::__construct();
		$this->val_rules   = array(array('domainlist'),);
		$this->attr_labels = array('domainlist' => 'Domains to be Processed',);
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  string    $d Parameter description (if any) ...
     * @return void     
     * @access protected
     */
	protected function addDomainToList($d)
		{
		if(preg_match(IEDomainValidator::$ie_domain_regex,$d)==1)
			$this->_domainlistArray[$d]=$d;
		else
			Yii::log('Invalid domain : "'.$d.'"', 'error', 'DynamicDomainListModel::addDomainToList()'.__LINE__);
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  unknown $a Parameter description (if any) ...
     * @return void   
     * @access public 
     */
	public function setDomainListFromArray($a)
		{
		$this->domainlist = implode(',',$a);
		$this->_domainlistArray = null;
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return array  Return description (if any) ...
     * @access public
     */
	public function domainListToArray()
		{
		// turn posted comma-separated list of domains into an array
		if($this->_domainlistArray==null or !is_array($this->_domainlistArray))
			{
			$this->_domainlistArray = array();
			$s = $this->domainlist;
            if($s!=null and strlen($s)>=4/*min valid domain name len*/)
            {
                  if(!(strpos($s,',')===false))
                     foreach(explode(',',$s) as $k => $v)
                        $this->addDomainToList($v);
                  else $this->addDomainToList($s);
            }
			// else : null, empty or invalid string - ignore
			}
        sort($this->_domainlistArray);
		return $this->_domainlistArray;
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  unknown $d Parameter description (if any) ...
     * @return string  Return description (if any) ...
     * @access public 
     * @static
     */
	public static function domainToVarPrefix($domain) { 
      return 'domlist_'.str_replace('.ie', '', $domain);
   }

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  unknown $p Parameter description (if any) ...
     * @return void   
     * @access public 
     */
	public function setFromPOST($p)
		{
		// first copy the GridSelectionModel data from POST (including 'domainlist')
		parent::setFromPOST($p);
		// then create all the dynamic fields from the 'domainlist'
		$this->createDomainConfirmList();
		// then again set the dynamically-created (incl. checkbox) fields from the POST
		parent::setFromPOST($p);
		// remove from list any domains that were unchecked
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  unknown $o Parameter description (if any) ...
     * @return void   
     * @access public 
     */
	public function copyFromOther($o)
		{
		parent::copyFromOther($o);
		// then create all the dynamic fields from the 'domainlist'
		$this->createDomainConfirmList();
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     */
	public function removeUncheckedDomains()
		{
		// remove domains from list(s) that are un-checked
		if($this->domainlist!=null)
			{
			$to_remove = array();
			foreach($this->domainListToArray() as $k => $v)
				{
				$mbrnam = DynamicDomainListModel::domainToVarPrefix($v);
				$mbr_conf = $mbrnam.'_confirmed';
				// test checkbox state
				if(isset($this->$mbr_conf) && ($this->$mbr_conf)!=null)
					if($this->$mbr_conf != 1) {
						$to_remove[$k] = $v;
               }
				}
			Yii::log('removing domains : '.print_r($to_remove,true), 'debug', 'DynamicDomainListModel::removeUncheckedDomains()');
			$this->setDomainListFromArray(array_diff($this->domainListToArray(), $to_remove));
			Yii::log('remaining domains : '.print_r($this->domainlist,true), 'debug', 'DynamicDomainListModel::removeUncheckedDomains()');
			}
		else
			Yii::log('domainlist not set', 'error', 'DynamicDomainListModel::removeUncheckedDomains()');
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     */
	public function createDomainConfirmList()
		{
		// add model data members - to represent one checkbox and dropdownlist _per_domain_  - from a list of domains
		if($this->domainlist != null)
			{
			$regprods = get_reg_prices();
			$dft_prodcode = $regprods['DEFAULT']['REN'];

			foreach($this->domainListToArray() as $k => $v)
				{
				$mbrnam = DynamicDomainListModel::domainToVarPrefix($v);
            
				// 'confirmed' for confirm checkbox
				$mbr_conf = $mbrnam.'_confirmed';
				$this->$mbr_conf = true;
				$this->addValRule($mbr_conf,'boolean');
            
            $mbr_prodcode = $mbrnam.'_confirmed';
            
				$this->$mbr_prodcode = $dft_prodcode;
            
		//		$this->addValRule($mbr_prodcode,'RenewalCodeValidator'); // 1-year only

				$this->attr_labels[$mbr_conf] = $v;
            Yii::log('labels= ',print_r($mbr_conf,true));
				}
			#Yii::log(print_r($this,true), 'debug', 'DynamicDomainListModel::createDomainConfirmList()');
			}
		else
			Yii::log('domainlist WAS NULL .. '.print_r($this,true), 'error', 'DynamicDomainListModel::createDomainConfirmList()');
		}
	}

