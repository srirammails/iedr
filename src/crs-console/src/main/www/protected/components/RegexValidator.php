<?php

/**
 * defines RegexValidator class
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
 * Validator for Yii Model Validation, using regular expressions
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       http://www.regular-expressions.info/
 * @see       http://www.yiiframework.com/doc/api/1.1/CValidator
 */
class RegexValidator
	extends CValidator
	{

    /**
     * perl-style regular expression used to validate
     * @var    string
     * @access public
     * @see http://php.net/manual/en/function.preg-match.php
     */
	var $regex = '/^.*$/';

    /**
     * eror message to display for invalid data; the invalid field's label is prepended to this string by {@link addErrMsgToModel}
     * @var    string
     * @access public
     */
	var $invalid_msg = 'invalid';

    /**
     * returns boolean outcome of regex match of field value
     * 
     * @param  string  $value field value
     * @return boolean true if count of regex data matches equals one
     * @access public 
     */
	public function validateValue($value)
		{
		return (preg_match($this->regex, $value)===1) ? true : false;
		}

    /**
     * adds an error message to a model about an attribute
     * 
     * @param  CModel    $model     the model to add the error message to
     * @param  string   $attribute the name of the attribute which is invalid
     * @return void     
     * @access protected
     */
	protected function addErrMsgToModel($model,$attribute)
		{
		$lbls = $model->attributeLabels();
		$lbl = $lbls[$attribute];
		$model->addError($attribute,Yii::t('yii',$lbl.' '.$this->invalid_msg));
		}

    /**
     * validates a model's attribute according to regex, adds error message if not valid
     * 
     * @param  CModel    $model     model with data to validate
     * @param  string    $attribute name of attribute in model to validate
     * @return boolean   true if valid
     * @access protected
     */
	protected function validateAttribute($model,$attribute)
		{
		// from CValidator
		if($model && $attribute)
			{
			$value=$model->$attribute;
			if(is_array($value))
				{
				// BUG  arrays not handled!! assume ok ...
				return true;
				}
			if($value!=null)
				if($this->validateValue($value))
					return true;
				else
					$this->addErrMsgToModel($model,$attribute);
			// null values implicitly allowed
			}
		return false;
		}
	}

