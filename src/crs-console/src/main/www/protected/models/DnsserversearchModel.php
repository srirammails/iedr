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
class DnsserversearchModel
    extends GridModelBase
{
    // BUG what unique key?


    /**
     * Description for public
     * @var    string
     * @access public
     */
    public $defaultSortColumn = 'PK';

    /**
     * Description for var
     * @var    array
     * @access public
     */
    var $columns = array(
        'PK' => array(
            'resultfield' => 'domainName',
            'criteriafield' => 'domainName',
            'label' => 'Domain Name',
            'width' => 55,
            'link' => 'domains/viewdomain',
            'type' => 'string',
        ),
        'B' => array(
            'resultfield' => 'holderName',
            'criteriafield' => 'holderName',
            'label' => 'Domain Holder',
            'width' => 55,
            'type' => 'string',
        ),
        'C' => array(
            'resultfield' => 'renewalDate',
            'criteriafield' => 'renewalDate',
            'label' => 'Ren Date',
            'width' => 60,
            'type' => 'datefilter',
        ),
        'D' => array(
            'resultfield' => 'registrationDate',
            'criteriafield' => 'registrationDate',
            'label' => 'Reg Date',
            'width' => 60,
            'type' => 'datefilter',
        ),
        'F' => array(
            'resultfield' => 'dnsName',
            'criteriafield' => 'dnsName',
            'label' => 'DNS Name',
            'width' => 40,
            'type' => 'string',
        ),
        'G' => array(
            'resultfield' => 'dnsIpAddress',
            'criteriafield' => 'dnsIpAddress',
            'label' => 'Glue',
            'width' => 30,
            'type' => 'string',
        ),
        'H' => array(
            'resultfield' => 'dnsOrder',
            'criteriafield' => 'dnsOrder',
            'label' => 'NS Order',
            'width' => 20,
            'type' => 'string',
        ),
    );

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return array  Return description (if any) ...
     * @access public
     */
    public function getSearchBase()
    {
        return new CRSDomainAppService_nsReportSearchCriteria();
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param  array  $o Parameter description (if any) ...
     * @return mixed  Return description (if any) ...
     * @access public
     */
    public function addResults($o)
    {
        ModelUtility::hasProperty($this->columns, $o);
        return array
        (
            'PK' => encode($o->domainName),
            'B' => cleanString(encode($o->holderName)),
            'C' => parseXmlDate($o->renewalDate),
            'D' => parseXmlDate($o->registrationDate),
            'F' => encode($o->dnsName),
            'G' => encode($o->dnsIpAddress),
            'H' => $o->dnsOrder,
        );
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return string    Return description (if any) ...
     * @access protected
     */
    protected function getExportFilenameTag()
    {
        return 'DnsserversearchModel_';
    }

    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        foreach ($searchparams as $key => $value) {
            if (is_object($value) && isset($value->field)) {
                $field = $this->columns[$value->field]['criteriafield'];
                if ($field != null) {
                    switch($field) {
                        case 'renewalDate':
                            $dates = split(" ",$value->data);
                            $criteria->renewalFrom = $dates[0];
                            $criteria->renewalTo = $dates[1];
                            unset($criteria->$field);
                            break;
                        case 'registrationDate':
                            $dates = split(" ",$value->data);
                            $criteria->registrationFrom = $dates[0];
                            $criteria->registrationTo = $dates[1];
                            unset($criteria->$field);
                            break;
                    }

                }
            }
        }
        Yii::log('SEARCH CITERIA= ' . print_r($criteria, true) . ' IN DnsserversearchModel');
        return $criteria;
    }

}

