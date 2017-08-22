<?php

/**
 * Short description for file
 *
 * Long description (if any) ...
 *
 * PHP version 5
 *
 * New Registration Console (C) IEDR 2011
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
 * Short description for class
 *
 * Long description (if any) ...
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
class Domains_RegNew
    extends FormModelBase
{

    /**
     * Description for public
     * @var    string
     * @access public
     */
    public $domain_names;

    /**
     * Description for protected
     * @var    unknown
     * @access protected
     */
    protected $_domain_array;

    /**
     * Description for protected
     * @var    array
     * @access protected
     */
    protected $_domain_errs_array;

    /**
     * indicate, if the model should handle one domain only
     */
    public $oneDomainAllowed = false;

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return string Return description (if any) ...
     * @access public
     */
    public function getScenario()
    {
        return 'submit';
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return mixed  Return description (if any) ...
     * @access public
     */
    public function rules()
    {
        return array(
            array('domain_names', 'required'),
            array('domain_names', 'length', 'min' => 4, 'max' => 4096),
            array('domain_names', 'safe', 'on' => $this->getScenario()),
            array('domain_names', 'LowerCaseValidator'),
            array('domain_names', 'validateDomainListSyntax'),
        );
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return mixed  Return description (if any) ...
     * @access public
     */
    public function attributeLabels()
    {
        if ($this->oneDomainAllowed) {
            return array(
                'domain_names' => 'Domain Name',
            );
        } else {
            return array(
                'domain_names' => 'Domain Name(s)',
            );
        }
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param  unknown $aCModelEvent Parameter description (if any) ...
     * @return void
     * @access public
     */
    public function onBeforeValidate($aCModelEvent)
    {
        $this->domain_names = trim($this->domain_names, "\r\n\t,: ;");
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param  boolean $force Parameter description (if any) ...
     * @return unknown Return description (if any) ...
     * @access public
     */
    public function getDomainsAsArray($force = false)
    {
        if ($force)
            $this->_domain_array = null;
        if ($this->_domain_array == null) {
            $this->_domain_errs_array = array();
            $this->_domain_array = Utility::parseInput($this->domain_names, $this->_domain_errs_array);
            if ($this->oneDomainAllowed && count($this->_domain_array) > 1) {
                $this->_domain_array = array($this->_domain_array[0]);
            }
            Yii::log('Domain String: ' . $this->domain_names, 'debug', 'Domains_RegNew->getDomainsAsArray()');
            Yii::log('Domain Array: ' . print_r($this->_domain_array, true), 'debug', 'Domains_RegNew->getDomainsAsArray()');
        }
        return $this->_domain_array;
    }

    #Validate Domain List


    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param  unknown $attrib Parameter description (if any) ...
     * @param  unknown $params Parameter description (if any) ...
     * @return boolean Return description (if any) ...
     * @access public
     */
    public function validateDomainListSyntax($attrib, $params)
    {
        $this->getDomainsAsArray(); // also sets errors array
        Yii::log('ERRS_ARRAY= ' . print_r($this->_domain_errs_array, true));
//       	if($this->oneDomainAllowed && count($this->_domain_errs_array)>1) {
// 			$err = 'Only one domain may be passed';
// 			Yii::log('Err Msg: '.$err, 'debug', 'Domains_RegNew->checkDomSyntax()');
// 			$this->addError('domain_names',$err);
//       		return false;
//       	} 

        if (count($this->_domain_errs_array) > 0) {
            $err = '';
            foreach ($this->_domain_errs_array as $k => $o) {
                $err = $err . encode($o) . " is invalid domain name<br>";
            }
            $this->addError('domain_names', $err);
            return false;
        }
        return true;
    }
}
