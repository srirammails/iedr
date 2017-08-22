<?php

/**
 * defines PhoneNumberValidator class
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
 * Validates A numeric phone of fax number
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
class PhoneNumberValidator
	extends RegexValidator
	{

    /**
     * regex for validating phone numbers
     * @var    string
     * @access public
     * @static
     */
	public static $phoneNumberRegex = '/^[0-9() -\+]{4,25}$/';

    /**
     * constructor, sets up regex and error message
     * 
     * @return void  
     * @access public
     */
	public function __construct()
		{
		$this->regex = PhoneNumberValidator::$phoneNumberRegex;
		$this->invalid_msg = 'is not a valid Phone Number (use only charaters "0-9 +-()") ; max length 25';
		}
	}

