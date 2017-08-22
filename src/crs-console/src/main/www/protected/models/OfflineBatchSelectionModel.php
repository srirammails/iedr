<?php

class OfflineBatchSelectionModel extends GridSelectionModel
{

    public function __construct()
    {
        $this->val_rules = array(
            array('domainlist,command,returnurl', 'required'),
            array('command', 'validateCommand'),

        );
        $this->attr_labels = array('domainlist' => 'Domains to be Processed',);

    }

    public function createDomainConfirmList()
    {
        if ($this->domainlist != null) {
            foreach ($this->domainListToArray() as $k => $v) {
                $mbrnam = DynamicDomainListModel::domainToVarPrefix($v);

                // 'confirmed' for confirm checkbox
                $mbr_conf = $mbrnam . '_confirmed';
                $this->$mbr_conf = true;
                $this->addValRule($mbr_conf, 'boolean');

                // add domain-name as label for this one
                $this->attr_labels[$mbr_conf] = $v;
            }
            #Yii::log(print_r($this,true), 'debug', 'DynamicDomainListModel::createDomainConfirmList()');
        }
        else
            Yii::log('domainlist WAS NULL .. ' . print_r($this, true), 'error', 'DynamicDomainListModel::createDomainConfirmList()');
    }
}
