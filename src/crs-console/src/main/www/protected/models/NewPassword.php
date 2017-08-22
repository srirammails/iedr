<?php

/**
 * defines NewPassword class
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
 * Newpassword is the data structure for holding user-password and new password data-form data during a SiteController/createDirectAccount action request.
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       SiteController, Yii
 */

class NewPassword
	extends CFormModel
	{

    /**
     * Description for public
     * 
     * @var    string
     * @access public 
     */
	public $new_password;

    /**
     * new password repeated
     * 
     * @var    string
     * @access public 
     */
	public $repeat_new_password;
	
	/**
	 * Flag that indicates if Two Factor Authentication should be used
	 * 
	 * @var unknown
	 */
	public $useTwoFactorAuthentication = true;

    /**
     * message for display, from controller
     * 
     * @var    string
     * @access public 
     */
	public $msg;



	/**
	 * Declares the validation rules.
	 * 
	 * Password fields required, new and repeat password must not be different.
	 * 
	 * @return void   
	 * @access public 
	 */
	public function rules()
	{
		return array(
			array('new_password, repeat_new_password', 'required'),
			array('useTwoFactorAuthentication', 'safe'),
			array('new_password', 'PasswordStrengthValidator'),
			//Tests if the new and repeat passwords are the same.
			array('new_password', 'matchSuppliedPasswordsValidatorFn'),
			array('new_password', 'length', 'min'=>'8', 'max'=>'16'),
		);
	}

	/**
	 * Declares attribute labels.
	 * 
	 * @return void   
	 * @access public 
	 */
	public function attributeLabels()
	{
		return array(
			'new_password'=>'Nic Handle Password',
			'repeat_new_password'=>'Repeat Nic Handle Password',
			'useTwoFactorAuthentication'=>"Use Two Factor Authentication",
		);
	}
    
    /**
     * Function to ensure password, and repeat password match.
     * 
     * @return void   
     * @access public 
     */
	public function matchSuppliedPasswordsValidatorFn()
		{
			if($this->new_password != $this->repeat_new_password){
				$this->addError('repeat_new_password', 'New and repeat passwords must match.');
				$this->addError('new_password', '');
			}
         return true;	
		}

	}

