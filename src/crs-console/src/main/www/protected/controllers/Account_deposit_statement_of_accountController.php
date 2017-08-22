<?php

/**
 * Description of Account_deposit_statement_of_accountController
 *
 * @author Artur Kielak
 */
class Account_deposit_statement_of_accountController extends AccountsGridController {
   public function actionMenu() {
      $model = new AccountDepositModel();
      $this->render('menu', array('model' => $model));
   }
   
   public function actionGetgriddata_main() {
      $this->actionGetgriddata();
   }
   
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * ($pagenum - 1);
      $limit = $perpage;
      $result = null;
      $user = Yii::app()->user->authenticatedUser;
      $criteria->nicHandleId = Yii::app()->user->authenticatedUser->username;
      Yii::log("CRITERIA:" . print_r($criteria, true));
      CRSPaymentAppService_service::findUserHistoricalDeposits($result, $this->backend_errors, $user, $criteria, $offset, $limit, $sort);
      $count = 0;
      $hasTotals = false;
      if($result != null) {
         if (count($this->backend_errors) == 0) {
            Yii::log('results00 = '.print_r($result,true));
            
            if(array_key_exists('totalResults', $result)) {
                $count = $result->totalResults;
                $hasTotals = true;
            } else {
                $count = count($result);
            }
            
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

            if($count) {
               if($hasTotals) {
                  $result = $result->results;
               }
               
               if ( is_array($result) && is_object($result[0])) {
                  foreach ($result as $k => $o) {
                     $r = $this->addResult($model, $o);
                     if ($r != null) {
                        $pagedata['rows'][] = $r;
                     }
                  }
               } else if (is_object($result)) {
                  $r = $this->addResult($model, $result);
                  if ($r != null)
                     $pagedata['rows'][] = $r;
               } else {
                  Yii::log('no results : ' . print_r($result, true), 'warning', 'Accounts_historyController getData' . __LINE__);
               }
            }
         } else {
            Yii::log('Call wsapi method failure. (' . print_r($this->backend_errors, true) . ')');
         }
      } else {
         Yii::log('Call wsapi method result is null.');
      }
      return $pagedata;
   }
   
}

?>