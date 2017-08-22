<?php

class CurrentNRPStatusesSelectionModel extends DynamicDomainListModel
{
    public $period;
    public $command;
    public $returnurl;
    public $needCreditCard;
    public $renewalDateType;
    public $defaultPeriods = array();

    public function __construct($isVoluntary = false)
    {
        parent::__construct($isVoluntary);

        $this->val_rules = array(
            array('domainlist,command,returnurl,renewalDateType', 'required'),
            array('command', 'validateCommand'),
            array('renewalDateType', 'validateRenewalDateType'),
            array('needCreditCard', 'boolean'),
        );
        $this->needCreditCard = 0;
        $this->renewalDateType = 'CURRENT';

        if ($isVoluntary == false) {
            $this->addMixinModel(new CreditCardFormSegmentModel());
            /*
            * Just to make asterisk available
            */
            $this->val_rules = array_merge($this->val_rules, array(
                array('cardholder, creditcardno, cardtype, exp_date, exp_month, exp_year, cvn', 'required'),
            ));
        }
    }

    public function validateCommand($attrib, $params)
    {
        $v = ' is not a valid Command';
        switch ($this->$attrib) {
            case 'paydeposit':
            case 'payonline':
            case 'removefromvoluntary':
                $v = null;
                break;
            default :
                break;
        }
        return $v;
    }

    public function validateRenewalDateType($attrib, $params)
    {
        $v = ' is not a valid Renewal Date Type';
        switch ($this->$attrib) {
            default :
                break;
        }
        return ' ';
    }

    public function setFromPOST($p)
    {
        parent::setFromPOST($p);
        if ($this->needCreditCard == 1)
            $this->setScenario(CreditCardFormSegmentModel::$without_amount_scenario);
    }

    public function isDebitCardAllowed()
    {
        return true;
    }

}

