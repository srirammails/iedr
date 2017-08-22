<?php

/**
 * defines ChangePasswordForm class
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
 * ChangePasswordForm is the data structure for holding user-password and new password data-form data during a SiteController/changePassword action request.
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       SiteController, NicHandleIdentity, Yii
 */

class ChangePasswordForm
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
     * new password
     * 
     * @var    string
     * @access public 
     */
	public $old_password;

    /**
     * new password repeated
     * 
     * @var    string
     * @access public 
     */
	public $repeat_new_password;

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
	 * Password fields required, old and new passwords must be different
	 * Old password must be the existing old password.
	 * New and repeat password must be different.
	 * 
	 * @return void   
	 * @access public 
	 */
	public function rules()
	{
		return array(
			array('old_password, new_password, repeat_new_password', 'required'),
			// old and new password must be different.
			array('oldPassword, new_password', 'newPasswordMustBeDifferentValidatorFn'),
			//Tests if the new and repeat passwords are the same.
			array('new_password', 'length', 'min'=>'8', 'max'=>'16'),
			array('new_password', 'PasswordStrengthValidator'),
			array('oldPassword', 'matchSuppliedPasswordsValidatorFn'),
			//supplied old password matches that already stored in the DB
			array('oldPassword', 'matchesOldPasswordValidatorFn'),
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
			'password'=>'NIC Password',
			'internalUser'=>'Internal Nic Handle',
		);
	}

    /**
     * Function to check if the supplied old password actually is the same as the one stored.
     * Only checks for SHA1 and OLD_PASSWORD matches. 
     * 
     * @return void   
     * @access public 
     */
	public function matchesOldPasswordValidatorFn() {
      
      $user = Yii::app()->user->authenticatedUser;
      $nicHandle = Yii::app()->user->name;
      
      CRSNicHandleAppService_service::changePassword($errs, $user, $nicHandle, $this->old_password, $this->new_password, $this->repeat_new_password);
      if(count($errs) == 0) {
         $model->msg = 'Password updated successfully.';
         Yii::app()->user->authenticatedUser->passwordChangeRequired=false;
         return true;
      } else {
         $model->msg = 'Password updated failure.';
         $this->addError('old_password', 'Old password does not match stored password.');
         return false;
      }
		}

    /**
     * Function to forbid using the same old and new passwords.
     * 
     * @return void   
     * @access public 
     */
	public function newPasswordMustBeDifferentValidatorFn()
		{

		if(strtolower(trim($this->old_password)) == strtolower(trim($this->new_password)))
			$this->addError('new_password', 'Old and New passwords must not match.');

		return true;	
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

