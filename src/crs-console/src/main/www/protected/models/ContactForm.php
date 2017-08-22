<?php

/**
 * defines ContactForm class
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
 * ContactForm class.
 * 
 * ContactForm is the data structure for keeping
 * contact form data. It is used by the 'contact' action of 'SiteController'.
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */

class ContactForm extends CFormModel
{

    /**
     * Description for public
     * 
     * @var    unknown
     * @access public 
     */
	public $name;

    /**
     * Description for public
     * 
     * @var    unknown
     * @access public 
     */
	public $email;

    /**
     * Description for public
     * 
     * @var    unknown
     * @access public 
     */
	public $subject;

    /**
     * Description for public
     * 
     * @var    unknown
     * @access public 
     */
	public $body;

	/**
	 * Declares the validation rules.
	 */
	public function rules()
	{
		return array(
			// name, email, subject and body are required
			array('name, email, subject, body', 'required'),
			// email has to be a valid email address
			array('email', 'email'),
		);
	}

	/**
	 * Declares customized attribute labels.
	 * If not declared here, an attribute would have a label that is
	 * the same as its name with the first letter in upper case.
	 */
	public function attributeLabels()
	{
		return array(
		);
	}
}
