<?php

/**
 * defines LoginForm class
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
 * LoginForm is the data structure for holding user-login-form data during a SiteController/login action request.
 * 
 * A Model Validator performs the authentication, by creating a CUserIdentity-subclass instance (NicHandleIdentity), and
 * invoking it's authenticate() method.
 * If authentication succeeds, the Yii application's login() function is called, with the authenticated NicHandleIdentity
 * instance as a parameter. (Yii->app()->login() merely stores the Identity instance, and does no authentication or authorization)
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

class LoginForm
	extends CFormModel
	{

    /**
     * username sent by login request
     * 
     * @var    string
     * @access public 
     */
	public $username;

    /**
     * password sent by login request
     * 
     * @var    string
     * @access public 
     */
	public $password;

    /**
     * unused variable
     * 
     * @var    unknown
     * @access public 
     */
	public $rememberMe;

    /**
     * optional internal username data sent by login request
     * 
     * @var    unknown
     * @access public 
     */
	public $internalUser;

    /**
     * NichandleIdentity Instance which performs authentication
     * 
     * @var    CUserIdentity
     * @access private
     */
	private $_identity;

	/**
	 * Declares the validation rules.
	 * 
	 * The rules state that username and password are required,
	 * and password needs to be authenticated.
	 * 
	 * @return void   
	 * @access public 
	 */
	
	/**
	 * new password sent by login request 
	 * 
	 * @var unknown
	 */
    /**
     * google pin for two factor authentication
     *
     * @var
     */
    public $google_pin;
    
    /**
     * For user convenience: holds the current server time can be helpful when troubleshooting problems with TFA
     * @var CRSInfo_serverTime
     */
    public $serverTime;

	public function rules()
	{
		return array(
			// username and password are required
			array('username, password, internalUser', 'required'),
			array('password', 'length', 'max'=>'16'),
			// uppercase
			array('username, internalUser', 'UpperCaseValidator'),
            // internal username
			array('internalUser', 'validateInternalUser'),
			// array('internalUser', 'NicHandleValidator'), # no, would cause recursive nic lookups
            array('google_pin', 'pinValidator'),
            // password needs to be authenticated
            array('password', 'authenticate'),
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
			'username'=>'NIC Handle',
			'password'=>'NIC Password',
			'internalUser'=>'Internal Nic Handle',
            'google_pin'=>'PIN',
        );
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

    /**
     * Validator function for internal-username form data
     * 
     * the internal-username must be found on the list returned by Utility::getIEDRUsers()
     * 
     * @param  string  $attrib name of model-attribute being validated
     * @param  array   $params validation parameter array
     * @return string  null if valid, or error message string
     * @access public 
     */
	public function validateInternalUser($attrib,$params)
		{
         if(Utility::isInternalNetwork()) {
            $v = ' is not a valid Internal User';
            $u = $this->$attrib;
            $usrs = Utility::getIEDRUsers();
            if(isset($usrs[$u]))
               $v = null;
            return $v;
         }
		}

    public function pinValidator($attrib, $params) {
        $pin = $this->$attrib;
        if (!empty($pin)) {
            if (is_numeric($pin)) {
                return true;
            } else {
                $this->addError($attrib, 'must be numerical');
                return false;
            }
        }
    }

	/**
	 * Validator function which authenticates username/password credentials
	 * 
	 * A NicHandleIdentity object instance is created with the request credentials,
	 * then it's authenticate member function is called.
	 * If authentication is not successful, an error message is added to the model
	 * for the view to display.
	 * 
	 * @param  string  $attrib name of model-attribute being validated
	 * @param  array   $params validation parameter array
	 * @return string  null if valid, or error message string
	 * @access public 
	 */
    public function authenticate($attribute,$params) {
    	//        Yii::log(print_r($this, true), 'DEBUG', 'LoginForm::authenticate()');
    	if(!$this->hasErrors()) {
    		$this->_identity=new NicHandleIdentity($this->internalUser, $this->username, $this->password, $this->google_pin);
    		if(!$this->_identity->authenticate($this->internalUser)) {
    			if(Utility::isInternalNetwork() && $this->internalUser == 'NOT_INTERNAL') {
    				$this->addError('internalUser','Incorrect Internal User.');
    			} else {
    			    if ($this->_identity->errorCode === NicHandleIdentity::ERROR_INVALID_CREDENTIALS) {
    					$this->addError('password','Incorrect NIC-Handle or password.');
    				} else if ($this->_identity->errorCode === NicHandleIdentity::ERROR_LOGIN_LOCKED) {
    					$time = null;
    					if ($this->_identity->accountLockPeriod > 120 ) {
    						$time = round($this->_identity->accountLockPeriod / 60, 0, PHP_ROUND_HALF_DOWN).' minutes';
    					} else {
    						$time = ($this->_identity->accountLockPeriod)." seconds";
    					}
    					$this->addError('password', "Your account has been locked for $time due to unsuccessfull login attempts.");
    				} else if ($this->_identity->errorCode === NicHandleIdentity::ERROR_TFA_NOT_INITIALIZED) {
    					$this->addError('google_pin', 'Your account has Two Factor Authentication turned on, but you have to generate your own secred code first by resetting your password');
    				} else if ($this->_identity->errorCode === NicHandleIdentity::ERROR_INVALID_PIN) {
    					$this->addError('google_pin', 'Invalid PIN');
    				} else if ($this->_identity->errorCode === NicHandleIdentity::ERROR_NO_SALT) {
    					$this->addError('password', 'Password reset required');
    				} else {
    					$this->addError('password', 'Unexpected error performing authentication');
    				}
    			}
    		}
    	}
    }
	/**
	 * Logs in the user if authentication was successful
	 * 
	 * If authentication was not yet attempted, it is attempted here.
	 * 
	 * @return boolean whether login is successful
	 * @access public 
	 */
	public function login() {
      if ($this->_identity === null) {
         $this->_identity = new NicHandleIdentity($this->internalUser, $this->username, $this->password, $this->new_password, $this->google_pin);
         $this->_identity->authenticate();
      }
      if ($this->_identity->errorCode === NicHandleIdentity::ERROR_NONE) {
          $duration = 0; // prevent "cookie-based authentication"
//         $duration = $this->_identity->sessionDuration;
         Yii::app()->user->login($this->_identity, $duration);
         return true;
      } else
         return false;
   }
}

