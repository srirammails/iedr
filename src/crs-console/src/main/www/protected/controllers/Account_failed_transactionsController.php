<?php
/**
 * Description of Account_failed_transactionsController
 *
 * @author Artur Kielak
 */
class Account_failed_transactionsController extends AccountsGridController {
   public function actionMenu() {
      $at_model = new FilledTransactionModel();
      if (Yii::app()->request->isPostRequest and isset($_POST['AllTicketsModel'])) {
         $at_model->attributes = $_POST['AllTicketsModel'];
      }
      Utility::writeActionToSession('account_failed_transactions/menu');
      $this->render('menu', array('model' => $at_model));
   }
   
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * ($pagenum - 1);
      $limit = $perpage;
      

      $sc = new CRSTicketAppService_sortCriterion();
      $sc->ascending = true; // type=xs:boolean, (NOT NULL), min=1
      if ($_GET['sord'] == "desc") {
         $sc->ascending = false;
      }

      if (is_null($model->columns[$_GET['sidx']]['criteriafield'])) {
         $sc->sortBy = 'id'; // type=xs:string, (NOT NULL), min=0
      } else {
         $sc->sortBy = $model->columns[$_GET['sidx']]['criteriafield'];
      }
      
      $criteria->accountId = Yii::app()->user->id;
      $criteria->financialStatus = CRSTicketAppService_financialStatusEnum::_STALLED;
      
      $result = null;
      CRSTicketAppService_service::find($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sc);
      if($result != null) {
         if($result->totalResults) {
            Yii::log('RESULT FILED TRANSACTION = '.print_r($result,true));
            if (count($this->backend_errors) == 0) {
               $pagedata['records'] = $result->totalResults;
               $pagedata['total'] = math_div($result->totalResults, $perpage) + 1;
               if (is_array($result->list))
                  foreach ($result->list as $k => $o)
                     $pagedata['rows'][] = $model->addResults($o);
               else
               if (is_object($result->list))
                  $pagedata['rows'][] = $model->addResults($result->list);
               else
                  Yii::log(print_r($result, true), 'warning', 'TicketsController::getData() : WARN : no \'results\':' . __LINE__);
            } else {
               Yii::log(print_r($this->backend_errors, true), 'error', 'TicketsController::getData() : ERROR:' . __LINE__);
            }
         } else {
           Yii::log('NO RESULTS IN FOUND TICKETS!');
         }  
      }
      return $pagedata;
   }
}
?>


