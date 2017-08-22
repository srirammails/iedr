<?php

/**
 * Description of Account_today_deposit_reservationsController
 *
 * @author Artur Kielak
 */
class Account_today_deposit_reservationsController extends AccountsGridController {

   public function cleanModel(&$model) {
      $model->setTotalResults(number_format(0));
      $model->setTotalVat(number_format(0, 2));
      $model->setTotalWithVat(number_format(0, 2));
      $model->setTotalAmount(number_format(0, 2));
   }

   public function actionMenu() {
      $model = new ViewToDayDepositReservationModel();
      $this->cleanModel($model);
      $result = null;
      CRSPaymentAppService_service::getNotSettledReservationsTotals($result, $this->backend_errors, Yii::app()->user->authenticatedUser, 'true');
      if ($result != null) {
         if (count($this->backend_errors) == 0) {
            $model->setTotalResults($result->totalResults);
            $model->setTotalVat($result->totalVat);
            $model->setTotalWithVat($result->totalWithVat);
            $model->setTotalAmount($result->totalAmount);
         } else {
            Yii::log('Some errors= ' . print_r($this->backend_errors, true));
            $model->setErrors($this->backend_errors);
         }
      } else {
         Yii::log('Result is null in actionReady()   ' . print_r($this->backend_errors, true));
         $model->setErrors('null');
      }
      Utility::writeActionToSession('account_today_deposit_reservations/menu');
      $this->render('menu', array('model' => $model));
   }

   public function actionGetgriddataDepositBalanceReady() {
      return $this->actionGetgriddata();
   }

   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $count = 0;

      Yii::log('offset : ' . print_r($offset, true));
      Yii::log('limit : ' . print_r($limit, true));
      Yii::log('sort : ' . print_r($sort, true));

      $result = null;
      $criteria->accountId = Yii::app()->user->id;
      $criteria->paymentMethod = CRSPaymentAppService_paymentMethod::_ADP;
      Yii::log('criteria : ' . print_r($criteria, true));

      CRSPaymentAppService_service::getNotSettledReservations($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
      if (count($this->backend_errors) == 0) {
         Yii::log('ResultT= ' . print_r($result, true));
         if (isset($result) && $result != null && property_exists($result, 'results')) {
            $count = $result->total;
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
