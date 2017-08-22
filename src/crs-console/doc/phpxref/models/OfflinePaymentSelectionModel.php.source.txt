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
class OfflinePaymentSelectionModel
	extends DynamicDomainListModel
	{

    /**
     * Description for public
     * @var    unknown
     * @access public 
     */
	public $command,$returnurl,$renewalDateType;

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  unknown $type Parameter description (if any) ...
     * @return void   
     * @access public 
     */
	public function __construct($type=CRSPaymentAppService_renewalDateType::_CURRENT)
		{
		parent::__construct();
		$this->val_rules   = array(
			array('domainlist,command,returnurl', 'required'),
			array('command', 'validateCommand'),
			);
		$this->renewalDateType = $type;
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
	public function validateCommand($attrib,$params)
		{
		$v = ' is not a valid Command';
		switch($this->$attrib) { case 'remove_from_batch': $v = null; break; }
		return $v;
		}
	}

