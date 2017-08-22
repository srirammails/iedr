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
class Domains_Created
	extends CFormModel
	{

    /**
     * Description for public
     * @var    unknown
     * @access public 
     */
	public $domainsprocessed;

    public $returnData;

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  array  $d Parameter description (if any) ...
     * @return void  
     * @access public
     */
	public function __construct($d=array(), $data = null)
		{
		$this->domainsprocessed = $d;
        $this->returnData = $data;
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return array  Return description (if any) ...
     * @access public
     */
	public function rules()
		{
		return array();
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return mixed  Return description (if any) ...
     * @access public
     */
	public function attributeLabels()
		{
		return array(
			'domainsprocessed' => 'Domains Processed',
			);
		}
	}

