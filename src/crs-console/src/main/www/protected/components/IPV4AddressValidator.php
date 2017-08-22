<?php

/**
 * defines AddressValidator class
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
 * Validate IPv4 dotted-quad addresses
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
class IPV4AddressValidator
	extends RegexValidator
	{

    /**
     * Singleton-patterned data holding the constructed IPv4 regex; will be null until first call to getRegex()
     * 
     * @var    string 
     * @access private
     * @static
     */
	static private $the_regex = null;

    /**
     * constructor, sets up regex and error message
     * 
     * @return void  
     * @access public
     */
	public function __construct()
		{
		$this->regex = self::getRegex();
		$this->invalid_msg = 'is not a valid IPv4 Address';
		}

    /**
     * constructs regex for constructor setup
     * 
     * @return string IPv4 dotted-quad regex
     * @access public
     * @static
     */
	static public function getRegex()
		{
		// use : IPV4AddressValidator::getRegex();
		if(self::$the_regex == null)
			{
			$ip4byte = '((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))';
			self::$the_regex = '/^'.$ip4byte.'\.'.$ip4byte.'\.'.$ip4byte.'\.'.$ip4byte.'$/';
			}
		return self::$the_regex;
		}
	}

