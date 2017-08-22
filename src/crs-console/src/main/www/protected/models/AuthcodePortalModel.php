<?php

/**
 * defines AuthcodePortalModel class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @copyright 2014 IEDR
 * @link      https://statistics.iedr.ie/
 */

class AuthcodePortalModel extends DynamicModelBase {

    public $domain_name;
    public $email;
    public $verifyCode;
    public $message;

    public function rules() {
        return array(
                array('domain_name, email, verifyCode', 'required'),
                array('domain_name', 'LowerCaseValidator'),
                array('email', 'EmailAddrValidator'),
                array('verifyCode', 'captcha', 'on'=>'insert'),
                array('verifyCode', 'activeCaptcha')
        );
    }

    public function clear() {
        $this->domain_name = null;
        $this->email = null;
        $this->verifyCode = null;
    }

    public function activeCaptcha() {
        $code = Yii::app()->controller->createAction('captcha')->verifyCode;
        if ($code != $this->verifyCode) {
            $this->addError('verifyCode', 'Wrong code.');
        }
    }

};

?>