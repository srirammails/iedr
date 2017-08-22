<?php

/**
 * Description of Account_deposit_balanceController
 *
 * @author Artur Kielak
 */
class Account_deposit_balanceController extends AccountsGridController {
   public function actionMenu() {
      $this->render('menu');
   }
      public function setDeposits(&$model) {
      $viewResult = null;
      $viewErrors = '';
      $user = Yii::app()->user->authenticatedUser;
      CRSPaymentAppService_service::viewDeposit($viewResult, $viewErrors, $user);
      if ($viewResult != null) {
         if (!isset($viewErrors)) {
            $model->depositBalance = $viewResult->closeBal;
            $model->depositReservation = $viewResult->reservedFunds;
            $model->availableBalance = $viewResult->availableFunds;
         } else {
            Yii::log('topup deposit count error ' . print_r($viewErrors, true));
         }
      } else {
         Yii::log('topup deposit result is null.');
      }
   }
   public function actionDeposit_balance() {
      $params = array();
      if (isset($_REQUEST['days'])) {
         $params = array('days' => $_REQUEST['days'],);
      } 

      $model = new DepositTopUpHistoryModel();
      $model->searchParams = $params;
      $this->setDeposits($model);
      $this->render('deposit_balance', array('model' => $model));
   }
}

?>
