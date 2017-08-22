<?php

/**
 * Description of RegistrarDetailsModel
 *
 * @author Artur Kielak
 */
class RegistrarDetailsModel extends CFormModel
{

    public $techContact;
    public $nameservers = array();
    public $nameserversCount;
    public $maxRows;
    public $minRows = 0;
    public $defaultRows;
    public $dnsNotificationPeriod;
    public $message;
    public $emailInvoiceFormat;

    public function __construct() {
        parent::__construct();
        CRSCommonAppService_service::getDomainSettings($result, $errs, Yii::app()->user->authenticatedUser);
        $this->maxRows = $result->maxCount;
        $this->defaultRows = $result->minCount;
        $this->nameserversCount = $this->defaultRows;
    }

    public function setEmailInvoiceFormat($emailInvoiceFormat)
    {
        $this->emailInvoiceFormat = $emailInvoiceFormat;
    }

    public function getEmailInvoiceFormat()
    {
        return $this->emailInvoiceFormat;
    }

    public function setTechContact($techContact)
    {
        $this->techContact = $techContact;
    }

    public function setNameservers($newServers)
    {
        $this->nameservers = is_array($newServers) ? $newServers : array($newServers);
    }

    public function setNameserversCount($newCount)
    {
        if ($newCount == 0) {
            $this->nameserversCount = $this->defaultRows;
        } else {
            $this->nameserversCount = $newCount;
        }
    }

    public function setDnsNotificationPeriod($dnsNotificationPeriod)
    {
        $this->dnsNotificationPeriod = $dnsNotificationPeriod;
    }

    public function setMessage($message)
    {
        $this->message = $message;
    }

    public function getTechContact()
    {
        return $this->techContact;
    }

    public function getNameservers()
    {
        return $this->nameservers;
    }

    public function getNameserversToSubmit()
    {
        $result = array();
        $j = 0;
        for($i = 0; $i < $this->nameserversCount; $i++) {
            if (!empty($this->nameservers[$i])) {
                $result[$j] = $this->nameservers[$i];
                $j++;
            }
        }
        return $result;
    }

    public function getDnsNotificationPeriod()
    {
        return $this->dnsNotificationPeriod;
    }

    public function getMessage()
    {
        return $this->message;
    }

    public function rules()
    {
        return array(
            array('techContact,emailInvoiceFormat', 'required'),
            array('techContact', 'UpperCaseValidator'),
            array('nameservers', 'NameserverListValidator', 'allowEmpty' => true),
            array('dnsNotificationPeriod', 'numerical', 'integerOnly' => true,),
        );
    }

    public function setFromPost($post)
    {
        Yii::log('FROM POST = ' . print_r($post, true));
        $this->techContact = $post['techContact'];
        $this->nameserversCount = $post['nameserversCount'];
        unset($this->nameservers);
        $this->nameservers = array();
        for ($i = 0; $i < $this->nameserversCount; $i++) {
            $this->nameservers[$i] = $post['nameservers'][$i];
        }
        $this->dnsNotificationPeriod = $post['dnsNotificationPeriod'];
        $this->emailInvoiceFormat = $post['emailInvoiceFormat'];
    }

    public function attributeLabels()
    {
        return array(
            'techContact' => 'Tech Contact',
            'dnsNotificationPeriod' => 'Dns Notification Period',
            'emailInvoiceFormat' => 'Email Invoice Format',
        );
    }

}

?>
