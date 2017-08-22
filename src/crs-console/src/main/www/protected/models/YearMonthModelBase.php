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
class YearMonthModelBase
	extends ViewGridModelBase
	{

   var $invSummary;
    /**
     * Description for var
     * @var    integer
     * @access public 
     */
	var $year;

    /**
     * Description for var
     * @var    integer
     * @access public 
     */
	var $month;

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
		$this->year  = 0+date("Y");
		$this->month = 0+date("F");
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return mixed  Return description (if any) ...
     * @access public
     */
	public function rules()
		{
		return array(
			array('month', 'required'),
			array('month', 'length', 'min'=>1, 'max'=>10),
			array('month', 'validateMonth'),
			array('year', 'required'),
			array('year', 'length', 'min'=>4, 'max'=>4),
			array('year', 'numerical', 'min'=>2000, 'max'=>2100, 'integerOnly'=>true),
			);
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
	public function validateMonth($attrib,$params)
		{
		$ma = GridUtility::getMonthSelOpts();
		if(!isset($ms[$this->$attrib]))
			return ' is not a valid Month';
		}
	}

