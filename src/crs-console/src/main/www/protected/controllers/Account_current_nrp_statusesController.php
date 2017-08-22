<?php

class Account_current_nrp_statusesController extends AccountsGridController {

   public function __construct() {
      $this->exportFileName = 'CurrentNRPStatuses_';
   }

   public function actionConfirm() {
      $this->showConfirmPage();
   }

   public function getSelectionModel($isVoluntary = false) {
      return new CurrentNRPStatusesSelectionModel($isVoluntary);
   }

   public function getSelectionModelName() {
      return 'CurrentNRPStatusesSelectionModel';
   }
   
   public function actionMain() {
      $this->render('account_current_nrp_statuses/main');
   }


   public function actionMenu() {
      $resultDomains = null;
      $errors = '';
      $count = 0;
      $result = null;

      CRSNicHandleAppService_service::get($resultDomains, $errors, Yii::app()->user->authenticatedUser, Yii::app()->user->authenticatedUser->username);
      if ($resultDomains != null) {
         if (count($errors) == 0) {
            Yii::log('RESULT= ' . print_r($resultDomains, true));
            $criteria = new CRSDomainAppService_domainSearchCriteria();
            $criteria->activeFlag = false;
            CRSDomainAppService_service::findDomains($result, $errors, Yii::app()->user->authenticatedUser, $criteria, 0, 1, null);
            if (count($this->backend_errors) == 0 && $result != null) {
               $count = $result->totalResults;
            }
         }
      }

      $model = new CurrentNRPStatusesModel($this);
      Utility::writeActionToSession('account_current_nrp_statuses/menu');
      $this->render('account_current_nrp_statuses/menu', array('model' => $model, 'countDomains' => $count));
   }

   public function actionGetgriddata_menu() {
      $this->actionGetgriddata();
   }

   public function actionGetgriddata_deletions() {
      $this->exportFileName = 'CurrentNRPStatuses_';
      $this->actionGetgriddata();
   }

   protected function showConfirmPage() {
      Yii::log('SHOW CONFIGRM PAGE= '.print_r($_POST,true) .   '   '. print_r($_GET,true));
      $rows = array();
      $domains = array();
      if(isset($_POST['gridactionpay']['domainlist'])) {
         $rows = split(",",$_POST['gridactionpay']['domainlist']);
         for($i=0;$i<count($rows);$i++) {
            $tmp = split("~",$rows[$i]);
            $domains [] = $tmp[0];
         }
         $_POST['gridactionpay']['domainlist'] = implode(",", $domains);
      }
      
      Yii::log('domainlist= '.print_r($rows,true));
      
      $postedForm = isset($_POST['gridactionpay']) ? $_POST['gridactionpay'] : ( isset($_POST['gridactionnopay']) ? $_POST['gridactionnopay'] : null );
      if ($postedForm != null) {
         switch($postedForm['command']){
            case 'removefromvoluntary':
               $model = $this->getSelectionModel(true);
               break;
               default: 
               $model = $this->getSelectionModel();
               break;
         }
         
         $model->setFromPOST($postedForm);

         $which = isset($_POST['gridactionpay']) ? 'gridactionpay' : 'gridactionnopay';
         
         $model->needCreditCard = ($model->command == 'payonline') ? 1 : 0;
         $model->defaultPeriods = implode(",",$rows);
         $this->redirectAfterPost('account_current_nrp_statuses/confirmaction', $model);
      } else {
         $this->redirect($model->returnurl, true);
      }
   }


   public function getViewBase() {
      return '/account_current_nrp_statuses/';
   }

   public function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $count = 0;
      $result = null;
      
      $criteria->activeFlag = false;
      $criteria->attachReservationInfo=true;
      CRSDomainAppService_service::findDomains($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
      Yii::log('Result= '.print_r($result,true));
      if (count($this->backend_errors) == 0 && $result != null) {
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
         
         if($count) {
            if (is_array($result->results) and is_object($result->results[0])) {
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
         } else {
            Yii::log('NO RESULTS IN NRP!');
         }
      } else {
         Yii::log(print_r($this->backend_errors, true), 'error', 'Accounts_msdController::getInvoicesData()' . __LINE__);
      }
      return $pagedata;
   }

}

