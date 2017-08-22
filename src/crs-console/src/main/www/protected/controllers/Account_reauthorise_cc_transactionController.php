<?php

class Account_reauthorise_cc_transactionController extends AccountsGridController {

   public function actionMenu() {
      $model = new ReauthorizeCCTransactionModel();
      Utility::writeActionToSession('account_reauthorise_cc_transaction/menu');
      $this->render("menu", array('model' => $model));
   }

   public function actionGetgriddata_re() {
      $this->exportFileName = 'Invoice_';
      $this->actionGetgriddata();
   }

   public function actionReauthorise() {
      $model = new ReauthorizeCCTransactionPayModel();
      $transactionId = '';
      if(!isset($_GET['id'])) {
         $this->redirect('account_reauthorise_cc_transaction/menu');
      } else {
         $transactionId = $_GET['id'];
      }
      $this->performAjaxValidation($model);
      if (Yii::app()->request->isPostRequest && isset($_POST['ReauthorizeCCTransactionPayModel'])) {
         $model->setFromPOST($_POST['ReauthorizeCCTransactionPayModel']);
         if ($model->validate()) {
            $result = null;
            $errs = '';
            $paymentRequest = CreditCardFormSegmentModel::asWSAPIPaymentObject($model);
            CRSCommonAppService_service::reauthoriseTransaction($result, $errs, Yii::app()->user->authenticatedUser, $transactionId, $paymentRequest);
            if($result != null) {
               Yii::log('RE RESULT IS NOT NULL ');
               $model->message = 'TRANSACTION_OK';
            } else {
               Yii::log('RE RESULT IS NULL '.print_r($errs,true));
               $model->error = $errs;
            }
         }
      }

      $this->render("reauthorise", array('model' => $model));
   }

   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $count = 0;

      $user = Yii::app()->user->authenticatedUser;
      $result = null;

      CRSPaymentAppService_service::getTransactionToReauthorise($result, $this->backend_errors, $user, $offset, $limit, $sort);
       if (count($this->backend_errors) == 0) {
           if (isset($result) && $result != null && property_exists($result, 'results')) {
               $count = $result->totalResults;
               $total = 0;
               if ($count > 0) {
                   $total = ceil($count / $perpage);
               } else {
                   $total = 0;
               }

               if ($pagenum > $total)
                   $pagenum = $total;

               $pagedata['total'] = $total;
               $pagedata['page'] = $pagenum;
               $pagedata['records'] = $count;

               if (is_array($result->results)) {
                   foreach ($result->results as $key => $o) {
                       $row = $model->addResults($o);
                       $pagedata['rows'][] = $row;
                   }
               } else if (is_object($result->results)) {
                   Yii::log('is_object');
                   $row = $model->addResults($result->results);
                   $pagedata['rows'][] = $row;
               } else {
                   Yii::log('Undefined result');
               }
           } else {
               Yii::log('Result is null.');
           }
       } else {
           Yii::log('Some errors ' . print_r($this->backend_errors, true));
       }
       return $pagedata;
   }

}

?>
