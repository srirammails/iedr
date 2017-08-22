<?php

/**
 * defines RegistrationCodeValidator class
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
 * validates that a product code is a registration code, not a renewal code
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class RegistrationCodeValidator
	extends ProductCodeValidator
	{

    /**
     * error message
     * 
     * @var    string   
     * @access protected
     * @static
     */
	protected static $errMsg = ' is not a valid Registration Option';

    /**
     * overridden parent function which further restricts the validation rule so that only registrations are valid (not renewals)
     * 
     * @param  object    $p product code
     * @return object    true if product code is a registration product
     * @access protected
     */
	protected function prodIsRightType($p)
		{
		return $p->forRegistration;
		}
	}

