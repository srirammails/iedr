<?php
/**
 * Description of Account_topup_my_deposit_accountController
 *
 * @author Artur Kielak
 */
class Account_topup_my_deposit_accountController extends AccountsGridController {
   protected function findDeposit($model, &$result) {
		$payreq = CreditCardFormSegmentModel::asWSAPIPaymentObject($model);
		$retval = CRSPaymentAppService_service::depositFunds($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $payreq);
		if(!$retval) {
			Yii::log('findDeposit error= '.print_r($this->backend_errors,true));
      } else {
			Yii::log('findDeposit = '.print_r($result,true));
		}
		return $retval;
	}
      
   public function actionMenu() {
      $model = new AccountTopUpModel();
      $model->setScenario('topup');
      $this->performAjaxValidation($model);
      if (Yii::app()->request->isPostRequest and isset($_POST['AccountTopUpModel'])) {
         $model->setFromPOST($_POST['AccountTopUpModel']);
         if ($model->validate()) {
            if ($this->findDeposit($model, $model->result)) {
               $model->message = 'TRANSACTION_OK';
            } else {
               $model->error = $this->backend_errors;
            }
         }
      }
      $this->render('menu', array('model' => $model));
   }
}
?>


