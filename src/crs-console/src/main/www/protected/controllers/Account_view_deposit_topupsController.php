<?php

/**
 * Description of Account_view_deposit_topupsController
 *
 * @author Artur Kielak
 */
class Account_view_deposit_topupsController extends AccountsGridController {

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

   public function actionMenu() {
      $params = array();
      if (isset($_REQUEST['days'])) {
         $params = array('days' => $_REQUEST['days'],);
      }
      $model = new DepositTopUpHistoryModel();
      $model->searchParams = $params;
      if (!$model->validate()){
        $model->resetSearchParams();
      }
      $this->setDeposits($model);
      $this->render('menu', array('model' => $model));
   }

   public function actionGetGridDataTopUpHistory() {
      $this->actionGetgriddata();
   }

   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;

      $result = null;
      $count = 0;

      Yii::log('TOPUP_HIST = ' . print_r($criteria, true));
      Yii::log('citeria= ' . print_r($criteria, true));
      Yii::log('$offset= ' . print_r($offset, true));
      Yii::log('$limit= ' . print_r($limit, true));
      Yii::log('$sort= ' . print_r($sort, true));

      $result = new CRSPaymentAppService_depositTopUpSearchResultVO();
      CRSPaymentAppService_service::getTopUpHistory($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria['fromDate'], $criteria['toDate'], $offset, $limit);
      if (count($this->backend_errors) == 0) {
         if ($result != null) {
            Yii::log('totalResults in top up history ' . print_r($result->totalResults, true));
            if (isset($result->results))
               Yii::log('result in top up history ' . print_r($result->results, true));
         } else {
            Yii::log('result is null in top up history!');
         }
      } else {
         Yii::log('backend errors in top up history!');
      }
      Yii::log('TOP_UP_HISTORY_RESULT' . print_r($result, true), 'debug', 'getData()');
      if (isset($result))
         $count = $result->totalResults;

      if (count($this->backend_errors) == 0) {
         Yii::log('COUNT OK ' . print_r($count, true));
         if ($result != null) {
            Yii::log('RESULT OK ' . print_r($result, true));
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

            if (isset($result->results)) {
               if (is_array($result->results) && is_object($result->results[0])) {
                  foreach ($result->results as $key => $o) {
                     $row = $model->addResults($o);
                     $pagedata['rows'][] = $row;
                  }
               } else if (is_object($result->results)) {
                  $row = $model->addResults($result->results);
                  $pagedata['rows'][] = $row;
               } else {
                  Yii::log('Undefined result');
               }
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
