<?php

/**
 * defines FormModelBase class
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
 * base class for models which need to display messages (e.g., success, ID of created object, etc) or errors
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       http://www.yiiframework.com/doc/api/1.1/CFormModel
 */
class FormModelBase
	extends CFormModel
	{

    /**
     * error message for display in view
     * @var    string
     * @access public 
     */
	public $error;

    /**
     * a code for which, if any, non-error message to display in a view
     * @var    string
     * @access public 
     */
	public $message;

    /**
     * array member for holding data returned by some operation for display in a view
     * @var    array 
     * @access public
     */
	public $commandresults;

    /**
     * constructor, creates commandresults array
     * 
     * @return void  
     * @access public
     */
	public function __construct()
		{
		parent::__construct();
		$this->commandresults = array();
		}

    /**
     * model operation called just before validators; calls {@link forceCaseOfFields}
     * 
     * @param  CModelEvent $aCModelEvent (ignored)
     * @return void   
     * @access public 
     */
	public function onBeforeValidate($aCModelEvent)
		{
		$this->forceCaseOfFields();
		}

    /**
     * trims leading, trailing and repeating newlines from an input string
     * 
     * @param  string $t string to be trimmed
     * @return string trimmed string
     * @access public 
     * @static
     */
	public static function trimNewlines($t)
		{
		$r = preg_replace("/\s*[\r\n]\s*/D","\r\n",trim($t));
		#Yii::log('result:'.str_replace(array("\r","\n"),array('\r','\n'),$r), 'debug', 'FormModelBase::trimNewlines()');
		return $r;
		}

    /**
     * forces form-fields which have upper- or lower-case-only validators to be all the correct case, before validation
     * 
     * @return void  
     * @access public
     */
	public function forceCaseOfFields()
		{
		foreach($this->rules() as $rarr)
			{
			$farr = explode(',',$rarr[0]);
			switch($rarr[1])
				{
				case 'UpperCaseValidator':
					foreach($farr as $f) if(!is_array($this->$f)) $this->$f = strtoupper($this->$f);
					break;
				case 'LowerCaseValidator':
					foreach($farr as $f) if(!is_array($this->$f)) $this->$f = strtolower($this->$f);
					break;
				}
			}
		}
	}

