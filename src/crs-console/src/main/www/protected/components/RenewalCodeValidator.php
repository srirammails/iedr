<?php

/**
 * defines RenewalCodeValidator class
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
 * validator that validates that a product code is a 1-year renewal product
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class RenewalCodeValidator
	extends ProductCodeValidator
	{

    /**
     * error message
     * 
     * @var    string   
     * @access protected
     * @static
     */
	protected static $errMsg = ' is not a valid Renewal Option';

    /**
     * returns true IIF product code is 'RRen1Yr', a 1-year renewal product
     * 
     * @param  object    $p product code
     * @return boolean   true if product code is 'RRen1Yr'
     * @access protected
     */
	protected function prodIsRightType($p)
		{
		#Yii::log('have:'.print_r($p,true), 'debug', 'RenewalCodeValidator::prodIsRightType()');
		return $p->id=='RRen1Yr';
		}
}

