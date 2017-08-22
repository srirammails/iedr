<?php

/**
 * defines EmailAddrValidator class
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
 * validator email addresses
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
class EmailAddrValidator
	extends HostNameValidator
	{

    /**
     * validates supplied attribute
     * 
     * email is assumed valid if there is one at-sign,
     * and the trimmed email has a non-zero-length string left of the at-sign,
     * and right of the at-sign is a valid hostname
     * 
     * @todo   could be refactored to make better use of base class functionality
     * @param  CModel     $model     model with data to validate
     * @param  string     $attribute name of model attribute to validate
     * @return boolean    true if valid
     * @access protected
     */
	protected function validateAttribute($model,$attribute)
		{
		if($model && $attribute)
			{
			$email = $model->$attribute;
			$parts_arr = explode('@',trim($email));
			// only one @ in string ?
			if(count($parts_arr)==2)
				// check part left of @ is non-zero len
				if(strlen($parts_arr[0])>0)
					// check hostname part is valid hostname
					if(preg_match($this->regex, $parts_arr[1])===1)
						return true;
			}
		$lbls = $model->attributeLabels();
		$lbl = $lbls[$attribute];
		$this->invalid_msg = 'is not a valid Email Address';
		$model->addError($attribute,Yii::t('yii',$lbl.' '.$this->invalid_msg));
		return false;
		}
	}

