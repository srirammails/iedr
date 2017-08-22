<?php
/**
* Validates Nameserver List, validation rules include:
*
* (1) existence - entered nameserver may not be blank (unless it is explicitly declared)
*
* (2) uniqueness - multiple nameservers of the same name are not allowed
*
* (3) syntactic correctness of the entered hosts
*
* This Validator references may be added to specify, along with the name of the model-attribute to be validated,
* three additional names of items in the model (obligatory if the names are other than default):
*
* (1) domain name (or domain name list) having the Nameserver
*
* (2) number of hosts that are declared
*
* (3) whether to allow empty nameservers
*
* Example Model Validation Rules entry:
*
* array('someNameservers', 'NameserverListValidator', 'dname'=>'someDomainName', 'ipAddresses'=>'someIPAddresses', 'nameserversCount'=>'someNameserversCount'),
*/

class NameserverListValidator
    extends CValidator
    {

    var $dname = 'domainlist';
    var $nameserversCount = 'nameserversCount';
    var $allowEmpty = false;

    var $model;
    var $nsName;

    var $nameservers;
    var $count;

    var $hostNameValidator;
    var $lowerCaseValidator;

    public function validateAttribute($model, $attribute) {
        $this->model = $model;
        $this->nsName = $attribute;
        if (!$this->initValues()) return false;
        $this->initValidators();

        for ($i = 0; $i < $this->count; $i++) {
            $this->validateNameserver($i);
        }
    }

    protected function initValues() {
        $attribute = $this->nsName;
        if (!isset($this->model->$attribute) || !is_array($this->model->$attribute)) return false;
        $this->nameservers = $this->model->$attribute;
        $x = $this->nameserversCount;
        if (!isset($this->model->$x)) return false;
        $this->count = $this->model->$x;
        return true;
    }

    protected function initValidators() {
        $this->hostNameValidator = new HostNameValidator();
        $this->lowerCaseValidator = new LowerCaseValidator();
    }

    protected function validateNameserver($i) {
        $nsName = $this->nsName;
        if (!$this->allowEmpty && (!isset($this->nameservers[$i]) || $this->nameservers[$i] == null)) {
            $this->model->addError("$nsName"."[$i]", "Nameserver " . ($i + 1) . " cannot be blank");
        };
        if (isset($this->nameservers[$i]) && !empty($this->nameservers[$i]) && (!$this->hostNameValidator->validateValue($this->nameservers[$i]))) {
            $this->model->addError("$nsName"."[$i]", "Nameserver " . ($i + 1) . " is not a valid host name");
        };
        if (!$this->lowerCaseValidator->validateValue($this->nameservers[$i])) {
            $this->model->addError("$nsName"."[$i]", "Nameserver " . ($i + 1) . " must be lower case");
        }
        for ($j = $i+1; $j < $this->count; $j++) {
            if (isset($this->nameservers[$i]) && isset($this->nameservers[$j]) && !empty($this->nameservers[$i]) && !empty($this->nameservers[$j])) {
                if ($this->nameservers[$i] == $this->nameservers[$j]) {
                    $this->model->addError("$nsName"."[$i]", 'Nameserver ' . ($i + 1) . ' name duplicates ' . ($j + 1));
                    $this->model->addError("$nsName"."[$j]", 'Nameserver ' . ($j + 1) . ' name duplicates ' . ($i + 1));
                }
            }
        }
    }
}

?>