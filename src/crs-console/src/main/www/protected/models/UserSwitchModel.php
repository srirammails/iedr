<?php

/**
 * defines UserSwitchModel class
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
 * Data Model for User-Switch widget
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       UserSwitchWidget,userSwitchView,UserSwitchModel
 */
class UserSwitchModel extends CFormModel {

    /**
     * username (i.e., Nic Handle) to switch to
     *
     * @var    string
     * @access public 
     */
	public $switchUser;

    /**
     * URL to return to after switching user
     *
     * @var    unknown
     * @access public 
     */
	public $returnUrl;

    /**
     * constructor sets default username, return url
     * 
     * @return void  
     * @access public
     */
	public function __construct()
		{
		$this->switchUser = Yii::app()->user->nicHandle->nicHandleId;
		$this->returnUrl  = $_SERVER['REQUEST_URI'];
		}

    /**
     * returns URL to return to
     * 
     * @return string URL to return to
     * @access public
     */
	public function getSwitchActionUrl()
		{
		$actionId = Yii::app()->getController()->getAction()->getId();
		if ($actionId === 'index') {
			// this one handles situation, where actionId == index since the ?r=site/index part may not been added here
			return '/index.php?r=site/switchuser';
		} else {		
			return preg_replace('/\/'.$actionId.'/', '/switchuser', $this->returnUrl);
		}
		}

    /**
     * Yii validation rules for user-switch form data
     * 
     * @return array  validation rules array
     * @access public
     */
	public function rules()
		{
		return array(
			// username and password are required
			array('switchUser', 'required'),
			array('returnUrl',  'required'),
			// uppercase
			array('switchUser', 'UpperCaseValidator'),
			array('switchUser', 'validateSwitchUser'),
			// array('switchUser', 'NicHandleValidator'), // no: would cause error in trying to get logged-in user as part of cache key
			);
		}

    /**
     * Yii Form Labels array
     * 
     * @return array  form labels
     * @access public
     */
	public function attributeLabels()
		{
		return array(
			'switchUser'=>'Registrar',
			);
		}

    /**
     * validate user-to-switch-to, must be in list of switchable-to registrars
     * 
     * @param  string $attrib model attribute-name to validate
     * @param  array  $params validation parameters (ignored)
     * @return string  null on is-valid, otherwise error message
     * @access public 
     */
	public function validateSwitchUser($attrib,$params)
		{
		$v = ' is not a valid Registrar';
		$u = $this->$attrib;
		$usrs = getRegistrarsForLogin();
		if(isset($usrs[$u]))
			$v = null;
		return $v;
		}

    /**
     * modifies data before validation: makes switchUser uppercase
     *
     * @param  CEvent $event unused
     * @return void
     * @access public
     */
    public function onBeforeValidate($event)
    {
        $this->switchUser = strtoupper($this->switchUser);
        parent::onBeforeValidate($event);
    }

}

