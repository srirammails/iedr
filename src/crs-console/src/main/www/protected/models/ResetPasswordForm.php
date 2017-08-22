<?php

/**
 * defines ResetPasswordForm class
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
 * ResetPasswordForm is a bean which allows to request a password reset token and change the password using the provided reset token. 
 * Used in the 'reset password' by ResetPasswordController
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       ResetPasswordController, Yii
 */

class ResetPasswordForm
	extends CFormModel
	{

	/**
	 * nichandle asking for the password reset
	 * 
	 * @var string
	 * @access public
	 */
	public $username;
	
	/**
	 * password reset token
	 * 
	 * @var string
	 * @access public
	 */
	public $token;
		
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
	 * secret generated for the user
	 * 
	 * @var string
	 */
	public $secret;
	
    /**
     * message for display, from controller
     * 
     * @var    string
     * @access public 
     */
	public $msg;
	
	public $errMsg;

	/**
	 * keeps all errors got from th backend
	 * 
	 * @var unknown
	 */
	private $backendErrors;
	
	private $errorMap = array(
			'NicHandleNotFoundException' => 'Nic handle not found in the database.',
			'NicHandleEmailException' => 'Error sending email.',
			'TokenExpiredException' => 'Token not valid: you should request for another one.',
			'PasswordTooEasyException' => 'Supplied password is too easy.'
			);
	
	const tokenRequestScenario = 'tokenRequest';
	const passwordChangeScenario = 'passwordChange';
	
	/**
	 * Declares the validation rules
	 * 
	 * 
	 * @return void   
	 * @access public 
	 */	
	public function rules()
	{
		return array(
			array('username', 'required','on'=>self::tokenRequestScenario),
			array('username, token, new_password, repeat_new_password', 'required', 'on'=>self::passwordChangeScenario),
			array('new_password', 'length', 'on'=>self::passwordChangeScenario, 'min'=>'8', 'max'=>'16'),
			array('new_password', 'PasswordStrengthValidator', 'on'=>self::passwordChangeScenario),
			array('repeat_new_password', 'matchSuppliedPasswordsValidatorFn', 'on'=>self::passwordChangeScenario),
			array('secret', 'any', 'on'=>self::passwordChangeScenario),
            array('username', 'NicHandleSyntaxValidator'),
		);
	}
	
	public function attributeLabels()
	{
		return array(
				'username'=>'NIC Handle',
				'new_password'=>'New NIC Password',
				'repeat_new_password'=>'Repeat New NIC Password',				
				'token'=>'Password Reset Token',
		);
	}
	
	public function requestResetToken() {
		Yii::log('Request reset token for '.$this->username, 'DEBUG', 'ResetPasswordForm::requestResetToken()');
		CRSNicHandleAppService_service::requestPasswordReset($this->backendErrors, $this->username, $_SERVER['REMOTE_ADDR']);
		Yii::log('Request token AFTER errors: '.$this->backendErrors, 'DEBUG', 'ResetPasswordForm::requestResetToken()');
		$this->mapErrorMessage();
	}
	
	public function changePassword() {
		Yii::log('Password change requested with token '.$this->token, 'DEBUG', 'ResetPasswordForm::changePassword()');
		CRSNicHandleAppService_service::resetPassword($this->secret, $this->backendErrors, $this->token, $this->username, $this->new_password);
		Yii::log('Password change errors '.$this->backendErrors, 'DEBUG', 'ResetPasswordForm::changePassword()');
		$this->mapErrorMessage();		
	}
	
	protected function mapErrorMessage() {
		$this->errMsg=$this->backendErrors;
		foreach ($this->errorMap as $key => $value) {
			if (stristr($this->backendErrors, $key)) {
				$this->errMsg=$value;
			}
		}
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
	public function any() {
		return true;
	}

    /**
     * modifies data before validation: makes username uppercase
     *
     * @param  CEvent $event unused
     * @return void
     * @access public
     */
    public function onBeforeValidate($event)
    {
        $this->username = strtoupper($this->username);
        parent::onBeforeValidate($event);
    }

}
	

