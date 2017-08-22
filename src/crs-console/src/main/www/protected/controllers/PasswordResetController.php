<?php
/**
 * defines PasswordREsetController class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */

/**
 * This controller class handles user's password reset use 
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
 
class PasswordResetController extends Controller {
	/**
	 * handles the request of changing the password
	 */
	public function actionRequest() {
		$model = new ResetPasswordForm();
		if (isset($_POST['ResetPasswordForm'])) {
			#Yii::log('POST CHANGES '.print_r($_POST,true));
			$model->setScenario(ResetPasswordForm::tokenRequestScenario);
			$model->attributes = $_POST['ResetPasswordForm'];
			if ($model->validate()) {
				$model->requestResetToken();
				if (empty($model->errMsg)) {					
					$model->msg = "Password reset token sent to your email address";
					$this->redirect(array('passwordReset/changePassword'));
				} else {
					$model->msg = "Cannot send password reset token: ".$model->errMsg;
				}
			}
		}
		$this->render('request', array('model'=>$model));
	}
	
	public function actionChangePassword() {
		$model = new ResetPasswordForm();
		#Yii::log('POST CHANGES '.print_r($_POST,true));
		if (isset($_POST['ResetPasswordForm'])) {
			#Yii::log('POST CHANGES '.print_r($_POST,true));
			$model->setScenario(ResetPasswordForm::passwordChangeScenario);
			$model->attributes = $_POST['ResetPasswordForm'];
			if ($model->validate()) {
				$model->changePassword();
				if (empty($model->errMsg)) {		
					$model->msg = "Password changed";
// 					$this->redirect(array('site/login'));
				} else {
					$model->msg = "Unsuccessful password change attempt: ".$model->errMsg;
				}
			} 
		}
		
		$this->render('resetPassword', array('model'=>$model));
	}
}

?>