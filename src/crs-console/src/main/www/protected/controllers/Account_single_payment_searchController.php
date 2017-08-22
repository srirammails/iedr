<?php
/**
 * Description of Account_single_payment_searchController
 *
 * @author Artur Kielak
 */
class Account_single_payment_searchController extends AccountsGridController {
   public function assignDomainName(&$model) {
      if(isset($_POST['ViewSinglePaymentSearchModel'])) {
         if(isset($_POST['ViewSinglePaymentSearchModel']['domainName'])) {
            if(!empty($_POST['ViewSinglePaymentSearchModel']['domainName'])) {
               $model->domainName = $_POST['ViewSinglePaymentSearchModel']['domainName'];
            }
         }
      }      
   }
   public function actionMenu() {
      Yii::log('POST= '.print_r($_POST,true));
      Yii::log('GET= '.print_r($_GET,true));

      $model = new ViewSinglePaymentSearchModel();
      $this->assignDomainName($model);
      $this->render("menu", array('model'=>$model));
   }
   
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $errs = '';
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $count = 0;

      $user = Yii::app()->user->authenticatedUser;
      $criteria = new CRSPaymentAppService_reservationSearchCriteria();
      $criteria->billingNhId = $user->username;
      
      Yii::log('citeria= ' . print_r($criteria, true));
      Yii::log('$offset= ' . print_r($offset, true));
      Yii::log('$limit= ' . print_r($limit, true));
      Yii::log('$sort= ' . print_r($sort, true));
      
      if(isset($model->domainName) && !empty($model->domainName)) {
         Yii::log('Isset domain = '.print_r($model->domainName,true));
         $criteria->domainName = $model->domainName;
      }
      
      Yii::log('cri= '.print_r($criteria,true));
      $result = null;
      CRSPaymentAppService_service::findHistoricalReservations($result, $this->backend_errors, $user, $criteria, $offset, $limit, $sort);
      if (count($this->backend_errors) == 0 && $result != null) {
         Yii::log('Results= '.print_r($result,true));
         if(property_exists($result, 'total')) {
            $count = $result->total;
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

         if($result->total) {
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
               Yii::log('no results', 'warning', 'Account_single_payment_searchController' . __LINE__);
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
