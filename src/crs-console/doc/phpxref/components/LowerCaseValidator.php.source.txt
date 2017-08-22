<?php

/**
 * defines LowerCaseValidator class
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
 * validates strings, checking that they are all lowercase
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class LowerCaseValidator
	extends RegexValidator
	{

    /**
     * constructor, sets up regex and error message
     * 
     * @return void  
     * @access public
     */
	public function __construct()
		{
		$this->regex = '/^[^A-Z]*$/';
		$this->invalid_msg = 'must be Lower Case';
		}
	}

