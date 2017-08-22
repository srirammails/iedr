<?php

/**
 * defines NicHandleSyntaxValidator class
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
 * Validates an IEDR NIC Handle's Syntax
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       NicHandleValidator
 */
class NicHandleSyntaxValidator
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
		$this->regex = '/^[A-Z0-9]+-IEDR$/';
		$this->invalid_msg = 'is not a valid NIC Handle';
		}
	}
