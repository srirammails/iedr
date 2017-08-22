<?php

/**
 * defines NicHandleIdentity class
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
 * define symbol for sessino attribute 'authenticated'
 */
define('AUTHENTICATED','authenticated');
/**
 * define symbol for sessino attribute 'authenticatedUser'
*/
define('AUTHENTICATED_USER_VO','authenticatedUser');
/**
 * define symbol for sessino attribute 'nicHandle'
*/
define('NIC_DETAILS','nicHandle');
/**
 * define symbol for sessino attribute 'resellerDefaults'
*/
define('RESELLER_DEFAULTS','resellerDefaults');
/**
 * define symbol for sessino attribute 'depositAccMin'
*/
define('DEPOSIT_ACC_MIN','depositAccMin');
/**
 * define symbol for sessino attribute 'depositAccMax'
*/
define('DEPOSIT_ACC_MAX','depositAccMax');
/**
 * define symbol for sessino attribute 'tariff'
*/
define('TARIFF','tariff');
/**
 * define symbol for sessino attribute 'agreementSigned'
*/
define('AGREEMENTSIGNED','agreementSigned');
/**
 * define symbol for sessino attribute 'ticketEdit'
*/
define('TICKETEDIT','ticketEdit');
/**
 * define symbol for sessino attribute 'lastInvoiceDate'
*/
define('LAST_INVOICE_DATE','lastInvoiceDate');
/**
 * define symbol for sessino attribute 'actualInternalUser'
*/
define('ACTUAL_INTERNAL_USER','actualInternalUser');
/**
 * define symbol for session attribute 'ResselerAccountName'
 */
define('ACCOUNT_NAME', 'accountName');


/**
 * Extends Yii CUserIdentity to authenticate with CRS-WS API Authentication etc
 *
 * Authenticates via CRS-WS API, also fetches User and Account data
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
*/
class NicHandleIdentity
extends CUserIdentity
{

	private $errorMap = array (
// 			"PasswordExpiredException" =>self::ERROR_PASSWORD_EXPIRED,
			"InvalidUsernameException" =>self::ERROR_INVALID_CREDENTIALS,
			"InvalidPasswordException" => self::ERROR_INVALID_CREDENTIALS,
			"Login Locked" => self::ERROR_LOGIN_LOCKED,
			"Empty secret" => self::ERROR_TFA_NOT_INITIALIZED,
			"GoogleAuthenticationException" => self::ERROR_INVALID_PIN,
			"Invalid pin" => self::ERROR_INVALID_PIN,
			"salt can not be empty" => self::ERROR_NO_SALT,
	);

// 	const ERROR_PASSWORD_EXPIRED = 1001;
	//const ERROR_NEW_PASSWORD_TOO_EASY = 1002;
	const ERROR_INVALID_CREDENTIALS = 1003;
	const ERROR_LOGIN_LOCKED = 1004;
	const ERROR_TFA_NOT_INITIALIZED = 1005;
	const ERROR_INVALID_PIN = 1006;
	const ERROR_NO_SALT = 1007;
	 
	function __construct($superUserName, $userName, $userPass, $google_pin=null) {
		parent::__construct(trim($userName), $userPass);
		$this->superUserName = trim($superUserName);
        $this->google_pin = $google_pin;
	}

	/**
	 * static string indicating user is not an internal user
	 *
	 * @var    string
	 * @access public
	 * @static
	 */
	public static $NOT_INTERNAL = 'NOT_INTERNAL';

	private $superUserName;

	/**
	 * Logged-in users Nic-Handle Account ID
	 *
	 * @var    integer
	 * @access private
	 */
	private $_id;

	public $sessionDuration = 0;

    public $google_pin;
    
    /**
     * number of seconds the account will remain locked
     * @var unknown
     */
    public $accountLockPeriod = 0;
    
	/**
	 * returns Logged-in users Nic-Handle Account ID
	 *
	 * @return integer Logged-in users Nic-Handle Account ID
	 * @access public
	 */
	public function getId()
	{
		return $this->_id;
	}

	/**
	 * Attempt authentication
	 *
	 * clears Yii Application Identity, and all user-related data, then attempts to authenticate via the backend CRS-WS API.
	 *
	 * @return boolean true if authentication succeeded
	 * @access public
	 */
	public function authenticate()
	{
		#Yii::log('AUTHENTICATE: '.print_r($this,true), 'error', 'NicHandleIdentity::authenticate()');
		$this->errorCode = self::ERROR_USERNAME_INVALID;
		$this->_id = null;
		Yii::app()->user->setId(null);
		$this->clearUserData();
		$this->setState(AUTHENTICATED, false);

		//      Yii::log('USER= '.print_r($this->username,true));
		//      Yii::log('SUPER_USER= '.print_r($this->superUserName,true));
		//      Yii::log('PASSOWRD= '.print_r($this->password,true));

		if(!isset($this->username) || !strlen($this->username)) {
			Yii::log('EMPTY USERNAME');
			return false;
		} else {
			Yii::log('NOT EMPTY USER ');
		}

		if(!isset($this->password) || !strlen($this->password)) {
			Yii::log('EMPTY PASSWORD');
			return false;
		} else {
			Yii::log('NOT EMPTY PASSWORD ');
		}

		if(Utility::isInternalNetwork()) {
			Yii::log('INTERNAL');
			if($this->CRSAuthenticationAndSwitchFunction($this->superUserName, $this->username, $this->password)) {
				$this->errorCode=self::ERROR_NONE;
				$this->setState(AUTHENTICATED, true);
			} else {
				$this->mapErrorMessage();
				$this->handleLoginLocked();				
			}
		} else if(!Utility::isInternalNetwork()) {
			Yii::log('NONE INTERNAL');
			if($this->CRSAuthenticationFunction($this->username, $this->password, $this->google_pin)) {
				$this->errorCode=self::ERROR_NONE;
				$this->setState(AUTHENTICATED, true);
			} else {
				$this->mapErrorMessage();
				$this->handleLoginLocked();
			}
		} else {
			Yii::log('NONE');
			$this->errorCode=self::ERROR_UNKNOWN_IDENTITY;
		}

		return ($this->errorCode==self::ERROR_NONE);
	}
	
	private function handleLoginLocked() {
		if ($this->errorCode == self::ERROR_LOGIN_LOCKED) {
			$exploded = explode(':', $this->errorMessage);
			if (count($exploded) == 4) {
				$this->accountLockPeriod = $exploded[3];
				Yii::log("LockPeriod (s): ".$this->accountLockPeriod, 'INFO', 'NicHandleIdentity::authenticate()');
			} else {
				Yii::log("Unknown LoginLockException message format: ".$this->errorMessage, 'WARN', 'NicHandleIdentity::authenticate()');
			}
		}
	}
	 
	protected function mapErrorMessage() {
		foreach ($this->errorMap as $key => $value) {
			if (stristr($this->errorMessage, $key)) {
				$this->errorCode=$value;
			}
		}
	}

	/**
	 * returns true if the current user has permission to switch to another registrar's account - for certain internal users only
	 *
	 * @todo this implementation is incomplete - always returns true
	 * @return boolean true
	 * @access public
	 * @static
	 */
	public static function canSwitchAccounts()
	{
		#if(!Yii::app()->user->isGuest and Yii::app()->user->actualInternalUser)
		#	{
		#	$usrs = Utility::getIEDRUsers();
			#	if(isset($usrs[Yii::app()->user->actualInternalUser]))
			return true;
			#	}
			#return false;
		}

		/**
		 * switch to another registrar's account
		 *
		 * This function is called as an alternative to authentication, in the special case of user-switching.
		 *
		 * The switch is perform by calling CRS API methods and setting new user token as the current token.
		 *
		 * @return void
		 * @access public
		 */
		public function switchToAccount() {
			$auvo = null;
			$switched = false;
			$actual = Yii::app()->user->authenticatedUser;

			CRSAuthenticationService_service::isUserSwitched($switched, $this->errorMessage, $actual);
			if ($switched) {
				CRSAuthenticationService_service::unswitch($actual, $this->errorMessage, $actual);
				if(count($this->errorMessage) != 0) {
					Yii::log('UN SWITCH ERROR= '.print_r($this->errorMessage,true));
					//   return false;
				}
			}
			Yii::log('name= ' .print_r($this->getName(), true));
			CRSAuthenticationService_service::switchUser($auvo, $this->errorMessage, $actual, $this->getName());
			if(count($this->errorMessage) != 0) {
				Yii::log('SWITCH ERROR= '.print_r($this->errorMessage,true));
				// return false;
			}

			$this->clearUserData();
			$this->setUserData($auvo);
			$this->setState(ACTUAL_INTERNAL_USER,$actual);
			Yii::log('SWITCH USER= '.print_r($auvo->username, true));
			return true;
		}

		/**
		 * Authenticate login credentials via the CRS-WS API.
		 *
		 * On authentication success, loads user-specific data
		 *
		 * @param  unknown   $nichandle username (nic-handle)
		 * @param  unknown   $nicpass   password
		 * @return boolean   true if successful
		 * @access protected
		 */
		protected function CRSAuthenticationFunction($nichandle, $nicpass, $pin) {
			$result = null; // CRSDomainAppService_authenticatedUserVO
			Yii::log($_SERVER['REMOTE_ADDR'], 'DEBUG', 'CRSAuthenticationFunction');
			CRSAuthenticationService_service::authenticate($result, $this->errorMessage, $nichandle, $nicpass, $_SERVER['REMOTE_ADDR'], $pin);
			if($result != null && count($this->errorMessage) == 0) {
				Yii::log('Authenticate result = '.print_r($result,true));
				$this->setState(ACTUAL_INTERNAL_USER, $nichandle);
				$this->setUserData($result);
				CRSInfo_service::getSessionDuration($this->sessionDuration, $this->errorMessage);
				return true;
			} else {
				Yii::log('Authenticate error = '.print_r($this->errorMessage,true));
			}
			return false;
		}


		protected function CRSAuthenticationAndSwitchFunction($superUserName, $userName, $userPass) {
			$result = null;
			CRSAuthenticationService_service::authenticateAndSwitchUser($result, $this->errorMessage, $superUserName, $userName , $userPass, $_SERVER['REMOTE_ADDR']);
			if($result != null) {
				if(count($this->errorMessage) == 0) {
					$this->setState(ACTUAL_INTERNAL_USER, $userName);
					$this->setUserData($result);
					CRSInfo_service::getSessionDuration($this->sessionDuration, $this->errorMessage);
					return true;
				} else {
					Yii::log('CRSAuthenticationAndSwitchFunction some errors '.print_r($this->errorMessage,true));
					return false;
				}
			} else {
				Yii::log('CRSAuthenticationAndSwitchFunction= '.print_r($this->errorMessage,true));
			}
			 
			return false;
		}
		/**
		 * Clears user-specific data
		 *
		 * @return void
		 * @access protected
		 */
		protected function clearUserData()
		{
			// these values are accessed elsewhere like '$x = Yii::app()->user->resellerDefaults;'
			foreach(array(
					ACTUAL_INTERNAL_USER,
					AGREEMENTSIGNED,
					AUTHENTICATED_USER_VO,
					DEPOSIT_ACC_MAX,
					DEPOSIT_ACC_MIN,
					LAST_INVOICE_DATE,
					NIC_DETAILS,
					RESELLER_DEFAULTS,
					TARIFF,
					TICKETEDIT,
					ACCOUNT_NAME,
			) as $X)
				$this->clearState($X);
		}

		/**
		 * reads and stores user-specific data
		 *
		 * @param  authenticatedUserVO $auvo contains CRS-WS API username and authentication token
		 * @return void
		 * @access public
		 */
		public function setUserData($auvo)
		{
			// logged in OK
			$this->setState(AUTHENTICATED_USER_VO, $auvo);
			$nicdetails = $this->getNICDetails($auvo);
			if($nicdetails!=null) {
				$this->_id = $nicdetails->accountId;
				$this->setState(NIC_DETAILS, $nicdetails);
			}
			$this->setState(RESELLER_DEFAULTS, $this->getResellerDefaults($auvo));
			$this->getDepositAccountLimits($auvo);
			$this->getResellerAccountDetails($auvo, $this->_id);
			Yii::log('set (LAST_INVOICE_DATE) '.LAST_INVOICE_DATE.'=', 'debug', 'NicHandleIdentity::getNICDetails()');
			Yii::log('set (reseller name) '.ACCOUNT_NAME.'='.$this->accountName, 'debug', 'NicHandleIdentity::getNICDetails()');
		}

		/**
		 * gets reseller defaults
		 *
		 * @param  authenticatedUserVO    $user authentication token
		 * @return CRSNicHandleAppService_resellerDefaultsVO   reseller defaults
		 * @access protected
		 */
		protected function getResellerDefaults($auvo)
		{
			$nichandle = $auvo->username;
			$result = null; // CRSNicHandleAppService_resellerDefaultsVO
			$errs   = null;
			CRSNicHandleAppService_service::getDefaults($result,$errs, $auvo, $nichandle);
			Yii::log(print_r($result,true), 'debug', 'NicHandleIdentity::getResellerDefaults()');
			return $result;
		}

		/**
		 * gets nic-handle details
		 *
		 * @param  authenticatedUserVO    $auvo authentication token
		 * @param  string    $nic  nic handle
		 * @return CRSNicHandleAppService_nicHandleVO   nichandle data
		 * @access protected
		 */
		protected function getNICDetails($auvo, $nic = null) {
			$result = null; // CRSNicHandleAppService_nicHandleVO
			$errs   = null;

			Yii::log('auvo= '.print_r($auvo,true));
			Yii::log('nic= '.print_r($nic,true));

			CRSNicHandleAppService_service::get($result, $errs, $auvo, $nic==null ? $auvo->username : $nic);
			if($result != null) {
				if(count($errs) == 0) {
					Yii::log("CRSNicHandleAppService_service::get ok ".print_r($errs,true));
				} else {
					Yii::log("Errors in CRSNicHandleAppService_service::get ".print_r($errs,true));
				}
			} else {
				Yii::log("CRSNicHandleAppService_service::get is null");
			}
			Yii::log(print_r($result,true), 'debug', 'NicHandleIdentity::getNICDetails()');
			return $result;
		}

		/**
		 * sets the minimum and maximum deposit amounts permitted per transaction, for this account, by callint the CRS-WS API
		 *
		 * @param  authenticatedUserVO    $auvo authentication token
		 * @return void
		 * @access protected
		 */
		protected function getDepositAccountLimits($auvo)
		{
			$result = null;
			$errs   = null;
			CRSPaymentAppService_service::getDepositLimits($result,$errs, $auvo);
			if($errs==null)
			{
				Yii::log(print_r($result,true), 'debug', 'NicHandleIdentity::getDepositAccountLimits()');
				$this->setState(DEPOSIT_ACC_MIN, $result->min);
				$this->setState(DEPOSIT_ACC_MAX, $result->max);
			}
		}

		/**
		 * gets, via CRS-WS API, some account details for given account ID.
		 *
		 * Details fetched :
		 * - tariff: one of { Deleted,RetailB,Silver,Trade,TradeA,TradeAChar,TradeB,TradeBChar }
		 * - agreementSigned: 0 or 1
		 * - ticketEdit: 0 or 1
		 *
		 * @param  integer   $accid Account ID
		 * @return void
		 * @access protected
		 */
		protected function getResellerAccountDetails($auvo, $accid)
		{
			$result = null;
			$errs   = null;
			CRSResellerAppService_service::get($result,$errs, $auvo, $accid);
			if($errs==null) {
				Yii::log(print_r($result,true), 'debug', 'NicHandleIdentity::getResellerAccount()');
				$this->setState(TARIFF, $result->tariff);
				$this->setState(AGREEMENTSIGNED, $result->agreementSigned);
				$this->setState(TICKETEDIT, $result->ticketEdit);
				$this->setState(ACCOUNT_NAME, $result->name);
			} else {
				$this->setState(TICKETEDIT, false);
				$this->setState(AGREEMENTSIGNED, false);
			}
		}

		/**
		 * gets last invoice date from CRS-WS API
		 *
		 * CRS-WS currently returns the last calendar day of the previous calendar month
		 *
		 * @param  authenticatedUserVO    $auvo authentication token
		 * @return string   date in string YYYY-MM-DD format
		 * @access protected
		 */
		protected function getLastInvoiceDate($auvo)
		{
			$result = null;
			$errs   = null;
			/*DEPRECATED*/
			//CRSPaymentAppService_service::getLastInvoiceDate($result,$errs, $auvo);
			if($errs==null)
			{
				//Yii::log(print_r($result,true), 'debug', 'NicHandleIdentity::getLastInvoiceDate()');
				return parseXmlDate($result);
			}
			return null;
		}
		
		public function getAccountName() {
			return $this->getState(ACCOUNT_NAME);
		}
	}


	