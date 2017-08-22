<?php
/**
* Validates Nameserver List along with related IP addresses, validation rules include:
*
* (1) existence - entered nameserver may not be blank (unless it is explicitly declared)
*
* (2) uniqueness - multiple nameservers of the same name are not allowed
*
* (3) syntactic correctness of the entered hosts
*
* (4) IP correctness
*
* (5) Glue-Record Validation Rules - whether IP address should be entered or not
*
* This Validator references may be added to specify, along with the name of the model-attribute to be validated,
* four additional names of items in the model (obligatory if the names are other than default):
*
* (1) Domain name (or domain name list) having the Nameserver
*
* (2) IP address list
*
* (3) number of hosts that are declared
*
* (4) whether to allow empty nameservers
*
* Example Model Validation Rules entry:
*
* array('someNameservers', 'NameserverListComplexValidator', 'dname'=>'someDomainName', 'ipName'=>'someIPAddresses', 'nameserversCount'=>'someNameserversCount'),
*/

class NameserverListComplexValidator
    extends NameserverListValidator
    {

    var $ipName = 'ipAddresses';
    var $ipList;

    var $nsNameGlueRecValidator;

    protected function initValues() {
        $ip_field = $this->ipName;
        if (!isset($this->model->$ip_field) || !is_array($this->model->$ip_field)) return false;
        $this->ipList = $this->model->$ip_field;
        return parent::initValues();
    }

    protected function initValidators() {
        parent::initValidators();
        $this->nsNameGlueRecValidator = new NSNameGlueRecValidator;
        $this->nsNameGlueRecValidator->dname = $this->dname;
        $this->nsNameGlueRecValidator->ns_name = $this->nsName;
    }

    protected function validateNameserver($i) {
        parent::validateNameserver($i);
        $ipName = $this->ipName;
        if (!$this->lowerCaseValidator->validateValue($this->ipList[$i])) {
            $this->model->addError("$ipName"."[$i]", "IP Address " . ($i + 1) . " must be lower case");
        }
        $this->nsNameGlueRecValidator->index = $i;
        $this->nsNameGlueRecValidator->validateAttribute($this->model, $this->ipName);
    }
}
?>