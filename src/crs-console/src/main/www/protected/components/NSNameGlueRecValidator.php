<?php

/**
 * defines NSNameGlueRecValidator class
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
 * Validates Nameserver IP Addresses, according to Glue-Record Validation Rules.
 *
 * This Validator must be referenced by adding options to the reference
 * which specify, along with the name of the model-attribute to be validated,
 * two or three additional items:
 *
 * (1) the name of the model-attribute of the Domain name having the Nameserver (or Domain name list)
 *
 * (2) the name of the model-attribute of the Host Name of the Nameserver (or Nameserver list)
 *
 * (3) additionally, if the previous item is a list - the index
 *
 * Example Model Validation Rules entry:
 *
 * array('nameserver_ipaddress_1', 'NSNameGlueRecValidator', 'dname'=>'domainlist', 'ns_name'=>'nameserver_hostname_1'),
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class NSNameGlueRecValidator
	extends CValidator
	{

    /**
     * name of model attribute containing corresponding nameserver hostname (or list of hostnames)
     *
     * @var    string
     * @access public 
     */
	public $ns_name = null;

    /**
     * index in the array of nameservers
     *
     * @var    integer
     * @access public
     */
    public $index = null;

    /**
     * name of model attribute containing domain-name (or list of domain names)
     *
     * @var    string
     * @access public 
     */
	public $dname 	= null;

    /**
     * Validates the IPv4 Address of a DNS Nameserver, adds error message if not valid
     * 
     * First checks if the model-attribute referred to by {@link ns_name}, i.e. the namesserver, matches
     * the model-attribute referred to by {@link dname} ; if this matches, then the "Glue Record" validation
     * rule applies:
     * 
     *  - When Domain Name is in the Domain of a Nameserver, the Nameserver MUST have a valid IPv4 Address
     * 
     * When the Domain does not match the Nameserver hostname, the "Non-Glue Record" rule applies:
     * 
     *  - When Domain Name is NOT in the Domain of a Nameserver, the Nameserver MUST NOT have an Address
     * 
     * @param  CModel    $model     model with data to validate
     * @param  string    $attribute name of attribute in model to validate, containing either en empty or null string, OR an IPv4 Address
     * @return boolean   true if valid
     * @access public 
     */
    public function validateAttribute($model, $attribute)
        {
        $ret = false;
        $msg = 'ERR';
        if($model && $attribute)
            {
            $x = $this->ns_name;
            if (!isset($model->$x)) return false;
            $ns_value = $model->$x;
            if (is_array($ns_value)) $ns_value = $ns_value[$this->index];
            $x = $this->dname;
            if (!isset($model->$x)) return false;
            $dname = $model->$x;
            if (!isset($model->$attribute)) return false;
            $ipaddr = $model->$attribute;
            if (is_array($model->$attribute)) {
                $ipaddr = $ipaddr[$this->index];
                $errattr = "$attribute"."[$this->index]";
            } else $errattr = $attribute;
            switch($this->any_domainnames_match($dname, $ns_value))
                {
                case 'NONE':
                    // No Match: Non-Glue-Record validation applies: Nameserver MUST NOT have an Address
                    $msg = 'MUST be empty';
                    $ret = (($ipaddr==null) || ($ipaddr==''));
                    break;
                case 'SOME': case 'ALL':
                    // Some or All Match: Glue-Record validation applies: Nameserver MUST have a Valid IPv4 Address
                    if (($ipaddr==null) || ($ipaddr=='')) {
                        $msg = 'MUST be filled';
                        $ret = false;
                    } else {
                        $msg = 'IP Address is invalid';
                        $ret = (preg_match(IPV4AddressValidator::getRegex(),$ipaddr)==1);
                    }
                    break;
                }
            }
        if(!$ret) $this->err($model, $errattr, $msg);
        return $ret;
        }

    /**
     * returns whether, for the input Nameserver Hostname and Domain Name list, None, Some, or All of the domains match the Nameserver
     * 
     * @param  mixed     $dname    single domain name, or array of domain names, in form "X.Y.ie"
     * @param  string    $ns_value nameserver hostname in form "Y.ie"
     * @return string    'NONE', 'SOME', or 'ALL'
     * @access protected
     */
    protected function any_domainnames_match($dname, $ns_value)
        {
        $nd = 1; $nm = 0;
        if(is_array($dname))
            {
            $nd=count($dname);
            foreach($dname as $k => $v)
                $nm += $this->domainname_matches($v, $ns_value);
            }
        else
            $nm = $this->domainname_matches($dname, $ns_value);
        return ($nm == 0) ? 'NONE' : ($nd == $nm ? 'ALL' : 'SOME');
        }

        /**
         * returns whether a single domain name matches a nameserver
         *
         * @param  string    $dname    single domain name
         * @param  string    $ns_value nameserver hostname in form "Y.ie"
         * @return int       1 - a domain matches a nameserver; 0 - does not match
         * @access protected
         */
    public function domainname_matches($dname, $ns_value)
        {
            if (strcmp($dname, $ns_value) == 0) return 1;
            else return preg_match('/\.'.$dname.'$/i', $ns_value); return 0;
        }

    /**
     * adds an error message to a model about an attribute
     * 
     * @param  CModel    $model     the model to add the error message to
     * @param  string    $attribute the name of the attribute which is invalid
     * @param  string    $msg       error message to add
     * @return void     
     * @access protected
     */
	protected function err($model,$attribute,$msg)
		{
		$lbls = $model->attributeLabels();
		$lbl = $lbls[$attribute];
		$model->addError($attribute,Yii::t('yii',$lbl.' '.$msg));
		}
	}

