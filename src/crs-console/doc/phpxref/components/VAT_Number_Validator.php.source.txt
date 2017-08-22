<?php

/**
 * defines VAT_Number_Validator class
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
 * validates European VAT numbers
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
class VAT_Number_Validator
	extends RegexValidator
	{

    /**
     * constructor, sets up regex string
     * 
     * Regex derived from info on pages 278 & 279 of {@link http://my.safaribooksonline.com/book/programming/regular-expressions/9780596802837/validation-and-formatting/284#X2ludGVybmFsX0ZsYXNoUmVhZGVyP3htbGlkPTk3ODA1OTY4MDI4MzcvMjc4}
     * Regexes update from {@link http://www.iecomputersystems.ie/ordering/eu_vat_numbers.htm}
     * See also {@link http://ec.europa.eu/taxation_customs/vies/faqvies.do#item11}
     * 
     * @return void  
     * @access public
     */
	public function __construct()
		{
		$EU_Vat_Regex = '^('.
			# Austria
			'(AT)U[0-9]{8}|'.
			# Belgium
			'(BE)[0-9]{9}|'.
			# Bulgaria
			'(BG)[0-9]{9,10}|'.
			# Cyprus
			'(CY)[0-9]{8,9,10}|'.
			# Czech Republic
			'(CZ)[0-9]{8,9,10}|'.
			# Denmark
			'(DK)[0-9]{8}|'.
			# Estonia
			'(EE)[0-9]{9}|'.
			# Finland
			'(FI)[0-9]{8}|'.
			# France (dont use I or O)
			'(FR)[0-9A-HJ-NP-Z]{2}[0-9]{9}|'.
			# Germany
			'(DE)[0-9]{9}|'.
			# Greece
			'(EL|GR)0[0-9]{8}|'.
			# Hungary
			'(HU)[0-9]{8}|'.
			# Ireland
			'(IE)?[0-9][0-9A-Z+*][0-9]{5}[A-Z]|'.
			# Italy
			'(IT)[0-9]{11}|'.
			# Latvia
			'(LV)[0-9]{11}|'.
			# Lithuania
			'(LT)([0-9]{9}|[0-9]{12})|'.
			# Luxembourg
			'(LU)[0-9]{8}|'.
			# Malta
			'(MT)[0-9]{8}|'.
			# Netherlands
			'(NL)[0-9]{9}B[0-9]{2}|'.
			# Poland
			'(PL)[0-9]{10}|'.
			# Portugal
			'(PT)[0-9]{9}|'.
			# Romania
			'(RO)[0-9]{2,10}|'.
			# Slovakia
			'(SK)[0-9]{10}|'.
			# Slovenia
			'(SI)[0-9]{8}|'.
			# Spain
			'(ES)([A-Z][0-9]{8}|[A-Z][0-9]{7}[A-Z]|[0-9]{8}[A-Z])|'.
			# Sweden
			'(SE)[0-9]{10}01|'.
			# United Kingdom
			'(GB)([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})'.
			')$';

		$this->regex = '/'.$EU_Vat_Regex.'/';
		$this->invalid_msg = 'is not a <a href="http://ec.europa.eu/taxation_customs/vies/faqvies.do#item11">valid EU VAT Number</a>';
		}

    /**
     * trims away non-alphanumeric characters and validates, setting model error message if invalid
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
			$vat = ''.$model->$attribute;
			$trimmed = preg_replace('/[^A-Z0-9+*]/i','',$vat);
			if(strlen($vat)==0 or preg_match($this->regex, $trimmed)===1)
				return true;
				// maybe also validate/lookup online : http://ec.europa.eu/taxation_customs/vies/checkVatService.wsdl
			}
		$lbls = $model->attributeLabels();
		$lbl = $lbls[$attribute];
		$model->addError($attribute,Yii::t('yii',$lbl.' '.$this->invalid_msg));
		return false;
		}

	}

