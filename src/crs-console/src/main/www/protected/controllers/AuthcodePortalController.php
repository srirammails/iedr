<?php

/**
 * defines AuthcodePortalController class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @copyright 2014 IEDR
 * @link      https://statistics.iedr.ie/
 */

class AuthcodePortalController extends Controller {

    public function actions() {
        return array(
                'captcha' => array(
                        'class'=>'CCaptchaAction',
                        'backColor'=>0xFFFFFF,
                        'testLimit'=>3,
                ),
        );
    }

    public function actionRequest() {
        $model = new AuthcodePortalModel();
        if (Yii::app()->request->isPostRequest) {
            if (isset($_POST['AuthcodePortalModel'])) {
                $model->setFromPOST($_POST['AuthcodePortalModel']);
                if ($model->validate()) {
                    $domain = $model->domain_name;
                    $email = $model->email;
                    $error = null;
                    CRSCommonAppService_service::sendAuthCodeFromPortal($error, $domain, $email);
                    if (isset($error)) {
                        // TODO WSAPIError.php should be used instead, but its API makes no sense in this use case
                        if (strstr($error, 'SOAP Exception : exception : ' . $domain)) {
                            $model->message = "We are sorry, the domain name you have entered does not exist. Please recheck it.";
                        } else if (strstr($error, 'AuthCodePortalEmailException')) {
                            $model->message = "We are sorry, the email address you have entered is not listed on the contact details for this domain. Please recheck your address, of if applicable, please contact your registrar for more information.";
                        } else if (strstr($error, 'AuthCodePortalLimitException')) {
                            $model->message = "We are sorry, the daily limit of generations has been reached. Please try again tomorrow.";
                        } else {
                            $model->message = $error;
                        }
                    } else {
                        $model->clear();
                        $model->message = "Your authcode has been sent to your email address";
                    }
                }
                $model->verifyCode = "";
            }
        }
        // always regenerate captcha on rendering new form
        Yii::app()->getController()->createAction('captcha')->getVerifyCode(true);
        $this->render('request', array('model' => $model));
    }

}