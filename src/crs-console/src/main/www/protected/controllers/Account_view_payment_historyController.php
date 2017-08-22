<?php
/**
 * Description of Account_view_payment_historyController
 *
 * @author Artur Kielak
 */
class Account_view_payment_historyController extends AccountsGridController {
   public function actionMenu() {
      $model = new ViewPaymentHistoryModel();
      Utility::writeActionToSession('account_view_payment_history/menu');
      $this->render("menu", array('model'=>$model));
   }
   
   public function actionPaymentView() {
   	if(array_key_exists('id', $_GET) && isset($_GET['id'])) {
   		$model = new ViewSinglePaymentModel($_GET['id']);
	   	$this->render("view_single_payment", array('model'=>$model));
   	} else {
   		$this->redirect("index.php?r=account_view_payment_history/menu");
   	}
   }
   
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $errs = '';
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $count = 0;

      $result = null;
      
      $user = Yii::app()->user->authenticatedUser; 
      $criteria = new CRSPaymentAppService_transactionSearchCriteria();
      $criteria->billingNhId = $user->username;
      
      Yii::log('citeria= ' . print_r($criteria, true));
      Yii::log('$offset= ' . print_r($offset, true));
      Yii::log('$limit= ' . print_r($limit, true));
      Yii::log('$sort= ' . print_r($sort, true));
      
      CRSPaymentAppService_service::findHistoricalTransactions($result, $this->backend_errors, $user, $criteria, $offset, $limit, $sort);
      if (count($this->backend_errors) == 0 && $result != null) {
         Yii::log('Results= '.print_r($result,true));
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

         if($result->totalResults) {
            if ( is_array($result->results) && is_object($result->results[0])) {
               foreach ($result->results as $k => $o) {
                  $r = $this->addResult($model, $o);
                  if ($r != null) {
                     $pagedata['rows'][] = $r;
                  }
               }
            } else if (is_object($result->results)) {
               $r = $this->addResult($model, $result->results);
               if ($r != null)
                  $pagedata['rows'][] = $r;
            } else {
               Yii::log('no results : ' . print_r($result, true), 'warning', 'Accounts_msdController::getInvoicesData()' . __LINE__);
            }
         }
      }
      else {
         Yii::log(print_r($this->backend_errors, true), 'error', ' findHistoricalTransactions ' . __LINE__);
      }

      return $pagedata;
   }
}

?>
