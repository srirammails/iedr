<?php

class DomainsTransferRegNew
extends FormModelBase
{

    /**
     * Name of a transferred domain
     * @var    string
     * @access public
     */
    public $domain_name;

    public function rules() {
        return array(
                array('domain_name', 'required'),
                array('domain_name', 'length', 'min' => 4, 'max' => 4096),
                array('domain_name', 'LowerCaseValidator'),
        );
    }

    public function attributeLabels() {
        return array(
                'domain_name' => 'Domain Name',
        );
    }

};
?>