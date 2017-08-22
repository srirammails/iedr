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
class HistoryPaymentSearchModel
	extends TransferPaymentHistoryModel
	{

    /**
     * Description for var
     * @var    unknown
     * @access public 
     */
	var $domainName;

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
			array('domainName', 'required'),
			array('domainName', 'IEDomainValidator'),
			);
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return string    Return description (if any) ...
     * @access protected
     */
	protected function getExportFilenameTag()
		{
		return 'HistoryPaymentSearchModel_';
		}
	}

