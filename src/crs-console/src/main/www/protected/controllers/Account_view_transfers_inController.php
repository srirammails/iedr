<?php
/**
 * Description of Account_view_transfers_inController
 *
 * @author Artur Kielak
 */
class Account_view_transfers_inController extends GridController {
   public $records = 0;
   public $type = '';

   public function setCountRecords(&$model, $type, $criteria = null) {
      $this->records = 0;
      $result = null;
      
      $model->setSearchCriteria();
      
      $criteria = new CRSDomainAppService_transferedDomainSearchCriteria();
      $criteria->transferDateFrom = $model->transferDateFrom;
      $criteria->transferDateTo =  $model->transferDateTo;
      $criteria->transferDirection = $type;

      CRSDomainAppService_service::findTransferedDomains($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, 1000, 0, null);
      if (count($this->backend_errors) == 0 && $result != null) {
         $this->records = $result->totalResults;
         $model->totalrows = $this->records;
      } else {
         $model->totalrows = 0;
      }
   }

   public function setAttributtes($model, $attr) {
      $this->performAjaxValidation($model);
      if (Yii::app()->request->isPostRequest and isset($_POST[$attr])) {
         $model->attributes = $_POST[$attr];
         if (!$model->validate()) {
            $model->resetDays();
         }
         $model->setSearchCriteria();
         Yii::log(print_r($model, true), 'debug', 'Accounts_payhist_crednotesController::setAttributtes()');
      }
   }
   
   public function actionMenu() {
      $this->type = "INBOUND";
      $model = new TransfersAwayAndTo($this->type);
      $this->setAttributtes($model, 'TransfersAwayAndTo');
      $this->setCountRecords($model, $this->type);
      Utility::writeActionToSession('account_view_transfers_in/menu');
      $this->render('menu', array('model' => $model));
   }
   
   
   public function actionGetgriddata_in() {
      $this->type = "INBOUND";
      return $this->actionGetgriddata();
   }
   
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      Yii::log('CRITERIA= '.print_r($criteria,true));
      Yii::log('LIMIT= '.print_r($limit,true));
      $result = null;
      CRSDomainAppService_service::findTransferedDomains($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
      if (count($this->backend_errors) == 0) {
         $this->records = $result->totalResults;
         Yii::log('COUNT OK ' . print_r($this->records, true));
         if ($this->records && $result->results != null) {
            Yii::log('RESULT OK ' . print_r($result->results, true));
            $total = 0;
            if ($this->records > 0) {
               $total = ceil($this->records / $perpage);
            } else {
               $total = 0;
            }

            if ($pagenum > $total)
               $pagenum = $total;

            $pagedata['total'] = $total;
            $pagedata['page'] = $pagenum;
            $pagedata['records'] = $this->records;

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
